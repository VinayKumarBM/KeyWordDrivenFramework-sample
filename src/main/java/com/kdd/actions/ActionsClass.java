package com.kdd.actions;

import static org.testng.Assert.*;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

import com.kdd.config.GlobalVariables;
import com.kdd.config.Session;
import com.kdd.exceptions.InvalidLocatorException;
import com.kdd.utility.DateUtility;
import com.kdd.utility.ElementOperations;

public class ActionsClass implements GlobalVariables{

	private final Logger Log = Logger.getLogger(ActionsClass.class.getName());
	
	public void click (String locator, String selector, String value) throws InvalidLocatorException {
		ElementOperations.getElement(locator, selector).click();
	}
	
	public void enterText (String locator, String selector, String value) throws InvalidLocatorException {
		ElementOperations.getElement(locator, selector).sendKeys(value);
	}

	public void clearAndEnterText (String locator, String selector, String value) throws InvalidLocatorException {
		WebElement element = ElementOperations.getElement(locator, selector);
		element.clear();
		element.sendKeys(value);
	}
	
	public void enterRandomEmailID (String locator, String selector, String value) throws InvalidLocatorException {
		value = "jondoe"+DateUtility.getStringDate("_ddMMyyyyHHmmss")+"@gamil.com";
		Log.info("Random email Id: "+value);
		enterText(locator, selector, value);
	}
	
	public void validateDataOnScreenMatches (String locator, String selector, String value) throws InvalidLocatorException {
		String onScreenText = ElementOperations.getElement(locator, selector).getText();
		Log.info("Data on the screen: "+onScreenText);
		assertEquals(onScreenText, value, "On Screen Text did not match");
	}
	
	public void waitForObjectToAppear (String locator, String selector, String value) throws InvalidLocatorException {
		ElementOperations.waitForVisiblityOfElement(locator, selector, objectWaitTime);
	}
	
	public void switchToFrame (String locator, String selector, String value) throws InvalidLocatorException {
		ElementOperations.switchToFrame(locator, selector);
	}
	
	public void switchToDefaultContent (String locator, String selector, String value) {
		ElementOperations.switchToDefaultContent();
	}
	
	public void moveToObjectAndClick (String locator, String selector, String value) throws InvalidLocatorException {
		ElementOperations.moveToObjectAndClick(locator, selector);
	}
	
	public void selectFromDropdown (String locator, String selector, String value) throws InvalidLocatorException {
		ElementOperations.selectFromDropdown(locator, selector, value);
	}
	
	public void validateDataOnScreenContains (String locator, String selector, String value) throws InvalidLocatorException {
		String onScreenText = ElementOperations.getElement(locator, selector).getText().toLowerCase();
		Log.info("Text on screen: "+onScreenText);
		assertTrue(onScreenText.contains(value.toLowerCase()), "On screen text "+onScreenText+" did not contain "+value.toLowerCase());
	}
	
	public void validateSearchResults (String locator, String selector, final String value) throws InvalidLocatorException {
		List<WebElement> eleList = ElementOperations.getElements(locator, selector);
		Session.setVariable("resultCount", eleList.size());
		eleList.stream().forEach(rs->{
			Log.info(rs.getText());
			assertTrue(rs.getText().toLowerCase().contains(value.toLowerCase()), "Search Result did not match");
		});
	}
	
	public void validateSearchResultCount (String locator, String selector, String value) throws InvalidLocatorException {
		String resultCountDetails = ElementOperations.getElement(locator, selector).getText();
		assertTrue(resultCountDetails.contains(String.valueOf(Session.getVariable().get("resultCount"))));
	}
	
	public void pause(String locator, String selector, String value) {
		try {
			Thread.sleep(Long.parseLong(value) * 1000);
		} catch (NumberFormatException | InterruptedException e) {
			Log.error("Exception Occured:", e);
		}
	}
}
