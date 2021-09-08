package testSuite;

import java.lang.reflect.Method;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import utility.Constants;
import utility.CoreUtil;
import utility.LoggerUtil;
import utility.Login;
import utility.SeleniumTools;
import utility.TestInit;

public class Megaseed_SmokeTest extends TestInit {
	public String Org_Name, ID_Number, Screen_Extenders, Screen_Extenders2, Survey_Name, Expected, Update_Pswd,
			Forgot_PasswordSeed;
	Boolean actual;
	static LoggerUtil loggerUtil = new LoggerUtil(Megaseed_SmokeTest.class);

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
			// SeleniumTools.quitBrowser();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(description = "Add, modify, delete Organization")
	public void VS_GM_01() {
		try {
			// Login to the Megaseed Application
			Login.MegaseedLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_G_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_G_Password"));
			// Verifying global mode
			Login.CheckMode("Global MEGASEED FOR QA", "global");
			// ****Clicking on Organizations
			SeleniumTools.clickOnObject("xpath", "//span[text()='Organizations']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Organizations']");
			// ********** clicking on New
			SeleniumTools.clickOnObject("xpath", "//div[@class='dropdown pull-left ng-hide']/following-sibling::p[2]");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='contentFrame']");

			// *****clicking on Region
			SeleniumTools.clickOnObject("xpath",
					"//span[text()='Facility Information']/parent::div/../div[2]/div[1]/echo-lookup/div[1]/span");
			// Search region
			SeleniumTools.switchToFrame("xpath", "//iframe[@id='frame-modal-clone-1']");
			// Select region test
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='col-xs-12 grid-col']/div[1]/div[2]/table/tbody/tr[1]/td[1]");

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Organizations']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='contentFrame']");

