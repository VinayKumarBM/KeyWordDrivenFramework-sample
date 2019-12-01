package com.kdd.runner;

import java.util.Map;
import java.util.Map.Entry;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.kdd.config.Executor;
import com.kdd.config.SessionDataManager;
import com.kdd.reports.ReportManager;
import com.kdd.utility.Log;

public class TestExecutor extends TestBase{

	@Test (dataProvider = "testCasesList")
	public void testCasesExecutor(int testRow, String testCase) throws Exception {
		Executor executor = new Executor();		
		SessionDataManager.getInstance().setSessionData("testCaseName", testCase);
		SessionDataManager.getInstance().setSessionData("testCaseRow", testRow);
		Log.startTestCase(testCase);
		ReportManager.startTest(testCase);
		executor.executeTestCase(testCase);
	}

	@DataProvider (name = "testCasesList")//, parallel = true)
	public Object[][] getTestCaseList(){
		Executor executor = new Executor();
		Map<Integer, String> mapOfTestCases = executor.getListOfTestCasesToExecute();
		Object[][] testCaseList = new Object[mapOfTestCases.size()][2];
		int i=0;
		for (Entry<Integer, String> mapIterator: mapOfTestCases.entrySet()) {
			testCaseList[i][0] = mapIterator.getKey(); 
			testCaseList[i][1] = mapIterator.getValue(); 
			i++;
		}			
		return testCaseList;
	}
}
