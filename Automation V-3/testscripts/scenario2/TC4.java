package testscripts.scenario2;

import java.awt.AWTException;

import org.testng.annotations.Test;

import supportlibraries.com.ncr.framework.IterationOptions;

import supportlibraries.DriverScript;
import supportlibraries.TestCase;


/**
 * Test for book flight tickets and verify booking
 * @author NCR
 */
public class TC4 extends TestCase
{
	@Test
	public void runTC4() throws AWTException
	{
		testParameters.setCurrentTestDescription("Test");
		testParameters.setIterationMode(IterationOptions.RunOneIterationOnly);
		//testParameters.setBrowser(Browser.InternetExplorer);
		
		driverScript = new DriverScript(testParameters);
		driverScript.driveTestExecution();
	}
}