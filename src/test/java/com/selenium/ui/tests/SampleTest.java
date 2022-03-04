package com.selenium.ui.tests;

import com.global.dataprovider.JsonDataProvider;
import com.selenium.connectors.JsonConnector;
import com.selenium.ui.pageobjects.*;
import com.selenium.ui.testbase.TestBase;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

public class SampleTest extends TestBase {
  
    
    @Test(dataProvider = "TestDataCalculation", dataProviderClass = JsonDataProvider.class, invocationCount = 1)
    public void TC001_SampleTest(String rowID, String description, JSONObject data) throws Exception {
       //Write your tests here
    }

   
}
