package com.kdd.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import com.kdd.config.DriverManager;
import com.kdd.utility.ElementOperations;

public class RegisterationPage extends ElementOperations {
	
	private WebDriver driver;
	
	public RegisterationPage() {
		driver = DriverManager.getInstance().getDriver();
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, implicitWaitTime), this);
	}
	
	@FindBy(name = "repeatedPassword")
	private WebElement confirmPasswordTextbox;
	
	@FindBy(name = "firstName")
	private WebElement firstnameTextbox;
	
	@FindBy(name = "lastName")
	private WebElement lastnameTextbox;
	
	@FindBy(name = "email")
	private WebElement emailTextbox;
	
	@FindBy(name = "phone")
	private WebElement telephoneTextbox;
	
	@FindBy(name = "address1")
	private WebElement address1Textbox;
	
	@FindBy(name = "address2")
	private WebElement address2Textbox;
	
	@FindBy(name = "city")
	private WebElement cityTextbox;
	
	@FindBy(name = "state")
	private WebElement stateTextbox;
	
	@FindBy(name = "zip")
	private WebElement zipTextbox;
	
	@FindBy(name = "country")
	private WebElement countryTextbox;
	
	@FindBy(name = "languagePreference")
	private WebElement languageDropdown;
	
	@FindBy(name = "favouriteCategoryId")
	private WebElement favouriteCategoryDropdown;
	
	@FindBy(name = "listOption")
	private WebElement myListCheckbox;
	
	@FindBy(name = "bannerOption")
	private WebElement myBannerCheckbox;
	
	@FindBy(css = ".button-bar>.button")
	private WebElement saveButton;
	
	@FindBy(css = "#MessageBar>p")
	private WebElement messageText;

	public void enterConfirmPassowrd(String confirmPassword) {
		enterText(confirmPasswordTextbox, confirmPassword);
	}
	
	public void enterFirstName(String firstName) {
		enterText(firstnameTextbox, firstName);
	}
	
	public void enterLastName(String lastName) {
		enterText(lastnameTextbox, lastName);
	}
	
	public void enterEmail(String email) {
		enterText(emailTextbox, email);
	}
	
	public void enterTelephone(String phone) {
		enterText(telephoneTextbox, phone);
	}
	
	public void enterAddress1(String address1) {
		enterText(address1Textbox, address1);
	}
	
	public void enterAddress2(String address2) {
		enterText(address2Textbox, address2);
	}
	
	public void enterCity(String city) {
		enterText(cityTextbox, city);
	}
	
	public void enterState(String state) {
		enterText(stateTextbox, state);
	}
	
	public void enterZip(String zip) {
		enterText(zipTextbox, zip);
	}
	
	public void enterCountry(String country) {
		enterText(countryTextbox, country);
	}
	
	public void selectLanguage(String language) {
		selectByVisibleText(languageDropdown, language);
	}
	
	public void selectFavouriteCategory(String favourite) {
		selectByVisibleText(favouriteCategoryDropdown, favourite);
	}
	
	public void clickMyList() {
		myListCheckbox.click();
	}

	public void clickMyBanner() {
		myBannerCheckbox.click();
	}
	
	public void clickSaveButton() {
		saveButton.click();
	}
	
	public String getMessage() {
		return getElementText(messageText);
	}
}
