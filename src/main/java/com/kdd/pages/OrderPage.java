package com.kdd.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import com.kdd.config.DriverManager;
import com.kdd.utility.ElementOperations;

public class OrderPage extends ElementOperations {

	private WebDriver driver;

	public OrderPage() {
		driver = DriverManager.getInstance().getDriver();
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, implicitWaitTime), this);
	}

	@FindBy(xpath = "//button[text()='Confirm']")
	private WebElement confirmButton;

	@FindBy(xpath = "//td[text()='Order No.']/following-sibling::td")
	private WebElement orderNoText;

	@FindBy(xpath = "//td[text()='Order Date']/following-sibling::td")
	private WebElement orderDateText;

	@FindBy(xpath = "//th[text()='Item ID']/../following-sibling::tr/td[1]/a")
	private WebElement itemIDLink;

	@FindBy(xpath = "//th[text()='Description']/../following-sibling::tr/td[2]")
	private WebElement descriptionText;

	@FindBy(xpath = "//th[text()='Quantity']/../following-sibling::tr/td[3]")
	private WebElement quantityText;

	@FindBy(xpath = "//th[text()='Price']/../following-sibling::tr/td[4]")
	private WebElement priceText;

	@FindBy(xpath = "//th[text()='Total Cost']/../following-sibling::tr/td[5]")
	private WebElement totalCostText;

	@FindBy(xpath = "//th[text()='Total']/following-sibling::th")
	private WebElement totalText;

	@FindBy(xpath = "//button[text()='Delete Order']")
	private WebElement deletOrderButton;

	@FindBy(css = "#Catalog a[href*='orderId']")
	private WebElement orderIdLink;
	
	public void clickConfirmButton() {
		confirmButton.click();
	}
	
	public void clickDeleteOrderButton() {
		deletOrderButton.click();
	}
	
	public boolean isOrderNoDisplayed() {
		return isElementDisplayed(orderNoText);
	}
	
	public String getOrderNo() {
		return getElementText(orderNoText);
	}
	
	public boolean isOrderDateDisplayed() {
		return isElementDisplayed(orderDateText);
	}
	
	public String getDescription() {
		return getElementText(descriptionText);
	}
	
	public String getQuality() {
		return getElementText(quantityText);
	}
	
	public void selectFirstOrder() {
		orderIdLink.click();
	}
	
	public String getFirstOrderID() {
		return getElementText(orderIdLink);
	}
}
