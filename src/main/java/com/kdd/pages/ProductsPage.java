package com.kdd.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import com.kdd.config.DriverManager;
import com.kdd.exceptions.InvalidLocatorException;
import com.kdd.utility.ElementOperations;

public class ProductsPage extends ElementOperations {
	
	private WebDriver driver;
	
	public ProductsPage() {
		driver = DriverManager.getInstance().getDriver();
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, implicitWaitTime), this);
	}
	
	@FindBy (xpath = "//th[text()='Product ID']/../../tr/td[1]//a")
	private WebElement petIDLink;
			
	@FindBy (linkText = "Add to Cart")
	private WebElement addToCartButton;
	
	private final String PET_BY_ID = "xpath|//a[text()='%s']";
	
	public void clickAddToCartButton() {
		addToCartButton.click();
	}
	
	public void selectFirstPetFromSearchResult() {
		petIDLink.click();
	}
	
	public void selectPetByID(String petId) throws InvalidLocatorException {
		getElementByReplacingText(driver, PET_BY_ID, petId).click();
	}
}
