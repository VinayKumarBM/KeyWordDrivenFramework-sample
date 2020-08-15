package com.kdd.config;

import java.lang.reflect.Method;
import java.util.Map;

import org.apache.log4j.Logger;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.kdd.actions.ActionsClass;
import com.kdd.exceptions.InvalidKeywordException;
import com.kdd.reports.ReportManager;
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
		String value;
		String stepDesc;
		try {
			int numberOfRows = ExcelReader.getNumberOfRows(testCaseID);
			for (int iRow = 1; iRow < numberOfRows; iRow++){
				keyword = ExcelManager.getInstance().getExcelReader().getCellData(iRow, keywordColumn, testCaseID);
				value = ExcelManager.getInstance().getExcelReader().getCellData(iRow, dataColumn, testCaseID);
				stepDesc = ExcelManager.getInstance().getExcelReader().getCellData(iRow, testStepDescriptionColumn, testCaseID);			
				Log.info("Keyword: "+keyword+" Value: "+ value);			
				try {
					executeAction(keyword, value);			
				} catch (Exception e) {
					Log.error("Exception Occurred while executing the step:\n", e);					
					String imageFilePath = ScreenshotUtility.takeScreenShot(DriverManager.getInstance().getDriver(), testCaseID);
					ReportManager.getTest().fail(stepDesc+"<br/>Keyword: "+keyword+" | Value: "+ value, MediaEntityBuilder.createScreenCaptureFromPath(imageFilePath).build());
					ReportManager.getTest().info(e);
					throw e;
				}
				if(stepDesc !=null && !stepDesc.isEmpty() ) {
					ReportManager.getTest().pass(stepDesc+"<br/>Keyword: "+keyword+" | Value: "+ value);
				}
			}
		} catch (Exception e) {
			int testCaseRow = (Integer) SessionDataManager.getInstance().getSessionData("testCaseRow");
			ExcelManager.getInstance().getExcelReader().setCellData(FAIL, testCaseRow, resultColumn, testDataPath, testDataSheet);
			throw e;
		}
	}

	private void executeAction(String keyword, String value) throws Exception {		
		ActionsClass keywordActions = new ActionsClass();
		Method method[] = keywordActions.getClass().getMethods();
		boolean keywordFound = false;
		for(int i = 0;i < method.length;i++){
			if(method[i].getName().equals(keyword)){
				method[i].invoke(keywordActions, value);
				keywordFound = true;
				break;
			}
		}
		if(!keywordFound) {
			throw new InvalidKeywordException("Invalid Keyword "+keyword);
		}
	}
}
