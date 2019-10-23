package com.kdd.config;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.kdd.utility.ElementOperations;

public class DriverManager implements GlobalVariables{

	private static WebDriver driver;

	public static WebDriver getDriver() {
		return driver;
	}

	public static WebDriver launchBrowser() {
		String browser = Config.getProperty("browser");

		if(browser.equalsIgnoreCase("CHROME")) {
			System.setProperty("webdriver.chrome.driver", chromePath);
			ChromeOptions option = new ChromeOptions();
			option.addArguments("--disable-infobars;");
			driver = new ChromeDriver(option);		
		}
		driver.get(baseURL);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
		new ElementOperations().setDriver(driver);
		return driver;
	}
}
