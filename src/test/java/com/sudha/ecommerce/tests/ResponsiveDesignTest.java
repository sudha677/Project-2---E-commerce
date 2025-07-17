package com.sudha.ecommerce.tests;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.sudha.ecommerce.base.BaseTest;
import com.sudha.ecommerce.pages.HomePage;

@Test(dependsOnMethods = { "com.sudha.ecommerce.tests.LoginPageTest.verifyLoginWithValidCredentials" })
public class ResponsiveDesignTest extends BaseTest {
	
	HomePageTest homePageTest;

	private static final Logger logger = LoggerFactory.getLogger(ResponsiveDesignTest.class);

	@Test(description = "Test horizontal scrolling - iPhone8", groups = "ui")
	public void testHorizontalScrollingOnIphone8() {
		HomePage homePage = new HomePage(driver);
		driver.manage().window().setSize(new Dimension(375, 667));
		validateHorizontalScrolling(homePage);
	}
	@Test(description = "Test horizontal scrolling - iPhone15 Pro", groups = "ui")
	public void testHorizontalScrollingOnIphone15Pro() {
		HomePage homePage = new HomePage(driver);
		driver.manage().window().setSize(new Dimension(430, 932));
		validateHorizontalScrolling(homePage);
	}
	

	private void validateHorizontalScrolling(HomePage homePage) {

		homePage.openAndLogin();
		homePage.getTopNavTexts();
		homePageTest.testNavBarAfterLogin();

		JavascriptExecutor js = (JavascriptExecutor) driver;
		Long scrollWidth = (Long) js.executeScript("return document.body.scrollWidth;");
		Long innerWidth = (Long) js.executeScript("return window.innerWidth;");

		logger.info("Scroll Width: " + scrollWidth);
		logger.info("Inner Width: " + innerWidth);

		Assert.assertTrue(scrollWidth <= innerWidth, "Horizontal scrolling detected on mobile viewport!");
	}
}
