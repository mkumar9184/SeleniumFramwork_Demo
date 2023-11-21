package com.yash.selenium.webdriver.Configuration;


import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.yash.selenium.webdriver.constant.ProjectConstant;

public class ChromeDriverConfiguration implements DriverConfiguration{

	@Override
	public WebDriver getDriver() {
		// TODO Auto-generated method stub
		  ChromeDriver chromeDriver = null;
	        String runtimeEnvironment = System.getProperty("runtime.environment");

	        if ("ci".equals(runtimeEnvironment)) {
	        	try {
					chromeDriver = (ChromeDriver) SingleBrowser.getInstance();
				} catch (InvalidFormatException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	        } else if ("local".equals(runtimeEnvironment)) {
	            System.setProperty("webdriver.chrome.driver", ProjectConstant.CHROME_DRIVER_FILE.getAbsolutePath());
	            chromeDriver = new ChromeDriver();
	        } else {
	            throw new IllegalArgumentException(runtimeEnvironment + " Runtime environment not supported");
	        }
	        return chromeDriver;
	}

}
