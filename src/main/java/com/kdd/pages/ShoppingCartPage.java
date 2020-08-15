package com.kdd.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import com.kdd.config.DriverManager;
import com.kdd.utility.ElementOperations;

public class ShoppingCartPage extends ElementOperations {
	
	private WebDriver driver;
	
	public ShoppingCartPage() {
		driver = DriverManager.getInstance().getDriver();
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, implicitWaitTime), this);
	}
	
	@FindBy(linkText = "Proceed to Checkout")
	private WebElement proceedToCheckoutButton;
	
	@FindBy(xpath = "//button[text()='Update Cart']")
	private WebElement updateCartButton;
	
	@FindBy(linkText = "Remove")
	private WebElement RemoveButton;
	
	@FindBy(linkText = "Remove All")
	private WebElement RemoveAllButton;
	
	@FindBy(xpath = "//input[@type='number']")
	private WebElement quantityTextbox;
	
	public void clickProceedToCheckoutButton() {
		proceedToCheckoutButton.click();
	}
	
	public void clickUpdateCartButton() {
		updateCartButton.click();
		pause(2);
	}
	
	public void clickRemoveButton() {
		RemoveButton.click();
	}
	
	public void clickRemoveAllButton() {
		RemoveAllButton.click();
	}
	
	public void changeQuantity(String quantity) {
		enterText(quantityTextbox, quantity);
	}
}
