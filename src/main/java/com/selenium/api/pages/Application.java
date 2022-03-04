package com.selenium.api.pages;

import com.selenium.api.KafeneApiHelper;
import com.selenium.ui.testbase.TestBase;
import org.checkerframework.checker.units.qual.K;

import java.util.List;
import java.util.Map;

public class Application extends TestBase {
	
	public List<String> createApplication(){
		KafeneApiHelper kafeneApiHelper = new KafeneApiHelper();
		Map<String,String> merchantInfo = kafeneApiHelper.loginMerchant_Api();
		Map<String,String> userInfo = kafeneApiHelper.setRegisterUser(merchantInfo.get("Token"));
		merchantInfo.putAll(userInfo);
		kafeneApiHelper.setAccountConfirmation(merchantInfo);
		return kafeneApiHelper.createApp(merchantInfo);
	}
	
	
}
