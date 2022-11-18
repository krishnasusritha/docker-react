package supportlibraries;

import supportlibraries.com.ncr.framework.FrameworkParameters;
import supportlibraries.com.ncr.framework.Util;
import supportlibraries.com.ncr.framework.selenium.ResultSummaryManager;
import supportlibraries.com.ncr.framework.selenium.SeleniumTestParameters;
import java.util.Date;
import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.xml.XmlTest;

public abstract class TestCase
{
  protected SeleniumTestParameters testParameters;
  protected DriverScript driverScript;
  private ResultSummaryManager resultSummaryManager = new ResultSummaryManager();
  private Date startTime;
  private Date endTime;

  @BeforeSuite
  public void suiteSetup(ITestContext testContext)
  {
    this.resultSummaryManager.setRelativePath();
    this.resultSummaryManager.initializeTestBatch(testContext.getSuite().getName());
    int nThreads;
  //int nThreads;
    if (testContext.getSuite().getParallel().equalsIgnoreCase("false"))
      nThreads = 1;
    else {
      nThreads = testContext.getCurrentXmlTest().getThreadCount();
    }
    this.resultSummaryManager.initializeSummaryReport(nThreads);
    this.resultSummaryManager.setupErrorLog();
  }

  @BeforeMethod
  public void testMethodSetup()
  {
    FrameworkParameters frameworkParameters = FrameworkParameters.getInstance();
    if (frameworkParameters.getStopExecution()) {
      suiteTearDown();

      throw new SkipException("Aborting all subsequent tests!");
    }
    this.startTime = Util.getCurrentTime();

    String currentScenario = 
      capitalizeFirstLetter(getClass().getPackage().getName().substring(12));
    String currentTestcase = getClass().getSimpleName();
    this.testParameters = new SeleniumTestParameters(currentScenario, currentTestcase);
  }

  private String capitalizeFirstLetter(String myString)
  {
    StringBuilder stringBuilder = new StringBuilder(myString);
    stringBuilder.setCharAt(0, Character.toUpperCase(stringBuilder.charAt(0)));
    return stringBuilder.toString();
  }

  @AfterMethod
  public void testMethodTearDown()
  {
    String testStatus = this.driverScript.getTestStatus();
    this.endTime = Util.getCurrentTime();
    String executionTime = Util.getTimeDifference(this.startTime, this.endTime);
    this.resultSummaryManager.updateResultSummary(this.testParameters.getCurrentScenario(), 
      this.testParameters.getCurrentTestcase(), 
      this.testParameters.getCurrentTestDescription(), 
      executionTime, testStatus);
  }

  @AfterSuite
  public void suiteTearDown()
  {
    this.resultSummaryManager.wrapUp(Boolean.valueOf(true));
  }
}