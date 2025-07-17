package com.sudha.ecommerce.tests;

import com.sudha.ecommerce.base.BaseTest;
import com.sudha.ecommerce.pages.ProductBrowsingPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class ProductBrowsingTest extends BaseTest {
    private ProductBrowsingPage productBrowsingPage;

    @BeforeMethod
    public void initPage() {
        productBrowsingPage = new ProductBrowsingPage(driver);
    }

    @Test (description = "Test Browse product categories Phones")
    public void testBrowseCategoryPhones() {
        productBrowsingPage.clickCategory("Phones");
        List<String> products = productBrowsingPage.getDisplayedProductNames();
        Assert.assertTrue(products.size() > 0, "No products found in Phones category");
    }

    @Test (description = "Test Browse product categories Laptops")
    public void testBrowseCategoryLaptops() {
        productBrowsingPage.clickCategory("Laptops");
        List<String> products = productBrowsingPage.getDisplayedProductNames();
        Assert.assertTrue(products.size() > 0, "No products found in Laptops category");
    }

    @Test (description = "Test Browse product categories Monitors")
    public void testBrowseCategoryMonitors() {
        productBrowsingPage.clickCategory("Monitors");
        List<String> products = productBrowsingPage.getDisplayedProductNames();
        Assert.assertTrue(products.size() > 0, "No products found in Monitors category");
    }

    @Test
    public void testViewProductDetails() {
        productBrowsingPage.clickCategory("Phones");
        productBrowsingPage.clickOnFirstProduct();
        String title = productBrowsingPage.getProductDetailTitle();
        String price = productBrowsingPage.getProductDetailPrice();
        Assert.assertFalse(title.isEmpty(), "Product title should not be empty.");
        Assert.assertFalse(price.isEmpty(), "Product price should not be empty.");
    }

    @Test (description = "Verify Home Navigation")
    public void testVerifyHomeNavigation() {
        productBrowsingPage.clickCategory("Laptops");
        productBrowsingPage.clickHomeLink();
        List<String> products = productBrowsingPage.getDisplayedProductNames();
        Assert.assertTrue(products.size() > 0, "Home page should show all products.");
    }
 
    @Test (description = "Test Navigate using Navbar links")
    public void testNavigateUsingNavbarLinks() {
        // Home
        productBrowsingPage.clickHomeLink();
        List<String> homeProducts = productBrowsingPage.getDisplayedProductNames();
        Assert.assertTrue(homeProducts.size() > 0, "Home page should show products");

        // Cart
        productBrowsingPage.clickCartLink();
        Assert.assertTrue(driver.getCurrentUrl().contains("cart"), "Should navigate to cart page");

        driver.navigate().back();

        // Contact
        productBrowsingPage.clickContactLink();
        Assert.assertTrue(productBrowsingPage.isContactModalVisible(), "Contact modal should be visible");
    }
}