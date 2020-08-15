package com.kdd.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import com.kdd.config.DriverManager;
import com.kdd.utility.ElementOperations;

public class BillingDetailsPage extends ElementOperations {
	
	private WebDriver driver;
	
	public BillingDetailsPage() {
		driver = DriverManager.getInstance().getDriver();
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, implicitWaitTime), this);
	}
	
	@FindBy(name = "cardType")
	private WebElement cardTypeDropdown;
	
	@FindBy(name = "creditCard")
	private WebElement creditCardTextbox;
	
	@FindBy(name = "expiryDate")
	private WebElement expiryDateTextbox;
	
	@FindBy(name = "billToFirstName")
	private WebElement billToFirstNameTextbox;
	
	@FindBy(name = "billToLastName")
	private WebElement billToLastNameTextbox;
	
	@FindBy(name = "billAddress1")
	private WebElement billAddress1Textbox;
		
	@FindBy(name = "billAddress2")
	private WebElement billAddress2Textbox;

	@FindBy(name = "billCity")
	private WebElement billCityTextbox;
	
	@FindBy(name = "billState")
	private WebElement billStateTextbox;
	
	@FindBy(name = "billZip")
	private WebElement billZipTextbox;
	
	@FindBy(name = "billCountry")
	private WebElement billCountryTextbox;
	
	@FindBy(name = "shippingAddressRequired")
	private WebElement differentShippingAddressCheckbox;
	
	@FindBy(xpath = "//button[text()='Continue']")
	private WebElement continueButton;
	
	@FindBy(xpath = "//button[text()='Cancel']")
	private WebElement cancelButton;
	
	public void clickContinueButton() {
		continueButton.click();
	}
	
	public void clickCancelButton() {
		cancelButton.click();
	}
}