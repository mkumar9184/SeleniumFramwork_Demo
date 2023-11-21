package com.yash.selenium.webdriver.Reporter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.yash.selenium.webdriver.Configuration.SingleBrowser;

public class ReportListener implements ITestListener{
	HashMap<String, ExtentTest> classMapper = new HashMap<>();
	static ExtentTest validationTest;

	public void onTestStart(ITestResult result) {
		ExtentTest extentTest, lastTest;
		String className = result.getTestClass().getRealClass().getSimpleName();

		if (null != classMapper && classMapper.get(className) == null) {
			lastTest = ExtentReportUtils.getTest();
			if (null != lastTest)
				ExtentReportUtils.endParentTest(lastTest);
			extentTest = ExtentReportUtils.startTest(className, "");
			classMapper.put(className, extentTest);
		}
		ExtentReportUtils.appendTest(classMapper.get(className), result.getName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		ExtentReportUtils.getTest().log(LogStatus.PASS, result.getName() + ": Pass");
	}

	/*
	 * LOGS FAIL TEST CASES AND ATTACHES SCREENSHOT
	 * 
	 * @see org.testng.ITestListener#onTestFailure(org.testng.ITestResult)
	 */
	@Override
	public void onTestFailure(ITestResult result) {
		try {
			File file = ((TakesScreenshot) SingleBrowser.getInstance()).getScreenshotAs(OutputType.FILE);
			String path = System.getProperty("user.dir") + "\\reports\\" + result.getMethod().getMethodName() + ".png";
			FileHandler.copy(file, new File(path));

			ExtentReportUtils.getTest().log(LogStatus.FAIL, result.getMethod().getMethodName(),
					ExtentReportUtils.getTest().addScreenCapture(path));
			ExtentReportUtils.getTest().log(LogStatus.FAIL, "Error : " + result.getThrowable().toString());
		} catch (IOException | WebDriverException | InvalidFormatException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		ExtentReportUtils.getTest().log(LogStatus.SKIP, result.getName() + " Skipped");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		ExtentReportUtils.getTest().log(LogStatus.FAIL, result.getName() + " Failed with Success Percentage");
	}

	@Override
	public void onStart(ITestContext context) {
	}

	@Override
	public void onFinish(ITestContext context) {
		ExtentReportUtils.endParentTest(ExtentReportUtils.getTest());

		Set<ITestResult> map = context.getFailedConfigurations().getAllResults();
		Set<ITestResult> map2 = context.getFailedTests().getAllResults();

		if (map != null && map.size() != 0) {
			validationTest = ExtentReportUtils.startTest("Failed Validations", "");
			for (ITestResult m : map) {
				ExtentReportUtils.getTest();
				validationTest.log(LogStatus.FAIL,
						"Error in " + m.getTestClass() + " : " + m.getThrowable().getMessage());

			}
		}
		ExtentReportUtils.endTest();
		ExtentReportUtils.getExtentReport().flush();
	}

}
