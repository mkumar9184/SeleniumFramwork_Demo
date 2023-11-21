package com.yash.selenium.webdriver.constant;

import java.io.File;

public class ProjectConstant {
	// TIMEOUT FOR AJAX
		public static final int AJAX_TIMEOUT = 10000;

		// CHROME DRIVER
		public static final String CHROME_FILE_NAME_LOCAL = "chromedriver.exe";

		public static final String PLATFORM_DATA_FILE = "";
//		
		public static final String ALL_PROJECT_ACTIVITY_DATA_FILE = "Project_ACTIVITY_DATA_FILE.xlsx";
		
		public static final String PROPERTY_ACTIVITY = "Activity";

		public static final String CHROME_FILE_NAME_CI = "chromedriver_v_101.exe";

		public static final File CHROME_DRIVER_FILE = new File(CHROME_FILE_NAME_LOCAL);

		public static final File CHROME_DRIVER_FILE_CI = new File(CHROME_FILE_NAME_CI);

		public static final File PREVIVE_PROPERTY_FILE = new File("Previve.properties");

		public static final File CONFIGURATION_PROPERTY_FILE = new File("ConfigurationProperties.properties");



		// IE DRIVER
		public static final File Edge_DRIVER_FILE = new File("msedgedriver.exe");

		// BASE URLS
		public static final String DEV_BASEURL = "";

		// TEST USER IDS
		public static final String DEV_USERID = "123456";

		public static final String DEV_PASSWORD = "";

		public static final String MO_USERID = "";

		public static final String MO_PASSWORD = "";

		public static final String PLATFORM_UNIT_ADMIN_USERID = "";

		public static final String PLATFORM_ADMIN_USERID = "";
		
		
		public static String warningLabelString = "WARNING : Please Refer Screen Shot For More Information";



}
