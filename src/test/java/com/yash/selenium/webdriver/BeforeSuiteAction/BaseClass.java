package com.yash.selenium.webdriver.BeforeSuiteAction;

import static com.yash.selenium.webdriver.Configuration.TestRunManager.getConfiguration;

import java.io.File;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.yash.selenium.webdriver.Configuration.SingleBrowser;
import com.yash.selenium.webdriver.TestData.LateActivityExcelFileReadWriteDelete;
import com.yash.selenium.webdriver.pages.LoginPage;


public class BaseClass {


	protected static WebDriver driver;
	public static String sheetNameInExcel1 = "Browser_Configuration";
	
	@BeforeSuite
	public void killChromeProcess() throws Exception {
		String os = System.getProperty("os.name");
		if(!os.equals("Linux"))
		 {
			Runtime.getRuntime().exec("taskkill /im chrome.exe /f /t");
			Reporter.log("=====Killing existing chrome instances before suite execution=====", true);
		 }
		else
			System.out.println("==========Skipped Chrome Kill instances task....=========");
		
			try {
				String workingDir = System.getProperty("user.dir");
				File dir = new File(workingDir + "\\test-output\\Extent_reports");
				if (dir.isDirectory() == true) {
					FileUtils.cleanDirectory(dir);
				}
		   	} catch (Exception e) {
				System.out.println(e);
			}
	}

	@BeforeTest
	public void setupApplication() throws Exception {
		Reporter.log("\n=====New Browser Session Started=====\n", true);
		driver = SingleBrowser.getInstance();
		LoginPage.Login(driver);

		Reporter.log("\n\n=====Application Started=====\n", true);
	}

	@AfterClass
	public void afterclassExecution() throws Exception {
//		WelcomePage.navigateTo(driver);
	
		Reporter.log("=====Navigated to Home Page after class execution=====\n", true);
	}

	@AfterSuite
	public void afterSuiteActions() throws Exception {
	//	String browserName = getConfiguration().getBrowesrName();
		Map<String, String> browserName = LateActivityExcelFileReadWriteDelete.readProjectTestCasefromExcelAsTrueOrFalse(sheetNameInExcel1);
		if(browserName.containsKey("Edge") && browserName.get("Edge").equalsIgnoreCase("Y")) {
			Runtime.getRuntime().exec("taskkill /IM msedge.exe /F");
		}
		
		if(browserName.containsKey("Chrome") && browserName.get("Chrome").equalsIgnoreCase("Y")) {
			Runtime.getRuntime().exec("taskkill /im chrome.exe /f /t");
		}
		
		Reporter.log("=====Killed existing chrome instances in after suite execution=====", true);
	}


}
