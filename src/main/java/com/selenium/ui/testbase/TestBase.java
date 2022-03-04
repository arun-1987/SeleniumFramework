package com.selenium.ui.testbase;


import com.selenium.connectors.JsonConnector;
import com.selenium.ui.browser.DriverFactoryManager;
import com.selenium.ui.helper.LoggerHelper;
import com.selenium.ui.listener.ReportListener;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Listeners({ReportListener.class})
public class TestBase {

    private static List<DriverFactoryManager> webDriverThreadPool = Collections.synchronizedList(new ArrayList<DriverFactoryManager>());
    private static ThreadLocal<DriverFactoryManager> driverFactoryThread;
    private static Boolean executionType;
    private static Logger logger = LoggerHelper.getLogger(TestBase.class);

    /**
     * Function to instantiate the driver object
     */
    @BeforeSuite(alwaysRun = true)
    public void instantiateDriverObject()  {
            executionType = Boolean.valueOf(JsonConnector.getConfig("executionTypeWeb"));
            executionSetUpWeb(executionType);
    }

    @BeforeMethod
    public void beforeTest() throws Exception {
        getDriver().manage().deleteAllCookies();
    }

    /**
     * Function to delete all the cookies before each method
     */
    @AfterMethod(alwaysRun = true)
    public void afterMethod() {
    	if(executionType) {
        try {
            driverFactoryThread.get().getDriver().quit();
            driverFactoryThread.remove();
        } catch (Exception ignored) {
        	logger.error("Unable to clear cookies, driver object is not viable...");
        }
    	}
    }

    /**
     * Function to quit the driver instances
     */
    @AfterSuite(alwaysRun = true)
    public void closeDriverObjects() {
    	if(executionType) {
    		webDriverThreadPool.forEach(driver->driver.quitDriver());
    	}
    }


    /**
     * Function to create webdriver instance
     * @param flag
     */
    public static void executionSetUpWeb(Boolean flag) {
    	if(flag) {
	    	driverFactoryThread = ThreadLocal.withInitial(() -> {
	        	DriverFactoryManager driverFactory = new DriverFactoryManager();
	            webDriverThreadPool.add(driverFactory);
	            return driverFactory;
	        });
    	}else {
    		logger.info("Execution type is set to API in Maven POM Properties."
    				+ "For UI Execution set the property flag executionTypeWeb to true");
    	}

    }
        
    /** 
     * Function to get the driver object
     * @return
     * @throws Exception
     */
    public static RemoteWebDriver getDriver(){
    		return driverFactoryThread.get().getDriver();
    }

    /**
     * Function to force quit the driver on demand
     */
    public void forceQuitDriverSession()  {
        try{
        if(executionType) {
           driverFactoryThread.get().getDriver().quit();
        }}catch (Exception e){
            e.printStackTrace();
        }
    }
}