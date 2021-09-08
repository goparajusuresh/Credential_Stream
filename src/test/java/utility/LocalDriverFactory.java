package utility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.Parameters;

public class LocalDriverFactory {
	
	public static WebDriver createInstance(String browserName) {
	       WebDriver driver = null;
	       if (browserName.toLowerCase().contains("firefox")) {
	    	   System.setProperty("webdriver.chrome.driver", "src/main/resources/Drivers/geckodriver.exe");
	           driver = new FirefoxDriver();
	           return driver;
	       }
	       if (browserName.toLowerCase().contains("internet")) {
	           driver = new InternetExplorerDriver();
	           return driver;
	       }
	      /* if (browserName.toLowerCase().contains("htmldriver")) {  //headless browser
	           driver = new HtmlUnitDriver();
	           return driver;
	       }*/
	       if (browserName.toLowerCase().contains("chrome")) {
				System.setProperty("webdriver.chrome.driver", "src/main/resources/Drivers/chromedriver.exe");
	           driver = new ChromeDriver();
	           return driver;
	       }
	       return driver;
	}

}
