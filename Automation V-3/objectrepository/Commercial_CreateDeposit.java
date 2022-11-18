/**
 * 
 */
package objectrepository;
import org.openqa.selenium.By;

/**
 * @author mg185281
 *
 */
public class Commercial_CreateDeposit {
//Domain selection Page
	public static final By UserType= By.id("Form:DomainsInput");
	public static final By Start= By.id("Form:StartButton");
	
	
//Login Page	
	public static final By UserID = By.id("Form:UserIDInput");
	public static final By Password = By.id("Form:PasswordInput");
	public static final By LoginButton = By.id("Form:LoginButton");
	
	
//Home page
	public static final By CreateDeposit = By.xpath("//img[@src=\"images/welcomelink_newdeposit.gif\"]");
	
//Create New Deposit Page
	
	public static final By ExpectedAmount = By.id("Form:ExpectedAmountInput");
	public static final By StartDepositButton = By.id("Form:StartDepositButton");
	//public static final By CreateDeposit = By.xpath("//img[@src=\"images/welcomelink_newdeposit.gif\"]");
	

//Deposit List
	public static final By DepositList = By.xpath("//img[@src=\"images/welcomelink_depositlist.gif\"]");
	public static final By CreateNewDeposit = By.id("Form:CreateDepositButton");
	
	
}
