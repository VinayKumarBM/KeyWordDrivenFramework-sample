package com.kdd.pages;

import java.util.List;

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
	private WebElement productIDLink;
	
	@FindBy (xpath = "//th[text()='Description']/../../tr/td[3]")
	private WebElement petDescriptionText;
	
	@FindBy (css = "strong>small")
	private WebElement petPDPDescriptionText;
	
	@FindBy (xpath = "//th[text()='Item ID']/../../tr/td[1]//a")
	private WebElement itemIDLink;
	
	@FindBy (xpath = "//input[@type='number']/../preceding-sibling::td[2]")
	private List<WebElement> cartPetDescriptionText;
	
	@FindBy (linkText = "Add to Cart")
	private WebElement addToCartButton;
	
	private final String PET_BY_ID = "xpath|//a[text()='%s']";
	
	public void clickAddToCartButton() {
		addToCartButton.click();
	}
	
	public void selectFirstPetFromSearchResult() {
		productIDLink.click();
	}
	
	public void selectFirstPetID() {
		itemIDLink.click();
	}
	
	public void selectPetByID(String petId) throws InvalidLocatorException {
		getElementByReplacingText(driver, PET_BY_ID, petId).click();
	}
	
	public String getPetDescritpion() {
		return getElementText(petDescriptionText);
	}
	
	public String getPetPDPDescritpion() {
		return getElementText(petPDPDescriptionText);
	}
	
	public String getPetCartDescritpion() {
		return getElementText(cartPetDescriptionText.get(cartPetDescriptionText.size()-1));
	} 
}
