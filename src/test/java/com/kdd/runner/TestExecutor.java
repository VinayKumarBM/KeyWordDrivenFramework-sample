package com.kdd.runner;

import static org.testng.Assert.fail;

import java.util.Map;
import java.util.Map.Entry;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.kdd.utility.Log;
import com.kdd.utility.ReportManager;

public class TestExecutor extends TestBase{

	@Test (dataProvider = "testCasesList")
	public void testCasesExecutor(int testRow, String testCase) {
		testCaseName = testCase;
		testCaseRow = testRow;
		Log.startTestCase(testCaseName);
		ReportManager.startTest(testCaseName);
		try {
			executor.executeTestCase(testCaseName);
		} catch (Exception e) {			
			fail("Exception Occured while executing the step:\n", e);
		}			
	}

	@DataProvider (name = "testCasesList")
	public Object[][] getTestCaseList(){
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
