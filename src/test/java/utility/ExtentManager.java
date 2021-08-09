package utility;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

	private static ExtentReports extent;
	public static String fileName;

	public static ExtentReports getInstance(String className) {
		if (extent == null) {
			extent = createInstance(className);
		}
		return extent;
	}

	public static ExtentReports createInstance(String className) {
		String timeStamp = new SimpleDateFormat("dd-MM-yyyy_HH-mm").format(new Date());
		fileName = "Reports/" + className + "_" + timeStamp + ".html";

		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter.config().setDocumentTitle(fileName);
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setReportName(fileName);

		extent = new ExtentReports();
		extent.setAnalysisStrategy(AnalysisStrategy.TEST);
		extent.attachReporter(htmlReporter);
		return extent;
	}

	public static void quitExtent() {
		extent.flush();
		extent = null;
	}

}
