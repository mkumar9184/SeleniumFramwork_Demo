package com.yash.selenium.webdriver.Actions;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.yash.selenium.webdriver.Reporter.ExtentManager;
import com.yash.selenium.webdriver.constant.ProjectConstant;


public class AdvanceActions {
	
	public static void deleteFolder(WebDriver driver) {
		String workingDir = System.getProperty("user.dir");
		File file = new File(workingDir + "\\test-output\\Extent_reports");
		String[] myFiles;
		if (file.isDirectory()) {
			myFiles = file.list();
			for (int i = 0; i < myFiles.length; i++) {
				File myFile = new File(file, myFiles[i]);
				myFile.delete();
			}
		}

	}

	public static void MakeDirectory()
	{
		  //Instantiate the File class
		String workingDir = System.getProperty("user.dir");
		File file = new File(workingDir + "\\test-output\\Extent_reports");

	      //Creating a folder using mkdir() method
	      boolean bool = file.mkdir();
	}

	public static List<String> readActivitiesFromProperties() {
		String oldProperty = PropertyFileActions.getPropertyValue(ProjectConstant.PROPERTY_ACTIVITY,
				ProjectConstant.PREVIVE_PROPERTY_FILE.getAbsolutePath());
		String[] arr = oldProperty.split(" ");
		List<String> activities = Arrays.asList(arr);
		return activities;
	}
	public String generateActvityIfNotInProperties(WebDriver driver, String projectNumber) {
		String activityNumber = AdvanceActions.readActivitiesFromProperties().get(0);
		if (activityNumber == null || "".equals(activityNumber)) {
//			return createActivityPage.createActivityForActiveProject(driver, projectNumber);
		}
		return activityNumber;
	}

	
	public static void setCalendarDate(WebDriver driver, WebElement ele, String date) {
		try {
			CommonHelperActions.hoverOverElementandClickAll(driver, ele);
			ele.sendKeys(Keys.DELETE);
			CommonHelperActions.hoverOverElementandClickAll(driver, ele);
			CommonHelperActions.enterTextActions(driver, ele, date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		CommonHelperActions.staticWait(500);
	}
	
	public static void assertErrorAction(ExtentTest childTest, String failStatement, WebDriver driver)
			throws IOException, Exception {
		childTest.fail(failStatement).addScreenCaptureFromPath(addScreenshot(driver));
		childTest.log(Status.FAIL, MarkupHelper.createLabel(ProjectConstant.warningLabelString, ExtentColor.RED));
	}
	
	public static String addScreenshot(WebDriver driver) throws Exception {
		TakesScreenshot newScreen = (TakesScreenshot) driver;
		String scnShot = newScreen.getScreenshotAs(OutputType.BASE64);
		return "data:image/png;base64, " + scnShot;
	}

	public static void passScreenCapture(WebDriver driver) {
		String workingDir = System.getProperty("user.dir");
		Date d = new Date();
		System.out.println(d.toString());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile,
					new File(workingDir + "\\test-output\\Extent_reports\\" + sdf.format(d) + ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void reportAssertTrue(WebDriver driver, ExtentTest childTest, boolean condition, String failStatement)
			throws IOException, Exception {

		try {
			Assert.assertTrue(condition);

		} catch (AssertionError e) {
			assertErrorAction(childTest, failStatement, driver);
		}

	}
	
	public static void scrollToElement(WebDriver driver, By by) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(by));
		WebElement ele = driver.findElement(by);
		scrollToElement(driver, ele);
	}

	public static void scrollPageUp(WebDriver driver, int param) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,-" + param + ")", "");
	}

	public static void scrollPageDown(WebDriver driver, int param) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0," + param + ")", "");
	}
	public static void scrollToTop(WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, 0);", "");
		CommonHelperActions.staticWait(500);
	}

	public static void scrollToWebElement(WebDriver driver, WebElement ele) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ele);
		CommonHelperActions.staticWait(800);
	}

	public static void scrollToElement(WebDriver driver, WebElement ele) {
		AdvanceActions.scrollToTop(driver);
		int y = ele.getLocation().y;
		CommonHelperActions.scrollPageDown(driver, y - 200);
		CommonHelperActions.staticWait(800);
	}

	public static void scrollToElementHorizontally(WebDriver driver, WebElement eleToScrollTo) {
		int x = eleToScrollTo.getLocation().x;
		scrollPageHorizontally(driver, x);
	}

	public static void scrollPageHorizontally(WebDriver driver, int param) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(" + param + ",0)", "");

	}
	
	public static void afterMethod(WebDriver driver, ITestResult result, ExtentTest childTest) throws InvalidFormatException, IOException {
		if (result.getStatus() == ITestResult.FAILURE) {

			childTest.log(Status.FAIL,
					MarkupHelper.createLabel("Method - " + result.getName() + " : FAILED", ExtentColor.RED));
			childTest.log(Status.FAIL, result.getThrowable());
			try {
				childTest.addScreenCaptureFromPath(addScreenshot(driver));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (result.getStatus() == ITestResult.SUCCESS) {
			childTest.log(Status.PASS,
					MarkupHelper.createLabel("Method - " + result.getName() + " : PASSED", ExtentColor.GREEN));
		} else if (result.getStatus() == ITestResult.SKIP) {
			childTest.log(Status.SKIP,
					MarkupHelper.createLabel("Method - " + result.getName() + " : SKIPPED", ExtentColor.ORANGE));
		}

		ExtentManager.getSystemInfo().flush();

	}

}
