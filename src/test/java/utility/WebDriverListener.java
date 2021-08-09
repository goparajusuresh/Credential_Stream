package utility;

import org.openqa.selenium.WebDriver;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

import utility.LocalDriverFactory;

public class WebDriverListener implements IInvokedMethodListener {

	//@Parameters("browserName")
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
	       if (method.isTestMethod()) {
	           //String browserName = method.getTestMethod().getXmlTest().getLocalParameters().get("browserName");
	           WebDriver driver = LocalDriverFactory.createInstance("chrome");
	           SeleniumTools.setWebDriver(driver);
	       }
	   }

	   public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
	       if (method.isTestMethod()) {
	           WebDriver driver = SeleniumTools.getDriver();
	           if (driver != null) {
	               //driver.quit();
	           }
	       }
	   }
}