			// ******* clicking on Region status
			SeleniumTools.clickOnObject("xpath",
					"//span[text()='Facility Information']/parent::div/../div[2]/div[2]/echo-lookup/div[1]/span");
			SeleniumTools.switchToFrame("xpath", "//iframe[@id='frame-modal-clone-1']");
			// Select status
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='col-xs-12 grid-col']/div[1]/div[2]/table/tbody/tr[1]/td[1]");

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Organizations']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='contentFrame']");

			// Clciking on organization Name
			Org_Name = SeleniumTools.ClearAndSetText("xpath", "//echo-input[@model='formFields.location']/div/input",
					"Jack&Jons" + "_" + SeleniumTools.getRandomString());

			// ******* Clicking on Address Line
			SeleniumTools.ClearAndSetText("xpath", "//echo-input[@model='formFields.address1']/div/input",
					"9605 Scranton Road, Suite 200" + SeleniumTools.getRandomNumbers());
			SeleniumTools.ClearAndSetText("xpath", "//echo-input[@model='formFields.zip']/div/div/input", "92121");

			// ********* Click on Save
			SeleniumTools.clickOnObject("xpath", "//button[@ng-show='model.saveForm.show']");
			CoreUtil.imSleepy(40000);

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Organizations']");
			// clicking on search for organization
			SeleniumTools.ClearAndSetTextEnter("xpath",
					"//div[@class='input-group-btn dropdown']/following-sibling::input", Org_Name);
			// Clicking on desired organization
			SeleniumTools.clickOnObject("xpath", "//span[contains(text(),'" + Org_Name + "')]");
			// Clicking on Demographic
			SeleniumTools.clickOnObject("xpath",
					"//div[@ng-hide='showScreensLoader']/ul/li/div/span[contains(text(),'Demographics')]");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='contentFrame']");

			// *********************** Modefy organization ****************
			SeleniumTools.ClearAndSetText("xpath", "//echo-input[@model='formFields.othername']/div/input",
					SeleniumTools.getRandomString());
			SeleniumTools.clickOnObject("xpath",
					"//echo-input[@model='formFields.useothername']/div/span[1]/span[2]/span");
			// ********* Click on Save
			SeleniumTools.clickOnObject("xpath", "//button[@ng-show='model.saveForm.show']");
			CoreUtil.imSleepy(30000);

			// ************************ Delete organization**********************
			SeleniumTools.clickOnObject("xpath", "//button[@ng-show='model.deleteForm.show']");
			// Clicking on Confirm Delete
			SeleniumTools.clickOnObject("xpath",
					"//div[text()='Are you sure you wish to delete this record?']/ancestor::div[2]/div[2]/div/a[2]");
			CoreUtil.imSleepy(5000);

		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}

	@Test(description = "Add, modify, delete Organization in Facility mode")
	public void VS_FM_01() {
		try {
			// Login to the Stage Application
			Login.MegaseedLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_F_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_F_Password"));
			// Verifying facility mode
			Login.CheckMode("Alvarado", "facility");
			// ****Clicking on Organizations
			SeleniumTools.clickOnObject("xpath", "//span[text()='Organizations']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Organizations']");
			// ********** clicking on New
			SeleniumTools.clickOnObject("xpath", "//div[@class='dropdown pull-left ng-hide']/following-sibling::p[2]");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='contentFrame']");

			// *****clicking on Region
			SeleniumTools.clickOnObject("xpath",
					"//span[text()='Facility Information']/parent::div/../div[2]/div[1]/echo-lookup/div[1]/span");
			// Search region
			SeleniumTools.switchToFrame("xpath", "//iframe[@id='frame-modal-clone-1']");
			// Select region test
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='col-xs-12 grid-col']/div[1]/div[2]/table/tbody/tr[1]/td[1]");

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Organizations']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='contentFrame']");

			// ******* clicking on Region status
			SeleniumTools.clickOnObject("xpath",
					"//span[text()='Facility Information']/parent::div/../div[2]/div[2]/echo-lookup/div[1]/span");
			SeleniumTools.switchToFrame("xpath", "//iframe[@id='frame-modal-clone-1']");
			// Select status
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='col-xs-12 grid-col']/div[1]/div[2]/table/tbody/tr[1]/td[1]");

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Organizations']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='contentFrame']");

			// Clciking on organization Name
			Org_Name = SeleniumTools.ClearAndSetText("xpath", "//echo-input[@model='formFields.location']/div/input",
					"Jack&Jons" + "_" + SeleniumTools.getRandomString());

			// ******* Clicking on Address Line
			SeleniumTools.ClearAndSetText("xpath", "//echo-input[@model='formFields.address1']/div/input",
					"2435 Fair Oaks Blvd " + SeleniumTools.getRandomNumbers());
			SeleniumTools.ClearAndSetText("xpath", "//echo-input[@model='formFields.zip']/div/div/input", "92121");

			// ********* Click on Save
			SeleniumTools.clickOnObject("xpath", "//button[@ng-show='model.saveForm.show']");
			CoreUtil.imSleepy(40000);

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Organizations']");
			// clicking on search for organization
			SeleniumTools.ClearAndSetTextEnter("xpath",
					"//div[@class='input-group-btn dropdown']/following-sibling::input", Org_Name);
			// Clicking on desired organization
			SeleniumTools.clickOnObject("xpath", "//span[contains(text(),'" + Org_Name + "')]");
			// Clicking on Demographic
			SeleniumTools.clickOnObject("xpath",
					"//div[@ng-hide='showScreensLoader']/ul/li/div/span[contains(text(),'Demographics')]");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='contentFrame']");

			// *********************** Modify organization ****************
			SeleniumTools.ClearAndSetText("xpath", "//echo-input[@model='formFields.othername']/div/input",
					SeleniumTools.getRandomString());
			SeleniumTools.clickOnObject("xpath",
					"//echo-input[@model='formFields.useothername']/div/span[1]/span[2]/span");
			// ********* Click on Save
			SeleniumTools.clickOnObject("xpath", "//button[@ng-show='model.saveForm.show']");
			CoreUtil.imSleepy(30000);

			// ************************ Delete organization**********************
			SeleniumTools.clickOnObject("xpath", "//button[@ng-show='model.deleteForm.show']");
			// Clicking on Confirm Delete
			SeleniumTools.clickOnObject("xpath",
					"//div[text()='Are you sure you wish to delete this record?']/ancestor::div[2]/div[2]/div/a[2]");
			CoreUtil.imSleepy(5000);

		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}

	@Test(description = "Add, modify, delete Facility from provider record in global mode")
	public void VS_GM_02() {
		try {
			// Login to the Megaseed Application
			Login.MegaseedLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_G_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_G_Password"));
			// Verifying global mode
			Login.CheckMode("Global MEGASEED FOR QA", "global");
			// ****Clicking on Search
			SeleniumTools.clickOnObject("xpath", "//div[@id='horizontal-menu-container']/ul/li[2]");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			// ********** clicking on search
			SeleniumTools.ClearAndSetTextEnter("xpath", "//div[@class='row search-input input-group']/input",
					"Global Automation");
			// Clicking on region
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='tree-container k-widget k-treeview']/ul/li/div/span[contains(text(),'Facilities')]");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			// Clicking on Add Region
			SeleniumTools.clickOnObject("xpath",
					"//echo-header-simple[@header='ctrl.headerModel']/div/div[2]/div/button[3]");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");
			// Clicking on Region
			SeleniumTools.clickOnObject("xpath", "//echo-lookup[@model='formFields.fac_cd']/div/span/button");
			// Search region
			SeleniumTools.switchToFrame("xpath", "//iframe[@id='frame-modal-clone-1']");
			// Select region test
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='col-xs-12 grid-col']/div[1]/div[2]/table/tbody/tr[1]/td[1]");
			CoreUtil.imSleepy(5000);

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			// Clicking on ID number
			ID_Number = SeleniumTools.ClearAndSetText("xpath", "//input[@title='Variable: F_ID_Numbr ... ']",
					SeleniumTools.getRandomNumbers());
			// Clciking on Status
			SeleniumTools.clickOnObject("xpath", "//echo-lookup[@model='formFields.active123']/div/span/button");
			SeleniumTools.switchToFrame("xpath", "//iframe[@id='frame-modal-clone-1']");
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='col-xs-12 grid-col']/div[1]/div[2]/table/tbody/tr[1]/td[1]");

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.switchToFrame("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");
			// ********* Click on Save
			SeleniumTools.clickOnObject("xpath", "//button[@ng-show='model.saveForm.show']");
			CoreUtil.imSleepy(40000);

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.switchToFrame("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			// *********** Modifying Facility ***********
			SeleniumTools.clickOnObject("xpath",
					"//table[@role='treegrid']/tbody/tr/td/span[text()='" + ID_Number + "      ']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");
			SeleniumTools.ClearAndSetText("xpath", "//input[@title='Variable: F_ID_Numbr ... ']",
					SeleniumTools.getRandomNumbers());
			// ********* Click on Save
			SeleniumTools.clickOnObject("xpath", "//button[@ng-show='model.saveForm.show']");
			CoreUtil.imSleepy(40000);

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.switchToFrame("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			// ******** Deleting Facility ****
			SeleniumTools.clickOnObject("xpath", "//echo-form-footer[@model='formFooterModel']/div/div/button");
			// Clciking on Confirm Delete
			SeleniumTools.clickOnObject("xpath",
					"//form[@ng-controller='FacilityController']/div/div/echo-interrupt[2]/div/div/div/div[2]/div/a[2]");
			CoreUtil.imSleepy(4000);

		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}

	@Test(description = "Add, modify, delete Facility from provider record in Facility mode")
	public void VS_FM_02() {
		try {
			// Login to the Stage Application
			Login.MegaseedLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_F_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_F_Password"));
			// Verifying facility mode
			Login.CheckMode("Alvarado", "facility");
			// ****Clicking on Search
			SeleniumTools.clickOnObject("xpath", "//div[@id='horizontal-menu-container']/ul/li[2]");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			// ********** clicking on search
			SeleniumTools.ClearAndSetTextEnter("xpath", "//div[@class='row search-input input-group']/input",
					"Facility Automation");
			// Clicking on region
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='tree-container k-widget k-treeview']/ul/li/div/span[contains(text(),'Facilities')]");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			// Clicking on Add Region
			SeleniumTools.clickOnObject("xpath",
					"//echo-header-simple[@header='ctrl.headerModel']/div/div[2]/div/button[3]");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");
			// Clicking on Region
			SeleniumTools.clickOnObject("xpath", "//echo-lookup[@model='formFields.fac_cd']/div/span/button");
			// Search region
			SeleniumTools.switchToFrame("xpath", "//iframe[@id='frame-modal-clone-1']");
			// Select region test
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='col-xs-12 grid-col']/div[1]/div[2]/table/tbody/tr[1]/td[1]");

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			// Clicking on ID number
			ID_Number = SeleniumTools.ClearAndSetText("xpath", "//input[@title='Variable: F_ID_Numbr ... ']",
					SeleniumTools.getRandomNumbers());
			// Clciking on Status
			SeleniumTools.clickOnObject("xpath", "//echo-lookup[@model='formFields.active123']/div/span/button");
			SeleniumTools.switchToFrame("xpath", "//iframe[@id='frame-modal-clone-1']");
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='col-xs-12 grid-col']/div[1]/div[2]/table/tbody/tr[1]/td[1]");

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.switchToFrame("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");
			// ********* Click on Save
			SeleniumTools.clickOnObject("xpath", "//button[@ng-show='model.saveForm.show']");
			CoreUtil.imSleepy(40000);

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.switchToFrame("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			// *********** Modifying Facility ***********
			SeleniumTools.clickOnObject("xpath",
					"//table[@role='treegrid']/tbody/tr/td/span[text()='" + ID_Number + "      ']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");
			SeleniumTools.ClearAndSetText("xpath", "//input[@title='Variable: F_ID_Numbr ... ']",
					SeleniumTools.getRandomNumbers());
			// ********* Click on Save
			SeleniumTools.clickOnObject("xpath", "//button[@ng-show='model.saveForm.show']");
			CoreUtil.imSleepy(40000);

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.switchToFrame("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			// ******** Deleting Facility ****
			SeleniumTools.clickOnObject("xpath", "//echo-form-footer[@model='formFooterModel']/div/div/button");
			// Clciking on Confirm Delete
			SeleniumTools.clickOnObject("xpath",
					"//form[@ng-controller='FacilityController']/div/div/echo-interrupt[2]/div/div/div/div[2]/div/a[2]");
			CoreUtil.imSleepy(4000);

		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}

	@Test(description = "Add, modify, delete Reference from provider record in Global Mode")
	public void VS_GM_03() {
		try {

			// Login to the Megaseed Application
			Login.MegaseedLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_G_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_G_Password"));
			// Verifying global mode
			Login.CheckMode("Global MEGASEED FOR QA", "global");

			// Click on Search
			SeleniumTools.clickOnObject("xpath", "//i[@title='Search']/following-sibling::span[text()='Search']");
			// CoreUtil.imSleepy(20000);

			// Click on Search All
			SeleniumTools.clickOnObject("xpath", "//span[text()='Search All']");
			CoreUtil.imSleepy(3000);
			// Switch to Frame
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");

			// Enter Value in search box
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@type='text']", "Automation");
			CoreUtil.imSleepy(5000);

			SeleniumTools.clickOnObject("xpath", "//span[text()='Global, Automation,']");
			CoreUtil.imSleepy(5000);

			// Select Provider name
			SeleniumTools.ScrollTo("xpath", "//span[contains(text(),'References')]");
			CoreUtil.imSleepy(10000);
			SeleniumTools.clickOnObject("xpath", "//span[contains(text(),'References')]");
			CoreUtil.imSleepy(10000);

			// Select References
			// SeleniumTools.clickOnObject("xpath", "//span[@class='k-in
			// k-state-focused']");
			// Thread.sleep(20000);
			// ---------with my user
			// ---->SeleniumTools.clickOnObject("xpath", "//span[@class='k-state-selected
			// k-in']");
			// ---->SeleniumTools.clickOnObject("xpath", "//span[@class='k-in
			// k-state-selected']");
			// Thread.sleep(20000);

			// Click on Add Reference button
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");

			SeleniumTools.clickOnObject("xpath", "//button[@class='btn btn-success ng-binding']");
			CoreUtil.imSleepy(10000);

			// Wait for Frame
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			// Enter Sal
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='sal']", "001");
			CoreUtil.imSleepy(5000);

			// Enter First Name
			// SeleniumTools.waitForFrameToBeAvailable("xpath",
			// "//iframe[@id='FormFrame']");
			// Thread.sleep(40000);
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='fname']", "Mark Shankar");
			CoreUtil.imSleepy(3000);

			// Enter Last Name
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='lname']", "Pawanovich");
			CoreUtil.imSleepy(3000);

			// Enter Suffix
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='suffix']", "6789");
			CoreUtil.imSleepy(3000);

			// Enter Title
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='title']", "Verity");
			CoreUtil.imSleepy(3000);

			// Select Reference Type(Global)
			SeleniumTools.clickOnObject("xpath",
					"//body/form[@id='ReferencesController']/div[1]/div[1]/custom-form[1]/section[1]/div[4]/div[1]/div[1]/div[1]/div[1]/echo-lookup[1]/div[1]/span[1]/button[1]");
			CoreUtil.imSleepy(10000);

			// Frame
			SeleniumTools.switchToFrame("xpath", "//iframe[@id='frame-modal-clone-1']");
			// SeleniumTools.waitForFrameToBeAvailable("xpath",
			// "//iframe[@id='frame-modal-clone-1']");

			SeleniumTools.ClearAndSetText("xpath", "//input[@id='lookup-text-search']", "Current Peer Reference");
			CoreUtil.imSleepy(10000);

			// Click on Search Button
			SeleniumTools.clickOnObject("xpath", "//input[@type='button' and @value='Search']");
			CoreUtil.imSleepy(10000);

			// Select from search
			// SeleniumTools.clickOnObject("xpath", "//td[text()='vPEER']");
			// SeleniumTools.ClearAndSetText("xpath", "//input[@id='lname']",
			// "//input[@id='organizate']");
			// Thread.sleep(20000);

			// Click on Save button
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save']");
			CoreUtil.imSleepy(20000);

			// Edit the Reference
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			SeleniumTools.ClearAndSetText("xpath", "//input[@id='organizate']", "HealthFusion");
			CoreUtil.imSleepy(3000);

			SeleniumTools.ClearAndSetText("xpath", "//input[@id='relationship']", "Brother");
			CoreUtil.imSleepy(3000);

			// SeleniumTools.ClearAndSetText("xpath", "//input[@class='form-control
			// ng-pristine ng-valid ng-isolate-scope ng-valid-maxlength ng-touched']",
			// "SaltLake City");

			// Click on Save
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save']");
			CoreUtil.imSleepy(10000);

			// Click on Delete
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			SeleniumTools.clickOnObject("xpath", "//span[text()=' Delete']");
			CoreUtil.imSleepy(20000);

			// Confirm Delete
			SeleniumTools.clickOnObject("xpath",
					"//echo-interrupt[@class='ng-isolate-scope']//div[@class='btn-group pull-right']//a[text()='Delete']");
			CoreUtil.imSleepy(20000);

		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}

	@Test(description = "Add, modify, delete Reference from provider record in Facility Mode")
	public void VS_FM_03() {
		try {
			// Login to the Stage Application
			Login.MegaseedLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_F_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_F_Password"));

			// Verifying facility mode
			Login.CheckMode("Alvarado", "facility");

			// Click on Search
			SeleniumTools.clickOnObject("xpath", "//i[@title='Search']/following-sibling::span[text()='Search']");
			CoreUtil.imSleepy(10000);

			// Click on Search All
			SeleniumTools.clickOnObject("xpath", "//span[text()='Search All']");
			CoreUtil.imSleepy(10000);
			// Switch to Frame
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			// Enter Value in search box
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@type='text']", "Facility");
			CoreUtil.imSleepy(10000);
			SeleniumTools.clickOnObject("xpath", "//span[text()='Facility, Automation,']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.ScrollTo("xpath", "//span[contains(text(),'References')]");
			CoreUtil.imSleepy(10000);


			// Click on Add Reference button
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			CoreUtil.imSleepy(5000);

			SeleniumTools.clickOnObject("xpath", "//button[@class='btn btn-success ng-binding']");
			CoreUtil.imSleepy(10000);

			// // Wait for Frame
			// SeleniumTools.waitForFrameToBeAvailable("xpath",
			// "//iframe[@id='FormFrame']");
			// Thread.sleep(40000);
			//
			// // Enter Sal
			//
			// SeleniumTools.ClearAndSetText("xpath", "//input[@id='sal']", "001");
			// Thread.sleep(20000);

			// Enter First Name
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			SeleniumTools.ClearAndSetText("xpath", "//input[@id='fname']", "Mark Shankar");
			CoreUtil.imSleepy(3000);

			// Enter Last Name
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='lname']", "Pawanovich");
			CoreUtil.imSleepy(3000);

			// Enter Suffix
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='suffix']", "6789");
			CoreUtil.imSleepy(3000);

			// Enter Title
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='title']", "Verity");
			CoreUtil.imSleepy(3000);

			// Select Reference Type(Global)
			SeleniumTools.clickOnObject("xpath",
					"//body/form[@id='ReferencesController']/div[1]/div[1]/custom-form[1]/section[1]/div[4]/div[1]/div[1]/div[1]/div[1]/echo-lookup[1]/div[1]/span[1]/button[1]");
			CoreUtil.imSleepy(10000);

			// Frame
			SeleniumTools.switchToFrame("xpath", "//iframe[@id='frame-modal-clone-1']");
			// SeleniumTools.waitForFrameToBeAvailable("xpath",
			// "//iframe[@id='frame-modal-clone-1']");

			SeleniumTools.ClearAndSetText("xpath", "//input[@id='lookup-text-search']", "Current Peer Reference");
			CoreUtil.imSleepy(10000);

			// Click on Search Button
			SeleniumTools.clickOnObject("xpath", "//input[@type='button' and @value='Search']");
			CoreUtil.imSleepy(10000);

			// Select from search
			// SeleniumTools.clickOnObject("xpath", "//td[text()='vPEER']");

			// SeleniumTools.ClearAndSetText("xpath", "//input[@id='lname']",
			// "//input[@id='organizate']");
			// Thread.sleep(20000);

			// Click on Save button
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");
			// Thread.sleep(40000);

			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save']");
			CoreUtil.imSleepy(20000);

			// Edit the Reference
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			SeleniumTools.ClearAndSetText("xpath", "//input[@id='organizate']", "HealthFusion");
			CoreUtil.imSleepy(5000);

			// SeleniumTools.ClearAndSetText("xpath", "//input[@id='relationship']",
			// "Brother");

			// SeleniumTools.ClearAndSetText("xpath", "//input[@class='form-control
			// ng-pristine ng-valid ng-isolate-scope ng-valid-maxlength ng-touched']",
			// "SaltLake City");

			// Click on Save
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save']");
			CoreUtil.imSleepy(20000);

			// Click on Delete
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			SeleniumTools.clickOnObject("xpath", "//span[text()=' Delete']");
			CoreUtil.imSleepy(20000);

			// Confirm Delete
			SeleniumTools.clickOnObject("xpath",
					"//echo-interrupt[@class='ng-isolate-scope']//div[@class='btn-group pull-right']//a[text()='Delete']");
			CoreUtil.imSleepy(20000);

		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}

	@Test(description = "Add, modify, delete Workflow from provider record in Global Mode")
	public void VS_GM_04() {
		try {
			Login.MegaseedLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_G_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_G_Password"));
			// Switch To Frame
			SeleniumTools.switchToFrame("xpath", "//iframe[@id='mainFrame']");
			CoreUtil.timeStamp();
			// Verifying global mode
			Login.CheckMode("Global MEGASEED FOR QA", "global");

			// Click on Search
			SeleniumTools.clickOnObject("xpath", "//i[@title='Search']/following-sibling::span[text()='Search']");
			CoreUtil.imSleepy(10000);

			// Click on Search All
			SeleniumTools.clickOnObject("xpath", "//span[text()='Search All']");
			CoreUtil.imSleepy(20000);
			// Switch to Frame
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			CoreUtil.imSleepy(5000);
			// Enter Value in search box
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@type='text']", "Automation");
			SeleniumTools.clickOnObject("xpath", "//span[text()='Global, Automation,']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.ScrollTo("xpath",
					"//div[@class='tree-container k-widget k-treeview']//span[contains(text(),'Workflows')]");
			CoreUtil.imSleepy(10000);

			// Click on Add Workflow
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			CoreUtil.imSleepy(5000);

			SeleniumTools.clickOnObject("xpath", "//button[@class='btn btn-success ng-binding']");
			CoreUtil.imSleepy(10000);

			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");
			CoreUtil.imSleepy(5000);

			// Click Workflow dropdown
			SeleniumTools.clickOnObject("xpath",
					"//body/form[@id='WorkflowProviderAreaFormController']/div[1]/div[1]/section[1]/div[1]/div[1]/echo-lookup[1]/div[1]/span[1]/button[1]");
			CoreUtil.imSleepy(10000);

			// Select Value Window
			SeleniumTools.switchToFrame("xpath", "//iframe[@id='frame-modal-clone-1']");
			CoreUtil.imSleepy(5000);

			SeleniumTools.clickOnObject("xpath",
					"//body/form[@id='ChecklistBrowController']/div[1]/div[1]/div[1]/div[1]/ul[1]/li[1]/div[1]/span[1]");
			CoreUtil.imSleepy(10000);

			SeleniumTools.clickOnObject("xpath", "//span[@class='codetext']//b[text()='W1230']");
			CoreUtil.imSleepy(10000);

			// Click on Save button
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save']");
			CoreUtil.imSleepy(20000);

			// Edit: Fill Notes
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			SeleniumTools.ClearAndSetTextEnter("xpath", "//textarea[@id='comment']", "Due to severe Neck Pain");
			CoreUtil.imSleepy(5000);

			// Save Edited
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save']");
			CoreUtil.imSleepy(20000);

			// Click on Delete
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			SeleniumTools.clickOnObject("xpath", "//span[text()=' Delete']");
			CoreUtil.imSleepy(10000);

			// Confirm Delete
			SeleniumTools.clickOnObject("xpath",
					"//echo-interrupt[@class='ng-isolate-scope']//div[@class='btn-group pull-right']//a[text()='Delete']");
			CoreUtil.imSleepy(10000);

		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}

	@Test(description = "Add, modify, delete Workflow from provider record in Facility Mode")
	public void VS_FM_04() {
		try {
			// Login to the Megaseed Application
			Login.MegaseedLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_F_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_F_Password"));
			// Switch To Frame
			SeleniumTools.switchToFrame("xpath", "//iframe[@id='mainFrame']");

			// Verifying facility mode
			Login.CheckMode("Alvarado", "facility");

			// Click on Search
			SeleniumTools.clickOnObject("xpath", "//i[@title='Search']/following-sibling::span[text()='Search']");
			CoreUtil.imSleepy(20000);

			// Click on Search All
			SeleniumTools.clickOnObject("xpath", "//span[text()='Search All']");
			CoreUtil.imSleepy(5000);
			// Switch to Frame
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			CoreUtil.imSleepy(5000);
			// Enter Value in search box
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@type='text']", "Facility");
			CoreUtil.imSleepy(10000);
			SeleniumTools.clickOnObject("xpath", "//span[text()='Facility, Automation,']");
			CoreUtil.imSleepy(5000);

			SeleniumTools.ScrollTo("xpath",
					"//div[@class='tree-container k-widget k-treeview']//span[contains(text(),'Workflows')]");
			CoreUtil.imSleepy(20000);

			// Click on Add Workflow
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			CoreUtil.imSleepy(5000);

			SeleniumTools.clickOnObject("xpath", "//button[@class='btn btn-success ng-binding']");
			CoreUtil.imSleepy(20000);

			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");
			CoreUtil.imSleepy(5000);

			// Click Workflow dropdown
			SeleniumTools.clickOnObject("xpath",
					"//body/form[@id='WorkflowProviderAreaFormController']/div[1]/div[1]/section[1]/div[1]/div[1]/echo-lookup[1]/div[1]/span[1]/button[1]");
			CoreUtil.imSleepy(10000);

			// Select Value Window
			SeleniumTools.switchToFrame("xpath", "//iframe[@id='frame-modal-clone-1']");
			CoreUtil.imSleepy(5000);

			SeleniumTools.clickOnObject("xpath",
					"//body/form[@id='ChecklistBrowController']/div[1]/div[1]/div[1]/div[1]/ul[1]/li[1]/div[1]/span[1]");
			CoreUtil.imSleepy(10000);

			SeleniumTools.clickOnObject("xpath", "//span[@class='codetext']//b[text()='W1230']");
			CoreUtil.imSleepy(10000);

			// Click on Save button
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save']");
			CoreUtil.imSleepy(20000);

			// Edit: Fill Notes
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			SeleniumTools.ClearAndSetTextEnter("xpath", "//textarea[@id='comment']", "Due to severe Neck Pain");
			CoreUtil.imSleepy(10000);

			// Save Edited
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save']");
			CoreUtil.imSleepy(10000);

			// Click on Delete
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			SeleniumTools.clickOnObject("xpath", "//span[text()=' Delete']");
			CoreUtil.imSleepy(20000);

			// Confirm Delete
			SeleniumTools.clickOnObject("xpath",
					"//echo-interrupt[@class='ng-isolate-scope']//div[@class='btn-group pull-right']//a[text()='Delete']");
			CoreUtil.imSleepy(10000);

		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}

	@Test(description = "Add, modify, delete Checklist from provider record in Global Mode")
	public void VS_GM_05() {
		try {
			Login.MegaseedLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_G_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_G_Password"));
			// Switch To Frame
			SeleniumTools.switchToFrame("xpath", "//iframe[@id='mainFrame']");
			// Verifying global mode
			Login.CheckMode("Global MEGASEED FOR QA", "global");

			// Click on Search
			SeleniumTools.clickOnObject("xpath", "//i[@title='Search']/following-sibling::span[text()='Search']");
			CoreUtil.imSleepy(10000);

			// Click on Search All
			SeleniumTools.clickOnObject("xpath", "//span[text()='Search All']");
			CoreUtil.imSleepy(3000);
			// Switch to Frame
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			// Enter Value in search box
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@type='text']", "Automation");
			SeleniumTools.clickOnObject("xpath", "//span[text()='Global, Automation,']");
			CoreUtil.imSleepy(5000);
			// SeleniumTools.clickOnObject("xpath",
			// "//body/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/span[2]/button[1]/i[1]");
			// SeleniumTools.clickOnObject("xpath", "//button[@class='btn btn-default
			// btn-sm']//i[@class='fa fa-search']");
			SeleniumTools.ScrollTo("xpath",
					"//div[@class='tree-container k-widget k-treeview']//span[contains(text(),'Checklists')]");
			CoreUtil.imSleepy(5000);

			// Click on Add Checklist
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");

			SeleniumTools.clickOnObject("xpath", "//button[@class='btn btn-success ng-binding']");
			CoreUtil.imSleepy(9000);

			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			// Click Checklist dropdown
			SeleniumTools.clickOnObject("xpath",
					"//body/form[@id='WorkflowProviderAreaFormController']/div[1]/div[1]/div[2]/section[1]/div[1]/div[1]/echo-lookup[1]/div[1]/span[1]/button[1]");
			CoreUtil.imSleepy(9000);

			// Select Value Window
			SeleniumTools.switchToFrame("xpath", "//iframe[@id='frame-modal-clone-1']");

			SeleniumTools.clickOnObject("xpath",
					"//body/form[@id='ChecklistBrowController']/div[1]/div[1]/div[1]/div[1]/ul[1]/li[3]/div[1]/span[1]");
			CoreUtil.imSleepy(5000);

			SeleniumTools.clickOnObject("xpath",
					"//body/form[@id='ChecklistBrowController']/div[1]/div[1]/div[1]/div[1]/ul[1]/li[3]/ul[1]/li[1]/div[1]/span[1]");
			CoreUtil.imSleepy(5000);

			SeleniumTools.clickOnObject("xpath", "//b[contains(text(),'EC')]");
			CoreUtil.imSleepy(5000);

			// Click on Facility Dropdown
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			SeleniumTools.clickOnObject("xpath",
					"//body/form[@id='WorkflowProviderAreaFormController']/div[1]/div[1]/div[2]/section[1]/div[2]/div[1]/echo-lookup[1]/div[1]/span[1]/button[1]");
			CoreUtil.imSleepy(5000);

			SeleniumTools.switchToFrame("xpath", "//iframe[@id='frame-modal-clone-1']");
			CoreUtil.imSleepy(5000);

			SeleniumTools.ClearAndSetText("xpath", "//input[@id='lookup-text-search']", "_Jen Facility A");
			CoreUtil.imSleepy(5000);

			// Click on Search Button
			SeleniumTools.clickOnObject("xpath", "//input[@type='button' and @value='Search']");
			CoreUtil.imSleepy(5000);

			// Click on Save button
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save']");
			CoreUtil.imSleepy(20000);

			// Edit: Fill Notes
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			SeleniumTools.ClearAndSetTextEnter("xpath",
					"//body/form[@id='WorkflowProviderAreaFormController']/div[1]/div[1]/div[2]/section[1]/div[1]/div[2]/echo-notes-field[1]/textarea[1]",
					" For Automation Testing");
			CoreUtil.imSleepy(9000);

			// Save Edited
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save']");
			CoreUtil.imSleepy(20000);

			// Click on Delete
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			SeleniumTools.clickOnObject("xpath", "//span[text()=' Delete']");
			CoreUtil.imSleepy(10000);

			// Confirm Delete
			SeleniumTools.clickOnObject("xpath",
					"//echo-interrupt[@class='ng-isolate-scope']//div[@class='btn-group pull-right']//a[text()='Delete']");
			CoreUtil.imSleepy(20000);

		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}

	@Test(description = "Add, modify, delete Checklist from provider record in Facility Mode")
	public void VS_FM_05() {
		try {
			// Login to the Stage Application
			Login.MegaseedLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_F_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_F_Password"));
			// Switch To Frame
			// SeleniumTools.switchToFrame("xpath", "//iframe[@id='mainFrame']");

			// Verifying facility mode
			Login.CheckMode("Alvarado", "facility");

			// Click on Search
			SeleniumTools.clickOnObject("xpath", "//i[@title='Search']/following-sibling::span[text()='Search']");
			CoreUtil.imSleepy(20000);

			// Click on Search All
			SeleniumTools.clickOnObject("xpath", "//span[text()='Search All']");
			CoreUtil.imSleepy(9000);
			// Switch to Frame
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			// Enter Value in search box
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@type='text']", "Facility");
			CoreUtil.imSleepy(10000);
			SeleniumTools.clickOnObject("xpath", "//span[text()='Facility, Automation,']");
			CoreUtil.imSleepy(5000);
			// SeleniumTools.clickOnObject("xpath", "//span[text()='Workflow, Test,']");
			// Thread.sleep(40000);

			SeleniumTools.ScrollTo("xpath",
					"//div[@class='tree-container k-widget k-treeview']//span[contains(text(),'Checklists')]");
			CoreUtil.imSleepy(9000);

			// Click on Add Checklist
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");

			SeleniumTools.clickOnObject("xpath", "//button[@class='btn btn-success ng-binding']");
			CoreUtil.imSleepy(10000);

			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			// Click Checklist dropdown
			SeleniumTools.clickOnObject("xpath",
					"//body/form[@id='WorkflowProviderAreaFormController']/div[1]/div[1]/div[2]/section[1]/div[1]/div[1]/echo-lookup[1]/div[1]/span[1]/button[1]");
			CoreUtil.imSleepy(10000);

			// Select Value Window
			SeleniumTools.switchToFrame("xpath", "//iframe[@id='frame-modal-clone-1']");

			SeleniumTools.clickOnObject("xpath",
					"//body/form[@id='ChecklistBrowController']/div[1]/div[1]/div[1]/div[1]/ul[1]/li[2]/div[1]/span[1]");
			CoreUtil.imSleepy(10000);

			SeleniumTools.clickOnObject("xpath",
					"//body/form[@id='ChecklistBrowController']/div[1]/div[1]/div[1]/div[1]/ul[1]/li[2]/ul[1]/li[1]/div[1]/span[1]");
			CoreUtil.imSleepy(10000);

			SeleniumTools.clickOnObject("xpath", "//b[contains(text(),'EC')]");
			CoreUtil.imSleepy(10000);

			// Select Region

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			SeleniumTools.clickOnObject("xpath",
					"//body/form[@id='WorkflowProviderAreaFormController']/div[1]/div[1]/div[2]/section[1]/div[2]/div[1]/echo-lookup[1]/div[1]/span[1]");
			CoreUtil.imSleepy(10000);

			SeleniumTools.switchToFrame("xpath", "//iframe[@id='frame-modal-clone-1']");

			SeleniumTools.ClearAndSetText("xpath", "//input[@id='lookup-text-search']", "Alvarado");
			CoreUtil.imSleepy(10000);

			// Click on Search Button
			SeleniumTools.clickOnObject("xpath", "//input[@type='button' and @value='Search']");
			CoreUtil.imSleepy(10000);

			// Select from search
			// SeleniumTools.clickOnObject("xpath", "//td[text()='ALVA']");
			// CoreUtil.imSleepy(10000);

			// Click on Save button
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save']");
			CoreUtil.imSleepy(20000);

			// Edit: Fill Notes
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			SeleniumTools.ClearAndSetTextEnter("xpath",
					"//body/form[@id='WorkflowProviderAreaFormController']/div[1]/div[1]/div[2]/section[1]/div[1]/div[2]/echo-notes-field[1]/textarea[1]",
					"Filling Checklist notes");
			CoreUtil.imSleepy(10000);

			// Save Edited
			SeleniumTools.switchToDefaultFrame();

			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save']");
			CoreUtil.imSleepy(20000);

			// Click on Delete
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			SeleniumTools.clickOnObject("xpath", "//span[text()=' Delete']");
			CoreUtil.imSleepy(10000);

			// Confirm Delete
			SeleniumTools.clickOnObject("xpath",
					"//echo-interrupt[@class='ng-isolate-scope']//div[@class='btn-group pull-right']//a[text()='Delete']");
			CoreUtil.imSleepy(20000);

		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}

	@Test(description = "Add, modify, delete Provider Contract from provider record in Global Mode")
	public void VS_GM_06() {
		try {
			Login.MegaseedLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_G_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_G_Password"));
			// Switch To Frame
			SeleniumTools.switchToFrame("xpath", "//iframe[@id='mainFrame']");
			// Verifying global mode
			Login.CheckMode("Global MEGASEED FOR QA", "global");

			// Click on Search
			SeleniumTools.clickOnObject("xpath", "//i[@title='Search']/following-sibling::span[text()='Search']");
			CoreUtil.imSleepy(9000);

			// Click on Search All
			SeleniumTools.clickOnObject("xpath", "//span[text()='Search All']");
			CoreUtil.imSleepy(3000);
			// Switch to Frame
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			// Enter Value in search box
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@type='text']", "Automation");
			// CoreUtil.imSleepy(5000);
			// Search Global Automation
			SeleniumTools.clickOnObject("xpath", "//span[text()='Global, Automation,']");
			CoreUtil.imSleepy(5000);

			// Click on Payer Contract in provider record
			SeleniumTools.ScrollTo("xpath",
					"//div[@class='tree-container k-widget k-treeview']//span[contains(text(),'Payer Contracts')]");
			CoreUtil.imSleepy(9000);

			// Click on Add Payer Contracts
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");

			SeleniumTools.clickOnObject("xpath", "//button[@class='btn btn-success ng-binding']");
			CoreUtil.imSleepy(10000);

			SeleniumTools.switchToFrame("xpath", "//iframe[@id='MainModalFrame']");
			CoreUtil.imSleepy(10000);

			// Enter Value in search box
			SeleniumTools.ClearAndSetTextEnter("xpath",
					"//div[@class='col-sx-6 divNoScroll k-pane k-scrollable']//input[@id='availableFilter' and @placeholder='Search for a Contract to Populate List']",
					"20/20");
			CoreUtil.imSleepy(10000);
			// Click on check all
			SeleniumTools.clickOnObject("xpath",
					"//body/form[@id='form1']/div[@id='content']/div[@id='buttons']/div[1]/div[1]/div[1]/input[1]");
			CoreUtil.imSleepy(10000);
			// Click on Add Contracts
			SeleniumTools.clickOnObject("xpath", "//input[@value='Add Contracts']");
			CoreUtil.imSleepy(10000);
			// Close Window
			SeleniumTools.switchToDefaultFrame();

			SeleniumTools.clickOnObject("xpath", "//button[@class='close']");
			// CoreUtil.imSleepy(15000);

			// Enter Pin
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			// SeleniumTools.scrollToElementAndClick("xpath",
			// "//div[@class='ng-scope']//input[@id='pin']");
			// CoreUtil.imSleepy(9000);

			// SeleniumTools.ClearAndSetTextEnter("xpath",
			// "//div[@class='ng-scope']//input[@id='pin']", "72188");
			// CoreUtil.imSleepy(5000);

			// Add Notes
			SeleniumTools.ScrollTo("xpath", "//button[text()='Add note']");
			CoreUtil.imSleepy(9000);
			SeleniumTools.ClearAndSetText("xpath", "//textarea[@class='paper']", "Testing Only");
			// CoreUtil.imSleepy(9000);

			// Click on Save button
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			// Click on Save
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save']");
			// CoreUtil.imSleepy(10000);

			// Click on Delete

			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			SeleniumTools.clickOnObject("xpath", "//span[text()=' Delete']");
			// CoreUtil.imSleepy(5000);

			// Confirm Delete
			SeleniumTools.clickOnObject("xpath",
					"//echo-interrupt[@class='ng-isolate-scope']//div[@class='btn-group pull-right']//a[text()='Delete']");
			// CoreUtil.imSleepy(10000);

		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}

	@Test(description = "Add, modify, delete Provider Contract from provider record in Facility Mode")
	public void VS_FM_06() {
		try {
			// Login to the Megaseed Application
			Login.MegaseedLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_F_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_F_Password"));
			// Switch To Frame
			// SeleniumTools.switchToFrame("xpath", "//iframe[@id='mainFrame']");

			// Verifying facility mode
			Login.CheckMode("Alvarado", "facility");
			// Click on Search
			SeleniumTools.clickOnObject("xpath", "//i[@title='Search']/following-sibling::span[text()='Search']");
			CoreUtil.imSleepy(9000);

			// Click on Search All
			SeleniumTools.clickOnObject("xpath", "//span[text()='Search All']");
			CoreUtil.imSleepy(9000);
			// Switch to Frame
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			// Enter Value in search box
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@type='text']", "Facility");
			CoreUtil.imSleepy(5000);

			// Select Provider name
			SeleniumTools.clickOnObject("xpath", "//span[text()='Facility, Automation,']");
			CoreUtil.imSleepy(5000);

			SeleniumTools.ScrollTo("xpath",
					"//div[@class='tree-container k-widget k-treeview']//span[contains(text(),'Payer Contracts')]");
			CoreUtil.imSleepy(9000);

			// Click on Add Checklist

			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.clickOnObject("xpath", "//button[@class='btn btn-success ng-binding']");
			CoreUtil.imSleepy(8000);

			SeleniumTools.switchToFrame("xpath", "//iframe[@id='MainModalFrame']");
			CoreUtil.imSleepy(8000);

			SeleniumTools.ClearAndSetTextEnter("xpath",
					"//div[@class='col-sx-6 divNoScroll k-pane k-scrollable']//input[@id='availableFilter']", "20/20");
			CoreUtil.imSleepy(8000);
			// Click on check all
			SeleniumTools.clickOnObject("xpath",
					"//body/form[@id='form1']/div[@id='content']/div[@id='buttons']/div[1]/div[1]/div[1]/input[1]");
			CoreUtil.imSleepy(8000);
			// Click on Add Contracts
			SeleniumTools.clickOnObject("xpath", "//input[@value='Add Contracts']");
			CoreUtil.imSleepy(10000);
			// Close Window
			SeleniumTools.switchToDefaultFrame();

			SeleniumTools.clickOnObject("xpath", "//button[@class='close']");
			CoreUtil.imSleepy(20000);

			// Enter Pin
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");


			// Add Notes
			SeleniumTools.ScrollTo("xpath", "//button[text()='Add note']");
			CoreUtil.imSleepy(8000);
			SeleniumTools.ClearAndSetText("xpath", "//textarea[@class='paper']", "Testing Only");
			CoreUtil.imSleepy(8000);

			// Click on Save button
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			// Click on Save
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save']");
			CoreUtil.imSleepy(10000);

			// Click on Delete
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			SeleniumTools.clickOnObject("xpath", "//span[text()=' Delete']");
			CoreUtil.imSleepy(6000);

			// Confirm Delete
			SeleniumTools.clickOnObject("xpath",
					"//echo-interrupt[@class='ng-isolate-scope']//div[@class='btn-group pull-right']//a[text()='Delete']");
			CoreUtil.imSleepy(10000);

		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}

	@Test(description = "Add, modify, delete Specialties from provider record in Global Mode")
	public void VS_GM_07() {
		try {
			Login.MegaseedLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_G_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_G_Password"));
			// Switch To Frame
			SeleniumTools.switchToFrame("xpath", "//iframe[@id='mainFrame']");
			// Verifying global mode
			Login.CheckMode("Global MEGASEED FOR QA", "global");

			// Click on Search
			SeleniumTools.clickOnObject("xpath", "//i[@title='Search']/following-sibling::span[text()='Search']");
			CoreUtil.imSleepy(9000);

			// Click on Search All
			SeleniumTools.clickOnObject("xpath", "//span[text()='Search All']");
			CoreUtil.imSleepy(3000);
			// Switch to Frame
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			// Enter Value in search box
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@type='text']", "Automation");
			// CoreUtil.imSleepy(5000);
			// Search Global Automation
			SeleniumTools.clickOnObject("xpath", "//span[text()='Global, Automation,']");
			CoreUtil.imSleepy(5000);

			SeleniumTools.ScrollTo("xpath",
					"//div[@class='tree-container k-widget k-treeview']//span[contains(text(),'Specialties')]");
			CoreUtil.imSleepy(5000);

			// Click on Add Specialties

			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");

			SeleniumTools.clickOnObject("xpath", "//button[@class='btn btn-success ng-binding']");
			CoreUtil.imSleepy(5000);

			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			// Select Region

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			SeleniumTools.clickOnObject("xpath",
					"//body/form[@id='SpecialtiesController']/div[1]/div[1]/section[1]/div[1]/div[1]/echo-lookup[1]/div[1]/span[1]/button[1]");
			CoreUtil.imSleepy(8000);

			SeleniumTools.switchToFrame("xpath", "//iframe[@id='frame-modal-clone-1']");
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='lookup-text-search']", "_Jen Facility A");
			CoreUtil.imSleepy(10000);

			// Click on Search Button
			SeleniumTools.clickOnObject("xpath", "//input[@type='button' and @value='Search']");
			CoreUtil.imSleepy(10000);

			// Select Speciality

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			SeleniumTools.clickOnObject("xpath",
					"//body/form[@id='SpecialtiesController']/div[1]/div[1]/section[1]/div[2]/div[1]/echo-lookup[1]/div[1]/span[1]/button[1]");
			CoreUtil.imSleepy(10000);

			SeleniumTools.switchToFrame("xpath", "//iframe[@id='frame-modal-clone-1']");
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='lookup-text-search']", "Acupuncture");
			CoreUtil.imSleepy(10000);

			// Click on Search Button
			SeleniumTools.clickOnObject("xpath", "//input[@type='button' and @value='Search']");
			CoreUtil.imSleepy(10000);

			// Click on Save
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save']");
			CoreUtil.imSleepy(10000);

			// Edit
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			SeleniumTools.ClearAndSetText("xpath", "//div[@class='ng-scope']//input[@id='year_cert']", "2020");
			CoreUtil.imSleepy(10000);

			SeleniumTools.ClearAndSetText("xpath", "//div[@class='ng-scope']//input[@id='year_recert']", "2021");
			CoreUtil.imSleepy(10000);

			// Click on Save
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save']");
			CoreUtil.imSleepy(10000);

			// Click on Delete
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			SeleniumTools.clickOnObject("xpath", "//span[text()=' Delete']");
			CoreUtil.imSleepy(10000);

			// Confirm Delete
			SeleniumTools.clickOnObject("xpath",
					"//echo-interrupt[@class='ng-isolate-scope']//div[@class='btn-group pull-right']//a[text()='Delete']");
			CoreUtil.imSleepy(20000);

		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}

	}

	@Test(description = "Add, modify, delete Specialties from provider record in Facility mode")
	public void VS_FM_07() {
		try {
			// Login to the Megaseed Application
			Login.MegaseedLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_F_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_F_Password"));
			// Switch To Frame
			// SeleniumTools.switchToFrame("xpath", "//iframe[@id='mainFrame']");

			// Verifying facility mode
			Login.CheckMode("Alvarado", "facility");
			// Click on Search
			SeleniumTools.clickOnObject("xpath", "//i[@title='Search']/following-sibling::span[text()='Search']");
			CoreUtil.imSleepy(9000);

			// Click on Search All
			SeleniumTools.clickOnObject("xpath", "//span[text()='Search All']");
			CoreUtil.imSleepy(9000);
			// Switch to Frame
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			// Enter Value in search box
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@type='text']", "Facility");
			CoreUtil.imSleepy(5000);

			// Select Provider name
			SeleniumTools.clickOnObject("xpath", "//span[text()='Facility, Automation,']");
			CoreUtil.imSleepy(5000);
			// Scroll to Specialties
			SeleniumTools.ScrollTo("xpath",
					"//div[@class='tree-container k-widget k-treeview']//span[contains(text(),'Specialties')]");
			CoreUtil.imSleepy(5000);

			// Click on Add Specialties

			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");

			SeleniumTools.clickOnObject("xpath", "//button[@class='btn btn-success ng-binding']");
			CoreUtil.imSleepy(5000);

			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			// Select Region

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			SeleniumTools.clickOnObject("xpath",
					"//body/form[@id='SpecialtiesController']/div[1]/div[1]/section[1]/div[1]/div[1]/echo-lookup[1]/div[1]/span[1]/button[1]");
			CoreUtil.imSleepy(10000);

			SeleniumTools.switchToFrame("xpath", "//iframe[@id='frame-modal-clone-1']");
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='lookup-text-search']", "Alvarado");
			CoreUtil.imSleepy(10000);

			// Click on Search Button
			SeleniumTools.clickOnObject("xpath", "//input[@type='button' and @value='Search']");
			CoreUtil.imSleepy(10000);

			// Select Speciality

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			SeleniumTools.clickOnObject("xpath",
					"//body/form[@id='SpecialtiesController']/div[1]/div[1]/section[1]/div[2]/div[1]/echo-lookup[1]/div[1]/span[1]/button[1]");
			CoreUtil.imSleepy(10000);

			SeleniumTools.switchToFrame("xpath", "//iframe[@id='frame-modal-clone-1']");
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='lookup-text-search']", "Anesthesiologist Assistant");
			CoreUtil.imSleepy(10000);

			// Click on Search Button
			SeleniumTools.clickOnObject("xpath", "//input[@type='button' and @value='Search']");
			CoreUtil.imSleepy(10000);

			// Click on Save
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save']");
			CoreUtil.imSleepy(10000);

			// Edit
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			SeleniumTools.ClearAndSetText("xpath", "//div[@class='ng-scope']//input[@id='year_cert']", "2020");
			CoreUtil.imSleepy(10000);

			SeleniumTools.ClearAndSetText("xpath", "//div[@class='ng-scope']//input[@id='year_recert']", "2021");
			CoreUtil.imSleepy(10000);

			// Click on Save
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save']");
			CoreUtil.imSleepy(10000);

			// Click on Delete
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			SeleniumTools.clickOnObject("xpath", "//span[text()=' Delete']");
			CoreUtil.imSleepy(10000);

			// Confirm Delete
			SeleniumTools.clickOnObject("xpath",
					"//echo-interrupt[@class='ng-isolate-scope']//div[@class='btn-group pull-right']//a[text()='Delete']");
			CoreUtil.imSleepy(20000);

		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}

	}

	@Test(description = "Add, modify, remove value from System Parameters General Screen")
	public void VS_GM_08() {
		try {
			// Login to the Deploy Application
			Login.MegaseedLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_G_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_G_Password"));
			// Verifying Global mode
			Login.CheckMode("Global MEGASEED FOR QA", "global");
			CoreUtil.imSleepy(2000);
			SeleniumTools.switchToFrame("id", "mainFrame");
			CoreUtil.imSleepy(5000);
			SeleniumTools.hoverElement("xpath", "//span[text()='Set up']");
			SeleniumTools.hoverElement("xpath", "//span[text()='System']");
			SeleniumTools.hoverElement("xpath", "//span[text()='System']/following::div//span[text()='Configuration']");
			SeleniumTools.clickOnObject("xpath", "//span[text()='System Parameters']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			CoreUtil.imSleepy(10000);
			SeleniumTools.clickOnObject("xpath", "//input[@title='Quick Help' and @id='faxsuffix']");
			CoreUtil.imSleepy(2000);
			SeleniumTools.ClearAndSetText("xpath", "//input[@title='Quick Help' and @id='faxsuffix']",
					SeleniumTools.getRandomNumbers());
			CoreUtil.imSleepy(2000);
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save ']");
			// Editing
			CoreUtil.imSleepy(15000);
			SeleniumTools.clickOnObject("xpath", "//input[@title='Quick Help' and @id='faxsuffix']");
			CoreUtil.imSleepy(2000);
			SeleniumTools.ClearAndSetText("xpath", "//input[@title='Quick Help' and @id='faxsuffix']",
					SeleniumTools.getRandomNumbers());
			CoreUtil.imSleepy(2000);
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save ']");
			// Removing
			CoreUtil.imSleepy(15000);
			SeleniumTools.clickOnObject("xpath", "//input[@title='Quick Help' and @id='faxsuffix']");
			CoreUtil.imSleepy(2000);
			SeleniumTools.ClearAndSetText("xpath", "//input[@title='Quick Help' and @id='faxsuffix']", "");
			CoreUtil.imSleepy(2000);
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save ']");

		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);

		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}

	@Test(description = "Add, modify, remove value from System Parameters General Screen")
	public void VS_FM_08() {
		try {
			// Login to the Megaseed Application
			Login.MegaseedLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_F_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_F_Password"));
			// Verifying Facility mode
			Login.CheckMode("Alvarado", "facility");
			CoreUtil.imSleepy(2000);
			SeleniumTools.switchToFrame("id", "mainFrame");
			CoreUtil.imSleepy(5000);
			SeleniumTools.hoverElement("xpath", "//span[text()='Set up']");
			SeleniumTools.hoverElement("xpath", "//span[text()='System']");
			SeleniumTools.hoverElement("xpath", "//span[text()='System']/following::div//span[text()='Configuration']");
			SeleniumTools.clickOnObject("xpath", "//span[text()='System Parameters']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			CoreUtil.imSleepy(10000);
			SeleniumTools.clickOnObject("xpath", "//input[@title='Quick Help' and @id='faxsuffix']");
			CoreUtil.imSleepy(2000);
			SeleniumTools.ClearAndSetText("xpath", "//input[@title='Quick Help' and @id='faxsuffix']",
					SeleniumTools.getRandomNumbers());
			CoreUtil.imSleepy(2000);
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save ']");
			// Editing
			CoreUtil.imSleepy(15000);
			SeleniumTools.clickOnObject("xpath", "//input[@title='Quick Help' and @id='faxsuffix']");
			CoreUtil.imSleepy(2000);
			SeleniumTools.ClearAndSetText("xpath", "//input[@title='Quick Help' and @id='faxsuffix']",
					SeleniumTools.getRandomNumbers());
			CoreUtil.imSleepy(2000);
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save ']");
			// Removing
			CoreUtil.imSleepy(15000);
			SeleniumTools.clickOnObject("xpath", "//input[@title='Quick Help' and @id='faxsuffix']");
			CoreUtil.imSleepy(2000);
			SeleniumTools.ClearAndSetText("xpath", "//input[@title='Quick Help' and @id='faxsuffix']", "");
			CoreUtil.imSleepy(2000);
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save ']");

		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);

		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}

	@Test(description = "Successfully change facilities")
	public static void VS_GM_09() {
		try {

			// Login to the Stage Application
			Login.MegaseedLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_G_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_G_Password"));
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.clickOnObject("xpath", "//div[@id='main-navigation']//img[@id='NavigationLogo']");
			CoreUtil.imSleepy(2000);
			SeleniumTools.clickOnObject("xpath", "//div[@id='facility']");
			CoreUtil.imSleepy(2000);
			SeleniumTools.switchToFrame("id", "frame-modal-clone-1");
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='lookup-text-search']", "test");
			CoreUtil.imSleepy(2000);
			SeleniumTools.clickOnObject("xpath", "//input[@type='button']");
			CoreUtil.imSleepy(7000);

			String FacName = SeleniumTools.getText("xpath", "//div[@id='code-lookup']//table//tr[1]/td[3]");
			SeleniumTools.clickOnObject("xpath", "//div[@id='code-lookup']//table//tr[1]/td[3]");
			CoreUtil.imSleepy(30000);
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.clickOnObject("xpath", "//div[@id='main-navigation']//img[@id='NavigationLogo']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.verifyText("xpath", "//div[@id='facility-business-unit']/div[@id='facility']", FacName);
			CoreUtil.imSleepy(2000);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			pNode.fail(e);
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}

	}

	@Test(description = "Successfully change facilities")
	public static void VS_FM_09() {
		try {

			// Login to the Stage Application
			Login.MegaseedLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_F_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_F_Password"));
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.clickOnObject("xpath", "//div[@id='main-navigation']//img[@id='NavigationLogo']");
			CoreUtil.imSleepy(2000);
			SeleniumTools.clickOnObject("xpath", "//div[@id='facility']");
			CoreUtil.imSleepy(2000);
			SeleniumTools.switchToFrame("id", "frame-modal-clone-1");
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='lookup-text-search']", "test");
			CoreUtil.imSleepy(2000);
			SeleniumTools.clickOnObject("xpath", "//input[@type='button']");
			CoreUtil.imSleepy(7000);

			String FacName = SeleniumTools.getText("xpath", "//div[@id='code-lookup']//table//tr[1]/td[3]");
			SeleniumTools.clickOnObject("xpath", "//div[@id='code-lookup']//table//tr[1]/td[3]");
			CoreUtil.imSleepy(30000);
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.clickOnObject("xpath", "//div[@id='main-navigation']//img[@id='NavigationLogo']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.verifyText("xpath", "//div[@id='facility-business-unit']/div[@id='facility']", FacName);
			CoreUtil.imSleepy(2000);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			pNode.fail(e);
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}

	}

	@Test(description = "Ensure Roster Library screen loads with existing templates (Set Up > Enroll > Roster Library) both in Global and Facility")
	public void VS_GM_10() {
		try {
			// Login to the Megaseed Application
			Login.MegaseedLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_G_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_G_Password"));
			// Verifying global mode
			Login.CheckMode("Global MEGASEED FOR QA", "global");
			CoreUtil.imSleepy(2000);
			SeleniumTools.switchToFrame("id", "mainFrame");
			CoreUtil.imSleepy(5000);
			SeleniumTools.hoverElement("xpath", "//span[text()='Set up']");
			SeleniumTools.hoverElement("xpath", "//li[@text='Set up']//span[text()='Enroll']");
			SeleniumTools.clickOnObject("xpath", "//span[text()='Roster Library']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.verifyText("xpath", "//span[text()='Highmark']", "Highmark");
			CoreUtil.imSleepy(5000);
		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}

	@Test(description = "Ensure Roster Library screen loads with existing templates (Set Up > Enroll > Roster Library) both in Global and Facility")
	public void VS_FM_10() {
		try {
			// Login to the Megaseed Application
			Login.MegaseedLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_F_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_F_Password"));
			// Verifying Facility mode
			Login.CheckMode("Alvarado", "facility");
			CoreUtil.imSleepy(2000);
			SeleniumTools.switchToFrame("id", "mainFrame");
			CoreUtil.imSleepy(5000);
			SeleniumTools.hoverElement("xpath", "//span[text()='Set up']");
			SeleniumTools.hoverElement("xpath", "//li[@text='Set up']//span[text()='Enroll']");
			SeleniumTools.clickOnObject("xpath", "//span[text()='Roster Library']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.verifyText("xpath", "//span[text()='Highmark']", "Highmark");
			CoreUtil.imSleepy(5000);
		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}

	}

	@Test(description = "Upload, view and remove Provider Photo in scanned link field in Demographic subarea both in Global and Facility")
	public void VS_GM_11() {
		try {
			// Login to the Stage Application
			Login.MegaseedLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_G_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_G_Password"));
			// Verifying Global mode
			Login.CheckMode("Global MEGASEED FOR QA", "global");
			// ****Clicking on Search
			SeleniumTools.clickOnObject("xpath", "//div[@id='horizontal-menu-container']/ul/li[2]");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			// ********** clicking on search
			SeleniumTools.ClearAndSetTextEnter("xpath", "//div[@class='row search-input input-group']/input",
					"Global Automation");
			// CoreUtil.imSleepy(5000);
			// Clicking on Demographics
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='tree-container k-widget k-treeview']/ul/li/div/span[contains(text(),'Demographics')]");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			// Clicking on Scanned links
			SeleniumTools.clickOnObject("xpath", "//span[text()='Scanned Links']");
			// Clicking on provider photo
			SeleniumTools.clickOnObject("xpath",
					"//echo-input[@model='formFields.pict1']/div[1]/div/span[1]/button[1]");
			CoreUtil.imSleepy(5000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='frame-modal-clone-1']");
			// Clicking on Choose File
			SeleniumTools.clickOnObject("xpath", "//input[@name='Image1']");
			CoreUtil.imSleepy(2000);
			Runtime.getRuntime()
					.exec(System.getProperty("user.dir") + "/src/test/resources/AutIT Scite/ProviderPhoto.exe");
			CoreUtil.imSleepy(2000);
			// Clicking on upload photo
			SeleniumTools.clickOnObject("xpath", "//input[@name='uploadButton']");
			CoreUtil.imSleepy(20000);

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			// ********* Click on Save
			SeleniumTools.clickOnObject("xpath", "//button[@ng-show='model.saveForm.show']");
			CoreUtil.imSleepy(30000);
			// ******************** Clicking on View provider photo ************
			SeleniumTools.clickOnObject("xpath", "//echo-input[@model='formFields.pict1']/div[1]/div/div/div/input");
			CoreUtil.imSleepy(20000);
			SeleniumTools.switchToDefaultFrame();
			// Closing View File
			SeleniumTools.clickOnObject("xpath", "//h4[text()='View File']/preceding-sibling::button");

			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			// ******************* Clicking on remove provider photo ************
			SeleniumTools.clickOnObject("xpath", "//echo-input[@model='formFields.pict1']/div[1]/div/input");
			SeleniumTools.PressKey("Ctrl+A");
			SeleniumTools.PressKey("Delete");
			// ********* Click on Save
			SeleniumTools.clickOnObject("xpath", "//button[@ng-show='model.saveForm.show']");
			CoreUtil.imSleepy(2000);
		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}

	@Test(description = "Upload, view and remove Provider Photo in scanned link field in Demographic subarea both in Global and Facility")
	public void VS_FM_11() {
		try {
			// Login to the Stage Application
			Login.MegaseedLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_F_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_F_Password"));
			// Verifying Global mode
			Login.CheckMode("Alvarado", "facility");
			// ****Clicking on Search
			SeleniumTools.clickOnObject("xpath", "//div[@id='horizontal-menu-container']/ul/li[2]");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			// ********** clicking on search
			SeleniumTools.ClearAndSetTextEnter("xpath", "//div[@class='row search-input input-group']/input",
					"Facility Automation");
			CoreUtil.imSleepy(5000);
			// Clicking on Demographics
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='tree-container k-widget k-treeview']/ul/li/div/span[contains(text(),'Demographics')]");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			// Clicking on Scanned links
			SeleniumTools.clickOnObject("xpath", "//span[text()='Scanned Links']");
			// Clicking on provider photo
			SeleniumTools.clickOnObject("xpath",
					"//echo-input[@model='formFields.pict1']/div[1]/div/span[1]/button[1]");
			CoreUtil.imSleepy(5000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='frame-modal-clone-1']");
			// Clicking on Choose File
			SeleniumTools.clickOnObject("xpath", "//input[@name='Image1']");
			CoreUtil.imSleepy(2000);
			Runtime.getRuntime()
					.exec(System.getProperty("user.dir") + "/src/test/resources/AutIT Scite/ProviderPhoto.exe");
			CoreUtil.imSleepy(2000);
			// Clicking on upload photo
			SeleniumTools.clickOnObject("xpath", "//input[@name='uploadButton']");
			CoreUtil.imSleepy(20000);

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			// ********* Click on Save
			SeleniumTools.clickOnObject("xpath", "//button[@ng-show='model.saveForm.show']");
			CoreUtil.imSleepy(35000);
			// ******************** Clicking on View provider photo ************
			SeleniumTools.clickOnObject("xpath", "//echo-input[@model='formFields.pict1']/div[1]/div/div/div/input");
			CoreUtil.imSleepy(20000);
			SeleniumTools.switchToDefaultFrame();
			// Closing View File
			SeleniumTools.clickOnObject("xpath", "//h4[text()='View File']/preceding-sibling::button");

			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			// ******************* Clicking on remove provider photo ************
			SeleniumTools.clickOnObject("xpath", "//echo-input[@model='formFields.pict1']/div[1]/div/input");
			SeleniumTools.PressKey("Ctrl+A");
			SeleniumTools.PressKey("Delete");
			// ********* Click on Save
			SeleniumTools.clickOnObject("xpath", "//button[@ng-show='model.saveForm.show']");
			CoreUtil.imSleepy(2000);
		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}

	@Test(description = "Add, modify, copy, archive, delete User (Set Up > User/Group maintenance)")
	public void VS_GM_12() {
		try {
			// Login to the Stage Application
			Login.StageLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_G_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_G_Password"));
			// Verifying Global mode
			Login.CheckMode("Global MEGASEED FOR QA", "global");
			SeleniumTools.hoverElement("xpath", "//span[text()='Set up']");
			SeleniumTools.hoverElement("xpath", "//span[text()='System']");
			SeleniumTools.clickOnObject("xpath", "//span[text()='User/Group Maintenance']");
			CoreUtil.imSleepy(10000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.clickOnObject("xpath", "//div[@id='menuHeader']//div[text()='New']");
			String UserName = SeleniumTools.ClearAndSetText("xpath", "//input[@id='full_name']",
					"Test" + SeleniumTools.getRandomString());
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='initials']",
					"automationtest" + SeleniumTools.getRandomNumber(3));
			String password = SeleniumTools.ClearAndSetText("xpath", "//input[@name='passwordtxt']",
					SeleniumTools.getRandomString() + SeleniumTools.getRandomNumbers() + "e@");
			SeleniumTools.ClearAndSetText("xpath",
					"//label[text()='Confirm Password ']/following::input[@type='password']", password);
			SeleniumTools.ClearAndSetText("xpath", "//input[@name='emailtxt']", "automationtest@yopmail.com");
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save']");
			CoreUtil.imSleepy(20000);
			SeleniumTools.ClearAndSetTextEnter("xpath", "//div[@id='menuHeader']//input[@placeholder='Search...']",
					UserName);
			CoreUtil.imSleepy(5000);
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='operfirst']", SeleniumTools.getRandomString());
			CoreUtil.imSleepy(2000);
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='opermiddle']", SeleniumTools.getRandomString());
			CoreUtil.imSleepy(2000);
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Copy']");
			SeleniumTools.switchToDefaultFrame();
			CoreUtil.imSleepy(5000);
			SeleniumTools.clickOnObject("xpath", "//button[text()='OK']");

			CoreUtil.imSleepy(5000);
			SeleniumTools.switchToFrame("id", "mainFrame");
			CoreUtil.imSleepy(5000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			CoreUtil.imSleepy(3000);
			// Copy User
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='firstName']", SeleniumTools.getRandomString());
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='middleName']", SeleniumTools.getRandomString());
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='lastName']", SeleniumTools.getRandomString());
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='login']",
					"automationTestLoginID" + SeleniumTools.getRandomNumber(3));
			String Password = SeleniumTools.ClearAndSetText("xpath", "//input[@id='password']",
					SeleniumTools.getRandomString() + SeleniumTools.getRandomNumbers() + "@e");
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='passwordverify']", Password);
			CoreUtil.imSleepy(2000);
			SeleniumTools.clickOnObject("xpath", "//input[@value='Done']");
			CoreUtil.imSleepy(3000);
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Archive']");
			SeleniumTools.switchToDefaultFrame();
			CoreUtil.imSleepy(5000);
			SeleniumTools.clickOnObject("xpath", "//button[text()='OK']");

		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}

	@Test(description = "Add, modify, copy, archive, delete User (Set Up > User/Group maintenance)")
	public void VS_FM_12() {
		try {
			// Login to the Stage Application
			Login.StageLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_F_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_F_Password"));
			// Verifying Global mode
			Login.CheckMode("Alvarado", "facility");
			SeleniumTools.hoverElement("xpath", "//span[text()='Set up']");
			SeleniumTools.hoverElement("xpath", "//span[text()='System']");
			SeleniumTools.clickOnObject("xpath", "//span[text()='User/Group Maintenance']");
			CoreUtil.imSleepy(10000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.clickOnObject("xpath", "//div[@id='menuHeader']//div[text()='New']");
			String UserName = SeleniumTools.ClearAndSetText("xpath", "//input[@id='full_name']",
					"Test" + SeleniumTools.getRandomString());
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='initials']",
					"automationtest" + SeleniumTools.getRandomNumber(3));
			String password = SeleniumTools.ClearAndSetText("xpath", "//input[@name='passwordtxt']",
					SeleniumTools.getRandomString() + SeleniumTools.getRandomNumbers() + "e@");
			SeleniumTools.ClearAndSetText("xpath",
					"//label[text()='Confirm Password ']/following::input[@type='password']", password);
			SeleniumTools.ClearAndSetText("xpath", "//input[@name='emailtxt']", "automationtest@yopmail.com");
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save']");
			CoreUtil.imSleepy(20000);
			SeleniumTools.ClearAndSetTextEnter("xpath", "//div[@id='menuHeader']//input[@placeholder='Search...']",
					UserName);
			CoreUtil.imSleepy(5000);
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='operfirst']", SeleniumTools.getRandomString());
			CoreUtil.imSleepy(2000);
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='opermiddle']", SeleniumTools.getRandomString());
			CoreUtil.imSleepy(2000);
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Copy']");
			SeleniumTools.switchToDefaultFrame();
			CoreUtil.imSleepy(5000);
			SeleniumTools.clickOnObject("xpath", "//button[text()='OK']");

			CoreUtil.imSleepy(5000);
			SeleniumTools.switchToFrame("id", "mainFrame");
			CoreUtil.imSleepy(5000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			CoreUtil.imSleepy(3000);
			// Copy User
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='firstName']", SeleniumTools.getRandomString());
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='middleName']", SeleniumTools.getRandomString());
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='lastName']", SeleniumTools.getRandomString());
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='login']",
					"automationTestLoginID" + SeleniumTools.getRandomNumber(3));
			String Password = SeleniumTools.ClearAndSetText("xpath", "//input[@id='password']",
					SeleniumTools.getRandomString() + SeleniumTools.getRandomNumbers() + "@e");
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='passwordverify']", Password);
			CoreUtil.imSleepy(2000);
			SeleniumTools.clickOnObject("xpath", "//input[@value='Done']");
			CoreUtil.imSleepy(3000);
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Archive']");
			SeleniumTools.switchToDefaultFrame();
			CoreUtil.imSleepy(5000);
			SeleniumTools.clickOnObject("xpath", "//button[text()='OK']");

		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}

	@Test(description = "Add, modify, delete Screen Extender (Set Up > Screen Table Maintenance > Screen Extenders) in Global mode")
	public void VS_GM_13() {
		try {
			// Login to the Stage Application
			Login.MegaseedLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_G_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_G_Password"));
			Login.CheckMode("Global MEGASEED FOR QA", "global");
			// ****Clicking on Setup
			SeleniumTools.clickOnObject("xpath", "//div[@id='horizontal-menu-container']/ul/li[7]");
			// clicking on screen Table maintanance
			SeleniumTools.clickOnObject("xpath", "//span[text()='Screen/Table Maintenance']");
			// clicking on screen Extenders
			SeleniumTools.clickOnObject("xpath", "//span[text()='Screen Extenders']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			// clicking on Education
			SeleniumTools.clickOnObject("xpath", "//span[contains(text(),'Education')]");
			// Clicking on Add New
			SeleniumTools.clickOnObject("xpath", "//input[@ng-click='AddNew()']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='editFrame']");
			// Clicking on Field name
			Screen_Extenders = SeleniumTools.ClearAndSetText("xpath", "//table[@id='Table1']/tbody/tr[1]/td[2]",
					SeleniumTools.getRandomString());
			// Enter max length
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@name='txtMaxLength']", "200");
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			// clicking on Save
			SeleniumTools.clickOnObject("xpath", "//button[text()='Save']");
			CoreUtil.imSleepy(5000);
			// ************************* Modifying the Screen Extenders
			// **************************************
			// Clicking on search
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@id='searchExtender']", Screen_Extenders);
			CoreUtil.imSleepy(2000);
			// clicking on the field label
			SeleniumTools.clickObject("xpath", "//table[@data-role='selectable']/tbody/tr[1]/td[1]");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='editFrame']");
			Screen_Extenders2 = SeleniumTools.ClearAndSetText("xpath", "//table[@id='usrData']/tbody/tr[1]/td[2]",
					SeleniumTools.getRandomString());

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			// clicking on Save
			SeleniumTools.clickOnObject("xpath", "//button[text()='Save']");
			CoreUtil.imSleepy(5000);
			// ************************* Deleting the screen Extenders
			// ***************************************
			// Clicking on search
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@id='searchExtender']", Screen_Extenders2);
			CoreUtil.imSleepy(2000);
			// clicking on the field label
			SeleniumTools.clickObject("xpath", "//table[@data-role='selectable']/tbody/tr[1]/td[1]");
			// clicking on Delete
			SeleniumTools.clickOnObject("xpath", "//button[text()='Delete']");
			SeleniumTools.switchToDefaultFrame();
			// Clicking on OK
			SeleniumTools.clickOnObject("xpath", "//button[text()='OK']");
			CoreUtil.imSleepy(5000);

		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}

	@Test(description = "Add, modify, delete Screen Extender (Set Up > Screen Table Maintenance > Screen Extenders) in Facility mode")
	public void VS_FM_13() {
		try {
			// Login to the Stage Application
			Login.MegaseedLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_F_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_F_Password"));
			Login.CheckMode("Alvarado", "facility");
			// ****Clicking on Setup
			SeleniumTools.clickOnObject("xpath", "//div[@id='horizontal-menu-container']/ul/li[7]");
			// clicking on screen Table maintanance
			SeleniumTools.clickOnObject("xpath", "//span[text()='Screen/Table Maintenance']");
			// clicking on screen Extenders
			SeleniumTools.clickOnObject("xpath", "//span[text()='Screen Extenders']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			// clicking on Education
			SeleniumTools.clickOnObject("xpath", "//span[contains(text(),'Education')]");
			// Clicking on Add New
			SeleniumTools.clickOnObject("xpath", "//input[@ng-click='AddNew()']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='editFrame']");
			// Clicking on Field name
			Screen_Extenders = SeleniumTools.ClearAndSetText("xpath", "//table[@id='Table1']/tbody/tr[1]/td[2]",
					SeleniumTools.getRandomString());
			// Enter max length
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@name='txtMaxLength']", "200");
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			// clicking on Save
			SeleniumTools.clickOnObject("xpath", "//button[text()='Save']");
			CoreUtil.imSleepy(5000);
			// ************************* Modifying the Screen Extenders
			// **************************************
			// Clicking on search
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@id='searchExtender']", Screen_Extenders);
			CoreUtil.imSleepy(2000);
			// clicking on the field label
			SeleniumTools.clickObject("xpath", "//table[@data-role='selectable']/tbody/tr[1]/td[1]");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='editFrame']");
			Screen_Extenders2 = SeleniumTools.ClearAndSetText("xpath", "//table[@id='usrData']/tbody/tr[1]/td[2]",
					SeleniumTools.getRandomString());

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			// clicking on Save
			SeleniumTools.clickOnObject("xpath", "//button[text()='Save']");
			CoreUtil.imSleepy(5000);
			// ************************* Deleting the screen Extenders
			// ***************************************
			// Clicking on search
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@id='searchExtender']", Screen_Extenders2);
			CoreUtil.imSleepy(2000);
			// clicking on the field label
			SeleniumTools.clickObject("xpath", "//table[@data-role='selectable']/tbody/tr[1]/td[1]");
			// clicking on Delete
			SeleniumTools.clickOnObject("xpath", "//button[text()='Delete']");
			SeleniumTools.switchToDefaultFrame();
			// Clicking on OK
			SeleniumTools.clickOnObject("xpath", "//button[text()='OK']");
			CoreUtil.imSleepy(5000);

		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}

	@Test(description = "Add, modify, delete Workflow (Set Up > Workflow in Global mode)")
	public void VS_GM_14() {
		try {
			// Login to the Megaseed Application
			Login.MegaseedLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_G_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_G_Password"));
			// Verifying global mode
			Login.CheckMode("Global MEGASEED FOR QA", "global");
			SeleniumTools.switchToFrame("xpath", "//iframe[@id='mainFrame']");
			// CoreUtil.imSleepy(2000);
			// clicking on setup
			SeleniumTools.clickOnObject("xpath", "//li[@text='Set up']");
			CoreUtil.imSleepy(2000);
			// clicking on Workflow
			SeleniumTools.clickOnObject("xpath", "//li[@text='Set up']//span[text()='Workflow']");
			CoreUtil.imSleepy(2000);
			// clicking on Add new Workflow
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.clickOnObject("xpath", "//div[@class='fph-row']/div[3]/a/i");
			CoreUtil.imSleepy(5000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");
			// entering data into workflow code
			SeleniumTools.ClearAndSetText("xpath",
					"//section[@class='section section-box ng-scope']/div[1]/div[1]/echo-input/div/input",
					SeleniumTools.getRandomNumbers());
			CoreUtil.imSleepy(2000);
			// selecting workflow type
			SeleniumTools.selectByTextByIndex("xpath",
					"//section[@class='section section-box ng-scope']/div[1]/div[2]/select", 1);
			CoreUtil.imSleepy(2000);
			// Entering data into workflow Name
			SeleniumTools.ClearAndSetTextEnter("xpath", "//div[@class='col-md-4 col-xs-8']/echo-input/div/input",
					"Workflowtest1");
			CoreUtil.imSleepy(2000);
			// clicking on Region ellipses
			SeleniumTools.clickOnObject("xpath",
					"//section[@class='section section-box ng-scope']/div[3]/div[1]/echo-lookup/div[1]/span/button");
			CoreUtil.imSleepy(3000);
			// selecting region
			SeleniumTools.switchToFrameByNumber(3);
			SeleniumTools.ClearAndSetText("xpath", "//div[@class='grids-ready']/div[1]/div[2]/div/div[2]/div[1]/input",
					"AL");
			CoreUtil.imSleepy(3000);

			SeleniumTools.clickOnObject("xpath", "//div[@class='grids-ready']/div[1]/div[2]/div/div[1]/div[2]/input");
			CoreUtil.imSleepy(4000);
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='col-xs-12 grid-col']/div[1]/div[2]/table/tbody/tr[1]/td[3]");
			CoreUtil.imSleepy(4000);
			// clicking on submit
			SeleniumTools.clickOnObject("xpath", "//button[text()='Submit']");
			CoreUtil.imSleepy(2000);
			SeleniumTools.switchToDefaultFrame();
			CoreUtil.imSleepy(3000);
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");
			// CoreUtil.imSleepy(3000);
			// Clicking Provider Type ellipses
			SeleniumTools.clickOnObject("xpath",
					"//section[@class='section section-box ng-scope']/div[3]/div[2]/echo-lookup/div/span/button");
			CoreUtil.imSleepy(3000);
			// Selecting Provider Type
			SeleniumTools.switchToFrameByNumber(3);
			SeleniumTools.clickOnObject("xpath", "//div[@class='col-xs-12 grid-col']/div[1]/div[2]/table/tbody/tr[1]");
			CoreUtil.imSleepy(3000);
			// clicking on Default User/Group ellipses
			SeleniumTools.switchToDefaultFrame();
			// CoreUtil.imSleepy(3000);
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");
			// CoreUtil.imSleepy(3000);
			// clicking on Default User/Group ellipses
			SeleniumTools.clickOnObject("xpath",
					"//section[@class='section section-box ng-scope']/div[5]/div[1]/div/span[1]/button");// /html/body/form/div/div/ng-include/section/div[5]/div[1]/div/span[1]/button
			CoreUtil.imSleepy(3000);
			// selecting User
			SeleniumTools.switchToFrameByNumber(1);
			SeleniumTools.ClearAndSetText("xpath", "//div[@class='layout']/div/div[1]/div[1]/input", "a");
			// clicking on search
			SeleniumTools.clickOnObject("xpath", "//div[@class='col-sm-3 m-t-3']//button[text()='Search']");
			CoreUtil.imSleepy(2000);
			// selecting user from filtered results
			SeleniumTools.clickOnObject("xpath", "//div[@class='col-sm-12']/div/div[2]/table/tbody/tr/td[1]");
			CoreUtil.imSleepy(2000);
			// Clicking on Process type ellipses
			SeleniumTools.switchToDefaultFrame();
			// CoreUtil.imSleepy(3000);
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");
			// CoreUtil.imSleepy(3000);
			SeleniumTools.clickOnObject("xpath",
					"//section[@class='section section-box ng-scope']/div[3]/div[3]/echo-lookup/div[1]/span/button");
			CoreUtil.imSleepy(3000);
			SeleniumTools.switchToFrame("xpath", "//iframe[@id='frame-modal-clone-1']");
			// enter data into search by text to search
			SeleniumTools.ClearAndSetText("xpath", "//div[@class='grids-ready']/div[1]/div[2]/div/div[2]/div[1]/input",
					"ap");
			CoreUtil.imSleepy(2000);
			// clicking on search button
			SeleniumTools.clickOnObject("xpath", "//div[@class='col-xs-2 ng-scope']/input");
			CoreUtil.imSleepy(4000);
			// Selecting code
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='col-xs-12 grid-col']/div[1]/div[2]/table/tbody/tr[1]/td[1]");
			CoreUtil.imSleepy(3000);
			// clicking on folder ellipses
			SeleniumTools.switchToDefaultFrame();
			// CoreUtil.imSleepy(3000);
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");
			// CoreUtil.imSleepy(3000);
			SeleniumTools.clickOnObject("xpath",
					"//section[@class='section section-box ng-scope']/div[2]/div[2]/div/span/button");
			CoreUtil.imSleepy(3000);
			// selecting folder name
			SeleniumTools.switchToFrameByNumber(1);
			SeleniumTools.clickOnObject("xpath", "//span[text()='Test']");
			CoreUtil.imSleepy(2000);
			// clicking on select folder option
			SeleniumTools.clickOnObject("xpath", "//span/button[text()='Select Folder']");
			CoreUtil.imSleepy(2000);
			// Clicking on save
			SeleniumTools.switchToDefaultFrame();
			// CoreUtil.imSleepy(3000);
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");
			// CoreUtil.imSleepy(3000);
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save']");
			CoreUtil.imSleepy(10000);

			/// **************adding line item**************//
			// clicking on Add lineitem button
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");
			SeleniumTools.clickOnObject("xpath", "//button[text()='Add Line Item']");
			CoreUtil.imSleepy(5000);
			// enter data into ItemID
			SeleniumTools.switchToFrameByNumber(1);
			SeleniumTools.ClearAndSetText("xpath",
					"//div[@class='tab-content']/div[1]/section/div[1]/div[1]/echo-input/div/input",
					SeleniumTools.getRandomNumbers());
			CoreUtil.imSleepy(2000);
			// Entering data into Item name
			SeleniumTools.ClearAndSetText("xpath",
					"//div[@class='tab-content']/div[1]/section/div[1]/div[2]/echo-input/div/input",
					"Workflow Packets");
			// enter data into Due in #days from previous item
			SeleniumTools.ClearAndSetText("xpath",
					"//div[@class='tab-content']/div[1]/section/div[3]/div[1]/echo-input/div/span[1]/span/input[1]",
					"10");
			CoreUtil.imSleepy(2000);
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='tab-content']/div[1]/section/div[6]/div/div[2]/div[1]/echo-input/div/span[1]/span[2]");
			CoreUtil.imSleepy(2000);
			// Clicking on show checkbox button to yes
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='tab-content']/div[1]/section/div[6]/div/div[2]/div[2]/echo-input/div/span[1]/span[2]");
			// clicking on Show number to yes
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='tab-content']/div[1]/section/div[6]/div/div[2]/div[3]/echo-input/div/span[1]/span[2]");
			CoreUtil.imSleepy(2000);
			// clicking on Show Comment to yes
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='tab-content']/div[1]/section/div[6]/div/div[3]/div[1]/echo-input/div/span[1]/span[2]");
			// clicking on Show Show note to yes
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='tab-content']/div[1]/section/div[6]/div/div[3]/div[2]/echo-input/div/span[1]/span[2]");
			// clicking on Show Image to yes
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='tab-content']/div[1]/section/div[6]/div/div[3]/div[3]/echo-input/div/span[1]/span[2]");
			CoreUtil.imSleepy(2000);
			// Entering text into Tooltip text
			SeleniumTools.ClearAndSetText("xpath",
					"//div[@class='tab-content']/div[1]/section/div[7]/div/echo-input/div/input", "WorkflowTesttip");
			CoreUtil.imSleepy(2000);
			// Clicking on save button
			SeleniumTools.clickOnObject("xpath", "//button[@class='btn btn-success footer-btn save-button']");
			CoreUtil.imSleepy(3000);
			/////// ********* Modifying the Line item*****************************///////
			// Clicking on Edit line item button
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");
			// CoreUtil.imSleepy(3000);
			SeleniumTools.clickOnObject("xpath", "//span[@class='k-icon k-i-pencil k-i-edit']");
			// enter data into ItemID
			SeleniumTools.switchToFrameByNumber(1);
			SeleniumTools.ClearAndSetText("xpath",
					"//div[@class='tab-content']/div[1]/section/div[1]/div[1]/echo-input/div/input",
					SeleniumTools.getRandomNumbers());
			CoreUtil.imSleepy(2000);
			// Entering data into Item name
			SeleniumTools.ClearAndSetTextEnter("xpath",
					"//div[@class='tab-content']/div[1]/section/div[1]/div[2]/echo-input/div/input", "WF Packets");
			// Clicking on Show date button to Toggle No
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='tab-content']/div[1]/section/div[6]/div/div[2]/div[1]/echo-input/div/span[1]/span[2]");
			CoreUtil.imSleepy(2000);
			// Clicking on show checkbox button to No
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='tab-content']/div[1]/section/div[6]/div/div[2]/div[2]/echo-input/div/span[1]/span[2]");
			// clicking on Show number to No
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='tab-content']/div[1]/section/div[6]/div/div[2]/div[3]/echo-input/div/span[1]/span[2]");
			CoreUtil.imSleepy(2000);
			// clicking on Show Comment to No
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='tab-content']/div[1]/section/div[6]/div/div[3]/div[1]/echo-input/div/span[1]/span[2]");
			// clicking on Show Show note to No
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='tab-content']/div[1]/section/div[6]/div/div[3]/div[2]/echo-input/div/span[1]/span[2]");
			// clicking on Show Image to No
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='tab-content']/div[1]/section/div[6]/div/div[3]/div[3]/echo-input/div/span[1]/span[2]");
			CoreUtil.imSleepy(2000);
			// Clicking on save button
			SeleniumTools.clickOnObject("xpath", "//button[@class='btn btn-success footer-btn save-button']");
			CoreUtil.imSleepy(8000);
			//// ***************Deleting Line item********************/////
			// Clicking on Delete button
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");
			// CoreUtil.imSleepy(3000);
			SeleniumTools.clickOnObject("xpath", "//span[@class='k-icon k-i-pencil k-i-edit']");
			CoreUtil.imSleepy(2000);
			SeleniumTools.switchToFrameByNumber(1);
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Delete']");
			CoreUtil.imSleepy(2000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.clickOnObject("xpath", "//button[@class='k-button k-primary']");
			CoreUtil.imSleepy(3000);

			// *************Modifying the Workflow*********************
			// clicking on Edit button
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");
			SeleniumTools.clickOnObject("xpath", "//i[@class='fa fa-pencil-square-o']");
			CoreUtil.imSleepy(3000);
			// edit Workflow name
			SeleniumTools.ClearAndSetTextEnter("xpath",
					"//label[text()='Workflow Name']/following-sibling::echo-input/div/input", "WF Test 2");
			CoreUtil.imSleepy(2000);
			// clicking region ellipses
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='modal fade ng-scope in']/div/div/div[2]/div[1]/div[2]/echo-lookup/div[1]/span/button");
			CoreUtil.imSleepy(3000);
			// Selecting Region
			SeleniumTools.switchToFrameByNumber(3);
			SeleniumTools.ClearAndSetText("xpath", "//div[@class='grids-ready']/div[1]/div[2]/div/div[2]/div[1]/input",
					"De");
			SeleniumTools.clickOnObject("xpath", "//div[@class='grids-ready']/div[1]/div[2]/div/div[1]/div[2]/input");
			CoreUtil.imSleepy(5000);
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='col-xs-12 grid-col']/div[1]/div[2]/table/tbody/tr[1]/td[3]");
			CoreUtil.imSleepy(4000);
			// clicking on Submit button
			SeleniumTools.clickOnObject("xpath", "//button[text()='Submit']");
			CoreUtil.imSleepy(2000);
			// clicking on Process completion days
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");
			// CoreUtil.imSleepy(3000);
			SeleniumTools.clickOnObject("xpath", "//span[@class='k-select']/span[1]");
			CoreUtil.imSleepy(3000);
			// clicking on save button
			SeleniumTools.clickOnObject("xpath", "//div[@class='modal fade ng-scope in']/div/div/div[3]/div/button[2]");
			CoreUtil.imSleepy(10000);
			// clicking on Edit button
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");
			// CoreUtil.imSleepy(3000);
			SeleniumTools.clickOnObject("xpath", "//i[@class='fa fa-pencil-square-o']");

			// ****************Deleting Workflow*******************
			// clicking On Delete button
			SeleniumTools.clickOnObject("xpath", "//button[text()='Delete']");
			CoreUtil.imSleepy(2000);
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");
			// CoreUtil.imSleepy(3000);
			SeleniumTools.clickOnObject("xpath",
					"//echo-interrupt[@class='ng-isolate-scope']/div[1]/div/div/div[2]/div/a[2]");
			CoreUtil.imSleepy(3000);

		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}

	@Test(description = "Add, modify, delete Workflow (Set Up > Workflow)in Facility mode")
	public void VS_FM_14() {
		try {
			// Login to the Stage Application
			Login.MegaseedLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_F_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_F_Password"));
			// Verifying facility mode
			Login.CheckMode("Alvarado", "facility");
			SeleniumTools.switchToFrame("xpath", "//iframe[@id='mainFrame']");
			// clicking on setup
			SeleniumTools.clickOnObject("xpath", "//li[@text='Set up']");
			CoreUtil.imSleepy(2000);
			// clicking on Workflow
			SeleniumTools.clickOnObject("xpath", "//li[@text='Set up']//span[text()='Workflow']");
			CoreUtil.imSleepy(2000);
			// clicking on Add new Workflow
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.clickOnObject("xpath", "//div[@class='fph-row']/div[3]/a/i");
			CoreUtil.imSleepy(5000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");
			// entering data into workflow code
			SeleniumTools.ClearAndSetText("xpath",
					"//section[@class='section section-box ng-scope']/div[1]/div[1]/echo-input/div/input",
					SeleniumTools.getRandomNumbers());
			CoreUtil.imSleepy(1000);
			// selecting workflow type
			SeleniumTools.selectByTextByIndex("xpath",
					"//section[@class='section section-box ng-scope']/div[1]/div[2]/select", 1);
			CoreUtil.imSleepy(1000);
			// Entering data into workflow Name
			SeleniumTools.ClearAndSetTextEnter("xpath", "//div[@class='col-md-4 col-xs-8']/echo-input/div/input",
					"Workflowtest1");
			CoreUtil.imSleepy(1000);
			// clicking on Region ellipses
			SeleniumTools.clickOnObject("xpath",
					"//section[@class='section section-box ng-scope']/div[3]/div[1]/echo-lookup/div[1]/span/button");
			CoreUtil.imSleepy(5000);
			// selecting region
			SeleniumTools.switchToFrameByNumber(3);
			SeleniumTools.ClearAndSetText("xpath", "//div[@class='grids-ready']/div[1]/div[2]/div/div[2]/div[1]/input",
					"AL");
			SeleniumTools.clickOnObject("xpath", "//div[@class='grids-ready']/div[1]/div[2]/div/div[1]/div[2]/input");
			CoreUtil.imSleepy(2000);
			// SeleniumTools.clickOnObject("xpath", "//div[@class='col-xs-12
			// grid-col']/div[1]/div[2]/table/tbody/tr[1]");
			// CoreUtil.imSleepy(2000);
			// clicking on submit
			// SeleniumTools.clickOnObject("xpath", "//button[text()='Submit']");
			// CoreUtil.imSleepy(2000);
			SeleniumTools.switchToDefaultFrame();
			// CoreUtil.imSleepy(3000);
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");
			// CoreUtil.imSleepy(3000);
			// Clicking Provider Type ellipses
			SeleniumTools.clickOnObject("xpath",
					"//section[@class='section section-box ng-scope']/div[3]/div[2]/echo-lookup/div/span/button");
			CoreUtil.imSleepy(5000);
			// Selecting Provider Type
			SeleniumTools.switchToFrameByNumber(3);
			SeleniumTools.clickOnObject("xpath", "//div[@class='col-xs-12 grid-col']/div[1]/div[2]/table/tbody/tr[1]");
			// clicking on Default User/Group ellipses
			SeleniumTools.switchToDefaultFrame();
			// CoreUtil.imSleepy(3000);
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");
			// CoreUtil.imSleepy(3000);
			// clicking on Default User/Group ellipses
			SeleniumTools.clickOnObject("xpath",
					"//section[@class='section section-box ng-scope']/div[5]/div[1]/div/span[1]/button");// /html/body/form/div/div/ng-include/section/div[5]/div[1]/div/span[1]/button
			CoreUtil.imSleepy(5000);
			// selecting User
			SeleniumTools.switchToFrameByNumber(1);
			SeleniumTools.ClearAndSetText("xpath", "//div[@class='layout']/div/div[1]/div[1]/input", "a");
			// clicking on search
			SeleniumTools.clickOnObject("xpath", "//div[@class='col-sm-3 m-t-3']//button[text()='Search']");
			CoreUtil.imSleepy(2000);
			// selecting user from filtered results
			SeleniumTools.clickOnObject("xpath", "//div[@class='col-sm-12']/div/div[2]/table/tbody/tr/td[1]");
			CoreUtil.imSleepy(2000);
			// Clicking on Process type ellipses
			SeleniumTools.switchToDefaultFrame();
			// CoreUtil.imSleepy(3000);
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");
			// CoreUtil.imSleepy(3000);
			SeleniumTools.clickOnObject("xpath",
					"//section[@class='section section-box ng-scope']/div[3]/div[3]/echo-lookup/div[1]/span/button");
			CoreUtil.imSleepy(3000);
			SeleniumTools.switchToFrame("xpath", "//iframe[@id='frame-modal-clone-1']");
			// enter data into search by text to search
			SeleniumTools.ClearAndSetText("xpath", "//div[@class='grids-ready']/div[1]/div[2]/div/div[2]/div[1]/input",
					"ap");
			CoreUtil.imSleepy(3000);
			// clicking on search button
			SeleniumTools.clickOnObject("xpath", "//div[@class='col-xs-2 ng-scope']/input");
			CoreUtil.imSleepy(2000);
			// Selecting code
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='col-xs-12 grid-col']/div[1]/div[2]/table/tbody/tr[1]/td[1]");
			CoreUtil.imSleepy(3000);
			// clicking on folder ellipses
			SeleniumTools.switchToDefaultFrame();
			// CoreUtil.imSleepy(3000);
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");
			// CoreUtil.imSleepy(3000);
			SeleniumTools.clickOnObject("xpath",
					"//section[@class='section section-box ng-scope']/div[2]/div[2]/div/span/button");
			CoreUtil.imSleepy(3000);
			// selecting folder name
			SeleniumTools.switchToFrameByNumber(1);
			SeleniumTools.clickOnObject("xpath", "//span[text()='Test']");
			CoreUtil.imSleepy(2000);
			// clicking on select folder option
			SeleniumTools.clickOnObject("xpath", "//span/button[text()='Select Folder']");
			CoreUtil.imSleepy(2000);
			// Clicking on save
			SeleniumTools.switchToDefaultFrame();
			// CoreUtil.imSleepy(3000);
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");
			// CoreUtil.imSleepy(3000);
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save']");
			CoreUtil.imSleepy(8000);

			/// **************adding line item**************//
			// clicking on Add lineitem button
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");
			SeleniumTools.clickOnObject("xpath", "//button[text()='Add Line Item']");
			CoreUtil.imSleepy(3000);
			// enter data into ItemID
			SeleniumTools.switchToFrameByNumber(1);
			SeleniumTools.ClearAndSetText("xpath",
					"//div[@class='tab-content']/div[1]/section/div[1]/div[1]/echo-input/div/input",
					SeleniumTools.getRandomNumbers());
			CoreUtil.imSleepy(2000);
			// Entering data into Item name
			SeleniumTools.ClearAndSetText("xpath",
					"//div[@class='tab-content']/div[1]/section/div[1]/div[2]/echo-input/div/input",
					"Workflow Packets");
			// enter data into Due in #days from previous item
			SeleniumTools.ClearAndSetText("xpath",
					"//div[@class='tab-content']/div[1]/section/div[3]/div[1]/echo-input/div/span[1]/span/input[1]",
					"10");
			CoreUtil.imSleepy(2000);
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='tab-content']/div[1]/section/div[6]/div/div[2]/div[1]/echo-input/div/span[1]/span[2]");
			CoreUtil.imSleepy(2000);
			// Clicking on show checkbox button to yes
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='tab-content']/div[1]/section/div[6]/div/div[2]/div[2]/echo-input/div/span[1]/span[2]");
			// clicking on Show number to yes
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='tab-content']/div[1]/section/div[6]/div/div[2]/div[3]/echo-input/div/span[1]/span[2]");
			CoreUtil.imSleepy(2000);
			// clicking on Show Comment to yes
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='tab-content']/div[1]/section/div[6]/div/div[3]/div[1]/echo-input/div/span[1]/span[2]");
			// clicking on Show Show note to yes
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='tab-content']/div[1]/section/div[6]/div/div[3]/div[2]/echo-input/div/span[1]/span[2]");
			// clicking on Show Image to yes
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='tab-content']/div[1]/section/div[6]/div/div[3]/div[3]/echo-input/div/span[1]/span[2]");
			CoreUtil.imSleepy(2000);
			// Entering text into Tooltip text
			SeleniumTools.ClearAndSetText("xpath",
					"//div[@class='tab-content']/div[1]/section/div[7]/div/echo-input/div/input", "WorkflowTesttip");
			CoreUtil.imSleepy(2000);
			// Clicking on save button
			SeleniumTools.clickOnObject("xpath", "//button[@class='btn btn-success footer-btn save-button']");
			CoreUtil.imSleepy(3000);
			/////// ********* Modifying the Line item*****************************///////
			// Clicking on Edit line item button
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");
			CoreUtil.imSleepy(3000);
			SeleniumTools.clickOnObject("xpath", "//span[@class='k-icon k-i-pencil k-i-edit']");
			// enter data into ItemID
			SeleniumTools.switchToFrameByNumber(1);
			SeleniumTools.ClearAndSetText("xpath",
					"//div[@class='tab-content']/div[1]/section/div[1]/div[1]/echo-input/div/input",
					SeleniumTools.getRandomNumbers());
			CoreUtil.imSleepy(1000);
			// Entering data into Item name
			SeleniumTools.ClearAndSetTextEnter("xpath",
					"//div[@class='tab-content']/div[1]/section/div[1]/div[2]/echo-input/div/input", "WF Packets");
			// Clicking on Show date button to Toggle No
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='tab-content']/div[1]/section/div[6]/div/div[2]/div[1]/echo-input/div/span[1]/span[2]");
			CoreUtil.imSleepy(1000);
			// Clicking on show checkbox button to No
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='tab-content']/div[1]/section/div[6]/div/div[2]/div[2]/echo-input/div/span[1]/span[2]");
			// clicking on Show number to No
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='tab-content']/div[1]/section/div[6]/div/div[2]/div[3]/echo-input/div/span[1]/span[2]");
			CoreUtil.imSleepy(1000);
			// clicking on Show Comment to No
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='tab-content']/div[1]/section/div[6]/div/div[3]/div[1]/echo-input/div/span[1]/span[2]");
			// clicking on Show Show note to No
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='tab-content']/div[1]/section/div[6]/div/div[3]/div[2]/echo-input/div/span[1]/span[2]");
			// clicking on Show Image to No
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='tab-content']/div[1]/section/div[6]/div/div[3]/div[3]/echo-input/div/span[1]/span[2]");
			CoreUtil.imSleepy(1000);
			// Clicking on save button
			SeleniumTools.clickOnObject("xpath", "//button[@class='btn btn-success footer-btn save-button']");
			CoreUtil.imSleepy(2000);
			//// ***************Deleting Line item********************/////
			// Clicking on Delete button
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");
			CoreUtil.imSleepy(3000);
			SeleniumTools.clickOnObject("xpath", "//span[@class='k-icon k-i-pencil k-i-edit']");
			CoreUtil.imSleepy(2000);
			SeleniumTools.switchToFrameByNumber(1);
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Delete']");
			CoreUtil.imSleepy(2000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.clickOnObject("xpath", "//button[@class='k-button k-primary']");
			CoreUtil.imSleepy(3000);
			// *************Modifying the Workflow*********************
			// clicking on Edit button
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");
			SeleniumTools.clickOnObject("xpath", "//i[@class='fa fa-pencil-square-o']");
			CoreUtil.imSleepy(3000);
			// edit Workflow name
			SeleniumTools.ClearAndSetTextEnter("xpath",
					"//label[text()='Workflow Name']/following-sibling::echo-input/div/input", "WF Test 2");
			CoreUtil.imSleepy(2000);
			// clicking on Process completion days
			SeleniumTools.clickOnObject("xpath", "//span[@class='k-select']/span[1]");
			CoreUtil.imSleepy(3000);
			// clicking on save button
			SeleniumTools.clickOnObject("xpath", "//div[@class='modal fade ng-scope in']/div/div/div[3]/div/button[2]");
			CoreUtil.imSleepy(8000);
			// clicking on Edit button
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");
			CoreUtil.imSleepy(3000);
			SeleniumTools.clickOnObject("xpath", "//i[@class='fa fa-pencil-square-o']");

			// ****************Deleting Workflow*******************
			// clicking On Delete button
			SeleniumTools.clickOnObject("xpath", "//button[text()='Delete']");
			CoreUtil.imSleepy(2000);
			SeleniumTools.clickOnObject("xpath",
					"//echo-interrupt[@class='ng-isolate-scope']/div[1]/div/div/div[2]/div/a[2]");
			CoreUtil.imSleepy(3000);
		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}

	@Test(description = "Add, modify, delete Survey (Set Up > Surveys) both in Global")
	public void VS_GM_15() {
		try {
			// Login to the Stage Application
			Login.MegaseedLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_G_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_G_Password"));
			Login.CheckMode("Global MEGASEED FOR QA", "global");
			// ****Clicking on Setup
			SeleniumTools.clickOnObject("xpath", "//div[@id='horizontal-menu-container']/ul/li[7]");
			// clicking on screen Table maintanance
			SeleniumTools.clickOnObject("xpath", "//span[text()='Surveys']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			// Clicking on Add New Survey
			SeleniumTools.clickOnObject("xpath", "//a[contains(text(),'Add New Survey')]");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");
			// Entering Survey Name
			Survey_Name = SeleniumTools.ClearAndSetText("xpath", "//input[@name='txtSurveyName']",
					SeleniumTools.getRandomString());
			// Entering Code
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@name='txtSurvCode']",
					SeleniumTools.getRandomNumbers());
			// Clicking on ...Floder
			SeleniumTools.clickOnObject("xpath", "//button[@onclick='browsePackages(); return false;']");
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='MainModalFrame']");
			// clicking Folder
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()='SeAutomation']");
			// selecting Folder
			SeleniumTools.clickOnObject("xpath", "//button[text()='Select Folder']");
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.switchToFrame("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");

			// Clicking on Save
			SeleniumTools.clickOnObject("xpath", "//input[@id='btnSaveSurv']");
			CoreUtil.imSleepy(5000);
			// ********************** Modify Survey *********************
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.switchToFrame("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			/*
			 * //Clicking on Search... SeleniumTools.ClearAndSetTextEnter("xpath",
			 * "//input[@id='setup-folder-filter']","Automation"); CoreUtil.imSleepy(5000);
			 */
			// Clicking on survey subarea
			SeleniumTools.clickOnObject("xpath", "//div[@id='SetupFoldersTree']/ul/li/div/div/span");
			// Clicking on SeAutomation
			SeleniumTools.clickOnObject("xpath", "//ul[@class='rtUL']/li/div/div/span[text()='SeAutomation']");
			// Clicking on Desired survey
			SeleniumTools.clickOnObject("xpath",
					"//ul[@class='rtUL rtLines']/li/ul/li/ul/li/div/a/span[contains(text(),'" + Survey_Name + "')]");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");
			// Entering opening text
			SeleniumTools.ClearAndSetTextEnter("xpath", "//textarea[@name='tabParagraphs:TabPanel1:txtOpening']",
					SeleniumTools.getRandomString());
			// Clicking on Save
			SeleniumTools.clickOnObject("xpath", "//input[@id='btnSaveSurv']");
			CoreUtil.imSleepy(5000);
			// ******************* Deleting Survey **************************
			SeleniumTools.clickOnObject("xpath", "//input[@id='btnDelSurv']");
			SeleniumTools.getAlertText();
			CoreUtil.imSleepy(4000);

		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}

	@Test(description = "Add, modify, delete Survey (Set Up > Surveys) both in Facility mode")
	public void VS_FM_15() {
		try {
			// Login to the Stage Application
			Login.MegaseedLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_F_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_F_Password"));
			Login.CheckMode("Alvarado", "facility");
			// ****Clicking on Setup
			SeleniumTools.clickOnObject("xpath", "//div[@id='horizontal-menu-container']/ul/li[7]");
			// clicking on screen Table maintanance
			SeleniumTools.clickOnObject("xpath", "//span[text()='Surveys']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			// Clicking on Add New Survey
			SeleniumTools.clickOnObject("xpath", "//a[contains(text(),'Add New Survey')]");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");
			// Entering Survey Name
			Survey_Name = SeleniumTools.ClearAndSetText("xpath", "//input[@name='txtSurveyName']",
					SeleniumTools.getRandomString());
			// Entering Code
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@name='txtSurvCode']",
					SeleniumTools.getRandomNumbers());
			// Selecting facility mode
			SeleniumTools.selectByText("xpath", "//select[@name='drpFacility']", "Alvarado");
			// Clicking on ...Floder
			SeleniumTools.clickOnObject("xpath", "//button[@onclick='browsePackages(); return false;']");
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='MainModalFrame']");
			// clicking Folder
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()='SeAutomation']");
			// selecting Folder
			SeleniumTools.clickOnObject("xpath", "//button[text()='Select Folder']");
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.switchToFrame("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");

			// Clicking on Save
			SeleniumTools.clickOnObject("xpath", "//input[@id='btnSaveSurv']");
			CoreUtil.imSleepy(5000);
			// ********************** Modify Survey *********************
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.switchToFrame("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			/*
			 * //Clicking on Search... SeleniumTools.ClearAndSetTextEnter("xpath",
			 * "//input[@id='setup-folder-filter']","Automation"); CoreUtil.imSleepy(5000);
			 */
			// Clicking on survey subarea
			SeleniumTools.clickOnObject("xpath", "//div[@id='SetupFoldersTree']/ul/li/div/div/span");
			// Clicking on SeAutomation
			SeleniumTools.clickOnObject("xpath", "//ul[@class='rtUL']/li/div/div/span[text()='SeAutomation']");
			// Clicking on Desired survey
			SeleniumTools.clickOnObject("xpath",
					"//ul[@class='rtUL rtLines']/li/ul/li/ul/li/div/a/span[contains(text(),'" + Survey_Name + "')]");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");
			// Entering opening text
			SeleniumTools.ClearAndSetTextEnter("xpath", "//textarea[@name='tabParagraphs:TabPanel1:txtOpening']",
					SeleniumTools.getRandomString());
			// Clicking on Save
			SeleniumTools.clickOnObject("xpath", "//input[@id='btnSaveSurv']");
			CoreUtil.imSleepy(5000);
			// ******************* Deleting Survey **************************
			SeleniumTools.clickOnObject("xpath", "//input[@id='btnDelSurv']");
			SeleniumTools.getAlertText();
			CoreUtil.imSleepy(4000);

		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}

	@Test(description = "Add, modify, delete Office/Location (Set Up > Offices/Locations)")
	public void VS_GM_16() {
		try {
			// Login to the Deploy Application
			Login.MegaseedLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_G_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_G_Password"));
			// Verifying Global mode
			Login.CheckMode("Global MEGASEED FOR QA", "global");
			SeleniumTools.hoverElement("xpath", "//span[text()='Set up']");
			SeleniumTools.clickOnObject("xpath", "//span[text()='Offices/Locations']");
			CoreUtil.imSleepy(2000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.clickOnObject("xpath", "//button[contains(text(),'Add Offices/Locations')]");
			CoreUtil.imSleepy(5000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");
			String AddName = SeleniumTools.ClearAndSetText("xpath", "//input[@id='location']",
					"Chicago" + SeleniumTools.getRandomString());
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='othername']",
					"Chicago" + SeleniumTools.getRandomString());
			SeleniumTools.clickOnObject("xpath", "//span[text()='NO']");
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='address1']",
					"Guntur" + SeleniumTools.getRandomNumber(3));
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='address2']",
					"Guntur" + SeleniumTools.getRandomNumber(3));
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='city']", "Hyderabad");
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='county_txt']", "Telangana");
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save']");
			CoreUtil.imSleepy(40000);

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.ClearAndSetTextEnter("xpath",
					"//button[text()='Search']/preceding::label[text()='Search:']/following::input[@type='text']",
					AddName);
			CoreUtil.imSleepy(5000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");
			 
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='city']", "RangaReddy");
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save']");
			CoreUtil.imSleepy(15000);
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");
			CoreUtil.imSleepy(3000);
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='col-xs-4 col-sm-4'][1]//button//span[text()=' Delete']");
			SeleniumTools.clickOnObject("xpath",
					"//echo-interrupt[@modal='deleteConfirm' and @class='ng-isolate-scope']//a[text()='Delete']");
			CoreUtil.imSleepy(10000);

		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}

	@Test(description = "Add, modify, delete Office/Location (Set Up > Offices/Locations)")
	public void VS_FM_16() {
		try {
			// Login to the Deploy Application
			Login.MegaseedLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_F_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_F_Password"));
			Login.CheckMode("Alvarado", "facility");
			SeleniumTools.hoverElement("xpath", "//span[text()='Set up']");
			SeleniumTools.clickOnObject("xpath", "//span[text()='Offices/Locations']");
			CoreUtil.imSleepy(2000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.clickOnObject("xpath", "//button[contains(text(),'Add Offices/Locations')]");
			CoreUtil.imSleepy(5000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");
			String AddName = SeleniumTools.ClearAndSetText("xpath", "//input[@id='location']",
					"Chicago" + SeleniumTools.getRandomString());
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='othername']",
					"Chicago" + SeleniumTools.getRandomString());
			SeleniumTools.clickOnObject("xpath", "//span[text()='NO']");
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='address1']",
					"Guntur" + SeleniumTools.getRandomNumber(3));
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='address2']",
					"Guntur" + SeleniumTools.getRandomNumber(3));
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='city']", "Hyderabad");
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='county_txt']", "Telangana");
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save']");
			CoreUtil.imSleepy(40000);
			
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.ClearAndSetTextEnter("xpath",
					"//button[text()='Search']/preceding::label[text()='Search:']/following::input[@type='text']",
					AddName);
			CoreUtil.imSleepy(5000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");

			SeleniumTools.ClearAndSetText("xpath", "//input[@id='city']", "RangaReddy");
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save']");
			CoreUtil.imSleepy(17000);
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");
			CoreUtil.imSleepy(3000);
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='col-xs-4 col-sm-4'][1]//button//span[text()=' Delete']");
			CoreUtil.imSleepy(3000);
			SeleniumTools.clickOnObject("xpath",
					"//echo-interrupt[@modal='deleteConfirm' and @class='ng-isolate-scope']//a[text()='Delete']");
			CoreUtil.imSleepy(10000);

		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}

	@Test(description = "Add, modify, delete Provider Checklist (Set Up > Checklists > Provider Checklists)")
	public void VS_GM_17() {
		try {
			// Login to the Stage Application
			Login.MegaseedLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_G_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_G_Password"));
			// Verifying Global mode
			Login.CheckMode("Global MEGASEED FOR QA", "global");
			SeleniumTools.hoverElement("xpath", "//span[text()='Set up']");
			SeleniumTools.hoverElement("xpath", "//span[text()='Checklists']");
			SeleniumTools.clickOnObject("xpath", "//span[text()='Provider Checklist']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.clickOnObject("xpath", "//a[contains(text(),'Add New Provider Checklists')]");
			CoreUtil.imSleepy(5000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");
			CoreUtil.imSleepy(2000);
			SeleniumTools.ClearAndSetText("xpath", "//input[@title='Quick Help' and @id='cd']",
					SeleniumTools.getRandomNumbers());
			SeleniumTools.ClearAndSetText("xpath",
					"//label[text()='Provider Checklist Code']//ancestor::section//label[text()='Provider Checklist Name']/parent::div//input",
					"Diabetes" + SeleniumTools.getRandomNumber(4));
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='col-md-2 col-xs-4']/label[text()='Folder']/parent::div//button");
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='MainModalFrame']");
			SeleniumTools.clickOnObject("xpath", "//span[text()='AutomationSE']");
			SeleniumTools.clickOnObject("xpath", "//button[text()='Select Folder']");
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='col-md-4 col-xs-8 ng-scope']/label[text()='Facility']/parent::div//button");
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frame-modal-clone-1']");
			SeleniumTools.clickOnObject("xpath", "//table[@class='k-selectable']/tbody/tr[1]/td[1]");

			CoreUtil.imSleepy(2000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save']");
			CoreUtil.imSleepy(30000);
			SeleniumTools.clickOnObject("xpath", "//button[text()='Add Line Item']");
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='MainModalFrame']");
			CoreUtil.imSleepy(2000);
			String ItemID = SeleniumTools.ClearAndSetText("xpath", "//input[@id='itemid']",
					SeleniumTools.getRandomNumbers());
			String ItemName = SeleniumTools.ClearAndSetText("xpath", "//input[@id='label']",
					SeleniumTools.getRandomString());
			SeleniumTools.clickOnObject("xpath",
					"//label[contains(text(),'Show Date?')]//following-sibling::echo-input//span[text()='NO']");
			SeleniumTools.clickOnObject("xpath",
					"//label[contains(text(),'Show Checkbox?')]//following-sibling::echo-input//span[text()='NO']");
			SeleniumTools.clickOnObject("xpath",
					"//label[contains(text(),'Show Number?')]//following-sibling::echo-input//span[text()='NO']");
			SeleniumTools.clickOnObject("xpath",
					"//label[contains(text(),'Show Comment?')]//following-sibling::echo-input//span[text()='NO']");
			SeleniumTools.clickOnObject("xpath",
					"//label[contains(text(),'Show Note?')]//following-sibling::echo-input//span[text()='NO']");
			SeleniumTools.clickOnObject("xpath",
					"//label[contains(text(),'Show Image?')]//following-sibling::echo-input//span[text()='NO']");
			CoreUtil.imSleepy(2000);
			SeleniumTools.clickOnObject("xpath",
					"//label[contains(text(),'Show Image?')]//following-sibling::echo-input//span[text()='NO']//ancestor::div[@class='layout ng-scope']//following-sibling::echo-form-footer//span[text()=' Save']");
			CoreUtil.imSleepy(10000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");
			SeleniumTools.clickOnObject("xpath", "//tr//td[text()='" + ItemName + "']//parent::tr//td[text()='" + ItemID
					+ "']//parent::tr//td//a[text()='Edit Line Item']");
			SeleniumTools.switchToDefaultFrame();
			CoreUtil.imSleepy(2000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='MainModalFrame']");
			String ItemID1 = SeleniumTools.ClearAndSetText("xpath", "//input[@id='itemid']",
					SeleniumTools.getRandomNumbers());
			String ItemName1 = SeleniumTools.ClearAndSetText("xpath", "//input[@id='label']",
					SeleniumTools.getRandomString());
			SeleniumTools.clickOnObject("xpath",
					"//label[contains(text(),'Show Image?')]//following-sibling::echo-input//span[text()='NO']//ancestor::div[@class='layout ng-scope']//following-sibling::echo-form-footer//span[text()=' Save']");
			CoreUtil.imSleepy(10000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");
			SeleniumTools.clickOnObject("xpath", "//tr//td[text()='" + ItemName1 + "']//parent::tr//td[text()='"
					+ ItemID1 + "']//parent::tr//td//a[text()='Edit Line Item']");
			SeleniumTools.switchToDefaultFrame();
			CoreUtil.imSleepy(2000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='MainModalFrame']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Delete']");
			CoreUtil.imSleepy(2000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.clickOnObject("xpath", "//button[text()='OK']");
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");
			SeleniumTools.clickOnObject("xpath", "//button[text()=' Edit']");
			SeleniumTools.ClearAndSetText("xpath", "//input[@title='Quick Help']",
					"Diabetes" + SeleniumTools.getRandomNumbers());
			CoreUtil.imSleepy(2000);
			SeleniumTools.clickOnObject("xpath", "//input[@title='Quick Help']//ancestor::div//button[text()='Save']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.clickOnObject("xpath", "//button[text()=' Edit']");
			CoreUtil.imSleepy(3000);
			SeleniumTools.clickOnObject("xpath",
					"//input[@title='Quick Help']//ancestor::div//button[text()='Delete']");
			CoreUtil.imSleepy(3000);
			SeleniumTools.clickOnObject("xpath",
					"//echo-loader[@ng-show='showLoader']/following::echo-interrupt[@modal='deleteConfirm']//a[text()='Delete']");
			CoreUtil.imSleepy(3000);

		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}

	@Test(description = "Add, modify, delete Provider Checklist (Set Up > Checklists > Provider Checklists)")
	public void VS_FM_17() {
		try {
			// Login to the Stage Application
			Login.MegaseedLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_F_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_F_Password"));
			// Verifying Facility mode
			Login.CheckMode("Alvarado", "facility");
			SeleniumTools.hoverElement("xpath", "//span[text()='Set up']");
			SeleniumTools.hoverElement("xpath", "//span[text()='Checklists']");
			SeleniumTools.clickOnObject("xpath", "//span[text()='Provider Checklist']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.clickOnObject("xpath", "//a[contains(text(),'Add New Provider Checklists')]");
			CoreUtil.imSleepy(5000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");
			CoreUtil.imSleepy(2000);
			SeleniumTools.ClearAndSetText("xpath", "//input[@title='Quick Help' and @id='cd']",
					SeleniumTools.getRandomNumbers());
			SeleniumTools.ClearAndSetText("xpath",
					"//label[text()='Provider Checklist Code']//ancestor::section//label[text()='Provider Checklist Name']/parent::div//input",
					"Diabetes" + SeleniumTools.getRandomNumber(4));
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='col-md-2 col-xs-4']/label[text()='Folder']/parent::div//button");
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='MainModalFrame']");
			SeleniumTools.clickOnObject("xpath", "//span[text()='AutomationSE']");
			SeleniumTools.clickOnObject("xpath", "//button[text()='Select Folder']");
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='col-md-4 col-xs-8 ng-scope']/label[text()='Facility']/parent::div//button");
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frame-modal-clone-1']");
			SeleniumTools.clickOnObject("xpath", "//table[@class='k-selectable']/tbody/tr[1]/td[1]");

			CoreUtil.imSleepy(2000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save']");
			CoreUtil.imSleepy(30000);
			SeleniumTools.clickOnObject("xpath", "//button[text()='Add Line Item']");
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='MainModalFrame']");
			CoreUtil.imSleepy(2000);
			String ItemID = SeleniumTools.ClearAndSetText("xpath", "//input[@id='itemid']",
					SeleniumTools.getRandomNumbers());
			String ItemName = SeleniumTools.ClearAndSetText("xpath", "//input[@id='label']",
					SeleniumTools.getRandomString());
			SeleniumTools.clickOnObject("xpath",
					"//label[contains(text(),'Show Date?')]//following-sibling::echo-input//span[text()='NO']");
			SeleniumTools.clickOnObject("xpath",
					"//label[contains(text(),'Show Checkbox?')]//following-sibling::echo-input//span[text()='NO']");
			SeleniumTools.clickOnObject("xpath",
					"//label[contains(text(),'Show Number?')]//following-sibling::echo-input//span[text()='NO']");
			SeleniumTools.clickOnObject("xpath",
					"//label[contains(text(),'Show Comment?')]//following-sibling::echo-input//span[text()='NO']");
			SeleniumTools.clickOnObject("xpath",
					"//label[contains(text(),'Show Note?')]//following-sibling::echo-input//span[text()='NO']");
			SeleniumTools.clickOnObject("xpath",
					"//label[contains(text(),'Show Image?')]//following-sibling::echo-input//span[text()='NO']");
			CoreUtil.imSleepy(2000);
			SeleniumTools.clickOnObject("xpath",
					"//label[contains(text(),'Show Image?')]//following-sibling::echo-input//span[text()='NO']//ancestor::div[@class='layout ng-scope']//following-sibling::echo-form-footer//span[text()=' Save']");
			CoreUtil.imSleepy(10000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");
			SeleniumTools.clickOnObject("xpath", "//tr//td[text()='" + ItemName + "']//parent::tr//td[text()='" + ItemID
					+ "']//parent::tr//td//a[text()='Edit Line Item']");
			SeleniumTools.switchToDefaultFrame();
			CoreUtil.imSleepy(2000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='MainModalFrame']");
			String ItemID1 = SeleniumTools.ClearAndSetText("xpath", "//input[@id='itemid']",
					SeleniumTools.getRandomNumbers());
			String ItemName1 = SeleniumTools.ClearAndSetText("xpath", "//input[@id='label']",
					SeleniumTools.getRandomString());
			SeleniumTools.clickOnObject("xpath",
					"//label[contains(text(),'Show Image?')]//following-sibling::echo-input//span[text()='NO']//ancestor::div[@class='layout ng-scope']//following-sibling::echo-form-footer//span[text()=' Save']");
			CoreUtil.imSleepy(10000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");
			SeleniumTools.clickOnObject("xpath", "//tr//td[text()='" + ItemName1 + "']//parent::tr//td[text()='"
					+ ItemID1 + "']//parent::tr//td//a[text()='Edit Line Item']");
			SeleniumTools.switchToDefaultFrame();
			CoreUtil.imSleepy(2000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='MainModalFrame']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Delete']");
			CoreUtil.imSleepy(2000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.clickOnObject("xpath", "//button[text()='OK']");
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");
			SeleniumTools.clickOnObject("xpath", "//button[text()=' Edit']");
			SeleniumTools.ClearAndSetText("xpath", "//input[@title='Quick Help']",
					"Diabetes" + SeleniumTools.getRandomNumbers());
			CoreUtil.imSleepy(2000);
			SeleniumTools.clickOnObject("xpath", "//input[@title='Quick Help']//ancestor::div//button[text()='Save']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.clickOnObject("xpath", "//button[text()=' Edit']");
			CoreUtil.imSleepy(3000);
			SeleniumTools.clickOnObject("xpath",
					"//input[@title='Quick Help']//ancestor::div//button[text()='Delete']");
			CoreUtil.imSleepy(3000);
			SeleniumTools.clickOnObject("xpath",
					"//echo-loader[@ng-show='showLoader']/following::echo-interrupt[@modal='deleteConfirm']//a[text()='Delete']");
			CoreUtil.imSleepy(3000);

		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}

	@Test(description = "Import Provider via CAQH (Demographics > Import Provider > CAQH Proview source > Claim Your Profile > Save) both in Global and Facility")
	public void VS_GM_18() {
		try {
			// Login to the Deploy Application
			Login.MegaseedLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_G_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("MegaSeed_G_Password"));
			// Switch To Frame
			SeleniumTools.switchToFrame("xpath", "//iframe[@id='mainFrame']");
			// Verifying global mode
			Login.CheckMode("Global MEGASEED FOR QA", "global");

			// Click on Search
			SeleniumTools.clickOnObject("xpath", "//i[@title='Search']/following-sibling::span[text()='Search']");
			CoreUtil.imSleepy(9000);

			// Click on Search All
			SeleniumTools.clickOnObject("xpath", "//span[text()='Search All']");
			CoreUtil.imSleepy(3000);
			// Switch to Frame
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			// Enter Value in search box
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@type='text']", "Automation");
			// CoreUtil.imSleepy(5000);
			// Search Global Automation
			SeleniumTools.clickOnObject("xpath", "//span[text()='Global, Automation,']");
			CoreUtil.imSleepy(5000);

			// Select Demographics
			SeleniumTools.ScrollTo("xpath",
					"//div[@class='tree-container k-widget k-treeview']//span[contains(text(),'Demographics')]");
			CoreUtil.imSleepy(5000);

			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			CoreUtil.imSleepy(1000);

			// Enter CAQH Username
			SeleniumTools.ScrollTo("xpath", "//input[@id='caqh_username']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@id='caqh_username']", "mntestaccount");
			CoreUtil.imSleepy(5000);

			// Enter CAQH Password
			SeleniumTools.ScrollTo("xpath", "//input[@id='caqh_password']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@id='caqh_password']", "tester123!");
			CoreUtil.imSleepy(5000);

			// Click on Save
			SeleniumTools.clickOnObject("xpath", "//span[contains(text(),'Save')]");
			CoreUtil.imSleepy(10000);

			// Click on Import Provider
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Import Provider']");
			CoreUtil.imSleepy(5000);

			// Select a Source
			SeleniumTools.clickOnObject("xpath",
					"//body/div[1]/div[4]/div[1]/div[1]/section[1]/div[1]/div[1]/select[1]");
			CoreUtil.imSleepy(5000);
			// SeleniumTools.hoverElement("xpath", "//span[text()='CAQH ProView']");
			// CoreUtil.imSleepy(5000);

			// Enter CAQH Username
			SeleniumTools.ClearAndSetText("xpath",
					"//body/div[1]/div[4]/div[1]/div[1]/section[1]/div[4]/div[1]/input[1]", "mntestaccount");
			CoreUtil.imSleepy(8000);

			// Enter CAQH Password
			SeleniumTools.ClearAndSetText("xpath",
					"//body/div[1]/div[4]/div[1]/div[1]/section[1]/div[4]/div[2]/input[1]", "tester123!");
			CoreUtil.imSleepy(8000);

			// Click on Claim Your Profile
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Claim Your Profile']");
			CoreUtil.imSleepy(16000);

			// Verify First Name
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.verifyText("xpath", "//table[@role='grid']/tbody/tr[1]/td[4]/span[text()='David']", "David");
			CoreUtil.imSleepy(5000);

			SeleniumTools.verifyText("xpath", "//table[@role='grid']/tbody/tr[1]/td[5]/span[text()='Franklin']",
					"Franklin");
			CoreUtil.imSleepy(5000);

			SeleniumTools.verifyText("xpath", "//table[@role='grid']/tbody/tr[1]/td[6]/span[text()='Bronson']",
					"Bronson");
			CoreUtil.imSleepy(5000);
		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}

	}

	@Test(description = "Ensure functioning 'Forgot Password' link on user login page")
	public void VS_GM_20() {
		try {
			// Login to the Stage Application
			Login.MegaseedLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Forgot_User"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Forgot_PasswordSeed"));
			// Verifying Global mode
			Login.CheckMode("Global MEGASEED FOR QA", "global");
			SeleniumTools.hoverElement("xpath", "//span[text()='Set up']");
			SeleniumTools.hoverElement("xpath", "//span[text()='System']");
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
			SeleniumTools.clickOnObject("xpath", "//span[@title='Cred Stream']/../div/ul/li[3]");
			// Clicking on Account name
			SeleniumTools.ClearAndSetText("xpath", "//input[@name='loginNameTxt']",
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Forgot_User"));
			// Clicking on Forgot password
			SeleniumTools.clickOnObject("xpath", "//a[text()='Forgot password?']");
			CoreUtil.imSleepy(2000);

			SeleniumTools.navigateURL("https://mail.google.com/mail/u/0/#inbox");
			// Clicking on Email
			SeleniumTools.ClearAndSetText("xpath", "//input[@type='email']",
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Forgot_Email"));
			CoreUtil.imSleepy(2000);
			// Clicking on Next
			SeleniumTools.clickOnObject("xpath", "//div[@class='qhFLie']/div/div/button");
			CoreUtil.imSleepy(2000);
			// Clicking on password
			SeleniumTools.ClearAndSetText("xpath", "//input[@type='password']",
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Gmail_Password"));
			CoreUtil.imSleepy(2000);
			// Clicking on submit
			SeleniumTools.clickOnObject("xpath", "//div[@class='qhFLie']/div/div/button");
			CoreUtil.imSleepy(10000);

			// Clicking on Open Email
			SeleniumTools.clickOnObject("xpath", "//table[@class='F cf zt']/tbody/tr[1]");
			CoreUtil.imSleepy(10000);
			// Clicking on reset link
			SeleniumTools.clickOnObject("xpath",
					"//a[contains(@href, 'https://qaverity.verity.cloud/MEGASEED/appweb/asp/resetpassword.aspx?TOKEN')]");
			CoreUtil.imSleepy(5000);

			SeleniumTools.switchToChildWindow();
			// ****************** To Reset your password *************
			// Clicking on Enter Account
			SeleniumTools.ClearAndSetText("xpath", "//input[@name='UserName']",
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Forgot_User"));
			// Clicking on new password
			Update_Pswd = SeleniumTools.ClearAndSetText("xpath", "//input[@name='Password']",
					"Cstream_" + SeleniumTools.getRandomNumber(3));
			// Clicking on Re-enter password
			SeleniumTools.ClearAndSetText("xpath", "//input[@name='ReenterPassword']", Update_Pswd);
			// Clicking on Submit
			SeleniumTools.clickOnObject("xpath", "//input[@type='submit']");
			CoreUtil.imSleepy(5000);
			Expected = SeleniumTools.getText("xpath", "//div[@id='PasswordChangedSuccessfully']/p/font");
			SeleniumTools.verifyText("xpath", "//div[@id='PasswordChangedSuccessfully']/p/font", Expected);
			// *********** Updating latest password in the .property file **********
			SeleniumTools.writeConfigFile(Constants.properties_stage, "Forgot_PasswordSeed", Update_Pswd)
					.setProperty("Forgot_PasswordSeed", Update_Pswd);
			// Clicking on Go to login page
			SeleniumTools.clickOnObject("xpath", "//input[@value='Go to Login Page']");
			// Relogin to the Application
			CoreUtil.imSleepy(2000);
			// Entering user name
			SeleniumTools.ClearAndSetText("xpath", "//input[@name='loginNameTxt']",
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Forgot_User"));
			// Entering Password
			SeleniumTools.ClearAndSetText("xpath", "//input[@name='PasswordTxt']",
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Forgot_PasswordSeed"));
			// Clicking on Login button
			SeleniumTools.clickOnObject("xpath", "//input[@name='LoginButton']");
			CoreUtil.imSleepy(4000);
		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}
}
