package com.selenium.ui.helper;

public class ResourceHelper {
	
	/**
	 * @param path
	 * @return base path
	 */
	public static String getResourceHelper(String path) {
		String basepath = System.getProperty("user.dir");
		return basepath + path;
	}
	
	public static String getBasePath() {
		return System.getProperty("user.dir");		
	}

}
