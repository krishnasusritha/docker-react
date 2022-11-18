package supportlibraries;

import supportlibraries.com.ncr.framework.CraftDataTable;
import supportlibraries.com.ncr.framework.FrameworkParameters;
import supportlibraries.com.ncr.framework.Settings;
import supportlibraries.com.ncr.framework.selenium.SeleniumReport;
import java.util.Properties;
import org.openqa.selenium.WebDriver;

public abstract class ReusableLibrary
{
  protected CraftDataTable dataTable;
  protected SeleniumReport report;
  protected WebDriver driver;
  protected ScriptHelper scriptHelper;
  protected Properties properties;
  protected FrameworkParameters frameworkParameters;

  public ReusableLibrary(ScriptHelper scriptHelper)
  {
    this.scriptHelper = scriptHelper;

    this.dataTable = scriptHelper.getDataTable();
    this.report = scriptHelper.getReport();
    this.driver = scriptHelper.getDriver();

    this.properties = Settings.getInstance();
    this.frameworkParameters = FrameworkParameters.getInstance();
  }
}