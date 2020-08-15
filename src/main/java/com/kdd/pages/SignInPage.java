package com.kdd.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import com.kdd.config.DriverManager;
import com.kdd.utility.ElementOperations;

public class SignInPage extends ElementOperations {
	
	private WebDriver driver;
	
	public SignInPage() {
		driver = DriverManager.getInstance().getDriver();
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, implicitWaitTime), this);
	}
	
	@FindBy(name = "username")
	private WebElement userNameTextbox;
	
	@FindBy(name = "password")
	private WebElement passwordTextbox;

	@FindBy(css = ".button-bar>.button")
	private WebElement loginButton;
	
	@FindBy(css = "#WelcomeContent")
	private WebElement welcomeText;
	
	@FindBy (linkText = "Register Now!")
	private WebElement registerLink;
	
	public void enterUserName(String userName) {
		enterText(userNameTextbox, userName);
	}
	
	public void enterPassword(String password) {
		enterText(passwordTextbox, password);
	}
	
	public void clickLoginButton() {
		loginButton.click();
	}
	
	public String getWelcomeText() {
		return getElementText(welcomeText);
	}
}
