package com.yash.selenium.webdriver.pages;

import static com.yash.selenium.webdriver.Configuration.TestRunManager.getConfiguration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.aventstack.extentreports.Status;
import com.yash.selenium.webdriver.Actions.CommonHelperActions;

public class LoginPage {
	static By userNameField = By.xpath("//input[@name='username']");
	
	static By passwordfield = By.xpath("//input[@name='password']");
	
	static By loginButton= By.xpath("//button[text()=' Login ']");
	static By forgetPass = By.xpath("//div[@class='orangehrm-login-forgot']");
	
	@FindBy(xpath="//div[@class='oxd-alert-content oxd-alert-content--error']")
	static
	WebElement errortext;
	 
	
	static WebDriver driver = null;
	
	static boolean flag= false;

	public static void enterUserName(WebDriver driver, String userName) {
		CommonHelperActions.enterTextfield(driver, userNameField, userName);
	}
	
	public static void enterPassword(WebDriver driver, String password) {
		CommonHelperActions.enterTextfield(driver, passwordfield, password);
	}

	
	public static void Login(WebDriver driver) {
		driver.get(getConfiguration().getURL());
		String os = System.getProperty("os.name");

		if(getConfiguration().getURL().equals("https://cert.deere.com/"))
		 {
		        CommonHelperActions.staticWait(4000);
		        System.out.println("URL Entered: " + getConfiguration().getURL() + "\n");
		}
		
		String url = driver.getCurrentUrl();
		boolean loggedIn = false;
		while (!loggedIn) {
			try {
//				
				if( url.contains(getConfiguration().getURL()) && os.equals("Linux")) {
					
					CommonHelperActions.searchElement(userNameField, driver);
					System.out.println("*********Login Started : Entering Username and password**********");
					enterUserName(driver, getConfiguration().getUserName());
					System.out.println("*********Entered Username :" + getConfiguration().getUserName() + "**********");

				}
				else if (url.contains(getConfiguration().getURL())) {
					System.out.println("URL Entered: " + getConfiguration().getURL() + "\n");
					
					CommonHelperActions.staticWait(10000);
					CommonHelperActions.searchElement(userNameField, driver);
					System.out.println("*********Login Started : Entering Username and password**********");
					
					for (int i = 1; i <=3; i++) {
						enterUserName(driver, getConfiguration().getUserName());
						enterPassword(driver, getConfiguration().getPassword());
						CommonHelperActions.clickButton(driver, loginButton);
						if(!driver.getCurrentUrl().contains("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login")) {
							flag = true;
							loggedIn = true;
							break;
						}else {
							flag =false;
						}
						
					}
					
					if(flag==false) {
						CommonHelperActions.clickButton(driver, forgetPass);
						loggedIn = true;
						System.out.println("*********RsetPassword for - :" + getConfiguration().getUserName() + "**********");
						
					}
					
					
					
					
				}
			}
			catch (Exception e) {
				System.out.println("============Login to the application is unsuccessfull==========");
				e.printStackTrace();
			}
		}

	}

	private static boolean isLoginPageDisplayed(WebDriver driver) {
		// TODO Auto-generated method stub
		return CommonHelperActions.verifyElementPresent(driver, userNameField);
	}
}
