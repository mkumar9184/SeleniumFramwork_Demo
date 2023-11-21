package com.yash.selenium.webdriver.Actions;

import com.yash.selenium.webdriver.constant.ProjectConstant;

public class ConfigurationActions {

	public static String getURLToRun() {
		if (System.getProperty("ApplicationUrl") != null) {
			return System.getProperty("ApplicationUrl");
		}
		return PropertyFileActions.getPropertyValue("ApplicationUrl", ProjectConstant.CONFIGURATION_PROPERTY_FILE.getAbsolutePath());
	}
	
	//user name
		public static String getUserNameToRun() {
			if (System.getProperty("usrNme") != null) {
				return System.getProperty("usrNme");
			}
			return PropertyFileActions.getPropertyValue("usrNme", ProjectConstant.CONFIGURATION_PROPERTY_FILE.getAbsolutePath());
		}
	
	//pwd 
	public static String getPasswordToRun() {
		if (System.getProperty("pwd") != null) {
			return System.getProperty("pwd");
		}
		return PropertyFileActions.getPropertyValue("pwd", ProjectConstant. CONFIGURATION_PROPERTY_FILE.getAbsolutePath());
	}
	


	public static String getEnvironmentToRun() {
		return PropertyFileActions.getPropertyValue("EnvironmentToRunOn",
				ProjectConstant. CONFIGURATION_PROPERTY_FILE.getAbsolutePath());
	}

	public static String getBrowserToRun() {
		return PropertyFileActions.getPropertyValue("BrowserToRunOn", ProjectConstant. CONFIGURATION_PROPERTY_FILE.getAbsolutePath());
	}

	public static String getServerToRun() {
		return PropertyFileActions.getPropertyValue("Server", ProjectConstant. CONFIGURATION_PROPERTY_FILE.getAbsolutePath());
	}
}
