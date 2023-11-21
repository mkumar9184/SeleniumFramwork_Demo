package com.yash.selenium.webdriver.Configuration;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.yash.selenium.webdriver.constant.ProjectConstant;

public class BrowserConfig {

	static WebDriver driver = null;
    private static final int TIMEOUT = 10;
    static DesiredCapabilities capab = null;
	public static WebDriver getDriver(String browserType) {
		String runtimeEnvironment = "local";

		if("chrome".equalsIgnoreCase(browserType)) {
			if("ci".equals(runtimeEnvironment)) {
				System.out.println(ProjectConstant.CHROME_DRIVER_FILE_CI.getAbsolutePath());
			//	System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_CI.getAbsolutePath());
			//	WebDriverManager.chromedriver().setup();
	            driver = new ChromeDriver();
			} else if ("local".equals(runtimeEnvironment)) {
	            System.setProperty("webdriver.chrome.driver", ProjectConstant.CHROME_DRIVER_FILE.getAbsolutePath());
	            driver = new ChromeDriver();
	        }
		} else if("Edge".equalsIgnoreCase(browserType)) {

	        System.setProperty("webdriver.ie.driver", ProjectConstant.Edge_DRIVER_FILE.getAbsolutePath());
	        capab = new DesiredCapabilities();
	        capab.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
	        capab.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
	        driver = new InternetExplorerDriver(capab);
	        driver.manage().timeouts().implicitlyWait(TIMEOUT, TimeUnit.SECONDS);

		} else if("ff".equalsIgnoreCase(browserType)) {
			FirefoxDriver fdriver = new FirefoxDriver();
			return fdriver;
		}

		return driver;
	}

}
