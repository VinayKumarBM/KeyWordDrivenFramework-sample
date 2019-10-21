package com.kdd.actions;

import static org.testng.Assert.*;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

import com.kdd.config.GlobalVariables;
import com.kdd.config.Session;
import com.kdd.utility.DateUtility;
import com.kdd.utility.ElementOperations;

public class ActionsClass implements GlobalVariables{

	private final Logger Log = Logger.getLogger(ActionsClass.class.getName());
	
	public void click (String locator, String selector, String value) {
		Log.info("Clicking on "+locator);
		ElementOperations.getElement(locator, selector).click();
	}
	
	public void enterText (String locator, String selector, String value) {
		Log.info("Entering value into "+locator);
		ElementOperations.getElement(locator, selector).sendKeys(value);
	}

	public void clearAndEnterText (String locator, String selector, String value) {
		Log.info("Entering value into "+locator);
		WebElement element = ElementOperations.getElement(locator, selector);
		element.clear();
		element.sendKeys(value);
	}
	
	public void enterRandomEmailID (String locator, String selector, String value) {
		value = "jondoe"+DateUtility.getStringDate("_ddMMyyyyHHmmss")+"@gamil.com";
		Log.info("Random email Id: "+value);
		enterText(locator, selector, value);
	}
	
	public void validateDataOnScreenMatches (String locator, String selector, String value) {
		String heading = ElementOperations.getElement(locator, selector).getText();
		Log.info("Data on the screen: "+heading);
		assertEquals(heading, value, "Page heading did not match");
	}
	
	public void waitForObjectToAppear (String locator, String selector, String value) {
		ElementOperations.waitForVisiblityOfElement(locator, selector, objectWaitTime);
	}
	
	public void switchToFrame (String locator, String selector, String value) {
		ElementOperations.switchToFrame(locator, selector);
	}
	
	public void switchToDefaultContent (String locator, String selector, String value) {
		ElementOperations.switchToDefaultContent();
	}
	
	public void moveToObjectAndClick (String locator, String selector, String value) {
		ElementOperations.moveToObjectAndClick(locator, selector);
	}
	
	public void selectFromDropdown (String locator, String selector, String value) {
		ElementOperations.selectFromDropdown(locator, selector, value);
	}
	
	public void validateDataOnScreenContains (String locator, String selector, String value) {
		String heading = ElementOperations.getElement(locator, selector).getText().toLowerCase();
		Log.info("Search Result Heading: "+heading);
		assertTrue(heading.contains(value.toLowerCase()), "Search Result Heading did not match");
	}
	
	public void validateSearchResults (String locator, String selector, final String value) {
		List<WebElement> eleList = ElementOperations.getElements(locator, selector);
		Session.setVariable("resultCount", eleList.size());
		eleList.stream().forEach(rs->{
			Log.info(rs.getText());
			assertTrue(rs.getText().toLowerCase().contains(value.toLowerCase()), "Search Result did not match");
		});
	}
	
	public void validateSearchResultCount (String locator, String selector, String value) {
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
