package com.selenium.ui.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class LoggerHelper {
	
	private static boolean root=false;

	/**
	 *
	 * @param name
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Logger getLogger(Class name) {
		if(root) {
			return LogManager.getLogger(name);
		}		
		//String path = ResourceHelper.getResourceHelper("\\src\\main\\resources\\configfile\\log4j2.properties");
	//	LoggerContext context = (LoggerContext) LogManager.getContext(false);
	//	context.setConfigLocation(new File(path).toURI());
		//PropertyConfigurator.configure("));		
		root = true;
		return LogManager.getLogger(name);
	}
	

	

	/**
	 * CSS styles to be used in HTML report
	 */
	public static final String ACTION_STYLE	= "color : purple; font-weight: bold;";
	public static final String DATA_STYLE 	= "color : black; font-weight: bold;";
	public static final String VERIFY_STYLE	= "color : green; font-weight: bold;";
	public static final String ERROR_STYLE 	= "style='color : red; font-weight: bold;";
	public static final String STEP_STYLE 	= "color : blue; font-weight: bold;";

	public static String format(String format, String data) {
		return String.format("%s", format, data);
	}

	/**
	 * Used to wrap data values so they are emphasized in the HTML report
	 */
	public static String formatData(String data) {
		return format(DATA_STYLE, data);
	}

	public static String formatError(String data) {
		return format(ERROR_STYLE, data);
	}

	public static String formatVerify(String data) {
		return format(VERIFY_STYLE, data);
	}

	public static String formatStepInfo(String data) {
		return format(STEP_STYLE, data);	
	}

	/**
	 * Used to wrap actions so they are emphasized in the HTML report
	 * - Edit, Copy, Validate, etc
	 */
	public static String formatAction(String action) {
		return String.format("%s", ACTION_STYLE, action);
	}
	
	public static String formatAction(String action, String data) {
		return String.format("%s %s", formatAction(action), formatData(data));
	}
	public static String formatAction(String action, String other, String data) {
		return String.format("%s %s %s", formatAction(action), other, formatData(data));
	}

}
