package testscripts.scenario1;

import java.awt.AWTException;

import org.testng.annotations.Test;

import supportlibraries.com.ncr.framework.selenium.Browser;

import supportlibraries.DriverScript;
import supportlibraries.TestCase;


/**
 * Test for login with invalid user credentials
 * @author NCR
 */
public class TC2 extends TestCase
{
	@Test
	public void runTC2() throws AWTException
	{
		testParameters.setCurrentTestDescription("Test for login with invalid user credentials");
		testParameters.setBrowser(Browser.Chrome);
		
		driverScript = new DriverScript(testParameters);
		driverScript.driveTestExecution();
	}
}