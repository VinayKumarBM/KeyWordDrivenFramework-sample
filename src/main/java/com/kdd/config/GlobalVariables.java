package com.kdd.config;

public interface GlobalVariables {

	String baseDirectory = System.getProperty("user.dir");
	String configPath = baseDirectory+"/config.properties";
	String objectRepository = baseDirectory+"/src/main/java/com/kdd/objects/object.properties";
	String chromePath = baseDirectory+"/src/test/resources/drivers/chromedriver.exe";

	// Application URL
	String baseURL = "http://automationpractice.com/index.php";

	// Path to test data sheet with test cases to run and test case details
	String testDataPath = baseDirectory+"/src/test/resources/data/TestDataSheet.xlsx";
	String testDataSheet = "TestCases";
	int testCaseColumn = 0;
	int testCaseDescriptionColumn = 1;
	int runModeColumn = 2;
	int resultColumn = 3;

	// Columns in Test Case sheet
	int testStepsColumn = 0;
	int keywordColumn = 1;
	int objectsColumn = 2;
	int locatorsColumn = 3;
	int valuesColumn = 4;
	int testStepDescriptionColumn = 5;

	String PASS = "PASS";
	String FAIL = "FAIL";
	String SKIP = "SKIP";

	// Wait times
	long implicitWaitTime = 15;
	long objectWaitTime = 30;

	// Screenshot folder and file details
	String screenshotFolder = baseDirectory+"/screenshots/";
	String fileFormat = ".png";
	
	// Report related details 
	String extentConfigFilePath = baseDirectory+"/extent-config.xml";
	String htmlReportPath = baseDirectory+"/target/html-report/";
	String htmlFileName = "index.html";
	
	// Custom report related details
	String customRerportPath = baseDirectory+"/reports/";
}
