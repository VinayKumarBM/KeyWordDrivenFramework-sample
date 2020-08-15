package com.kdd.utility;

import java.util.List;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.kdd.config.GlobalVariables;
import com.kdd.exceptions.InvalidLocatorException;

public class ElementOperations implements GlobalVariables{

	private static final Logger Log = Logger.getLogger(ElementOperations.class.getName());

	private String[] getElementLocator(String locatorDetails) {		
		return locatorDetails.split("\\|");
	}

	public static void pause(long value) {
		try {
			Thread.sleep(value * 1000);
		} catch (NumberFormatException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected WebElement getElement(WebDriver driver, String locatorDetails) throws InvalidLocatorException {
		String[] locator = getElementLocator(locatorDetails);
		return driver.findElement(getElementBy(locator[0].trim(), locator[1].trim()));
	}

	protected List<WebElement> getElements(WebDriver driver, String locatorDetails) throws InvalidLocatorException {
		String[] locator = getElementLocator(locatorDetails);
		return driver.findElements(getElementBy(locator[0].trim(), locator[1].trim()));		
	}

	private By getElementBy(String locator, String selector) throws InvalidLocatorException {
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
		case "partiallink": {
			byLocator = By.partialLinkText(selector);
			break;
		}
		case "link": {
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
		case "css": {
			byLocator = By.cssSelector(selector);
			break;
		}
		case "xpath": {
			byLocator = By.xpath(selector);
			break;
		}
		default:{
			throw new InvalidLocatorException("Invalid Locator type "+locator);
		}
		}
		return byLocator;
	}

	protected String getElementText(WebElement element) {
		String text = element.getText().trim();
		Log.info("Message: "+text);
		return text;
	}

	protected boolean isElementDisplayed(WebElement element) {
		try {
			return element.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	protected void enterText(WebElement element, String data) {		
		element.clear();
		Log.info("Entering text: "+data);
		element.sendKeys(data);
	}

	protected WebElement getElementByReplacingText(WebDriver driver, String locatorDetails, String key) throws InvalidLocatorException {
		return getElement(driver, String.format(locatorDetails, key));
	}

	protected WebElement waitForVisiblityOfElement(WebDriver driver, WebElement element, long timeOutInSeconds) throws InvalidLocatorException {
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		return wait.until(ExpectedConditions.visibilityOf(element));
	}

	protected void switchToFrame(WebDriver driver, WebElement element) throws InvalidLocatorException {
		driver.switchTo().frame(element);
	}

	protected void switchToDefaultContent(WebDriver driver) {
		driver.switchTo().defaultContent();
	}

	protected void moveToObjectAndClick(WebDriver driver, WebElement element) throws InvalidLocatorException {
		Actions action = new Actions(driver);
		action.moveToElement(element).click().build().perform();
	}

	protected void selectByVisibleText(WebElement element, String text) {
		Log.info("Selecting: "+text);
		new Select(element).selectByVisibleText(text);
	}
}
