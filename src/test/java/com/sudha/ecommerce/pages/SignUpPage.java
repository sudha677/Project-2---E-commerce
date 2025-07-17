package com.sudha.ecommerce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SignUpPage {

	private WebDriver driver;

	public SignUpPage(WebDriver driver) {
		this.driver = driver;
	}

	public void openSignUpModel() {
		driver.get("https://www.demoblaze.com/");
		driver.findElement(By.id("signin2")).click();
	}
}
