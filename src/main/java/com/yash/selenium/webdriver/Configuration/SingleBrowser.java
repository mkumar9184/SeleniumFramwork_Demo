package com.yash.selenium.webdriver.Configuration;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.yash.selenium.webdriver.TestData.LateActivityExcelFileReadWriteDelete;
import com.yash.selenium.webdriver.constant.ProjectConstant;

import io.github.bonigarcia.wdm.WebDriverManager;

import static com.yash.selenium.webdriver.Configuration.TestRunManager.getConfiguration;
import static com.yash.selenium.webdriver.constant.ProjectConstant.CHROME_DRIVER_FILE_CI;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

public class SingleBrowser {

	public static WebDriver driver;

	/**
	 * @return
	 */
	
	public static String sheetNameInExcel1 = "Browser_Configuration";

	
	public static WebDriver getInstance() throws InvalidFormatException, IOException {
//		String browserName = getConfiguration().getBrowesrName();
		
		Map<String, String> browserName = LateActivityExcelFileReadWriteDelete.readProjectTestCasefromExcelAsTrueOrFalse(sheetNameInExcel1);
		
			
		if(browserName.containsKey("Edge") && browserName.get("Edge").equalsIgnoreCase("Y")) {
			System.setProperty("webdriver.edge.driver", ProjectConstant.Edge_DRIVER_FILE.getAbsolutePath());
			System.setProperty("org.freemarker.loggerLibrary", "none");
			if (driver == null) {
				DesiredCapabilities capability = DesiredCapabilities.edge();
			        EdgeOptions option = new EdgeOptions();
			        capability.setCapability("edgeOptions", option);
			        capability.setBrowserName("C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedgedriver.exe");
				
				if(getConfiguration().getURL().equals("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login"))
					capability.setCapability("ms:inPrivate", true);
				capability.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
				
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
				System.out.println("Edge Browser Is started");
				System.out.println("after setting options");
	

			}
		}else {
			System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_FILE_CI.getAbsolutePath());
			System.setProperty("org.freemarker.loggerLibrary", "none");
			if (driver == null) {
				ChromeOptions options = new ChromeOptions();
				String os = "";
				
				options.setPageLoadStrategy(PageLoadStrategy.NONE);
				options.addArguments("--no-sandbox");
				options.addArguments("--disable-gpu");
				os = System.getProperty("os.name");
				if(os.equals("Linux"))
				{
					options.setBinary("/usr/bin/google-chrome");
					options.addArguments("--headless");
				
					WebDriverManager.chromedriver().setup();
					driver = new ChromeDriver(options);
					System.out.println("Chrom Browser Is started");
					return driver;
				}
				else 
				{
				   options.addArguments("start-maximized");
		
				  options.setBinary("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
				}
				
				if(getConfiguration().getURL().equals("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login"))
					options.addArguments("--incognito");
				options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
				options.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
				options.setCapability(ChromeOptions.CAPABILITY, options);

				options.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver(options);
				System.out.println("Chrom Browser Is started");
				System.out.println("after setting options");

				

			}
		}
		

		
		return driver;
	}

	public static void close() {
		driver.close();
		driver = null;
	}

	public static void quit() {
		driver.quit();
		driver = null;
	}
}
