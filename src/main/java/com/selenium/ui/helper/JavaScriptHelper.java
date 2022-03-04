package com.selenium.ui.helper;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptHelper {

	private WebDriver driver;
	private Logger log = LoggerHelper.getLogger(this.getClass());
	
	public JavaScriptHelper(WebDriver driver) {
		this.driver = driver;
		// TODO Auto-generated constructor stub
	}

	
	public Object executeScript(String script) {
		JavascriptExecutor Je = (JavascriptExecutor) driver;
		return Je.executeScript(script);
		
	}
	
	public Object executeScript(String script,Object...objects) {
		JavascriptExecutor Je = (JavascriptExecutor) driver;
		return Je.executeScript(script, objects);	
	}
	
	public void scrollToElement(WebElement element) {
		log.info("Executing scrollToElement using javascript for the element "+element.toString());
		executeScript("window.scrollTo(arguments[0],arguments[1])",element.getLocation().x,element.getLocation().y);
	}
	
	public void scrollToElementAndClick(WebElement element) {
		log.info("Executing scrollToElementAndClick using javascript for the element "+element.toString());
		executeScript("window.scrollTo(arguments[0],arguments[1])",element.getLocation().x,element.getLocation().y);
		element.click();
	}
		
	public void scrollIntoView(WebElement element) {
		log.info("Executing scrollIntoView using javascript for the element"+element.toString());
		executeScript("arguments[0].scrollIntoView()",element);
	}
	
	public void scrollDownVertically() {
		log.info("Executing scrollDownVertically using javascript for the element");
		executeScript("window.scrollTo(0,document.body.scrollHeight)");
	}
	
	public void scrollUpVertically() {
		log.info("Executing scrollUpVertically using javascript for the element");
		executeScript("window.scrollTo(0,-document.body.scrollHeight)");
	}
}
