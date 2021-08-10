package testSuite;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import utility.Constants;
import utility.CoreUtil;
import utility.ExcelReader;
import utility.LoggerUtil;
import utility.Login;
import utility.NotepadReader;
import utility.RTFReader;
import utility.SeleniumTools;
import utility.TestInit;

public class Bolt extends TestInit {
	public static String Provider_Name, Actual, Expected, Color, Workflow_Name, Listing_title, Spreadsheet_Title,
			fileName;
	static LoggerUtil loggerUtil = new LoggerUtil(Bolt.class);

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
			SeleniumTools.quitBrowser();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// @ removed this test case as confirmed by @parker
	@Test(description = "Verity - Bolt - Monitor Query", enabled = false)
	public void Bolt_SG_01() {
		try {

			// Login to the Stage Application
			Login.StageLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Password"));
			// Verifying Global mode
			Login.CheckMode("Verity QA Team1", "global");
			// Clicking on Bolt
			SeleniumTools.hoverElement("xpath", "//li[@text='Bolt']");
			// Clicking on Editor
			SeleniumTools.clickOnObject("xpath", "//ul[@role='menu']//span[text()='Editor']");
			CoreUtil.imSleepy(20000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterFilterFrame']");
			// Enter Query name
			SeleniumTools.ClearAndSetText("xpath", "//input[@name='full_nameTxt']",
					"Name_Alcantara_" + SeleniumTools.getRandomNumber(3));
			// Clicking on Folder
			SeleniumTools.scrollToElementAndClick("xpath", "//button[@class='btn btn-default silver folder-btn']");
			CoreUtil.imSleepy(10000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='MainModalFrame']");
			// Clicking on Automation
			SeleniumTools.clickOnObject("xpath",
					"//div[@id='PackagesTreeView']/ul//span[text()='Automation']/ancestor::div/preceding-sibling::span");
			// Clicking on Monitor
			SeleniumTools.clickOnObject("xpath",
					"//div[@id='PackagesTreeView']//span[text()='Automation']/ancestor::li//span[text()='Monitor']");
			// Clicking on Select Folder
			SeleniumTools.clickOnObject("xpath", "//button[text()='Select Folder']");
			CoreUtil.imSleepy(5000);

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterFilterFrame']");

			// Clicking on Master Table
			SeleniumTools.selectByText("xpath", "//select[@name='masterdbfTxt']", "Provider/Physician");

			// Clicking on Save
			SeleniumTools.scrollToElementAndClick("xpath", "//input[@name='saveButton']");
			CoreUtil.imSleepy(10000);

			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterFilterFrame']");

			// Clicking on Add variable
			SeleniumTools.scrollToElementAndClick("xpath", "//button[text()='Add Variable']");
			CoreUtil.imSleepy(10000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='MainModalFrame']");
			// Clicking on search
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@ng-model='searchVarText']", "Last Name");
			// Clicking on provider/phisican
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='folderLeafHeader folderClose']/span[text()='Provider/Physician']");
			// Clicking on Last Name
			SeleniumTools.clickOnObject("xpath", "//span[text()='Last Name']");
			CoreUtil.imSleepy(10000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='editVarFrame']");
			// Clicking on Last Name valu
			SeleniumTools.ClearAndSetText("xpath", "//input[@ng-model='filterline.Filt_value']", "Alcantara");

			SeleniumTools.switchToFrame("xpath", "//iframe[@id='MainModalFrame']");
			// Clicking on Finish
			SeleniumTools.scrollToElementAndClick("xpath", "//div[@id='FilterWizard']/div/ul/li[2]/button/span");
			// Clicking on close
			SeleniumTools.clickOnObject("xpath", "//input[@value='Close']");
			CoreUtil.imSleepy(5000);

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterFilterFrame']");

			// Clicking on Verify/Monitor
			SeleniumTools.clickOnObject("xpath", "//span[text()='Verify/Monitor']");

			// Clicking on Unchecked
			SeleniumTools.clickOnObject("xpath", "//input[@name='PSVinactive']");

			// Clicking on Mode
			SeleniumTools.selectByText("xpath", "//select[@name='PSVDD']", "SAM Exclusions");

			// Clicking on Check everyday
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@name='PSVdaysTxt']", "1");

			// Select calendar
			SeleniumTools.clickOnObject("xpath", "//input[@value='ScheduleTypeRadioSchedule']");
			SeleniumTools.PressTabwithValue("04042021");

			// Clicking on Frequency
			SeleniumTools.selectByText("xpath", "//select[@name='ScheduleDropDownList']", "Daily");
			CoreUtil.imSleepy(5000);
			SeleniumTools.javascriptClick("//span[text()='Notes']");

