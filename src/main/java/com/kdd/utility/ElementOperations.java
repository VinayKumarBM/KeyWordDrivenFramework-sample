package com.kdd.utility;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ElementOperations {

	private static WebDriver driver;

	public void setDriver(WebDriver driver) {
		if(driver!=null)
			ElementOperations.driver = driver;
		else 
			new Throwable("Driver was not set in Element Operation");
	}

	public static WebElement getElement(String locator, String selector) {
		WebElement element = driver.findElement(getElementBy(locator, selector));
		return element;
	}

	public static List<WebElement> getElements(String locator, String selector) {
		List<WebElement> elements = driver.findElements(getElementBy(locator, selector));
		return elements;
	}

	private static By getElementBy(String locator, String selector) {
		By byLocator = null;
		switch (locator.toLowerCase()) {
		case "id": {
			byLocator = By.id(selector);
			break;
		}
		case "name": {
			byLocator = By.name(selector);
			break;
		}
		case "partiallinktext": {
			byLocator = By.partialLinkText(selector);
			break;
		}
		case "linktext": {
			byLocator = By.linkText(selector);
			break;
		}
		case "classname": {
			byLocator = By.className(selector);
			break;
		}
		case "tagname": {
			byLocator = By.tagName(selector);
			break;
		}
		case "cssselector": {
			byLocator = By.cssSelector(selector);
			break;
		}
		case "xpath": {
			byLocator = By.xpath(selector);
			break;
		}
		default:{
			new Exception("Invalid Object selector type");
		}
		}
		return byLocator;
	}

	public static WebElement waitForVisiblityOfElement(String locator, String selector, long timeOutInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		return wait.until(ExpectedConditions.visibilityOf(getElement(locator, selector)));
	}

	public static void switchToFrame(String locator, String selector) {
		driver.switchTo().frame(getElement(locator, selector));
	}

	public static void switchToDefaultContent() {
		driver.switchTo().defaultContent();
	}

	public static void moveToObjectAndClick(String locator, String selector) {
		Actions action = new Actions(driver);
		action.moveToElement(getElement(locator, selector)).click().build().perform();
	}

	public static void selectFromDropdown(String locator, String selector, String value) {
		new Select(getElement(locator, selector)).selectByVisibleText(value);
	}
}
