package com.kdd.config;

import java.lang.reflect.Method;
import java.util.Map;

import org.apache.log4j.Logger;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.kdd.actions.ActionsClass;
import com.kdd.exceptions.InvalidKeywordException;
import com.kdd.reports.ReportManager;
import com.kdd.reports.ReportUtil;
import com.kdd.utility.DateUtility;
import com.kdd.utility.ExcelReader;
import com.kdd.utility.ScreenshotUtility;

public class Executor implements GlobalVariables{

	private static final Logger Log = Logger.getLogger(Executor.class.getName());

	public Map<Integer, String> getListOfTestCasesToExecute() {
		Log.info("Getting List of Test Cases to execute.");
		return new ExcelReader().getTestCasesToRun(testDataSheet, runModeColumn, testCaseColumn);
	}

	public void executeTestCase(String testCaseID) throws Exception {
		String keyword;
		String selector;
		String locator = "NA";
		String value;
		String stepDesc;
		String objectArray[];
		try {
			int numberOfRows = ExcelReader.getNumberOfRows(testCaseID);
			for (int iRow = 1; iRow < numberOfRows; iRow++){
				keyword = ExcelManager.getInstance().getExcelReader().getCellData(iRow, keywordColumn, testCaseID);
				selector = new ObjectReader().getObjectProperty(ExcelManager.getInstance().getExcelReader().getCellData(iRow, objectsColumn, testCaseID));
				if(selector !=null) {
					objectArray = selector.split(":");
					locator = objectArray[0].trim();
					selector = objectArray[1].trim();
				}
				value = ExcelManager.getInstance().getExcelReader().getCellData(iRow, valuesColumn, testCaseID);
				stepDesc = ExcelManager.getInstance().getExcelReader().getCellData(iRow, testStepDescriptionColumn, testCaseID);			
				Log.info("Keyword: "+keyword+" Locator: "+ locator+" Selector: "+ selector+" Value: "+ value);			
				try {
					executeAction(keyword, locator, selector, value);			
				} catch (Exception e) {
					Log.error("Exception Occured while executing the step:\n", e);
					ReportManager.getTest().fail(e);
					String imageFilePath = ScreenshotUtility.takeScreenShot(DriverManager.getInstance().getDriver(), testCaseID);
					ReportManager.getTest().info("Screenshot", MediaEntityBuilder.createScreenCaptureFromPath(imageFilePath).build());	
					ReportUtil.addKeyword(FAIL+": "+stepDesc,keyword,FAIL,imageFilePath);
					throw e;
				}
				if(stepDesc !=null && !stepDesc.isEmpty() ) {
					ReportUtil.addKeyword(stepDesc,keyword,PASS,null);
					ReportManager.getTest().pass(stepDesc+"<br/>Keyword: "+keyword+" | Selector: "+ selector+" | Locator: "+ locator+" | Value: "+ value);
				}
			}
		} catch (Exception e) {
			int testCaseRow = (Integer) SessionDataManager.getInstance().getSessionData("testCaseRow");
			ExcelManager.getInstance().getExcelReader().setCellData(FAIL, testCaseRow, resultColumn, testDataPath, testDataSheet);
			String testCaseName = (String) SessionDataManager.getInstance().getSessionData("testCaseName");
			String testStartTime = (String) SessionDataManager.getInstance().getSessionData("testStartTime");
			ReportUtil.addTestCase(testCaseName, testStartTime, DateUtility.getStringDate("hh.mm.ss aaa"), FAIL);
			throw e;
		}
	}

	private void executeAction(String keyword, String locator, String selector, String value) throws Exception {		
		ActionsClass keywordActions = new ActionsClass();
		Method method[] = keywordActions.getClass().getMethods();
		boolean keywordFound = false;
		for(int i = 0;i < method.length;i++){
			if(method[i].getName().equals(keyword)){
				method[i].invoke(keywordActions,locator, selector, value);
				keywordFound = true;
				break;
			}
		}
		if(!keywordFound) {
			throw new InvalidKeywordException("Invalid Keyword "+keyword);
		}
	}
}
