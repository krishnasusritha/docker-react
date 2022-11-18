package allocator;

import java.awt.AWTException;
import java.util.Date;

import supportlibraries.DriverScript;
import supportlibraries.com.ncr.framework.selenium.*;

import supportlibraries.com.ncr.framework.FrameworkParameters;
import supportlibraries.com.ncr.framework.Util;


/**
 * Class to facilitate parallel execution of test scripts
 * @author NCR
 */
public class ParallelRunner implements Runnable
{
	private final SeleniumTestParameters testParameters;
	private final ResultSummaryManager resultSummaryManager;
	
	
	/**
	 * Constructor to initialize the details of the test case to be executed
	 * @param testParameters The {@link SeleniumTestParameters} object (passed from the {@link Allocator})
	 * @param resultSummaryManager The {@link ResultSummaryManager} object (passed from the {@link Allocator})
	 */
	public ParallelRunner(SeleniumTestParameters testParameters, ResultSummaryManager resultSummaryManager)
	{
		super();
		
		this.testParameters = testParameters;
		this.resultSummaryManager = resultSummaryManager;
	}
	
	@Override
	public void run()
	{
		Date startTime = Util.getCurrentTime();
		String testStatus = null;
		try {
			testStatus = invokeTestScript();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date endTime = Util.getCurrentTime();
		String executionTime = Util.getTimeDifference(startTime, endTime);
		resultSummaryManager.updateResultSummary(testParameters.getCurrentScenario(),
									testParameters.getCurrentTestcase(),
									testParameters.getCurrentTestDescription(),
									executionTime, testStatus);
	}
	
	private String invokeTestScript() throws AWTException
	{
		String testStatus;
		FrameworkParameters frameworkParameters = FrameworkParameters.getInstance();
		
		if(frameworkParameters.getStopExecution()) {
			testStatus = "Aborted";
		} else {
			System.out.println("starting invokeTestScript");
			DriverScript driverScript = new DriverScript(this.testParameters);
			driverScript.setTestExecutedInUnitTestFramework(false);
			driverScript.driveTestExecution();
			testStatus = driverScript.getTestStatus();
			System.out.println("ending invokeTestScript");
		}
		
		return testStatus;
	}
}