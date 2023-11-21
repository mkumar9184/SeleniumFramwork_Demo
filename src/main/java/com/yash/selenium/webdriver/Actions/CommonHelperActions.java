package com.yash.selenium.webdriver.Actions;



import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import com.aventstack.extentreports.ExtentTest;
import com.google.common.base.Function;
import com.yash.selenium.webdriver.constant.ProjectConstant;

public class CommonHelperActions {
	public static final int POLLING_REFRESH_IN_MILLISECONDS = 10;

	final static WebDriver driver = null;

	public static final int TIME_TO_WAIT_FOR_ELEMENT = 30;

	private static int RANDOM_NUMBER;

	// private static XSSFWorkbook workbook;
	// private static XSSFWorkbook workbook;
	/*
	 * TEXTBOX OPERATIONS
	 *
	 *
	 */

	public static void enterTextfield(WebDriver driver, By field, String value) {
		WebElement element = searchElement(field, driver);
		element.clear();
		
		element.sendKeys(value);
	}

	public static void enterTextfield(WebDriver driver, WebElement element, String value) {
		WebElement inputElement = searchByWebElement(element, driver);
		inputElement.clear();
		inputElement.sendKeys(value);
	}

	public static void setTextFieldValueBySelectAll(WebDriver driver, By by, String value) {
		CommonHelperActions.selectAllTextAndSetaValue(driver, by, value);
	}

	public static void eraseTextUsingBackSpace(WebDriver driver, By by) {
		CommonHelperActions.selectAllTextInAField(driver, by);
		CommonHelperActions.hitBackSpace(driver, by);
		CommonHelperActions.tabToNextElement(driver, by);
	}

	public static void killChromeProcesses() {
		String chromeDriverEXEprocessName = "ci".equals(ConfigurationActions.getServerToRun()) ?ProjectConstant.CHROME_FILE_NAME_CI
				: ProjectConstant.CHROME_FILE_NAME_LOCAL;
		try {
			Runtime.getRuntime().exec("TASKKILL /F /IM " + chromeDriverEXEprocessName);
			// System.out.println("THIS IS EXECUTED FOR " + chromeDriverEXEprocessName);
			Runtime.getRuntime().exec("TASKKILL /F /IM chrome.exe");

		} catch (Exception e) {

		}
	}

	/*
	 * BUTTON OPERATIONS
	 */

	public static void clickButton(WebDriver driver, By field) {
		try {
			WebElement element = searchElement(field, driver);
			CommonHelperActions.waitAndClickAnElement(driver, element);
			CommonHelperActions.waitForPrevivePage(driver);
		} catch (Exception e) {
			System.out.println("exeption occured while click on contacts accordion");
			System.out.println(e.getMessage());
		}

	}

	public static void clickButtonWebEle(WebDriver driver, WebElement field) {
		WebElement element = searchByWebElement(field, driver);
		try {
			element.click();
			CommonHelperActions.waitForPageToLoad(driver);
		} catch (Exception e) {

		}

	}

	/*
	 * DROPDOWN LIST OPERATIONS
	 */

	public static void selectByVisibleText(WebDriver driver, By field, String text) {
		WebElement element = searchElement(field, driver);
		Select select = new Select(element);
		select.selectByVisibleText(text);
		CommonHelperActions.waitForPageToLoad(driver);
	}

	public static void selectByIndex(WebDriver driver, By field, int index) {
		WebElement element = searchElement(field, driver);
		Select select = new Select(element);
		select.selectByIndex(index);
		CommonHelperActions.waitForPageToLoad(driver);
	}

	public static void selectByValue(WebDriver driver, By field, String value) {
		WebElement element = searchElement(field, driver);
		Select select = new Select(element);
		select.selectByValue(value);
		CommonHelperActions.waitForPageToLoad(driver);
	}

	public static List<String> getAllSelectOptions(By by) {
		WebElement dropdown = searchElement(by, driver);
		dropdown.click();
		List<WebElement> allOptions = dropdown.findElements(By.tagName("option"));
		List<String> allOptionsText = new ArrayList<>();
		for (WebElement thisOption : allOptions) {
			allOptionsText.add(thisOption.getText());
		}
		return allOptionsText;
	}

	public List<String> getAllSelectOptionsWithSpan(String element) {
		By by = By.id(element);
		By span = By.id(element + "-Span");
		WebElement dropdown = searchElement(by, driver);
		driver.findElement(span).click();
		List<WebElement> allOptions = dropdown.findElements(By.tagName("option"));
		List<String> allOptionsText = new ArrayList<>();
		for (WebElement thisOption : allOptions) {
			allOptionsText.add(thisOption.getText());
		}
		simulateTab(by);
		return allOptionsText;
	}

