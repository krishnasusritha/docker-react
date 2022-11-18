package supportlibraries.com.ncr.framework.selenium;

public enum Browser
{
  Chrome(
    "chrome"), 
  Firefox("firefox"), 
  HtmlUnit("htmlunit"), 
  InternetExplorer("internet explorer"), 
  Opera(
    "opera"), 
  Safari("safari");

  private String value;

  private Browser(String value)
  {
    this.value = value;
  }

  public String getValue()
  {
    return this.value;
  }
}
