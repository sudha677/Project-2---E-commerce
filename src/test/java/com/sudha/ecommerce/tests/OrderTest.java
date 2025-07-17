package com.sudha.ecommerce.tests;

import static org.testng.Assert.assertEquals;

import java.time.Duration;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.sudha.ecommerce.base.BaseTest;
import com.sudha.ecommerce.pages.HomePage;

@Test(dependsOnMethods = { "com.sudha.ecommerce.tests.CartTest.addMultipleProductsToCart" })
public class OrderTest extends BaseTest {

	private static final Logger logger = LoggerFactory.getLogger(OrderTest.class);
	@Test(description = "Place order with valid details", groups = "ui", priority = 1)
	public void placeOrderWithValidDetails() {
		HomePage homePage = new HomePage(driver);
		homePage.openAndLogin();
		homePage.getTopNavTexts();
		homePage.addRandomProductsToCart(2);
		homePage.clickAddToCart();
		homePage.clickCartLink();
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tbodyid")));

		List<WebElement> allProducts = driver.findElements(By.cssSelector("#tbodyid tr.success"));
		if(allProducts.size() < 1) {
			driver.navigate().back();
			homePage.addRandomProductsToCart(1);
			homePage.clickCartLink();
			
		}
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"page-wrapper\"]/div/div[2]/button")));
		WebElement placeOrderBtn = driver.findElement(By.xpath("//*[@id=\"page-wrapper\"]/div/div[2]/button"));
		assertEquals(placeOrderBtn.getText(), "Place Order");
		placeOrderBtn.click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("orderModal")));
		
		Calendar calendar = Calendar.getInstance();
		driver.findElement(By.id("name")).sendKeys("Sudha");
		driver.findElement(By.id("country")).sendKeys("UK");
		driver.findElement(By.id("city")).sendKeys("Cambridge");
		driver.findElement(By.id("card")).sendKeys("4111111111111111");
		driver.findElement(By.id("month")).sendKeys(String.valueOf(calendar.get(Calendar.MONTH) + 1));
		driver.findElement(By.id("year")).sendKeys(String.valueOf(calendar.get(Calendar.YEAR)));
		
		WebElement purchaseButton = driver.findElement(By.xpath("//button[text()='Purchase']"));
		wait.until(ExpectedConditions.elementToBeClickable(purchaseButton)).click();
		WebElement alertBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
		        By.cssSelector(".sweet-alert.showSweetAlert.visible")));

		String confirmationHeader = alertBox.findElement(By.tagName("h2")).getText();
		String confirmationDetails = alertBox.findElement(By.cssSelector("p.lead")).getText();

		logger.info("Confirmation Title: " + confirmationHeader);
		logger.info("Details:\n" + confirmationDetails);

		WebElement okButton = alertBox.findElement(By.cssSelector("button.confirm"));
		wait.until(ExpectedConditions.elementToBeClickable(okButton)).click();
	}
	
	@Test(description = "Place order with empty form", groups = "ui", priority = 2)
	public void placeOrderWithEmptyForm() {
		HomePage homePage = new HomePage(driver);
		homePage.openAndLogin();
		homePage.getTopNavTexts();
		homePage.clickCartLink();
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tbodyid")));

		List<WebElement> allProducts = driver.findElements(By.cssSelector("#tbodyid a.hrefch"));
		if(allProducts.size() < 1) {
			driver.navigate().back();
			homePage.addRandomProductsToCart(1);
			homePage.clickCartLink();
			
		}
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"page-wrapper\"]/div/div[2]/button")));
		WebElement placeOrderBtn = driver.findElement(By.xpath("//*[@id=\"page-wrapper\"]/div/div[2]/button"));
		assertEquals(placeOrderBtn.getText(), "Place Order");
		placeOrderBtn.click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("orderModal")));
		
		
		WebElement purchaseButton = driver.findElement(By.xpath("//button[text()='Purchase']"));
		wait.until(ExpectedConditions.elementToBeClickable(purchaseButton)).click();
		
		wait.ignoring(NoAlertPresentException.class).until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals(alert.getText(), "Please fill out Name and Creditcard.");
		alert.accept();
	}
}
