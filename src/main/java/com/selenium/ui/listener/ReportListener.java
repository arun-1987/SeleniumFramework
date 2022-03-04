package com.selenium.ui.listener;

import com.selenium.connectors.JsonConnector;
import com.selenium.ui.browser.DriverFactoryManager;
import com.selenium.ui.helper.LoggerHelper;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.json.Json;
import org.openqa.selenium.remote.Augmenter;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.selenium.ui.helper.ResourceHelper;
import com.selenium.ui.reporter.ExtentManager;
import com.selenium.ui.reporter.ThreadSafeExtent;
import com.selenium.ui.testbase.TestBase;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

public class ReportListener extends TestListenerAdapter {

	public static String screenshotpath;
	private  static Logger logger = LoggerHelper.getLogger(ReportListener.class);

	private boolean createFile(File screenshot) {
		boolean fileCreated = false;

		if (screenshot.exists()) {
			fileCreated = true;
		} else {
			File parentDirectory = new File(screenshot.getParent());
			if (parentDirectory.exists() || parentDirectory.mkdirs()) {
				try {
					fileCreated = screenshot.createNewFile();
				} catch (IOException errorCreatingScreenshot) {
					errorCreatingScreenshot.printStackTrace();
				}
			}
		}

		return fileCreated;
	}

	private void writeScreenshotToFile(WebDriver driver, File screenshot) {
		try {
			FileOutputStream screenshotStream = new FileOutputStream(screenshot);
			screenshotStream.write(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
			screenshotStream.close();
		} catch (IOException unableToWriteScreenshot) {
			System.err.println("Unable to write " + screenshot.getAbsolutePath());
			unableToWriteScreenshot.printStackTrace();
		}
	}

	private String getScreenShotInBase64(WebDriver driver) {
		try {
			screenshotpath = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
			//System.out.println(screenshotpath);
		//	Screenshot s = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
		//	ImageIO.write(s.getImage(),"PNG",new File(screenshotAbsolutePath));
			//byte[] fileContent = FileUtils.readFileToByteArray(new File(screenshotAbsolutePath));
		//	screenshotpath = Base64.getEncoder().encodeToString(fileContent);
		//	System.out.println(screenshotpath);
		} catch (Exception e) {
			System.err.println("Unable to take screenshot " + screenshotpath);
		}
		return screenshotpath;
	}

	
	public static ExtentReports extentreports;
	public static ExtentTest test;

	public void onTestStart(ITestResult result) {
		logger.info("Test Initiated  "+result.getMethod().getMethodName());
		test = extentreports.createTest(result.getMethod().getMethodName());
		ThreadSafeExtent.setTest(test);
	}

	public void onTestSuccess(ITestResult result) {
		logger.info("Test Executed Successfully  "+result.getMethod().getMethodName());
		Reporter.getOutput(result).forEach(item -> ThreadSafeExtent.getTest().info(item));
		ThreadSafeExtent.getTest().log(Status.PASS, result.getName() + " Passed...");
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

	
	
	@Override
	public void onTestFailure(ITestResult failingTest) {
		logger.info("Test Failed  "+failingTest.getMethod().getMethodName());
		//if(TestBase.getDriver()!=null)
		try {
			WebDriver driver = TestBase.getDriver();
			String screenshotDirectory = JsonConnector.getConfig("screenShotPath");
			String screenshotAbsolutePath = screenshotDirectory + "/" + System.currentTimeMillis() + "_"
					+ failingTest.getName() + ".png";
			String screenShotPath = getScreenShotInBase64(driver);
			File screenshot = new File(screenshotAbsolutePath);
			if (createFile(screenshot)) {
				try {
					writeScreenshotToFile(driver, screenshot);
					Reporter.getOutput(failingTest).forEach(item -> ThreadSafeExtent.getTest().info(item));
					ThreadSafeExtent.getTest().log(Status.FAIL, failingTest.getThrowable().getMessage(),
							MediaEntityBuilder.createScreenCaptureFromBase64String(screenShotPath).build());
				} catch (ClassCastException weNeedToAugmentOurDriverObject) {
					writeScreenshotToFile(new Augmenter().augment(driver), screenshot);
				}
				System.out.println("Written screenshot to " + screenshotAbsolutePath);
			} else {
				System.err.println("Unable to create " + screenshotAbsolutePath);
			}
			
		} 
		catch(NullPointerException n) {
			System.err.println("Driver object is null may be due to API test execution");
			Reporter.getOutput(failingTest).forEach(item -> ThreadSafeExtent.getTest().info(item));
			ThreadSafeExtent.getTest().log(Status.FAIL, failingTest.getThrowable().getMessage());
		}
		catch (Exception ex) {
			System.err.println("Unable to capture screenshot...");
			ex.printStackTrace();
		}
		
	}
}