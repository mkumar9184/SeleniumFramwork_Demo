package com.yash.selenium.webdriver.Reporter;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.yash.selenium.webdriver.Actions.AdvanceActions;
import com.yash.selenium.webdriver.Configuration.SingleBrowser;


public class ExtentManager {
	static WebDriver driver = null;

	private static ExtentReports extent;

	static String workingDir = System.getProperty("user.dir");

	static String reportName = new SimpleDateFormat("dd-MMM-yyyy-hh-mm").format(new Date())
			+ "_ExtentReportResults.html";

	static String filepath = workingDir + "\\test-output\\Extent_reports\\" + reportName;

	public synchronized static ExtentReports getSystemInfo() throws InvalidFormatException, IOException {
		if (extent == null) {
			driver = SingleBrowser.getInstance();
			try {
			//	VisionActions.isOpenModalExist(driver);
			//	VisionActions.isIPPSecurityModalExist(driver);
			} catch (Exception e) {
			}

			extent = new ExtentReports();
			extent.attachReporter(getHtmlReporter());

			Capabilities browserCap = ((RemoteWebDriver) driver).getCapabilities();
			String browserName = browserCap.getBrowserName();
			extent.setSystemInfo("Browser Name", browserName);
			extent.setSystemInfo("Browser Version", System.getProperty("browser.version"));
			extent.setSystemInfo("Environment", System.getProperty("webEnv"));
		}
		return extent;
	}

	private static ExtentHtmlReporter getHtmlReporter() {

		File file = new File(filepath);
		if(file.isDirectory())
		{
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(filepath);
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setDocumentTitle("Manoj Automation Suite");
		htmlReporter.config().setReportName("Automation Execution Report");
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setTheme(Theme.DARK);
		htmlReporter.config().setTimeStampFormat("dd MMM yyyy HH:mm");
		return htmlReporter;
		}
		else
		{
			AdvanceActions.MakeDirectory();
			ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(filepath);
			htmlReporter.config().setChartVisibilityOnOpen(true);
			htmlReporter.config().setDocumentTitle("Manoj Automation Suite");
			htmlReporter.config().setReportName("Automation Execution Report");
			htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
			htmlReporter.config().setTheme(Theme.DARK);
			htmlReporter.config().setTimeStampFormat("dd MMM yyyy HH:mm");
			return htmlReporter;
		}
	}

}
