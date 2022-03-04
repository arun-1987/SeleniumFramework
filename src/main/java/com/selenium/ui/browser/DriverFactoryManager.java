package com.selenium.ui.browser;



import static org.openqa.selenium.remote.CapabilityType.PROXY;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.selenium.connectors.JsonConnector;
import com.selenium.ui.helper.LoggerHelper;

import static com.selenium.ui.browser.DriverType.*;
import static com.selenium.ui.browser.DriverType.valueOf;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;


public class DriverFactoryManager {

	private  static Logger logger = LoggerHelper.getLogger(DriverFactoryManager.class);
	private RemoteWebDriver driver;
    private DriverType selectedDriverType;
    

    private final String operatingSystem = System.getProperty("os.name").toUpperCase();
    private final String systemArchitecture = System.getProperty("os.arch");
    private final boolean useRemoteWebDriver = Boolean.valueOf(JsonConnector.getConfig("remoteDriver")!=null?JsonConnector.getConfig("remoteDriver"):"false");
    private final boolean useGrid = Boolean.valueOf(JsonConnector.getConfig("useGrid")!=null?JsonConnector.getConfig("useGrid"):"false");
    private final boolean apiInstance = Boolean.valueOf(JsonConnector.getConfig("apiInstance")!=null?JsonConnector.getConfig("apiInstance"):"false");
    private final boolean proxyEnabled = Boolean.getBoolean("proxyEnabled");

    
    public DriverFactoryManager() {
    	logger.debug("Default browser type is Chrome. For other options reset the value in config.json");
        DriverType driverType = WEBDRIVERMANAGERCHROME;
        String browser = System.getProperty("browser", 
                driverType.toString()).toUpperCase();
        try {
            driverType = valueOf(browser);
        } catch (IllegalArgumentException ignored) {
        	logger.error("Unknown driver specified, defaulting to '" + driverType + "'...");
        } catch (NullPointerException ignored) {
        	logger.error("No driver specified, defaulting to '" + driverType + "'...");
        }
        selectedDriverType = WEBDRIVERMANAGERCHROME;
    }
    
    public RemoteWebDriver getDriver(){
        if (driver == null) {
            instantiateWebDriver(selectedDriverType);
        }
        return driver;
    }

    public RemoteWebDriver getStoredDriver() {
        return driver;
    }
    

    public void quitDriver() {
        if (null != driver) {
            driver.quit();
            driver = null;
        }
    }
    
    private void instantiateWebDriver(DriverType driverType) {
        logger.info("Local Operating System: " + operatingSystem);
        logger.info("Local Architecture: " + systemArchitecture);
        logger.info("Selected Browser: " + selectedDriverType);
        logger.info("Selenium Grid is set to : " + useGrid);
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        if (proxyEnabled) {
           //code for if proxy enabled
            desiredCapabilities.setCapability(PROXY, "");
        }
        if (useGrid) {
            URL seleniumGridURL = null;
            try {
                 seleniumGridURL = new URL(JsonConnector.getConfig("gridURL"));
            }catch (MalformedURLException e){
                logger.error("Exception in the url formed "+e.getMessage());
            }
            String desiredBrowserVersion = JsonConnector.getConfig("desiredBrowserVersion");
            String desiredPlatform = JsonConnector.getConfig("desiredPlatform");
            if (null != desiredPlatform && !desiredPlatform.isEmpty()) {
                desiredCapabilities.setPlatform(Platform.valueOf(desiredPlatform.toUpperCase()));
            }
            if (null != desiredBrowserVersion && !desiredBrowserVersion.isEmpty()) {
                desiredCapabilities.setVersion(desiredBrowserVersion);
            }
            desiredCapabilities.setBrowserName(selectedDriverType.toString());
            driver = new RemoteWebDriver(seleniumGridURL, desiredCapabilities);
        } else if(apiInstance) {
            logger.info("Api Execution is initiated");
        } else {
        	driver = driverType.getWebDriverObject(desiredCapabilities);
        }
    }

}
