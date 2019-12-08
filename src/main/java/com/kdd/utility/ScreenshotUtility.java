package com.kdd.utility;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.kdd.config.GlobalVariables;

public class ScreenshotUtility implements GlobalVariables{
	private static final Logger log = Logger.getLogger(ScreenshotUtility.class.getName());
	
	public static String takeScreenShot(WebDriver driver,String testCaseName){
		// Take screenshot and store as a file format
		File src= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String screenShotFilePath = screenshotFolder+testCaseName+DateUtility.getStringDate("_ddMMyyyy_HHmmss")+fileFormat;
		try{
			FileUtils.copyFile(src, new File(screenShotFilePath));
		}
		catch(Exception exp){
			log.error("Exception occured in takeScreenShot: "+exp.getMessage());
		}
		return screenShotFilePath;
	}
}
