package com.sudha.ecommerce.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.sudha.ecommerce.base.BaseTest;

public class ExtentListener implements ITestListener {

	public static ExtentReports extent;
	public static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
	public static WebDriver driver; // Assign from test

	@Override
	public void onStart(ITestContext context) {
		ExtentSparkReporter reporter = new ExtentSparkReporter("test-output/ExtentReport.html");
		extent = new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Project", "Automation Framework");
	}

	@Override
	public void onTestStart(ITestResult result) {
		String browser = BaseTest.getCurrentBrowser();

		ExtentTest test = extent.createTest(result.getMethod().getMethodName()).assignCategory(browser.toUpperCase());
		extentTest.set(test);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		if (driver != null && isDriverSessionActive(driver)) {
			String path = ScreenshotUtil.takeScreenshot(driver, result.getMethod().getMethodName());
			if (path != null) {
				extentTest.get().pass("Test Passed").addScreenCaptureFromPath(path, "Passed Screenshot");
			} else {
				extentTest.get().pass("Test Passed (No screenshot captured)");
			}
		}
	}

	@Override
	public void onTestFailure(ITestResult result) {

		String browser = BaseTest.getCurrentBrowser();
		extentTest.get().fail("Test failed on browser: " + browser + " " + result.getThrowable());

		if (driver != null && isDriverSessionActive(driver)) {
			String path = ScreenshotUtil.takeScreenshot(driver, result.getMethod().getMethodName());
			extentTest.get().addScreenCaptureFromPath(path, "Failure Screenshot");
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		extentTest.get().skip(result.getThrowable());
	}

	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
	}

	public boolean isDriverSessionActive(WebDriver driver) {
		try {
			return ((RemoteWebDriver) driver).getSessionId() != null;
		} catch (Exception e) {
			return false;
		}
	}
}
