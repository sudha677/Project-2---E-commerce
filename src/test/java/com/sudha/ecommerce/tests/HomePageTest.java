package com.sudha.ecommerce.tests;

import com.sudha.ecommerce.base.BaseTest;
import com.sudha.ecommerce.dto.Product;
import com.sudha.ecommerce.pages.HomePage;
import com.sudha.ecommerce.pages.LoginPage;
import com.sudha.ecommerce.store.TestDataStore;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.Arrays;
import java.util.List;

public class HomePageTest  extends BaseTest{

    private HomePage homePage;


    @Test
    public void testNavBarAfterLogin() {
    	HomePage homePage = new HomePage(driver);
        homePage.openAndLogin();
        List<String> actual = homePage.getTopNavTexts();
        List<String> expected = Arrays.asList("Home", "Contact", "About us", "Cart", "Log out",
                "Welcome " + TestDataStore.getUserName());
        
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void testAddToCartAndValidateInCart() {
    	HomePage homePage = new HomePage(driver);
        homePage.openAndLogin();
        List<Product> products = homePage.addRandomProductsToCart(2);
        homePage.clickCartLink();

        for (Product product : products) {
            Assert.assertTrue(homePage.isProductInCart(product),
                    "Product not found in cart: " + product.getName());
        }
    }

    @Test
    public void testDeleteProductFromCart() {
    	HomePage homePage = new HomePage(driver);
        homePage.openAndLogin();
        List<Product> products = homePage.addRandomProductsToCart(1);
        homePage.clickCartLink();

        Product product = products.get(0);
        Assert.assertTrue(homePage.deleteProductFromCart(product.getName()),
                "Product was not deleted: " + product.getName());
    }

    @Test
    public void testContactModalUI() {
    	HomePage homePage = new HomePage(driver);
        homePage.openAndLogin();
        homePage.openContactModal();
        homePage.fillContactForm("test@example.com", "Test User", "Message sample");
    }


    @AfterClass
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}