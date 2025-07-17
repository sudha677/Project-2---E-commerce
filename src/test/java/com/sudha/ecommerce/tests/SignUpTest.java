package com.sudha.ecommerce.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.sudha.ecommerce.base.BaseTest;
import com.sudha.ecommerce.pages.SignUpPage;
import com.sudha.ecommerce.store.TestDataStore;
import com.sudha.ecommerce.util.UsernameGenerator;

public class SignUpTest extends BaseTest {

	private static final Logger logger = LoggerFactory.getLogger(SignUpTest.class);

	@BeforeClass
	public void beforeEachBrowser() {
		TestDataStore.setUserName(UsernameGenerator.generateUsername());
		TestDataStore.setPassword("Test@12345");
	}

	@Test(description = "Verify user sign-up with unique credentials", priority = 1, groups = "ui")
	public void validSignUp() {
		SignUpPage signUpPage = new SignUpPage(driver);
		signUpPage.openSignUpModel();
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		WebElement usernameField = wait.until(ExpectedConditions.elementToBeClickable(By.id("sign-username")));
		WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(By.id("sign-password")));

		usernameField.sendKeys(TestDataStore.getUserName());
		passwordField.sendKeys(TestDataStore.getPassword());
		
		WebElement closeButton = driver.findElement(By.xpath("//*[@id=\"signInModal\"]/div/div/div[3]/button[1]"));
		WebElement signUpButton = driver.findElement(By.xpath("//*[@id=\"signInModal\"]/div/div/div[3]/button[2]"));

		assertTrue(closeButton.isDisplayed());
		assertTrue(signUpButton.isDisplayed());
		assertEquals(closeButton.getText(), "Close");
		assertEquals(signUpButton.getText(), "Sign up");
		signUpButton.click();
		wait.ignoring(NoAlertPresentException.class).until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals(alert.getText(), "Sign up successful.");
		alert.accept();
		logger.info("User Name: " + TestDataStore.getUserName());

	}

	public WebElement waitForElementVisible(By locator, int timeoutSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	@Test(description = "Verify sign-up with existing username", priority = 2, groups = "ui")
	public void inValidSignUp() {
		SignUpPage signUpPage = new SignUpPage(driver);
		signUpPage.openSignUpModel();
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		WebElement usernameField = wait.until(ExpectedConditions.elementToBeClickable(By.id("sign-username")));
		WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(By.id("sign-password")));

		usernameField.sendKeys("sudha");
		passwordField.sendKeys(TestDataStore.getPassword());
		
		WebElement closeButton = driver.findElement(By.xpath("//*[@id=\"signInModal\"]/div/div/div[3]/button[1]"));
		WebElement signUpButton = driver.findElement(By.xpath("//*[@id=\"signInModal\"]/div/div/div[3]/button[2]"));

		assertTrue(closeButton.isDisplayed());
		assertTrue(signUpButton.isDisplayed());
		assertEquals(closeButton.getText(), "Close");
		assertEquals(signUpButton.getText(), "Sign up");
		signUpButton.click();
		wait.ignoring(NoAlertPresentException.class).until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals(alert.getText(), "This user already exist.");
		alert.accept();

	}

	@Test(description = "Verify sign-up with empty fields", priority = 3, groups = "ui")
	public void blankSignUp() {
		SignUpPage signUpPage = new SignUpPage(driver);
		signUpPage.openSignUpModel();
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		WebElement usernameField = wait.until(ExpectedConditions.elementToBeClickable(By.id("sign-username")));
		WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(By.id("sign-password")));

		usernameField.clear();
		passwordField.clear();
		
		WebElement closeButton = driver.findElement(By.xpath("//*[@id=\"signInModal\"]/div/div/div[3]/button[1]"));
		WebElement signUpButton = driver.findElement(By.xpath("//*[@id=\"signInModal\"]/div/div/div[3]/button[2]"));

		assertTrue(closeButton.isDisplayed());
		assertTrue(signUpButton.isDisplayed());
		assertEquals(closeButton.getText(), "Close");
		assertEquals(signUpButton.getText(), "Sign up");
		signUpButton.click();
		wait.ignoring(NoAlertPresentException.class).until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals(alert.getText(), "Please fill out Username and Password.");
		alert.accept();
	}
}
