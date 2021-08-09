package utility;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.lf5.LogLevel;;

public class LoggerUtil {

	Logger logger;
	@SuppressWarnings("rawtypes")
	Class clazz;

	@SuppressWarnings("rawtypes")
	public LoggerUtil(Class clazz) {
		this.clazz = clazz;
		logger = LogManager.getLogger(this.clazz.getName());
	}
	
	//Entry level loggers
	public void entryLogger(String methodName) {
		LogMessage(LogLevel.INFO, methodName, "Entry");

	}
	//exit level loggers
	public void exitLogger(String methodName) {

		LogMessage(LogLevel.INFO, methodName, "Exit");
	}
	
	
	public void mesgLogger(String methodName) {

		LogMessage(LogLevel.INFO, "END TEST-", methodName);
	}

	public void LogSevereMessage(LogLevel severity, String methodName, String logMessage, Exception ex)
			throws Exception {

		SeleniumTools.getScreenshot(clazz.getName() + "-" + methodName + "-");
		logger.log(Level.toLevel(severity.toString()),
				"       -       " + clazz.getName() + "     -     " + methodName + "      -      " + logMessage, ex);
		throw ex;
	}

	public void LogMessage(LogLevel severity, String methodName, String logMessage) {
		logger.log(Level.toLevel(severity.toString()),
				" - " + clazz.getName() + "      -      " + methodName + "-" + logMessage);
	}

	

}
