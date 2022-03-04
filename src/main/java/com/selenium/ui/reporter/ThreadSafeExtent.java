package com.selenium.ui.reporter;

import com.aventstack.extentreports.ExtentTest;

public class ThreadSafeExtent {

	 public static ThreadLocal<ExtentTest>  extentTestThreadSafe = new ThreadLocal<ExtentTest>(); 
	 public static synchronized ExtentTest getTest() 
	 { 
	   return extentTestThreadSafe.get(); 
	 }
	 public static void setTest(ExtentTest tst) 
	 { 
	    extentTestThreadSafe.set(tst); 
	 }
}
