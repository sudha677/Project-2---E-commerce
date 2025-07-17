package com.sudha.ecommerce.tests;

import java.time.Duration;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.sudha.ecommerce.base.BaseTest;
import com.sudha.ecommerce.pages.LoginPage;
import com.sudha.ecommerce.store.TestDataStore;
import com.sudha.ecommerce.util.UsernameGenerator;

@Test(dependsOnMethods = { "com.sudha.ecommerce.tests.SignUpTest.validSignUp" })
public class LoginPageTest extends BaseTest {

	    private LoginPage loginPage;
	    
	@Test(description = "Verify login with valid credentials", groups = "ui")
	public void verifyLoginWithValidCredentials() {

		loginPage.loginWithValidUser();
	}

	@Test(description = "Verify login with incorrect password", groups = "ui")
	public void verifyLoginWithInCorrectUserNameAndPassword() {
		loginPage.openLoginModal();
		loginPage.fillLoginFormAndSubmit("asdf", UsernameGenerator.generateUsername());
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.ignoring(NoAlertPresentException.class).until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals(alert.getText(), "Wrong password.");
		alert.accept();
	}

	@Test(description = "Verify login with empty fields", groups = "ui")
	public void verifyLoginWithEmptyFields() {
		loginPage.openLoginModal();
		loginPage.fillLoginFormAndSubmit(StringUtils.EMPTY, StringUtils.EMPTY);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.ignoring(NoAlertPresentException.class).until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals(alert.getText(), "Please fill out Username and Password.");
		alert.accept();
	}


    @DataProvider(name = "validUserCredentials")
    public Object[][] validUserCredentials() {
        return new Object[][]{
            {"testuser1", "password1"},
            {"demoUser", "demoPass"}
        };
    }

    @Test(dataProvider = "validUserCredentials", description = "Password Field validation", groups = "ui")
    public void verifyPasswordFieldIsMasked(String username, String password) {

        loginPage.openLoginModal();

        String fieldType = loginPage.getPasswordFieldType(username, password);

        Assert.assertEquals(fieldType, "password", "Password field is not masked properly");
    }

	


	    @BeforeMethod
	    public void setUp() {
	        loginPage = new LoginPage(driver);
	    }

	    @Test
	    public void testLoginFormButtonsAreVisible() {
	        loginPage.openLoginModal();
	        Assert.assertTrue(loginPage.isLoginButtonVisible(), "Login button should be visible");
	        Assert.assertTrue(loginPage.isCloseButtonVisible(), "Close button should be visible");
	        Assert.assertEquals(loginPage.getLoginButtonText(), "Log in");
	        Assert.assertEquals(loginPage.getCloseButtonText(), "Close");
	    }

	    @Test
	    public void testSuccessfulLogin() {

	        loginPage.loginWithValidUser();
	        String welcomeText = loginPage.getWelcomeText();
	        String expectedText = "Welcome " + TestDataStore.getUserName();
	        Assert.assertEquals(welcomeText, expectedText, "Incorrect welcome message");

	        String logoutText = loginPage.getLogoutText();
	        Assert.assertEquals(logoutText, "Log out", "Logout text not visible");
	    }

	    @AfterClass
	    public void tearDown() {
	        if (driver != null) driver.quit();
	    }
	}

