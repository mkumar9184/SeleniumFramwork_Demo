package com.yash.selenium.webdriver.Configuration;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.yash.selenium.webdriver.constant.ProjectConstant;

public class IEDriverConfiguration implements DriverConfiguration {
	    private static final int TIMEOUT = 10;
	    DesiredCapabilities capab = null;
	    public WebDriver getDriver() {
	        InternetExplorerDriver ieDriver;

	        System.setProperty("webdriver.ie.driver", ProjectConstant.Edge_DRIVER_FILE.getAbsolutePath());
	        capab = new DesiredCapabilities();
	        capab.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
	        capab.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
	        capab.setCapability("ms:inPrivate", true);
	        ieDriver = new InternetExplorerDriver(capab);
	        ieDriver.manage().timeouts().implicitlyWait(TIMEOUT, TimeUnit.SECONDS);
	        
	        return ieDriver;
	    }

}
