package com.selenium.ui.helper;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.awaitility.Awaitility.*;
import static java.util.concurrent.TimeUnit.*;
import static org.hamcrest.Matchers.*;

import com.selenium.connectors.JsonConnector;
import com.selenium.ui.testbase.TestBase;
import org.testng.Reporter;

public class WaitHelper {

	private Logger logger = LoggerHelper.getLogger(this.getClass());


	/**
	 * Global function to set implicit wait
	 * 
	 * @param timeout
	 * @param unit
	 */

	public void setImplicitWait(WebDriver driver,long timeout, TimeUnit unit) {
		logger.info("setImplicitWait has been set to " + timeout);
		driver.manage().timeouts().implicitlyWait(timeout, unit);
	}

	/**
	 * Function to get the webdriverwait object
	 * 
	 * @param timeoutinseconds
	 * @param pollingmillisecs
	 * @return
	 */
	public WebDriverWait getWait(WebDriver driver,int timeoutinseconds, int pollingmillisecs) {
		WebDriverWait wait = new WebDriverWait(driver, timeoutinseconds);
		wait.pollingEvery(Duration.ofMillis(pollingmillisecs));
		wait.ignoring(NoSuchElementException.class);
		wait.ignoring(StaleElementReferenceException.class);
		wait.ignoring(ElementNotVisibleException.class);
		wait.ignoring(NoSuchFrameException.class);
		return wait;
	}

	/**
	 * Function to wait for element to be visible
	 *
	 * @param timeout
	 * @param polling
	 */
	public WebElement waitForElementVisible(WebDriver driver,By locator, int timeout, int polling) {
		logger.info("Waiting for " + locator.toString() + " for the specified:" + timeout + " seconds");
		WebDriverWait wait = getWait(driver,timeout, polling);
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	/**
	 * Function to wait for element to be not visible
	 *
	 * @param timeout
	 * @param polling
	 */
	public Boolean waitForElementNotVisible(WebDriver driver,By locator, int timeout, int polling) {
		logger.info("Waiting for " + locator.toString() + " for the specified:" + timeout + " seconds");
		WebDriverWait wait = getWait(driver,timeout, polling);
		return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}

	/**
	 * Function to wait for element to be clickable
	 *
	 * @param timeout
	 * @param polling
	 */
	public WebElement waitForElementClickable(WebDriver driver,By locator, int timeout, int polling) {
		logger.info("Waiting for Element " + locator.toString() + " for the specified:" + timeout + " seconds");
		WebDriverWait wait = getWait(driver,timeout, polling);
		return wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	/**
	 * Function to wait for element to be clickable
	 *
	 * @param timeout
	 * @param polling
	 * @return
	 */
	public boolean waitForIsDisplayed(WebDriver driver,By locator, int timeout, int polling) {
		logger.info("Waiting for Element" + locator.toString() + " for the specified:" + timeout + " seconds");
		WebDriverWait wait = getWait(driver,timeout, polling);
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)) != null ? true : false;
	}

	/**
	 * Function to wait using fluent style
	 * 
	 * @param timeout
	 * @param pollingsec
	 * @return
	 */
	public Wait<WebDriver> getFluentWait(WebDriver driver,int timeout, int pollingsec) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofMillis(pollingsec)).ignoring(NoSuchElementException.class)
				.withMessage("getFluentwait timedout");
		return wait;

	}

	/**
	 * Function to wait for ajax load
	 * 
	 * @return
	 */
	public static ExpectedCondition<Boolean> waitForAjaxLoad() {
		return new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver input) {
				return (Boolean) ((JavascriptExecutor) input)
						.executeScript("return (window.jQuery != null)   && (jQuery.active === 0);");
			}

		};
	}

	/**
	 * Function to check whether the angular call has completed without using jquery
	 * @return
	 */
	public static ExpectedCondition<Boolean> angularHasFinishedProcessing() {
		return new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return Boolean.valueOf(((JavascriptExecutor) driver).executeScript(
						"return (window.angular !== undefined) "
						+ "&& (angular.element(document).injector() !== undefined) "
						+ "&& (angular.element(document).injector() "
						+ ".get('$http').pendingRequests.length === 0)")
						.toString());
			}
		};
	}

	/**
	 * Function to wait for pageload time out
	 * 
	 * @param timeout
	 * @param unit
	 */
	public void pageLoadTimeOut(WebDriver driver,long timeout, TimeUnit unit) {
		logger.info("Waiting for page load timeout");
		driver.manage().timeouts().pageLoadTimeout(timeout, unit);
	}

	/*####################Actions using awaitility####################*/
	
	/**
	 * Function to wait for file to get downloaded using lib awaitility
	 * 
	 * @param filename
	 */
	public void waitForFileDownloadUsingAwait(String filename) {
		logger.debug("Waiting for file to get downloaded using awaitility");
		Path filePath = Paths.get(".", filename);
		await().atMost(1, MINUTES).ignoreExceptions().until(() -> filePath.toFile().exists());
	}

	/**
	 * Function to wait until text matches with the expected
	 *
	 * @param textToMatch
	 */
	public void waitUntilTitleMatches(WebDriver driver, String textToMatch) {
		await().atMost(10, TimeUnit.SECONDS).ignoreException(StaleElementReferenceException.class)
				.until(pageTitleMatches(driver,textToMatch));
	}
	/**
	 * Functional predicate to check is AlertPresent
	 */
	Predicate<WebDriver> isAlertPresent = (d) -> {
		d.switchTo().alert();
		return true;
	};

	/**
	 *
	 * @param driver
	 * @param locator
	 */
	public void isDisplayedUsingAwait(WebDriver driver, By locator) {
		Reporter.log("Waiting for "+locator.toString()+" using await");
		await().atMost(40,SECONDS).pollInterval(2, TimeUnit.SECONDS)
				.ignoreException(StaleElementReferenceException.class)
				.ignoreException(IllegalStateException.class)
				.until(isDisplayed(driver,locator));
	}


	/**
	 *
	 * @param driver
	 * @param locator
	 */
	public void isNotDisplayedUsingAwait(WebDriver driver, By locator) {
		await().atMost(1,MINUTES).pollInterval(1, TimeUnit.SECONDS)
				.ignoreException(StaleElementReferenceException.class).
				until(isNotDisplayed(driver,locator), is(false));
	}
	
	private Callable<Boolean> pageTitleMatches(WebDriver driver, String searchString) {
		return () -> driver.getTitle().toLowerCase().startsWith(searchString.toLowerCase());
	}
	
	private Callable<Boolean> isDisplayed(WebDriver driver, By locator) {
		return () -> driver.findElement(locator).isDisplayed();
	}

	private Callable isNotDisplayed(WebDriver driver, By locator) {
		return new Callable() {
			public Boolean call()  {
				boolean flag = true;
				try {
					 driver.findElement(locator).isDisplayed();
				}catch (NoSuchElementException n){
					flag=false;
				}
				return flag;
			}
		};
	}

	/**
	 * Function to wait for element to be present
	 *
	 * @param timeout
	 * @param polling
	 */
	public WebElement waitForPresenceOfElement(WebDriver driver,By locator, int timeout, int polling) {
		logger.info("Waiting for the Element " + locator.toString() + " for the specified:" + timeout + " seconds");
		WebDriverWait wait = getWait(driver,timeout, polling);
		return wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.presenceOfElementLocated(locator));
	}

}
