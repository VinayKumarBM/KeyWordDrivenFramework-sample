package com.kdd.utility;

import org.apache.log4j.Logger;

public class Log {

	//Initialize Log4j logs
	private static final Logger Log = Logger.getLogger(Log.class.getName());//

	// This is to print log for the beginning of the test case, as we usually run so many test cases as a test suite
	public static synchronized void startTestCase(String testCaseName){
		Log.info("****************************************************************************************");
		Log.info("****************************************************************************************");
		Log.info("\t\t\t\t"+testCaseName.toUpperCase()+ " START");
		Log.info("****************************************************************************************");
		Log.info("****************************************************************************************");
	}
	
	public static synchronized void endTestCase(String testCaseName){
		Log.info("****************************************************************************************");
		Log.info("****************************************************************************************");
		Log.info("\t\t\t\t"+testCaseName.toUpperCase());
		Log.info("****************************************************************************************");
		Log.info("****************************************************************************************");
	}

}
