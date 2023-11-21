package com.yash.selenium.webdriver.page.ContactSales;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.yash.selenium.webdriver.Actions.CommonHelperActions;

public class TestPage {
	WebDriver driver = null;

	@FindBy(xpath = "//*[text()='Dashboard']")
	WebElement dashBoard;

	@FindBy(xpath = "//*[text()='Dashboar']")
	WebElement dashBoarderr;

	@FindBy(xpath = "//input[@placeholder='Search']")
	WebElement searchtext;

	@FindBy(xpath = "//ul[@class='oxd-main-menu']//li")
	List<WebElement> searchTextManuList;

	@FindBy(xpath = "//span[@class='oxd-topbar-header-breadcrumb']")
	WebElement timeSheetHeader;

	public TestPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public boolean checkContactSaleButtonStatus(ExtentTest childTest) {
		com.yash.selenium.webdriver.Actions.CommonHelperActions.staticWait(7000);
		Reporter.log("		Step-1  Verify user is login in Home ");
		if (dashBoard.isEnabled() && dashBoard.getTagName().contains("p")) {
			childTest.log(Status.INFO, "User successfully login in OrangeHr page");

			return true;
		}
		return false;

	}

	public boolean checkContactSaleButtonStatusfail(ExtentTest childTest) {
		CommonHelperActions.staticWait(7000);
		Reporter.log("		Step-1  Verify user is login in Home ");
		try {
			if (dashBoarderr.isEnabled() && dashBoard.getTagName().contains("pa")) {
				childTest.log(Status.INFO, "User successfully login in OrangeHr page");

				return false;
			}
		} catch (Exception e) {
			childTest.log(Status.ERROR, "User not logined in OrangeHr page");
		}
		return false;

	}

	public boolean textSearchBox(ExtentTest childTest) {
		CommonHelperActions.staticWait(200);
	
		Reporter.log("		Step-1  Enter Search text as 'Admin' ");
		searchtext.sendKeys("im");
		CommonHelperActions.staticWait(200);
		try {
			
			for (int i = 0; i < searchTextManuList.size(); i++) {
				if (searchTextManuList.get(i).getText().equals("PIM")) {
					CommonHelperActions.waitAndClickAnElement(driver, searchTextManuList.get(i));
					CommonHelperActions.staticWait(500);
					Reporter.log("		Step-2  Click on '" + searchTextManuList.get(i).getText() + "'link");
					break;
				}
			}
			CommonHelperActions.staticWait(500);
			String str = timeSheetHeader.getText();

			if (str.contains("PIM")) {

				childTest.log(Status.INFO, "User is successfully move on 'TimeSheet' module  in OrangeHr ");
				System.out.println(" true");
				return true;
			}
		} catch (Exception e) {
			childTest.log(Status.ERROR, "User is unable to move on 'TimeSheet' module  in OrangeHr ");
		}
		return false;

	}

}
