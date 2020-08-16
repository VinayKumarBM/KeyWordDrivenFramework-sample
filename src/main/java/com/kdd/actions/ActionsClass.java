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
		
	public void navigateToRegistrationScreen(String data) {		
		homePage.clickSignupLink();
	}
	
	public void navigateToLoginScreen(String data) {	
		homePage.clickSigninLink();
	}
	
	public void  navigateToShoppingCart(String data) {		
		homePage.clickCartLink();
	}
	
	public void  navigateToMyOrdersScreen(String data) {		
		homePage.clickMyOrdersLink();
	}
	
	public void login(String data) {
		String[] dataArray = data.split("\\|");
		loginToStore(dataArray[0], dataArray[1]);
	}

	public void loginWithNewUser(String data) {
		loginToStore((String) SessionDataManager.getInstance().getSessionData("userName"), 
				(String) SessionDataManager.getInstance().getSessionData("password"));
	}
	
	private void loginToStore(String userName, String password) {
		loginPage.enterUserName(userName);
		loginPage.enterPassword(password);
		loginPage.clickLoginButton();
		assertTrue(homePage.isSignOutLinkDisplayed(), "Login unsuccessful");
	}
	
	public void enterRegisterationDetails(String data) {
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
	
	public void clickSaveButton(String data) {
		registerationPage.clickSaveButton();
	}
	
	public void validateSuccessMessage(String data) {	
		assertEquals(registerationPage.getMessage(), data);
	}

	public void validateMessageContainsText(String data) {	
		Log.info("Expected Message: "+data);
		assertTrue(registerationPage.getMessage().contains(data));
	}
	
	public void logout(String data) {		
		homePage.clickLogoutLink();
		assertTrue(homePage.isSignInLinkDisplayed(), "Logout unsuccessful");
	}
	
	public void searchForPet(String data) {
		homePage.searchPet(data);
		homePage.clickSearchButton();
		assertTrue(homePage.isPageHeadingDisplayed(), "Search Results were not displayed");
	}

	public void selectAPet(String data) {
		productsPage.selectFirstPetFromSearchResult();
	}
	
	public void addToCart(String data) {
		productsPage.clickAddToCartButton();
	}
	
	public void updateCart(String data) {
		cartPage.changeQuantity(data);
		cartPage.clickUpdateCartButton();
	}
	
	public void proceedToCheckout(String data) {
		cartPage.clickProceedToCheckoutButton();
	}
	
	public void continueCheckout(String data) {
		billingDetailsPage.clickContinueButton();
	}
	
	public void confirmOrder(String data) {
		orderPage.clickConfirmButton();
	}
	
	public void validateOrderDetails(String data) {
		String[] dataArray = data.split("\\|");		
		assertTrue(orderPage.isOrderNoDisplayed(), "Order No did not displayed");
		assertTrue(orderPage.isOrderDateDisplayed(), "Order Date was not displayed");
		orderPage.getOrderNo();
		Log.info("Expected Message: "+dataArray[0]);
		assertTrue(orderPage.getDescription().contains(dataArray[0]));
		assertEquals(orderPage.getQuality(), dataArray[1], "Quantity did not match");		
	}
	
	public void selectAnOrder(String data) {
		SessionDataManager.getInstance().setSessionData("orderId", orderPage.getFirstOrderID());
		orderPage.selectFirstOrder();
	}
	
	public void deleteOrder(String data) {
		orderPage.clickDeleteOrderButton();
		assertTrue(homePage.isPageHeadingDisplayed(), "My Order page was not displayed");
	}
	
	public void validateOrderIsDeleted(String data) {
		String orderID = (String) SessionDataManager.getInstance().getSessionData("orderId");
		assertFalse(orderID.equalsIgnoreCase(orderPage.getFirstOrderID()), "Order was not deleted");
	}
	
	public void removePetFromCart(String data) throws InvalidLocatorException {
		int beforeRemove = cartPage.getPetsInCart();
		cartPage.clickRemoveButton();
		assertEquals(cartPage.getPetsInCart(), beforeRemove-1, "Pet was not removed");
	}
	
	public void removeAllPetFromCart(String data) {
		cartPage.clickRemoveAllButton();
		assertEquals(cartPage.getEmptyCartMessage(), data, "Cart was not empty");
	}
}
