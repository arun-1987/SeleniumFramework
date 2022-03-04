package com.selenium.ui.pageobjects;

import com.global.dataprovider.GlobalPropertiesReader;
import com.selenium.connectors.JsonConnector;
import com.selenium.ui.helper.ExceptionHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.testng.Reporter;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class LoginPage extends BasePage {


    By EMAILID = By.xpath("//input[contains(@id,'EmailAddress')]");
    By PASSWORD = By.xpath("//input[contains(@id,'Password')]");
    By LOGIN_BTN = By.xpath("//button[contains(.,'Log in')]");

    public LoginPage launchApplication() {
        Reporter.log("************"+new Throwable().getStackTrace()[0].getMethodName()+"****************");
        return this;
    }

  
}
