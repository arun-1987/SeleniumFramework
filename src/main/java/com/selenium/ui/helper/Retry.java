package com.selenium.ui.helper;

import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer{
	
	private int retrycount = 0;
	private int maxretrycount = 0;
	private Logger log = LoggerHelper.getLogger(this.getClass());

	public boolean retry(ITestResult result) {
		// TODO Auto-generated method stub
		if(retrycount<maxretrycount) {
			log.info("Retrying test "+result.getName()+" with status "+getResultStatusName(result.getStatus())+" for the "+(retrycount+1)+" times");
			retrycount++;
			return true;
		}
		return false;
	}

	public String getResultStatusName(int status) {
		String resultname = null;
		if(status==1) {
			resultname = "PASS";
		}if(status==2) {
			resultname = "FAIL";
		}
		if(status==3) {
			resultname = "SKIP";
		}
		return resultname;
		
	}
}
