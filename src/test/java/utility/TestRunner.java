// Run this class to execute failed test cases instead of going to "test-output" folder ->"testng-failed.xml" file and execute, using TestNG class
package utility;
import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;

public class TestRunner {
	public static void name(String args[]) {
		
		TestNG runner = new TestNG();
		
		List<String> list = new ArrayList<String>();
		
		list.add("C:\\Cred_Stream_Q4\\test-output\\testng-failed.xml");   //test-output" folder ->testng-failed.xml file location
		
		runner.setTestSuites(list);
		
		runner.run();
	}

}
