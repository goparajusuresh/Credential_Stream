package testSuite;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import utility.Constants;
import utility.CoreUtil;
import utility.LoggerUtil;
import utility.Login;
import utility.SeleniumTools;
import utility.TestInit;

public class Verity extends TestInit {
	public static String Provider_Name, Update_Pswd, Actual, Expected, Login_ID, Color, Fac_Name;

	static LoggerUtil loggerUtil = new LoggerUtil(Verity.class);

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
	public void getResult(ITestResult result) {
		try {
			if (result.getStatus() == ITestResult.FAILURE) {
				String screenShotPath = TestInit.getScreenshot(driver, "screenShotName");
				pNode.log(Status.FAIL, result.getThrowable());
				pNode.log(Status.FAIL, "Snapshot below: " + pNode.addScreenCaptureFromPath(screenShotPath));
			}
			//extent.endTest(pNode);
			 SeleniumTools.quitBrowser();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// This script includes 4 test cases Add, Modify, Demographics - Country - Add
	// and delete provider
	@Test(description = "Provider - Add, Provider - Demographics - Modify, Provider - Demographics - Country - Add, Provider - Delete")
	public void Verity_SG_01() {
		try {
			// Login to the Stage Application
			Login.StageLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Password"));
			// Verifying Global mode
			Login.CheckMode("Verity QA Team1", "global");

			// Clicking on search
			SeleniumTools.hoverElement("xpath", "//li[@text='Search'] [@class='k-item k-state-default']");
			// Clicking on Provider
			SeleniumTools.clickOnObject("xpath", "//span[text()='Provider']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.clickOnObject("xpath", "//p[text()='New']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.waitForFrameToBeAvailable("id", "browserFrame");
			Provider_Name = SeleniumTools.ClearAndSetText("xpath", "//input[@name='lastName']",
					"Test_Provider_ "+SeleniumTools.getRandomString(3));
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@name='firstName']", SeleniumTools.getRandomString(2));
			
			// Clicking on Facility
			SeleniumTools.clickOnObject("xpath", "//echo-lookup[@model='fac']/div[1]/span/button");
			CoreUtil.imSleepy(10000);
			SeleniumTools.switchToFrame("xpath", "//iframe[@name='frame-modal-clone-1']");
			// selecting facility
			SeleniumTools.clickOnObject("xpath", "//td[text()='Alvarado']");
			// Click on Submit
			SeleniumTools.clickOnObject("xpath", "//button[text()='Submit']");
			CoreUtil.imSleepy(5000);

			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("id", "browserFrame");
			// Clicking on status
			SeleniumTools.clickOnObject("xpath", "//echo-lookup[@model='status']/div[1]/span/button");
			CoreUtil.imSleepy(8000);
			SeleniumTools.switchToFrame("xpath", "//iframe[@name='frame-modal-clone-1']");
			SeleniumTools.clickOnObject("xpath", "//td[text()='vACT ']");

			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("id", "browserFrame");
			// Clciking on AddNew
			SeleniumTools.clickOnObject("xpath", "//button[text()='Add New']");
			CoreUtil.imSleepy(20000);

			// Clicking on SAVE
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save']");
			CoreUtil.imSleepy(10000);
			// ****************** Modifying provider ********************
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@ng-model='query']", Provider_Name);
			CoreUtil.imSleepy(5000);
			// clicking on Demographics
			SeleniumTools.clickOnObject("xpath",
					"//div[@ng-show='s.Expanded']/ul/li/div/span[contains(text(),'Demographics')]");

			CoreUtil.imSleepy(5000);
			SeleniumTools.waitForFrameToBeAvailable("id", "browserFrame");
			SeleniumTools.scrollToElementByXpath("xpath", "//input[@id='address1']");
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@id='address1']",
					"2435 Fair Oaks Blvd " + SeleniumTools.getRandomNumbers());
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@id='zip']", "92121");
			// Entering phone number
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@id='phone1']", SeleniumTools.getRandomNumbers());
			// Save
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save']");
			CoreUtil.imSleepy(10000);
			// ****************** Provider - Demographics - Country - Add
			// ********************
			SeleniumTools.clickOnObject("xpath", "//echo-lookup[@model='formFields.l_country']/div/span/button");
			CoreUtil.imSleepy(10000);
			// Search for a country
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='frame-modal-clone-1']");
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@id='lookup-code-search']", "In");
			SeleniumTools.clickOnObject("xpath", "//input[@class='btn btn-primary']");
			CoreUtil.imSleepy(5000);
			// Selecting the country
			SeleniumTools.clickOnObject("xpath", "//table[@class='k-selectable']/tbody/tr/td[text()='India']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("id", "browserFrame");
			SeleniumTools.scrollToElementByXpath("xpath", "//echo-lookup[@model='formFields.l_country']/div/input");
			Expected = SeleniumTools.getText("xpath", "//echo-lookup[@model='formFields.l_country']/div/input");
			SeleniumTools.verifyText("xpath", "//echo-lookup[@model='formFields.l_country']/div/input", Expected);

			// ************* Deleting Provider ************
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Delete']");
			SeleniumTools.clickOnObject("xpath",
					"//echo-interrupt[@class='ng-isolate-scope']/div[1]/div/div/div[2]/div/a[2]");
			CoreUtil.imSleepy(10000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.clickOnObject("xpath", "//button[text()='OK']");
			CoreUtil.imSleepy(2000);

			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@ng-model='query']", Provider_Name);
			CoreUtil.imSleepy(2000);
			Actual = SeleniumTools.getText("xpath", "//p[@ng-show='showFeedback']");
			assertEquals(Actual, "0 record(s) found");

		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
			Assert.fail();
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}

	@Test(description = "Forgot Password")
	public void Verity_SG_02() {
		try {
			// Login to the Stage Application
			Login.StageLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Forgot_User"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Forgot_PasswordStage"));
			// Verifying Global mode
			Login.CheckMode("Verity QA Team1", "global");
			SeleniumTools.hoverElement("xpath", "//li[@text='Set up']");
			SeleniumTools.hoverElement("xpath", "//span[text()='System']");
			SeleniumTools.hoverElement("xpath", "//span[text()='User/Group']");
			SeleniumTools.clickOnObject("xpath", "//span[text()='User/Group Maintenance']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			// Search for the user
			SeleniumTools.ClearAndSetTextEnter("xpath", "//div[@id='menuHeader']//input[@placeholder='Search...']",
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Forgot_User"));
			// Clicking on user
			SeleniumTools.clickOnObject("xpath", "//span[contains(text(),'Cred Stream')]");
			// Clicking on userinfo
			SeleniumTools.clickOnObject("xpath", "//span[contains(text(),'User Info')]");
			// Clicking on Email
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@name='emailtxt']",
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Forgot_Email"));
			// Clicking on Save
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save']");
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.clickOnObject("xpath", "//button[text()='OK']");
			SeleniumTools.switchToFrame("id", "mainFrame");
			// Logout
			SeleniumTools.clickOnObject("xpath", "//img[@id='user-picture']");
			SeleniumTools.clickOnObject("xpath", "//div[@class='nav-row']//span[text()='Log out']");
			// Clicking on Account name
			SeleniumTools.ClearAndSetText("xpath", "//input[@name='loginNameTxt']",
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Forgot_User"));
			// Clicking on Forgot password
			SeleniumTools.clickOnObject("xpath", "//a[text()='Forgot password?']");
			CoreUtil.imSleepy(2000); 

			SeleniumTools.navigateURL("https://accounts.google.com/signin");
			// Clicking on Email
			SeleniumTools.ClearAndSetText("xpath", "//input[@type='email']",
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Forgot_Email"));
			CoreUtil.imSleepy(2000);
			// Clicking on Next
			SeleniumTools.scrollToElementAndClick("xpath", "//div[@class='VfPpkd-Jh9lGc']");
			CoreUtil.imSleepy(2000);
			// Clicking on password
			SeleniumTools.ClearAndSetText("xpath", "//input[@type='password']",
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Gmail_Password"));
			CoreUtil.imSleepy(2000);
			// Clicking on submit
			SeleniumTools.clickOnObject("xpath", "//div[@class='qhFLie']/div/div/button");
			CoreUtil.imSleepy(5000);
			SeleniumTools.navigateURL("https://mail.google.com/mail/");
			CoreUtil.imSleepy(15000);
			// Clicking on Open Email
			SeleniumTools.clickOnObject("xpath", "//table[@class='F cf zt']/tbody/tr[1]");
			CoreUtil.imSleepy(10000);
			// Clicking on reset link
			SeleniumTools.clickOnObject("xpath",
					"//a[contains(@href, 'https://client.verity.cloud/QA/')]");
			CoreUtil.imSleepy(10000);
			SeleniumTools.switchToChildWindow();
			// ****************** To Reset your password *************
			// Clicking on Enter Account
			SeleniumTools.ClearAndSetText("xpath", "//input[@name='UserName']",
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Forgot_User"));
			// Clicking on new password
			Update_Pswd = SeleniumTools.ClearAndSetText("xpath", "//input[@name='Password']",
					"Cstream_" + SeleniumTools.getRandomNumber(4));
			// Clicking on Re-enter password
			SeleniumTools.ClearAndSetText("xpath", "//input[@name='ReenterPassword']", Update_Pswd);
			// Clicking on Submit
			SeleniumTools.clickOnObject("xpath", "//input[@type='submit']");
			CoreUtil.imSleepy(5000);
			Expected = SeleniumTools.getText("xpath", "//div[@id='PasswordChangedSuccessfully']/p/font");
			SeleniumTools.verifyText("xpath", "//div[@id='PasswordChangedSuccessfully']/p/font", Expected);
			// *********** Updating latest password in the .property file ******************
			SeleniumTools.writeConfigFile(Constants.properties_stage, "Forgot_PasswordStage", Update_Pswd)
					.setProperty("Forgot_PasswordStage", Update_Pswd);
			// Clicking on Go to login page
			SeleniumTools.clickOnObject("xpath", "//input[@value='Go to Login Page']");
			// Relogin to the Application
			CoreUtil.imSleepy(2000);
			// Entering user name
			SeleniumTools.ClearAndSetText("xpath", "//input[@name='loginNameTxt']",
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Forgot_User"));
			// Entering Password
			SeleniumTools.ClearAndSetText("xpath", "//input[@name='PasswordTxt']",
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Forgot_PasswordStage"));
			// Clicking on Login button
			SeleniumTools.clickOnObject("xpath", "//input[@name='LoginButton']");
			CoreUtil.imSleepy(4000);

		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
			Assert.fail();
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}

	@Test(description = "Change from Global to Facility specific login")
	public static void Verity_SG_03() {
		try {

			// Login to the Stage Application
			Login.StageLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Password"));
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.clickOnObject("xpath", "//div[@id='main-navigation']//img[@id='NavigationLogo']");
			SeleniumTools.clickOnObject("xpath", "//div[@ng-mouseleave='nav.onFacilityLinkLeave()']/div[@id='facility']");
			CoreUtil.imSleepy(10000);
			SeleniumTools.switchToFrame("id", "frame-modal-clone-1");
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='lookup-text-search']", "test");
			SeleniumTools.clickOnObject("xpath", "//input[@type='button']");
			CoreUtil.imSleepy(5000);
			
			String FacName = SeleniumTools.getText("xpath", "//div[@id='code-lookup']//table//tr[1]/td[3]");
			SeleniumTools.clickOnObject("xpath", "//div[@id='code-lookup']//table//tr[1]/td[3]");
			CoreUtil.imSleepy(30000);
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.clickOnObject("xpath", "//div[@id='main-navigation']//img[@id='NavigationLogo']");
			SeleniumTools.verifyText("xpath", "//div[@id='facility-business-unit']/div[@id='facility']", FacName);

		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
			Assert.fail();
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}

	}

	@Test(description = "Account Management - Create a new user then login as that user ")
	public static void Verity_SG_04() {
		try {
			// Login to the Stage Application
			Login.StageLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Password"));
			// Verifying Global mode
			Login.CheckMode("Verity QA Team1", "global");
			SeleniumTools.hoverElement("xpath", "//li[@text='Set up']");
			SeleniumTools.hoverElement("xpath", "//span[text()='System']");
			SeleniumTools.hoverElement("xpath", "//span[text()='User/Group']");
			SeleniumTools.clickOnObject("xpath", "//span[text()='User/Group Maintenance']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.clickOnObject("xpath", "//div[@id='menuHeader']//div[text()='New']");
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='operfirst']", SeleniumTools.getRandomString());
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='opermiddle']", SeleniumTools.getRandomString());
			// Clicking on Full Name
			String UserName = SeleniumTools.ClearAndSetText("xpath", "//input[@id='full_name']",
					"Test_" + SeleniumTools.getRandomString());
			// Entering Login ID
			Login_ID = SeleniumTools.ClearAndSetText("xpath", "//input[@id='initials']",
					"automationtest" + SeleniumTools.getRandomNumber(4));
			// Entering password
			String Password = SeleniumTools.ClearAndSetText("xpath", "//input[@name='passwordtxt']",
					SeleniumTools.getRandomString() + SeleniumTools.getRandomNumbers() + "e@");
			SeleniumTools.ClearAndSetText("xpath",
					"//label[text()='Confirm Password ']/following::input[@type='password']", Password);
			CoreUtil.imSleepy(2000);
			SeleniumTools.ClearAndSetText("xpath", "//input[@name='emailtxt']", "automationtest@yopmail.com");
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save']");
			CoreUtil.imSleepy(10000);
			SeleniumTools.ClearAndSetTextEnter("xpath", "//div[@id='menuHeader']//input[@placeholder='Search...']",
					UserName);
			CoreUtil.imSleepy(5000);
			
			// ******** Logout And ReLogin ******************//
			Login.LogoutandReLogin(Login_ID,Password);
			// Password reset
			SeleniumTools.ClearAndSetText("xpath", "//input[@name='OldPassTxt']", Password);
			Update_Pswd = SeleniumTools.ClearAndSetText("xpath", "//input[@name='NewPassTxt']",
					SeleniumTools.getRandomString() + SeleniumTools.getRandomNumbers() + "e@");
			SeleniumTools.ClearAndSetText("xpath", "//input[@name='VerifyPass']", Update_Pswd);
			SeleniumTools.clickOnObject("xpath", "//input[@name='Save']");
			CoreUtil.imSleepy(40000);

			// ******** Logout And ReLogin ******************//
			Login.LogoutandReLogin(
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Password"));
			CoreUtil.imSleepy(40000);
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.hoverElement("xpath", "//li[@text='Set up']");
			SeleniumTools.hoverElement("xpath", "//span[text()='System']");
			SeleniumTools.hoverElement("xpath", "//span[text()='User/Group']");
			SeleniumTools.clickOnObject("xpath", "//span[text()='User/Group Maintenance']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.ClearAndSetTextEnter("xpath", "//div[@id='menuHeader']//input[@placeholder='Search...']",
					UserName);
			SeleniumTools.clickOnObject("xpath", "//span[contains(text(),'"+UserName+"')]");
			//Clicking on User info
			SeleniumTools.clickOnObject("xpath", "//span[contains(text(),'User Info')]");
			//Clicking on Archive
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Archive']");
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.clickOnObject("xpath", "//button[text()='OK']");
			CoreUtil.imSleepy(15000);
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.ClearAndSetTextEnter("xpath", "//div[@id='menuHeader']//input[@placeholder='Search...']",
					UserName);
			Actual = SeleniumTools.getText("xpath", "//div[@id='UserContainerPanel']/div[2]");
			assertEquals(Actual, "No Records Found");
			
		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
			Assert.fail();
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}
	@Test(description="Verity - Provider - Credentials/Licenses - VerifyNow!")
	public static void Verity_SG_05() {
		try {
			Login.StageLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Password"));
			// Verifying Global mode
			Login.CheckMode("Verity QA Team1", "global");
			// ****Clicking on Search
			// Clicking on search
			SeleniumTools.hoverElement("xpath", "//li[@text='Search'] [@class='k-item k-state-default']");
			// Clicking on Provider
			SeleniumTools.clickOnObject("xpath", "//span[text()='Provider']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			// ********** clicking on search
			SeleniumTools.ClearAndSetTextEnter("xpath", "//div[@class='row search-input input-group']/input",
					"Ann Connor");
			// Clicking on Credentials
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='tree-container k-widget k-treeview']//span[contains(text(),'Credentials/Licenses')]");
			CoreUtil.imSleepy(10000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			// Clicking on existing record
			SeleniumTools.clickOnObject("xpath", "//div[@class='k-grid-content k-auto-scrollable']/table/tbody/tr[1]");
			CoreUtil.imSleepy(10000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");
			SeleniumTools.scrollToElementAndClick("xpath", "//select[@name='#psvMode']");

			// SeleniumTools.selectByText("xpath", "//select[@name='#psvMode']","Sanction");
			// SeleniumTools.selectByText("xpath",
			// "//select[@name='#psvMode']","Expirable");
			// SeleniumTools.selectByText("xpath", "//select[@name='#psvMode']","Normal");

			SeleniumTools.verifyDropdown("xpath", "//select[@name='#psvMode']", "Sanction");
			SeleniumTools.verifyDropdown("xpath", "//select[@name='#psvMode']", "Normal");
			SeleniumTools.verifyDropdown("xpath", "//select[@name='#psvMode']", "Expirable");
			// Clicking on ************** Verify Now!************
			SeleniumTools.clickOnObject("xpath", "//input[@name='VerifyNow']");
			CoreUtil.imSleepy(10000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frame-modal-clone-1']");
			SeleniumTools.verifyText("xpath", "//h1[text()='Verification Result']", "Verification Result");
			// Clicking on Save
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save All']");
			SeleniumTools.clickOnObject("xpath", "//button[text()='OK']");

		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
			Assert.fail();
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}
	
	@Test(description="Facility - Add")
	public static void Verity_SG_06() {
		try {
			Login.StageLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Password"));
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			// Clicking on logo/image
			SeleniumTools.clickOnObject("xpath", "//img[@ng-click='nav.knowledgeNow ? return : nav.showHome()']");
			// Clicking on Mousehover element
			SeleniumTools.clickOnObject("xpath", "//div[@ng-mouseleave='nav.onFacilityLinkLeave()']/div[@id='facility']");
			CoreUtil.imSleepy(10000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frame-modal-clone-1']");
			//Clicking on AddNew
			SeleniumTools.clickOnObject("xpath", "//button[text()='Add New']");
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frame-modal-clone-2']");

			//Enter Code
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='fac_cd']", SeleniumTools.getRandomNumbers());
			Fac_Name = SeleniumTools.ClearAndSetText("xpath", "//input[@id='full_name']", "Test_Facility"+SeleniumTools.getRandomNumber(3));
			
			// Click on Education Type
			//SeleniumTools.clickOnObject("xpath", "//echo-lookup[@model='formFields.link_educ']//button");
			//select the Electronic Funds Transfer radio button
			SeleniumTools.clickOnObject("xpath", "//input[@id='electronicFundsTransfer']");
			//Click on Save
			SeleniumTools.clickOnObject("xpath", "//button[@ng-click='model.saveForm.onClick()']");
			CoreUtil.imSleepy(10000);
			SeleniumTools.switchToDefaultFrame();
			//Close window
			SeleniumTools.clickOnObject("xpath", "//h4[text()='Change Facility']/preceding-sibling::button");
			CoreUtil.imSleepy(5000);
			SeleniumTools.switchToFrame("id", "mainFrame");
			// Navigate to setup->user/group maintenance
			SeleniumTools.hoverElement("xpath", "//li[@text='Set up']");
			SeleniumTools.hoverElement("xpath", "//span[text()='System']");
			SeleniumTools.hoverElement("xpath", "//span[text()='User/Group']");
			SeleniumTools.clickOnObject("xpath", "//span[text()='User/Group Maintenance']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			// Search for the user
			SeleniumTools.ClearAndSetTextEnter("xpath", "//div[@id='menuHeader']//input[@placeholder='Search...']",
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Username"));
			// Clicking on user
			SeleniumTools.clickOnObject("xpath",
					"//ul[@class='k-group k-treeview-lines']//span[contains(text(),'global')]");
			// Clicking on Entity Security
			SeleniumTools.clickOnObject("xpath", "//span[contains(text(),'Entity Security')]");
			SeleniumTools.clickOnObject("xpath", "//select[@id='facilitySelect']/../span");
			SeleniumTools.ClearAndSetText("xpath", "//input[@aria-owns='facilitySelect_listbox']", Fac_Name);
			CoreUtil.imSleepy(5000);
			// Select Facility
			SeleniumTools.clickOnObject("xpath", "//span[text()='"+Fac_Name+"']");
			CoreUtil.imSleepy(5000);
			//Check All
			SeleniumTools.clickOnObject("xpath", "//div[@class='col-xs-6 pull-left']/button[text()='Check All']");
			//Save
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save']");
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.clickOnObject("xpath", "//button[text()='OK']");
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			
			//Click on patient area Security **********
			SeleniumTools.clickOnObject("xpath", "//span[text()='Patient Area Security']");
			//Facility checked
			SeleniumTools.clickOnObject("xpath", "//label[text()='Facility']");
			//Check All
			SeleniumTools.clickOnObject("xpath", "//div[@id='patient-area-security-tab']//button[text()='Check All']");
			//Save
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save']");
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.clickOnObject("xpath", "//button[text()='OK']");
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			
			//Click on Contract Security **********
			SeleniumTools.clickOnObject("xpath", "//span[text()='Contract Security']");
			//Check All
			SeleniumTools.clickOnObject("xpath", "//div[@id='contract-security-tab']//button[text()='Check All']");
			//Save
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save']");
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.clickOnObject("xpath", "//button[text()='OK']");
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");

			//Click on User Branding  **********
			SeleniumTools.clickOnObject("xpath", "//span[text()='User Branding']");
			//Check All
			SeleniumTools.clickOnObject("xpath", "//brand-levelaccessbrandinggridcontrol[@class='ng-isolate-scope']//button[text()='Check All']");
			//Except managed care
			SeleniumTools.clickOnObject("xpath", "//label[text()='Managed Care']");
			//Save
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save']");
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.clickOnObject("xpath", "//button[text()='OK']");
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			
			//Click on Record Status  **********
			SeleniumTools.clickOnObject("xpath", "//span[text()='Record Status']");
			//Check All
			SeleniumTools.clickOnObject("xpath", "//recordstatusgridcontrol[@class='ng-isolate-scope']//input[@value='Check All']");
			//Save
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save']");
			
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.clickOnObject("xpath", "//button[text()='OK']");
			// ******** Logout And ReLogin ******************//
			Login.LogoutandReLogin(
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Password"));
			CoreUtil.imSleepy(40000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			// Clicking on logo/image
			SeleniumTools.clickOnObject("xpath", "//img[@ng-click='nav.knowledgeNow ? return : nav.showHome()']");
			// Clicking on Mousehover element
			SeleniumTools.clickOnObject("xpath", "//div[@ng-mouseleave='nav.onFacilityLinkLeave()']/div[@id='facility']");
			
			CoreUtil.imSleepy(10000);
			SeleniumTools.switchToFrame("id", "frame-modal-clone-1");
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@id='lookup-text-search']", Fac_Name);
			CoreUtil.imSleepy(5000);
			//Clcik on Edit
			SeleniumTools.clickOnObject("xpath", "//td[text()='"+Fac_Name+"']/following-sibling::td/a[text()='Edit']");
			CoreUtil.imSleepy(10000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.switchToFrame("id", "frame-modal-clone-2");
			//Delete facility
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Delete']");
			SeleniumTools.clickOnObject("xpath", "//echo-interrupt[@modal='interruptModal']//a[text()='Delete']");
			CoreUtil.imSleepy(5000);
			
		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
			Assert.fail();
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}
	
	@Test(description="Facility - Change Facility - Save")
	public static void Verity_SG_07() {
		try {
			Login.StageLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Password"));
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			// Clicking on logo/image
			SeleniumTools.clickOnObject("xpath", "//img[@ng-click='nav.knowledgeNow ? return : nav.showHome()']");
			// Clicking on Mousehover element
			SeleniumTools.clickOnObject("xpath", "//div[@ng-mouseleave='nav.onFacilityLinkLeave()']/div[@id='facility']");
			
			CoreUtil.imSleepy(10000);
			SeleniumTools.switchToFrame("id", "frame-modal-clone-1");
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@id='lookup-text-search']", "test");
			CoreUtil.imSleepy(5000);
			//Clcik on Edit
			SeleniumTools.clickOnObject("xpath", "//td[text()='test']/following-sibling::td/a[text()='Edit']");
			CoreUtil.imSleepy(10000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.switchToFrame("id", "frame-modal-clone-2");
			//select the creditCard radio button
			SeleniumTools.clickOnObject("xpath", "//input[@id='electronicFundsTransfer']");
			
			//click on EducType
			SeleniumTools.clickOnObject("xpath", "//echo-lookup[@model='formFields.link_educ']//button");
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.switchToFrame("id", "frame-modal-clone-3");
			SeleniumTools.clickOnObject("xpath", "//td[text()='vOTHR']/preceding-sibling::td/input");
			//Submit
			SeleniumTools.clickOnObject("xpath", "//button[text()='Submit']");
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.switchToFrame("id", "frame-modal-clone-2");
			
			//Save facility
			SeleniumTools.clickOnObject("xpath", "//form[@id='FacilityEditController']//span[text()=' Save']");
			CoreUtil.imSleepy(30000);
			SeleniumTools.switchToDefaultFrame();
			//Close window
			SeleniumTools.clickOnObject("xpath", "//h4[text()='Change Facility']/preceding-sibling::button");
			CoreUtil.imSleepy(5000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			// Clicking on logo/image
			SeleniumTools.clickOnObject("xpath", "//img[@ng-click='nav.knowledgeNow ? return : nav.showHome()']");
			
		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
			Assert.fail();
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}
	@Test(description="Set Up - System - Configuration - System Parameters - Save"
			+ "Set Up - System - Configuration - System Parameters - Modify ")
	public static void Verity_SG_08() {
		try {
			Login.StageLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Password"));
			Login.CheckMode("Verity QA Team1", "global");
			// Clicking on logo/image
			SeleniumTools.clickOnObject("xpath", "//img[@ng-click='nav.knowledgeNow ? return : nav.showHome()']");
			// ****************** 1) No error should display Without modify and save **********************
			//Click on setup
			SeleniumTools.hoverElement("xpath", "//li[@text='Set up']");
			SeleniumTools.hoverElement("xpath", "//span[text()='System']");
			
			// Configuration
			SeleniumTools.hoverElement("xpath", "//span[text()='System']/parent::li//li[@text='Configuration']/span[text()='Configuration']");
			// System parameters
			SeleniumTools.clickOnObject("xpath", "//span[text()='System Parameters']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			CoreUtil.imSleepy(5000);
			// SAVE
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save ']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.switchToFrame("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.clickOnObject("xpath", "//img[@ng-click='nav.knowledgeNow ? return : nav.showHome()']");
			
			
			// ****************** 2) No error should display After modify and save **********************
			// Click on setup
			SeleniumTools.hoverElement("xpath", "//li[@text='Set up']");
			SeleniumTools.hoverElement("xpath", "//span[text()='System']");

			// Configuration
			SeleniumTools.hoverElement("xpath",
					"//span[text()='System']/parent::li//li[@text='Configuration']/span[text()='Configuration']");
			// System parameters
			SeleniumTools.clickOnObject("xpath", "//span[text()='System Parameters']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			CoreUtil.imSleepy(5000);
			
			//Edit Group Spaces in Listings
			SeleniumTools.ClearAndSetText("xpath", "//label[text()='Group Spaces in Listings']/following-sibling::echo-input/div/span[1]/span/input[1]", SeleniumTools.getRandomNumbers());

			//Edit Customer id 
			// SeleniumTools.clickOnObject("xpath", "//label[text()='Customer ID']/following-sibling::echo-input/div/input");
			// SeleniumTools.ClearAndSetText("xpath", "//label[text()='Customer ID']/following-sibling::echo-input/div/input", SeleniumTools.getRandomNumbers());
			
			// SAVE
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save ']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.switchToFrame("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.clickOnObject("xpath", "//img[@ng-click='nav.knowledgeNow ? return : nav.showHome()']");
			
		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
			Assert.fail();
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}
	@Test(description="Verity / Provider / National Student Clearinghouse Query")
	public static void Verity_SG_09() {
		try {
			Login.StageLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Password"));
			Login.CheckMode("Verity QA Team1", "global");
			// Click on setup
			SeleniumTools.hoverElement("xpath", "//li[@text='Set up']");
			SeleniumTools.hoverElement("xpath", "//span[text()='System']");
			
			// Configuration
			SeleniumTools.hoverElement("xpath",
					"//span[text()='System']/parent::li//li[@text='Configuration']/span[text()='Configuration']");
			// System parameters
			SeleniumTools.clickOnObject("xpath", "//span[text()='System Parameters']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			CoreUtil.imSleepy(5000);
			// Click on Integrated Accounts
			SeleniumTools.clickOnObject("xpath", "//a[text()='Integrated Accounts']");
			CoreUtil.imSleepy(5000);
			// Enter id
			SeleniumTools.clickOnObject("xpath", "//input[@id='nsc_id']");
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='nsc_id']", "10051384");
			// Enter username
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='nsc_username']", "ECHOXML");
			// Enter password
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='nsc_password']", "l51fE@jL3VP4Szo");
			
			// SAVE
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save ']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.switchToFrame("xpath", "//iframe[@id='mainFrame']");
			
			// Clicking on search
			SeleniumTools.hoverElement("xpath", "//li[@text='Search'] [@class='k-item k-state-default']");
			// Clicking on Provider
			SeleniumTools.clickOnObject("xpath", "//span[text()='Provider']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			// ********** clicking on search
			SeleniumTools.ClearAndSetTextEnter("xpath", "//div[@class='row search-input input-group']/input",
					"Suresh Goparaju");
			// Clicking on Education
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='tree-container k-widget k-treeview']//span[contains(text(),'Education')]");
			CoreUtil.imSleepy(5000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			// Clicking on record
			SeleniumTools.clickOnObject("xpath", "//div[@class='k-grid-content k-auto-scrollable']//table//tr[1]");
			CoreUtil.imSleepy(10000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");
			// Click on Send
			SeleniumTools.clickOnObject("xpath", "//input[@value='Send']");
			CoreUtil.imSleepy(10000);
			SeleniumTools.switchToFrame("xpath", "//iframe[@id='frame-modal-clone-1']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='extended-iframe']");
			// Click on Save
			SeleniumTools.clickOnObject("xpath",
					"//button[@class='btn btn-success footer-btn save-button']/span[text()=' Save']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.switchToDefaultFrame();
			// close
			SeleniumTools.clickOnObject("xpath", "//div[@id='modal-clone-1']//button[@class='close']/span");

			SeleniumTools.switchToFrame("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			// Clicking on Verification
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='tree-container k-widget k-treeview']//span[contains(text(),'Verifications')]");
			CoreUtil.imSleepy(5000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			// Clicking on record
			SeleniumTools.clickOnObject("xpath", "//div[@class='k-grid-content k-auto-scrollable']//table//tr[1]");
			CoreUtil.imSleepy(5000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");
			// Delete
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Delete']");
			// Confirm Delete
			SeleniumTools.clickOnObject("xpath", "//echo-interrupt[@class='ng-isolate-scope']//a[text()='Delete']");
			CoreUtil.imSleepy(2000);

		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
			Assert.fail();
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}
	
	@Test(description="Search - Provider - Credentialing History - Category - Modify", enabled=false)
	public static void Verity_SG_10() {
		try {
			Login.StageLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Password"));
			Login.CheckMode("Verity QA Team1", "global");
			// Clicking on search
			SeleniumTools.hoverElement("xpath", "//li[@text='Search'] [@class='k-item k-state-default']");
			// Clicking on Provider
			SeleniumTools.clickOnObject("xpath", "//span[text()='Provider']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			// ********** clicking on search
			SeleniumTools.ClearAndSetTextEnter("xpath", "//div[@class='row search-input input-group']/input",
					"Global Automation");
			// Clicking on Credentialing History
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='tree-container k-widget k-treeview']//span[contains(text(),'Credentials/Licenses')]");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			// Clicking on Add Credentialing
			SeleniumTools.clickOnObject("xpath", "//button[contains(text(),'Add Credentialing History')]");
			CoreUtil.imSleepy(10000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			// Clicking on category ellipses
			SeleniumTools.clickOnObject("xpath", "//echo-lookup[@model='formFields.staff_cd']/div/span/button");
			SeleniumTools.switchToFrame("xpath", "//iframe[@id='frame-modal-clone-1']");
			// Select status
			SeleniumTools.clickOnObject("xpath", "//td[contains(text(),'Active Associate Attending')]");

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			// ********* Click on Save
			SeleniumTools.clickOnObject("xpath", "//button[@ng-show='model.saveForm.show']");
			CoreUtil.imSleepy(15000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");
			SeleniumTools.clickOnObject("xpath", "//echo-lookup[@model='formFields.staff_cd']/div/input");
			Actual = SeleniumTools.getAttribute("xpath", "//echo-lookup[@model='formFields.staff_cd']/div/input","value");
			assertEquals(Actual, "Active Associate Attending");
			
			// ******** Deleting Credentialing History ****
			SeleniumTools.clickOnObject("xpath", "//echo-form-footer[@model='formFooterModel']/div/div/button");
			// Clciking on Confirm Delete
			SeleniumTools.clickOnObject("xpath",
					"//form[@ng-controller='CredentialingHistoryController']/div/div/echo-interrupt[2]/div/div/div/div[2]/div/a[2]");
			CoreUtil.imSleepy(4000);

		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
			Assert.fail();
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}
}
