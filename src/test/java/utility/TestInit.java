package utility;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class TestInit {
	public static ExtentReports extent;
	public static ExtentTest pNode;
	protected static WebDriver driver;
	public WebDriverWait wait;
	public static ThreadLocal<ExtentTest> parentTest = new ThreadLocal<ExtentTest>();
	public static String testName;

	@BeforeSuite(alwaysRun = true)
	public void beforeSuite(ITestContext suite) throws Exception {
		/*
		 * extent = ExtentManager.getInstance(getClass().getSimpleName()); pNode
		 * = extent.createTest(getClass().getSimpleName());
		 * parentTest.set(pNode);
		 */
		extent = ExtentManager.getInstance(getClass().getSimpleName());

	}

	@BeforeClass(alwaysRun = true)
	public void beforeClassRun() {

	}

	@BeforeTest(alwaysRun = true)
	public void beforeTest() {
		

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(Method method) {
	/*	testName=method.getName();
		Test test = method.getAnnotation(Test.class);
		pNode=extent.createTest(testName+":"+test.description());*/
	}

	@AfterMethod(alwaysRun = true)
	public void afterMethod(ITestResult result) throws Exception {
		if (result.getStatus() == ITestResult.FAILURE) {
			try {
				String temp = getScreenshot(driver, result.getName());
				pNode.fail(result.getThrowable().getMessage(),
						MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
				
				
				MediaEntityModelProvider mediaModel = MediaEntityBuilder.createScreenCaptureFromPath("screenshot.png").build();
				pNode.fail("details", mediaModel);	
				
				pNode.addScreenCaptureFromPath(temp);
				System.out.print(result.getName() + ": Execution Failed");
				String code = "Method: " + result.getName() + "\n" + "Reason: " + result.getThrowable().toString();
				Markup m = MarkupHelper.createCodeBlock(code);
				pNode.fail(m);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (result.getStatus() == ITestResult.SKIP) {
			System.out.print(result.getName() + ": Execution Skipped");
			pNode.skip(result.getName() + ":Test Case Executed Skipped!");
		}

		extent.flush();
		// System.out.print("\nEND TEST: " + result.getName());
		// pNode.info(result.getTestName());
	}

	public static String getScreenshot(WebDriver driver, String screenshotName) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;

		File src = ts.getScreenshotAs(OutputType.FILE);
		
		String path = System.getProperty("user.dir") + "/FailedTestsScreenshots/"+screenshotName+System.currentTimeMillis()+".png";
		
		File destination = new File(path); 

		FileUtils.copyFile(src, destination);
	
		return path;
	}

	@AfterTest(alwaysRun = true)
	public void afterTest() {
		// todo write your code here
	}

	@AfterClass(alwaysRun = true)
	public void afterClassRun() {
		// Quit the driver
		// driver.close();
		driver = null;
	}

	@AfterSuite(alwaysRun = true)
	public void afterSuite() {
		// todo write your code here

	}

	protected void markSetupAsFailure(Exception e) {
		pNode.fail("Exception: " + e.toString());
		Assert.fail(e.getMessage());
	}

	public void markTestAsFailure(Exception e, ExtentTest pNode) {
		e.printStackTrace();
		pNode.fail("Test Case Failed due to Exception");
		pNode.error(e);
		Assert.fail(e.getMessage());
	}

	public static void messeage(String text) {
		pNode.log(Status.INFO, text);
	}

}
