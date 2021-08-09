package utility;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;

public class CoreUtil {
	
	static Logger logger = Logger.getLogger("CoreUtil");

	public static String str;

	

	public static void screenshot(WebDriver driver, File file) {
		if (!file.getParentFile().isDirectory()) {
			file.getParentFile().mkdirs();
		}

		try {

			TakesScreenshot screenshotDriver = (TakesScreenshot) (driver instanceof TakesScreenshot ? driver
					: new Augmenter().augment(driver));
			File screenshot = screenshotDriver.getScreenshotAs(OutputType.FILE);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void imSleepy(Integer sleeptime){
		 try {
			 	int converttoseconds = (sleeptime/1000);
			 	logger.info("Thread Sleep for " + converttoseconds + " seconds");
			 	Thread.sleep(sleeptime);                 //1000 milliseconds is one second.
	        } catch(InterruptedException ex) {
	            Thread.currentThread().interrupt();
	        }
	}

	public static String timeStamp() {
		String timeStamp = new SimpleDateFormat("--yyyy-MM-dd-HH-mm-ss").format(new Date());
		return timeStamp;
	}

	public static String timeStamp_forFilename() {
		String timeStamp = new SimpleDateFormat("--yyyy-MM-dd-HH-mm-ss").format(new Date());
		return timeStamp;
	}

}
