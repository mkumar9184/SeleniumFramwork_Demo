package com.yash.selenium.webdriver.Reporter;

import java.util.HashMap;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class ExtentReportUtils {
	private static ExtentReports extentReport;
	private static HashMap<ExtentTest, ExtentTest> parentChildMap = new HashMap<ExtentTest, ExtentTest>();
	private static HashMap<Integer, ExtentTest> testMap = new HashMap<Integer, ExtentTest>();

	/*static String reportName = new SimpleDateFormat("dd-MMM-yyyy-hh-mm").format(new Date())
			+ "_ExtentReportResults.html";*/
	static String reportName = "ManojAutomaion_ExtentReportResults.html";
	static String filepath = System.getProperty("user.dir") + "\\reports\\" + reportName;	

	static{
		extentReport = new ExtentReports(filepath);
	}

	public static ExtentReports getExtentReport() {
		if (extentReport == null)
			extentReport = new ExtentReports(filepath);
		return extentReport;
	}

	public static ExtentTest startTest(String testName, String desc) {
		ExtentTest test = extentReport.startTest(testName, desc);
		testMap.put((int) (long) (Thread.currentThread().getId()), test);
		return test;
	}

	public static void appendTest(ExtentTest parentNode, String testName) {
		ExtentTest childNode = extentReport.startTest(testName, "");
		parentNode.appendChild(childNode);
		parentChildMap.put(childNode, parentNode);
		testMap.put((int) (long) (Thread.currentThread().getId()), childNode);
	}

	public static void endTest() {
		extentReport.endTest(testMap.get((int) (long) (Thread.currentThread().getId())));
	}

	public static void endParentTest(ExtentTest childNode) {
		extentReport.endTest(parentChildMap.get(childNode));
	}

	public static ExtentTest getTest() {
		return testMap.get((int) (long) (Thread.currentThread().getId()));
	}
}
