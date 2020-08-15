package com.kdd.runner;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.kdd.config.Config;
import com.kdd.config.Directory;
import com.kdd.config.DriverManager;
import com.kdd.config.GlobalVariables;
import com.kdd.config.SessionDataManager;
import com.kdd.reports.ReportManager;
import com.kdd.utility.ExcelReader;
import com.kdd.utility.Log;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase implements GlobalVariables{
	
	private static final Logger Logs = Logger.getLogger(TestBase.class.getName());
	
	@BeforeSuite
	public void configurations(ITestContext context) {
		DOMConfigurator.configure("log4j.xml");
		Logs.info("Setting up Test data Excel sheet");
		Directory directory = new Directory();
		directory.clearFolder(screenshotFolder);
		directory.clearFolder(htmlReportPath);
		ExcelReader.setExcelFile(testDataPath);
		ExcelReader.clearColumnData(testDataSheet, resultColumn, testDataPath);
	}
	
	@BeforeMethod
	public void initialization() {
		WebDriver driver = launchBrowser();
		DriverManager.getInstance().setDriver(driver);
	}
	
	@AfterMethod
	public void updateStatus(ITestResult result) throws Exception {
		String status = SKIP;
		if(result.getStatus() == ITestResult.FAILURE) {
			status = FAIL;								
		}
		if(result.getStatus() == ITestResult.SUCCESS) {
			status = PASS;
		}
		Logs.info("Closing all the browser.");
		String testCaseName = (String) SessionDataManager.getInstance().getSessionData("testCaseName");
		ReportManager.endTest();
		DriverManager.getInstance().getDriver().quit();
		Log.endTestCase(testCaseName+" "+status);
	}
	
	private static WebDriver launchBrowser() {
		String browser = Config.getProperty("browser");
		WebDriver driver = null;
		if(browser.equalsIgnoreCase("CHROME")) {
			WebDriverManager.chromedriver().arch32().setup();
			ChromeOptions option = new ChromeOptions();
			option.addArguments("--disable-infobars");
			driver = new ChromeDriver(option);		
		}
		driver.get(baseURL);
		driver.manage().window().maximize();
		return driver;
	}
}
