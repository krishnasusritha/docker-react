package testscripts.scenario1;

import java.awt.AWTException;

import org.testng.annotations.Test;

import supportlibraries.com.ncr.framework.IterationOptions;

import supportlibraries.DriverScript;
import supportlibraries.TestCase;


/**
 * Test for login with valid user credentials
 * @author NCR
 */
public class TC1 extends TestCase
{
	@Test
	public void runTC1() throws AWTException
	{
		testParameters.setCurrentTestDescription("Test for login with valid user credentials");
		testParameters.setIterationMode(IterationOptions.RunOneIterationOnly);
		//testParameters.setBrowser(Browser.HtmlUnit);
		
		driverScript = new DriverScript(testParameters);
		driverScript.driveTestExecution();
	}
}