			// Clicking on Save
			SeleniumTools.javascriptClick("//input[@name='saveButton']");
			CoreUtil.imSleepy(10000);

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.switchToFrame("id", "mainFrame");
			// ************************ Navigate to provider***************************
			SeleniumTools.hoverElement("xpath", "//li[@text='Search'] [@class='k-item k-state-default']");
			SeleniumTools.clickOnObject("xpath", "//span[text()='Search']/ancestor::li//span[text()='Provider']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			// Enter Value in search box
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@ng-model='query']", "Roman Alcantara");
			// Clicking on Verifications
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='tree-container k-widget k-treeview']/ul//span[contains(text(),'Verifications:')]");
			Actual = SeleniumTools.getText("xpath",
					"//div[@class='tree-container k-widget k-treeview']/ul//span[contains(text(),'Verifications:')]/span");

			SeleniumTools.switchToFrame("id", "mainFrame");
			// Clicking on Bolt
			SeleniumTools.hoverElement("xpath", "//li[@text='Bolt']");
			// Clicking on Editor
			SeleniumTools.clickOnObject("xpath", "//ul[@role='menu']//span[text()='Editor']");
			CoreUtil.imSleepy(20000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterFilterFrame']");

			// Clicking on Execute Query
			SeleniumTools.scrollToElementAndClick("xpath", "//input[@name='executeButton']");
			CoreUtil.imSleepy(10000);
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()='Last Run: ']/following-sibling::span/b/a");
			CoreUtil.imSleepy(10000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			// Clicking on Verifications
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='tree-container k-widget k-treeview']/ul//span[contains(text(),'Verifications:')]");
			SeleniumTools.refreshPage();
			CoreUtil.imSleepy(10000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.switchToFrame("id", "mainFrame");
			// ************** Navigate to provider and search **************
			SeleniumTools.hoverElement("xpath", "//li[@text='Search'] [@class='k-item k-state-default']");
			SeleniumTools.clickOnObject("xpath", "//span[text()='Search']/ancestor::li//span[text()='Provider']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			// Enter Value in search box
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@ng-model='query']", "Alcantara");
			CoreUtil.imSleepy(15000);
			// Clicking on Verifications
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='tree-container k-widget k-treeview']/ul//span[contains(text(),'Verifications:')]");
			Expected = SeleniumTools.getText("xpath",
					"//div[@class='tree-container k-widget k-treeview']/ul//span[contains(text(),'Verifications:')]/span");
			assertNotEquals(Actual, Expected);
			SeleniumTools.switchToFrame("id", "mainFrame");
			// Clicking on Bolt
			SeleniumTools.hoverElement("xpath", "//li[@text='Bolt']");
			// Clicking on Editor
			SeleniumTools.clickOnObject("xpath", "//ul[@role='menu']//span[text()='Editor']");
			CoreUtil.imSleepy(20000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterFilterFrame']");
			// Clicking on Delete Query
			SeleniumTools.scrollToElementAndClick("xpath", "//input[@name='deleteButton']");
			SeleniumTools.getAlertText();
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

	@Test(description = "Bolt - Go to Editor - Spreadsheet - Create then Upload Template File - Print")
	public static void Bolt_SG_02() {
		try {
			Login.StageLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Password"));
			// Verifying Global mode
			Login.CheckMode("Verity QA Team1", "global");
			// Clicking on Bolt
			SeleniumTools.hoverElement("xpath", "//li[@text='Bolt']");
			// Clicking on Editor
			SeleniumTools.clickOnObject("xpath", "//ul[@role='menu']//span[text()='Editor']");
			CoreUtil.imSleepy(20000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterFilterFrame']");
			// Enter Query name
			SeleniumTools.ClearAndSetText("xpath", "//input[@name='full_nameTxt']", "");
			// SeleniumTools.getColor("xpath", "//ul[@class='rwzBreadCrumb']/li[1]/a");
			// Assert.assertEquals(Color, "#083a81");

			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			// Clicking on Dropdown
			SeleniumTools.scrollToElementAndClick("xpath",
					"//a/span[text()='Queries']//ancestor::ul/following-sibling::div[@class='rwzContentWrapper']//div[@id='filterStep']//span[@class='rddtInner']/span[text()='Add new...']");

			// Searching for record
			SeleniumTools.ClearAndSetText("xpath",
					"//div[@id='ReporterWizard']//a/span[text()='Queries']//ancestor::div[@id='ReporterWizard']/preceding-sibling::div//input[@value='Search...']",
					"Spreadsheet - Create then Upload");
			SeleniumTools.scrollToElementAndClick("xpath",
					"//span[contains(text(),'Spreadsheet - Create then Upload')]");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterFilterFrame']");
			// Clicking on Execute Query
			SeleniumTools.scrollToElementAndClick("xpath", "//input[@name='executeButton']");
			CoreUtil.imSleepy(6000);

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");

			// Clicking on Spreadsheet
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()='Spreadsheets']");
			// Clicking on Dropdown
			SeleniumTools.scrollToElementAndClick("xpath",
					"//a/span[text()='Spreadsheets']//ancestor::ul/following-sibling::div[@class='rwzContentWrapper']//div[@id='data_WizardTree']/span//span[text()='Add new...']");
			// Searching for record
			SeleniumTools.ClearAndSetText("xpath",
					"//a/span[text()='Spreadsheets']//ancestor::div[@id='ReporterWizard']/preceding-sibling::div//input[@class='rddtFilterInput radPreventDecorate rddtFilterEmptyMessage']",
					"Create - Spreadsheet");
			SeleniumTools.scrollToElementAndClick("xpath",
					"//span[contains(text(),'SeAutomation')]//ancestor::li//ul//span[contains(text(),'Create - Spreadsheet')]");

			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterDataWizFrame']");
			// Generate Spreadsheet
			SeleniumTools.scrollToElementAndClick("xpath", "//input[@title='Generate Spreadsheet']");

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			// clicking on OK
			SeleniumTools.scrollToElementAndClick("xpath", "//button[text()='OK']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.refreshPage();
			CoreUtil.imSleepy(20000);

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			// SeleniumTools.getText("xpath", "//span[text()='Report complete']");
			// clicking on Notification
			SeleniumTools.hoverElement("xpath", "//echo-popover[@title='Notifications']/span/i");

			// Verifying report
			SeleniumTools.verifyText("xpath",
					"//div[@id='navEntry']//echo-slat[@slat='notification']//div[text()='Create - Spreadsheet']",
					"Create - Spreadsheet");
			SeleniumTools.getText("xpath",
					"//div[@id='navEntry']//echo-slat[@slat='notification']//div[text()='Create - Spreadsheet']/parent::div/ul/li[last()]");

			// Clicking on Report
			SeleniumTools.scrollToElementAndClick("xpath",
					"//div[@id='navEntry']//echo-slat[@slat='notification']//div[text()='Create - Spreadsheet']");
			CoreUtil.imSleepy(5000);

			// Verifying downloaded file
			File filelocation = new File("C:\\Users\\SureshGoparaju\\Downloads");
			File[] totalfiles = filelocation.listFiles();

			for (File file : totalfiles) {
				if (file.getName().contains("create - spreadsheet")) {
					System.out.println("File is downloaded.");
					break;
				}
			}

			// Clicking on Bolt
			SeleniumTools.hoverElement("xpath", "//li[@text='Bolt']");
			// Clicking on Editor
			SeleniumTools.clickOnObject("xpath", "//ul[@role='menu']//span[text()='Editor']");
			CoreUtil.imSleepy(20000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterFilterFrame']");
			// Enter Query name
			SeleniumTools.ClearAndSetText("xpath", "//input[@name='full_nameTxt']", "");
			// SeleniumTools.getColor("xpath", "//ul[@class='rwzBreadCrumb']/li[1]/a");
			// Assert.assertEquals(Color, "#083a81");

			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			// Clicking on Dropdown
			SeleniumTools.scrollToElementAndClick("xpath",
					"//a/span[text()='Queries']//ancestor::ul/following-sibling::div[@class='rwzContentWrapper']//div[@id='filterStep']//span[@class='rddtInner']/span[text()='Add new...']");

			// Searching for record
			SeleniumTools.ClearAndSetText("xpath",
					"//div[@id='ReporterWizard']//a/span[text()='Queries']//ancestor::div[@id='ReporterWizard']/preceding-sibling::div//input[@value='Search...']",
					"Spreadsheet - Create then Upload");
			SeleniumTools.scrollToElementAndClick("xpath",
					"//span[contains(text(),'Spreadsheet - Create then Upload')]");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterFilterFrame']");
			// Clicking on Execute Query
			SeleniumTools.scrollToElementAndClick("xpath", "//input[@name='executeButton']");
			CoreUtil.imSleepy(6000);

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");

			// Clicking on Spreadsheet
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()='Spreadsheets']");
			// Clicking on Dropdown
			SeleniumTools.scrollToElementAndClick("xpath",
					"//a/span[text()='Spreadsheets']//ancestor::ul/following-sibling::div[@class='rwzContentWrapper']//div[@id='data_WizardTree']/span//span[text()='Add new...']");
			// Searching for record
			SeleniumTools.ClearAndSetText("xpath",
					"//a/span[text()='Spreadsheets']//ancestor::div[@id='ReporterWizard']/preceding-sibling::div//input[@class='rddtFilterInput radPreventDecorate rddtFilterEmptyMessage']",
					"Create - Spreadsheet");
			SeleniumTools.scrollToElementAndClick("xpath",
					"//span[contains(text(),'SeAutomation')]//ancestor::li//ul//span[contains(text(),'Create - Spreadsheet')]");

			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterDataWizFrame']");
			// Clicking on Options
			SeleniumTools.scrollToElementAndClick("xpath", "//div[@id='tabsDiv']//span[contains(text(),'Options')]");
			// Choose File
			SeleniumTools.scrollToElementAndClick("xpath", "//input[@value='Choose File']");
			CoreUtil.imSleepy(2000);
			Runtime.getRuntime()
					.exec(System.getProperty("user.dir") + "/src/test/resources/AutIT Scite/Spreadsheet.exe");
			CoreUtil.imSleepy(5000);
			SeleniumTools.verifyText("xpath", "//span[text()='Spreadsheet_Report.xlsx']", "Spreadsheet_Report.xlsx");
			SeleniumTools.verifyText("xpath", "//div[text()='File upload is complete.']", "File upload is complete.");

			// Save Record
			SeleniumTools.scrollToElementAndClick("xpath", "//div[@class='center-block']/input[@name='saveButton']");

		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
			Assert.fail();
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}

	}

	@Test(description = "Bolt - Workflow - Batch Add - Many Records")
	public static void Bolt_SG_03() {

		try {
			Login.StageLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Password"));
			// Verifying Global mode
			Login.CheckMode("Verity QA Team1", "global");
			// clicking on setup
			SeleniumTools.hoverElement("xpath", "//li[@text='Set up']");
			// clicking on Workflow
			SeleniumTools.clickOnObject("xpath", "//li[@text='Set up']//span[text()='Workflow']");
			// clicking on Add new Workflow
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.clickOnObject("xpath", "//a[contains(text(),'Add New Workflow')]");
			CoreUtil.imSleepy(5000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");
			// entering data into workflow code
			SeleniumTools.ClearAndSetText("xpath", "//section[@class='section section-box ng-scope']//input[@id='cd']",
					SeleniumTools.getRandomNumbers());
			// selecting workflow type
			SeleniumTools.selectByTextByIndex("xpath",
					"//section[@class='section section-box ng-scope']//select[@ng-model='formFields.subtype.Value']",
					1);
			// Entering data into workflow Name
			Workflow_Name = SeleniumTools.ClearAndSetText("xpath",
					"//div[@class='col-md-4 col-xs-8']//input[@id='txt']",
					"Bolt_Workflow_" + SeleniumTools.getRandomNumber(3));
			// Selecting Folder
			SeleniumTools.clickOnObject("xpath",
					"//section[@class='section section-box ng-scope']//button[@ng-disabled='formFields.folder.DisplayAttributes.ReadOnly']");
			CoreUtil.imSleepy(10000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='MainModalFrame']");
			// Selecting SeAutomation
			SeleniumTools.clickOnObject("xpath", "//div[@id='PackagesTreeView']//span[text()='SeAutomation']");
			SeleniumTools.clickOnObject("xpath", "//button[text()='Select Folder']");

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.switchToFrame("id", "mainFrame");

			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");
			// clicking on Region ellipses
			SeleniumTools.clickOnObject("xpath",
					"//section[@class='section section-box ng-scope']//label[text()='Facility']/following-sibling::echo-lookup//button[@class='btn btn-default']");
			CoreUtil.imSleepy(10000);
			// selecting region
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='frame-modal-clone-1']");
			SeleniumTools.ClearAndSetTextEnter("xpath",
					"//div[@class='grids-ready']//input[@placeholder='Search by text']", "AL");
			CoreUtil.imSleepy(5000);
			SeleniumTools.clickOnObject("xpath", "//td[text()='ALVA']");
			// clicking on submit
			SeleniumTools.clickOnObject("xpath", "//button[text()='Submit']");
			CoreUtil.imSleepy(5000);
			// Clicking on save
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save']");
			CoreUtil.imSleepy(8000);

			// ***************** adding line item*****************
			// clicking on Add lineitem button
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");
			SeleniumTools.clickOnObject("xpath", "//button[text()='Add Line Item']");
			CoreUtil.imSleepy(5000);
			// enter data into ItemID
			SeleniumTools.switchToFrameByNumber(1);
			SeleniumTools.ClearAndSetText("xpath", "//div[@class='tab-content']//input[@id='itemid']",
					SeleniumTools.getRandomNumbers());
			// Entering data into Item name
			SeleniumTools.ClearAndSetText("xpath", "//div[@class='tab-content']//input[@id='label']",
					"Workflow Packets");

			// Entering text into Tooltip text
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='tooltiptxt']", "WorkflowTesttip");
			// Clicking on save button
			SeleniumTools.clickOnObject("xpath",
					"//form[@ng-controller='WorkflowFormController']//div[@class='container-fluid']/ancestor::form//echo-form-footer//button[@class='btn btn-success footer-btn save-button']");
			CoreUtil.imSleepy(10000);

			// ******** Navigate to BOLT *******
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.switchToFrame("id", "mainFrame");
			// Clicking on Bolt
			SeleniumTools.hoverElement("xpath", "//li[@text='Bolt']");
			// Clicking on Editor
			SeleniumTools.clickOnObject("xpath", "//ul[@role='menu']//span[text()='Editor']");
			CoreUtil.imSleepy(20000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterFilterFrame']");
			// Enter Query name
			SeleniumTools.ClearAndSetText("xpath", "//input[@name='full_nameTxt']", "");
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			// Clicking on Dropdown
			SeleniumTools.scrollToElementAndClick("xpath",
					"//a/span[text()='Queries']//ancestor::ul/following-sibling::div[@class='rwzContentWrapper']//div[@id='filterStep']//span[@class='rddtInner']/span[text()='Add new...']");

			// Searching for record
			SeleniumTools.ClearAndSetText("xpath",
					"//div[@id='ReporterWizard']//a/span[text()='Queries']//ancestor::div[@id='ReporterWizard']/preceding-sibling::div//input[@value='Search...']",
					"Bolt_Workflow_Query");
			SeleniumTools.scrollToElementAndClick("xpath", "//span[contains(text(),'Bolt_Workflow_Query')]");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterFilterFrame']");
			// Clicking on Execute Query
			SeleniumTools.scrollToElementAndClick("xpath", "//input[@name='executeButton']");
			CoreUtil.imSleepy(6000);

			// Note the no.of record returned
			SeleniumTools.getText("xpath", "//span[text()='Last Run: ']/following-sibling::span/b/a/span");

			// Clicking on Others Tab
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()='Others']");

			// Clicking on Dropdown
			SeleniumTools.scrollToElementAndClick("xpath", "//div[@id='othersTree']/span");
			// Searching for record
			SeleniumTools.ClearAndSetText("xpath",
					"//a/span[text()='Spreadsheets']//ancestor::div[@id='ReporterWizard']/preceding-sibling::div//input[@class='rddtFilterInput radPreventDecorate rddtFilterEmptyMessage']",
					"Batch Add");
			CoreUtil.imSleepy(4000);
			// Clicking on Batch Add
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()='Batch Add']");

			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterOthersFrame']");
			// Selecting Workflow from dropdown
			SeleniumTools.selectByText("xpath",
					"//select[@class='type-dropdown form-control ng-pristine ng-untouched ng-valid']", "Workflow");
			CoreUtil.imSleepy(10000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");

			// Clicking on workflow ellipsis
			SeleniumTools.scrollToElementAndClick("xpath",
					"//span[@class='error ng-hide']/following-sibling::echo-lookup/div/span/button");
			CoreUtil.imSleepy(5000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='frame-modal-clone-1']");
			// Clicking on SeAutomation
			SeleniumTools.scrollToElementAndClick("xpath",
					"//span[contains(text(),'SeAutomation')]/ancestor::span[1]/ancestor::span/preceding-sibling::span");
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()='" + Workflow_Name + "']");
			CoreUtil.imSleepy(5000);

			// *** Navigate to provide to check count **********
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.hoverElement("xpath", "//li[@text='Search'] [@class='k-item k-state-default']");
			SeleniumTools.clickOnObject("xpath", "//span[text()='Search']/ancestor::li//span[text()='Provider']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			// Enter Value in search box
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@ng-model='query']", "suresh Goparaju");
			CoreUtil.imSleepy(5000);
			// Clicking on Workflows
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='tree-container k-widget k-treeview']//span[contains(text(),'Workflows:')]");

			// Actual = SeleniumTools.getText("xpath", "//div[@class='tree-container
			// k-widget k-treeview']//span[contains(text(),'Workflows:')]/span");

			// Enter Value in search box
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@ng-model='query']", "suresh G");
			CoreUtil.imSleepy(5000);
			SeleniumTools.clickOnObject("xpath", "//span[text()='Suresh, G,']/preceding-sibling::span");
			// Clicking on Workflows
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='tree-container k-widget k-treeview']//span[contains(text(),'Workflows:')]");

			// String Actual1 = SeleniumTools.getText("xpath", "//div[@class='tree-container
			// k-widget k-treeview']//span[contains(text(),'Workflows:')]/span");

			// ********** Back to Bolt *********
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.switchToFrame("id", "mainFrame");
			// Clicking on Bolt
			SeleniumTools.hoverElement("xpath", "//li[@text='Bolt']");
			// Clicking on Editor
			SeleniumTools.clickOnObject("xpath", "//ul[@role='menu']//span[text()='Editor']");
			CoreUtil.imSleepy(20000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterOthersFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");
			// Clicking on Execute
			SeleniumTools.scrollToElementAndClick("xpath", "//button[contains(text(),'Execute')]");

			SeleniumTools.switchToDefaultFrame();
			// Clicking on OK
			SeleniumTools.scrollToElementAndClick("xpath", "//button[text()='OK']");
			// Verify Message
			SeleniumTools.verifyText("xpath", "//div[text()='Execute Success']", "Execute Success");
			// Click on OK
			SeleniumTools.scrollToElementAndClick("xpath", "//button[text()='OK']");
			CoreUtil.imSleepy(5000);

			// *** Navigate to Suresh Goparaju provide **********
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.hoverElement("xpath", "//li[@text='Search'] [@class='k-item k-state-default']");
			SeleniumTools.clickOnObject("xpath", "//span[text()='Search']/ancestor::li//span[text()='Provider']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			// Enter Value in search box
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@ng-model='query']", "suresh Goparaju");
			CoreUtil.imSleepy(5000);
			// Clicking on Workflows
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='tree-container k-widget k-treeview']//span[contains(text(),'Workflows:')]");

			// Expected = SeleniumTools.getText("xpath", "//div[@class='tree-container
			// k-widget k-treeview']//span[contains(text(),'Workflows:')]/span");
			// Assert.assertNotEquals(Actual, Expected);
			CoreUtil.imSleepy(10000);
			// Delete the workflow record
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.clickOnObject("xpath", "//span[contains(text(),'" + Workflow_Name + "')]");
			CoreUtil.imSleepy(5000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Delete']");
			SeleniumTools.clickOnObject("xpath", "//echo-interrupt[@class='ng-isolate-scope']//a[text()='Delete']");
			CoreUtil.imSleepy(5000);

			// *** Navigate to Suresh G provide **********
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.hoverElement("xpath", "//li[@text='Search'] [@class='k-item k-state-default']");
			SeleniumTools.clickOnObject("xpath", "//span[text()='Search']/ancestor::li//span[text()='Provider']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			// Enter Value in search box
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@ng-model='query']", "suresh G");
			CoreUtil.imSleepy(5000);
			SeleniumTools.clickOnObject("xpath", "//span[text()='Suresh, G,']/preceding-sibling::span");
			// Clicking on Workflows
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='tree-container k-widget k-treeview']//span[contains(text(),'Workflows:')]");

			// String Expected1 = SeleniumTools.getText("xpath",
			// "//div[@class='tree-container k-widget
			// k-treeview']//span[contains(text(),'Workflows:')]/span");
			// Assert.assertNotEquals(Actual1, Expected1);
			CoreUtil.imSleepy(10000);
			// Delete the workflow record
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.clickOnObject("xpath", "//span[contains(text(),'" + Workflow_Name + "')]");
			CoreUtil.imSleepy(5000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='FormFrame']");
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Delete']");
			SeleniumTools.clickOnObject("xpath", "//echo-interrupt[@class='ng-isolate-scope']//a[text()='Delete']");
			CoreUtil.imSleepy(5000);

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			// clicking on setup
			SeleniumTools.hoverElement("xpath", "//li[@text='Set up']");
			// clicking on Workflow
			SeleniumTools.clickOnObject("xpath", "//li[@text='Set up']//span[text()='Workflow']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Set up']");
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@id='setup-folder-filter']", Workflow_Name);
			CoreUtil.imSleepy(5000);
			// Click on Workflow
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='RadTreeView RadTreeView_MetroTouch']//span[text()='Workflow']");
			// Click on SeAutomation
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='RadTreeView RadTreeView_MetroTouch']//span[text()='SeAutomation']");
			SeleniumTools.clickOnObject("xpath", "//span[contains(text(),'" + Workflow_Name + "')]");
			CoreUtil.imSleepy(5000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");
			// Clicking on Edit
			SeleniumTools.clickOnObject("xpath", "//button[text()=' Edit']");
			// Click on Delete
			SeleniumTools.clickOnObject("xpath", "//button[@ng-click='deleteWorkflowDetails()']");
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

	@Test(description = "Bolt Report - Query/Export of Facility data - My Reports - Print Bolt Report - ensure Notifications maintain Facility specificity")
	public static void Bolt_SG_04() {
		try {
			Login.StageLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("User1"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("User_Password"));
			Login.CheckMode("Verity QA Team1", "global");

			// Clicking on Bolt
			SeleniumTools.hoverElement("xpath", "//li[@text='Bolt']");
			// Clicking on Editor
			SeleniumTools.clickOnObject("xpath", "//span[text()='Reports']");
			CoreUtil.imSleepy(10000);
			// Clicking on print bolt report icon
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='title slat-title']/span[contains(text(),'Export Facility Data')]/following-sibling::div/button[@title='Print Bolt Report']");
			SeleniumTools.clickOnObject("xpath", "//a[text()='Ok']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.refreshPage();
			CoreUtil.imSleepy(20000);

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			// clicking on Notification
			SeleniumTools.hoverElement("xpath", "//echo-popover[@title='Notifications']/span/i");

			// Verifying report
			SeleniumTools.verifyText("xpath", "//div[@id='navEntry']//div[text()='Bolt_Export_Facility_Data']",
					"Bolt_Export_Facility_Data");
			SeleniumTools.getText("xpath",
					"//div[@id='navEntry']//div[text()='Bolt_Export_Facility_Data']/following-sibling::ul/li[last()]");

			// Clicking on Report
			SeleniumTools.scrollToElementAndClick("xpath",
					"//div[@id='navEntry']//div[text()='Bolt_Export_Facility_Data']");
			SeleniumTools.switchToChildWindow();
			CoreUtil.imSleepy(5000);
			SeleniumTools.getText("xpath", "//span[@id='errorMsg']");
			SeleniumTools.clickOnObject("xpath", "//a[text()='Click here to view the exported file']");
			CoreUtil.imSleepy(5000);

			// Verifying downloaded file
			File filelocation = new File("C:\\Users\\SureshGoparaju\\Downloads");
			File[] totalfiles = filelocation.listFiles();

			for (File file : totalfiles) {
				if (file.getName().contains("efd")) {
					System.out.println("File is downloaded.");
					// file.delete();
					break;
				}
			}
			// Validating Facility1 should be display, Facility2 should not be display
			NotepadReader.verifyNotepadText("efd.txt", "Facility1", "Facility2");
			SeleniumTools.closeTab();
			// Logout
			// ******** Logout And ReLogin ******************//
			Login.LogoutandReLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("User2"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("User_Password"));
			Login.CheckMode("Facility2", "facility");

			// Clicking on Bolt
			SeleniumTools.hoverElement("xpath", "//li[@text='Bolt']");
			// Clicking on Reports
			SeleniumTools.clickOnObject("xpath", "//span[text()='Reports']");
			CoreUtil.imSleepy(5000);
			// Clicking on print bolt report icon
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='title slat-title']/span[contains(text(),'Export Facility Data')]/following-sibling::div/button[@title='Print Bolt Report']");
			SeleniumTools.clickOnObject("xpath", "//a[text()='Ok']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.refreshPage();
			CoreUtil.imSleepy(20000);

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			// clicking on Notification
			SeleniumTools.hoverElement("xpath", "//echo-popover[@title='Notifications']/span/i");

			// Verifying report
			SeleniumTools.verifyText("xpath", "//div[@id='navEntry']//div[text()='Bolt_Export_Facility_Data']",
					"Bolt_Export_Facility_Data");
			SeleniumTools.getText("xpath",
					"//div[@id='navEntry']//div[text()='Bolt_Export_Facility_Data']/following-sibling::ul/li[last()]");

			// Clicking on Report
			SeleniumTools.scrollToElementAndClick("xpath",
					"//div[@id='navEntry']//div[text()='Bolt_Export_Facility_Data']");
			SeleniumTools.switchToChildWindow();
			CoreUtil.imSleepy(5000);
			SeleniumTools.getText("xpath", "//span[@id='errorMsg']");
			SeleniumTools.clickOnObject("xpath", "//a[text()='Click here to view the exported file']");
			CoreUtil.imSleepy(5000);

			// Verifying downloaded file
			File filelocation1 = new File("C:\\Users\\SureshGoparaju\\Downloads");
			File[] totalfiles1 = filelocation1.listFiles();

			for (File file : totalfiles1) {
				if (file.getName().contains("efd")) {
					System.out.println("File is downloaded.");
					// file.delete();
					break;
				}
			}
			// Validating Facility2 should be display, Facility1 should not be display
			NotepadReader.verifyNotepadText("efd.txt", "Facility2", "Facility1");

		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
			Assert.fail();
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}

	@Test(description = "1. Create and save new letter/listing----- Create a Listing\r\n" + "2. Upload a letter/listing"
			+ "Go to Editor - Listings – Print" + "Run a letter")
	public void Bolt_SG_05() {
		try {
			Login.StageLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Password"));
			// Verifying Global mode
			Login.CheckMode("Verity QA Team1", "global");
			// Clicking on Bolt
			SeleniumTools.hoverElement("xpath", "//li[@text='Bolt']");
			// Clicking on Editor
			SeleniumTools.clickOnObject("xpath", "//ul[@role='menu']//span[text()='Editor']");
			CoreUtil.imSleepy(20000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterFilterFrame']");

			SeleniumTools.ClearAndSetText("xpath", "//input[@name='full_nameTxt']", "");
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			// Select Query
			SeleniumTools.scrollToElementAndClick("xpath",
					"//a/span[text()='Queries']//ancestor::ul/following-sibling::div[@class='rwzContentWrapper']//div[@id='filterStep']//span[@class='rddtInner']/span[text()='Add new...']");

			SeleniumTools.ClearAndSetText("xpath",
					"//div[@id='ReporterWizard']//a/span[text()='Queries']//ancestor::div[@id='ReporterWizard']/preceding-sibling::div//input[@value='Search...']",
					"Spreadsheet - Create then Upload");
			SeleniumTools.scrollToElementAndClick("xpath",
					"//span[contains(text(),'Spreadsheet - Create then Upload')]");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterFilterFrame']");
			CoreUtil.imSleepy(6000);

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");

			// Clicking on Listings
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()='Listings']");
			CoreUtil.imSleepy(6000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterListingFrame']");
			// Enter Title
			Listing_title = SeleniumTools.ClearAndSetText("xpath",
					"//input[@ng-model='listing_controller.listing.Info.Title']",
					"Mr." + SeleniumTools.getRandomString(2));
			// select folder
			SeleniumTools.scrollToElementAndClick("xpath",
					"//button[@ng-click='listing_controller.browsePackages();']");
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='MainModalFrame']");
			// Click on Seautomation
			SeleniumTools.scrollToElementAndClick("xpath",
					"//div[@id='PackagesTreeView']//span[text()='SeAutomation']");
			SeleniumTools.scrollToElementAndClick("xpath", "//button[text()='Select Folder']");

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterListingFrame']");
			// Save
			SeleniumTools.scrollToElementAndClick("xpath", "//img[@title='Save Changes']");
			CoreUtil.imSleepy(15000);

			// **************** 2. Upload a letter/listing ************************
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterListingFrame']");
			// click on blue up arrow
			SeleniumTools.scrollToElementAndClick("xpath", "//input[@title='Upload Template']");
			CoreUtil.imSleepy(8000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("id", "popupFrame");
			// choose file
			SeleniumTools.scrollToElementAndClick("xpath", "//input[@name='Image1']");
			CoreUtil.imSleepy(5000);
			Runtime.getRuntime().exec(System.getProperty("user.dir") + "/src/test/resources/AutIT Scite/Listings.exe");
			CoreUtil.imSleepy(5000);
			// upload this file
			SeleniumTools.scrollToElementAndClick("xpath", "//input[@name='uploadButton']");
			CoreUtil.imSleepy(8000);
			SeleniumTools.getAlertText();
			CoreUtil.imSleepy(5000);
			// Save
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterListingFrame']");
			SeleniumTools.scrollToElementAndClick("xpath", "//img[@title='Save Changes']");
			CoreUtil.imSleepy(20000);

			// *** Generate listing
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterListingFrame']");
			SeleniumTools.scrollToElementAndClick("xpath", "//img[@title='Generate Listing']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.getAlertText();
			SeleniumTools.refreshPage();
			CoreUtil.imSleepy(20000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			// SeleniumTools.getText("xpath", "//span[text()='Report complete']");
			// clicking on Notification
			SeleniumTools.hoverElement("xpath", "//echo-popover[@title='Notifications']/span/i");
			// div[@id='navEntry']//echo-slat[@slat='notification']//div[text()='"+Listing_title+"']
			// validate report
			SeleniumTools.verifyText("xpath",
					"//div[@id='navEntry']//echo-slat[@slat='notification']//div[text()='" + Listing_title + "']",
					Listing_title);
			SeleniumTools.getText("xpath", "//div[@id='navEntry']//echo-slat[@slat='notification']//div[text()='"
					+ Listing_title + "']/parent::div/ul/li[last()]");
			// div[@id='navEntry']//echo-slat[@slat='notification']//div[text()='"+Listing_title+"']/parent::div/ul/li[last()]
			// Clicking on Report
			SeleniumTools.scrollToElementAndClick("xpath",
					"//div[@id='navEntry']//echo-slat[@slat='notification']//div[text()='" + Listing_title + "']");
			CoreUtil.imSleepy(10000);
			// Validating Facility1 should be display, Facility2 should not be display
			RTFReader.RTF("note.doc", "Suresh");
			// Clicking on Bolt
			SeleniumTools.hoverElement("xpath", "//li[@text='Bolt']");
			// Clicking on Editor
			SeleniumTools.clickOnObject("xpath", "//ul[@role='menu']//span[text()='Editor']");
			CoreUtil.imSleepy(20000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterFilterFrame']");

			SeleniumTools.ClearAndSetText("xpath", "//input[@name='full_nameTxt']", "");
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			// Select Query
			SeleniumTools.scrollToElementAndClick("xpath",
					"//a/span[text()='Queries']//ancestor::ul/following-sibling::div[@class='rwzContentWrapper']//div[@id='filterStep']//span[@class='rddtInner']/span[text()='Add new...']");

			SeleniumTools.ClearAndSetText("xpath",
					"//div[@id='ReporterWizard']//a/span[text()='Queries']//ancestor::div[@id='ReporterWizard']/preceding-sibling::div//input[@value='Search...']",
					"Spreadsheet - Create then Upload");
			SeleniumTools.scrollToElementAndClick("xpath",
					"//span[contains(text(),'Spreadsheet - Create then Upload')]");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterFilterFrame']");
			CoreUtil.imSleepy(6000);

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");

			// Clicking on Listings
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()='Listings']");
			CoreUtil.imSleepy(6000);
			// Clicking on Dropdown
			SeleniumTools.scrollToElementAndClick("xpath", "//div[@id='listingTree']/span");
			// Clicking on search...
			SeleniumTools.ClearAndSetText("xpath",
					"//a/span[text()='Spreadsheets']//ancestor::div[@id='ReporterWizard']/preceding-sibling::div//input[@class='rddtFilterInput radPreventDecorate rddtFilterEmptyMessage']",
					Listing_title);
			SeleniumTools.scrollToElementAndClick("xpath", "//span[contains(text(),'" + Listing_title + "')]");

			// *** Delete listing
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterListingFrame']");
			SeleniumTools.scrollToElementAndClick("xpath", "//img[@title='Delete Listing']");
			SeleniumTools.getAlertText();

		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}

	@Test(description = "Editor – Listings – Add New Listing - Add Table and Merge Variables")
	public void Bolt_SG_06() {
		try {
			Login.StageLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Password"));
			// Verifying Global mode
			Login.CheckMode("Verity QA Team1", "global");
			// Clicking on Bolt
			SeleniumTools.hoverElement("xpath", "//li[@text='Bolt']");
			// Clicking on Editor
			SeleniumTools.clickOnObject("xpath", "//ul[@role='menu']//span[text()='Editor']");
			CoreUtil.imSleepy(20000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterFilterFrame']");

			SeleniumTools.ClearAndSetText("xpath", "//input[@name='full_nameTxt']", "");
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			// Select Query
			SeleniumTools.scrollToElementAndClick("xpath",
					"//a/span[text()='Queries']//ancestor::ul/following-sibling::div[@class='rwzContentWrapper']//div[@id='filterStep']//span[@class='rddtInner']/span[text()='Add new...']");

			SeleniumTools.ClearAndSetText("xpath",
					"//div[@id='ReporterWizard']//a/span[text()='Queries']//ancestor::div[@id='ReporterWizard']/preceding-sibling::div//input[@value='Search...']",
					"Spreadsheet - Create then Upload");
			SeleniumTools.scrollToElementAndClick("xpath",
					"//span[contains(text(),'Spreadsheet - Create then Upload')]");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterFilterFrame']");
			CoreUtil.imSleepy(6000);

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");

			// Clicking on Listings
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()='Listings']");
			CoreUtil.imSleepy(6000);

			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterListingFrame']");
			// Enter Title
			Listing_title = SeleniumTools.ClearAndSetText("xpath",
					"//input[@ng-model='listing_controller.listing.Info.Title']",
					"Mr." + SeleniumTools.getRandomString(2));
			// select folder
			SeleniumTools.scrollToElementAndClick("xpath",
					"//button[@ng-click='listing_controller.browsePackages();']");
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='MainModalFrame']");
			// Click on Seautomation
			SeleniumTools.scrollToElementAndClick("xpath",
					"//div[@id='PackagesTreeView']//span[text()='SeAutomation']");
			SeleniumTools.scrollToElementAndClick("xpath", "//button[text()='Select Folder']");

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterListingFrame']");
			// Save
			SeleniumTools.scrollToElementAndClick("xpath", "//img[@title='Save Changes']");
			CoreUtil.imSleepy(15000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterListingFrame']");
			// **************** Editor – Listings – Add New Listing - Add Table and Merge
			// Variables
			// Clicking on Editor
			SeleniumTools.scrollToElementAndClick("xpath",
					"//ul[@class='k-tabstrip-items k-reset']//span[contains(text(),'Editor')]");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='editor-frame']");

			// click on insert table
			SeleniumTools.scrollToElementAndClick("xpath", "//a[@title='Insert Table']");
			// Select table
			SeleniumTools.scrollToElementAndClick("xpath",
					"//div[@class='Default reDropDownBody reInsertTable']//table/tbody/tr[1]/td[5]");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='RadEditor1_contentIframe']");

			SeleniumTools.EnterValuePressTab("1.");
			SeleniumTools.PressTabwithValue("2.");
			SeleniumTools.PressTabwithValue("3.");
			SeleniumTools.PressTabwithValue("4.");
			SeleniumTools.PressTabwithValue("5.");

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterListingFrame']");
			// click on view variable
			SeleniumTools.scrollToElementAndClick("xpath", "//img[@title='View Variables']");
			CoreUtil.imSleepy(10000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frame-modal-clone-1']");
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@class='Search-input searchText form-control']",
					"First Name");
			CoreUtil.imSleepy(20000);
			/// Click on provider/physician
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()='Provider/Physician                      ']");
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()='FirstName']");
			// CopyTo Clipboard
			SeleniumTools.clickOnObject("xpath", "//button[@ng-disabled='!VariableName']");
			SeleniumTools.switchToDefaultFrame();
			// Close window
			SeleniumTools.clickOnObject("xpath", "//h4[text()='Variables']/preceding-sibling::button");
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterListingFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='editor-frame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='RadEditor1_contentIframe']");

			SeleniumTools.scrollToElementAndClick("xpath", "//td[contains(text(),'1.')]");
			SeleniumTools.PressKey("End");
			SeleniumTools.PressKey("Paste");
			// ===================================================

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterListingFrame']");
			// click on view variable
			SeleniumTools.scrollToElementAndClick("xpath", "//img[@title='View Variables']");
			CoreUtil.imSleepy(10000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frame-modal-clone-1']");
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@class='Search-input searchText form-control']",
					"MiddleName");
			CoreUtil.imSleepy(20000);
			/// Click on provider/physician
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()='Provider/Physician                      ']");
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()='MiddleName']");
			// CopyTo Clipboard
			SeleniumTools.clickOnObject("xpath", "//button[@ng-disabled='!VariableName']");
			SeleniumTools.switchToDefaultFrame();
			// Close window
			SeleniumTools.clickOnObject("xpath", "//h4[text()='Variables']/preceding-sibling::button");
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterListingFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='editor-frame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='RadEditor1_contentIframe']");

			SeleniumTools.scrollToElementAndClick("xpath", "//td[contains(text(),'2.')]");
			SeleniumTools.PressKey("End");
			SeleniumTools.PressKey("Paste");
			// ===================================================
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterListingFrame']");
			// click on view variable
			SeleniumTools.scrollToElementAndClick("xpath", "//img[@title='View Variables']");
			CoreUtil.imSleepy(10000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frame-modal-clone-1']");
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@class='Search-input searchText form-control']",
					"Last Name");
			CoreUtil.imSleepy(20000);
			/// Click on provider/physician
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()='Provider/Physician                      ']");
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()='LastName']");
			// CopyTo Clipboard
			SeleniumTools.clickOnObject("xpath", "//button[@ng-disabled='!VariableName']");
			SeleniumTools.switchToDefaultFrame();
			// Close window
			SeleniumTools.clickOnObject("xpath", "//h4[text()='Variables']/preceding-sibling::button");
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterListingFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='editor-frame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='RadEditor1_contentIframe']");

			SeleniumTools.scrollToElementAndClick("xpath", "//td[contains(text(),'3.')]");
			SeleniumTools.PressKey("End");
			SeleniumTools.PressKey("Paste");
			// ===================================================
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterListingFrame']");
			// click on view variable
			SeleniumTools.scrollToElementAndClick("xpath", "//img[@title='View Variables']");
			CoreUtil.imSleepy(10000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frame-modal-clone-1']");
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@class='Search-input searchText form-control']",
					"Gender");
			CoreUtil.imSleepy(20000);
			/// Click on provider/physician
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()='Provider/Physician                      ']");
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()='Gender']");
			// CopyTo Clipboard
			SeleniumTools.clickOnObject("xpath", "//button[@ng-disabled='!VariableName']");
			SeleniumTools.switchToDefaultFrame();
			// Close window
			SeleniumTools.clickOnObject("xpath", "//h4[text()='Variables']/preceding-sibling::button");
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterListingFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='editor-frame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='RadEditor1_contentIframe']");

			SeleniumTools.scrollToElementAndClick("xpath", "//td[contains(text(),'4.')]");
			SeleniumTools.PressKey("End");
			SeleniumTools.PressKey("Paste");
			// ===================================================
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterListingFrame']");
			// click on view variable
			SeleniumTools.scrollToElementAndClick("xpath", "//img[@title='View Variables']");
			CoreUtil.imSleepy(10000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frame-modal-clone-1']");
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@class='Search-input searchText form-control']",
					"Birthdate");
			CoreUtil.imSleepy(20000);
			/// Click on provider/physician
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()='Provider/Physician                      ']");
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()='Birthdate']");
			// CopyTo Clipboard
			SeleniumTools.clickOnObject("xpath", "//button[@ng-disabled='!VariableName']");
			SeleniumTools.switchToDefaultFrame();
			// Close window
			SeleniumTools.clickOnObject("xpath", "//h4[text()='Variables']/preceding-sibling::button");
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterListingFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='editor-frame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='RadEditor1_contentIframe']");

			SeleniumTools.scrollToElementAndClick("xpath", "//td[contains(text(),'5.')]");
			SeleniumTools.PressKey("End");
			SeleniumTools.PressKey("Paste");
			// ===================================================

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterListingFrame']");
			// click on Save
			SeleniumTools.scrollToElementAndClick("xpath", "//img[@title='Save Changes']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.getAlertText();
			CoreUtil.imSleepy(5000);
			SeleniumTools.getAlertText();
			CoreUtil.imSleepy(20000);

			// *** Generate listing
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterListingFrame']");
			SeleniumTools.scrollToElementAndClick("xpath", "//img[@title='Generate Listing']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.getAlertText();

			SeleniumTools.refreshPage();
			CoreUtil.imSleepy(20000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			// SeleniumTools.getText("xpath", "//span[text()='Report complete']");
			// clicking on Notification
			SeleniumTools.hoverElement("xpath", "//echo-popover[@title='Notifications']/span/i");

			// validate report
			SeleniumTools.verifyText("xpath",
					"//div[@id='navEntry']//echo-slat[@slat='notification']//div[text()='" + Listing_title + "']",
					Listing_title);
			SeleniumTools.getText("xpath", "//div[@id='navEntry']//echo-slat[@slat='notification']//div[text()='"
					+ Listing_title + "']/parent::div/ul/li[last()]");

			// Clicking on Report
			SeleniumTools.scrollToElementAndClick("xpath",
					"//div[@id='navEntry']//echo-slat[@slat='notification']//div[text()='" + Listing_title + "']");
			CoreUtil.imSleepy(10000);
			// Validating Facility1 should be display, Facility2 should not be display
			RTFReader.RTF("note.doc", "05/24/2021");

			// Clicking on Bolt
			SeleniumTools.hoverElement("xpath", "//li[@text='Bolt']");
			// Clicking on Editor
			SeleniumTools.clickOnObject("xpath", "//ul[@role='menu']//span[text()='Editor']");
			CoreUtil.imSleepy(20000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterFilterFrame']");

			SeleniumTools.ClearAndSetText("xpath", "//input[@name='full_nameTxt']", "");
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			// Select Query
			SeleniumTools.scrollToElementAndClick("xpath",
					"//a/span[text()='Queries']//ancestor::ul/following-sibling::div[@class='rwzContentWrapper']//div[@id='filterStep']//span[@class='rddtInner']/span[text()='Add new...']");

			SeleniumTools.ClearAndSetText("xpath",
					"//div[@id='ReporterWizard']//a/span[text()='Queries']//ancestor::div[@id='ReporterWizard']/preceding-sibling::div//input[@value='Search...']",
					"Spreadsheet - Create then Upload");
			SeleniumTools.scrollToElementAndClick("xpath",
					"//span[contains(text(),'Spreadsheet - Create then Upload')]");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterFilterFrame']");
			CoreUtil.imSleepy(6000);

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");

			// Clicking on Listings
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()='Listings']");
			CoreUtil.imSleepy(6000);
			// Clicking on Dropdown
			SeleniumTools.scrollToElementAndClick("xpath", "//div[@id='listingTree']/span");
			// Clicking on search...
			SeleniumTools.ClearAndSetText("xpath",
					"//a/span[text()='Spreadsheets']//ancestor::div[@id='ReporterWizard']/preceding-sibling::div//input[@class='rddtFilterInput radPreventDecorate rddtFilterEmptyMessage']",
					Listing_title);
			SeleniumTools.scrollToElementAndClick("xpath", "//span[contains(text(),'" + Listing_title + "')]");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterListingFrame']");

			// *** Delete listing
			SeleniumTools.scrollToElementAndClick("xpath", "//img[@title='Delete Listing']");
			SeleniumTools.getAlertText();

		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}

	@Test(description = "Create & Execute a Spreadsheet" + "Bolt - Go to Editor - Spreadsheet – Print")
	public void Bolt_SG_07() {
		try {
			Login.StageLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Password"));
			// Verifying Global mode
			Login.CheckMode("Verity QA Team1", "global");
			// Clicking on Bolt
			SeleniumTools.hoverElement("xpath", "//li[@text='Bolt']");
			// Clicking on Editor
			SeleniumTools.clickOnObject("xpath", "//ul[@role='menu']//span[text()='Editor']");
			CoreUtil.imSleepy(20000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterFilterFrame']");
			// Enter Query name
			SeleniumTools.ClearAndSetText("xpath", "//input[@name='full_nameTxt']", "");

			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			// Clicking on Dropdown
			SeleniumTools.scrollToElementAndClick("xpath",
					"//a/span[text()='Queries']//ancestor::ul/following-sibling::div[@class='rwzContentWrapper']//div[@id='filterStep']//span[@class='rddtInner']/span[text()='Add new...']");

			// Searching for record
			SeleniumTools.ClearAndSetText("xpath",
					"//div[@id='ReporterWizard']//a/span[text()='Queries']//ancestor::div[@id='ReporterWizard']/preceding-sibling::div//input[@value='Search...']",
					"Spreadsheet - Create then Upload");
			SeleniumTools.scrollToElementAndClick("xpath",
					"//span[contains(text(),'Spreadsheet - Create then Upload')]");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterFilterFrame']");
			// Clicking on Execute Query
			SeleniumTools.scrollToElementAndClick("xpath", "//input[@name='executeButton']");
			CoreUtil.imSleepy(6000);

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");

			// Clicking on Spreadsheet
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()='Spreadsheets']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterDataWizFrame']");
			// Enter Title
			Spreadsheet_Title = SeleniumTools.ClearAndSetText("xpath", "//input[@name='txtTitle']",
					"Create & Execute " + SeleniumTools.getRandomNumber(3));
			// Selecting Folder
			SeleniumTools.scrollToElementAndClick("xpath", "//button[@id='readonlyviewarchive']");
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='MainModalFrame']");
			SeleniumTools.scrollToElementAndClick("xpath",
					"//div[@id='PackagesTreeView']//span[text()='SeAutomation']");
			SeleniumTools.clickOnObject("xpath", "//button[text()='Select Folder']");

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterDataWizFrame']");

			// Add Variables
			SeleniumTools.scrollToElementAndClick("xpath", "//div[@id='variablesTab']//button[text()='Add Variable']");
			CoreUtil.imSleepy(10000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='MainModalFrame']");
			// Search
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@name='txtVarSearch']", "Last Name");
			SeleniumTools.scrollToElementAndClick("xpath", "//div[text()='Provider/Physician']");
			SeleniumTools.scrollToElementAndClick("xpath", "//div[text()='LastName - Last Name']");
			// Add Selected
			SeleniumTools.scrollToElementAndClick("xpath", "//button[text()='Add Selected']");
			CoreUtil.imSleepy(5000);

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterDataWizFrame']");
			// Click on Save
			SeleniumTools.scrollToElementAndClick("xpath",
					"//div[@id='buttonsAndTitle']//input[@title='Save Changes']");
			CoreUtil.imSleepy(20000);

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterDataWizFrame']");
			// Generate Spreadsheet
			SeleniumTools.scrollToElementAndClick("xpath", "//input[@title='Generate Spreadsheet']");

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			// clicking on OK
			SeleniumTools.scrollToElementAndClick("xpath", "//button[text()='OK']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.refreshPage();
			CoreUtil.imSleepy(20000);

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			// SeleniumTools.getText("xpath", "//span[text()='Report complete']");
			// clicking on Notification
			SeleniumTools.hoverElement("xpath", "//echo-popover[@title='Notifications']/span/i");

			// Verifying report
			SeleniumTools.verifyText("xpath",
					"//div[@id='navEntry']//echo-slat[@slat='notification']//div[text()='" + Spreadsheet_Title + "']",
					Spreadsheet_Title);
			SeleniumTools.getText("xpath", "//div[@id='navEntry']//echo-slat[@slat='notification']//div[text()='"
					+ Spreadsheet_Title + "']/parent::div/ul/li[last()]");

			// Clicking on Report
			SeleniumTools.scrollToElementAndClick("xpath",
					"//div[@id='navEntry']//echo-slat[@slat='notification']//div[text()='" + Spreadsheet_Title + "']");
			CoreUtil.imSleepy(5000);

			// Verifying downloaded file
			File filelocation = new File("C:\\Users\\SureshGoparaju\\Downloads");
			File[] totalfiles = filelocation.listFiles();

			for (File file : totalfiles) {
				if (file.getName().contains("create - spreadsheet")) {
					System.out.println("File is downloaded.");
					break;
				}
			}

			fileName = SeleniumTools.getRecentFile();
			// String location = fileName.replace("\\", "\\\\");
			ExcelReader.readAllExcelFiles(fileName, "Goparaju");

			// Clicking on Bolt
			SeleniumTools.hoverElement("xpath", "//li[@text='Bolt']");
			// Clicking on Editor
			SeleniumTools.clickOnObject("xpath", "//ul[@role='menu']//span[text()='Editor']");
			CoreUtil.imSleepy(20000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterFilterFrame']");

			SeleniumTools.ClearAndSetText("xpath", "//input[@name='full_nameTxt']", "");
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			// Select Query
			SeleniumTools.scrollToElementAndClick("xpath",
					"//a/span[text()='Queries']//ancestor::ul/following-sibling::div[@class='rwzContentWrapper']//div[@id='filterStep']//span[@class='rddtInner']/span[text()='Add new...']");

			SeleniumTools.ClearAndSetText("xpath",
					"//div[@id='ReporterWizard']//a/span[text()='Queries']//ancestor::div[@id='ReporterWizard']/preceding-sibling::div//input[@value='Search...']",
					"Spreadsheet - Create then Upload");
			SeleniumTools.scrollToElementAndClick("xpath",
					"//span[contains(text(),'Spreadsheet - Create then Upload')]");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterFilterFrame']");
			// Clicking on Execute Query
			SeleniumTools.scrollToElementAndClick("xpath", "//input[@name='executeButton']");
			CoreUtil.imSleepy(6000);

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");

			// Clicking on Spreadsheet
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()='Spreadsheets']");
			// Clicking on Dropdown
			SeleniumTools.scrollToElementAndClick("xpath",
					"//div[@class='RadDropDownTree RadDropDownTree_Metro'][@id='data_WizardTree']/span");
			// Searching for record
			SeleniumTools.ClearAndSetText("xpath",
					"//a/span[text()='Spreadsheets']//ancestor::div[@id='ReporterWizard']/preceding-sibling::div//input[@class='rddtFilterInput radPreventDecorate rddtFilterEmptyMessage']",
					Spreadsheet_Title);
			SeleniumTools.scrollToElementAndClick("xpath", "//span[contains(text(),'" + Spreadsheet_Title + "')]");

			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterDataWizFrame']");

			// Click on Delete
			SeleniumTools.scrollToElementAndClick("xpath",
					"//div[@id='buttonsAndTitle']//input[@title='Delete Spreadsheet']");
			SeleniumTools.getAlertText();
			CoreUtil.imSleepy(5000);

			// ************** Bolt - Go to Editor - Spreadsheet – Print *********
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			// Clicking on Dropdown
			SeleniumTools.scrollToElementAndClick("xpath",
					"//div[@class='RadDropDownTree RadDropDownTree_Metro'][@id='data_WizardTree']/span");
			// Searching for record
			SeleniumTools.ClearAndSetText("xpath",
					"//a/span[text()='Spreadsheets']//ancestor::div[@id='ReporterWizard']/preceding-sibling::div//input[@class='rddtFilterInput radPreventDecorate rddtFilterEmptyMessage']",
					"Create - Spreadsheet");
			SeleniumTools.scrollToElementAndClick("xpath", "//span[contains(text(),'Create - Spreadsheet')]");

			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterDataWizFrame']");
			// Generate Spreadsheet
			SeleniumTools.scrollToElementAndClick("xpath", "//input[@title='Generate Spreadsheet']");

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			// clicking on OK
			SeleniumTools.scrollToElementAndClick("xpath", "//button[text()='OK']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.refreshPage();
			CoreUtil.imSleepy(10000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			// SeleniumTools.getText("xpath", "//span[text()='Report complete']");
			// clicking on Notification
			SeleniumTools.hoverElement("xpath", "//echo-popover[@title='Notifications']/span/i");

			// Verifying report
			SeleniumTools.verifyText("xpath",
					"//div[@id='navEntry']//echo-slat[@slat='notification']//div[text()='Create - Spreadsheet']",
					"Create - Spreadsheet");
			SeleniumTools.getText("xpath",
					"//div[@id='navEntry']//echo-slat[@slat='notification']//div[text()='Create - Spreadsheet']/parent::div/ul/li[last()]");

			// Clicking on Report
			SeleniumTools.scrollToElementAndClick("xpath",
					"//div[@id='navEntry']//echo-slat[@slat='notification']//div[text()='Create - Spreadsheet']");
			CoreUtil.imSleepy(5000);

			// Verifying downloaded file
			for (File file : totalfiles) {
				if (file.getName().contains("create - spreadsheet")) {
					System.out.println("File is downloaded.");
					break;
				}
			}

			fileName = SeleniumTools.getRecentFile();
			ExcelReader.readAllExcelFiles(fileName, "Goparaju");

		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}

	@Test(description = "BOLT-Others-Map")
	public void Bolt_SG_08() {
		try {
			Login.StageLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Password"));
			// Verifying Global mode
			Login.CheckMode("Verity QA Team1", "global");
			SeleniumTools.hoverElement("xpath", "//li[@text='Search'] [@class='k-item k-state-default']");
			SeleniumTools.clickOnObject("xpath", "//span[text()='Search']/ancestor::li//span[text()='Provider']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			// Enter Value in search box
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@ng-model='query']", "Hamil Mark");
			CoreUtil.imSleepy(5000);
			// Clicking on Education
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='tree-container k-widget k-treeview']/ul//span[contains(text(),'Education:')]");
			SeleniumTools.waitForFrameToBeAvailable("id", "browserFrame");
			// Click on Add Education
			SeleniumTools.clickOnObject("xpath", "//button[contains(text(),'Add Education')]");
			SeleniumTools.waitForFrameToBeAvailable("id", "FormFrame");
			// Click on Type
			SeleniumTools.clickOnObject("xpath", "//label[text()='Type']/following-sibling::echo-lookup//button");
			CoreUtil.imSleepy(10000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "frame-modal-clone-1");
			SeleniumTools.ClearAndSetText("xpath", "//input[@placeholder='Search by text']", "Medical School");
			SeleniumTools.clickOnObject("xpath", "//div[@ng-if='showSearchCd']/input[@value='Search']");

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("id", "browserFrame");
			SeleniumTools.waitForFrameToBeAvailable("id", "FormFrame");
			// Click on Institute
			SeleniumTools.clickOnObject("xpath",
					"//label[text()='Institution']/following-sibling::echo-lookup//button");
			CoreUtil.imSleepy(10000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "frame-modal-clone-1");
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@placeholder='Search by text']",
					"University of Tampa");
			CoreUtil.imSleepy(5000);
			SeleniumTools.clickOnObject("xpath", "//td[contains(text(),'University of Tampa')]");
			CoreUtil.imSleepy(5000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("id", "browserFrame");
			SeleniumTools.waitForFrameToBeAvailable("id", "FormFrame");
			// Click on Save
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save']");
			CoreUtil.imSleepy(20000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");

			// Clicking on Bolt
			SeleniumTools.hoverElement("xpath", "//li[@text='Bolt']");
			// Clicking on Editor
			SeleniumTools.clickOnObject("xpath", "//ul[@role='menu']//span[text()='Editor']");
			CoreUtil.imSleepy(20000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterFilterFrame']");
			// Enter Query name
			SeleniumTools.ClearAndSetText("xpath", "//input[@name='full_nameTxt']", "");
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			// Clicking on Dropdown
			SeleniumTools.scrollToElementAndClick("xpath",
					"//a/span[text()='Queries']//ancestor::ul/following-sibling::div[@class='rwzContentWrapper']//div[@id='filterStep']//span[@class='rddtInner']/span[text()='Add new...']");

			// Searching for record
			SeleniumTools.ClearAndSetText("xpath",
					"//div[@id='ReporterWizard']//a/span[text()='Queries']//ancestor::div[@id='ReporterWizard']/preceding-sibling::div//input[@value='Search...']",
					"Bolt_Mark Hamil");
			SeleniumTools.scrollToElementAndClick("xpath", "//span[contains(text(),'Bolt_Mark Hamil')]");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterFilterFrame']");
			// Clicking on Execute Query
			SeleniumTools.scrollToElementAndClick("xpath", "//input[@name='executeButton']");
			CoreUtil.imSleepy(6000);

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");

			// Clicking on Others
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()='Others']");
			// Clicking on Dropdown
			SeleniumTools.scrollToElementAndClick("xpath", "//div[@id='othersTree']/span");
			// Searching for record
			SeleniumTools.ClearAndSetText("xpath",
					"//a/span[text()='Spreadsheets']//ancestor::div[@id='ReporterWizard']/preceding-sibling::div//input[@class='rddtFilterInput radPreventDecorate rddtFilterEmptyMessage']",
					"Map");
			CoreUtil.imSleepy(4000);
			// Clicking on Map
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()='Map']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterOthersFrame']");
			// Clicking on Medical school
			SeleniumTools.scrollToElementAndClick("xpath", "//label[text()='vMED:Medical School']");

			CoreUtil.imSleepy(2000);
			// Click on Execute Query
			SeleniumTools.javascriptClick("//input[@name='ImageButton1']");
			// SeleniumTools.scrollToElementAndClick("xpath",
			// "//input[@name='ImageButton1']");
			CoreUtil.imSleepy(4000);
			Actual = SeleniumTools.getTextAlert();
			Assert.assertTrue(Actual.contains("It may take a minute to load addresses"));
			SeleniumTools.getAlertText();
			CoreUtil.imSleepy(5000);
			SeleniumTools.switchToWindowByIndex(1);

			// SeleniumTools.verifyText("xpath", "//td[contains(text(),'401 W Kennedy
			// Blvd')]", " 401 W Kennedy Blvd # N, , Tampa, FL, 33606 ");
			SeleniumTools.clickOnObject("xpath", "//span[text()='Reset Map ']");
			SeleniumTools.getAlertText();
			SeleniumTools.closeTab();
			CoreUtil.imSleepy(2000);
			SeleniumTools.switchToDefaultWindow();
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterOthersFrame']");
			// Click on Execute Query
			SeleniumTools.javascriptClick("//input[@name='ImageButton1']");
			CoreUtil.imSleepy(4000);
			SeleniumTools.getAlertText();
			CoreUtil.imSleepy(5000);
			SeleniumTools.switchToWindowByIndex(1);
			CoreUtil.imSleepy(5000);
			SeleniumTools.getText("xpath", "//td[contains(text(),'401 W Kennedy Blvd')]");
			SeleniumTools.verifyText("xpath", "//td[contains(text(),'401 W Kennedy Blvd')]",
					"401 W Kennedy Blvd # N, , Tampa, FL, 33606");
			CoreUtil.imSleepy(5000);
			SeleniumTools.closeTab();

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.hoverElement("xpath", "//li[@text='Search'] [@class='k-item k-state-default']");
			SeleniumTools.clickOnObject("xpath", "//span[text()='Search']/ancestor::li//span[text()='Provider']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("id", "browserFrame");
			SeleniumTools.waitForFrameToBeAvailable("id", "FormFrame");
			// Click on Delete
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Delete']");
			SeleniumTools.clickOnObject("xpath", "//echo-interrupt[@class='ng-isolate-scope']//a[text()='Delete']");

		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}

	@Test(description = "BOLT-Others-Batch Update")
	public void Bolt_SG_09() {
		try {
			Login.StageLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Password"));
			// Verifying Global mode
			Login.CheckMode("Verity QA Team1", "global");

			// ******** Preconditons ******** adding education record for both providers
			// ********
			// Navigate provider
			SeleniumTools.hoverElement("xpath", "//li[@text='Search'] [@class='k-item k-state-default']");
			SeleniumTools.clickOnObject("xpath", "//span[text()='Search']/ancestor::li//span[text()='Provider']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			// Enter Value in search box
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@ng-model='query']", "suresh Goparaju");
			CoreUtil.imSleepy(5000);
			// Clicking on Workflows
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='tree-container k-widget k-treeview']//span[contains(text(),'Education:')]");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			// Add Education
			SeleniumTools.clickOnObject("xpath", "//button[contains(text(),'Add Education')]");
			CoreUtil.imSleepy(10000);
			SeleniumTools.waitForFrameToBeAvailable("id", "FormFrame");
			// Click on Type
			SeleniumTools.clickOnObject("xpath", "//echo-lookup[@model='formFields.sch_type']//button");

			CoreUtil.imSleepy(10000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "frame-modal-clone-1");
			SeleniumTools.ClearAndSetText("xpath", "//input[@placeholder='Search by text']", "Medical School");
			SeleniumTools.clickOnObject("xpath", "//div[@ng-if='showSearchCd']/input[@value='Search']");

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("id", "browserFrame");
			SeleniumTools.waitForFrameToBeAvailable("id", "FormFrame");
			// Click on Institute
			SeleniumTools.clickOnObject("xpath",
					"//label[text()='Institution']/following-sibling::echo-lookup//button");
			CoreUtil.imSleepy(10000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "frame-modal-clone-1");
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@placeholder='Search by text']",
					"University of Tampa");
			CoreUtil.imSleepy(5000);
			SeleniumTools.clickOnObject("xpath", "//td[contains(text(),'University of Tampa')]");
			CoreUtil.imSleepy(5000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("id", "browserFrame");
			SeleniumTools.waitForFrameToBeAvailable("id", "FormFrame");
			// Click on Save
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save']");
			CoreUtil.imSleepy(20000);

			// Navigate to suresh G
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			// Enter Value in search box
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@ng-model='query']", "suresh G");
			CoreUtil.imSleepy(5000);
			SeleniumTools.clickOnObject("xpath", "//span[text()='Suresh, G,']/preceding-sibling::span");
			CoreUtil.imSleepy(5000);
			// Clicking on education
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='tree-container k-widget k-treeview']//span[contains(text(),'Education:')]");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			// Add Education
			SeleniumTools.clickOnObject("xpath", "//button[contains(text(),'Add Education')]");
			CoreUtil.imSleepy(10000);
			SeleniumTools.waitForFrameToBeAvailable("id", "FormFrame");
			// Click on Type
			SeleniumTools.clickOnObject("xpath", "//echo-lookup[@model='formFields.sch_type']//button");

			CoreUtil.imSleepy(10000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "frame-modal-clone-1");
			SeleniumTools.ClearAndSetText("xpath", "//input[@placeholder='Search by text']", "Medical School");
			SeleniumTools.clickOnObject("xpath", "//div[@ng-if='showSearchCd']/input[@value='Search']");

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("id", "browserFrame");
			SeleniumTools.waitForFrameToBeAvailable("id", "FormFrame");
			// Click on Institute
			SeleniumTools.clickOnObject("xpath",
					"//label[text()='Institution']/following-sibling::echo-lookup//button");
			CoreUtil.imSleepy(10000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "frame-modal-clone-1");
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@placeholder='Search by text']",
					"University of Tampa");
			CoreUtil.imSleepy(5000);
			SeleniumTools.clickOnObject("xpath", "//td[contains(text(),'University of Tampa')]");
			CoreUtil.imSleepy(5000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("id", "browserFrame");
			SeleniumTools.waitForFrameToBeAvailable("id", "FormFrame");
			// Click on Save
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Save']");
			CoreUtil.imSleepy(20000);

			// Navigating to Bolt->others
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			// Clicking on Bolt
			SeleniumTools.hoverElement("xpath", "//li[@text='Bolt']");
			// Clicking on Editor
			SeleniumTools.clickOnObject("xpath", "//ul[@role='menu']//span[text()='Editor']");
			CoreUtil.imSleepy(20000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterFilterFrame']");
			// Enter Query name
			SeleniumTools.ClearAndSetText("xpath", "//input[@name='full_nameTxt']", "");
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			// Clicking on Dropdown
			SeleniumTools.scrollToElementAndClick("xpath",
					"//a/span[text()='Queries']//ancestor::ul/following-sibling::div[@class='rwzContentWrapper']//div[@id='filterStep']//span[@class='rddtInner']/span[text()='Add new...']");

			// Searching for record
			SeleniumTools.ClearAndSetText("xpath",
					"//div[@id='ReporterWizard']//a/span[text()='Queries']//ancestor::div[@id='ReporterWizard']/preceding-sibling::div//input[@value='Search...']",
					"Spreadsheet - Create then Upload");
			SeleniumTools.scrollToElementAndClick("xpath",
					"//span[contains(text(),'Spreadsheet - Create then Upload')]");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterFilterFrame']");
			// Clicking on Execute Query
			SeleniumTools.scrollToElementAndClick("xpath", "//input[@name='executeButton']");
			CoreUtil.imSleepy(6000);

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");

			// Clicking on Others
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()='Others']");
			// Clicking on Dropdown
			SeleniumTools.scrollToElementAndClick("xpath", "//div[@id='othersTree']/span");
			// Searching for record
			SeleniumTools.ClearAndSetText("xpath",
					"//a/span[text()='Spreadsheets']//ancestor::div[@id='ReporterWizard']/preceding-sibling::div//input[@class='rddtFilterInput radPreventDecorate rddtFilterEmptyMessage']",
					"Batch Update");
			CoreUtil.imSleepy(4000);
			// Clicking on Map
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()='Batch Update']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterOthersFrame']");
			// Select table dropdown
			SeleniumTools.selectByText("xpath", "//select[@name='TableDropDownList']", "Education");
			CoreUtil.imSleepy(5000);
			// Select feild dropdown
			SeleniumTools.selectByText("xpath", "//select[@name='FieldDropDownList']", "Degree");
			CoreUtil.imSleepy(5000);
			// set new value
			SeleniumTools.scrollToElementAndClick("xpath", "//input[@name='LookupSelectorButton']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='frame-modal-clone-1']");
			SeleniumTools.scrollToElementAndClick("xpath", "//td[contains(text(),'Associates Degree (AD)')]");

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterOthersFrame']");
			// Click on execute
			SeleniumTools.scrollToElementAndClick("xpath", "//input[@name='UpdateButton']");
			SeleniumTools.getAlertText();

			// Verify Field
			SeleniumTools.verifyText("xpath", "//table[@class='resultsgridview']/tbody/tr/th[1]", "Degree");
			SeleniumTools.getText("xpath", "//table[@class='resultsgridview']/tbody/tr/td[1]");
			// Verify Value
			SeleniumTools.verifyText("xpath", "//table[@class='resultsgridview']/tbody/tr/td[1]",
					"Associates Degree (AD)");

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			// Navigate provider
			SeleniumTools.hoverElement("xpath", "//li[@text='Search'] [@class='k-item k-state-default']");
			SeleniumTools.clickOnObject("xpath", "//span[text()='Search']/ancestor::li//span[text()='Provider']");

			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.waitForFrameToBeAvailable("id", "FormFrame");
			// Click on Delete
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Delete']");
			SeleniumTools.clickOnObject("xpath", "//echo-interrupt[@class='ng-isolate-scope']//a[text()='Delete']");
			CoreUtil.imSleepy(10000);

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			// Enter Value in search box
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@ng-model='query']", "suresh Goparaju");
			CoreUtil.imSleepy(5000);
			// Clicking on education
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='tree-container k-widget k-treeview']//span[contains(text(),'Education:')]");
			CoreUtil.imSleepy(5000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.clickOnObject("xpath", "//span[contains(text(),'University of Tampa')]");
			CoreUtil.imSleepy(10000);
			SeleniumTools.waitForFrameToBeAvailable("id", "FormFrame");
			// Click on Delete
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Delete']");
			SeleniumTools.clickOnObject("xpath", "//echo-interrupt[@class='ng-isolate-scope']//a[text()='Delete']");
			CoreUtil.imSleepy(5000);

		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}

	@Test(description = "BOLT-Others-Batch Add")
	public void Bolt_SG_10() {
		try {
			Login.StageLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Password"));
			// Verifying Global mode
			Login.CheckMode("Verity QA Team1", "global");
			// Navigating to Bolt->others
			SeleniumTools.hoverElement("xpath", "//li[@text='Bolt']");
			// Clicking on Editor
			SeleniumTools.clickOnObject("xpath", "//ul[@role='menu']//span[text()='Editor']");
			CoreUtil.imSleepy(20000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterFilterFrame']");
			// Enter Query name
			SeleniumTools.ClearAndSetText("xpath", "//input[@name='full_nameTxt']", "");
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			// Clicking on Dropdown
			SeleniumTools.scrollToElementAndClick("xpath",
					"//a/span[text()='Queries']//ancestor::ul/following-sibling::div[@class='rwzContentWrapper']//div[@id='filterStep']//span[@class='rddtInner']/span[text()='Add new...']");

			// Searching for record
			SeleniumTools.ClearAndSetText("xpath",
					"//div[@id='ReporterWizard']//a/span[text()='Queries']//ancestor::div[@id='ReporterWizard']/preceding-sibling::div//input[@value='Search...']",
					"Spreadsheet - Create then Upload");
			SeleniumTools.scrollToElementAndClick("xpath",
					"//span[contains(text(),'Spreadsheet - Create then Upload')]");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterFilterFrame']");
			// Clicking on Execute Query
			SeleniumTools.scrollToElementAndClick("xpath", "//input[@name='executeButton']");
			CoreUtil.imSleepy(6000);

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");

			// Clicking on Others
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()='Others']");
			// Clicking on Dropdown
			SeleniumTools.scrollToElementAndClick("xpath", "//div[@id='othersTree']/span");
			// Searching for record
			SeleniumTools.ClearAndSetText("xpath",
					"//a/span[text()='Spreadsheets']//ancestor::div[@id='ReporterWizard']/preceding-sibling::div//input[@class='rddtFilterInput radPreventDecorate rddtFilterEmptyMessage']",
					"Batch Add");
			CoreUtil.imSleepy(4000);
			// Clicking on Batch Add
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()='Batch Add']");

			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterOthersFrame']");
			// Selecting Workflow from dropdown
			SeleniumTools.selectByText("xpath",
					"//select[@class='type-dropdown form-control ng-pristine ng-untouched ng-valid']",
					"Offices/Locations");
			CoreUtil.imSleepy(10000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");
			// Click on Officelocation ellipse
			SeleniumTools.scrollToElementAndClick("xpath", "//echo-lookup[@model='formFields.l_location']//button");
			CoreUtil.imSleepy(10000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='frame-modal-clone-1']");
			// Enter search text
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='lookup-text-search']", "Test Location");
			SeleniumTools.selectByText("xpath", "//select[@class='form-control ng-pristine ng-untouched ng-valid']",
					"Default");
			CoreUtil.imSleepy(5000);
			// click on Test location
			SeleniumTools.scrollToElementAndClick("xpath", "//td[text()='Test Location']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterOthersFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frameContent']");
			// Clicking on Execute
			SeleniumTools.scrollToElementAndClick("xpath", "//button[contains(text(),'Execute')]");
			SeleniumTools.switchToDefaultFrame();
			// Clicking on OK
			SeleniumTools.scrollToElementAndClick("xpath", "//button[text()='OK']");
			// Click on OK
			SeleniumTools.scrollToElementAndClick("xpath", "//button[text()='OK']");
			CoreUtil.imSleepy(5000);

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			// Navigate to provider ####1
			SeleniumTools.hoverElement("xpath", "//li[@text='Search'] [@class='k-item k-state-default']");
			SeleniumTools.clickOnObject("xpath", "//span[text()='Search']/ancestor::li//span[text()='Provider']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			// Enter Value in search box
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@ng-model='query']", "suresh Goparaju");
			CoreUtil.imSleepy(5000);
			// Clicking on office/locations
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='tree-container k-widget k-treeview']//span[contains(text(),'Offices/Locations')]");
			CoreUtil.imSleepy(5000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.clickOnObject("xpath", "//span[text()='Test Location']");
			SeleniumTools.waitForFrameToBeAvailable("id", "FormFrame");
			// Delete
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Delete']");
			SeleniumTools.clickOnObject("xpath", "//echo-interrupt[@class='ng-isolate-scope']//a[text()='Delete']");
			CoreUtil.imSleepy(5000);

			// Navigate to provider ###2
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			// Navigate provider
			SeleniumTools.hoverElement("xpath", "//li[@text='Search'] [@class='k-item k-state-default']");
			SeleniumTools.clickOnObject("xpath", "//span[text()='Search']/ancestor::li//span[text()='Provider']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Search']");
			// Enter Value in search box
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@ng-model='query']", "suresh G");
			CoreUtil.imSleepy(5000);
			SeleniumTools.clickOnObject("xpath", "//span[text()='Suresh, G,']/preceding-sibling::span");
			// Clicking on Workflows
			SeleniumTools.clickOnObject("xpath",
					"//div[@class='tree-container k-widget k-treeview']//span[contains(text(),'Offices/Locations')]");
			CoreUtil.imSleepy(5000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='browserFrame']");
			SeleniumTools.clickOnObject("xpath", "//span[text()='Test Location']");
			SeleniumTools.waitForFrameToBeAvailable("id", "FormFrame");
			// Delete
			SeleniumTools.clickOnObject("xpath", "//span[text()=' Delete']");
			SeleniumTools.clickOnObject("xpath", "//echo-interrupt[@class='ng-isolate-scope']//a[text()='Delete']");
			CoreUtil.imSleepy(5000);

		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}

	@Test(description = "Bolt - Go to Editor - Others - CAQH Upload")
	public void Bolt_SG_11() {
		try {
			Login.StageLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Password"));
			// Verifying Global mode
			Login.CheckMode("Verity QA Team1", "global");
			// Navigating to Bolt->others
			SeleniumTools.hoverElement("xpath", "//li[@text='Bolt']");
			// Clicking on Editor
			SeleniumTools.clickOnObject("xpath", "//ul[@role='menu']//span[text()='Editor']");
			CoreUtil.imSleepy(20000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterFilterFrame']");
			// Enter Query name
			SeleniumTools.ClearAndSetText("xpath", "//input[@name='full_nameTxt']", "");
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			// Clicking on Dropdown
			SeleniumTools.scrollToElementAndClick("xpath",
					"//a/span[text()='Queries']//ancestor::ul/following-sibling::div[@class='rwzContentWrapper']//div[@id='filterStep']//span[@class='rddtInner']/span[text()='Add new...']");
			// Searching for record
			SeleniumTools.ClearAndSetText("xpath",
					"//div[@id='ReporterWizard']//a/span[text()='Queries']//ancestor::div[@id='ReporterWizard']/preceding-sibling::div//input[@value='Search...']",
					"Spreadsheet - Create then Upload");
			SeleniumTools.scrollToElementAndClick("xpath",
					"//span[contains(text(),'Spreadsheet - Create then Upload')]");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterFilterFrame']");
			// Clicking on Execute Query
			SeleniumTools.scrollToElementAndClick("xpath", "//input[@name='executeButton']");
			CoreUtil.imSleepy(6000);

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");

			// Clicking on Others
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()='Others']");
			// Clicking on Dropdown
			SeleniumTools.scrollToElementAndClick("xpath", "//div[@id='othersTree']/span");
			// Searching for record
			SeleniumTools.ClearAndSetText("xpath",
					"//a/span[text()='Spreadsheets']//ancestor::div[@id='ReporterWizard']/preceding-sibling::div//input[@class='rddtFilterInput radPreventDecorate rddtFilterEmptyMessage']",
					"CAQH Upload");
			CoreUtil.imSleepy(4000);
			// Clicking on CAQH Upload
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()='CAQH Upload']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterOthersFrame']");
			// Click on upload to CAQH
			SeleniumTools.scrollToElementAndClick("xpath", "//input[@value='Upload to CAQH']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.getText("xpath", "//p[text()='Upload is completed.']");
			SeleniumTools.verifyText("xpath", "//p[text()='Upload is completed.']", "Upload is completed.");

			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			// Click back on Quries tab
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()='Queries']");
			CoreUtil.imSleepy(10000);
			// Click on Forms/Letters tab
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()='Forms/Letters']");
			CoreUtil.imSleepy(10000);

			SeleniumTools.refreshPage();
			CoreUtil.imSleepy(10000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			// clicking on Notification
			SeleniumTools.hoverElement("xpath", "//echo-popover[@title='Notifications']/span/i");
			// Verifying report
			SeleniumTools.verifyText("xpath",
					"//div[@id='navEntry']//echo-slat[@slat='notification']//div[text()='CAQH Upload: Suresh, Goparaju']",
					"CAQH Upload: Suresh, Goparaju");
			SeleniumTools.verifyText("xpath",
					"//div[@id='navEntry']//echo-slat[@slat='notification']//div[text()='CAQH Upload: Suresh, G']",
					"CAQH Upload: Suresh, G");

		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}

	@Test(description = "Search - Provider - Right Click - Bolt - Print Forms/Letters")
	public void Bolt_SG_12() {
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
			// Enter Value in search box
			SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@ng-model='query']", "goparaju");
			CoreUtil.imSleepy(5000);
			// Clicking on Bolt icon
			SeleniumTools.clickOnObject("xpath", "//span[@title='Bolt']/i");
			CoreUtil.imSleepy(10000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='MainModalFrame']");
			// Clcik on Forms/Letters
			SeleniumTools.scrollToElementAndClick("xpath",
					"//div[@id='ProviderQuickViewTree2_Panel']//span[text()='Forms/Letters']");
			// Click on SeAutomation
			SeleniumTools.scrollToElementAndClick("xpath",
					"//div[@id='ProviderQuickViewTree2_Panel']//span[text()='Forms/Letters']/ancestor::li/ul//span[text()='SeAutomation']");
			// Click on Checkbox
			SeleniumTools.scrollToElementAndClick("xpath",
					"//span[text()='SeAutomation']/parent::div/parent::div/parent::li//div[contains(text(),'Print Forms/Letters')]");

			// Click on Generate
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()='Generate']");

			SeleniumTools.switchToDefaultFrame();
			// Close window
			SeleniumTools.clickOnObject("xpath", "//h4[text()='Bolt']/preceding-sibling::button");
			SeleniumTools.refreshPage();
			CoreUtil.imSleepy(20000);
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			// clicking on Notification
			SeleniumTools.hoverElement("xpath", "//echo-popover[@title='Notifications']/span/i");
			// Verifying report
			SeleniumTools.verifyText("xpath",
					"//div[@id='navEntry']//echo-slat[@slat='notification']//div[text()='Print Forms/Letters']",
					"Print Forms/Letters");
			SeleniumTools.getText("xpath",
					"//div[@id='navEntry']//echo-slat[@slat='notification']//div[text()='Print Forms/Letters']/parent::div/ul/li[last()]");
			SeleniumTools.scrollToElementAndClick("xpath",
					"//div[@id='navEntry']//echo-slat[@slat='notification']//div[text()='Print Forms/Letters']");
			CoreUtil.imSleepy(10000);
			// Validating text present in the file
			RTFReader.RTF("note.doc", "Suresh");

		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}

	@Test(description = "Bolt - Queries - Automation - Affiliation - Requestor Information")
	public void Bolt_SG_13() {
		try {
			// Login to the Stage Application
			Login.StageLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Password"));
			// Verifying Global mode
			Login.CheckMode("Verity QA Team1", "global");
			// Clicking on Bolt
			SeleniumTools.hoverElement("xpath", "//li[@text='Bolt']");
			// Clicking on Editor
			SeleniumTools.clickOnObject("xpath", "//ul[@role='menu']//span[text()='Editor']");
			CoreUtil.imSleepy(20000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterFilterFrame']");
			// Enter Query name
			SeleniumTools.ClearAndSetText("xpath", "//input[@name='full_nameTxt']",
					"Bolt_Affiliation_Test" + SeleniumTools.getRandomNumber(3));
			// Clicking on Folder
			SeleniumTools.scrollToElementAndClick("xpath", "//button[@class='btn btn-default silver folder-btn']");
			CoreUtil.imSleepy(10000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='MainModalFrame']");
			// Clicking on Automation
			SeleniumTools.clickOnObject("xpath",
					"//div[@id='PackagesTreeView']/ul//span[text()='Automation']/ancestor::div/preceding-sibling::span");
			// Clicking on Monitor
			SeleniumTools.clickOnObject("xpath",
					"//div[@id='PackagesTreeView']//span[text()='Automation']/ancestor::li//span[text()='Monitor']");
			// Clicking on Select Folder
			SeleniumTools.clickOnObject("xpath", "//button[text()='Select Folder']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterFilterFrame']");

			// Clicking on Master Table
			SeleniumTools.selectByText("xpath", "//select[@name='masterdbfTxt']", "Provider/Physician");
			// Clicking on Save
			SeleniumTools.scrollToElementAndClick("xpath", "//input[@name='saveButton']");
			CoreUtil.imSleepy(10000);
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterFilterFrame']");
			// Clicking on Verify/Monitor
			SeleniumTools.clickOnObject("xpath", "//span[text()='Verify/Monitor']");

			// Clicking on Mode
			SeleniumTools.selectByText("xpath", "//select[@name='PSVDD']", "Affiliation");
			CoreUtil.imSleepy(2000);
			// Clicking on Requestor Information
			SeleniumTools.scrollToElementAndClick("xpath", "//span[contains(text(),'Requestor Information')]");
			CoreUtil.imSleepy(10000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='popupFrame']");
			SeleniumTools.javascriptClick("//input[@name='FirstTextBox']");

			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			// Close window
			SeleniumTools.javascriptClick("//h4[text()='Fill In Requestor Information']/a");
			SeleniumTools.getAlertCancel();
			CoreUtil.imSleepy(5000);
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterFilterFrame']");
			// Clicking on Delete Query
			SeleniumTools.scrollToElementAndClick("xpath", "//input[@name='deleteButton']");
			SeleniumTools.getAlertText();
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

	@Test(description = "Bolt - Letters/Forms - View Variables - Orgs & Conversion - Code Maintenance")
	public void Bolt_SG_14() {
		try {
			// Login to the Stage Application
			Login.StageLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Password"));
			// Verifying Global mode
			Login.CheckMode("Verity QA Team1", "global");
			// Clicking on Bolt
			SeleniumTools.hoverElement("xpath", "//li[@text='Bolt']");
			// Clicking on Editor
			SeleniumTools.clickOnObject("xpath", "//ul[@role='menu']//span[text()='Editor']");
			CoreUtil.imSleepy(20000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterFilterFrame']");
			// Enter Query name
			SeleniumTools.ClearAndSetText("xpath", "//input[@name='full_nameTxt']", "");
			SeleniumTools.switchToFrame("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");
			// Clicking on Dropdown
			SeleniumTools.scrollToElementAndClick("xpath",
					"//a/span[text()='Queries']//ancestor::ul/following-sibling::div[@class='rwzContentWrapper']//div[@id='filterStep']//span[@class='rddtInner']/span[text()='Add new...']");
			// Searching for record
			SeleniumTools.ClearAndSetText("xpath",
					"//div[@id='ReporterWizard']//a/span[text()='Queries']//ancestor::div[@id='ReporterWizard']/preceding-sibling::div//input[@value='Search...']",
					"Spreadsheet - Create then Upload");
			SeleniumTools.scrollToElementAndClick("xpath",
					"//span[contains(text(),'Spreadsheet - Create then Upload')]");
			CoreUtil.imSleepy(6000);

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='EditorFrame']");

			// Clicking on forms/letter
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()='Forms/Letters']");
			// Clicking on Dropdown
			SeleniumTools.scrollToElementAndClick("xpath",
					"//div[@class='rwzContentWrapper']//div[@id='letterTree']//span[@class='rddtInner']/span[text()='Add new...']");
			// Searching for record
			SeleniumTools.ClearAndSetText("xpath",
					"//div[@id='ReporterWizard']/preceding-sibling::div//input[@class='rddtFilterInput radPreventDecorate rddtFilterEmptyMessage']",
					"Orgs & Conversion");
			SeleniumTools.scrollToElementAndClick("xpath",
					"//div[@id='letterTree_EmbeddedTree']//li//span[text()='SeAutomation']/ancestor::li//ul//span[contains(text(),'Orgs & Conversion')]");
			CoreUtil.imSleepy(6000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterLetterFrame']");
			// Click on View variable
			SeleniumTools.scrollToElementAndClick("xpath", "//img[@title='View Variables']");
			CoreUtil.imSleepy(10000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='frame-modal-clone-1']");
			// Click on Orgs & Conversion
			SeleniumTools.scrollToElementAndClick("xpath", "//span[contains(text(),'Orgs & Conversion')]");
			// Click on Code maintenance
			SeleniumTools.scrollToElementAndClick("xpath", "//input[@value='Code Maintenance']");
			SeleniumTools.switchToChildWindow();
			CoreUtil.imSleepy(10000);
			// SeleniumTools.switchToDefaultFrame();
			// SeleniumTools.waitForFrameToBeAvailable("xpath",
			// "//frameset[@frameborder='Yes']");
			// SeleniumTools.waitForFrameToBeAvailable("xpath", "//frame[@name='cTop']");

			// SeleniumTools.clickOnObject("xpath", "//input[@id='full_nameSearch']");
			SeleniumTools.closeTab();
			SeleniumTools.switchToDefaultWindow();

		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
			Assert.fail();
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}

	@Test(description = "Bolt Reports - Manage Schedules - User/Group - Recipients - Add User")
	public void Bolt_SG_15() {
		try {
			// Login to the Stage Application
			Login.StageLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Password"));
			// Verifying Global mode
			Login.CheckMode("Verity QA Team1", "global");
			// Clicking on Bolt
			SeleniumTools.hoverElement("xpath", "//li[@text='Bolt']");
			// Clicking on Editor
			SeleniumTools.clickOnObject("xpath", "//span[text()='Reports']");
			CoreUtil.imSleepy(10000);
			// Clicking on print bolt report icon
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterPickersFrame']");
			// Select reporte
			SeleniumTools.scrollToElementAndClick("xpath", "//div[@id='rapidreportTree']/span");
			SeleniumTools.ClearAndSetText("xpath",
					"//div[@id='PickerController']/form/div[@class='rddtSlide']//input[@value='Search...']",
					"Export Facility Data");
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()='Export Facility Data']");
			CoreUtil.imSleepy(10000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			// Click on More
			SeleniumTools.scrollToElementAndClick("xpath", "//button[text()='More  ']");
			// Click on Manage schedules
			SeleniumTools.scrollToElementAndClick("xpath", "//a[text()=' Manage Schedules']");
			CoreUtil.imSleepy(10000);
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='RapidReportsModalFrame']");
			// select user/group
			SeleniumTools.doubleClickOnObject("xpath", "//div[@kendo-grid='recipientsGrid']/table/tbody/tr[1]/td[2]");
			SeleniumTools.scrollToElementAndClick("xpath", "//li[text()='User/Group']");
			// Click on Recipients
			SeleniumTools.scrollToElementAndClick("xpath",
					"//div[@kendo-grid='recipientsGrid']/table/tbody/tr[1]/td[3]");

			Actual = SeleniumTools.getAttribute("xpath",
					"//div[@kendo-grid='recipientsGrid']/table/tbody/tr[1]/td[3]//input", "placeholder");
			assertEquals(Actual, "User/Group");
			// Click on ellipses
			SeleniumTools.scrollToElementAndClick("xpath",
					"//div[@kendo-grid='recipientsGrid']/table/tbody/tr[1]/td[3]//span");
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "MainModalFrame");
			// Enter Full name
			SeleniumTools.ClearAndSetText("xpath", "//input[@id='fullName']", "Suresh");
			// Click on Search
			SeleniumTools.scrollToElementAndClick("xpath", "//button[text()='Search']");
			// Select user
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()='Suresh  Gopparaju']");

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='RapidReportsModalFrame']");
			// Enter Description to save
			SeleniumTools.ClearAndSetText("xpath", "//input[@placeholder='Description']", "Test");
			// click on SAVE
			SeleniumTools.scrollToElementAndClick("xpath", "//div[@class='row']//button[text()=' Save']");
			CoreUtil.imSleepy(10000);
			SeleniumTools.scrollToElementAndClick("xpath", "//td[text()='Suresh  Gopparaju']");
			SeleniumTools.scrollToElementAndClick("xpath",
					"//div[@kendo-grid='recipientsGrid']/table/tbody/tr[1]/td[3]//button[@title='Delete row']");
			// click on SAVE
			SeleniumTools.scrollToElementAndClick("xpath", "//div[@class='row']//button[text()=' Save']");
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

	@Test(description = "Bolt Reports – Add New - select Query - select Letter – select Spreadsheet - Save then Print All")
	public void Bolt_SG_16() {
		try {
			// Login to the Stage Application
			Login.StageLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Password"));
			// Verifying Global mode
			Login.CheckMode("Verity QA Team1", "global");
			// Clicking on Bolt
			SeleniumTools.hoverElement("xpath", "//li[@text='Bolt']");
			// Clicking on Editor
			SeleniumTools.clickOnObject("xpath", "//span[text()='Reports']");
			CoreUtil.imSleepy(10000);
			// Clicking on print bolt report icon
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterPickersFrame']");
			// Select Bolt Report
			SeleniumTools.scrollToElementAndClick("xpath", "//div[@id='rapidreportTree']/span");
			SeleniumTools.scrollToElementAndClick("xpath",
					"//div[@id='rapidreportTree_EmbeddedTree']//span[text()='Add new...']");
			CoreUtil.imSleepy(5000);

			// Select Quries
			SeleniumTools.scrollToElementAndClick("xpath", "//div[@id='filterTree']/span");
			SeleniumTools.ClearAndSetText("xpath",
					"//div[@id='filterTree_EmbeddedTree']/parent::div/parent::div//input[@value='Search...']",
					"Spreadsheet - Create then Upload");
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()='Spreadsheet - Create then Upload']");
			SeleniumTools.scrollToElementAndClick("xpath", "//div[@id='filterTree']/span");
			CoreUtil.imSleepy(2000);

			// Select Forms/letters
			SeleniumTools.scrollToElementAndClick("xpath", "//div[@id='letterTree']/span");
			SeleniumTools.ClearAndSetText("xpath",
					"//div[@id='letterTree_EmbeddedTree']/parent::div/parent::div//input[@value='Search...']",
					"Print Forms/Letters");
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()='Print Forms/Letters']");
			SeleniumTools.scrollToElementAndClick("xpath", "//div[@id='letterTree']/span");
			CoreUtil.imSleepy(2000);

			// Select Spreadsheet
			SeleniumTools.scrollToElementAndClick("xpath", "//div[@id='data_wizardTree']/span");
			SeleniumTools.ClearAndSetText("xpath",
					"//div[@id='data_wizardTree_EmbeddedTree']/parent::div/parent::div//input[@value='Search...']",
					"Create - Spreadsheet");
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()='Create - Spreadsheet']");
			SeleniumTools.scrollToElementAndClick("xpath", "//div[@id='data_wizardTree']/span");
			CoreUtil.imSleepy(2000);

			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			// Click on SAVE
			SeleniumTools.scrollToElementAndClick("xpath", "//div[@class='col-xs-6']//button[text()=' Save']");

			CoreUtil.imSleepy(5000);
			// Enter Title
			SeleniumTools.ClearAndSetText("xpath", "//input[@ng-model='modal.reportTitle']",
					SeleniumTools.getRandomString());
			// Select Folder
			SeleniumTools.scrollToElementAndClick("xpath", "//button[@class='btn btn-default silver']");
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "MainModalFrame");
			SeleniumTools.scrollToElementAndClick("xpath",
					"//div[@id='PackagesTreeView']//span[text()='SeAutomation']");
			SeleniumTools.scrollToElementAndClick("xpath", "//button[text()='Select Folder']");
			CoreUtil.imSleepy(5000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			// Save
			SeleniumTools.scrollToElementAndClick("xpath", "//a[text()='Save']");
			CoreUtil.imSleepy(10000);

			// Print All
			SeleniumTools.scrollToElementAndClick("xpath", "//button[text()=' Print All']");
			CoreUtil.imSleepy(2000);

			// Reports added to notifications
			SeleniumTools.verifyText("xpath", "//div[text()='Reports added to notifications.']",
					"Reports added to notifications.");
			// Ok
			SeleniumTools.scrollToElementAndClick("xpath", "//a[text()='Ok']");
			CoreUtil.imSleepy(10000);

			// Delete Record
			// Click on More
			SeleniumTools.scrollToElementAndClick("xpath", "//button[text()='More  ']");
			// Click on Manage schedules
			SeleniumTools.scrollToElementAndClick("xpath", "//a[text()=' Delete Report']");
			CoreUtil.imSleepy(5000);
			// Confirm Delete
			SeleniumTools.scrollToElementAndClick("xpath",
					"//echo-interrupt[@modal='interruptModel']//a[text()='Delete']");
			CoreUtil.imSleepy(5000);

			SeleniumTools.refreshPage();
			CoreUtil.imSleepy(20000);
			SeleniumTools.switchToDefaultFrame();
			SeleniumTools.waitForFrameToBeAvailable("id", "mainFrame");
			// SeleniumTools.getText("xpath", "//span[text()='Report complete']");
			// clicking on Notification
			SeleniumTools.hoverElement("xpath", "//echo-popover[@title='Notifications']/span/i");
			// Verifying report
			SeleniumTools.verifyText("xpath",
					"//div[@id='navEntry']//echo-slat[@slat='notification']//div[text()='Create - Spreadsheet']",
					"Create - Spreadsheet");
			SeleniumTools.getText("xpath",
					"//div[@id='navEntry']//echo-slat[@slat='notification']//div[text()='Create - Spreadsheet']/parent::div/ul/li[last()]");
			SeleniumTools.verifyText("xpath",
					"//div[@id='navEntry']//echo-slat[@slat='notification']//div[text()='Print Forms/Letters']",
					"Print Forms/Letters");
			SeleniumTools.getText("xpath",
					"//div[@id='navEntry']//echo-slat[@slat='notification']//div[text()='Print Forms/Letters']/parent::div/ul/li[last()]");

		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
			Assert.fail();
		} finally {
			loggerUtil.exitLogger(new Object() {
			}.getClass().getEnclosingMethod().getName());
		}
	}

	@Test(description = "Bolt Report - select existing - with Query & Letter - Create Copy - Delete Report")
	public void Bolt_SG_17() {
		try {
			// Login to the Stage Application
			Login.StageLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Password"));
			// Verifying Global mode
			Login.CheckMode("Verity QA Team1", "global");
			// Clicking on Bolt
			SeleniumTools.hoverElement("xpath", "//li[@text='Bolt']");
			// Clicking on Reports
			SeleniumTools.clickOnObject("xpath", "//span[text()='Reports']");
			CoreUtil.imSleepy(15000);
			// Clicking on print bolt report icon
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterPickersFrame']");
			// Select Bolt Report
			SeleniumTools.scrollToElementAndClick("xpath", "//div[@id='rapidreportTree']/span");
			SeleniumTools.ClearAndSetText("xpath",
					"//div[@id='rapidreportTree_EmbeddedTree']/parent::div/parent::div//input[@value='Search...']",
					"Export Facility Data");
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()='Export Facility Data']");
			SeleniumTools.scrollToElementAndClick("xpath", "//div[@id='rapidreportTree']/span");
			CoreUtil.imSleepy(8000);

			// Select Forms/letters
			SeleniumTools.scrollToElementAndClick("xpath", "//div[@id='letterTree']/span");
			SeleniumTools.ClearAndSetText("xpath",
					"//div[@id='letterTree_EmbeddedTree']/parent::div/parent::div//input[@value='Search...']",
					"Print Forms/Letters");
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()='Print Forms/Letters']");
			SeleniumTools.scrollToElementAndClick("xpath", "//div[@id='letterTree']/span");
			CoreUtil.imSleepy(2000);

			SeleniumTools.switchToFrame("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			// Click on More
			SeleniumTools.scrollToElementAndClick("xpath", "//button[text()='More  ']");
			// Click on Create copy
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()=' Create copy']");
			CoreUtil.imSleepy(5000);

			SeleniumTools.switchToFrame("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='RapidReportsModalFrame']");
			// New Title
			Listing_title = SeleniumTools.ClearAndSetText("xpath", "//input[@name='NameTxt']",
					"Export Facility Data - Copy");
			// Copy
			SeleniumTools.scrollToElementAndClick("xpath", "//input[@value='Copy']");
			CoreUtil.imSleepy(20000);
			SeleniumTools.switchToFrame("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			// Cancel
			SeleniumTools.scrollToElementAndClick("xpath",
					"//div[text()='Open Bolt Reports? ']//ancestor::echo-interrupt//a[text()='Cancel']");
			CoreUtil.imSleepy(10000);

			SeleniumTools.switchToFrame("xpath", "//iframe[@id='mainFrame']");
			// Clicking on Bolt
			SeleniumTools.hoverElement("xpath", "//li[@text='Bolt']");
			// Clicking on Reports
			SeleniumTools.clickOnObject("xpath", "//span[text()='Reports']");
			CoreUtil.imSleepy(15000);
			// Clicking on print bolt report icon
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterPickersFrame']");
			// Select Bolt Report
			SeleniumTools.scrollToElementAndClick("xpath", "//div[@id='rapidreportTree']/span");
			SeleniumTools.ClearAndSetText("xpath",
					"//div[@id='rapidreportTree_EmbeddedTree']/parent::div/parent::div//input[@value='Search...']",
					Listing_title);
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()='" + Listing_title + "']");
			SeleniumTools.scrollToElementAndClick("xpath", "//div[@id='rapidreportTree']/span");
			CoreUtil.imSleepy(10000);

			// ******** Logout And ReLogin ******************//
			Login.LogoutandReLogin(
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Password"));
			Login.CheckMode("Verity QA Team1", "global");
			// Clicking on Bolt
			SeleniumTools.hoverElement("xpath", "//li[@text='Bolt']");
			// Clicking on Reports
			SeleniumTools.clickOnObject("xpath", "//span[text()='Reports']");
			CoreUtil.imSleepy(15000);
			// Clicking on print bolt report icon
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterPickersFrame']");
			// Select Bolt Report
			SeleniumTools.scrollToElementAndClick("xpath", "//div[@id='rapidreportTree']/span");
			SeleniumTools.ClearAndSetText("xpath",
					"//div[@id='rapidreportTree_EmbeddedTree']/parent::div/parent::div//input[@value='Search...']",
					Listing_title);
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()='" + Listing_title + "']");
			SeleniumTools.scrollToElementAndClick("xpath", "//div[@id='rapidreportTree']/span");
			CoreUtil.imSleepy(10000);

			// ******** Delete Record **********
			SeleniumTools.switchToFrame("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			// Click on More
			SeleniumTools.scrollToElementAndClick("xpath", "//button[text()='More  ']");
			// Click on Delete report
			SeleniumTools.scrollToElementAndClick("xpath", "//a[text()=' Delete Report']");
			CoreUtil.imSleepy(5000);
			// Confirm Delete
			SeleniumTools.scrollToElementAndClick("xpath",
					"//echo-interrupt[@modal='interruptModel']//a[text()='Delete']");
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

	@Test(description = "Bolt Report - select existing - with Query & Letter - Add A Note", enabled = false)
	public void Bolt_SG_18() {
		try {
			// Login to the Stage Application
			Login.StageLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Password"));
			// Verifying Global mode
			Login.CheckMode("Verity QA Team1", "global");
			// Clicking on Bolt
			SeleniumTools.hoverElement("xpath", "//li[@text='Bolt']");
			// Clicking on Reports
			SeleniumTools.clickOnObject("xpath", "//span[text()='Reports']");
			CoreUtil.imSleepy(10000);
			// Clicking on print bolt report icon
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterPickersFrame']");
			// Select Bolt Report
			SeleniumTools.scrollToElementAndClick("xpath", "//div[@id='rapidreportTree']/span");
			SeleniumTools.ClearAndSetText("xpath",
					"//div[@id='rapidreportTree_EmbeddedTree']/parent::div/parent::div//input[@value='Search...']",
					"Export Facility Data");
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()='Export Facility Data']");
			SeleniumTools.scrollToElementAndClick("xpath", "//div[@id='rapidreportTree']/span");
			CoreUtil.imSleepy(8000);

			// Select Forms/letters
			SeleniumTools.scrollToElementAndClick("xpath", "//div[@id='letterTree']/span");
			SeleniumTools.ClearAndSetText("xpath",
					"//div[@id='letterTree_EmbeddedTree']/parent::div/parent::div//input[@value='Search...']",
					"Print Forms/Letters");
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()='Print Forms/Letters']");
			SeleniumTools.scrollToElementAndClick("xpath", "//div[@id='letterTree']/span");
			CoreUtil.imSleepy(2000);

			SeleniumTools.switchToFrame("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			// Click on More
			SeleniumTools.scrollToElementAndClick("xpath", "//button[text()='More  ']");
			// Click on Add a note
			SeleniumTools.scrollToElementAndClick("xpath", "//a[text()=' Add a note']");
			CoreUtil.imSleepy(2000);

			SeleniumTools.switchToFrame("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='RapidReportsModalFrame']");
			// Add a note
			Actual = SeleniumTools.ClearAndSetText("xpath", "//textarea[@name='NotesTextbox']",
					SeleniumTools.getRandomString() + " Add a note");
			// Save Note
			SeleniumTools.scrollToElementAndClick("xpath", "//input[@value='Save Note']");
			CoreUtil.imSleepy(2000);
			SeleniumTools.switchToFrame("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			// Close Bolt Report
			SeleniumTools.scrollToElementAndClick("xpath", "//button[@title='Close Bolt Report']");
			CoreUtil.imSleepy(5000);
			// Close
			SeleniumTools.scrollToElementAndClick("xpath",
					"//div[text()='Close Bolt Report? ']//ancestor::echo-interrupt//a[text()='Close']");
			CoreUtil.imSleepy(10000);

			SeleniumTools.switchToFrame("xpath", "//iframe[@id='mainFrame']");
			// Clicking on Bolt
			SeleniumTools.hoverElement("xpath", "//li[@text='Bolt']");
			// Clicking on Reports
			SeleniumTools.clickOnObject("xpath", "//span[text()='Reports']");
			CoreUtil.imSleepy(20000);
			// Clicking on print bolt report icon
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='ReporterPickersFrame']");
			// Select Bolt Report
			SeleniumTools.scrollToElementAndClick("xpath", "//div[@id='rapidreportTree']/span");
			SeleniumTools.ClearAndSetText("xpath",
					"//div[@id='rapidreportTree_EmbeddedTree']/parent::div/parent::div//input[@value='Search...']",
					"Export Facility Data");
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()='Export Facility Data']");
			SeleniumTools.scrollToElementAndClick("xpath", "//div[@id='rapidreportTree']/span");
			CoreUtil.imSleepy(8000);

			// Select Forms/letters
			SeleniumTools.scrollToElementAndClick("xpath", "//div[@id='letterTree']/span");
			SeleniumTools.ClearAndSetText("xpath",
					"//div[@id='letterTree_EmbeddedTree']/parent::div/parent::div//input[@value='Search...']",
					"Print Forms/Letters");
			SeleniumTools.scrollToElementAndClick("xpath", "//span[text()='Print Forms/Letters']");
			SeleniumTools.scrollToElementAndClick("xpath", "//div[@id='letterTree']/span");
			CoreUtil.imSleepy(2000);

			SeleniumTools.switchToFrame("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			// Click on More
			SeleniumTools.scrollToElementAndClick("xpath", "//button[text()='More  ']");
			// Click on Add a note
			SeleniumTools.scrollToElementAndClick("xpath", "//a[text()=' Add a note']");
			CoreUtil.imSleepy(2000);

			SeleniumTools.switchToFrame("xpath", "//iframe[@id='mainFrame']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@name='Bolt']");
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='RapidReportsModalFrame']");
			// Add a note
			Expected = SeleniumTools.getText("xpath", "//textarea[@name='NotesTextbox']");

			// Validating Add a note
			assertEquals(Actual, Expected);
			SeleniumTools.ClearAndSetText("xpath", "//textarea[@name='NotesTextbox']", "");
			// Save Note
			SeleniumTools.scrollToElementAndClick("xpath", "//input[@value='Save Note']");
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

	@Test(description = "Jenkins")
	public void Jenkins() {
		try {
			// Login to the Stage Application
			Login.StageLogin(SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Username"),
					SeleniumTools.readConfigFile(Constants.properties_stage).getProperty("Stage_G_Password"));
			// Verifying Global mode
			// Login.CheckMode("Verity QA Team1", "global");

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