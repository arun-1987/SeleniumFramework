package com.selenium.ui.reporter;

import java.util.List;
import java.util.stream.Stream;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class ExtentListener implements ITestListener {

	public static ExtentReports extentreports;
	public static ExtentTest test;

	public void onTestStart(ITestResult result) {
		test = extentreports.createTest(result.getMethod().getMethodName());
		ThreadSafeExtent.setTest(test);
	}

	
	public void onTestSuccess(ITestResult result) {	
		Reporter.getOutput(result).forEach(item -> ThreadSafeExtent.getTest().info(item));
		ThreadSafeExtent.getTest().log(Status.PASS, result.getName() + " Passed...");
	}

	
	public void onTestFailure(ITestResult result) {
		Reporter.getOutput(result).forEach(item -> ThreadSafeExtent.getTest().info(item));
		ThreadSafeExtent.getTest().log(Status.FAIL, result.getName() + " Test Failed...");
	}

	public void onTestSkipped(ITestResult result) {
		// extenttest.log(Status.SKIP, result.getThrowable()+" Skipped...");
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// not implemented
	}

	public void onTestFailedWithTimeout(ITestResult result) {
		onTestFailure(result);
	}

	public void onStart(ITestContext context) {
		extentreports = ExtentManager.getInstance();
	}

	public void onFinish(ITestContext context) {
		extentreports.flush();
	}

}
