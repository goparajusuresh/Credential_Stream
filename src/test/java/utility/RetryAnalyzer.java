package utility;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

public class RetryAnalyzer implements IRetryAnalyzer, IAnnotationTransformer{

	int counter = 0;
	int retryLimit = 2;
	
	@Override
	public boolean retry(ITestResult result) {
		if(counter < retryLimit) {
		counter++;
		return true;
		}
		return false;
	}

	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		
		annotation.setRetryAnalyzer(RetryAnalyzer.class);
		
	}
}