	public void selectDropDownByOptionCode(By by, String optionCode) {
		boolean isOptionPresent = false;
		WebElement dropdown = searchElement(by, driver);
		dropdown.click();
		List<WebElement> allOptions = dropdown.findElements(By.tagName("option"));
		while (!isOptionPresent) {
			for (WebElement thisOption : allOptions) {
				if (thisOption.getText().contains(optionCode)) {
					isOptionPresent = true;
					Select select = new Select(driver.findElement(by));
					select.selectByVisibleText(thisOption.getText());
					CommonHelperActions.waitForPageToLoad(driver);
					break;
				}
			}
		}
	}

	public void validateAllDropDownOptions(List<String> expectedOptions, By by, ExtentTest test)
			throws IOException, Exception {
		By optionsDropDown = by;
		List<String> foundOptions = getAllSelectOptions(optionsDropDown);

		for (String expectedOption : expectedOptions) {
			if (!foundOptions.contains(expectedOption)) {
				AdvanceActions.reportAssertTrue(driver, test, foundOptions.contains(expectedOption),
						"Missing item from drop drown options: " + expectedOption);
			}
		}
	}

	public static void selectItemFromList(WebDriver driver, String parentXpath, String tagname, String item) {
		boolean optionSelected = false;
		while (!optionSelected) {
			List<WebElement> options = driver.findElement(By.xpath(parentXpath)).findElements(By.tagName(tagname));
			try {
				for (WebElement e : options) {
					if (e.getText().equals(item)) {
						e.click();
						optionSelected = true;
						break;
					}
				}
			} catch (Exception e) {
				options = driver.findElement(By.xpath(parentXpath)).findElements(By.tagName(tagname));
			}
		}
	}

	public void simulateTab(final By by) {
		driver.findElement(by).sendKeys(Keys.TAB);
	}

	/*
	 * CHECKBOX OPERATIONS
	 */
	public static void toggleCheckBox(WebDriver driver, By field) {
		WebElement element = searchElement(field, driver);
		element.click();
		CommonHelperActions.waitForPageToLoad(driver);
	}

	public static void checkCheckBox(WebDriver driver, By by, boolean desiredCheckState) {
		WebElement checkBox = searchElement(by, driver);
		boolean checkedState = checkBox.getAttribute("checked") != null;
		if (desiredCheckState != checkedState) {
			checkBox.click();
			CommonHelperActions.waitForPageToLoad(driver);
		}
	}

	/*
	 * RADIOBUTTON OPERATIONS
	 */
	public static void selectRadioButton(WebDriver driver, By field) {
		WebElement element = searchElement(field, driver);
		element.click();
		CommonHelperActions.waitForPageToLoad(driver);
	}

	public static void selectRadioButtonFromGroup(WebDriver driver, int index, String groupName) {
		boolean bValue = false;
		List<WebElement> radiobuttonList = driver.findElements(By.name(groupName));
		WebElement oRadioButton = radiobuttonList.get(index);
		bValue = oRadioButton.isSelected();

		if (!bValue) {
			oRadioButton.click();
			CommonHelperActions.waitForPageToLoad(driver);
		}
	}

	public static void hoverOverElement(WebDriver driver, By by) {
		CommonHelperActions.waitForElementVisible(driver, by);
		WebElement we = driver.findElement(by);
		Actions action = new Actions(driver);
		action.moveToElement(we).build().perform();
	}

	public static void hoverOverWebElement(WebDriver driver, WebElement ele) {
		Actions action = new Actions(driver);
		action.moveToElement(ele).click().build().perform();
	}

	public static void hoverOverElementandClick(WebDriver driver, WebElement webE) {
		Actions action = new Actions(driver);
		action.moveToElement(webE).click().build().perform();
	}

	public static void hoverOverElementandClickAll(WebDriver driver, WebElement webE) {
		webE.click();
		String selectAll = Keys.CONTROL + "A";
		webE.sendKeys(selectAll);
	}

