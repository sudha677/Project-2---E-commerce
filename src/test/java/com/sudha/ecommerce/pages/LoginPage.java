package com.sudha.ecommerce.pages;

import com.sudha.ecommerce.store.TestDataStore;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // --- Locators ---
    private By loginLink = By.id("login2");
    private By usernameInput = By.id("loginusername");
    private By passwordInput = By.id("loginpassword");
    private By closeBtn = By.xpath("//*[@id='logInModal']//button[text()='Close']");
    private By loginBtn = By.xpath("//*[@id='logInModal']//button[text()='Log in']");
    private By loggedInUser = By.id("nameofuser");
    private By logoutLink = By.id("logout2");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // --- Actions ---
    public void openLoginModal() {

        driver.findElement(loginLink).click();
    }

    public void fillLoginFormAndSubmit(String username, String password) {
        wait.until(ExpectedConditions.elementToBeClickable(usernameInput)).sendKeys(username);
        driver.findElement(passwordInput).sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(loginBtn)).click();
    }
    
    public String getPasswordFieldType(String username, String password) {
        wait.until(ExpectedConditions.elementToBeClickable(usernameInput)).sendKeys(username);
        driver.findElement(passwordInput).sendKeys(password);
        WebElement passwordField = driver.findElement(passwordInput);
		String fieldType = passwordField.getAttribute("type");
		return fieldType;

    }

    public void loginWithValidUser() {
        openLoginModal();
        if (TestDataStore.getUserName() == null || TestDataStore.getUserName().trim().isEmpty()) {
            TestDataStore.setUserName("efQrTojdR6c");
        }
        if (TestDataStore.getPassword() == null || TestDataStore.getPassword().trim().isEmpty()) {
            TestDataStore.setPassword("Test@12345");
        }
        fillLoginFormAndSubmit(TestDataStore.getUserName(), TestDataStore.getPassword());
    }

    // --- Getters for Validation ---
    public String getWelcomeText() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(loggedInUser));
        return driver.findElement(loggedInUser).getText();
    }

    public String getLogoutText() {
        return driver.findElement(logoutLink).getText();
    }

    public boolean isLoginButtonVisible() {
        return driver.findElement(loginBtn).isDisplayed();
    }

    public boolean isCloseButtonVisible() {
        return driver.findElement(closeBtn).isDisplayed();
    }

    public String getLoginButtonText() {
        return driver.findElement(loginBtn).getText();
    }

    public String getCloseButtonText() {
        return driver.findElement(closeBtn).getText();
    }
}