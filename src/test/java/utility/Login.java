package utility;


public class Login extends TestInit {
	public static String Mode, FacName;
	static Boolean Status;
	static LoggerUtil loggerUtil = new LoggerUtil(Login.class);

	public static void MegaseedLogin(String CAHQ_Username, String CAHQ_Password) {
		try {
			SeleniumTools.openBrowser("chrome");
			SeleniumTools.navigateURL(Constants.VS_Megaseed);
			CoreUtil.imSleepy(2000);
			SeleniumTools.ClearAndSetText("xpath", "//input[@name='loginNameTxt']", CAHQ_Username);
			SeleniumTools.ClearAndSetText("xpath", "//input[@name='PasswordTxt']", CAHQ_Password);
			SeleniumTools.clickOnObject("xpath", "//input[@name='LoginButton']");
			CoreUtil.imSleepy(40000);
			SeleniumTools.clickOnObject("xpath", "//button[text()='Do not show again']");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void DeployLogin(String CAHQ_Username, String CAHQ_Password) {
		try {
			SeleniumTools.openBrowser("chrome");
			SeleniumTools.navigateURL(Constants.VS_Deploy);
			CoreUtil.imSleepy(2000);
			SeleniumTools.ClearAndSetText("xpath", "//input[@name='loginNameTxt']", CAHQ_Username);
			SeleniumTools.ClearAndSetText("xpath", "//input[@name='PasswordTxt']", CAHQ_Password);
			SeleniumTools.clickOnObject("xpath", "//input[@name='LoginButton']");
			CoreUtil.imSleepy(40000);
			SeleniumTools.clickOnObject("xpath", "//button[text()='Do not show again']");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void StageLogin(String Username, String Password) {
		try {
			SeleniumTools.openBrowser("chrome");
			SeleniumTools.navigateURL(Constants.VS_Stage);
			CoreUtil.imSleepy(2000);
			SeleniumTools.ClearAndSetText("xpath", "//input[@name='loginNameTxt']", Username);
			SeleniumTools.ClearAndSetText("xpath", "//input[@name='PasswordTxt']", Password);
			SeleniumTools.clickOnObject("xpath", "//input[@name='LoginButton']");
			//SeleniumTools.clickOnObject("xpath", "//button[text()='Do not show again']");
		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
		}
	}
	
	public static void HubLogin(String CAHQ_Username, String CAHQ_Password) {
		try {
			SeleniumTools.openBrowser("chrome");
			SeleniumTools.navigateURL(Constants.VS_Hub);
			CoreUtil.imSleepy(2000);
			SeleniumTools.ClearAndSetText("xpath", "//input[@placeholder='Email or Username']", CAHQ_Username);
			SeleniumTools.ClearAndSetText("xpath", "//input[@placeholder='Password']", CAHQ_Password);
			SeleniumTools.clickOnObject("xpath", "//input[@value='Log In']");
			CoreUtil.imSleepy(30000);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void LogoutandReLogin(String Username, String Password) {
		try {

			SeleniumTools.switchToFrame("id", "mainFrame");
			// Logout
			SeleniumTools.clickOnObject("xpath", "//img[@id='user-picture']");
			SeleniumTools.clickOnObject("xpath", "//div[@class='nav-row']//span[text()='Log out']");
			SeleniumTools.ClearAndSetText("xpath", "//input[@name='loginNameTxt']", Username);
			SeleniumTools.ClearAndSetText("xpath", "//input[@name='PasswordTxt']", Password);
			SeleniumTools.clickOnObject("xpath", "//input[@name='LoginButton']");

		} catch (Exception e) {
			e.printStackTrace();
			pNode.fail(e);
		}
	}

	public static void CheckMode(String ExpectedMode, String mode) {
		try {
			// Switch to frame
			SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='mainFrame']");
			// Clicking on logo/image
			SeleniumTools.clickOnObject("xpath", "//img[@ng-click='nav.knowledgeNow ? return : nav.showHome()']");
			// Get the text/Mode from the link
			Mode = SeleniumTools.getText("xpath", "//div[@ng-mouseleave='nav.onFacilityLinkLeave()']/div[@id='facility']");

			if (Mode.equals(ExpectedMode)) {
				Status = true;
				String str = String.valueOf(Status);
				System.out.println(str);
				loggerUtil.entryLogger("Logged in as Expected mode");

			} else {
				loggerUtil.entryLogger("Changing to Expected mode");
				// Clicking on Mousehover element
				SeleniumTools.clickOnObject("xpath", "//div[@ng-mouseleave='nav.onFacilityLinkLeave()']/div[@id='facility']");
				CoreUtil.imSleepy(20000);
				SeleniumTools.switchToDefaultFrame();
				SeleniumTools.waitForFrameToBeAvailable("xpath", "//iframe[@id='frame-modal-clone-1']");

				if (mode.equals("global")) {

					SeleniumTools.clickOnObject("xpath", "//button[text()='Global Mode']");
					CoreUtil.imSleepy(20000);
					SeleniumTools.switchToDefaultFrame();
					SeleniumTools.switchToFrame("xpath", "//iframe[@id='mainFrame']");
					// Clicking on logo/image
					SeleniumTools.clickOnObject("xpath",
							"//img[@ng-click='nav.knowledgeNow ? return : nav.showHome()']");

				} else {
					// Search for desired mode
					SeleniumTools.ClearAndSetTextEnter("xpath", "//input[@placeholder='Search by text']", ExpectedMode);
					CoreUtil.imSleepy(5000);
					// Clciking on Desired Mode
					FacName = SeleniumTools.getText("xpath", "//table[@class='k-selectable']/tbody/tr[1]/td[2]");
					SeleniumTools.clickOnObject("xpath", "//table[@class='k-selectable']/tbody/tr[1]/td[2]");
					CoreUtil.imSleepy(20000);
					SeleniumTools.switchToDefaultFrame();
					SeleniumTools.switchToFrame("xpath", "//iframe[@id='mainFrame']");
					// Clicking on logo/image
					SeleniumTools.clickOnObject("xpath",
							"//img[@ng-click='nav.knowledgeNow ? return : nav.showHome()']");
					SeleniumTools.getText("xpath", "//div[@ng-mouseleave='nav.onFacilityLinkLeave()']/div[@id='facility']");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
