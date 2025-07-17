package com.sudha.ecommerce.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;

;

public class ScreenshotUtil {

	public static String takeScreenshot(WebDriver driver, String testName) {
		String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String relativePath = "screenshots/" + testName + "_" + timestamp + ".png";
		String fullPath = "test-output/" + relativePath;

		try {
			if (isAlertPresent(driver)) {
				System.out.println("Alert is present. Skipping screenshot.");
				return null;
			}

			File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

			FileUtils.copyFile(src, new File(fullPath));

		} catch (UnhandledAlertException e) {
			System.out.println("Unhandled alert present, cannot take screenshot: " + e.getMessage());
			return null;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return relativePath;
	}

	private static boolean isAlertPresent(WebDriver driver) {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException ex) {
			return false;
		}
	}
}