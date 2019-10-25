package com.kdd.config;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverManager implements GlobalVariables{

	private static WebDriver driver;
	
	private DriverManager() {
		if(driver == null) {
			driver = launchBrowser();
		}
	}

	public static WebDriver getDriver() {
		new DriverManager();
		return driver;
	}

	private static WebDriver launchBrowser() {
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
//		new ElementOperations().setDriver(driver);
		return driver;
	}

	public static void quit() {
		getDriver().quit();
		driver = null;
	}
}
