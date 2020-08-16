package com.kdd.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import com.kdd.config.DriverManager;
import com.kdd.exceptions.InvalidLocatorException;
import com.kdd.utility.ElementOperations;

public class HomePage extends ElementOperations {
	
	private WebDriver driver;
	
	public HomePage() {
		driver = DriverManager.getInstance().getDriver();
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, implicitWaitTime), this);
	}
	
	@FindBy(linkText = "My Account")
	private WebElement myAccountLink;
	
	@FindBy(linkText = "Sign Up")
	private WebElement signupLink;
	
	@FindBy(linkText = "Sign In")
	private WebElement signinLink;
	
	@FindBy(linkText = "Sign Out")
	private WebElement logoutLink;
	
	@FindBy(xpath = "//input[@placeholder='Product Search']")
	private WebElement searchTextbox;
	
	@FindBy(xpath = "//button[text()='Search']")
	private WebElement searchButton;
	
	@FindBy(css = "#Catalog>h3")
	private WebElement headingText;
	
	@FindBy(linkText = "My Orders")
	private WebElement myOrdersLink;
	
	@FindBy(name = "img_cart")
	private WebElement cartLink;
	
	private final String CATEGORIES_LINK = "xpath|//div[@id='QuickLinks']/a[text()='%s']";

	public void clickMyAccountLink() {
		myAccountLink.click();
	}
	
	public void clickSignupLink() {
		signupLink.click();
	}
	
	public void clickSigninLink() {
		signinLink.click();
	}
	
	public boolean isSignInLinkDisplayed() {
		return isElementDisplayed(signinLink);
	}
	
	public void clickMyOrdersLink() {
		myOrdersLink.click();
	}
	
	public void clickCartLink() {
		cartLink.click();
	}
	
	public void clickLogoutLink() {
		logoutLink.click();
	}
	
	public boolean isSignOutLinkDisplayed() {
		return isElementDisplayed(logoutLink);
	}
	
	public void searchPet(String petName) {
		enterText(searchTextbox, petName);
	}
	
	public void clickSearchButton() {
		searchButton.click();
	}
	
	public boolean isPageHeadingDisplayed() {
		return isElementDisplayed(headingText);
	}
	
	public void selectPetByCategory(String category) throws InvalidLocatorException {
		getElementByReplacingText(driver, CATEGORIES_LINK, category).click();
	}
}
