package com.kdd.runner;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.kdd.config.Directory;
import com.kdd.config.DriverManager;
import com.kdd.config.Executor;
import com.kdd.config.GlobalVariables;
import com.kdd.reports.ReportManager;
import com.kdd.reports.ReportUtil;
import com.kdd.utility.DateUtility;
import com.kdd.utility.ExcelReader;
import com.kdd.utility.Log;

public class TestBase implements GlobalVariables{
	
	final Logger Logs = Logger.getLogger(TestBase.class.getName());
	Executor executor = new Executor();
	String testCaseName;
	int testCaseRow;
	String testStartTime;
	Directory directory = new Directory();
	
	@BeforeSuite
	public void configurations(ITestContext context) {
		DOMConfigurator.configure("log4j.xml");
		Logs.info("Setting up Test data Excel sheet");
		directory.clearFolder(screenshotFolder);
		directory.clearFolder(htmlReportPath);
		directory.clearFolder(customRerportPath);
		ExcelReader.setExcelFile(testDataPath);
		ExcelReader.clearColumnData(testDataSheet, resultColumn, testDataPath);
		ReportUtil.startTesting(); 
		ReportUtil.startSuite(context.getCurrentXmlTest().getSuite().getName());
	}
	
	@BeforeMethod
	public void initialization() {
		testStartTime = DateUtility.getStringDate("hh.mm.ss aaa");
		DriverManager.getDriver();
	}
	
	@AfterMethod
	public void updateStatus(ITestResult result) throws Exception {
		String status = SKIP;
		ExcelReader.getNumberOfRows(testDataSheet);
		if(result.getStatus() == ITestResult.FAILURE) {
			status = FAIL;								
		}
		if(result.getStatus() == ITestResult.SUCCESS) {
			status = PASS;
		}
		Logs.info("Closing all the browser.");
		ExcelReader.setCellData(status, testCaseRow, resultColumn, testDataPath);
		ReportUtil.addTestCase(testCaseName, testStartTime, DateUtility.getStringDate("hh.mm.ss aaa"), status);				
		ReportManager.endTest();
		DriverManager.quit();
		Log.endTestCase(testCaseName+" "+status);
	}
	
	@AfterSuite
	public void endTestSuite() {	
		ReportUtil.endSuite();
		ReportUtil.updateEndTime();		
	}
}
