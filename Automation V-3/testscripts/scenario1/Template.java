package testscripts.scenario1;

import java.awt.AWTException;

import org.testng.annotations.Test;

import supportlibraries.com.ncr.framework.IterationOptions;
import supportlibraries.com.ncr.framework.selenium.Browser;

import supportlibraries.DriverScript;
import supportlibraries.TestCase;


/**
 * Template test
 * @author NCR
 */
public class Template extends TestCase
{
	@Test(enabled = false)
	public void runTemplate() throws AWTException
	{
		// Modify the test parameters as required
		testParameters.setCurrentTestDescription("Template test");
		testParameters.setIterationMode(IterationOptions.RunOneIterationOnly);
		testParameters.setBrowser(Browser.InternetExplorer);
		
		driverScript = new DriverScript(testParameters);
		driverScript.driveTestExecution();
	}
}