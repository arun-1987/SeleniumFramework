package com.selenium.ui.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.selenium.ui.testbase.TestBase;

public class WebElementHelper {

	private Logger logger = LoggerHelper.getLogger(this.getClass());

	private static WebDriver driver;
	
	public WebElementHelper() throws Exception {
		this.driver = TestBase.getDriver();
	}
	
	
	Function<WebDriver, ArrayList<WebElement>> getListOfElements(By locator){
			return new Function<WebDriver, ArrayList<WebElement>>() {
		public ArrayList<WebElement> apply(WebDriver driver) {
		ArrayList<WebElement> list = (ArrayList<WebElement>) driver.findElements(locator);
		return list;
		}
		};
}
	
	Function<WebDriver, List<WebElement>> getListOfElements = 
	        driver -> driver.findElements(By.id("foo"));	
}
