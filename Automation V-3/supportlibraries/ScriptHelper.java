package supportlibraries;

import supportlibraries.com.ncr.framework.CraftDataTable;
import supportlibraries.com.ncr.framework.selenium.SeleniumReport;
import org.openqa.selenium.WebDriver;

public class ScriptHelper
{
  private final CraftDataTable dataTable;
  private final SeleniumReport report;
  private final WebDriver driver;

  public ScriptHelper(CraftDataTable dataTable, SeleniumReport report, WebDriver driver)
  {
    this.dataTable = dataTable;
    this.report = report;
    this.driver = driver;
  }

  public CraftDataTable getDataTable()
  {
    return this.dataTable;
  }

  public SeleniumReport getReport()
  {
    return this.report;
  }

  public WebDriver getDriver()
  {
    return this.driver;
  }
}