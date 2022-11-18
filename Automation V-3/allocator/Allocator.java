package allocator;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.openqa.selenium.Platform;

import supportlibraries.com.ncr.framework.selenium.*;
import supportlibraries.com.ncr.framework.FrameworkParameters;
import supportlibraries.com.ncr.framework.ExcelDataAccess;

import supportlibraries.com.ncr.framework.IterationOptions;
import supportlibraries.com.ncr.framework.Settings;


/**
 * Class to manage the batch execution of test scripts within the framework
 * @author Ncr
 */
public class Allocator
{
	private FrameworkParameters frameworkParameters =FrameworkParameters.getInstance();
	private Properties properties;
	private ResultSummaryManager resultSummaryManager = new ResultSummaryManager();
	
	
	/**
	 * The entry point of the test batch execution
	 * @param args Command line arguments to the Allocator (Not applicable)
	 */
	public static void main(String[] args)
	{
		Allocator allocator = new Allocator();
		allocator.driveBatchExecution();
	}
	
	private void driveBatchExecution()
	{
		System.out.println("Starting : driveBatchExecution ");
		resultSummaryManager.setRelativePath();
		properties = Settings.getInstance();
		resultSummaryManager.initializeTestBatch(properties.getProperty("RunConfiguration"));
		
		int nThreads = Integer.parseInt(properties.getProperty("NumberOfThreads"));
		resultSummaryManager.initializeSummaryReport(nThreads);
		
		resultSummaryManager.setupErrorLog();
		
		executeTestBatch(nThreads);
		
		resultSummaryManager.wrapUp(false);
		resultSummaryManager.launchResultSummary();
		
		System.out.println("Ending : driveBatchExecution ");
	}
	
	private void executeTestBatch(int nThreads)
	{
		System.out.println("Starting : executeTestBatch ");
		List<SeleniumTestParameters> testInstancesToRun =
							getRunInfo(frameworkParameters.getRunConfiguration());
		ExecutorService parallelExecutor = Executors.newFixedThreadPool(nThreads);
		System.out.println("Test Instances  : "+testInstancesToRun.size());
		
		for (int currentTestInstance = 0; currentTestInstance < testInstancesToRun.size() ; currentTestInstance++ ) {
			System.out.println("inside for loop");
			ParallelRunner testRunner =
					new ParallelRunner(testInstancesToRun.get(currentTestInstance),
																resultSummaryManager);
			parallelExecutor.execute(testRunner);
			
			if(frameworkParameters.getStopExecution()) {
				break;
			}
		}
		
		parallelExecutor.shutdown();
		while(!parallelExecutor.isTerminated()) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Ending : executeTestBatch ");

	}
	
	private List<SeleniumTestParameters> getRunInfo(String sheetName)
	{
		
		System.out.println("Starting : getRunInfo");
		ExcelDataAccess runManagerAccess = new ExcelDataAccess(frameworkParameters.getRelativePath(), "Run Manager");			
		runManagerAccess.setDatasheetName(sheetName);
		
		int nTestInstances = runManagerAccess.getLastRowNum();
		List<SeleniumTestParameters> testInstancesToRun = new ArrayList<SeleniumTestParameters>();
		
		for (int currentTestInstance = 1; currentTestInstance <= nTestInstances; currentTestInstance++) {
			System.out.println("Inside for loop");
			String executeFlag = runManagerAccess.getValue(currentTestInstance, "Execute");
			
			if (executeFlag.equalsIgnoreCase("Yes")) {
				System.out.println("Inside if loop");
				String currentScenario = runManagerAccess.getValue(currentTestInstance, "TestScenario");
				String currentTestcase = runManagerAccess.getValue(currentTestInstance, "TestCase");
				SeleniumTestParameters testParameters =
						new SeleniumTestParameters(currentScenario, currentTestcase);
				
				testParameters.setCurrentTestDescription(runManagerAccess.getValue(currentTestInstance, "Description"));
				
				String iterationMode = runManagerAccess.getValue(currentTestInstance, "IterationMode");
				if (!iterationMode.equals("")) {
					testParameters.setIterationMode(IterationOptions.valueOf(iterationMode));
				} else {
					testParameters.setIterationMode(IterationOptions.RunAllIterations);
				}
				
				String startIteration = runManagerAccess.getValue(currentTestInstance, "StartIteration");
				if (!startIteration.equals("")) {
					testParameters.setStartIteration(Integer.parseInt(startIteration));
				}
				String endIteration = runManagerAccess.getValue(currentTestInstance, "EndIteration");
				if (!endIteration.equals("")) {
					testParameters.setEndIteration(Integer.parseInt(endIteration));
				}
				
				String browser = runManagerAccess.getValue(currentTestInstance, "Browser");
				if (!browser.equals("")) {
					testParameters.setBrowser(Browser.valueOf(browser));
				} else {
					testParameters.setBrowser(Browser.valueOf(properties.getProperty("DefaultBrowser")));
				}
				String browserVersion = runManagerAccess.getValue(currentTestInstance, "BrowserVersion");
				if (!browserVersion.equals("")) {
					testParameters.setBrowserVersion(browserVersion);
				}
				String platform = runManagerAccess.getValue(currentTestInstance, "Platform");
				if (!platform.equals("")) {
					testParameters.setPlatform(Platform.valueOf(platform));
				} else {
					testParameters.setPlatform(Platform.valueOf(properties.getProperty("DefaultPlatform")));
				}
				
				System.out.println("testParameters : "+testParameters.getBrowserVersion());
				System.out.println("testParameters : "+testParameters.getCurrentScenario());
				System.out.println("testParameters : "+testParameters.getCurrentTestcase());
				System.out.println("testParameters : "+testParameters.getCurrentTestDescription());
				System.out.println("testParameters : "+testParameters.getBrowser());
				
				testInstancesToRun.add(testParameters);
				System.out.println("Ending : getRunInfo");
			}
		}
		
		return testInstancesToRun;
	}
}