package testscripts.scenario1;

import java.awt.AWTException;

import org.testng.annotations.Test;

import supportlibraries.DriverScript;
import supportlibraries.TestCase;


/**
 * Test for register new user and login using the registered user
 * @author NCR
 */
public class TC3 extends TestCase
{
	@Test
	public void runTC3() throws AWTException
	{
		testParameters.setCurrentTestDescription("Test");
		
		driverScript = new DriverScript(testParameters);
		driverScript.driveTestExecution();
	}
}