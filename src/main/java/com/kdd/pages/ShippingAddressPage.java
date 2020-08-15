package com.kdd.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import com.kdd.config.DriverManager;
import com.kdd.utility.ElementOperations;

public class ShippingAddressPage extends ElementOperations {
	
	private WebDriver driver;
	
	public ShippingAddressPage() {
		driver = DriverManager.getInstance().getDriver();
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, implicitWaitTime), this);
	}	
	
	@FindBy(name = "shipToFirstName")
	private WebElement shipToFirstNameTextbox;
	
	@FindBy(name = "shipToLastName")
	private WebElement shipToLastNameTextbox;
	
	@FindBy(name = "shipAddress1")
	private WebElement shipAddress1Textbox;
		
	@FindBy(name = "shipAddress2")
	private WebElement shipAddress2Textbox;

	@FindBy(name = "shipCity")
	private WebElement shipCityTextbox;
	
	@FindBy(name = "shipState")
	private WebElement shipStateTextbox;
	
	@FindBy(name = "shipZip")
	private WebElement shipZipTextbox;
	
	@FindBy(name = "shipCountry")
	private WebElement shipCountryTextbox;
	
	@FindBy(xpath = "//button[text()='Back']")
	private WebElement backButton;

}
