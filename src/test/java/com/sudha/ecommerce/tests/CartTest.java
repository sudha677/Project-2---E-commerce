package com.sudha.ecommerce.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.sudha.ecommerce.base.BaseTest;
import com.sudha.ecommerce.dto.Product;
import com.sudha.ecommerce.pages.HomePage;

@Test(dependsOnMethods = { "com.sudha.ecommerce.tests.LoginPageTest.verifyLoginWithValidCredentials" })
public class CartTest extends BaseTest {
	List<Product> selectedProducts;
	int noOfProductsToSelect = 2;

	@Test(description = "Add product to cart", groups = "ui", priority = 1)
	public void addProductToCart() {
		HomePage homePage = new HomePage(driver);
		homePage.openAndLogin();
		
		selectedProducts = homePage.addRandomProductsToCart(1);
		homePage.clickAddToCart();
		assertTrue(selectedProducts.size() > 0, "Selected Products List should not be Empty!");
	}
	
	@Test(description = "View cart with added products", groups = "ui", priority = 2)
	public void viewCartWithAddedProducts() {

		HomePage homePage = new HomePage(driver);
		homePage.openAndLogin();
		homePage.clickCartLink();
		assertEquals(homePage.isProductInCart(selectedProducts.get(0)), true);
		
	}
	
	@Test(description = "Remove product from cart", groups = "ui", priority = 3)
	public void removeProductFromTheCart() {
		HomePage homePage = new HomePage(driver);
		homePage.openAndLogin();
		homePage.getTopNavTexts();
		homePage.clickCartLink();
		assertTrue(homePage.isProductInCart(selectedProducts.get(0)));
		homePage.deleteProductFromCart(selectedProducts.get(0).getName());
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until((WebDriver d) -> !homePage.isProductInCart(selectedProducts.get(0)));
		assertFalse(homePage.isProductInCart(selectedProducts.get(0)));
		
	}
	
	@Test(description = "Add Multiple products to cart", groups = "ui", priority = 4)
	public void addMultipleProductsToCart() {
		HomePage homePage = new HomePage(driver);
		homePage.openAndLogin();
		homePage.getTopNavTexts();
		
		selectedProducts = homePage.addRandomProductsToCart(noOfProductsToSelect);
		
		assertTrue(selectedProducts.size() > 0, "Selected Products List should not be Empty!");
		homePage.clickCartLink();
		for(Product product : selectedProducts) {
			assertTrue(homePage.isProductInCart(product), "Product Name: " + product.getName() + " must be in the cart!");
		}
		
	}
}
