package com.yash.selenium.page;

import java.io.IOException;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.yash.selenium.webdriver.Actions.CommonHelperActions;
import com.yash.selenium.webdriver.BeforeSuiteAction.BaseClass;
import com.yash.selenium.webdriver.Reporter.ExtentManager;
import com.yash.selenium.webdriver.TestData.LateActivityExcelFileReadWriteDelete;
import com.yash.selenium.webdriver.page.ContactSales.TestPage;

public class testclass extends BaseClass {

	ExtentReports report;
	ExtentTest parentTest;
	ExtentTest childTest;
	public static Map<String, String> projectNumber = null;
	public static String sheetNameInExcel2 = "DEVL";
	SoftAssert sassert = null;
	TestPage testpage = null;

	@AfterMethod
	public void afterMethod(ITestResult result) throws InvalidFormatException, IOException {
		com.yash.selenium.webdriver.Actions.AdvanceActions.afterMethod(driver, result, childTest);

	}

	@BeforeClass
	public void setUp() throws InvalidFormatException, IOException {
		Reporter.log("*******************Test Script****************", true);
		testpage = new TestPage(driver);
		report = ExtentManager.getSystemInfo();
		projectNumber = LateActivityExcelFileReadWriteDelete.readProjectTestCasefromExcelAsTrueOrFalse(sheetNameInExcel2);
		parentTest = report.createTest("OrangeHr Automation Report");

	}

	@Test
	public void checkDashBoardScreen() {

		childTest = parentTest.createNode("Login with 'valid credential In application");
		sassert = new SoftAssert();

		if (projectNumber.containsKey("checkDashBoarScreen")) {
			if (projectNumber.get("checkDashBoarScreen").equalsIgnoreCase("N"))
				throw new SkipException("Skipping this Class");
		}

		com.yash.selenium.webdriver.Actions.CommonHelperActions.staticWait(6000);
		sassert.assertTrue(testpage.checkContactSaleButtonStatus(childTest), "User is not loginned in application");
//		 sassert.assertTrue(testpage.checkContactSaleButtonStatusfail(childTest), "User is not loginned in application");
		sassert.assertAll();

	}

	@Test
	public void checkDashBoardScreendef() {

		childTest = parentTest.createNode("Login with 'Invalid check");
		sassert = new SoftAssert();

		if (projectNumber.containsKey("checkDashBoardScreendef")) {
			if (projectNumber.get("checkDashBoardScreendef").equalsIgnoreCase("N"))
				throw new SkipException("Skipping this Class");
		}

		com.yash.selenium.webdriver.Actions.CommonHelperActions.staticWait(6000);
//		 sassert.assertTrue(testpage.checkContactSaleButtonStatus(childTest), "User is not loginned in application");
		sassert.assertTrue(testpage.checkContactSaleButtonStatusfail(childTest), "User is not loginned in application");
		sassert.assertAll();

	}

	@Test
	public void timeSheetModuleTest() {

		childTest = parentTest.createNode("Move On Time Sheet Module");
		sassert = new SoftAssert();

		if (projectNumber.containsKey("timeSheetModuleTest")) {
			if (projectNumber.get("timeSheetModuleTest").equalsIgnoreCase("N"))
				throw new SkipException("Skipping this Class");
		}

		sassert.assertTrue(testpage.textSearchBox(childTest), "User is not moved on TimeSheet module in application");
		sassert.assertAll();

	}

}
