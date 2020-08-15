package com.kdd.actions;

import static org.testng.Assert.*;

import org.apache.log4j.Logger;

import com.kdd.config.GlobalVariables;
import com.kdd.config.SessionDataManager;
import com.kdd.exceptions.InvalidLocatorException;
import com.kdd.pages.BillingDetailsPage;
import com.kdd.pages.HomePage;
import com.kdd.pages.OrderPage;
import com.kdd.pages.ProductsPage;
import com.kdd.pages.RegisterationPage;
import com.kdd.pages.ShoppingCartPage;
import com.kdd.pages.SignInPage;

public class ActionsClass implements GlobalVariables{
	private static final Logger Log = Logger.getLogger(ActionsClass.class.getName());
	private HomePage homePage = new HomePage(); 
	private SignInPage loginPage = new SignInPage();
	private RegisterationPage registerationPage = new RegisterationPage(); 
	private ProductsPage productsPage = new ProductsPage();
	private ShoppingCartPage cartPage = new ShoppingCartPage();
	private BillingDetailsPage billingDetailsPage = new BillingDetailsPage();
	private OrderPage orderPage = new OrderPage();
		
	public void navigateToRegistrationScreen(String data) throws InvalidLocatorException {		
		homePage.clickSignupLink();
	}
	
	public void navigateToLoginScreen(String data) throws InvalidLocatorException {	
		homePage.clickSigninLink();
	}
	
	public void  navigateToShoppingCart(String data) throws InvalidLocatorException {		
		homePage.clickCartLink();
	}
	
	public void login(String data) throws InvalidLocatorException {
		String[] dataArray = data.split("\\|");
		loginToStore(dataArray[0], dataArray[1]);
	}

	public void loginWithNewUser(String data) throws InvalidLocatorException {
		loginToStore((String) SessionDataManager.getInstance().getSessionData("userName"), 
				(String) SessionDataManager.getInstance().getSessionData("password"));
	}
	
	private void loginToStore(String userName, String password) {
		loginPage.enterUserName(userName);
		loginPage.enterPassword(password);
		loginPage.clickLoginButton();
		assertTrue(homePage.isSignOutLinkDisplayed(), "Login unsuccessful");
	}
	
	public void enterRegisterationDetails(String data) throws InvalidLocatorException {
		String[] dataArray = data.split("\\|");
		String userName = dataArray[0]+System.nanoTime();
		SessionDataManager.getInstance().setSessionData("userName", userName);
		SessionDataManager.getInstance().setSessionData("password", dataArray[1]);
		loginPage.enterUserName(userName);
		loginPage.enterPassword(dataArray[1]);
		registerationPage.enterConfirmPassowrd(dataArray[2]);
		registerationPage.enterFirstName(dataArray[3]);
		registerationPage.enterLastName(dataArray[4]);
		registerationPage.enterEmail(dataArray[5]);
		registerationPage.enterTelephone(dataArray[6]);
		registerationPage.enterAddress1(dataArray[7]);
		registerationPage.enterAddress2(dataArray[8]);
		registerationPage.enterCity(dataArray[9]);
		registerationPage.enterState(dataArray[10]);
		registerationPage.enterZip(dataArray[11]);
		registerationPage.enterCountry(dataArray[12]);
		registerationPage.selectLanguage(dataArray[13]);
		registerationPage.selectFavouriteCategory(dataArray[14]);
		if(dataArray[15].equalsIgnoreCase("yes")) {
			registerationPage.clickMyList();
		}
		if(dataArray[16].equalsIgnoreCase("yes")) {
			registerationPage.clickMyBanner();
		}
	}
	
	public void clickSaveButton(String data) throws InvalidLocatorException {
		registerationPage.clickSaveButton();
	}
	
	public void validateSuccessMessage(String data) throws InvalidLocatorException {	
		assertEquals(registerationPage.getMessage(), data);
	}

	public void validateMessageContainsText(String data) throws InvalidLocatorException {	
		Log.info("Expected Message: "+data);
		assertTrue(registerationPage.getMessage().contains(data));
	}
	
	public void logout(String data) throws InvalidLocatorException {		
		homePage.clickLogoutLink();
		assertTrue(homePage.isSignInLinkDisplayed(), "Logout unsuccessful");
	}
	
	public void searchForPet(String data) throws InvalidLocatorException {
		homePage.searchPet(data);
		homePage.clickSearchButton();
		assertTrue(homePage.isSearchResultDisplayed(), "Search Results were not displayed");
	}

	public void selectAPet(String data) throws InvalidLocatorException {
		productsPage.selectFirstPetFromSearchResult();
	}
	
	public void addToCart(String data) throws InvalidLocatorException {
		productsPage.clickAddToCartButton();
	}
	
	public void updateCart(String data) throws InvalidLocatorException {
		cartPage.changeQuantity(data);
		cartPage.clickUpdateCartButton();
	}
	
	public void proceedToCheckout(String data) throws InvalidLocatorException {
		cartPage.clickProceedToCheckoutButton();
	}
	
	public void continueCheckout(String data) throws InvalidLocatorException {
		billingDetailsPage.clickContinueButton();
	}
	
	public void confirmOrder(String data) throws InvalidLocatorException {
		orderPage.clickConfirmButton();
	}
	
	public void validateOrderDetails(String data) throws InvalidLocatorException {
		String[] dataArray = data.split("\\|");		
		assertTrue(orderPage.isOrderNoDisplayed(), "Order No did not displayed");
		assertTrue(orderPage.isOrderDateDisplayed(), "Order Date was not displayed");
		orderPage.getOrderNo();
		Log.info("Expected Message: "+dataArray[0]);
		assertTrue(orderPage.getDescription().contains(dataArray[0]));
		assertEquals(orderPage.getQuality(), dataArray[1], "Quantity did not match");		
	}
}
