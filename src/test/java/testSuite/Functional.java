package testSuite;

import static org.testng.Assert.assertEquals;

import java.lang.reflect.Method;

import org.junit.Assert;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import utility.Constants;
import utility.CoreUtil;
import utility.LoggerUtil;
import utility.Login;
import utility.SeleniumTools;
import utility.TestInit;

public class Functional extends TestInit {
	static LoggerUtil loggerUtil = new LoggerUtil(Functional.class);

	@Override
	@BeforeMethod
	public void beforeMethod(Method method) {
		try {
			testName = method.getName();
			Test test = method.getAnnotation(Test.class);
			if (test == null) {
				return;
			}
			loggerUtil.entryLogger(testName + "::" + test.description());
			pNode = extent.createTest(testName + ":" + test.description());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterMethod
	public void afterMethod(Method method) {
		try {
			//SeleniumTools.quitBrowser();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(description = "Test case 01")
	public void Test01() throws Exception {
		try {

			Login.StageLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Password"));
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}

	}

	@Test(description="Iretry Analyzer ") //retryAnalyzer = utility.RetryAnalyzer.class
	public static void Test02() {
		try {
			Login.DeployLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty(""),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty(""));
		
		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
			Assert.fail(); //if we get exception also we are making it as fail
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}

	}
}