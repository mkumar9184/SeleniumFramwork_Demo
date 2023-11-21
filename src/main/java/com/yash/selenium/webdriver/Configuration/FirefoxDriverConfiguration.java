package com.yash.selenium.webdriver.Configuration;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FirefoxDriverConfiguration implements DriverConfiguration {


    private static final int TIMEOUT = 10;

    public WebDriver getDriver() {
        WebDriver driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(TIMEOUT, TimeUnit.SECONDS);
        return driver;
    }

}
