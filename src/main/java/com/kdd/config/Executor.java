package com.kdd.config;

import java.lang.reflect.Method;
import java.util.Map;

import org.apache.log4j.Logger;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.kdd.actions.ActionsClass;
import com.kdd.utility.ExcelReader;
import com.kdd.utility.ReportManager;
import com.kdd.utility.ReportUtil;
import com.kdd.utility.ScreenshotUtility;

public class Executor implements GlobalVariables{

	private ActionsClass keywordActions = new ActionsClass();
	private Method method[] = keywordActions.getClass().getMethods();
	private final Logger Log = Logger.getLogger(Executor.class.getName());

	public Map<Integer, String> getListOfTestCasesToExecute() {
		Log.info("Getting List of Test Cases to execute.");
		return ExcelReader.getTestCasesToRun(testDataSheet, runModeColumn, testCaseColumn);
	}

	public void executeTestCase(String testCaseID) throws Exception {
		String keyword;
		String selector;
		String locator = "NA";
		String value;
		String stepDesc;
		String objectArray[];
		int numberOfRows = ExcelReader.getNumberOfRows(testCaseID);
		for (int iRow = 1; iRow < numberOfRows; iRow++){
			keyword = ExcelReader.getCellData(iRow, keywordColumn);
			selector = new ObjectReader().getObjectProperty(ExcelReader.getCellData(iRow, objectsColumn));
			if(selector !=null) {
				objectArray = selector.split(":");
				locator = objectArray[0].trim();
				selector = objectArray[1].trim();
			}
			value = ExcelReader.getCellData(iRow, valuesColumn);
			stepDesc = ExcelReader.getCellData(iRow, testStepDescriptionColumn);			
			Log.info("Keyword: "+keyword+" Locator: "+ locator+" Selector: "+ selector+" Value: "+ value);			
			try {
				executeAction(keyword, locator, selector, value);			
			} catch (Exception e) {
				Log.error("Exception Occured while executing the step:\n", e);
				ReportManager.getTest().fail(e);
				String imageFilePath = ScreenshotUtility.takeScreenShot(DriverManager.getDriver(), testCaseID);
				ReportManager.getTest().info("Screenshot", MediaEntityBuilder.createScreenCaptureFromPath(imageFilePath).build());	
				ReportUtil.addKeyword(FAIL+": "+stepDesc,keyword,FAIL,imageFilePath);
				throw e;
			}
			if(stepDesc !=null && !stepDesc.isEmpty() ) {
				ReportUtil.addKeyword(stepDesc,keyword,PASS,null);
				ReportManager.getTest().pass(stepDesc+"<br/>Keyword: "+keyword+" | Selector: "+ selector+" | Locator: "+ locator+" | Value: "+ value);
			}
		}
	}

	private void executeAction(String keyword, String locator, String selector, String value) throws Exception {		
		for(int i = 0;i < method.length;i++){
			if(method[i].getName().equals(keyword)){
				method[i].invoke(keywordActions,locator, selector, value);
				break;
			}
		}
	}
}