	// through ClipBoard Copy and Paste Functionality
	public static void enterTextActions(WebDriver driver, WebElement webE, String str) {
		StringSelection stringSelection = new StringSelection(str);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);
		String paste = Keys.chord(Keys.CONTROL, "v");
		webE.sendKeys(paste);
	}

	public static void clickAndHoldOnAnElement(WebDriver driver, WebElement element) {
		Actions action = new Actions(driver);
		action.clickAndHold(element).perform();
	}

	public static void dragAndDropOfElements(WebDriver driver, WebElement source, WebElement destination) {
		Actions action = new Actions(driver);
		action.dragAndDrop(source, destination).build().perform();
	}

	/*
	 * Link OPERATIONS
	 */
	public static void clickOnLinkByLinkText(String linkText) {
		By searchLink = By.linkText(linkText);
		WebElement searchLinkField = searchElement(searchLink, driver);
		searchLinkField.click();
		CommonHelperActions.waitForPageToLoad(driver);
	}

	public static void clickOnLink(WebDriver driver, By element) {
		By link = element;
		WebElement searchLinkField = searchElement(link, driver);
		searchLinkField.click();
		CommonHelperActions.waitForPageToLoad(driver);
	}

	public static void clickOnLinkByPartialLinkText(String partialLinkText) {
		driver.findElement(By.partialLinkText(partialLinkText));
	}

	public static boolean isLinkValid(By link) {
		String linkURL = driver.findElement(link).getAttribute("href");
		try {
			HttpURLConnection.setFollowRedirects(false);
			HttpURLConnection con = (HttpURLConnection) new URL(linkURL).openConnection();
			con.setRequestMethod("HEAD");
			return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/*
	 * ALERT OPERATIONS
	 */

	public static String waitForAlertAndAccept(WebDriver driver) {
		int itr = 0;
		String alertText = null;
		while (itr++ < 5) {
			try {
				Alert alert = driver.switchTo().alert();
				alertText = alert.getText();
				alert.accept();
				break;
			} catch (NoAlertPresentException e) {
				try {
					Thread.sleep(1000);
					continue;
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
		return alertText;
	}

	public static String waitForAlertAndReject(WebDriver driver) {
		int itr = 0;
		String alertText = null;
		while (itr++ < 5) {
			try {
				Alert alert = driver.switchTo().alert();
				alertText = alert.getText();
				alert.dismiss();
				break;
			} catch (NoAlertPresentException e) {
				try {
					Thread.sleep(1000);
					continue;
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
		return alertText;
	}

	/*
	 * UTILITY OPERATIONS
	 */

	public static void closeCurrentWindow(WebDriver driver) {
		driver.close();
	}

	public void deleteTextFieldValue(By by) {
		WebElement field = searchElement(by, driver);
		field.sendKeys(Keys.DELETE);
	}

	public String executeJavaScript(String js) {
		return executeJavaScript(js, "");
	}

	public String executeJavaScript(String js, String args) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		String data = (String) executor.executeScript(js, args);
		return data;
	}

	public static String getCurrentWindowHandle(WebDriver driver) {
		return driver.getWindowHandle();
	}

	public static void switchToWindowHandle(WebDriver driver, String windowHandle) {
		driver.switchTo().window(windowHandle);
	}

	public static boolean switchBetweenWindows(WebDriver driver, String expectedWindowTitle) {
		Set<String> handleString = driver.getWindowHandles();
		for (String windowHandle : handleString) {
			String windowTitle = driver.switchTo().window(windowHandle).getTitle();
			if (windowTitle.equals(expectedWindowTitle)) {
				return true;
			}
		}
		return false;
	}

	public static void switchToAnotherWindowByClosingCurrentWindow(WebDriver driver) {
		Set<String> allHandles = driver.getWindowHandles();
		String thisWindowHandle = driver.getWindowHandle();
		if (allHandles.size() > 1) {
			for (String handle : allHandles) {
				if (!thisWindowHandle.equals(handle)) {
					driver.close();
					driver.switchTo().window(handle);
				}
			}
		}
	}

	public static void switchToAnotherWindowWillCloseWindow(WebDriver driver) {
		Set<String> allHandles = driver.getWindowHandles();
		String thisWindowHandle = driver.getWindowHandle();
		if (allHandles.size() > 1) {
			for (String handle : allHandles) {
				if (!thisWindowHandle.equals(handle)) {
					try {
						driver.switchTo().window(thisWindowHandle);

						driver.close();
						driver.switchTo().window(handle);

					} catch (Exception e) {
						System.out.println(e.getMessage());
					}

				}
			}
		}

	}

	public static void switchToAnotherWindow(WebDriver driver) {
		Set<String> allHandles = driver.getWindowHandles();
		String thisWindowHandle = driver.getWindowHandle();
		if (allHandles.size() > 1) {
			for (String handle : allHandles) {
				if (!thisWindowHandle.equals(handle)) {
					driver.switchTo().window(handle);
				}
			}
		}
		CommonHelperActions.waitForPageToLoad(driver);
		CommonHelperActions.waitForPrevivePage(driver);
	}

	public static String getRandomString(int length) {
		RANDOM_NUMBER = 51;
		char[] letters = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
				's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
				'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
		Random randomNumberGenerator = new Random();
		char[] stringArr = new char[length];
		int thisRandomNumber = 0;
		for (int itr = 0; itr < length; itr++) {
			thisRandomNumber = randomNumberGenerator.nextInt(RANDOM_NUMBER);
			stringArr[itr] = letters[thisRandomNumber];
		}
		return new String(stringArr);
	}

	public static String getRandomNumber(int length) {
		RANDOM_NUMBER = 9;
		char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		Random randomNumberGenerator = new Random();
		char[] stringArr = new char[length];
		int thisRandomNumber = 0;
		for (int itr = 0; itr < length; itr++) {
			thisRandomNumber = randomNumberGenerator.nextInt(RANDOM_NUMBER);
			stringArr[itr] = digits[thisRandomNumber];
		}
		return new String(stringArr).replaceFirst("^0+(?!$)", "");
	}

	public static String getRandomAlphaNumericString(int length) {
		RANDOM_NUMBER = 61;
		char[] letters = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
				's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
				'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7',
				'8', '9' };
		Random randomNumberGenerator = new Random();
		char[] stringArr = new char[length];
		int thisRandomNumber = 0;
		for (int itr = 0; itr < length; itr++) {
			thisRandomNumber = randomNumberGenerator.nextInt(RANDOM_NUMBER);
			stringArr[itr] = letters[thisRandomNumber];
		}
		return new String(stringArr);
	}

	public static String getRandomDecimalNumber() {
		String s = null;
		Random generator = new Random();
		double number = generator.nextDouble() * 9999.99;
		s = String.format("%.2f", number);
		s = s.indexOf(".") < 0 ? s : s.replaceAll("0*$", "").replaceAll("\\.$", "");
		return s;
	}

	public static String getRandomPhoneNumber() {
		return ("(".concat(CommonHelperActions.getRandomNumber(3)).concat(")")
				.concat(CommonHelperActions.getRandomNumber(3)).concat("-")
				.concat(CommonHelperActions.getRandomNumber(4)));
	}

	public static String getRandomYear() {
		int RANDOM_NUMBER = 20;
		String[] years = new String[20];
		int year = 1999;
		for (int i = 0; i < 20; i++) {
			years[i] = String.valueOf(year);
			year++;
		}
		Random randomNumberGenerator = new Random();
		int randomNumber = randomNumberGenerator.nextInt(RANDOM_NUMBER);
		return years[randomNumber];
	}

	public static String getTodaysDate(String dateFormat) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		DateFormat df = new SimpleDateFormat(dateFormat);
		return df.format(timestamp);
	}

	public static String getYesterdaysDate(String dateFormat) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis() - 1);
		DateFormat df = new SimpleDateFormat(dateFormat);
		return df.format(timestamp);
	}

	public static void clickSomeWebElement(WebDriver driver, WebElement element) {
		boolean elementClickable = false;
		while (!elementClickable) {
			try {
				elementClickable = true;
				CommonHelperActions.waitAndClickAnElement(driver, element);
		//		element.click();
			} catch (Exception e) {
				elementClickable = false;
			}
		}
	}

	public static void clickElementIfClickable(WebDriver driver, WebElement element) {
		boolean elementClickable = false;
		while (!elementClickable) {
			try {
				JavascriptExecutor je = (JavascriptExecutor) driver;
				je.executeScript("arguments[0].click()", element);
				elementClickable = true;
			} catch (StaleElementReferenceException e) {
				clickElementIfClickable(driver, element);
			}
		}
	}

	/**
	 * Sometime it happens while automating the angular app, view gets loaded
	 * entirely but performing any action on that view fails the test. This could
	 * happen because angular $http calls are still pending in backend. We can have
	 * explicit wait in this way to ensure that angular has made all the $http
	 * calls. Wait until angular finishes the $http calls while loading the view
	 */
	public static void waitForAngPagetoLoad(WebDriver driver) {
		try {
			final String javaScriptToLoadAngular = "var injector = window.angular.element('body').injector();"
					+ "var $http = injector.get('$http');" + "return ($http.pendingRequests.length === 0)";

			ExpectedCondition<Boolean> pendingHttpCallsCondition = new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver driver) {
					return ((JavascriptExecutor) driver).executeScript(javaScriptToLoadAngular).equals(true);
				}
			};
			WebDriverWait wait = new WebDriverWait(driver, 90);
			wait.until(pendingHttpCallsCondition);
		} catch (Exception e) {

		}
	}

	/**
	 * @author tbkc0cx - Anmol Koul - Use this method compulsory for any element
	 *         click operations(textbox, checkbox, radiobox, dropdown) and
	 *         enhance/add the steps accordingly as per any new functionality
	 *         upgrade
	 *
	 */

	public static boolean waitAndClickAnElement(WebDriver driver, WebElement element) {
		boolean result = false;
		int attempts = 0;
		while (attempts < 50) {
			try {
				if (element.isDisplayed() || element.isEnabled()) {
					element.click();
					result = true;
					break;
				}
			} catch (StaleElementReferenceException | InvalidElementStateException e) {
				try {
					JavascriptExecutor je = (JavascriptExecutor) driver;
					je.executeScript("arguments[0].click()", element);
					result = true;
					break;
				} catch (StaleElementReferenceException | InvalidElementStateException e1) {
				}
			}
			CommonHelperActions.staticWait(200);
			attempts++;
		}
		if (result == false) {
			System.out.println(element + ": Element is missing from the DOM");
		}
		CommonHelperActions.waitForPrevivePage(driver);
		return result;
	}

	public static String getTextFromField(WebDriver driver, By by) {
		List<WebElement> element = driver.findElements(by);
		return element.get(0).getAttribute("value").trim();
	}

	public static String getTextFromField(WebElement ele) {
		return ele.getText().trim();
	}

	public static String getValueFromField(WebDriver driver, By by) {
		WebElement element = searchElement(by, driver);
		return element.getAttribute("value");
	}

	public static WebElement searchElement(final By locator, final WebDriver driver) {
		@SuppressWarnings("deprecation")
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(60, TimeUnit.SECONDS)
				.pollingEvery(250, TimeUnit.MILLISECONDS).ignoring(Exception.class);
		WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
			@Override
			public WebElement apply(WebDriver driver) {
				return driver.findElement(locator);
			}
		});
		return foo;
	}

	public static WebElement searchByWebElement(final WebElement webElement, final WebDriver driver) {
		@SuppressWarnings("deprecation")
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(250, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class).ignoring(NullPointerException.class);
		WebElement findelem = wait.until(new Function<WebDriver, WebElement>() {
			@Override
			public WebElement apply(WebDriver driver) {
				return webElement;
			}
		});
		return findelem;
	};

	public static List<WebElement> searchElementList(final String tagName, final WebDriver driver) {
		@SuppressWarnings("deprecation")
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(10, TimeUnit.SECONDS)
				.pollingEvery(250, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class);
		List<WebElement> elements = wait.until(new Function<WebDriver, List<WebElement>>() {
			@Override
			public List<WebElement> apply(WebDriver driver) {
				return driver.findElements(By.tagName(tagName));
			}
		});
		return elements;
	};

	public static WebElement findLinkOnPageAndReturn(WebDriver driver, String linkText) {
		List<WebElement> links = searchElementList("a", driver);

		for (WebElement link : links) {
			if (linkText.equals(link.getText())) {
				return link;
			}
		}
		return null;
	}

	public static WebElement searchElementByClassName(final String className, final WebDriver driver) {
		@SuppressWarnings("deprecation")
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(10, TimeUnit.SECONDS)
				.pollingEvery(250, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class);
		WebElement findelem = wait.until(new Function<WebDriver, WebElement>() {
			@Override
			public WebElement apply(WebDriver driver) {
				return driver.findElement(By.className(className));
			}
		});
		return findelem;
	};

	public static boolean FindElementFromListOnTextAndClick(WebDriver driver, List<WebElement> list, String text) {
		for (WebElement ele : list) {
			if (StringUtils.equals(text, ele.getText())) {
				ele.click();
				return true;
			}
		}
		return false;
	}

	public static boolean findDisplayedElementFromListAndClick(WebDriver driver, List<WebElement> list) {
		if (list.size() > 0) {
			for (WebElement element : list) {
				if (element.isDisplayed()) {
					clickSomeWebElement(driver, element);
					return true;
				}
			}
		}
		return false;
	}

	public static Capabilities getCapabilities(WebDriver driver) {
		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		return cap;
	}

	public static String getInnerHTMLOfElement(WebDriver driver, By by) {
		return driver.findElement(by).getAttribute("innerHTML");
	}

	/*
	 * SYNC OPERATIONS
	 */

	public static void staticWait(int i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void waitForElementAndClick(WebDriver driver, By by) {
		new WebDriverWait(driver, TIME_TO_WAIT_FOR_ELEMENT).until(ExpectedConditions.elementToBeClickable(by));
		WebElement element = searchElement(by, driver);
		element.click();
		CommonHelperActions.waitForPageToLoad(driver);
	}

	public static void waitForElementAndClick(WebDriver driver, WebElement ele) {
		new WebDriverWait(driver, TIME_TO_WAIT_FOR_ELEMENT).until(ExpectedConditions.elementToBeClickable(ele));
		WebElement element = searchByWebElement(ele, driver);
		element.click();
		CommonHelperActions.waitForPageToLoad(driver);
	}

	public static void waitForElementVisible(WebDriver driver, By by) {
		new WebDriverWait(driver, TIME_TO_WAIT_FOR_ELEMENT).until(ExpectedConditions.visibilityOfElementLocated(by));
	}

	public static void waitForElementVisible(WebDriver driver, WebElement ele) {
		new WebDriverWait(driver, TIME_TO_WAIT_FOR_ELEMENT).until(ExpectedConditions.visibilityOf(ele));
	}

	public static void waitForElementInVisible(WebDriver driver, WebElement ele) {
		new WebDriverWait(driver, TIME_TO_WAIT_FOR_ELEMENT).until(ExpectedConditions.invisibilityOf(ele));
	}
	
	public static void waitForSelectOption(WebDriver driver, By by, String value) {
		Select select;
		List<WebElement> options;
		boolean found = false;

		while (!found) {
			select = new Select(driver.findElement(by));
			options = select.getOptions();
			for (WebElement option : options) {
				if (value.equals(option.getText())) {
					found = true;
				}
			}
		}
	}

	public static void waitForElementPresent(WebDriver driver, By by) {
		boolean found = false;
		int iterations = 0;
		while (!found) {
			iterations++;
			try {
				Thread.sleep(500);
				if (verifyElementPresent(driver, by)) {
					found = true;
				} else {
					if (iterations > 20) {
						found = true;
					}
				}
			} catch (Exception e) {

			}
		}
	}

	public static void waitForElementNotDisplayed(WebDriver driver, By by) {
		int iterations = 0;
		boolean elementNotPresent = false;
		while (!elementNotPresent) {
			iterations++;
			try {
				Thread.sleep(500);
				if (!verifyElementDisplayed(driver, by)) {
					elementNotPresent = true;
					Thread.sleep(500);
				} else if (iterations > 50) {
					elementNotPresent = true;
					Thread.sleep(500);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void waitForElementDisplayed(WebDriver driver, By by) {
		int iterations = 0;
		boolean elementPresent = false;
		while (!elementPresent) {
			iterations++;
			try {
				Thread.sleep(500);
				if (verifyElementDisplayed(driver, by)) {
					elementPresent = true;
					Thread.sleep(500);
					// Thread.sleep(500);
				} else if (iterations > 100) {
					elementPresent = true;
					Thread.sleep(500);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/*
	 * public static void waitForPrevivePage(WebDriver driver) { try { By
	 * loadingImage = By.id("loading"); boolean isHidden = false; String styleattr =
	 * ""; CommonHelperActions.staticWait(500); while (!isHidden) { styleattr =
	 * driver.findElement(loadingImage).getAttribute("style");
	 *
	 * if (!"display: none;".equals(styleattr)) { isHidden = true;
	 * CommonHelperActions.staticWait(500); } else {
	 * CommonHelperActions.staticWait(500); } }
	 *
	 * } catch (Exception e) { } }
	 */

	public static void WaitforAttributeChange(WebDriver driver, By locator, String Attribute, String Value) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.attributeContains(locator, Attribute, Value));
	}

	public static void waitForPrevivePage(WebDriver driver) {
		try {
			//By loadingImage = By.xpath("//*[@id='loading']/div");
			By loadingImage = By.xpath("//div[@id='spinner']//*[@class='spinner-wrapper']//span");
			WebElement waitClass = driver.findElement(loadingImage);
			boolean isDisplayed = false;
			//if (driver.getCurrentUrl().contains("https://vision-qual1.deere.com/ang/#/")) {
			if (driver.getCurrentUrl().contains("https://advance-devl.deere.com/")) {
				//CommonHelperActions.waitForElementDisplayed(driver, loadingImage);
				int itr = 0;
				CommonHelperActions.staticWait(500);
				while (!isDisplayed && itr < 100) {
					itr++;
					CommonHelperActions.staticWait(500);
					if (!waitClass.isDisplayed()) {
						isDisplayed = true;
						if (isDisplayed == true) {
							CommonHelperActions.staticWait(1000);
							if (waitClass.isDisplayed()) {
								isDisplayed = false;
							}
						}
					}
				}
			}
			/*
			 * This below code is invalid for new Angular UI wait for Loading Bar(below part
			 * works only for old UI)
			 */

			else {
				loadingImage = By.id("loading");
				int itr = 0;
				CommonHelperActions.staticWait(500);
				while (!isDisplayed && itr < 60) {
					itr++;
					CommonHelperActions.staticWait(1000);
					String styleattr = driver.findElement(loadingImage).getAttribute("style");
					if ("display: none;".equals(styleattr) && styleattr != null) {
						CommonHelperActions.staticWait(500);
						isDisplayed = true;
						if (isDisplayed == true) {
							CommonHelperActions.staticWait(1000);
							if (waitClass.isDisplayed()) {
								isDisplayed = false;
							}
						} else {
							CommonHelperActions.staticWait(500);
						}
					}
				}
			}
		} catch (Exception e) {
		}
	}

	public static void waitForElementClickeable(WebDriver driver, By by) {
		WebElement element = searchElement(by, driver);
		new WebDriverWait(driver, TIME_TO_WAIT_FOR_ELEMENT).until(ExpectedConditions.elementToBeClickable(element));
	}

	public static WebElement fleuntwaitForElementClickeable(WebDriver driver, By locator) {

		@SuppressWarnings("deprecation")
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(60, TimeUnit.SECONDS)
				.pollingEvery(250, TimeUnit.MILLISECONDS).ignoring(Exception.class);

		WebElement foo = wait.until(ExpectedConditions.elementToBeClickable(locator));

		return foo;

	}

	public static void waitForElementClickeable(WebDriver driver, WebElement element) {
		WebElement elementToBeClicked = searchByWebElement(element, driver);
		new WebDriverWait(driver, TIME_TO_WAIT_FOR_ELEMENT)
				.until(ExpectedConditions.elementToBeClickable(elementToBeClicked));
	}

	public static void waitForPageToLoad(WebDriver driver) {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
		Wait<WebDriver> wait = new WebDriverWait(driver, 60);
		wait.until(expectation);
	}

	public static boolean waitForTextPresentAtSpecificLocation(WebDriver driver, WebElement uploadedSucessMessage, String textToVerify) {
		int iteration = 0;
		boolean textFound = false;

		while (!textFound) {
			CommonHelperActions.staticWait(500);
			iteration++;
			if (verifyTextInSpecificLocation(uploadedSucessMessage, textToVerify)) {
				textFound = true;
				return true;
			} else if (iteration > 10) {
				return false;
			}
		}
		return false;
	}

	/*
	 * KEYBOARD FUNCTIONS
	 */

	public static void hitBackSpace(WebDriver driver, By by) {
		WebElement element = CommonHelperActions.searchElement(by, driver);
		element.sendKeys(Keys.BACK_SPACE);
	}

	public static void hitEscape(WebDriver driver, By by) {
		WebElement element = CommonHelperActions.searchElement(by, driver);
		element.sendKeys(Keys.ESCAPE);
	}

	public static void tabToNextElement(WebDriver driver, By by) {
		WebElement element = CommonHelperActions.searchElement(by, driver);
		element.sendKeys(Keys.TAB);
	}

	public static void selectAllTextInAField(WebDriver driver, By by) {
		WebElement element = searchElement(by, driver);
		element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
	}

	public static void selectAllTextAndSetaValue(WebDriver driver, By by, String value) {
		WebElement element = searchElement(by, driver);
		element.sendKeys(Keys.chord(Keys.CONTROL, "a"), value);
	}

	public static void sendKeyboardSequence(WebDriver driver, By by, String value) {
		WebElement element = searchElement(by, driver);
		char[] charArr = value.toCharArray();

		for (char c : charArr) {
			element.sendKeys(String.valueOf(c));
		}
	}

	public static void switchToWindowBasedonPageTitle(String Title, WebDriver driver) {
		Set<String> windowHandles = driver.getWindowHandles();
		for (String s : windowHandles) {
			driver.switchTo().window(s);
			if (driver.getTitle().equalsIgnoreCase(Title)) {
				break;
			}
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

	public static void triggerElementOnBlur(WebElement ele) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].blur();", ele);
	}

	/*
	 * VERIFICATION OPERATIONS
	 */
	public static boolean verifyDropDownSelection(WebDriver driver, By dropDown, String textVerify) {
		return driver.findElement(dropDown).getText().equals(textVerify);
	}

	public static boolean verifyTextInSpecificLocation(WebElement uploadedSucessMessage, String textVerify) {
		return uploadedSucessMessage.getText().contains(textVerify);
	}

	/*  
	 * Created by za635kx (suraj)
	 * */
	public static String returnText(WebDriver driver,By by) 
	{
		return driver.findElement(by).getText();
	}

	
	public static boolean verifyTextOnThePage(WebDriver driver, String textToVerify) {
		return driver.getPageSource().contains(textToVerify);

	}

	public static boolean verifyCheckBoxChecked(WebDriver driver, By byClass) {
		return driver.findElement(byClass).getAttribute("checked") != null;
	}

	public static boolean verifyElementPresent(WebDriver driver, By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean verifyElementHasElement(WebElement ele, By by) {
		try {
			ele.findElement(by);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean verifyElementNotPresent(WebDriver driver, By by) {
		try {
			driver.findElement(by);
			return false;
		} catch (NoSuchElementException e) {
			return true;
		}
	}

	public static boolean verifyElementDisplayed(WebDriver driver, By by) {
		if (verifyElementPresent(driver, by)) {
			WebElement ele = driver.findElement(by);
			return ele.isDisplayed();
		} else {
			return false;
		}
		/*
		 * try { WebElement element = searchElement(by, driver); if
		 * (!element.isDisplayed()) { return true; } else { return false; } } catch
		 * (NoSuchElementException e) { Reporter.log("Element: " + by +
		 * " is NOT found or incorrect!"); return false; }
		 */
	}

	public static boolean verifyElementNotDisplayed(WebDriver driver, By by) {
		try {
			WebElement element = searchElement(by, driver);
			if (!element.isDisplayed()) {
				return true;
			} else {
				return false;
			}
		} catch (NoSuchElementException e) {
			Reporter.log("Element: " + by + " is NOT found or incorrect!");
			return false;
		}
	}

	public static boolean verifyTargetContainsText(String target, String substring) {
		return target.contains(substring);
	}

	public static boolean isElementEnabled(WebDriver driver, By field) {
		return searchElement(field, driver).isEnabled();
	}

	/*
	 * public static XSSFSheet getWorkbookSheet(File file, int index ) throws
	 * Exception { FileInputStream fin = new FileInputStream(file); workbook = new
	 * XSSFWorkbook(fin); XSSFSheet worksheet = workbook.getSheetAt(index); return
	 * worksheet; }
	 */

	/*
	 * public static XSSFSheet getWorkbookSheet(File file, int index ) throws
	 * Exception { FileInputStream fin = new FileInputStream(file); workbook = new
	 * XSSFWorkbook(fin); XSSFSheet worksheet = workbook.getSheetAt(index); return
	 * worksheet; }
	 */

	public static void writeToFileAndSave(String text, String fileName) throws IOException {
		BufferedWriter bw = null;
		FileWriter fw = null;

		fw = new FileWriter(new File(fileName));
		bw = new BufferedWriter(fw);
		bw.write(text);

		System.out.println("Done");

		try {

			if (bw != null)
				bw.close();

			if (fw != null)
				fw.close();

		} catch (IOException ex) {

			ex.printStackTrace();

		}

	}

	public static void createNewFile(File filetoCreate) {
		try {

			File file = new File(filetoCreate.getAbsolutePath());

			if (file.exists()) {
				file.delete();
			}
			file.createNewFile();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String incrementDateAndReturn(String date, int count, boolean isWeekendExcluded) { // Start
																										// date
		HashMap<String, String> months = new HashMap<>();
		months.put("Jan", "01");
		months.put("Feb", "02");
		months.put("Mar", "03");
		months.put("Apr", "04");
		months.put("May", "05");
		months.put("Jun", "06");
		months.put("Jul", "07");
		months.put("Aug", "08");
		months.put("Sep", "09");
		months.put("Oct", "10");
		months.put("Nov", "11");
		months.put("Dec", "12");
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String sourcedate = date.split(" ")[0].concat("/").concat(months.get(date.split(" ")[1])).concat("/")
				.concat(date.split(" ")[2]);
		Date date1 = null;
		int itr = 0;
		int counttoIncrement = 1;
		count--;
		while (itr < count) {
			try {
				date1 = sdf.parse(sourcedate);
				String day = date1.toString().split(" ")[0];
				if (isWeekendExcluded) {
					if ("Fri".equals(day)) {
						counttoIncrement = 3;
					} else if ("Sat".equals(day)) {
						counttoIncrement = 2;
					} else {
						counttoIncrement = 1;
					}
				}
				c.setTime(date1);
				c.add(Calendar.DATE, counttoIncrement); // number of days to add
				date = sdf.format(c.getTime());
				sourcedate = date.toString();
				itr++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String month = null;
		Set<String> keys = months.keySet();
		for (String key : keys) {
			if (sourcedate.split("/")[1].equals(months.get(key))) {
				month = key;
			}
		}
		return (sourcedate.split("/")[0]).concat(" ").concat(month).concat(" ").concat((sourcedate).split("/")[2]);

	}

	public static XSSFSheet readFromExcelSheet(String path, String Sheet) {
		XSSFSheet sheet = null;
		try {
			InputStream excelToRead = new FileInputStream(path);
			XSSFWorkbook wb = new XSSFWorkbook(excelToRead);
			sheet = wb.getSheet(Sheet);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sheet;
	}
	
	
   public static void clickThroughActionClass(WebElement element) {
	   Actions act =  new Actions(driver);
		act.moveToElement(element).click().build().perform();	
   }
   
   
   public void fileUpload(String filepath) throws Exception {
		CommonHelperActions.staticWait(3000);
		try {
		Robot robot = new Robot();

		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		CommonHelperActions.staticWait(500);
		StringSelection selection = new StringSelection(filepath);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
		robot.setAutoDelay(1000);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		CommonHelperActions.staticWait(500);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_V);
		robot.setAutoDelay(1000);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		}
		catch(Exception e) {
			System.out.println("Error occurred while uploading attachement");
//			System.out.print(e.printStackTrace());
		}
	}
}
