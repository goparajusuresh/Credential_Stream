package utility;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.lf5.LogLevel;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.internal.AbstractParallelWorker.Arguments;

public class SeleniumTools extends TestInit {

	public static  WebDriver driver;

	static WebElement element;
	static Actions action;
	static LoggerUtil loggerUtil = new LoggerUtil(SeleniumTools.class);
	static int screenshotCounter = 1;
	
	public SeleniumTools(WebDriver driver) {
		SeleniumTools.driver = driver;

	}
	private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();
	
	public static void setWebDriver(WebDriver driver) {
		webDriver.set(driver);
	}
	
	public static WebDriver getDriver() {
		return webDriver.get();
	}
	
	//@Parameters({"browserName"}) 
	public static void openBrowser(String browserName) throws Exception {
		try {
			if (browserName.equalsIgnoreCase("Firefox")) {

				try {
					loggerUtil.entryLogger("openFirefoxBrowser");
					System.setProperty("webdriver.gecko.driver", "src/main/resources/Drivers/geckodriver.exe");
					driver = new FirefoxDriver();
					driver.manage().window().maximize();

				} catch (Exception e) {
					loggerUtil.LogSevereMessage(LogLevel.SEVERE, "openFirefoxBrowser", "unable to open firefox browser",
							e);
				} finally {
					loggerUtil.exitLogger("openFirefoxBrowser");
				}
			} else if (browserName.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver", "src/main/resources/Drivers/chromedriver.exe");
				try {
					loggerUtil.entryLogger("openChromeBrowser");
					ChromeOptions options = new ChromeOptions();
					options.addArguments("chrome.switches", "--disable-extensions");
					options.addArguments("disable-infobars");
					options.addArguments("--no-sandbox");
					//options.addArguments("--headless");
					//options.setHeadless(true);		
					driver = SeleniumTools.getDriver();
					
					// System.out.println("Thread id = " + Thread.currentThread().getId());
					// System.out.println("Hashcode of webDriver instance = " +
					// SeleniumTools.getDriver().hashCode());
					// driver = new ChromeDriver(options);
					// SessionId sid = ((RemoteWebDriver)driver).getSessionId();
					driver.manage().window().maximize();
					driver.manage().deleteAllCookies();
					pNode.info("Chrome Browser is opened");
				} catch (Exception e) {
					loggerUtil.LogSevereMessage(LogLevel.SEVERE, "openChromeBrowser", "unable to open chrome browser",
							e);
				} finally {
					loggerUtil.exitLogger("openChromeBrowser");
				}
			} else if (browserName.equalsIgnoreCase("IE")) {
				try {
					loggerUtil.entryLogger("openIEBrowser");
					System.setProperty("webdriver.ie.driver", "src/main/resources/Drivers/IEDriverServer.exe");
					DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
					capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
							true);
					capabilities.setCapability("requireWindowFocus", true);
					capabilities.setCapability("nativeEvents", false);
					driver = new InternetExplorerDriver();
					driver.manage().window().maximize();
				} catch (Exception e) {
					loggerUtil.LogSevereMessage(LogLevel.SEVERE, "openIEBrowser", "unable to open Internet explorer",
							e);
				} finally {
					loggerUtil.exitLogger("openIEBrowser");
				}
			}
		} catch (WebDriverException e) {
			System.out.println(e.getMessage());
		}
	}

	public static By locatorValue(String locatorTpye, String value) {
		By by;
		switch (locatorTpye) {
		case "id":
			by = By.id(value);
			break;
		case "name":
			by = By.name(value);
			break;
		case "xpath":
			by = By.xpath(value);
			break;
		case "css":
			by = By.cssSelector(value);
			break;
		case "linkText":
			by = By.linkText(value);
			break;
		case "partialLinkText":
			by = By.partialLinkText(value);
			break;
		default:
			by = null;
			break;
		}
		return by;
	}

	public static void fillData(String locatorType, String value, String text) {
		WebDriver driver = SeleniumTools.getDriver();
		try {
			By locator;
			locator = locatorValue(locatorType, value);
			loggerUtil.entryLogger("fillDataByID");
			explicitWait(locatorType, value);
			WebElement element = driver.findElement(locator);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			CoreUtil.imSleepy(1000);
			element.sendKeys(text);
			loggerUtil.LogMessage(LogLevel.DEBUG, "fillData", "Data filled: " + text);
		} catch (NoSuchElementException e) {
			try {
				loggerUtil.LogSevereMessage(LogLevel.ERROR, "fillData", "unable to fill data", e);
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		} finally {
			loggerUtil.exitLogger("fillData");
		}
	}

	public static void clickOnObject(String locatorType, String value) throws Exception {
		WebDriver driver = SeleniumTools.getDriver();
		try {
			driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

			By locator;
			locator = locatorValue(locatorType, value);
			loggerUtil.entryLogger("clickObject");
			explicitWait(locatorType, value);
			WebElement elementUI = driver.findElement(locator);
			if (elementUI != null && elementUI.isEnabled() && elementUI.isDisplayed()) {
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elementUI);
				Thread.sleep(2000);
				elementUI.click();
				loggerUtil.LogMessage(LogLevel.INFO, "clickOnObject At : ", value);
				pNode.info("Clicked on object At : " + value);
			} else {
				loggerUtil.LogMessage(LogLevel.WARN, "clickOnObject At", "Element " + value + " not found");
				pNode.info("Element " + value + " Not found");
			}
		} catch (NoSuchElementException e) {
			loggerUtil.LogSevereMessage(LogLevel.ERROR, "clickObject", "unable to click on object", e);
			//pNode.info(e);
			pNode.fail(e);
		} finally {
			loggerUtil.exitLogger("clickObject");
		}
	}

	public static void scrollToElementByXpath(String locatorType, String value) {
		WebDriver driver = SeleniumTools.getDriver();
		
		By locator;
		locator = locatorValue(locatorType, value);
		explicitWait(locatorType, value);
		((JavascriptExecutor) SeleniumTools.driver).executeScript("arguments[0].scrollIntoView();",
				SeleniumTools.driver.findElement(locator));
	}

	public static void scrollToElementAndClick(String locatorType, String value) throws Exception  {
		WebDriver driver = SeleniumTools.getDriver();
		try {
			By locator;
			locator = locatorValue(locatorType, value);
			loggerUtil.entryLogger("scrollToElementAndClick");
			explicitWait(locatorType, value);
			WebElement elementUI = driver.findElement(locator);
			if (elementUI != null && elementUI.isEnabled() && elementUI.isDisplayed()) {
				Actions actions = new Actions(driver);
				actions.moveToElement(elementUI).click().build().perform();
				pNode.info("scrollToElementAndClick At : " + value);
				loggerUtil.LogMessage(LogLevel.INFO, "scrollToElementAndClick At : ", value);
			} else {
				loggerUtil.LogMessage(LogLevel.WARN, "scrollToElementAndClick At", "Element " + value + " not found");
			}

		} catch (NoSuchElementException e) {
			loggerUtil.LogSevereMessage(LogLevel.ERROR, "scrollToElementAndClick",
					"unable scrollToElementAndClick on object", e);
			pNode.fail(e);
		} finally {
			loggerUtil.exitLogger("clickObject");
		}

	}

	public static void ScrollTo(String locatorType, String element1) {
		WebDriver driver = SeleniumTools.getDriver();
		try {
			By locator;
			locator = locatorValue(locatorType, element1);
			Actions action = new Actions(driver);
			WebElement element = driver.findElement(locator);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			Thread.sleep(500);
			action.doubleClick(element).perform();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void PressTabwithValue(String Value) {
		WebDriver driver = SeleniumTools.getDriver();
		try {

			Actions action = new Actions(driver);
			action.sendKeys(Keys.TAB).build().perform();
			CoreUtil.imSleepy(2000);
			action.sendKeys(Value).build().perform();
			//CoreUtil.imSleepy(2000);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void EnterValuePressTab(String Value) {
		WebDriver driver = SeleniumTools.getDriver();
		try {

			Actions action = new Actions(driver);
			action.sendKeys(Value).build().perform();
			CoreUtil.imSleepy(2000);
			//action.sendKeys(Keys.TAB).build().perform();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// press Key on page
	public static void PressKey(String keyName) {
		WebDriver driver = SeleniumTools.getDriver();
		Actions action = new Actions(driver);
		switch (keyName) {
		case "ESCAPE":
			CoreUtil.imSleepy(5000);
			action.sendKeys(Keys.chord(Keys.ESCAPE)).build().perform();
			CoreUtil.imSleepy(2000);
			break;
		case "TAB":
			CoreUtil.imSleepy(5000);
			action.sendKeys(Keys.TAB).build().perform();
			CoreUtil.imSleepy(2000);
			break;
		case "Ctrl+A":
			CoreUtil.imSleepy(3000);
			action.sendKeys(Keys.chord(Keys.CONTROL, "a")).build().perform();
			CoreUtil.imSleepy(2000);
			break;
		case "Ctrl+V":
			CoreUtil.imSleepy(3000);
			action.sendKeys(Keys.chord(Keys.CONTROL, "v")).build().perform();
			CoreUtil.imSleepy(2000);
			break;	
		case "Delete":
			CoreUtil.imSleepy(3000);
			action.sendKeys(Keys.chord(Keys.DELETE)).build().perform();
			CoreUtil.imSleepy(2000);
			break;
		case "Enter":
			CoreUtil.imSleepy(3000);
			action.sendKeys(Keys.chord(Keys.ENTER)).build().perform();
			CoreUtil.imSleepy(2000);
			break;	
		case "End":
			CoreUtil.imSleepy(3000);
			action.sendKeys(Keys.chord(Keys.END)).build().perform();
			CoreUtil.imSleepy(2000);
			break;		
		case "SelectSomeText":
			CoreUtil.imSleepy(3000);
			action.sendKeys(Keys.chord(Keys.LEFT_SHIFT, Keys.ARROW_UP)).build().perform();
			CoreUtil.imSleepy(2000);
			break;
		case "Review":
			CoreUtil.imSleepy(2000);
			action.sendKeys(Keys.chord(Keys.SHIFT, "R")).build().perform();
			CoreUtil.imSleepy(2000);
			break;
		case "Preapre":
			CoreUtil.imSleepy(2000);
			action.sendKeys(Keys.chord(Keys.SHIFT, "P")).build().perform();
			CoreUtil.imSleepy(2000);
			break;
		case "Refresh":
			CoreUtil.imSleepy(2000);
			action.sendKeys(Keys.chord(Keys.CONTROL, "R")).build().perform();
			CoreUtil.imSleepy(2000);
			break;
		default:
			System.out.println("Wrong Key");
			break;
		}
	}

	public static void selectCheckBox(String locatorType, String value, boolean sCheck) {
		WebDriver driver = SeleniumTools.getDriver();
		By locator;
		locator = locatorValue(locatorType, value);
		if (driver.findElement(locator).isSelected() && sCheck == false)
			driver.findElement(locator).click();
		else if (!driver.findElement(locator).isSelected() && sCheck == true)
			driver.findElement(locator).click();
	}

	// Get Alert Okay
	public static String getAlertText() {
		WebDriver driver = SeleniumTools.getDriver();
		Alert alt = driver.switchTo().alert();
		String text = alt.getText();
		alt.accept();
		return text;
	}

	// Get Alert cancel
	public static String getAlertCancel() {
		WebDriver driver = SeleniumTools.getDriver();
		Alert alt = driver.switchTo().alert();
		String text = alt.getText();
		alt.dismiss();
		return text;
	}

	// Get the Text from alert
	public static String getTextAlert() {
		WebDriver driver = SeleniumTools.getDriver();
		Alert alt = driver.switchTo().alert();
		String text = alt.getText();
		System.out.println(text);
		return text;
	}
	
	// Send Text to alert
	public static String getAlert() {
		WebDriver driver = SeleniumTools.getDriver();
		Alert alt = driver.switchTo().alert();
		String text = alt.getText();
		System.out.println(text);
		return text;
	}

	public static void closeTab() throws Exception {
		WebDriver driver = SeleniumTools.getDriver();
		try {

			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_W);
			robot.keyRelease(KeyEvent.VK_W);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			SeleniumTools.switchToMainWindow();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Get text based on Value
	public static String getText(String locatorType, String value) throws Exception {
		String uiText = null;
		WebDriver driver = SeleniumTools.getDriver();
		try {
			loggerUtil.entryLogger("getText");
			By locator;
			locator = locatorValue(locatorType, value);
			explicitWait(locatorType, value);
			WebElement elementUI = driver.findElement(locator);
			if (elementUI != null && elementUI.isEnabled() && elementUI.isDisplayed())
				uiText = elementUI.getText();
			loggerUtil.LogMessage(LogLevel.INFO, "getText", uiText.toString());
			pNode.info(value + "GetText From the element :" + uiText.toString());
		} catch (Exception ex) {
			loggerUtil.LogSevereMessage(LogLevel.SEVERE, "getText", "unable to getTextByID", ex);
		} finally {
			loggerUtil.exitLogger("getText");
		}
		return uiText;
	}

	// Get Attribute Value based on value attribute
	public static String getAttributeTitle(String locatorType, String value) throws Exception {
		WebDriver driver = SeleniumTools.getDriver();
		String uiText = null;
		try {
			By locator;
			locator = locatorValue(locatorType, value);
			loggerUtil.entryLogger("getAttributeTitle");
			CoreUtil.imSleepy(2000);
			;
			WebElement elementUI = driver.findElement(locator);
			if (elementUI != null && elementUI.isEnabled() && elementUI.isDisplayed())
				uiText = elementUI.getAttribute("title");
			loggerUtil.LogMessage(LogLevel.INFO, "getAttributeTitle", uiText.toString());
		} catch (Exception ex) {
			loggerUtil.LogSevereMessage(LogLevel.WARN, "getAttributeTitleByXPath", ex.getMessage(), ex);
		} finally {
			loggerUtil.exitLogger("getAttributeTitleByXPath");
		}
		return uiText;
	}

	// Get Attribute Value based on value attribute
	public static String getAttributeDisabled(String locatorType, String value) throws Exception {
		WebDriver driver = SeleniumTools.getDriver();
		String uiText = null;
		try {
			By locator;
			locator = locatorValue(locatorType, value);
			loggerUtil.entryLogger("getAttributedisabled");
			CoreUtil.imSleepy(2000);
			;
			WebElement elementUI = driver.findElement(locator);
			if (elementUI != null && elementUI.isEnabled() && elementUI.isDisplayed())
				uiText = elementUI.getAttribute("disabled");
			loggerUtil.LogMessage(LogLevel.INFO, "getAttributedisabled", uiText.toString());
		} catch (Exception ex) {
			loggerUtil.LogSevereMessage(LogLevel.WARN, "getAttributedisabledByXPath", ex.getMessage(), ex);
		} finally {
			loggerUtil.exitLogger("getAttributedisabledByXPath");
		}
		return uiText;
	}

	// Get Attribute value based on attribute
	public static String getAttribute(String locatorType, String value, String AttrValue) throws Exception {
		WebDriver driver = SeleniumTools.getDriver();
		String uiText = null;
		try {
			loggerUtil.entryLogger("getAttributevalue");
			By locator;
			locator = locatorValue(locatorType, value);
			CoreUtil.imSleepy(2000);
			WebElement elementUI = driver.findElement(locator);
			if (elementUI != null && elementUI.isEnabled() && elementUI.isDisplayed())
				uiText = elementUI.getAttribute(AttrValue);
			loggerUtil.LogMessage(LogLevel.INFO, "getAttribute"+AttrValue, uiText.toString());
		} catch (Exception ex) {
			loggerUtil.LogSevereMessage(LogLevel.WARN, "getAttribute"+AttrValue, ex.getMessage(), ex);
		} finally {
			loggerUtil.exitLogger("getAttribute"+AttrValue+"ByXPath");
		}
		return uiText;
	}

	// Get color based on attribute
	public static String getColor(String locatorType, String value) throws Exception {
		WebDriver driver = SeleniumTools.getDriver();
		String colorValue = null;
		String hexcolor = null;
		try {
			loggerUtil.entryLogger("getcolor");
			By locator;
			locator = locatorValue(locatorType, value);
			CoreUtil.imSleepy(2000);
			WebElement elementUI = driver.findElement(locator);
			if (elementUI != null && elementUI.isEnabled() && elementUI.isDisplayed())
				colorValue = elementUI.getCssValue("color");

			hexcolor = Color.fromString(colorValue).asHex(); // Converting GRB to hex color

			System.out.println(hexcolor);
			loggerUtil.LogMessage(LogLevel.INFO, "getcolor", colorValue.toString());
		} catch (Exception ex) {
			loggerUtil.LogSevereMessage(LogLevel.WARN, "getcolor", ex.getMessage(), ex);
		} finally {
			loggerUtil.exitLogger("getcolor");
		}
		return hexcolor;
	}

	// Getcss value based on attribute
	public static String getCss(String locatorType, String value, String cssvalue) throws Exception {
		WebDriver driver = SeleniumTools.getDriver();
		String css = null;
		try {
			loggerUtil.entryLogger("getcss");
			By locator;
			locator = locatorValue(locatorType, value);
			CoreUtil.imSleepy(2000);
			WebElement elementUI = driver.findElement(locator);
			if (elementUI != null && elementUI.isEnabled() && elementUI.isDisplayed())
				css = elementUI.getCssValue(cssvalue);
			loggerUtil.LogMessage(LogLevel.INFO, "getcss", css.toString());
		} catch (Exception ex) {
			loggerUtil.LogSevereMessage(LogLevel.WARN, "getcolor", ex.getMessage(), ex);
		} finally {
			loggerUtil.exitLogger("getcss");
		}
		return css;
	}

	// Refreshing the page
	public static void refreshPage() {
		WebDriver driver = SeleniumTools.getDriver();
		driver.navigate().refresh();
	}

	// Wait for the pop up to load based total number of windows
	public static void waitForPopUpToBeAvailable(int numberOfWindows) {
		WebDriver driver = SeleniumTools.getDriver();
		try {
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.numberOfWindowsToBe(numberOfWindows));
		} catch (Exception ex) {
			loggerUtil.LogMessage(LogLevel.WARN, "waitForPopUpToBeAvailable", ex.getMessage());
		}
	}

	// Switch to Main Window
	public static void switchToMainWindow() throws Exception {
		WebDriver driver = SeleniumTools.getDriver();
		try {
			loggerUtil.entryLogger("switchToPopWindow");
			waitForPopUpToBeAvailable(2);
			Set<String> str = driver.getWindowHandles();
			Object[] s = str.toArray();
			driver.switchTo().window(s[0].toString());
		} catch (Exception ex) {
			loggerUtil.LogSevereMessage(LogLevel.SEVERE, "switchToPopWindow", "unable to switchToPopWindow", ex);
		} finally {
			loggerUtil.exitLogger("switchToPopWindow");
		}
	}

	// Switch to 1st child Window
	public static void switchToChildWindow() throws Exception {
		WebDriver driver = SeleniumTools.getDriver();
		try {
			loggerUtil.entryLogger("switchToPopWindow");
			waitForPopUpToBeAvailable(2);
			Set<String> str = driver.getWindowHandles();
			Object[] s = str.toArray();
			driver.switchTo().window(s[1].toString());
		} catch (Exception ex) {
			loggerUtil.LogSevereMessage(LogLevel.SEVERE, "switchToPopWindow", "unable to switchToPopWindow", ex);
		} finally {
			loggerUtil.exitLogger("switchToPopWindow");
		}
	}

	// Switch to any child Window by giving index 
	public static void switchToWindowByIndex(int index) throws Exception {
		WebDriver driver = SeleniumTools.getDriver();
		try {
			loggerUtil.entryLogger("switchToPopWindow");
			waitForPopUpToBeAvailable(2);
			Set<String> str = driver.getWindowHandles();
			Object[] s = str.toArray();
			driver.switchTo().window(s[index].toString());
		} catch (Exception ex) {
			loggerUtil.LogSevereMessage(LogLevel.SEVERE, "switchToPopWindow", "unable to switchToPopWindow", ex);
		} finally {
			loggerUtil.exitLogger("switchToPopWindow");
		}
	}

	// Switch to window based on specified window object
	public static void switchToWindow(String uiObject) throws Exception {
		WebDriver driver = SeleniumTools.getDriver();
		try {
			loggerUtil.entryLogger("switchToWindow");
			driver.switchTo().window(uiObject);
			// CoreUtil.imSleepy(5000);
		} catch (Exception ex) {
			loggerUtil.LogSevereMessage(LogLevel.SEVERE, "switchToWindow", "unable to switchToWindow", ex);
		} finally {
			loggerUtil.exitLogger("switchToWindow");
		}
	}

	// Switch to default window
	public static void switchToDefaultWindow() throws Exception {
		WebDriver driver = SeleniumTools.getDriver();
		try {
			loggerUtil.entryLogger("switchToDefaultWindow");
			driver.switchTo().defaultContent();
		} catch (Exception ex) {
			loggerUtil.LogMessage(LogLevel.WARN, "switchToDefaultWindow", "unable to switchToDefaultWindow");
		} finally {
			loggerUtil.exitLogger("switchToDefaultWindow");
		}
	}

	public static String getCurrentPageTitle() {
		WebDriver driver = SeleniumTools.getDriver();
		String Title = driver.getTitle();
		return Title;
	}

	public static String getCurrentUrl() {
		WebDriver driver = SeleniumTools.getDriver();
		String Url = driver.getCurrentUrl();
		return Url;
	}

	public static void selectCheckBoxByXpath(String locatorType, String value, boolean sCheck) {
		WebDriver driver = SeleniumTools.getDriver();
		By locator;
		locator = locatorValue(locatorType, value);
		explicitWait(locatorType, value);
		if (driver.findElement(locator).isSelected() && sCheck == false)
			driver.findElement(locator).click();
		else if (!driver.findElement(locator).isSelected() && sCheck == true)
			driver.findElement(locator).click();
	}

	// Get array of window object
	public static ArrayList<Object> getNumberofWindows() throws Exception {
		WebDriver driver = SeleniumTools.getDriver();
		ArrayList<Object> windows = new ArrayList<Object>();
		try {
			loggerUtil.entryLogger("getNumberofWindows");
			windows = new ArrayList<Object>(driver.getWindowHandles());
		} catch (Exception ex) {
			loggerUtil.LogSevereMessage(LogLevel.SEVERE, "getNumberofWindows", "unable to getNumberofWindows", ex);
		} finally {
			loggerUtil.exitLogger("getNumberofWindows");
		}
		return windows;
	}

	// to check whether check box is check or not based on ID locator
	public static boolean isCheckboxChecked(String locatorType, String value) throws Exception {
		WebDriver driver = SeleniumTools.getDriver();
		boolean status = false;
		try {
			loggerUtil.entryLogger("isCheckboxChecked");
			By locator;
			locator = locatorValue(locatorType, value);
			explicitWait(locatorType, value);
			WebElement usrs = driver.findElement(locator);
			if (usrs.isSelected()) {
				status = true;
				return status;
			}

		} catch (Exception ex) {
			loggerUtil.LogSevereMessage(LogLevel.ERROR, "isCheckboxChecked", "unable to CheckboxChecked or not", ex);
		} finally {
			loggerUtil.exitLogger("isCheckboxChecked");
		}

		return status;

	}

	// get drop down values based on visible text
	public static List<WebElement> getOptionsFromDropdown(String locatorType, String value) throws Exception {
		WebDriver driver = SeleniumTools.getDriver();
		List<WebElement> options = null;
		try {
			loggerUtil.entryLogger("getOptionsFromDropdown");
			By locator;
			locator = locatorValue(locatorType, value);
			explicitWait(locatorType, value);
			WebElement usrs = driver.findElement(locator);
			Select usr = new Select(usrs);
			options = usr.getOptions();
			loggerUtil.LogMessage(LogLevel.DEBUG, "getOptionsFromDropdown", "able to get the options from dropdown");

		} catch (Exception ex) {
			loggerUtil.LogSevereMessage(LogLevel.ERROR, "getOptionsFromDropdown",
					"unable to get the options from dropdown", ex);
		} finally {
			loggerUtil.exitLogger("getOptionsFromDropdown");
		}
		return options;
	}
	
	// get drop down values based on visible text
		public static String getFirstSelectedOption(String locatorType, String value) throws Exception {
			WebDriver driver = SeleniumTools.getDriver();
			WebElement SelectedOption;
			String option = null;
			try {
				loggerUtil.entryLogger("getFirstSelectedOption");
				By locator;
				locator = locatorValue(locatorType, value);
				explicitWait(locatorType, value);
				WebElement usrs = driver.findElement(locator);
				Select usr = new Select(usrs);
				SelectedOption = usr.getFirstSelectedOption();
				option = SelectedOption.getText();
				System.out.println(option);
				loggerUtil.LogMessage(LogLevel.DEBUG, "getFirstSelectedOption", "get the first selected options from dropdown");

			} catch (Exception ex) {
				loggerUtil.LogSevereMessage(LogLevel.ERROR, "getFirstSelectedOption",
						"unable to get the options from dropdown", ex);
			} finally {
				loggerUtil.exitLogger("getFirstSelectedOption");
			}
			return option;
		}

	public static boolean isElementClickable(String locatorType, String value) throws Exception {
		WebDriver driver = SeleniumTools.getDriver();
		boolean status = false;
		try {
			loggerUtil.entryLogger("isElementClickable");
			By locator;
			locator = locatorValue(locatorType, value);
			explicitWait(locatorType, value);
			WebElement elementUI = driver.findElement(locator);
			if (elementUI != null && elementUI.isEnabled() && elementUI.isDisplayed()) {
				WebDriverWait wait = new WebDriverWait(driver, 6);
				wait.until(ExpectedConditions.elementToBeClickable(elementUI));
				status = true;
				String str = String.valueOf(status);
				loggerUtil.LogMessage(LogLevel.INFO, "isElementClickable", value + "Element is Clickable : " + str);
				pNode.info(value + "Element is Clickable");
			} else {
				status = false;
				String str = String.valueOf(status);
				loggerUtil.LogMessage(LogLevel.INFO, "isElementClickable", value + "Element is Not Clickable : " + str);
				pNode.info(value + "Element is Not Clickable");
			}

		} catch (Exception ex) {
			loggerUtil.LogSevereMessage(LogLevel.WARN, "isElementClickable", ex.getMessage(), ex);
		} finally {
			loggerUtil.exitLogger("isElementClickable");
		}
		return status;
	}

	// IS element present based on name
	public static boolean isElementPresent(String locatorType, String value) {
		WebDriver driver = SeleniumTools.getDriver();
		boolean status = true;
		WebElement elementUI = null;
		try {
			loggerUtil.entryLogger("isElementPresent");
			By locator;
			locator = locatorValue(locatorType, value);
			elementUI = driver.findElement(locator);
			if (elementUI != null && elementUI.isEnabled() && elementUI.isDisplayed()) {
				status = true;
				String str = String.valueOf(status);
				loggerUtil.LogMessage(LogLevel.INFO, "isElementPresent", value + "Element is available : " + str);
				pNode.info(value + "Element is Available");
			} else {
				status = false;
				String str = String.valueOf(status);
				loggerUtil.LogMessage(LogLevel.DEBUG, "isElementPresent", value + "Element is not available : " + str);
				pNode.info(value + "Element is not available");

			}
		} catch (Exception e) {
			loggerUtil.LogMessage(LogLevel.WARN, "isElementPresent", value + "Element not found");
		} finally {
			loggerUtil.exitLogger("isElementPresent");
		}
		return status;
	}

	// IS element present based on name
	public static boolean isElementSelected(String locatorType, String value) {
		WebDriver driver = SeleniumTools.getDriver();
		boolean status = true;
		WebElement elementUI = null;
		try {
			loggerUtil.entryLogger("isElementSelected");
			By locator;
			locator = locatorValue(locatorType, value);
			elementUI = driver.findElement(locator);
			if (elementUI.isSelected()) {
				System.out.println("radiobutton is selected by default");
				status = true;
				String str = String.valueOf(status);
				loggerUtil.LogMessage(LogLevel.INFO, "isElementSelected", value + "Element is Selected : " + str);
				pNode.info(value + "Element is Selected");
			} else {
				System.out.println("radiobutton is not selected by default");
				status = false;
				String str = String.valueOf(status);
				loggerUtil.LogMessage(LogLevel.DEBUG, "isElementSelected", value + "Element is not Selected : " + str);
				pNode.info(value + "Element is not Selected");
			}
		} catch (Exception e) {
			loggerUtil.LogMessage(LogLevel.WARN, "isElementSelected", value + "Element not Selected");
		} finally {
			loggerUtil.exitLogger("isElementSelected");
		}
		return status;
	}

	public static boolean isDisplayed(String locatorType, String value) {
		WebDriver driver = SeleniumTools.getDriver();
		boolean status = true;
		WebElement elementUI = null;
		loggerUtil.entryLogger("isElementDisplayed");
		By locator;
		locator = locatorValue(locatorType, value);
		elementUI = driver.findElement(locator);
		if (elementUI.isDisplayed()) {
			status = true;
			String str = String.valueOf(status);
			loggerUtil.LogMessage(LogLevel.INFO, "isElementDisplayed", value + "Element is Displayed : " + str);
			pNode.info(value + "Element is Displayed");
		} else {
			status = false;
			String str = String.valueOf(status);
			loggerUtil.LogMessage(LogLevel.DEBUG, "isElementDisplayed", value + "Element is not Displayed : " + str);
			pNode.info(value + "Element is not Displayed");

		}

		return status;
	}

	// Click on the webElement based on Classname
	public static void clickObject(String locatorType, String value) throws Exception {
		WebDriver driver = SeleniumTools.getDriver();
		try {
			By locator;
			locator = locatorValue(locatorType, value);
			loggerUtil.entryLogger("clickObjectByClassName");
			explicitWait(locatorType, value);
			WebElement elementUI = driver.findElement(locator);
			if (elementUI != null && elementUI.isEnabled() && elementUI.isDisplayed())
				elementUI.click();
			else {
				loggerUtil.LogMessage(LogLevel.WARN, "clickObject", "Element " + locator + " not found");
			}
		} catch (Exception ex) {
			loggerUtil.LogSevereMessage(LogLevel.ERROR, "clickObject", "unable to click on object", ex);
		} finally {
			loggerUtil.exitLogger("clickObject");
		}
	}

	public static void doubleClickOnObject(String locatorType, String value) throws Exception {
		WebDriver driver = SeleniumTools.getDriver();
		try {
			driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

			By locator;
			locator = locatorValue(locatorType, value);
			loggerUtil.entryLogger("clickObject");
			explicitWait(locatorType, value);
			WebElement elementUI = driver.findElement(locator);
			if (elementUI != null && elementUI.isEnabled() && elementUI.isDisplayed()) {
				Actions a = new Actions(driver);
				a.moveToElement(elementUI).click().doubleClick().build().perform(); 
				pNode.info("Double clicked on Element" + value);
				loggerUtil.LogMessage(LogLevel.INFO, "doubleClickOnObject", "Element " + locator);
			} else {
				loggerUtil.LogMessage(LogLevel.WARN, "doubleClickOnObject", "Element " + locator + " not found");
			}
		} catch (NoSuchElementException e) {
			loggerUtil.LogSevereMessage(LogLevel.ERROR, "doubleClickOnObject", "unable to  doubleClick on object", e);
			pNode.info(e);
		} finally {
			loggerUtil.exitLogger("doubleClickOnObject");
		}
	}

	// will perform mouse right click and click on element from the menu
	public static void rightClick(String locatorType, String value, String LinkText) {
		WebDriver driver = SeleniumTools.getDriver();
		try {
			By locator;
			locator = locatorValue(locatorType, value);
			loggerUtil.entryLogger("rightClick");
			explicitWait(locatorType, value);
			WebElement element = driver.findElement(locator);
			Actions action1 = new Actions(driver);
			action1.moveToElement(element);
			action1.contextClick(element).build().perform(); /* this will perform right click */
			WebElement elementEdit = driver
					.findElement(By.linkText(LinkText)); /* This will select menu after right click */
			elementEdit.click();
		} catch (NoSuchElementException e) {
			try {
				loggerUtil.LogSevereMessage(LogLevel.ERROR, "rightClick", "unable to click on object", e);
			} catch (Exception e1) {

				e1.printStackTrace();
			}
		} finally {
			loggerUtil.exitLogger("rightClick");
		}
	}

	// will perform mouse right click and select the submenu from the main menu
	public static void AssignUnassignRole(String locatorType, String value, String LinkText, String Role) {
		WebDriver driver = SeleniumTools.getDriver();
		try {
			By locator;
			locator = locatorValue(locatorType, value);
			loggerUtil.entryLogger("Assign/UnassignRole");
			explicitWait(locatorType, value);
			WebElement element = driver.findElement(locator);
			Actions action = new Actions(driver);
			action.moveToElement(element);
			action.contextClick(element).build().perform(); //this will perform right click
			WebElement elementEdit = driver.findElement(By.linkText(LinkText));
			action.moveToElement(elementEdit).build().perform();
			WebElement elementEdit2 = driver.findElement(By.linkText(Role));
			elementEdit2.click();

			// action.keyDown(Keys.ARROW_DOWN).keyDown(Keys.ARROW_DOWN).keyDown(Keys.ARROW_DOWN);
			/*
			 * action1.contextClick(element).build().perform(); this will perform right
			 * click WebElement elementEdit = driver.findElement(By.linkText(LinkText));
			 * This will select menu after right click action1.moveToElement(elementEdit);
			 * WebElement elementEdit1 = driver.findElement(By.linkText(LinkText2));
			 * action1.moveToElement(elementEdit1); elementEdit1.click();
			 */
		} catch (NoSuchElementException e) {
			try {
				loggerUtil.LogSevereMessage(LogLevel.ERROR, "rightClicksecondText", "unable to click on object", e);
			} catch (Exception e1) {

				e1.printStackTrace();
			}
		} finally {
			loggerUtil.exitLogger("rightClicksecondText");
		}
	}

	public static void mouseLeftClick(String locatorType, String element1) {
		WebDriver driver = SeleniumTools.getDriver();
		try {
			By locator;
			locator = locatorValue(locatorType, element1);
			Actions action = new Actions(driver);
			WebElement element = driver.findElement(locator);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			Thread.sleep(500);
			action.contextClick(element).perform();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Date Selection
	public static void dateSelect(String datePicker, String date) {
		WebDriver driver = SeleniumTools.getDriver();
		try {
			driver.findElement(By.xpath(datePicker)).click();
			CoreUtil.imSleepy(2000);
			driver.findElement(By.xpath(date)).click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Switch to out of the frame(default)
	public static void switchToDefaultFrame() {
		WebDriver driver = SeleniumTools.getDriver();
		driver.switchTo().defaultContent();
	}

	// Switch to Frame Window
	public static void switchToiFrame(String locatorType, String value, String name) throws Exception {
		WebDriver driver = SeleniumTools.getDriver();
		try {
			loggerUtil.entryLogger("switchToFrameByID");
			driver.switchTo().frame(name);
			waitForFrameToBeAvailable(locatorType, value);
		} catch (Exception ex) {
			loggerUtil.LogSevereMessage(LogLevel.SEVERE, "switchToFrame", "unable to switchToFrame", ex);
		} finally {
			loggerUtil.exitLogger("switchToFrame");
		}
	}

	// Switch to Frame Window by ID ==> goto default frame and switch to desired fram
	public static void switchToFrame(String locatorType, String value) throws Exception {
		WebDriver driver = SeleniumTools.getDriver();
		try {
			loggerUtil.entryLogger("switchToFrameByID");
			driver.switchTo().defaultContent();
			waitForFrameToBeAvailable(locatorType, value);
		} catch (Exception ex) {
			loggerUtil.LogSevereMessage(LogLevel.SEVERE, "switchToFrame", "unable to switchToFrame", ex);
		} finally {
			loggerUtil.exitLogger("switchToFrame");
		}
	}

	// Switch to Frame Window by Number
	public static void switchToFrameByNumber(int uiObject) throws Exception {
		WebDriver driver = SeleniumTools.getDriver();
		try {
			loggerUtil.entryLogger("switchToFrameByNumber");
			driver.switchTo().defaultContent();
			driver.switchTo().frame(uiObject);
		} catch (Exception ex) {
			loggerUtil.LogSevereMessage(LogLevel.SEVERE, "switchToFrameByNumber", "unable to switchToFrameByNumber",
					ex);
		} finally {
			loggerUtil.exitLogger("switchToFrameByNumber");
		}
	}

	// Wait for the frame to load based on ID
	public static void waitForFrameToBeAvailable(String locatorType, String value) {
		WebDriver driver = SeleniumTools.getDriver();
		WebDriverWait wait = new WebDriverWait(driver, 60);
		By locator;
		locator = locatorValue(locatorType, value);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
	}

	// explicit wait based on Xpath
	public static void explicitWait(String locatorType, String value) {
		WebDriver driver = SeleniumTools.getDriver();
		try {
			loggerUtil.entryLogger("explicitWait");
			By locator;
			locator = locatorValue(locatorType, value);
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (Exception ex) {
			loggerUtil.LogMessage(LogLevel.WARN, "explicitWaitByXpath",
					"Explicit wait of 60 seconds over/Exception occured." + ex.getMessage());

		} finally {
			loggerUtil.exitLogger("explicitWaitByXpath");
		}
	}

	public static void hoverElement(String locatorType, String value) throws Exception {
		WebDriver driver = SeleniumTools.getDriver();
		try {
			loggerUtil.entryLogger("hoverElement");
			By locator;
			locator = locatorValue(locatorType, value);
			explicitWait(locatorType, value);
			Actions hoverAction = new Actions(driver);
			WebElement hoverElement = driver.findElement(locator);
			hoverAction.clickAndHold(hoverElement).perform();
			hoverAction.release(hoverElement);
			pNode.info(value + "Mouse hover to the element");
			loggerUtil.LogMessage(LogLevel.INFO, "clickOnObject At : ", value);
			// Perform mouse
			// hover action
			// using
			// 'clickAndHold'
			// method
		} catch (Exception ex) {
			loggerUtil.LogSevereMessage(LogLevel.SEVERE, "hoverElementUsingXPath",
					"unable to hover Element using XPath", ex);
			pNode.fail(ex);
		} finally {
			loggerUtil.exitLogger("hoverElementUsingXPath");
		}
	}

	// Close the working browser
	public static void closeBrowser() throws Exception {
		WebDriver driver = SeleniumTools.getDriver();
		try {
			loggerUtil.entryLogger("closeBrowser");
			driver.close();
			loggerUtil.LogMessage(LogLevel.INFO, "closeBrowser", "browser closed");
		} catch (Exception ex) {
			loggerUtil.LogSevereMessage(LogLevel.SEVERE, "closeBrowser", "unable to close browser", ex);
		} finally {
			loggerUtil.exitLogger("closeBrowser");
		}
	}

	public static void quitBrowser() throws Exception {
		WebDriver driver = SeleniumTools.getDriver();
		try {
			loggerUtil.entryLogger("quitBrowser");
			driver.quit();
		} catch (Exception e) {
			loggerUtil.LogSevereMessage(LogLevel.SEVERE, "quitBrowser", "Unable to quit the browser ", e);
		} finally {
			loggerUtil.exitLogger("quitBrowser");
		}
	}

	// Take screen shot
	public static void getScreenshot() {
		WebDriver driver = SeleniumTools.getDriver();
		try {
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			// The below method will save the screen shot
			FileUtils.copyFile(scrFile, new File("./FailedTestsScreenshots/" + CoreUtil.timeStamp_forFilename() + "/"
					+ String.valueOf(screenshotCounter) + ".jpg"));
			screenshotCounter++;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// Take screen shot
	public static void getScreenshot(String methodName) {
		WebDriver driver = SeleniumTools.getDriver();
		try {
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			// The below method will save the screen shot
			FileUtils.copyFile(scrFile, new File("./FailedTestsScreenshots/" + methodName
					+ CoreUtil.timeStamp_forFilename() + "/" + String.valueOf(screenshotCounter) + ".jpg"));
			screenshotCounter++;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	// Open the URL/Application
	public static WebDriver navigateURL(String URL) throws Exception {
		WebDriver driver = SeleniumTools.getDriver();
		
		try {
			loggerUtil.entryLogger("navigateURL");
			driver.get(URL);
			pNode.info("Navigated to " + URL);
			loggerUtil.LogMessage(LogLevel.INFO, "navigateUrl", "navigated to URL:" + URL);
		} catch (Exception e) {
			loggerUtil.LogSevereMessage(LogLevel.SEVERE, "navigateURL", "unable to navigate URL", e);
		} finally {
			loggerUtil.exitLogger("navigateUrl");
		}
		return driver;

	}

	// Verify the expected text with actual Text based ID
	public static boolean verifyText(String locatorType, String value, String ExpectedText) throws Exception {
		WebDriver driver = SeleniumTools.getDriver();
		boolean Status = false;
		String Text = null;
		try {
			loggerUtil.entryLogger("verifyText");
			By locator;
			locator = locatorValue(locatorType, value);
			explicitWait(locatorType, value);
			WebElement elementUI = driver.findElement(locator);
			Text = elementUI.getText();
			if (ExpectedText.equals(Text)) {
				Status = true;
				String str = String.valueOf(Status);
				System.out.println(str);
				loggerUtil.entryLogger("Element is avaliable");
			} else {
				Status = false;
				String str = String.valueOf(Status);
				System.out.println(str);
				loggerUtil.entryLogger("Element is not avaliable");
				Assert.fail();
			}
			loggerUtil.LogMessage(LogLevel.DEBUG, "verifyText", "Text from element: " + Text);
		} catch (Exception ex) {
			loggerUtil.LogSevereMessage(LogLevel.ERROR, "verifyText", "unable to verift text", ex);
		} finally {
			loggerUtil.exitLogger("verifyText");
		}
		return Status;
	}

	// Verify the expected text with actual Text based Xpath
	public static boolean verifyTextByMultiple(String locatorType, String value, String ExpectedText) throws Exception {
		WebDriver driver = SeleniumTools.getDriver();
		boolean Status = false;
		String Text = null;
		try {
			By locator;
			locator = locatorValue(locatorType, value);
			explicitWait(locatorType, value);
			loggerUtil.entryLogger("verifyTextBy");
			List<WebElement> elementUI = driver.findElements(locator);
			for (int i = 0; i <= elementUI.size(); i++) {
				Text = elementUI.get(i).getText();
				loggerUtil.LogMessage(LogLevel.DEBUG, "verifyTextBy", "Text from element: " + Text);
				if (ExpectedText.equals(Text)) {
					Status = true;
					System.out.println(Text);
					String str = String.valueOf(Status);
					System.out.println(str);
					loggerUtil.entryLogger("Element is avaliable");
				}
			}
		} catch (Exception ex) {
			// loggerUtil.LogSevereMessage(LogLevel.ERROR,
			// "verifyTextByXpathUsingIndex", "unable to verift text", ex);
			loggerUtil.LogMessage(LogLevel.SEVERE, "verifyTextByXpathUsingIndex", ex.getMessage());
		} finally {
			loggerUtil.exitLogger("verifyTextByXpathUsingIndex");
		}
		return Status;
	}
	// Verifying dropdown values
	public static void verifyDropdown(String locatorType, String value, String Option) throws Exception {
		WebDriver driver = SeleniumTools.getDriver();
		try {
			int count = 0;
			String[] exp = { Option };

			loggerUtil.entryLogger("Verifying dropdown values");
			By locator;
			locator = locatorValue(locatorType, value);
			WebElement dropdown = driver.findElement(locator);
			Select select = new Select(dropdown);

			List<WebElement> options = select.getOptions();
			for (WebElement we : options) {
				for (int i = 0; i < exp.length; i++) {
					if (we.getText().equals(exp[i])) {
						count++;
					}
				}
			}
			if (count == exp.length) {
				System.out.println("matched");
			} else {
				System.out.println("Houston, we have a problem.");
			}

			loggerUtil.LogMessage(LogLevel.INFO, "Verify dropdown values", "Option " + Option + " selected");
			pNode.info("Verify dropdown values : " + Option);
		} catch (Exception ex) {
			loggerUtil.LogSevereMessage(LogLevel.ERROR, "Verify dropdown values", "unable to verify text", ex);
		} finally {
			loggerUtil.exitLogger("Verify dropdown values");
		}
	}

	// Get Text
	public static boolean getTextByMultiple(String locatorType, String value) throws Exception {
		WebDriver driver = SeleniumTools.getDriver();
		boolean Status = false;
		String Text = null;
		try {
			By locator;
			locator = locatorValue(locatorType, value);
			explicitWait(locatorType, value);
			loggerUtil.entryLogger("verifyTextBy");
			List<WebElement> elementUI = driver.findElements(locator);
			for (int i = 0; i <= elementUI.size(); i++) {
				Text = elementUI.get(i).getText();
				loggerUtil.LogMessage(LogLevel.DEBUG, "GetTextBy", "Text from element: " + Text);
			}
		} catch (Exception ex) {
			// loggerUtil.LogSevereMessage(LogLevel.ERROR,
			// "verifyTextByXpathUsingIndex", "unable to verift text", ex);
			loggerUtil.LogMessage(LogLevel.SEVERE, "GetText", ex.getMessage());
		} finally {
			loggerUtil.exitLogger("GetTextBy");
		}
		return Status;
	}

	public static void assertTrue(String locatorType, String value) {
		WebDriver driver = SeleniumTools.getDriver();		

		By locator;
		locator = locatorValue(locatorType, value);
		WebElement elementUI = driver.findElement(locator);
		boolean Text = elementUI.isDisplayed();
		if (Text == false) {
			loggerUtil.entryLogger("Element is not avaliable");
		}

	}

	// Clear the Textbox field and Passing some data to Textbox
	public static String ClearAndSetText(String locatorType, String value, String text) {
		WebDriver driver = SeleniumTools.getDriver();
		try {
			loggerUtil.entryLogger("ClearAndSetText");
			By locator;
			locator = locatorValue(locatorType, value);
			explicitWait(locatorType, value);
			WebElement element = driver.findElement(locator);
			String fieldValue = element.getText();
			Actions navigator = new Actions(driver);
			navigator.click(element).sendKeys(Keys.END).keyDown(Keys.SHIFT).sendKeys(Keys.HOME).keyUp(Keys.SHIFT)
					.sendKeys(Keys.BACK_SPACE).sendKeys(text).perform();
			pNode.info("Passing data to textbox :" + text);
			loggerUtil.LogMessage(LogLevel.INFO, "ClearAndSetText At : " + value, "Data filled: " + text);

		} catch (Exception e) {
			try {
				loggerUtil.LogSevereMessage(LogLevel.ERROR, "Clear And Set Text", "unable to Clear and set text", e);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} 		return text;
	}

	// Explicit wait for clickable object by XPath
	public static void explicitWaitByXpathClickable(String uiObject) {
		WebDriver driver = SeleniumTools.getDriver();
		try {
			loggerUtil.entryLogger("explicitWaitByXpathClickable");
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(uiObject)));
		} catch (Exception ex) {
			loggerUtil.LogMessage(LogLevel.WARN, "explicitWaitByXpathClickable",
					"Explicit wait of 60 seconds over/Exception occured." + ex.getMessage());
		} finally {
			loggerUtil.exitLogger("explicitWaitByXpathClickable");
		}
	}

	// method to drag and drop elements
	public static void dragAndDrop(String sourceElement, String destinationElement) throws Exception {
		WebDriver driver = SeleniumTools.getDriver();
		try {
			loggerUtil.entryLogger("dragAndDrop");
			explicitWaitByXpathClickable(sourceElement);
			explicitWaitByXpathClickable(destinationElement);
			WebElement sourceWE = driver.findElement(By.xpath(sourceElement));
			WebElement destWE = driver.findElement(By.xpath(destinationElement));
			if (sourceWE.isDisplayed() && destWE.isDisplayed()) {
				Actions action = new Actions(driver);
				action.dragAndDrop(sourceWE, destWE).release().build().perform();
				// action.clickAndHold(sourceWE).moveToElement(destWE).release().build().perform();

			} else {
				System.out.println("Element was not displayed to drag");
			}
		} catch (Exception ex) {
			loggerUtil.LogSevereMessage(LogLevel.FATAL, "dragAndDrop", ex.getMessage(), ex);
		} finally {
			loggerUtil.exitLogger("dragAndDrop");
		}
	}

	public static int getRandomNumber(int length) {
		Random r = new Random(System.currentTimeMillis());
		switch (length) {
		case 3: {
			return r.nextInt(999 - 100 + 1) + 100;
		}
		case 4: {
			return r.nextInt(9999 - 1000 + 1) + 1000;
		}
		case 5: {
			return r.nextInt(99999 - 10000 + 1) + 10000;
		}
		case 6: {
			return r.nextInt(999999 - 100000 + 1) + 100000;
		}
		case 7: {
			return r.nextInt(9999999 - 1000000 + 1) + 1000000;
		}
		case 8: {
			return r.nextInt(99999999 - 10000000 + 1) + 10000000;
		}
		case 9: {
			return r.nextInt(999999999 - 100000000 + 1) + 100000000;
		}
		}
		return length;
	}

	public static String getRandomString(int length) {
		
		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		return sb.toString();
	}
	
	public static String getRandomString() {
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder builder = new StringBuilder();
		Random rnd = new Random();
		while (builder.length() < 10) {
			int index = (int) (rnd.nextFloat() * chars.length());
			builder.append(chars.charAt(index));
		}
		String str = builder.toString();
		return str;
	}
	public static void powerSignoff(String locator1, String locator2) {
		WebDriver driver = SeleniumTools.getDriver();
		try {
			Actions navigator = new Actions(driver);
			WebElement e1 = driver.findElement(By.xpath(locator1));
			WebElement e2 = driver.findElement(By.xpath(locator2));
			navigator.click(e1).keyDown(Keys.CONTROL).click(e2).build().perform();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Clear the Textbox field and Passing some data to Textbox and Click on
	// Enter
	public static void ClearAndSetTextEnter(String locatorType, String value, String text) {
		WebDriver driver = SeleniumTools.getDriver();	
		By locator;
		locator = locatorValue(locatorType, value);
		WebElement element = driver.findElement(locator);
		Actions navigator = new Actions(driver);
		navigator.click(element).sendKeys(Keys.END).keyDown(Keys.SHIFT).sendKeys(Keys.HOME).keyUp(Keys.SHIFT)
				.sendKeys(Keys.BACK_SPACE).sendKeys(text).sendKeys(Keys.ENTER).perform();
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		loggerUtil.LogMessage(LogLevel.INFO, "ClearAndSetText At : " + value, "Data filled and Entered: " + text);
	}

	public static String getRandomNumbers() {
		char[] chars = { '5', '6', '7', '8', '9', '0' };
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 6; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		String output = sb.toString();
		return output;
	}

	// select Text from drop down based on visible text
	public static void selectByText(String locatorType, String value, String Option) throws Exception {
		WebDriver driver = SeleniumTools.getDriver();
		try {
			loggerUtil.entryLogger("selectByTextUsingID");
			By locator;
			locator = locatorValue(locatorType, value);
			explicitWait(locatorType, value);
			WebElement usrs = driver.findElement(locator);
			Select usr = new Select(usrs);
			Thread.sleep(1000);
			usr.selectByVisibleText(Option);
			loggerUtil.LogMessage(LogLevel.INFO, "selectByText", "Option " + Option + " selected");
			pNode.info("SelectByText : " + Option);
		} catch (Exception ex) {
			loggerUtil.LogSevereMessage(LogLevel.ERROR, "selectByText", "unable to select by text", ex);
		} finally {
			loggerUtil.exitLogger("selectByText");
		}
	}

	// Navigate to Previous Page
	public static void navigateTopreviousPage() {
		WebDriver driver = SeleniumTools.getDriver();
		driver.navigate().back();
	}

	// select Text from drop down based on visible text
	public static void selectByTextByIndex(String locatorType, String value, int Option) throws Exception {
		WebDriver driver = SeleniumTools.getDriver();
		try {
			loggerUtil.entryLogger("selectByText");
			By locator;
			locator = locatorValue(locatorType, value);
			explicitWait(locatorType, value);
			WebElement usrs = driver.findElement(locator);
			Select usr = new Select(usrs);
			Thread.sleep(1000);
			usr.selectByIndex(Option);
			loggerUtil.LogMessage(LogLevel.INFO, "selectByTextByIndex", "Option " + Option + " selected");
			pNode.info("selectByTextByIndex" + Option);
		} catch (Exception ex) {
			loggerUtil.LogSevereMessage(LogLevel.ERROR, "selectByTextByIndex", "unable to select by text", ex);
		} finally {
			loggerUtil.exitLogger("selectByTextUsingID");
		}

	}

	// select calendar through JavascriptExecutor
	public static void selectCalendar(WebDriver driver, WebElement element, String datevalue) throws Exception {
		try {
			loggerUtil.entryLogger("selectCalendar");

			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].setAttribute('value','" + datevalue + "');", element); // WebElement
																									// date=driver.find();
			//js.executeScript("arguments[0].value='enter text in textbox'", element);																						// String
			// js.executeScript("arguments[0].value='enter the value here';", element);					// date="MM-DD-YYYY"
			loggerUtil.LogMessage(LogLevel.INFO, "selectByTextByIndex", "Option " + datevalue + " selected"); // selectCalendar(driver,
																												// date,dateval);
			pNode.info("selectCalendar" + datevalue);

		} catch (Exception ex) {
			loggerUtil.LogSevereMessage(LogLevel.ERROR, "selectByTextByIndex", "unable to select by text", ex);
		} finally {
			loggerUtil.exitLogger("selectCalendar");
		}
	}

	public static void fetchingTableDataUsingTRAndTDTags(String locatorType, String value) throws Exception {
		WebDriver driver = SeleniumTools.getDriver();
		try {
			// int count=0;
			loggerUtil.entryLogger("fetchingTableDataUsingTRAndTDTags");
			By locator;
			locator = locatorValue(locatorType, value);
			explicitWait(locatorType, value);
			driver.findElement(locator);
			List<WebElement> rows = driver.findElements(By.tagName("tr"));
			for (int i = 0; i <= rows.size() - 1; i++) {
				List<WebElement> columns = rows.get(i).findElements(By.tagName("th"));
				for (int j = 0; j < columns.size(); j++) {

					System.out.print("\t" + columns.get(j).getText());

				}
				List<WebElement> columns1 = rows.get(i).findElements(By.tagName("td"));
				for (int k = 0; k < columns1.size(); k++) {

					System.out.print(columns1.get(k).getText() + "\t" + "\t");

				}
				System.out.println();
			}

		} catch (Exception ex) {
			loggerUtil.LogSevereMessage(LogLevel.ERROR, "fetchingTableDataUsingTRAndTDTags",
					"unable to locate on object", ex);
		} finally {
			loggerUtil.exitLogger("fetchingTableDataUsingTRAndTDTags");
		}
	}

	public static Properties readConfigFile(String str) throws IOException {
		
		File configFile = new File(str);
		FileInputStream fileinput = new FileInputStream(configFile);
		Properties props = new Properties();
		props.load(fileinput);
		return props;
	}

	public static Properties writeConfigFile(String str, String Key, String Update_Pswd) throws IOException {

		File configFile = new File(str);
		FileInputStream fileinput = new FileInputStream(configFile);
		Properties prop = new Properties();
		prop.load(fileinput);
		fileinput.close();
		FileOutputStream fos = new FileOutputStream(configFile);
		prop.setProperty(Key, Update_Pswd);
		prop.store(fos, null);
		fos.close();
		return prop;
	}

	public static void javaScriptExecutorClick(String xpath) {
		WebDriver driver = SeleniumTools.getDriver();
		WebElement element = driver.findElement(By.xpath(xpath));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
	}

	// clicking on object using javascript executor
	public static void javascriptClick(String xpath) {
		WebDriver driver = SeleniumTools.getDriver();
		WebElement element1 = SeleniumTools.driver.findElement(By.xpath(xpath));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element1);
		loggerUtil.LogMessage(LogLevel.INFO, "javascriptClick", "method" + xpath + " Clicked");
		pNode.info("javascriptClick At: " + xpath);
	}
	// clicking on three dots
		public static void setupActions(String value) {
			WebDriver driver = SeleniumTools.getDriver();
			WebElement mnThreedots = SeleniumTools.driver.findElement(By.cssSelector("span.fa.fa-ellipsis-h"));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].click();", mnThreedots);
			CoreUtil.imSleepy(2000);
			WebElement details = SeleniumTools.driver.findElement(By.xpath("//span[text()='" + value + "']"));
			js.executeScript("arguments[0].click();", details);
			driver.findElement(By.xpath("//div[@class='ng-confirm-buttons']/button[1]")).click();
		}

	// clicking on three dots and click on Edit in Setup module
	public static void threeDotsEdit() {
		WebDriver driver = SeleniumTools.getDriver();
		//SeleniumTools.threeDotsClick();
		WebElement Edit1 = SeleniumTools.driver.findElement(By.xpath("//div[@class='rdg-cell-action-menu']/span[1]"));
		Edit1.click();
		CoreUtil.imSleepy(2000);
	}

	
	// clicking on three dots and click on activate in Admin->users
	public static void threeDotsActivate()
			throws EncryptedDocumentException, InvalidFormatException, IOException, Exception {
		WebDriver driver = SeleniumTools.getDriver();
		//SeleniumTools.threeDotsClick();
		WebElement Edit1 = SeleniumTools.driver.findElement(By.xpath("//div[@class='rdg-cell-action-menu']/span[2]"));
		Edit1.click();
		CoreUtil.imSleepy(2000);
		SeleniumTools.clickOnObject(ExcelReader.readExcelFile(Constants.VS_ELEMENTSPATH, "Project Users", 42, 2),
				ExcelReader.readExcelFile(Constants.VS_ELEMENTSPATH, "Project Users", 43, 3));
	}

	// clicking on three dots in Archive management in Archive tab using java script
	public static void AMthreeDotsClick(String value) {
		WebDriver driver = SeleniumTools.getDriver();
		WebElement mnThreedots = SeleniumTools.driver.findElement(By.xpath(
				"//archived-projects[@class='ng-scope']/div/div[1]/div/div/div[2]/div/div/div[2]/div[1]/div/div[17]/div[1]/div/span"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", mnThreedots);
		CoreUtil.imSleepy(2000);
		WebElement details = SeleniumTools.driver.findElement(By.xpath("//span[text()='" + value + "']"));
		js.executeScript("arguments[0].click();", details);
	}

	public String getCurrentDay () {
        //Create a Calendar Object
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
 
        //Get Current Day as a number
        int todayInt = calendar.get(Calendar.DAY_OF_MONTH);
        System.out.println("Today Int: " + todayInt +"\n");
 
        //Integer to String Conversion
        String todayStr = Integer.toString(todayInt);
        System.out.println("Today Str: " + todayStr + "\n");
 
        return todayStr;
    }
	
	public static void getCurrentday() {
		// Create a Calendar Object
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

		// Get Current Day as a number
		int todayInt = calendar.get(Calendar.DAY_OF_MONTH);
		// Integer to String Conversion
		String todayStr = Integer.toString(todayInt);
		System.out.println("Today Str: " + todayStr + "\n");

		/*
		 * int monthInt=calendar.get((calendar.MONTH)+1); String monthStr=
		 * Integer.toString(monthInt); System.out.println(monthStr + 1);
		 */
		int yearInt = calendar.get(calendar.DATE);
		// String yearStr = Integer.toString(yearInt);
		System.out.println(yearInt);
		// return todayStr;
	}
	
	
	// Get Recently downloaded FileName
	public static String getRecentFile() {
		WebDriver driver = SeleniumTools.getDriver();
		File chosenFile = null;
		File directory = new File("C:\\Users\\SureshGoparaju\\Downloads");
		long lastModifiedTime = Long.MIN_VALUE;

		try {
			File[] files = directory.listFiles(File::isFile);
			if (files != null) {
				for (File file : files) {
					if (file.lastModified() > lastModifiedTime) {
						chosenFile = file;
						lastModifiedTime = file.lastModified();
					}
				}
			}

			System.out.println(chosenFile);
			loggerUtil.LogMessage(LogLevel.INFO, "getFile", chosenFile.toString());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			loggerUtil.exitLogger("getFile");
		}

		return chosenFile.toString();
	}
}