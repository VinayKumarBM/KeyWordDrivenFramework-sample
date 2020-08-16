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
import com.kdd.pages.ShippingAddressPage;
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
	private ShippingAddressPage shippingAddressPage = new ShippingAddressPage();
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
	
	public void  navigateToMyAccountScreen(String data) {		
		homePage.clickMyAccountLink();
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
	
	public void enterUserInformation(String data) {
		String[] dataArray = data.split("\\|");
		String userName = dataArray[0]+System.nanoTime();
		SessionDataManager.getInstance().setSessionData("userName", userName);
		SessionDataManager.getInstance().setSessionData("password", dataArray[1]);
		loginPage.enterUserName(userName);
		loginPage.enterPassword(dataArray[1]);	
		registerationPage.enterConfirmPassowrd(dataArray[2]);
	}
	
	public void enterAccountInformation(String data) {
		String[] dataArray = data.split("\\|");		
		registerationPage.enterFirstName(dataArray[0]);
		registerationPage.enterLastName(dataArray[1]);
		registerationPage.enterEmail(dataArray[2]);
		registerationPage.enterTelephone(dataArray[3]);
		registerationPage.enterAddress1(dataArray[4]);
		registerationPage.enterAddress2(dataArray[5]);
		registerationPage.enterCity(dataArray[6]);
		registerationPage.enterState(dataArray[7]);
		registerationPage.enterZip(dataArray[8]);
		registerationPage.enterCountry(dataArray[9]);
		registerationPage.selectLanguage(dataArray[10]);
		registerationPage.selectFavouriteCategory(dataArray[11]);
		if(dataArray[12].equalsIgnoreCase("yes")) {
			registerationPage.clickMyList();
		}
		if(dataArray[13].equalsIgnoreCase("yes")) {
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
		SessionDataManager.getInstance().setSessionData("petDescription", productsPage.getPetDescritpion());
	}
	
	public void selectPetByID(String data) {
		productsPage.selectFirstPetID();
		assertEquals(SessionDataManager.getInstance().getSessionData("petDescription"), productsPage.getPetPDPDescritpion(),
				"Pet Description did not match on PDP");
	}
	
	public void addToCart(String data) {
		productsPage.clickAddToCartButton();
		assertEquals(SessionDataManager.getInstance().getSessionData("petDescription"), productsPage.getPetCartDescritpion(),
				"Pet Description did not match on Cart");
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
		assertTrue(orderPage.isOrderNoDisplayed(), "Order No did not displayed");
		assertTrue(orderPage.isOrderDateDisplayed(), "Order Date was not displayed");
		orderPage.getOrderNo();
		assertEquals(orderPage.getDescription(), SessionDataManager.getInstance().getSessionData("petDescription"),
				"Pet description did not match on Order Details");
		assertEquals(orderPage.getQuality(), data, "Quantity did not match");		
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
	
	public void selectPetCategory(String data) throws InvalidLocatorException {
		homePage.selectPetByCategory(data);
		assertTrue(homePage.isPageHeadingDisplayed(), data+" category was not displayed");
	}
	
	public void updatePaymentDetails(String data) {
		String[] dataArray = data.split("\\|");
		billingDetailsPage.selectCardType(dataArray[0]);
		billingDetailsPage.updateCardNumber(dataArray[1]);
		billingDetailsPage.updateExpiryDate(dataArray[2]);
	}
	
	public void updateBillingAddress(String data) {
		String[] dataArray = data.split("\\|");
		billingDetailsPage.updateFirstName(dataArray[0]);
		billingDetailsPage.updateLastName(dataArray[1]);
		billingDetailsPage.updateAddress1(dataArray[2]);
		billingDetailsPage.updateAddress2(dataArray[3]);
		billingDetailsPage.updateCity(dataArray[4]);
		billingDetailsPage.updateState(dataArray[5]);
		billingDetailsPage.updateZip(dataArray[6]);
		billingDetailsPage.updateCountry(dataArray[7]);
	}
	
	public void changeShippingAddress(String data) {
		billingDetailsPage.shipToDifferentAddress();
	}
	
	public void updateShippingAddress(String data) {
		String[] dataArray = data.split("\\|");
		shippingAddressPage.updateFirstName(dataArray[0]);
		shippingAddressPage.updateLastName(dataArray[1]);
		shippingAddressPage.updateAddress1(dataArray[2]);
		shippingAddressPage.updateAddress2(dataArray[3]);
		shippingAddressPage.updateCity(dataArray[4]);
		shippingAddressPage.updateState(dataArray[5]);
		shippingAddressPage.updateZip(dataArray[6]);
		shippingAddressPage.updateCountry(dataArray[7]);
	}
}
