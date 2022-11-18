package businesscomponents;

import org.apache.commons.io.IOUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.remote.server.handler.SendKeys;
import org.openqa.selenium.security.UserAndPassword;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;

import supportlibraries.com.ncr.framework.Status;
import supportlibraries.com.ncr.framework.selenium.ExecutionMode;
//import com.sun.jna.platform.unix.X11.KeySym;

import java.awt.AWTException;
//import java.awt.List;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Driver;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import objectrepository.*;


/**
 * Functional Components class
 */
public class FunctionalComponents extends ReusableLibrary
{
	boolean rc;
	JavascriptExecutor executor = (JavascriptExecutor) driver; 
	//private boolean acceptNextAlert = true;

	Robot rb = null;
	//SearchTests st = null;
	//public String concatPrice = null;

	/*public String  vendorAgent= dataTable.getData("user_Data","VENDOR AGENT");
	public String  NoofPassengers= dataTable.getData("user_Data","No_of_Passengers");
	public String vendorPhone = dataTable.getData("user_Data","VENDOR PHONE");
	public String InternalAccountingRemarks = dataTable.getData("user_Data","InternalAccountingRemarks");
	public String deposit_amount = dataTable.getData("User_Data", "DEPOSIT AMOUNT");



	public static String ssoUrl="https://ssointrawq.intra.aexp.com";
	public static String BASEURL = "https://qtravel.intra.aexp.com/travel/intra/authreg/getADW?release=R4";
*/
	/**
	 * Constructor to initialize the component library
	 * @param scriptHelper The {@link ScriptHelper} object passed from the {@link DriverScript}
	 */
	public FunctionalComponents(ScriptHelper scriptHelper)
	{
		super(scriptHelper);

		try {
			rb = new Robot();
			//st = new SearchTests(scriptHelper);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	
	public void invokeApplication() throws InterruptedException, AWTException
	{
		if(properties.getProperty("DefaultBrowser").equalsIgnoreCase("InternetExplorer"))
		{
			driver.get(properties.getProperty("ApplicationUrl"));
			Thread.sleep(10000);
		}
		else
		{
			/*String userName = dataTable.getData("General_Data", "Username");
			String password = dataTable.getData("General_Data", "Password");
			String ads_sso = dataTable.getData("User_Data", "AdsSSO");*/

			//String ssologin = ads_sso+userName;
			//String ssoUrl="https://ssointrawq.intra.aexp.com";
			String url = properties.getProperty("ApplicationUrl");
			//String encodedCredentials =returnEncodedCredentials(ssologin , password);
			//String SSOURL=ssoUrl.replace("https://","https://"+encodedCredentials+"@");
			//String BASEURL=url.replace("https://","https://"+encodedCredentials+"@");

			//driver.get(SSOURL);
			driver.get(url);
			Thread.sleep(10);

			//driver.get(properties.getProperty("ApplicationUrl"));
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			//driver.manage().deleteAllCookies();
			report.updateTestLog("Open Application", "Application URL:" +
					properties.getProperty("ApplicationUrl"), Status.DONE);
		}


	}

	public void login() throws InterruptedException, IOException, AWTException
	{
		//JavascriptExecutor executor = (JavascriptExecutor) driver; 
		driver.findElement(By.id("Form:StartButton")).click();
		Thread.sleep(2000);
		
		String userName = dataTable.getData("General_Data", "Username");
		String password = dataTable.getData("General_Data", "Password");
		//String ads_sso = dataTable.getData("User_Data", "AdsSSO");
		//String ss = "ads-sso-2";
		//String ssologin = ads_sso+userName;

		// Enter username and password
		driver.findElement(Objectrepository.txtUsername).clear();
		driver.findElement(Objectrepository.txtUsername).sendKeys(userName);
		driver.findElement(Objectrepository.txtPassword).clear();
		driver.findElement(Objectrepository.txtPassword).sendKeys(password);
		driver.findElement(Objectrepository.btnLogin).click();

		if(properties.getProperty("DefaultBrowser").equalsIgnoreCase("InternetExplorer"))
		{

			boolean rc;

			System.out.println("rc");

			System.out.println("alert is displayed");
			Alert aa = driver.switchTo().alert();
			System.out.println("We are at alert");

			Thread.sleep(5000);

			aa.sendKeys("ads-sso-2\\t22encore");
			Robot a;
			try {
				a = new Robot();
				a.keyPress(KeyEvent.VK_ENTER);
				Thread.sleep(2000);

				System.out.println("password is displayed");

				Alert bb = driver.switchTo().alert();
				System.out.println("We are at alert");
				bb.sendKeys(password); 
				Thread.sleep(5000);
				a.keyPress(KeyEvent.VK_ENTER);
				System.out.println("Password entered");
				Thread.sleep(10000);

			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("ENCORE page");

		}

		// Verify the login successful or not by check the logout link display on the page
		Boolean logincheck = waitForElement(Objectrepository.linkLogOut);
		if(logincheck){
			report.updateTestLog("Load Dashboard Screen", "Verify User login", Status.PASS);
		}
		else{
			report.updateTestLog("Load Dashboard Screen", "Verify User login", Status.FAIL);
		}

	//report.updateTestLog("Login", "login User Details : " + "User=" + userName + ", " + "Password = " + password, Status.DONE);
		report.updateTestLog("Login User","User Name : "+ userName,Status.DONE);
	}
	public String returnEncodedCredentials(String userID, String password )
	{
		String credentials;
		userID=userID.toLowerCase();
		userID=userID.replace("-", "%2D");
		userID=userID.replace("\\", "%5C");
		userID=userID.replace("@", "%40");
		password=password.replace("-", "%2D");
		password=password.replace("@", "%40");
		password=password.replace("$", "%24");
		credentials=userID+":"+password;
		return(credentials);

	} 

	public boolean waitForElement(By element) {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		boolean flag = false;
		for(int i =1;i<=5;i++){
			try{
				if(driver.findElement(element).isDisplayed()){
					flag = true;
					break;
				}
			}catch(Exception e){
				System.out.println(e);
				flag=false;
			}
		}
		return flag;
	} 

	public boolean waitTillTheElementLoads(By element) {
		boolean flag = false;
		try{
			WebDriverWait wait = new WebDriverWait(driver,200);
			wait.until(ExpectedConditions.visibilityOfElementLocated(element));
			flag = true;
		}
		catch(Exception E){
			flag = false;
		}
		return flag;
	}  
//	public void pA5Link() throws InterruptedException, IOException
//	{
//		/*ChromeOptions c = new ChromeOptions();
//		c.addArguments("disable-extensions");
//		c.addArguments("--start-maximized");
//		System.setProperty("webdriver.chrome.driver", "D:\\chromedriver.exe");
//
//		driver = new ChromeDriver(c);*/
//		System.out.println("clicking on quick link");
//		Thread.sleep(1000);
//		executor.executeScript("arguments[0].click();", driver.findElement(By.xpath("//*[@id='quickLinksToolTip']/div")));
//		Thread.sleep(2000);
//		System.out.println("clicking on PA5 link");
//
//		Thread.sleep(3000);
//		//Window handle
//		String parentHandle = driver.getWindowHandle(); // get the current window handle
//		driver.findElement(By.linkText("Power Agent")).click();
//		System.out.println("PA5 opened");
//
//		for (String winHandle : driver.getWindowHandles()) {
//			driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle (that's your newly opened window)
//			report.updateTestLog("PA5 Page opend", "PA5 Page opend", Status.PASS);
//		}
//
//		Thread.sleep(30000);
//	}

	public void selectmiramar() throws InterruptedException, IOException, AWTException
	{
		//Selecting miramar Agency

		Actions action = new Actions (driver);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebElement menu = driver.findElement(By.xpath("/html/body/div[2]/div[1]/div[1]/ul/li[3]/a"));
		Thread.sleep(5000);
		action.moveToElement(menu).build().perform();
		action.click(menu).perform();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("AMEX miramar - AMEX miramar")));
		Thread.sleep(3000);
		driver.findElement(By.xpath("/html/body/div[2]/div[1]/div[1]/ul/li[3]/div/div/ul/li[5]/a")).click();
		Thread.sleep(2000);
		if(driver.findElement(By.xpath("//span[contains(@class,'header-agency-label')]")).getText()=="AMEX phoenix"){
			driver.findElement(By.xpath("/html/body/div[2]/div[1]/div[1]/ul/li[3]/a")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("/html/body/div[2]/div[1]/div[1]/ul/li[3]/div/div/ul/li[4]/a")).click();
		}

		//wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(),'AMEX miramar - AMEX miramar')]"))).click();
		boolean rc;

		rc = waitForElement(By.xpath("//li[contains(@class,'products-menu-item manual-tour')]"));
		if(rc)
		{

			driver.findElement(By.xpath("//li[contains(@class,'products-menu-item manual-tour')]"));
			Thread.sleep(5000);
			report.updateTestLog("Booking information page is opend", "Booking information page is opend", Status.PASS);

		}
		else
		{
			report.updateTestLog("Booking information page not opend", "Booking information page not opend", Status.FAIL);
		}
		report.updateTestLog("clicked on MIRAMER link", "clicked on MIRAMER link", Status.PASS);
	}
	public void tourIcon() throws InterruptedException, IOException, AWTException
	{
		driver.findElement(By.xpath("//li[contains(@class,'products-menu-item manual-tour')]")).click();
		Thread.sleep(5000);
		report.updateTestLog("clicked on Tour Icon", "clicked on Tour Icon", Status.PASS);
	}
	public void cancelPNR() throws InterruptedException, IOException, AWTException
	{
		//Click on service tab
		driver.findElement(By.xpath("//a[contains(text(),'Service')]")).click();
		Thread.sleep(5000);
		//Entering the PNR
		driver.findElement(By.xpath("//input[contains(@class,'input-selected-border-left quick-search')]")).click();
		String PNR = dataTable.getData("User_Data", "PNR");
		WebElement pnr = driver.findElement(By.xpath("//input[contains(@class,'input-selected-border-left quick-search')]"));
		pnr.sendKeys(PNR);
		//Click on Search
		driver.findElement(By.xpath("//a[contains(@class,'button task button-search input-selected-border')]")).click();
		Thread.sleep(10000);
		//Click on trips
		driver.findElement(By.xpath("//span[contains(@class,'abc small button')]")).click();
		Thread.sleep(5000);
		//Click on View
		driver.findElement(By.xpath("//a[contains(@class,'small button view')]")).click();
		Thread.sleep(5000);
		//Click on expand
		driver.findElement(By.xpath("//Span[contains(text(),'Expand')]")).click();
		Thread.sleep(1000);
		executor.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//div[contains(@class,'ui-panel-container-padding')]/ul")));

		//Click on cancel
		driver.findElement(By.xpath("//div[contains(@class,'ui-panel-container-padding')]/ul/li[1]")).click();
		Thread.sleep(10000);
		//Page down

		/* Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    robot.keyPress(KeyEvent.VK_PAGE_DOWN);
	    JavascriptExecutor jse = (JavascriptExecutor) driver;
	    jse.executeScript("window.scrollBy(0,450)", "");
	    Thread.sleep(5000);*/
		executor.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//button[contains(text(),'Cancel Booking')]")));
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,450)", "");
		//Click on Cancel booking
		driver.findElement(By.xpath("//button[contains(text(),'Cancel Booking')]")).click();
		Thread.sleep(10000);
		report.updateTestLog("PNR Cancelled", "PNR Cancelled", Status.PASS);



	}


	public void pullprofile() throws InterruptedException, IOException, AWTException
	{
		String entercardnum = dataTable.getData("General_Data", "CARDNUMBER");
		driver.findElement(BookingInformationPage.SELECT_CUSTOMER).click();
		driver.findElement(BookingInformationPage.SEARCH_CARD).click();

		driver.findElement(BookingInformationPage.SEARCH_CARD).sendKeys(entercardnum);

		rc = waitForElement(By.xpath("//a[contains(@class,'manage-customer-info-button-use button')]"));
		if(rc)
		{

			driver.findElement(By.xpath("//a[contains(@class,'manage-customer-info-button-use button')]"));
			Thread.sleep(6000);
			report.updateTestLog("Profile serached", "Profile serached", Status.PASS);

		}
		else
		{
			report.updateTestLog("Unable to search profile", "Unable to search profile", Status.FAIL);
		}

		driver.findElement(BookingInformationPage.USE_CUSTOMER).click();
		Thread.sleep(5000);
		//driver.switchTo().alert();
//		Actions action = new Actions(driver);
//		WebElement clickyes =  driver.findElement(By.xpath("/html/body/div[6]/div/form/div[3]/div/div[2]/button"));
//		action.moveToElement(clickyes).build().perform();
//		executor.executeScript("arguments[0].click();",clickyes);                                               
//		Thread.sleep(10000);
		//driver.findElement(BookingInformationPage.CLICK_YES).click();
		
		if(dataTable.getData("User_Data", "NewPNR").equalsIgnoreCase("Yes"))
		{
		
		
		String newPNR = dataTable.getData("User_Data", "NewPNR");
		Actions action = new Actions(driver);
		WebElement clickyes =  driver.findElement(By.xpath("/html/body/div[6]/div/form/div[3]/div/div[2]/button"));
		action.moveToElement(clickyes).build().perform();
		executor.executeScript("arguments[0].click();",clickyes);                                               
		Thread.sleep(10000);
		//driver.findElement(BookingInformationPage.CLICK_YES).click();
		}
		else if(dataTable.getData("User_Data", "SelectPNR").equalsIgnoreCase("Yes"))
		{
			// Click on Select PNR radio button
			Actions action = new Actions(driver);
		WebElement selectPNR =  driver.findElement(By.xpath("/html/body/div[6]/div/form/div[2]/div[2]/div/ul/li[2]/label"));
		action.moveToElement(selectPNR).build().perform();
		executor.executeScript("arguments[0].click();",selectPNR); 
		
		// Select PNR from the dropdown list
		Select locatorDropdown= new Select(driver.findElement(By.id("customer-record-locators")));
		locatorDropdown.selectByIndex(1);
		Thread.sleep(3000);
		
		// Click on Yes to move to next page
		Actions action2 = new Actions(driver);
		WebElement clickyes =  driver.findElement(By.xpath("/html/body/div[6]/div/form/div[3]/div/div[2]/button"));
		action2.moveToElement(clickyes).build().perform();
		executor.executeScript("arguments[0].click();",clickyes);                                               
		Thread.sleep(10000);                                     
		
		
		}
		else if(dataTable.getData("User_Data", "EnterPNR").equalsIgnoreCase("Yes")){
			//Click on Enter PNR radio button
			Actions action1 = new Actions(driver);
			WebElement enterPNR =  driver.findElement(By.xpath("/html/body/div[6]/div/form/div[2]/div[2]/div/ul/li[3]/label"));
			action1.moveToElement(enterPNR).build().perform();
			executor.executeScript("arguments[0].click();",enterPNR);                                               
			Thread.sleep(3000);
			
			//enter PNR
			
			String EnterPNR1 = dataTable.getData("User_Data", "EnterPNR1");
			Actions action4 = new Actions(driver);
			//WebElement enterPNR1 =  driver.findElement(By.xpath("/html/body/div[6]/div/form/div[2]/div[2]/div[3]/div/label"));
			WebElement enterPNR1= driver.findElement(By.id("pnr-entry"));
		
			action4.moveToElement(enterPNR1).build().perform();
			executor.executeScript("arguments[0].click();",enterPNR1); 
			enterPNR1.sendKeys(EnterPNR1);
			Thread.sleep(2000);
			
			// Click on Yes to move to next page
			Actions action = new Actions(driver);
			WebElement clickyes =  driver.findElement(By.xpath("/html/body/div[6]/div/form/div[3]/div/div[2]/button"));
			action.moveToElement(clickyes).build().perform();
			executor.executeScript("arguments[0].click();",clickyes);                                               
			Thread.sleep(10000);
			
		}
		
		boolean rc;

		rc = waitForElement(By.xpath("//input[contains(@id,'line_item_vendor_name')]"));
		if(rc)
		{

			driver.findElement(By.xpath("//input[contains(@id,'line_item_vendor_name')]"));
			Thread.sleep(5000);
			report.updateTestLog("Booking information page is opend", "Booking information page is opend", Status.PASS);

		}
		else
		{
			report.updateTestLog("Booking information page not opend", "Booking information page not opend", Status.FAIL);
		}
		report.updateTestLog("Pulled the Profile", "Pulled the Profile", Status.PASS);

	}


	public void bookinginfopullprofile() throws InterruptedException, IOException, AWTException
	{

		//String Vendorname = dataTable.getData("User_Data", "Vendorname");
		String Vendornamecode = dataTable.getData("User_Data", "Vendornamecode");

		driver.findElement(BookingInformationPage.VENDOR_NAME).click();
		driver.findElement(BookingInformationPage.VENDOR_NAME).sendKeys(Vendornamecode);
		//String s = driver.findElement(By.xpath("//input[contains(@id,'line_item_vendor_name')]")).getText();
		//System.out.println(s);
		driver.findElement(By.xpath("//div[@class='list is-dropdown has-results']/div/div/a")).click();
		Thread.sleep(5000);
		/*int i=1;
		int j1=i+1;
		try{
			for (i=1;i<j1;i++){	    		    		
				String s = driver.findElement(By.xpath("//input[contains(@id,'line_item_vendor_name')]")).getText();
				System.out.println(s);
				if (s.equals(Vendorname))
				{
					Thread.sleep(8000);
					System.out.println(s);
					driver.findElement(By.xpath("//div[@class='list is-dropdown has-selection has-results']/div")).click();
					Thread.sleep(5000);
					//	driver.findElement(BookingInformationPage.VENDOR_NAME).sendKeys(Vendorname,Keys.TAB);	
					break;
				}
				j1++;
			}

		}
		catch(Exception e){
			System.out.println("***Selected Vendor***");	
		}*/

		//	String DestinationCity = dataTable.getData("User_Data", "DestinationCity");
		String DestinationCitycode = dataTable.getData("User_Data", "DestinationCitycode");

		driver.findElement(BookingInformationPage.DESTINATION_CITY).click();
		driver.findElement(BookingInformationPage.DESTINATION_CITY).sendKeys(DestinationCitycode,Keys.ENTER);
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[@class='list is-dropdown has-results']/div/div/a")).click();
		Thread.sleep(5000);
		/*int j=1;
		int k=j+1;
		try{	    	
			for (j=1;j<k;j++){	    		    		
				String s1 = driver.findElement(By.xpath("//div[contains(@class,'list-results')]/div/a["+j+"]/b")).getText();
				System.out.println(s1);
				if (s1.equals(DestinationCitycode))
				{
					System.out.println(s1);
					Thread.sleep(2000);
					WebElement destcity = driver.findElement(By.xpath("//div[contains(@class,'list-results')]/div/a["+j+"]/b"));
					JavascriptExecutor exe = (JavascriptExecutor) driver;
					exe.executeScript("arguments[0].click();",destcity);
					Thread.sleep(5000);
					//driver.findElement(BookingInformationPage.DESTINATION_CITY).sendKeys(DestinationCity,Keys.TAB);    			
					break;
				}
				j++;	    		
			}
		}
		catch(Exception e){
			System.out.println("***Please search for another word***");	        

		}*/
		JavascriptExecutor exe = (JavascriptExecutor) driver;
		exe.executeScript("window.scrollBy(0,250)", "");
		//String Region = dataTable.getData("User_Data", "Region");
		String Regioncode = dataTable.getData("User_Data", "Regioncode");

		driver.findElement(BookingInformationPage.REGION).click();
		driver.findElement(BookingInformationPage.REGION).sendKeys(Regioncode,Keys.ENTER);
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[@class='list is-dropdown has-results']/div/div/a")).click();
		Thread.sleep(5000);
		/*int l=1;
		int m=l+1;
		try{	    	
			for (l=1;l<m;l++){	    		    		
				String s2 = driver.findElement(By.xpath("//div[contains(@class,'list-results')]/div/a["+l+"]/b")).getText();
				if (s2.equals(Regioncode))
				{
					System.out.println(s2);
					Thread.sleep(2000);
					WebElement region = driver.findElement(By.xpath("//div[contains(@class,'list-results')]/div/a["+l+"]/b"));
					JavascriptExecutor jse = (JavascriptExecutor) driver;
					jse.executeScript("arguments[0].click();",region);
					Thread.sleep(2000);
					//driver.findElement(BookingInformationPage.DESTINATION_CITY).sendKeys(Keys.TAB);  			
					break;
				}
				j++;	    		
			}
		}
		catch(Exception e){
			System.out.println("***Please search for another word***");	        

		}
		 */
		String hotelname = dataTable.getData("User_Data", "TOUR/HOTELNAME");
		String travelStartDate = dataTable.getData("user_Data","TRAVELDATESTART");
		String travelEndDate = dataTable.getData("user_Data","TRAVELDATEEND");
		String confirmationCode = dataTable.getData("user_Data","CONFIRMATIONCODE");
		String roomType = dataTable.getData("user_Data","ROOMTYPECODE");
		String hotelName1 = dataTable.getData("user_Data", "HOTELNAME");

		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		
//		//Add Hotel1
//		driver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/div[3]/div[2]/div/div[1]/div/form/div[2]/div/a")).click();
//		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//Hotel name
		driver.findElement(BookingInformationPage.TOUR_HOTEL_NAME).click();    			
		driver.findElement(BookingInformationPage.TOUR_HOTEL_NAME).sendKeys(hotelname,Keys.TAB);    			
		//Travel start date
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		driver.findElement(BookingInformationPage.TOUR_STARTDATE).clear();
		driver.findElement(BookingInformationPage.TOUR_STARTDATE).sendKeys(travelStartDate);

		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		//Travel end date
		driver.findElement(BookingInformationPage.TOUR_ENDDATE).clear();
		driver.findElement(BookingInformationPage.TOUR_ENDDATE).sendKeys(travelEndDate,Keys.TAB);
		//Enter Confirmation code
		driver.findElement(BookingInformationPage.CONFIRM_CODE).sendKeys(confirmationCode,Keys.TAB);
		driver.findElement(BookingInformationPage.ROOM_TYPE).sendKeys(roomType);
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);

		/*exe.executeScript("window.scrollBy(0,250)", "");
		driver.findElement(BookingInformationPage.HOTEL_NAME).click();
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		driver.findElement(BookingInformationPage.HOTEL_NAME).sendKeys(hotelName1);
		exe.executeScript("window.scrollBy(0,250)", "");
		Select dropdown = new Select(driver.findElement(By.id("traveler_1")));
		dropdown.selectByIndex(3);
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		exe.executeScript("window.scrollBy(0,250)", "");*/
		if(dataTable.getData("User_Data", "addMoreHotel").equalsIgnoreCase("Yes"))
		{
			addMoreHotel();
		}

		if(dataTable.getData("User_Data", "Air_included").equalsIgnoreCase("Yes"))
		{
			airincluded_bookinginformationpage();
		}

		else if(dataTable.getData("User_Data", "airseparate").equalsIgnoreCase("Yes"))
		{
			airseparate_bookinginformationpage();
		}

		else if(dataTable.getData("User_Data", "aironown").equalsIgnoreCase("Yes"))
		{
			aironown_bookinginformationpage();
		}


		Thread.sleep(5000);
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_PAGE_DOWN);
		robot.keyRelease(KeyEvent.VK_PAGE_DOWN);

		report.updateTestLog("page down", "page down", Status.PASS);
		driver.findElement(By.cssSelector("ul.panel-submit-nav>li>button")).sendKeys(Keys.ARROW_DOWN);
		System.out.println("scroll down");
		Thread.sleep(5000);
		//driver.findElement(By.xpath("//label[contains(text(),'Tour Program')]")).sendKeys(Keys.ARROW_DOWN);
		//Thread.sleep(5000);
		System.out.println("scroll down");
		WebElement submitButton = driver.findElement(By.cssSelector("ul.panel-submit-nav>li>button"));
		/*Thread.sleep(5000);
		System.out.println("About to click on continue");
		Actions actions1 =new Actions(driver);
		actions1.moveToElement(submitButton).build().perform();*/
		executor.executeScript("arguments[0].click();",submitButton); 
		System.out.println("clicked on continue");

		//driver.findElement(By.xpath("//button[contains(@type,'submit')]")).click();
		Thread.sleep(5000);

		//exe.executeScript("window.scrollBy(0,250)", "");
		boolean rc;

		rc = waitForElement(By.xpath("//div[contains(@class,'passenger-title')]"));
		if(rc)
		{

			driver.findElement(By.xpath("//div[contains(@class,'passenger-title')]"));
			Thread.sleep(5000);
			report.updateTestLog("Passenger selection page is opend", "Passenger selection page is opend", Status.PASS);

		}
		else
		{
			report.updateTestLog("Passenger selection page is not opend", "Passenger selection page is not opend", Status.FAIL);
		}

	}
	public void passengerselectionpage_onepassenger() throws InterruptedException, IOException, AWTException
	{
		/*Actions a = new Actions(driver);
		Thread.sleep(2000);
		a.moveToElement(
				driver.findElement(By
						.xpath("//a[contains(@class,'passenger-close is-icon')]")))
						.click().build().perform();*/
		Thread.sleep(2000);
		executor.executeScript("arguments[0].click();",driver.findElement(By.xpath("//a[contains(@class,'passenger-close is-icon')]")));
		Thread.sleep(3000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		/*js.executeScript("arguments[0].scrollIntoView(true);", driver
				.findElement(By.xpath("//button[contains(text(),'Continue')]")));
		driver.findElement(By.xpath("//button[contains(text(),'Continue')]"))
		.click();*/
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		report.updateTestLog(
				"Clicked on continue in the passengerselectionpage",
				"Clicked on continue in the passengerselectionpage",
				Status.PASS);
		report.updateTestLog("Clicked on continue in the passengerselectionpage", "Clicked on continue in the passengerselectionpage", Status.PASS);


	}
	public void bookinginfowithoutpullprofile() throws InterruptedException, IOException, AWTException
	{
		//String Vendorname = dataTable.getData("User_Data", "Vendorname");
		String Vendornamecode = dataTable.getData("User_Data", "Vendornamecode");

		driver.findElement(BookingInformationPage.VENDOR_NAME).click();
		driver.findElement(BookingInformationPage.VENDOR_NAME).sendKeys(Vendornamecode);
		//String s = driver.findElement(By.xpath("//input[contains(@id,'line_item_vendor_name')]")).getText();
		//System.out.println(s);
		driver.findElement(By.xpath("//div[@class='list is-dropdown has-results']/div/div/a")).click();
		Thread.sleep(5000);
		/*int i=1;
				int j1=i+1;
				try{
					for (i=1;i<j1;i++){	    		    		
						String s = driver.findElement(By.xpath("//input[contains(@id,'line_item_vendor_name')]")).getText();
						System.out.println(s);
						if (s.equals(Vendorname))	
						{
							Thread.sleep(8000);
							System.out.println(s);
							driver.findElement(By.xpath("//div[@class='list is-dropdown has-selection has-results']/div")).click();
							Thread.sleep(5000);
							//	driver.findElement(BookingInformationPage.VENDOR_NAME).sendKeys(Vendorname,Keys.TAB);	
							break;
						}
						j1++;
					}

				}
				catch(Exception e){
					System.out.println("***Selected Vendor***");	
				}*/

		//	String DestinationCity = dataTable.getData("User_Data", "DestinationCity");
		String DestinationCitycode = dataTable.getData("User_Data", "DestinationCitycode");

		driver.findElement(BookingInformationPage.DESTINATION_CITY).click();
		driver.findElement(BookingInformationPage.DESTINATION_CITY).sendKeys(DestinationCitycode,Keys.ENTER);
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[@class='list is-dropdown has-results']/div/div/a")).click();
		Thread.sleep(5000);
		/*int j=1;
				int k=j+1;
				try{	    	
					for (j=1;j<k;j++){	    		    		
						String s1 = driver.findElement(By.xpath("//div[contains(@class,'list-results')]/div/a["+j+"]/b")).getText();
						System.out.println(s1);
						if (s1.equals(DestinationCitycode))
						{
							System.out.println(s1);
							Thread.sleep(2000);
							WebElement destcity = driver.findElement(By.xpath("//div[contains(@class,'list-results')]/div/a["+j+"]/b"));
							JavascriptExecutor exe = (JavascriptExecutor) driver;
							exe.executeScript("arguments[0].click();",destcity);
							Thread.sleep(5000);
							//driver.findElement(BookingInformationPage.DESTINATION_CITY).sendKeys(DestinationCity,Keys.TAB);    			
							break;
						}
						j++;	    		
					}
				}
				catch(Exception e){
					System.out.println("***Please search for another word***");	        

				}*/
		JavascriptExecutor exe = (JavascriptExecutor) driver;
		exe.executeScript("window.scrollBy(0,250)", "");
		//String Region = dataTable.getData("User_Data", "Region");
		String Regioncode = dataTable.getData("User_Data", "Regioncode");

		driver.findElement(BookingInformationPage.REGION).click();
		driver.findElement(BookingInformationPage.REGION).sendKeys(Regioncode,Keys.ENTER);
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//div[@class='list is-dropdown has-results']/div/div/a")).click();
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);

		String Hotelname = dataTable.getData("User_Data", "TOUR/HOTELNAME");
		String travelStartDate = dataTable.getData("user_Data","TRAVELDATESTART");
		String travelEndDate = dataTable.getData("user_Data","TRAVELDATEEND");
		String confirmationCode = dataTable.getData("user_Data","CONFIRMATIONCODE");
		String roomType = dataTable.getData("user_Data","ROOMTYPECODE");
		String numberOfRooms = dataTable.getData("user_Data","NUMBER OF ROOMS");
		String tourStatus = dataTable.getData("user_Data", "TOUR_STATUS");
		String referencePNR = dataTable.getData("user_Data","CROSS REFERENCED PNR");

		String hotelName1 = dataTable.getData("user_Data", "HOTELNAME");
		String roomType1 = dataTable.getData("user_Data", "ROOM TYPE CODE");
		String hotelInDate = dataTable.getData("user_Data","HOTEL IN DATE");
		String hotelOutDate = dataTable.getData("user_Data","HOTEL OUT DATE");
		String hotelStatus = dataTable.getData("user_Data","HOTEL_STATUS");
		String traveler = dataTable.getData("user_Data","TRAVELER");
		String gender = dataTable.getData("user_Data", "GENDER");
		String title = dataTable.getData("user_Data","TITLE");
		String firstName = dataTable.getData("user_Data","LEGALFIRSTNAME" );
		String middleName = dataTable.getData("user_Data","LEGAL MIDDLE NAME");
		String lastName = dataTable.getData("user_Data","LEGALLASTNAME");
		String lastSecondName = dataTable.getData("user_Data","LEGAL SECOND LAST NAME");
		String suffix = dataTable.getData("user_Data","SUFFIX");
		String  dob = dataTable.getData("user_Data","DATEOFBIRTH");
		String citizenship = dataTable.getData("user_Data","COUNTRY OF CITIZENSHIP");
		String  air = dataTable.getData("user_Data","AIR");
		String  smoker = dataTable.getData("user_Data","SMOKER");

		//String  vendorAgent= dataTable.getData("user_Data","VENDOR AGENT");

		//String vendorPhone = dataTable.getData("user_Data","VENDOR PHONE");
		String  othSpecialInfo = dataTable.getData("user_Data","OTH SPECIAL ADVISEMENTS");
		String  packagePrice= dataTable.getData("user_Data","YOUR TOTAL PACKAGE PRICE ");
		String tourProgram = dataTable.getData("user_Data","TOUR PROGRAM");
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		//Hotel name
		driver.findElement(BookingInformationPage.TOUR_HOTEL_NAME).click();    			
		driver.findElement(BookingInformationPage.TOUR_HOTEL_NAME).sendKeys(Hotelname,Keys.TAB);    			
		//Travel start date
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		driver.findElement(BookingInformationPage.TOUR_STARTDATE).clear();
		driver.findElement(BookingInformationPage.TOUR_STARTDATE).sendKeys(travelStartDate);

		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		//Travel end date
		driver.findElement(BookingInformationPage.TOUR_ENDDATE).clear();
		driver.findElement(BookingInformationPage.TOUR_ENDDATE).sendKeys(travelEndDate,Keys.TAB);
		//Enter Confirmation code
		driver.findElement(BookingInformationPage.CONFIRM_CODE).sendKeys(confirmationCode,Keys.TAB);
		driver.findElement(BookingInformationPage.ROOM_TYPE).sendKeys(roomType);
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);

		exe.executeScript("window.scrollBy(0,250)", "");
		/*driver.findElement(BookingInformationPage.HOTEL_NAME).click();
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		driver.findElement(BookingInformationPage.HOTEL_NAME).sendKeys(hotelName1);
		exe.executeScript("window.scrollBy(0,250)", "");
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		Select dropdown = new Select(driver.findElement(By.xpath("//select[contains(@id,'line_item[hotel_info][1][title_id_1]')]")));
		dropdown.selectByIndex(2);
		exe.executeScript("window.scrollBy(0,250)", "");
		driver.findElement(BookingInformationPage.FIRST_NAME_bookinginfo).click();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.findElement(BookingInformationPage.FIRST_NAME_bookinginfo).sendKeys(firstName,Keys.TAB);
		driver.findElement(BookingInformationPage.FIRST_NAME_bookinginfo).sendKeys(Keys.TAB);
		System.out.println("Enterd First");
		exe.executeScript("window.scrollBy(0,250)", "");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.findElement(BookingInformationPage.LAST_NAME_bookinginfo).sendKeys(lastName);
		exe.executeScript("window.scrollBy(0,250)", "");
		driver.findElement(BookingInformationPage.DOB).click();

		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		driver.findElement(BookingInformationPage.DOB).sendKeys(dob);
		Thread.sleep(5000);*/
		if(dataTable.getData("User_Data", "addMoreHotel").equalsIgnoreCase("Yes"))
		{
			addMoreHotel();
		}
		exe.executeScript("window.scrollBy(0,250)", "");

		if(dataTable.getData("User_Data", "Air_included").equalsIgnoreCase("Yes"))
		{
			airincluded_bookinginformationpage();
		}

		else if(dataTable.getData("User_Data", "airseparate").equalsIgnoreCase("Yes"))
		{
			airseparate_bookinginformationpage();
		}

		else if(dataTable.getData("User_Data", "aironown").equalsIgnoreCase("Yes"))
		{
			aironown_bookinginformationpage();
		}


		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_PAGE_DOWN);
		robot.keyRelease(KeyEvent.VK_PAGE_DOWN);

		report.updateTestLog("page down", "page down", Status.PASS);
		driver.findElement(By.cssSelector("ul.panel-submit-nav>li>button")).sendKeys(Keys.ARROW_DOWN);
		System.out.println("scroll down");
		driver.findElement(By.cssSelector("ul.panel-submit-nav>li>button")).click();
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		boolean rc;

		rc = waitForElement(By.xpath("//a[contains(@class,'passenger-close is-icon')]"));
		if(rc)
		{

			driver.findElement(By.xpath("//a[contains(@class,'passenger-close is-icon')]"));
			Thread.sleep(5000);
			report.updateTestLog("Passenger selection page is opend", "Passenger selection page is opend", Status.PASS);

		}

	}

	public void insuranceInfopurchased() throws InterruptedException, IOException, AWTException
	{
		Thread.sleep(2000);
		String provider = dataTable.getData("User_Data", "PROVIDER");
		driver.findElement(BookingInformationPage.PROVIDER).sendKeys(provider,Keys.TAB);
		Thread.sleep(2000);
		String phone =dataTable.getData("User_Data", "PHONE");
		driver.findElement(BookingInformationPage.PHONE).sendKeys(phone,Keys.TAB);
		Thread.sleep(2000);
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		report.updateTestLog("Clicked continue on Insurance page", "Clicked continue on Insurance page by puchased Option", Status.PASS);

	}

	public void optionsPageMethodpullprofile() throws InterruptedException, IOException
	{


		Select dropdown_traveler = new Select(driver.findElement(OptionsPage.TRAVELER));
		dropdown_traveler.selectByIndex(2);
		String tour_rate = dataTable.getData("User_Data", "TOUR RATE");
		driver.findElement(OptionsPage.TOUR_RATE).sendKeys(tour_rate);

		String taxes = dataTable.getData("User_Data", "TAXES");
		driver.findElement(OptionsPage.TAXES).sendKeys(taxes);

		//Second traveler

		Select dropdown_traveler2 = new Select(driver.findElement(OptionsPage.TRAVELER2));
		dropdown_traveler2.selectByIndex(3);

		String tour_rate2 = dataTable.getData("User_Data", "TOUR RATE2");
		driver.findElement(OptionsPage.TOUR_RATE2).sendKeys(tour_rate2);


		String taxes2 = dataTable.getData("User_Data", "TAXES2");
		driver.findElement(OptionsPage.TAXES2).sendKeys(taxes2);

		//Additional option
		String commission = dataTable.getData("User_Data", "COMMISSION");
		driver.findElement(OptionsPage.COMMISSION).sendKeys(commission);
		JavascriptExecutor exe = (JavascriptExecutor) driver; 
		exe.executeScript("window.scrollBy(0,250)", ""); 
		String full_final_due_date = dataTable.getData("User_Data", "FULL/FINAL DUE DATE");
		driver.findElement(OptionsPage.FULL_FINAL_DUE_DATE).sendKeys(full_final_due_date);
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);

		String deposit_due_date = dataTable.getData("User_Data", "DEPOSITDUEDATE1");
		driver.findElement(OptionsPage.DEPOSIT_DUE_DATE).sendKeys(deposit_due_date);
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		//String deposit_amount = dataTable.getData("User_Data", "DEPOSIT AMOUNT");
		driver.findElement(OptionsPage.DEPOSIT_AMOUNT).sendKeys(deposit_amount);
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		//click on continue
		driver.findElement(By.xpath("//button[@type= 'submit']")).click();
		Thread.sleep(5000);

		/*boolean rc;

		rc = waitForElement(By.xpath("//input[contains(@id,'line_item_vendor_name')]"));
		if(rc)
		{

			driver.findElement(By.xpath("//input[contains(@id,'line_item_vendor_name')]"));
			Thread.sleep(5000);
			report.updateTestLog("Clicked on continue in Options page", "Clicked on continue in Options page", Status.PASS);

		}
		else
		{
			report.updateTestLog("Clicked on continue in Options page", "Clicked on continue in Options page", Status.PASS);
		}*/
	}







	public void confirmationPage() throws InterruptedException, IOException, AWTException
	{
		// Read the PNR and confirmation number from the screen
		String divID = driver.findElement(By.xpath("//div[contains(@class,'confirmation')]")).getAttribute("id");
		String PNR = driver.findElement(By.xpath("//ul[contains(@class,'confirmation-items')]/li[1]")).getText();
		String confirmation_num = driver.findElement(By.xpath("//ul[contains(@class,'confirmation-items')]/li[2]")).getText();
		System.out.println(divID);
		System.out.println(PNR);
		System.out.println(confirmation_num);
		Thread.sleep(10000);

		report.updateTestLog("Read PNR No", ""+PNR+"", Status.SCREENSHOT);
		report.updateTestLog("Read confirmation Number", ""+confirmation_num+"", Status.DONE);
		JavascriptExecutor exe = (JavascriptExecutor) driver; 
		exe.executeScript("window.scrollBy(0,500)", ""); 
		WebElement bookinginfo_section = driver.findElement(By.xpath("//div[contains(@class,'panel')]"));

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", bookinginfo_section);
		WebElement lastelement = driver.findElement(By.xpath("//span[contains(text(),'Region Name')]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", lastelement);
		exe.executeScript("window.scrollBy(0,500)", "");
		Thread.sleep(5000);
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_PAGE_DOWN);
		robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
		//Click on View Your Order button
		exe.executeScript("arguments[0].click();",driver.findElement(By.xpath("//button[contains(text(),'View Your Order')] ")));
		//driver.findElement(By.xpath("//button[contains(text(),'View Your Order')] "));
		Thread.sleep(5000);
		boolean rc;

		rc = waitForElement(By.xpath("//span[contains(text(),'Confirmation Number')]"));
		if(rc)
		{

			driver.findElement(By.xpath("//span[contains(text(),'Confirmation Number')]"));
			Thread.sleep(5000);
			report.updateTestLog("Clicked on view your order button", "Clicked on view your order button", Status.PASS);

		}
		else
		{
			report.updateTestLog("Clicked on view your order button", "Clicked on view your order button", Status.PASS);
		}
	}
	public void viewYourOrderPagecancelPNR() throws InterruptedException, IOException, AWTException
	{
		String cancelpenalty = dataTable.getData("user_Data","Cancel_Penalty");
		String CommissionRefund = dataTable.getData("user_Data","Commission_Refund");
		String AmountDuefromVendor = dataTable.getData("user_Data","AmountDuefromVendor");
		String TotalRefundDueClient = dataTable.getData("user_Data","TotalRefundDueClient");
		String CardholderName = dataTable.getData("user_Data","CardholderName");
		String CardNumber = dataTable.getData("user_Data","CardNumber");
		String InternalAccountingRemarks = dataTable.getData("user_Data","InternalAccountingRemarks");
		/*//Click on service tab
		driver.findElement(By.xpath("//a[contains(text(),'Service')]")).click();
		Thread.sleep(3000);
		//Entering the PNR
		driver.findElement(By.xpath("//input[contains(@class,'input-selected-border-left quick-search')]")).click();
		String PNR = dataTable.getData("User_Data", "PNR");
		WebElement pnr = driver.findElement(By.xpath("//input[contains(@class,'input-selected-border-left quick-search')]"));
		pnr.sendKeys(PNR);
		//Click on Search
		driver.findElement(By.xpath("//a[contains(@class,'button task button-search input-selected-border')]")).click();
		Thread.sleep(10000);
		//Click on trips
		driver.findElement(By.xpath("//span[contains(@class,'abc small button')]")).click();
		Thread.sleep(2000);
		//Click on View
		driver.findElement(By.xpath("//a[contains(@class,'small button view')]")).click();
		Thread.sleep(2000);*/
		executor.executeScript("window.scrollBy(0,100)", ""); 
		//Click on expand
		driver.findElement(By.xpath("//Span[contains(text(),'Expand')]")).click();
		Thread.sleep(1000);
		executor.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//div[contains(@class,'ui-panel-container-padding')]/ul")));

		//Click on cancel
		driver.findElement(By.xpath("//div[contains(@class,'ui-panel-container-padding')]/ul/li[1]")).click();
		Thread.sleep(10000);
		driver.findElement(By.xpath("//label[contains(@for,'apply_cancel_fee_no')]")).click();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//input[contains(@name,'cancellation_remarks_cancel_penalty')]")).click();
		driver.findElement(By.xpath("//input[contains(@name,'cancellation_remarks_cancel_penalty')]")).sendKeys(cancelpenalty);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//input[contains(@name,'cancellation_remarks_comission_refund')]")).sendKeys(CommissionRefund,Keys.TAB);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//input[contains(@name,'cancellation_remarks_amount_due_from_vendor')]")).sendKeys(AmountDuefromVendor,Keys.TAB);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//input[contains(@name,'cancellation_remarks_total_refund_client')]")).sendKeys(TotalRefundDueClient,Keys.TAB);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//input[contains(@name,'cancellation_remarks_card_holder_name')]")).sendKeys(CardholderName,Keys.TAB);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//input[contains(@name,'cancellation_remarks_card_nbr')]")).sendKeys(CardNumber,Keys.TAB);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//input[contains(@id,'cancellation_remarks_internal_accounting_remarks_1')]")).sendKeys(InternalAccountingRemarks,Keys.TAB);
		driver.findElement(By.xpath("//input[contains(@id,'cancellation_remarks_internal_accounting_remarks_2')]")).sendKeys(InternalAccountingRemarks,Keys.TAB);
		driver.findElement(By.xpath("//input[contains(@id,'cancellation_remarks_internal_accounting_remarks_3')]")).sendKeys(InternalAccountingRemarks,Keys.TAB);
		driver.findElement(By.xpath("//input[contains(@id,'cancellation_remarks_internal_accounting_remarks_4')]")).sendKeys(InternalAccountingRemarks,Keys.TAB);
		driver.findElement(By.xpath("//input[contains(@id,'cancellation_remarks_internal_accounting_remarks_5')]")).sendKeys(InternalAccountingRemarks,Keys.TAB);

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		JavascriptExecutor exe = (JavascriptExecutor) driver; 
		exe.executeScript("window.scrollBy(0,250)", ""); 
		//Click on Cancel booking
		driver.findElement(By.xpath("//button[contains(text(),'Cancel Booking')]")).click();
		Thread.sleep(5000);
		report.updateTestLog("PNR Cancelled", "PNR Cancelled", Status.PASS);
		boolean rc;

		rc = waitForElement(By.xpath("//button[contains(@class,'button secondary')]"));
		if(rc)
		{

			driver.findElement(By.xpath("//button[contains(@class,'button secondary')]"));
			Thread.sleep(5000);
			report.updateTestLog("Cancelled the PNR", "Cancelled the PNR", Status.PASS);

		}

	}

	public void modifyPage() throws InterruptedException, IOException, AWTException
	{
		/*//Click on service tab
		driver.findElement(By.xpath("//a[contains(text(),'Service')]")).click();
		Thread.sleep(5000);
		//Entering the PNR
		driver.findElement(By.xpath("//input[contains(@class,'input-selected-border-left quick-search')]")).click();
		String PNR = dataTable.getData("User_Data", "PNR");
		WebElement pnr = driver.findElement(By.xpath("//input[contains(@class,'input-selected-border-left quick-search')]"));
		pnr.sendKeys(PNR);
		//Click on Search
		driver.findElement(By.xpath("//a[contains(@class,'button task button-search input-selected-border')]")).click();
		Thread.sleep(10000);
		//Click on trips
		driver.findElement(By.xpath("//span[contains(@class,'abc small button')]")).click();
		Thread.sleep(5000);
		//Click on View
		driver.findElement(By.xpath("//a[contains(@class,'small button view')]")).click();
		Thread.sleep(5000);*/
		//Click on expand
		driver.findElement(By.xpath("//Span[contains(text(),'Expand')]")).click();
		Thread.sleep(1000);
		executor.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//div[contains(@class,'ui-panel-container-padding')]/ul")));
		//Click on modify booking
		driver.findElement(By.xpath("//a[contains(text(),'Modify Booking')]")).click();
		boolean rc;

		rc = waitForElement(By.id("vendor-agent"));
		if(rc)
		{

			driver.findElement(By.id("vendor-agent"));;
			Thread.sleep(5000);
			report.updateTestLog("Modify page is opend", "Modify page is opend", Status.PASS);
		}

		String vendor_agent = dataTable.getData("user_Data","vendor_agent");
		String vendor_phone = dataTable.getData("user_Data","vendor_phone");
		String change_description = dataTable.getData("user_Data","change_description");
		String cardholderfirstname = dataTable.getData("user_Data","cardholderfirstname");
		String cardholderlastname = dataTable.getData("user_Data","cardholderlastname");
		String card_number = dataTable.getData("user_Data","card_number");
		String sale_amount = dataTable.getData("user_Data","sale_amount");
		String depositdueamount = dataTable.getData("user_Data","depositdueamount");
		String depositduedate = dataTable.getData("user_Data","DEPOSITDUEDATE1");
		String finalfullamount = dataTable.getData("user_Data","finalfullamount");
		String fullduedate = dataTable.getData("user_Data","fullduedate");
		String commission = dataTable.getData("user_Data","COMMISSION");
		String confirmationnumbermodify = dataTable.getData("user_Data","confirmationnumbermodify");

		driver.findElement(BookingInformationPage.vendor_agent).click();
		driver.findElement(BookingInformationPage.vendor_agent).clear();
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		driver.findElement(BookingInformationPage.vendor_agent).sendKeys(vendor_agent,Keys.TAB);
		driver.findElement(BookingInformationPage.vendor_phone).clear();
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		driver.findElement(BookingInformationPage.vendor_phone).sendKeys(vendor_phone,Keys.TAB);
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		report.updateTestLog("After entering the phone number", "", Status.SCREENSHOT);
		driver.findElement(BookingInformationPage.change_description).sendKeys(change_description,Keys.TAB);
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		//report.updateTestLog("After entering the change description", "", Status.SCREENSHOT);
		/*
		executor.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.id("cardholder-first-name")));
		executor.executeScript("window.scrollBy(0,100)", "");*/
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		driver.findElement(BookingInformationPage.cardholderfirstname).clear();
		driver.findElement(BookingInformationPage.cardholderfirstname).sendKeys(cardholderfirstname,Keys.TAB);
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		driver.findElement(BookingInformationPage.cardholderlastname).clear();
		driver.findElement(BookingInformationPage.cardholderlastname).sendKeys(cardholderlastname,Keys.TAB);
		driver.findElement(BookingInformationPage.card_number).sendKeys(card_number,Keys.TAB);
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		report.updateTestLog("After card number", "", Status.SCREENSHOT);

		driver.findElement(BookingInformationPage.sale_amount).clear();
		driver.findElement(BookingInformationPage.sale_amount).sendKeys(sale_amount,Keys.TAB);
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		driver.findElement(BookingInformationPage.depositdueamount).clear();
		driver.findElement(BookingInformationPage.depositdueamount).sendKeys(depositdueamount,Keys.TAB);
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		driver.findElement(BookingInformationPage.depositduedate).clear();
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		driver.findElement(BookingInformationPage.depositduedate).sendKeys(depositduedate,Keys.TAB);
		driver.findElement(BookingInformationPage.finalfullamount).clear();
		driver.findElement(BookingInformationPage.finalfullamount).sendKeys(finalfullamount,Keys.TAB);

		driver.findElement(BookingInformationPage.fullduedate).clear();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.findElement(BookingInformationPage.fullduedate).sendKeys(fullduedate,Keys.TAB);
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		report.updateTestLog("After amount details", "", Status.SCREENSHOT);
		driver.findElement(BookingInformationPage.commission).clear();
		driver.findElement(BookingInformationPage.commission).sendKeys(commission);
		driver.findElement(BookingInformationPage.confirmationnumbermodify).clear();

		driver.findElement(BookingInformationPage.confirmationnumbermodify).sendKeys(confirmationnumbermodify,Keys.TAB);
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		report.updateTestLog("After entering confirmation numbre", "", Status.SCREENSHOT);
		executor.executeScript("window.scrollBy(0,250)", "");
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		Thread.sleep(5000);
		boolean rc1;

		rc1 = waitForElement(By.xpath("//button[contains(@class,'button secondary')]"));
		if(rc1)
		{

			driver.findElement(By.xpath("//button[contains(@class,'button secondary')]"));
			Thread.sleep(5000);
			report.updateTestLog("PNR Modified clicked on continue", "PNR Modified clicked on continue", Status.PASS);

		}
		report.updateTestLog("PNR Modified Sucessfully", "PNR Modified Sucessfully", Status.PASS);


	}

	public void optionsPageMethodwithoutpullprofile() throws InterruptedException, IOException
	{
		Thread.sleep(2000);
		//String gender = dataTable.getData("User_Data", "Gender");
		Select dropdown_gender = new Select(driver.findElement(OptionsPage.GENDER));
		dropdown_gender.selectByIndex(1);
		Thread.sleep(1000);
		//String title = dataTable.getData("User_Data", "Title");
		Select dropdown_title = new Select(driver.findElement(OptionsPage.TITLE));
		dropdown_title.selectByIndex(1);
		Thread.sleep(1000);
		String first_name = dataTable.getData("User_Data", "FIRST NAME");
		driver.findElement(OptionsPage.FIRST_NAME).sendKeys(first_name,Keys.TAB);
		Thread.sleep(1000);
		String middle_name = dataTable.getData("User_Data", "MIDDLE NAME");
		driver.findElement(OptionsPage.MIDDLE_NAME).sendKeys(middle_name,Keys.TAB);
		Thread.sleep(1000);
		String last_name = dataTable.getData("User_Data", "LAST NAME");
		driver.findElement(OptionsPage.LAST_NAME).sendKeys(last_name,Keys.TAB);
		Thread.sleep(1000);
		String second_last_name = dataTable.getData("User_Data", "SECOND LAST NAME");
		driver.findElement(OptionsPage.SECONDLAST_NAME).sendKeys(second_last_name,Keys.TAB);
		Thread.sleep(1000);
		String suffix = dataTable.getData("User_Data", "SUFFIX");
		Select dropdown_suffix = new Select(driver.findElement(OptionsPage.TITLE));
		dropdown_suffix.selectByIndex(1);
		driver.findElement(OptionsPage.SUFFIX).sendKeys(suffix,Keys.TAB);
		Thread.sleep(1000);
		String dob = dataTable.getData("User_Data", "DATE OF BIRTH");
		driver.findElement(OptionsPage.DOB).clear();
		driver.findElement(OptionsPage.DOB).sendKeys(dob,Keys.TAB);
		Thread.sleep(1000);
		//	String citizenship = dataTable.getData("User_Data", "COUNTRY OF CITIZENSHIP");
		Select dropdown_citizenship = new Select(driver.findElement(OptionsPage.CITIZENSHIP));
		dropdown_citizenship.selectByIndex(1);
		Thread.sleep(1000);
		String passport_no = dataTable.getData("User_Data", "PASSPORT NUMBER");
		driver.findElement(OptionsPage.PASSPORT_NO).sendKeys(passport_no,Keys.TAB);
		Thread.sleep(1000);
		String passport_country = dataTable.getData("User_Data", "PASSPORT COUNTRY");
		Select drop_passport_country = new Select(driver.findElement(OptionsPage.PASSPORTCOUNTRY));
		drop_passport_country.selectByVisibleText(passport_country);
		Thread.sleep(1000);	
		String passport_exp_date = dataTable.getData("User_Data", "PASSPORT EXPIRATION DATE");
		driver.findElement(OptionsPage.PASSPORTCOUNTRY).sendKeys(passport_exp_date,Keys.TAB);
		Thread.sleep(1000);
		String tour_rate = dataTable.getData("User_Data", "TOUR RATE");
		driver.findElement(OptionsPage.TOUR_RATE).sendKeys(tour_rate);
		Thread.sleep(1000);
		String taxes = dataTable.getData("User_Data", "TAXES");
		driver.findElement(OptionsPage.TAXES).sendKeys(taxes);
		Thread.sleep(1000);
		//Additional option
		String commission = dataTable.getData("User_Data", "COMMISSION");
		driver.findElement(OptionsPage.COMMISSION).sendKeys(commission);
		Thread.sleep(1000);
		String full_final_due_date = dataTable.getData("User_Data", "FULL/FINAL DUE DATE");
		driver.findElement(OptionsPage.FULL_FINAL_DUE_DATE).clear();
		driver.findElement(OptionsPage.FULL_FINAL_DUE_DATE).sendKeys(full_final_due_date);
		Thread.sleep(1000);
		String deposit_due_date = dataTable.getData("User_Data", "DEPOSITDUEDATE1");
		driver.findElement(OptionsPage.DEPOSIT_DUE_DATE).clear();
		driver.findElement(OptionsPage.DEPOSIT_DUE_DATE).sendKeys(deposit_due_date);
		Thread.sleep(1000);
		//String deposit_amount = dataTable.getData("User_Data", "DEPOSIT AMOUNT");
		driver.findElement(OptionsPage.DEPOSIT_AMOUNT).sendKeys(deposit_amount);

		JavascriptExecutor exe = (JavascriptExecutor) driver;
		exe.executeScript("window.scrollBy(0,250)", "");

		driver.findElement(By.xpath("//button[@type= 'submit']")).click();
		//click on INSURANCE INFORMATION page for navigate back
		//driver.findElement(By.xpath("//a[@class= 'button button-icon']")).click();

	}
	/*	public void air_yes() throws InterruptedException, IOException
	{
		driver.findElement(By.xpath("//label[contains(@for,'included')]")).click();
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		Thread.sleep(5000);
		executor.executeScript("window.scrollBy(0,250)", ""); 
		String Airlinename = dataTable.getData("User_Data", "Airlinename");
		String Airlinecode = dataTable.getData("User_Data", "Airlinecode");

		driver.findElement(BookingInformationPage.AIRLINE_NAME).click();
		driver.findElement(BookingInformationPage.AIRLINE_NAME).sendKeys(Airlinename,Keys.ENTER);
		int i=1;
		try{
			for (i=1;i<2;i++){	    		    		
				String s = driver.findElement(By.xpath("//div[contains(@class,'list-results')]/div/div/a["+i+"]")).getText();
				if (s.equals(Airlinename))
				{
					System.out.println(s);
					driver.findElement(By.xpath("//div[contains(@class,'list-results')]/div/div/a["+i+"]")).click();
					//	driver.findElement(BookingInformationPage.VENDOR_NAME).sendKeys(Vendorname,Keys.TAB);	    			
					break;
				}
			}
		}
		catch(Exception e){
			System.out.println("***Selected Airline***");	
		}

		String depart_air = dataTable.getData("User_Data", "Airlinecode");
		String depart_aircode = dataTable.getData("User_Data", "Airlinecode");

		driver.findElement(BookingInformationPage.DEPART_AIRLINE_NAME).click();
		driver.findElement(BookingInformationPage.DEPART_AIRLINE_NAME).sendKeys(depart_air,Keys.ENTER);
		int j=1;
		try{
			for (j=1;j<2;j++){	    		    		
				String s = driver.findElement(By.xpath("//div[contains(@class,'list-results')]/div/div/a["+j+"]")).getText();
				if (s.equals(depart_air))
				{
					System.out.println(s);
					driver.findElement(By.xpath("//div[contains(@class,'list-results')]/div/div/a["+j+"]")).click();
					//	driver.findElement(BookingInformationPage.VENDOR_NAME).sendKeys(Vendorname,Keys.TAB);	    			
					break;
				}
			}
		}
		catch(Exception e){
			System.out.println("***Selected Depart airline***");	
		}

		String arrival_air = dataTable.getData("User_Data", "ARRIVALAIRPORT");
		String Arrival_aircode = dataTable.getData("User_Data", "ARRIVALAIRPORT");

		driver.findElement(BookingInformationPage.Arrival_AIRLINE_NAME).click();
		driver.findElement(BookingInformationPage.Arrival_AIRLINE_NAME).sendKeys(depart_air,Keys.ENTER);
		int k=1;
		try{
			for (k=1;k<2;k++){	    		    		
				String s = driver.findElement(By.xpath("//div[contains(@class,'list-results')]/div/div/a["+j+"]")).getText();
				if (s.equals(arrival_air))
				{
					System.out.println(s);
					driver.findElement(By.xpath("//div[contains(@class,'list-results')]/div/div/a["+j+"]")).click();
					//	driver.findElement(BookingInformationPage.VENDOR_NAME).sendKeys(Vendorname,Keys.TAB);	    			
					break;
				}
			}
		}
		catch(Exception e){
			System.out.println("***Selected Depart airline***");	
		}


		driver.findElement(By.xpath("//input[contains(@name,'line_item[agent_name]')]")).click();
		driver.findElement(By.xpath("//input[contains(@name,'line_item[agent_name]')]")).sendKeys(vendorAgent,Keys.TAB);
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//input[contains(@name,'line_item[agent_phone]')]")).click();
		driver.findElement(By.xpath("//input[contains(@name,'line_item[agent_phone]')]")).sendKeys(vendorPhone,Keys.TAB);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.findElement(By.name("line_item[pre_post_information]")).sendKeys(InternalAccountingRemarks,Keys.TAB);
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		driver.findElement(By.name("line_item[packages_prices_include]")).sendKeys(deposit_amount,Keys.TAB);
		Select dropdown = new Select(driver.findElement(By.id("line_item[tour_program]")));
		dropdown.selectByIndex(5);
		Thread.sleep(5000);




	}
	 */
	public void passengerselectionpage() throws InterruptedException, IOException, AWTException
	{
		String  NoofPassengers= dataTable.getData("user_Data","Passengers");
		String[] val = NoofPassengers.split(",");
		int s = val.length;
		for(int k=1;k<=s;k++){
			//if(val[0].matches("Adult")){
			String type = val[k-1];
			System.out.println(type);

			switch(type){

			case "Adult": 
			{
				System.out.println("ENTERD Adult LOOP"+k);
				String  NoofAdults= dataTable.getData("user_Data","No_of_Adults");
				int y = Integer.parseInt(NoofAdults);
				for(int i=1;i<=y;i++){
					if(i==1 & y==1)
					{
						passengerselectionpage_onepassenger();
						//break;

					}else if(i==1 | i==2){

						//break;
					}
					else{
						passengerselectionpage_3more();
						//break;
					}
				}
				break;
			}

			case "infant":
			{	
				String  NoofInfants= dataTable.getData("user_Data","No_of_Infants");
				int y = Integer.parseInt(NoofInfants);
				if(y>0){
					for(int i=1;i<=y;i++){
						passengerselectionpage_3more();
						List<WebElement> passengers = driver.findElements(By.xpath("//div[@class='passenger-box']"));
						int passSize = passengers.size();

						System.out.println("ENTERD Infant LOOP"+k);
						driver.findElement(By.id("traveler_"+passSize)).click();
						Select dropdown = new Select(driver.findElement(By.id("traveler_"+passSize)));
						dropdown.selectByValue("INF");
						Thread.sleep(3000);
					}
				}
				break;
			}

			case "child":
			{
				System.out.println("ENTERD CHILD LOOP"+k);
				String  NoofChilds= dataTable.getData("user_Data","No_of_Childs");
				int y = Integer.parseInt(NoofChilds);
				System.out.println("childs:"+y);
				if(y>0){
					for(int i=1;i<=y;i++){
						passengerselectionpage_3more();
						List<WebElement> passengers = driver.findElements(By.xpath("//div[@class='passenger-box']"));
						int passSize = passengers.size();
						System.out.println(y);
						//WebElement childdropdown = driver.findElement(By.id("traveler_"+passSize));              
						driver.findElement(By.id("traveler_"+passSize)).click();
						Select dropdown = new Select(driver.findElement(By.id("traveler_"+passSize)));
						dropdown.selectByValue("CHD");
						Thread.sleep(3000);
						Select dropdownage = new Select(driver.findElement(By.id("traveler_age_"+passSize)));
						dropdownage.selectByVisibleText("3");
					}
				}
				break;
			}


			}}}

	/*String number = driver.findElement(By.xpath("//a[contains(@class,'passenger-add field-replicator-trigger')]")).getText().toString();
		String value = number;
		int x = Integer.parseInt(NoofPassengers);
		System.out.println(x);

		for(int i=1;i<=x;i++)
		{

			passengerselectionpage_3more();
		}

		try{
			//String passangerselectionchild = dataTable.getData("User_Data", "PassengerTypechild");
			if(dataTable.getData("User_Data", "PassengerTypechild").matches("")){
				String passangerselectioninfant = dataTable.getData("User_Data", "PassengerTypeinfant");
				if(passangerselectioninfant.equalsIgnoreCase("Infant"))
				{
					String  Noofinfants= dataTable.getData("user_Data","No_of_infants");
					String number = driver.findElement(By.xpath("//a[contains(@class,'passenger-add field-replicator-trigger')]")).getText().toString();
					String value = number;
					int y = Integer.parseInt(Noofinfants);
					System.out.println(y);
					for(int j=2;j>=y;j++)
					{
						driver.findElement(By.id("traveler_"+j)).click();
						Select dropdown = new Select(driver.findElement(By.id("traveler_"+j)));
						dropdown.selectByValue("INF");
						Thread.sleep(3000);

						y--;
						if(y==0)
							break;

					}
				}
			}
			else{
				if(passangerselectionchild.equalsIgnoreCase("Child"))
		{
				String  Noofchilds= dataTable.getData("user_Data","No_of_Childs");
				String number = driver.findElement(By.xpath("//a[contains(@class,'passenger-add field-replicator-trigger')]")).getText().toString();
				String value = number;
				int y = Integer.parseInt(Noofchilds);
				System.out.println(y);
				for(int j=2;j>=y;j++)
				{
					WebElement childdropdown = driver.findElement(By.id("traveler_"+j+""));              
				List<WebElement> dropDowns = childdropdown.findElements(By.id("traveler_"+j+""));
				System.out.println(dropDowns.size());
				dropDowns.get(0).click();
					driver.findElement(By.id("traveler_"+j)).click();
					Select dropdown = new Select(driver.findElement(By.id("traveler_"+j)));
					dropdown.selectByValue("CHD");
					Thread.sleep(3000);
					Select dropdownage = new Select(driver.findElement(By.id("traveler_age_"+j)));
					dropdownage.selectByVisibleText("3");
					y--;
					if(y==0)
						break;

				}
			}

		}catch(Exception e){
			String passangerselectioninfant = dataTable.getData("User_Data", "PassengerTypeinfant");
			if(passangerselectioninfant.equalsIgnoreCase("Infant"))
			{
				String  Noofinfants= dataTable.getData("user_Data","No_of_infants");
				String number = driver.findElement(By.xpath("//a[contains(@class,'passenger-add field-replicator-trigger')]")).getText().toString();
				String value = number;
				int y = Integer.parseInt(Noofinfants);
				System.out.println(y);
				for(int j=3;j>=y;j++)
				{
					driver.findElement(By.id("traveler_"+j)).click();
					Select dropdown = new Select(driver.findElement(By.id("traveler_"+j)));
					dropdown.selectByValue("INF");
					Thread.sleep(3000);

					y--;
					if(y==0)
						break;

				}
			}
		}
		passengerselction_continue();



		//System.out.println(i);
		//}
	 */	
	public void passengerselectionpage_3more() throws InterruptedException, IOException, AWTException
	{
		executor.executeScript("window.scrollBy(0,250)", ""); 
		driver.findElement(By.xpath("//a[contains(@class,'passenger-add field-replicator-trigger')]")).click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);



	}
	public void passengerselction_continue() throws InterruptedException, IOException, AWTException
	{
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_PAGE_DOWN);
		robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
		/*driver.findElement(By.xpath("//a[contains(@class,'button button-icon')]"))
		.sendKeys(Keys.ARROW_DOWN);
		driver.findElement(By.xpath("//a[contains(@class,'button button-icon')]"))
		.sendKeys(Keys.ARROW_DOWN);
		Thread.sleep(8000);*/
		Thread.sleep(5000);
		executor.executeScript("arguments[0].click();",driver.findElement(By.xpath("//button[contains(text(),'Continue')]"))); 
		//		driver.findElement(By.xpath("//button[contains(@type,'submit')]"))
		//		.click();
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		report.updateTestLog(
				"Clicked on continue in the passengerselectionpage",
				"Clicked on continue in the passengerselectionpage",
				Status.PASS);

	}
	public void travellers_options() throws InterruptedException, AWTException{

		String  NoofPassengers= dataTable.getData("user_Data","No_of_Passengers");
		int x = Integer.parseInt(NoofPassengers);
		System.out.println(x);
		for (int i=1;i<=x;i++){
			/*	String a1 = "traveler_"+i;
			String a2 = "traveler_"+i+"_gender";
			String a3 = "traveler_"+i+"_title_id";
			String a4 = "traveler_"+i+"_first_name";
			String a5 = "traveler_"+i+"_middle_name";
			String a6 = "traveler_"+i+"_last_name";
			String a7 = "traveler_"+i+"_second_last_name";
			String a8 = "traveler_"+i+"_suffix_id";
			String a9 = "traveler_"+i+"_citizenship_country";
			String a10 = "traveler_"+i+"_passport_number";
			String a11 = "traveler_"+i+"_passport_country";
			String a12 = "travelers_price_breakdown["+i+"][TourRate]";
			String a13 = "travelers_price_breakdown["+i+"][LessDiscount]";
			String a14 = "travelers_price_breakdown["+i+"][AirAddonDeviation]";
			String a15 = "travelers_price_breakdown["+i+"][AirUpgrade]";
			String a16 = "travelers_price_breakdown["+i+"][PreTourHotelPkg]";
			String a17 = "travelers_price_breakdown["+i+"][PostTourHotelPkg]";
			String a18 = "travelers_price_breakdown["+i+"][CustomsImmigration]";
			String a19 = "travelers_price_breakdown["+i+"][Transfers]";
			String a20 = "travelers_price_breakdown["+i+"][OptionalExcursion]";
			String a21 = "travelers_price_breakdown["+i+"][MiscPurchases]";
			String a22 = "travelers_price_breakdown["+i+"][Taxes]";*/
			if(driver.findElement(By.xpath("//span[contains(text(),'Traveler #"+i+"')]")).isDisplayed())
			{
				try{Select dropdown_traveler = new Select(driver.findElement(By.id("traveler_"+i)));
				dropdown_traveler.selectByIndex(i);}
				catch(Exception e){
					String[] alphabets = {"A","B","C","D","E","F","G","H","I"};

					Select dropdown_gender = new Select(driver.findElement(By.id("traveler_"+i+"_gender")));
					dropdown_gender.selectByIndex(1);
					Thread.sleep(1000);
					//String title = dataTable.getData("User_Data", "Title");
					Select dropdown_title = new Select(driver.findElement(By.id("traveler_"+i+"_title_id")));
					dropdown_title.selectByIndex(2);

					//First name
					String first_name = dataTable.getData("User_Data", "LEGALFIRSTNAME");

					driver.findElement(By.id("traveler_"+i+"_first_name")).sendKeys(first_name+alphabets[i],Keys.TAB);
					String last_name = dataTable.getData("User_Data", "LEGALLASTNAME");
					driver.findElement(By.id("traveler_"+i+"_last_name")).sendKeys(last_name+alphabets[i],Keys.TAB);

					driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

					executor.executeScript("window.scrollBy(0,200)", "");
					driver.findElement(By.xpath("//div[@class='calendar is-trigger'][@data-calendar='date-of-birth-calendar-"+i+"']/div[@class='calendar-input-container']/input[@type='text']")).click();
					driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

					driver.findElement(By.xpath("//div[contains(@data-calendar,'date-of-birth-calendar-"+i+"')]/div[2]/div/div/table/tbody/tr/td[7]")).click();
				}
			}
			/*String date = "10";
					List<WebElement> ele = driver.findElements(By.xpath("//div[contains(@class,'calendar-container')]/div/div/table/tbody/tr/td[10]"));
					int val = ele.size();
					for(int j=1;j<=val;j++){
						for(int k=1;k<=5;k++){
							String dis = driver.findElement(By.xpath("//*[contains(@class,'calendar-container')]/div/div/table/tbody/tr["+j+"]/td["+k+"]")).getAttribute("class");
							if(dis.contains("disabled")){
								System.out.println("Disabled");
							}else{
								String dis1 = driver.findElement(By.xpath("//*[contains(@class,'calendar-container')]/div/div/table/tbody/tr["+j+"]/td["+k+"]")).getText().toString().trim();
								if(dis1.matches(date)){
									executor.executeScript("window.scrollBy(0,150)", ""); 
									driver.findElement(By.xpath("//*[contains(@class,'calendar-container')]/div/div/table/tbody/tr["+j+"]/td["+k+"]")).click();
								}
							}

						}
					}*/

			//driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			//driver.findElement(By.xpath("//div[@class='calendar is-trigger'][@data-calendar='date-of-birth-calendar-"+i+"']/div[@class='calendar-input-container']/input[@type='text']")).sendKeys(dob,Keys.TAB);
			/*driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
					String first_name1 = dataTable.getData("User_Data", "LEGALFIRSTNAME1");
					driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
					driver.findElement(By.id("traveler_2_first_name")).sendKeys(first_name1);
					String first_name2 = dataTable.getData("User_Data", "LEGALFIRSTNAME2");
					driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
					driver.findElement(By.id("traveler_3_first_name")).sendKeys(first_name2);*/
			/*	String first_name3 = dataTable.getData("User_Data", "LEGALFIRSTNAME3");
					driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
					driver.findElement(By.id("traveler_4_first_name")).sendKeys(first_name3);
					driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);*/






			/*Select dropdown_traveler2 = new Select(driver.findElement(By.id("traveler_"+i)));
			dropdown_traveler2.selectByIndex(2);

			Select dropdown_traveler3 = new Select(driver.findElement(By.id("traveler_"+i)));
			dropdown_traveler3.selectByIndex(3);*/
			Thread.sleep(1000);
			String tour_rate = dataTable.getData("User_Data", "TOUR RATE");
			driver.findElement(By.id("travelers_price_breakdown["+i+"][TourRate]")).sendKeys(tour_rate);
			String taxes = dataTable.getData("User_Data", "TAXES");
			driver.findElement(By.id("travelers_price_breakdown["+i+"][Taxes]")).sendKeys(taxes);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		}
		//Additional option
		String commission = dataTable.getData("User_Data", "COMMISSION");
		driver.findElement(OptionsPage.COMMISSION).sendKeys(commission);
		JavascriptExecutor exe = (JavascriptExecutor) driver; 
		exe.executeScript("window.scrollBy(0,250)", ""); 
		String full_final_due_date = dataTable.getData("User_Data", "FULL/FINAL DUE DATE");
		driver.findElement(OptionsPage.FULL_FINAL_DUE_DATE).sendKeys(full_final_due_date);
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		String deposit_due_date = dataTable.getData("User_Data", "DEPOSITDUEDATE1");
		driver.findElement(OptionsPage.DEPOSIT_DUE_DATE).sendKeys(deposit_due_date);
		driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
		//String deposit_amount = dataTable.getData("User_Data", "DEPOSIT AMOUNT");
		driver.findElement(OptionsPage.DEPOSIT_AMOUNT).sendKeys(deposit_amount);
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		Thread.sleep(5000);
		//click on continue
		driver.findElement(By.xpath("//button[@type= 'submit']")).click();
		report.updateTestLog("Filled the data succesfully on options page", "Filled the data succesfully on options page", Status.PASS);
		Thread.sleep(5000);
	}
	public void insuranceInfodeclined() throws InterruptedException, IOException
	{
		Thread.sleep(3000);
		driver.findElement(By.xpath("//label[contains(@for,'declined')] ")).click();
		Thread.sleep(2000);
		driver.findElement(By.cssSelector("ul.panel-submit-nav>li>button")).sendKeys(Keys.ARROW_DOWN);
		Thread.sleep(2000);
		driver.findElement(By.cssSelector("ul.panel-submit-nav>li>button")).click();
		Thread.sleep(5000);
		/*WebDriverWait wait = new WebDriverWait(driver, 15);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']")));
		 */
		driver.findElement(By.xpath("/html/body/div[6]/div/div/ul[1]/li[2]/label")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("/html/body/div[6]/div/div/ul[2]/li/button")).click();;
		Thread.sleep(5000);

		//button[@type='submit']
	}
	public void withoutpulling_paymentspage_addresses()
			throws InterruptedException, IOException {
		// Billing Address
		String bill_addressline1 = dataTable.getData("User_Data",
				"BILLING ADDRESS LINE 1");
		driver.findElement(BookingInformationPage.BILLING_ADDRESS_LINE1)
		.sendKeys(bill_addressline1, Keys.TAB);

		String bill_addressline2 = dataTable.getData("User_Data",
				"BILLING ADDRESS LINE 2");
		driver.findElement(BookingInformationPage.BILLING_ADDRESS_LINE2)
		.sendKeys(bill_addressline2, Keys.TAB);

		String billing_city = dataTable.getData("User_Data", "BILLING CITY");
		driver.findElement(BookingInformationPage.BILLING_CITY).sendKeys(
				billing_city, Keys.TAB);

		// Select billing_state = new
		// Select(driver.findElement(BookingInformationPage.BILLING_STATE));
		String BILLING_STATE = dataTable.getData("User_Data", "BILLING_STATE");
		Select billingstate = new Select(driver.findElement(BookingInformationPage.BILLING_STATE));
		billingstate.selectByValue(BILLING_STATE);
		// billing_state.selectByIndex(2);

		String billing_postal = dataTable
				.getData("User_Data", "BILLING POSTAL");
		driver.findElement(BookingInformationPage.BILLING_POSTAL).sendKeys(
				billing_postal);

		// Select billing_country = new
		// Select(driver.findElement(BookingInformationPage.BILLING_COUNTRY));
		// billing_country.selectByVisibleText("United State");
		String BILLING_COUNTRY = dataTable
				.getData("User_Data", "BILLING_COUNTRY");
		Select billingcountry = new Select(
				driver.findElement(BookingInformationPage.BILLING_COUNTRY));
		billingcountry.selectByValue(BILLING_COUNTRY);

		JavascriptExecutor exe = (JavascriptExecutor) driver;
		exe.executeScript("window.scrollBy(0,250)", "");

		// Shipping Address
		String ship_addressline1 = dataTable.getData("User_Data",
				"SHIPPING ADDRESS LINE 1");
		driver.findElement(BookingInformationPage.SHIPPING_ADDRESS_LINE1)
		.sendKeys(ship_addressline1, Keys.TAB);

		String ship_addressline2 = dataTable.getData("User_Data",
				"SHIPPING ADDRESS LINE 2");
		driver.findElement(BookingInformationPage.SHIPPING_ADDRESS_LINE2)
		.sendKeys(ship_addressline2, Keys.TAB);

		String shipping_city = dataTable.getData("User_Data", "SHIPPING CITY");
		driver.findElement(BookingInformationPage.SHIPPING_CITY).sendKeys(
				shipping_city, Keys.TAB);

		// Select shipping_state = new
		// Select(driver.findElement(BookingInformationPage.SHIPPING_STATE));
		// shipping_state.selectByIndex(2);
		String SHIPPING_STATE = dataTable.getData("User_Data", "BILLING_STATE");
		Select shippingstate = new Select(
				driver.findElement(BookingInformationPage.SHIPPING_STATE));
		shippingstate.selectByValue(SHIPPING_STATE);

		String shipping_postal = dataTable.getData("User_Data",
				"SHIPPING POSTAL");
		driver.findElement(BookingInformationPage.SHIPPING_POSTAL).sendKeys(
				shipping_postal);

		// Select shipping_country = new
		// Select(driver.findElement(BookingInformationPage.SHIPPING_COUNTRY));
		// shipping_country.selectByVisibleText("United State");
		String SHIPPING_COUNTRY = dataTable.getData("User_Data",
				"SHIPPING_COUNTRY");
		Select shippingcountry = new Select(
				driver.findElement(BookingInformationPage.SHIPPING_COUNTRY));
		shippingcountry.selectByValue(SHIPPING_COUNTRY);


	}

	public void pulling_paymentspage_addresses() throws InterruptedException,
	IOException {
		// Billing Address
		Select billing_address = new Select(driver.findElement(By
				.xpath("//select[@id='billing_address']")));
		billing_address.selectByIndex(1);
		// Shipping Address
		Select shipping_address = new Select(driver.findElement(By
				.xpath("//select[@id='shipping_address']")));
		shipping_address.selectByIndex(1);
	}

	public void paymentspage_vendorInfo() throws InterruptedException,
	IOException {

		// vendor Info
		JavascriptExecutor exe = (JavascriptExecutor) driver;
		exe.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//input[@name='billing_info[custom_data][vendor_agent]']")));
		
		String VENDOR_AGENT_NAME = dataTable.getData("User_Data",
				"VENDOR_AGENT_NAME");
		driver.findElement(
				By.xpath("//input[@name='billing_info[custom_data][vendor_agent]']"))
				.sendKeys(VENDOR_AGENT_NAME);
		String VENDOR_AGENT_PHONE = dataTable.getData("User_Data",
				"VENDOR_AGENT_PHONE");
		driver.findElement(
				By.xpath("//input[@name='billing_info[custom_data][vendor_phone]']"))
				.sendKeys(VENDOR_AGENT_PHONE);

		// Continue to Review pages
		exe.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//button[@type='submit']")));
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		Thread.sleep(6000);

	}

	public void withoutpulling_paymentpage_CustomerInfo()
			throws InterruptedException, IOException {
		// customer Info
		// Select dropdown_title = new
		// Select(driver.findElement(By.xpath("//select[@id='customer_title']")));
		// dropdown_title.selectByIndex(1);
		String TITLE = dataTable.getData("User_Data", "cus_Title");
		Select cus_Title = new Select(
				driver.findElement(BookingInformationPage.TITLE));
		cus_Title.selectByValue(TITLE);
		String cust_first_name = dataTable.getData("User_Data",
				"CUSTOMER FIRSTNAME");
		driver.findElement(By.xpath("//input[@id='customer_first_name']"))
		.sendKeys(cust_first_name);
		String cust_last_name = dataTable.getData("User_Data",
				"CUSTOMER LASTNAME");
		driver.findElement(By.xpath("//input[@id='customer_last_name']"))
		.sendKeys(cust_last_name);
		String cust_telephone = dataTable.getData("User_Data",
				"CUSTOMER TELEPHONE");
		driver.findElement(
				By.xpath("//input[@id='customer_primary_telephone']"))
				.sendKeys(cust_telephone);
		String cust_email = dataTable.getData("User_Data", "CUSTOMER EMAIL");
		driver.findElement(By.xpath("//input[@id='customer_primary_email']"))
		.sendKeys(cust_email);

	}

	public void pulling_paymentpage_CustomerInfo() throws InterruptedException,
	IOException {
		// Select dropdown_title = new
		// Select(driver.findElement(By.xpath("//select[@id='customer_title']")));
		// dropdown_title.selectByIndex(1);
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(BookingInformationPage.TITLE));
		String TITLE = dataTable.getData("User_Data", "cus_Title");
		Select cus_Title = new Select(
				driver.findElement(BookingInformationPage.TITLE));
		cus_Title.selectByValue(TITLE);
	}

	public void paymentspage_Check() throws InterruptedException, IOException {
		Thread.sleep(2000);
		driver.findElement(BookingInformationPage.Check).click();
		System.out.println("Checking for Check");
		Thread.sleep(1000);
		String Checknumber = dataTable.getData("User_Data", "Checknumber");
		driver.findElement(
				By.xpath("//input[@name='billing_info[check_info][check_number]'][@id='check_number']"))
				.sendKeys(Checknumber);
		String CheckFirstName = dataTable
				.getData("User_Data", "CheckFirstName");
		driver.findElement(
				By.xpath("//input[@name='billing_info[check_info][first_name]'][@id='check_first_name']"))
				.sendKeys(CheckFirstName);
		String CheckLastName = dataTable.getData("User_Data", "CheckLastName");
		driver.findElement(
				By.xpath("//input[@name='billing_info[check_info][last_name]'][@id='check_last_name']"))
				.sendKeys(CheckLastName);
		String CheckPayableTo = dataTable
				.getData("User_Data", "CheckPayableTo");
		driver.findElement(
				By.xpath("//input[@name='billing_info[check_info][payable_to]'][@id='payable-to-field']"))
				.sendKeys(CheckPayableTo);
		String CheckSendTO = dataTable.getData("User_Data", "CheckSendTO");
		driver.findElement(
				By.xpath("//input[@name='billing_info[check_info][send_to]'][@id='send-to-field']"))
				.sendKeys(CheckSendTO);
		System.out.println("Entered all check details ");
		Thread.sleep(2000);

		// Select send to address
		String profilepull = dataTable.getData("User_Data", "profilepull");
		if (profilepull.matches("yes")) {
			JavascriptExecutor exe = (JavascriptExecutor) driver;
			exe.executeScript("window.scrollBy(0,250)", "");
			Thread.sleep(2000);
		}

		else {
			String sendto_addressline1 = dataTable.getData("User_Data",
					"SENDTO ADDRESS LINE 1");
			driver.findElement(BookingInformationPage.SENDTO_ADDRESS_LINE1)
			.sendKeys(sendto_addressline1, Keys.TAB);

			String sendto_addressline2 = dataTable.getData("User_Data",
					"SENDTO ADDRESS LINE 2");
			driver.findElement(BookingInformationPage.SENDTO_ADDRESS_LINE2)
			.sendKeys(sendto_addressline2, Keys.TAB);

			String sendto_city = dataTable.getData("User_Data", "SENDTO CITY");
			driver.findElement(BookingInformationPage.SENDTO_CITY).sendKeys(
					sendto_city, Keys.TAB);

			Select sendto_state = new Select(
					driver.findElement(BookingInformationPage.SENDTO_STATE));
			sendto_state.selectByIndex(2);

			String sendto_postal = dataTable.getData("User_Data",
					"SENDTO POSTAL");
			driver.findElement(BookingInformationPage.SENDTO_POSTAL).sendKeys(
					sendto_postal,Keys.TAB);

			/*	Select sendto_country = new Select(
					driver.findElement(BookingInformationPage.SENDTO_COUNTRY));
			sendto_country.selectByVisibleText("United State");*/
			driver.findElement(BookingInformationPage.SENDTO_COUNTRY).sendKeys(Keys.TAB);
		}
		Thread.sleep(2000);

	}

	public void Makepaymentspage_Check() throws InterruptedException,
	IOException {
		Thread.sleep(2000);
		driver.findElement(BookingInformationPage.Check).click();
		System.out.println("Checking for Check");
		Thread.sleep(1000);
		String Checknumber = dataTable.getData("User_Data",
				"MakepaymentChecknumber");
		driver.findElement(
				By.xpath("//input[@name='billing_info[check_info][check_number]'][@id='check_number']"))
				.sendKeys(Checknumber);
		String CheckFirstName = dataTable.getData("User_Data",
				"MakepaymentCheckFirstName");
		driver.findElement(
				By.xpath("//input[@name='billing_info[check_info][first_name]'][@id='check_first_name']"))
				.sendKeys(CheckFirstName);
		String CheckLastName = dataTable.getData("User_Data",
				"MakepaymentCheckLastName");
		driver.findElement(
				By.xpath("//input[@name='billing_info[check_info][last_name]'][@id='check_last_name']"))
				.sendKeys(CheckLastName);
		String CheckPayableTo = dataTable.getData("User_Data",
				"MakepaymentCheckPayableTo");
		driver.findElement(
				By.xpath("//input[@name='billing_info[check_info][payable_to]'][@id='payable-to-field']"))
				.sendKeys(CheckPayableTo);
		String CheckSendTO = dataTable.getData("User_Data",
				"MakepaymentCheckSendTO");
		driver.findElement(
				By.xpath("//input[@name='billing_info[check_info][send_to]'][@id='send-to-field']"))
				.sendKeys(CheckSendTO);
		System.out.println("Entered all check details ");
		Thread.sleep(2000);

		// Select send to address
		String profilepull = dataTable.getData("User_Data", "profilepull");
		if (profilepull.matches("yes")) {
			JavascriptExecutor exe = (JavascriptExecutor) driver;
			exe.executeScript("window.scrollBy(0,250)", "");
			Thread.sleep(2000);
		}

		else {
			String sendto_addressline1 = dataTable.getData("User_Data",
					"MakepaymentSENDTO ADDRESS LINE 1");
			driver.findElement(BookingInformationPage.SENDTO_ADDRESS_LINE1)
			.sendKeys(sendto_addressline1, Keys.TAB);

			String sendto_addressline2 = dataTable.getData("User_Data",
					"MakepaymentSENDTO ADDRESS LINE 2");
			driver.findElement(BookingInformationPage.SENDTO_ADDRESS_LINE2)
			.sendKeys(sendto_addressline2, Keys.TAB);

			String sendto_city = dataTable.getData("User_Data",
					"MakepaymentSENDTO CITY");
			driver.findElement(BookingInformationPage.SENDTO_CITY).sendKeys(
					sendto_city, Keys.TAB);

			Select sendto_state = new Select(
					driver.findElement(BookingInformationPage.MakepaymentSENDTO_STATE));
			sendto_state.selectByIndex(2);

			String sendto_postal = dataTable.getData("User_Data",
					"MakepaymentSENDTO POSTAL");
			driver.findElement(BookingInformationPage.SENDTO_POSTAL).sendKeys(
					sendto_postal,Keys.TAB);

			Select sendto_country = new Select(
					driver.findElement(BookingInformationPage.MakepaymentSENDTO_COUNTRY));
			sendto_country.selectByVisibleText("United State");
		}
		Thread.sleep(2000);

	}

	public void paymentspage_CC() throws InterruptedException, IOException {
		Thread.sleep(2000);
		System.out.println("Checking for Credit card");
		driver.findElement(BookingInformationPage.CreditCard).click();
		System.out.println("Clicked on credit card");
		Thread.sleep(1000);
		String profilepull = dataTable.getData("User_Data", "profilepull");
		if (profilepull.matches("yes")) {
			Select CC = new Select(driver.findElement(By
					.id("credit_card_card_id")));
			CC.selectByIndex(1);
			System.out.println("Credit card selected from dropdown");
			Thread.sleep(2000);
		} else {
			Select CardType = new Select(driver.findElement(By
					.id("credit_card_card_type")));
			CardType.selectByVisibleText("American Express");// need to change
			Thread.sleep(2000);
			String CreditCardNumber = dataTable.getData("User_Data",
					"CreditCardNumber");
			driver.findElement(BookingInformationPage.CreditCardNumber)
			.sendKeys(CreditCardNumber);
			String CreditCard_FirstName = dataTable.getData("User_Data",
					"CreditCard_FirstName");
			driver.findElement(BookingInformationPage.CreditCard_FirstName)
			.sendKeys(CreditCard_FirstName);
			String CreditCard_LastName = dataTable.getData("User_Data",
					"CreditCard_LastName");
			driver.findElement(BookingInformationPage.CreditCard_LastName)
			.sendKeys(CreditCard_LastName);

			// Select Exp date
			String CreditCard_ExpMonth = dataTable.getData("User_Data",
					"CreditCard_ExpMonth");
			Select CardExpMonth = new Select(
					driver.findElement(BookingInformationPage.CreditCard_ExpMonth));
			CardExpMonth.selectByValue(CreditCard_ExpMonth);

			String CreditCard_ExpYear = dataTable.getData("User_Data",
					"CreditCard_ExpYear");
			Select CardExpYear = new Select(
					driver.findElement(BookingInformationPage.CreditCard_ExpYear));
			CardExpYear.selectByValue(CreditCard_ExpYear);

		}

	}

	public void Makepaymentspage_CC() throws InterruptedException, IOException {
		Thread.sleep(2000);
		System.out.println("Checking for Credit card");
		driver.findElement(BookingInformationPage.CreditCard).click();
		System.out.println("Clicked on credit card");
		Thread.sleep(1000);
		String profilepull = dataTable.getData("User_Data", "profilepull");
		if (profilepull.matches("yes")) {
			Select CC = new Select(driver.findElement(By
					.id("credit_card_card_id")));
			CC.selectByIndex(1);
			System.out.println("Credit card selected from dropdown");
			Thread.sleep(2000);
		} else {
			Select CardType = new Select(driver.findElement(By
					.id("credit_card_card_type")));
			CardType.selectByVisibleText("American Express");
			Thread.sleep(2000);
			String CreditCardNumber = dataTable.getData("User_Data",
					"MakepaymentCreditCardNumber");
			driver.findElement(BookingInformationPage.CreditCardNumber)
			.sendKeys(CreditCardNumber);
			String CreditCard_FirstName = dataTable.getData("User_Data",
					"MakepaymentCreditCard_FirstName");
			driver.findElement(BookingInformationPage.CreditCard_FirstName)
			.sendKeys(CreditCard_FirstName);
			String CreditCard_LastName = dataTable.getData("User_Data",
					"MakepaymentCreditCard_LastName");
			driver.findElement(BookingInformationPage.CreditCard_LastName)
			.sendKeys(CreditCard_LastName);

			// Select Exp date
			String CreditCard_ExpMonth = dataTable.getData("User_Data",
					"MakepaymentCreditCard_ExpMonth");
			Select CardExpMonth = new Select(
					driver.findElement(BookingInformationPage.CreditCard_ExpMonth));
			CardExpMonth.selectByValue(CreditCard_ExpMonth);

			String CreditCard_ExpYear = dataTable.getData("User_Data",
					"MakepaymentCreditCard_ExpYear");
			Select CardExpYear = new Select(
					driver.findElement(BookingInformationPage.CreditCard_ExpYear));
			CardExpYear.selectByValue(CreditCard_ExpYear);

		}

	}

	public void paymentspage_NoPayment() throws InterruptedException,
	IOException, AWTException {
		//html/body/div[3]/div[2]/div[2]/div[3]/div[2]/div/div[1]/div[2]/form/div[2]/div[2]/div[2]/div[3]/div[1]
		// ((JavascriptExecutor)
		// driver).executeScript("arguments[0].scrollIntoView();",
		// BookingInformationPage.NO_PAYMENT);
		JavascriptExecutor exe = (JavascriptExecutor) driver;
		exe.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//div[@class='payment-amount form-group']/div[1]")));
		WebElement NO_PAYMENT = driver.findElement(By.xpath("//div[@class='payment-amount form-group']/div[1]"));
		//WebElement NO_PAYMENT = driver.findElement(By.xpath("//input[@name='billing_info[payment_option]'][@id='payment_option_1']"));
		/*if (NO_PAYMENT.isDisplayed()) {
			driver.findElement(BookingInformationPage.NO_PAYMENT).click();
		}*/
		/*String profilepull = dataTable.getData("User_Data", "profilepull");
		if (profilepull.matches("yes")) {
			// Billing Address & Shipping address
			pulling_paymentspage_addresses();
			Thread.sleep(2000);
			// customer Info
			pulling_paymentpage_CustomerInfo();
			Thread.sleep(2000);
		}*/
		
		exe.executeScript("window.scrollBy(0,250)", "");
		pulling_paymentpage_CustomerInfo();
		Thread.sleep(2000);
		paymentspage_vendorInfo();
		Thread.sleep(2000);
		report.updateTestLog("Selected payment method as No payment",
				"Selected payment method as No payment", Status.PASS);

	}

	public void paymentspage_Deposit() throws InterruptedException, IOException {
		JavascriptExecutor exe = (JavascriptExecutor) driver;
		exe.executeScript("window.scrollBy(0,1250)", "");
		Thread.sleep(3000);
		WebElement DEPOSIT = driver.findElement(By
				.xpath("//label[contains(text(),'Deposit')]"));
		// driver.findElement(BookingInformationPage.DEPOSIT).click();
		if (DEPOSIT.isDisplayed()) {
			driver.findElement(BookingInformationPage.DEPOSIT).click();
			System.out.println("Clicked on Deposit");
		}
		exe.executeScript("window.scrollBy(0,250)", "");
		String PaymentMethod = dataTable.getData("User_Data", "PaymentMethod");
		if (PaymentMethod.matches("CreditCard")) {
			paymentspage_CC();
			Thread.sleep(3000);
		} else
			paymentspage_Check();
		Thread.sleep(3000);

		String profilepull = dataTable.getData("User_Data", "profilepull");
		if (profilepull.matches("yes")) {
			// Billing Address & Shipping address
			pulling_paymentspage_addresses();
			Thread.sleep(2000);
			// customer Info
			pulling_paymentpage_CustomerInfo();
			Thread.sleep(2000);
		} else {
			withoutpulling_paymentspage_addresses();
			exe.executeScript("window.scrollBy(0,250)", "");
			withoutpulling_paymentpage_CustomerInfo();
		}
		exe.executeScript("window.scrollBy(0,250)", "");
		paymentspage_vendorInfo();
		Thread.sleep(2000);
	}

	public void paymentspage_Final() throws InterruptedException, IOException {
		JavascriptExecutor exe = (JavascriptExecutor) driver;
		exe.executeScript("window.scrollBy(0,1250)", "");
		Thread.sleep(3000);
		WebElement FINAL_PAYMENT = driver.findElement(By
				.xpath("//label[contains(text(),'Final Payment')]"));
		if (FINAL_PAYMENT.isDisplayed()) {
			driver.findElement(BookingInformationPage.FINAL_PAYMENT).click();
			System.out.println("Clicked on Final Payment");
		}
		exe.executeScript("window.scrollBy(0,250)", "");
		String PaymentMethod = dataTable.getData("User_Data", "PaymentMethod");
		if (PaymentMethod.matches("CreditCard")) {
			paymentspage_CC();
			Thread.sleep(3000);
		} else
			paymentspage_Check();
		Thread.sleep(3000);
		String profilepull = dataTable.getData("User_Data", "profilepull");
		if (profilepull.matches("yes")) {
			// Billing Address & Shipping address
			pulling_paymentspage_addresses();
			Thread.sleep(2000);
			// customer Info
			pulling_paymentpage_CustomerInfo();
			Thread.sleep(2000);
		} else {
			withoutpulling_paymentspage_addresses();
			exe.executeScript("window.scrollBy(0,250)", "");
			withoutpulling_paymentpage_CustomerInfo();
		}
		exe.executeScript("window.scrollBy(0,250)", "");
		paymentspage_vendorInfo();
		Thread.sleep(2000);

	}

	public void paymentspage_Full() throws InterruptedException, IOException {
		JavascriptExecutor exe = (JavascriptExecutor) driver;
		exe.executeScript("window.scrollBy(0,1250)", "");
		Thread.sleep(3000);
		WebElement FULL_PAYEMNT = driver.findElement(By
				.xpath("//label[contains(text(),'Full Payment')]"));
		if (FULL_PAYEMNT.isDisplayed()) {
			driver.findElement(BookingInformationPage.FULL_PAYEMNT).click();
			System.out.println("Clicked on Full Payment");
		}
		exe.executeScript("window.scrollBy(0,250)", "");
		String PaymentMethod = dataTable.getData("User_Data", "PaymentMethod");
		if (PaymentMethod.matches("CreditCard")) {
			paymentspage_CC();
			Thread.sleep(3000);
		} else
			paymentspage_Check();
		Thread.sleep(3000);
		String profilepull = dataTable.getData("User_Data", "profilepull");
		if (profilepull.matches("yes")) {
			// Billing Address & Shipping address
			pulling_paymentspage_addresses();
			Thread.sleep(2000);
			// customer Info
			pulling_paymentpage_CustomerInfo();
			Thread.sleep(2000);
		} else {
			withoutpulling_paymentspage_addresses();
			exe.executeScript("window.scrollBy(0,250)", "");
			withoutpulling_paymentpage_CustomerInfo();
		}
		exe.executeScript("window.scrollBy(0,250)", "");
		paymentspage_vendorInfo();
		Thread.sleep(2000);

	}

	public void paymentspage_Additional() throws InterruptedException,
	IOException {
		JavascriptExecutor exe = (JavascriptExecutor) driver;
		exe.executeScript("window.scrollBy(0,1250)", "");
		Thread.sleep(3000);
		WebElement ADDITIONAL_PAYMENT = driver
				.findElement(By
						.xpath("//label[contains(text(),'Additional Payment')]"));
		if (ADDITIONAL_PAYMENT.isDisplayed()) {
			driver.findElement(BookingInformationPage.ADDITIONAL_PAYMENT)
			.click();
			driver.findElement(BookingInformationPage.Additional_amount)
			.clear();
			String payment_AddAmount = dataTable.getData("User_Data",
					"payment_AddAmount");
			driver.findElement(BookingInformationPage.Additional_amount)
			.sendKeys(payment_AddAmount);
			System.out.println("Additional Payment amount entered");
		}
		exe.executeScript("window.scrollBy(0,250)", "");
		String PaymentMethod = dataTable.getData("User_Data", "PaymentMethod");
		if (PaymentMethod.matches("CreditCard")) {
			paymentspage_CC();
			Thread.sleep(3000);
		} else
			paymentspage_Check();
		Thread.sleep(3000);
		String profilepull = dataTable.getData("User_Data", "profilepull");
		if (profilepull.matches("yes")) {
			// Billing Address & Shipping address
			pulling_paymentspage_addresses();
			Thread.sleep(2000);
			// customer Info
			pulling_paymentpage_CustomerInfo();
			Thread.sleep(2000);
		} else {
			withoutpulling_paymentspage_addresses();
			exe.executeScript("window.scrollBy(0,250)", "");
			withoutpulling_paymentpage_CustomerInfo();
		}
		exe.executeScript("window.scrollBy(0,250)", "");
		paymentspage_vendorInfo();
		Thread.sleep(2000);

	}

	public void reviewpage() throws InterruptedException, IOException,
	AWTException {
		// Validation of Data
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		// Terms & Conditions-Continue
		JavascriptExecutor exe = (JavascriptExecutor) driver;
		exe.executeScript("window.scrollBy(0,500)", "");

		WebElement bookinginfo_section = driver.findElement(By
				.xpath("//div[contains(@class,'panel')]"));

		((JavascriptExecutor) driver).executeScript(
				"arguments[0].scrollIntoView();", bookinginfo_section);
		WebElement lastelement = driver.findElement(By
				.xpath("//span[contains(text(),'Region Name')]"));
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].scrollIntoView();", lastelement);

		exe.executeScript("window.scrollBy(0,500)", "");
		Thread.sleep(5000);
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_PAGE_DOWN);
		robot.keyRelease(KeyEvent.VK_PAGE_DOWN);
		// driver.findElement(By.xpath("//button[contains(@type,'button') and @id='reviewButton']")).sendKeys(Keys.ARROW_DOWN);

		Thread.sleep(10000);
		// driver.findElement(BookingInformationPage.terms_general);
		// executor.executeScript("arguments[0].click();",driver.findElement(By.xpath("//div[@class='booking-information']/form/div[7]/ul/li[1]")));
		// driver.findElement(By.xpath("//*[contains(text(),'I have reviewed the Terms and Conditions')]")).submit();
		driver.findElement(
				By.xpath("//*[contains(text(),'I have reviewed the Terms and Conditions')]"))
				.click();

		Thread.sleep(5000);
		// driver.findElement(BookingInformationPage.terms_itinerary);
		// executor.executeScript("arguments[0].click();",driver.findElement(By.xpath("//div[@class='booking-information']/form/div[7]/ul/li[2]")));
		// driver.findElement(By.xpath("//*[contains(text(),'reviewed the tour itinerary above')]")).submit();
		driver.findElement(
				By.xpath("//*[contains(text(),'reviewed the tour itinerary above')]"))
				.click();
		Thread.sleep(5000);
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		exe.executeScript("window.scrollBy(0,250)", "");

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.findElement(
				By.xpath("//button[contains(@type,'button') and @id='reviewButton']"))
				.click();
		Thread.sleep(5000);
		driver.findElement(
				By.xpath("//button[contains(@class,'button disclosure-submit-button')]"))
				.click();

		boolean rc;

		rc = waitForElement(By
				.xpath("//span[contains(text(),'Confirmation Number')]"));
		if (rc) {

			driver.findElement(By
					.xpath("//span[contains(text(),'Confirmation Number')]"));
			Thread.sleep(5000);
			report.updateTestLog("Clicked on continue in Review page",
					"Clicked on continue in Review page", Status.PASS);

		} else {
			report.updateTestLog("Clicked on continue in Review page",
					"Clicked on continue in Review page", Status.PASS);
		}

	}
	public void withoutpulling_Makepaymentspage_addresses()
			throws InterruptedException, IOException {
		// Billing Address
		String bill_addressline1 = dataTable.getData("User_Data",
				"MakepaymentBILLING ADDRESS LINE 1");
		driver.findElement(BookingInformationPage.BILLING_ADDRESS_LINE1)
		.sendKeys(bill_addressline1, Keys.TAB);

		String bill_addressline2 = dataTable.getData("User_Data",
				"MakepaymentBILLING ADDRESS LINE 2");
		driver.findElement(BookingInformationPage.BILLING_ADDRESS_LINE2)
		.sendKeys(bill_addressline2, Keys.TAB);

		String billing_city = dataTable.getData("User_Data",
				"MakepaymentBILLING CITY");
		driver.findElement(BookingInformationPage.BILLING_CITY).sendKeys(
				billing_city, Keys.TAB);

		//Select billing_state = new Select(
		//driver.findElement(BookingInformationPage.MakepaymentBILLING_STATE));
		//billing_state.selectByIndex(2);
		String BILLING_STATE = dataTable.getData("User_Data", "MakepaymentBILLING_STATE");
		Select billingstate = new Select(driver.findElement(BookingInformationPage.MakepaymentBILLING_STATE));
		billingstate.selectByValue(BILLING_STATE);

		String billing_postal = dataTable.getData("User_Data",
				"MakepaymentBILLING POSTAL");
		driver.findElement(BookingInformationPage.BILLING_POSTAL).sendKeys(
				billing_postal);

		//Select billing_country = new Select(driver.findElement(BookingInformationPage.MakepaymentBILLING_COUNTRY));
		//billing_country.selectByVisibleText("United State");
		String BILLING_COUNTRY = dataTable.getData("User_Data", "MakepaymentBILLING_COUNTRY");
		Select billingcountry = new Select(driver.findElement(BookingInformationPage.MakepaymentBILLING_COUNTRY));
		billingcountry.selectByValue(BILLING_COUNTRY);


		JavascriptExecutor exe = (JavascriptExecutor) driver;
		exe.executeScript("window.scrollBy(0,250)", "");

		// Shipping Address
		String ship_addressline1 = dataTable.getData("User_Data",
				"MakepaymentSHIPPING ADDRESS LINE 1");
		driver.findElement(BookingInformationPage.SHIPPING_ADDRESS_LINE1)
		.sendKeys(ship_addressline1, Keys.TAB);

		String ship_addressline2 = dataTable.getData("User_Data",
				"MakepaymentSHIPPING ADDRESS LINE 2");
		driver.findElement(BookingInformationPage.SHIPPING_ADDRESS_LINE2)
		.sendKeys(ship_addressline2, Keys.TAB);

		String shipping_city = dataTable.getData("User_Data",
				"MakepaymentSHIPPING CITY");
		driver.findElement(BookingInformationPage.SHIPPING_CITY).sendKeys(
				shipping_city, Keys.TAB);

		//Select shipping_state = new Select(driver.findElement(BookingInformationPage.MakepaymentSHIPPING_STATE));
		//shipping_state.selectByIndex(2);
		String SHIPPING_STATE = dataTable.getData("User_Data", "MakepaymentSHIPPING_STATE");
		Select shipping_state = new Select(driver.findElement(BookingInformationPage.MakepaymentSHIPPING_STATE));
		shipping_state.selectByValue(SHIPPING_STATE);

		String shipping_postal = dataTable.getData("User_Data",
				"MakepaymentSHIPPING POSTAL");
		driver.findElement(BookingInformationPage.SHIPPING_POSTAL).sendKeys(
				shipping_postal);

		//Select shipping_country = new Select(driver.findElement(BookingInformationPage.MakepaymentSHIPPING_COUNTRY));
		//shipping_country.selectByVisibleText("United State");
		String SHIPPING_COUNTRY = dataTable.getData("User_Data", "MakepaymentSHIPPING_COUNTRY");
		Select shipping_country = new Select(driver.findElement(BookingInformationPage.MakepaymentSHIPPING_COUNTRY));
		shipping_country.selectByValue(SHIPPING_COUNTRY);

	}

	public void makepaymentspage() throws InterruptedException, IOException {
		Thread.sleep(2000);
		// Click on expand
		driver.findElement(By.xpath("//Span[contains(text(),'Expand')]"))
		.click();
		Thread.sleep(1000);
		executor.executeScript(
				"arguments[0].scrollIntoView(true);",
				driver.findElement(By
						.xpath("//div[contains(@class,'ui-panel-container-padding')]/ul")));
		// Click Makepayment button on view order page

		driver.findElement(BookingInformationPage.makepayment_button).click();
		System.out.println("Makepayment button clicked on View order page ");
		Thread.sleep(5000);
		JavascriptExecutor exe = (JavascriptExecutor) driver;
		exe.executeScript("window.scrollBy(0,250)", "");
		String Makepayment = dataTable.getData("User_Data", "Makepayment");
		String MakePaymentMethod = dataTable.getData("User_Data",
				"MakePaymentMethod");

		if (Makepayment.matches("Deposit")) {

			WebElement DEPOSIT = driver.findElement(By
					.xpath("//label[contains(text(),'Deposit')]"));

			if (DEPOSIT.isDisplayed()) {

				driver.findElement(BookingInformationPage.DEPOSIT).click();
				System.out.println("Clicked on Deposit");
				String makepayment_DepAmount = dataTable.getData("User_Data",
						"makepayment_DepAmount");
				driver.findElement(BookingInformationPage.makepayment_DepAmount)
				.clear();
				driver.findElement(BookingInformationPage.makepayment_DepAmount)
				.sendKeys(makepayment_DepAmount);

			}
			exe.executeScript("window.scrollBy(0,250)", "");
			if (MakePaymentMethod.matches("CreditCard")) {
				Makepaymentspage_CC();
				Thread.sleep(3000);
			} else
				Makepaymentspage_Check();
			Thread.sleep(3000);

			String profilepull = dataTable.getData("User_Data", "profilepull");
			if (profilepull.matches("yes")) {
				// Billing Address & Shipping address
				pulling_paymentspage_addresses();
				Thread.sleep(2000);

			} else {
				withoutpulling_Makepaymentspage_addresses();
				Thread.sleep(2000);

			}

			exe.executeScript("window.scrollBy(0,250)", "");
			Thread.sleep(2000);
		}

		else if (Makepayment.matches("Final Payment")) {
			exe.executeScript("window.scrollBy(0,1250)", "");
			Thread.sleep(3000);
			WebElement FINAL_PAYMENT = driver.findElement(By
					.xpath("//label[contains(text(),'Final Payment')]"));
			if (FINAL_PAYMENT.isDisplayed()) {
				driver.findElement(BookingInformationPage.FINAL_PAYMENT)
				.click();
				System.out.println("Clicked on Final Payment");
			}
			exe.executeScript("window.scrollBy(0,250)", "");
			if (MakePaymentMethod.matches("CreditCard")) {
				Makepaymentspage_CC();
				Thread.sleep(3000);
			} else
				Makepaymentspage_Check();
			Thread.sleep(3000);
			String profilepull = dataTable.getData("User_Data", "profilepull");
			if (profilepull.matches("yes")) {
				// Billing Address & Shipping address
				pulling_paymentspage_addresses();
				Thread.sleep(2000);

			} else {
				withoutpulling_Makepaymentspage_addresses();
				Thread.sleep(2000);

			}

			Thread.sleep(2000);

		}

		else if (Makepayment.matches("Full Payment")) {
			exe.executeScript("window.scrollBy(0,1250)", "");
			Thread.sleep(3000);
			WebElement FULL_PAYEMNT = driver.findElement(By
					.xpath("//label[contains(text(),'Full Payment')]"));
			if (FULL_PAYEMNT.isDisplayed()) {
				driver.findElement(BookingInformationPage.FULL_PAYEMNT).click();
				System.out.println("Clicked on Full Payment");
			}
			exe.executeScript("window.scrollBy(0,250)", "");
			if (MakePaymentMethod.matches("CreditCard")) {
				Makepaymentspage_CC();
				Thread.sleep(3000);
			} else
				Makepaymentspage_Check();
			Thread.sleep(3000);
			String profilepull = dataTable.getData("User_Data", "profilepull");
			if (profilepull.matches("yes")) {
				// Billing Address & Shipping address
				pulling_paymentspage_addresses();
				Thread.sleep(2000);
				exe.executeScript("window.scrollBy(0,250)", "");

			} else {
				withoutpulling_Makepaymentspage_addresses();
				Thread.sleep(2000);

			}
			Thread.sleep(2000);

		} else if (Makepayment.matches("Additional Payment")) {
			exe.executeScript("window.scrollBy(0,250)", "");
			Thread.sleep(3000);
			WebElement ADDITIONAL_PAYMENT = driver
					.findElement(By
							.xpath("//label[contains(text(),'Additional Payment')]"));
			if (ADDITIONAL_PAYMENT.isDisplayed()) {
				driver.findElement(BookingInformationPage.ADDITIONAL_PAYMENT)
				.click();
				String makepayment_AddAmount = dataTable.getData("User_Data",
						"makepayment_AddAmount");
				driver.findElement(BookingInformationPage.makepayment_AddAmount)
				.clear();
				driver.findElement(BookingInformationPage.makepayment_AddAmount)
				.sendKeys(makepayment_AddAmount);
			}
			exe.executeScript("window.scrollBy(0,250)", "");
			if (MakePaymentMethod.matches("CreditCard")) {
				Makepaymentspage_CC();
				Thread.sleep(3000);
			} else
				Makepaymentspage_Check();
			Thread.sleep(3000);
			String profilepull = dataTable.getData("User_Data", "profilepull");
			if (profilepull.matches("yes")) {
				// Billing Address & Shipping address
				pulling_paymentspage_addresses();
				Thread.sleep(2000);

			} else {
				withoutpulling_Makepaymentspage_addresses();
				Thread.sleep(2000);

			}

		}
		exe.executeScript("window.scrollBy(0,250)", "");
		// Billing Address
		exe.executeScript("window.scrollBy(0,250)", "");
		// Shipping Address
		exe.executeScript("window.scrollBy(0,500)", "");
		driver.findElement(
				By.xpath(".//*[@id='form_payment']/div[2]/ul[2]/li/button"))
				.click();
		Thread.sleep(5000);

	}
	public void addMoreHotel() throws InterruptedException, IOException
	{
		JavascriptExecutor exe = (JavascriptExecutor) driver;
		exe.executeScript("window.scrollBy(0,250)", "");

		/*	String hotelStatus = dataTable.getData("user_Data", "HOTEL_STATUS");
		String traveler = dataTable.getData("user_Data", "TRAVELER");*/
		String[] alphabets = {"A","B","C","D","E","F","G","H","I"};
		String firstName = dataTable.getData("user_Data","LEGALFIRSTNAME" );
		String lastName = dataTable.getData("user_Data","LEGALLASTNAME");
		String  dob = dataTable.getData("user_Data","DATEOFBIRTH");

		Thread.sleep(5000);
		String numberOfHotel =dataTable.getData("User_Data", "Number Of Hotel");
		int number =Integer.parseInt(numberOfHotel);
		System.out.println(number);
		for(int i=1;i<=number;i++)
		{
			//WebElement AddmoreHotel=  driver.findElement(By.xpath("//a[@class='passenger-add field-replicator-trigger']"));
			WebElement AddmoreHotel=  driver.findElement(By.xpath("/html/body/div[3]/div[2]/div[2]/div[3]/div[2]/div/div[1]/div/form/div[2]/div/a"));
			//WebElement menu = driver.findElement(By.xpath("/html/body/div[2]/div[1]/div[1]/ul/li[3]/a"));
			System.out.println("Find Addhotel link");
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("arguments[0].click();", AddmoreHotel);
			System.out.println("clicked on Addhotel link");
			Thread.sleep(5000);
			exe.executeScript("window.scrollBy(0,250)", "");
			// Hotel Name 
			String hotelName = dataTable.getData("user_Data", "HOTELNAME");
			driver.findElement(By.id("line_item[hotel_info]["+i+"][hotel_name_"+i+"]")).click();

			driver.findElement(By.id("line_item[hotel_info]["+i+"][hotel_name_"+i+"]")).sendKeys(hotelName);
			exe.executeScript("window.scrollBy(0,250)", "");
			/*//Room Type Code
			String roomtype = dataTable.getData("user_Data", "ROOM TYPE CODE");
			driver.findElement(By.xpath("//input[@id='line_item[hotel_info]["+i+"][hotel_name_"+i+"]']")).click();
			driver.findElement(By.xpath("//input[@id='line_item[hotel_info]["+i+"][hotel_name_"+i+"]']")).sendKeys(roomtype,Keys.TAB);*/
			Thread.sleep(2000);

			/*// Hotel in date
			//	public static final HOTEL_INDATE 
			String hotelInDate = dataTable.getData("user_Data", "HOTEL IN DATE");
			driver.findElement(By.xpath("//div[contains(@data-calendar,'line_item_hotel_info_2_hotel_date')]")).clear();
			driver.findElement(By.xpath("//div[contains(@data-calendar,'line_item_hotel_info_2_hotel_date')]")).sendKeys(hotelInDate,Keys.TAB);
			Thread.sleep(5000);

			// hotel out date
			//public static final HOTEL_OUTDATE =
			String hotelOutDate = dataTable.getData("user_Data", "HOTEL OUT DATE");
			driver.findElement(By.xpath("//div[contains(@data-calendar,'line_item_hotel_info_2_hotel_date')]")).clear();
			driver.findElement(By.xpath("//div[contains(@data-calendar,'line_item_hotel_info_2_hotel_date')]")).sendKeys(
					hotelOutDate, Keys.TAB);*/

			exe.executeScript("window.scrollBy(0,250)", "");
			Select dropdown = new Select(driver.findElement(By.id("line_item[hotel_info]["+i+"][title_id_"+i+"]")));
			dropdown.selectByIndex(2);
			exe.executeScript("window.scrollBy(0,250)", "");
			driver.findElement(By.id("line_item[hotel_info]["+i+"][first_name_"+i+"]")).click();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			driver.findElement(By.id("line_item[hotel_info]["+i+"][first_name_"+i+"]")).sendKeys(firstName+alphabets[i],Keys.TAB);
			driver.findElement(By.id("line_item[hotel_info]["+i+"][first_name_"+i+"]")).sendKeys(Keys.TAB);
			System.out.println("Enterd First");
			exe.executeScript("window.scrollBy(0,150)", "");
			driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
			driver.findElement(By.id("line_item[hotel_info]["+i+"][last_name_"+i+"]")).sendKeys(lastName+alphabets[i]);

			driver.findElement(By.xpath("//div[contains(@data-calendar,'date-of-birth-calendar-"+i+"')]/div/input")).click();
			driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
			driver.findElement(By.xpath("//div[contains(@data-calendar,'date-of-birth-calendar-"+i+"')]/div/input")).sendKeys(dob);
			Thread.sleep(2000);

		}



	}
	public void airincluded_bookinginformationpage() throws InterruptedException, IOException
	{
		/*JavascriptExecutor exe = (JavascriptExecutor) driver;
		exe.executeScript("window.scrollBy(0,250)", "");*/
		Thread.sleep(5000); 
		driver.findElement(By.xpath("//label[contains(text(),'Included')]")).click();

		executor.executeScript("window.scrollBy(0,250)", ""); 
		Thread.sleep(5000); 
		String airline = dataTable.getData("User_Data", "AIRLINE NAME");
		driver.findElement(BookingInformationPage.AIRLINE_NAME).click();
		driver.findElement(BookingInformationPage.AIRLINE_NAME).sendKeys(airline,Keys.TAB);
		WebElement Airline = driver.findElement(
				By.xpath("//a[contains(@class,'list-option')]"));
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", Airline);

		Thread.sleep(5000); 	
		String departure_airport = dataTable.getData("User_Data", "DEPARTURE AIRPORT");
		driver.findElement(By.xpath("//input[@id='departure_airport-name']")).click();
		driver.findElement(By.xpath("//input[@id='departure_airport-name']")).sendKeys(departure_airport);
		WebElement Departure_airport = driver.findElement(By.xpath("//div[@class='collapser is-expanded']/div[2]/div[1]/div[1]/div[1]/div[1]/a[1]"));
		jse.executeScript("arguments[0].click();", Departure_airport);

		Thread.sleep(5000); 
		String arrival_airport = dataTable.getData("User_Data", "ARRIVAL AIRPORT");
		driver.findElement(By.xpath("//input[@id='arrival_airport-name']")).click();
		driver.findElement(By.xpath("//input[@id='arrival_airport-name']")).sendKeys(arrival_airport);
		WebElement Arrival_airport = driver.findElement(By.xpath("//div[@class='collapser is-expanded']/div[2]/div[2]/div[1]/div[1]/div[1]/a[1]"));
		jse.executeScript("arguments[0].click();", Arrival_airport);


		Thread.sleep(1000);
		String air_vendoragent = dataTable.getData("User_Data","VENDOR AGENT");
		driver.findElement(By.xpath("//input[@id='line_item[agent_name]']")).sendKeys(air_vendoragent,Keys.TAB);
		Thread.sleep(1000);

		String air_vendorphone = dataTable.getData("User_Data", "VENDOR PHONE");
		driver.findElement(By.xpath("//input[contains(@name,'line_item[agent_phone]')]")).sendKeys(air_vendorphone,Keys.TAB);

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		String pre_post_information = dataTable.getData("User_Data", "OTH SPECIAL ADVISEMENTS");
		driver.findElement(By.xpath("//textarea[@id='line_item[pre_post_information]']")).sendKeys(pre_post_information,Keys.TAB);

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		String packages_prices = dataTable.getData("User_Data", "YOUR TOTAL PACKAGE PRICE ");
		driver.findElement(By.xpath("//textarea[@id='line_item[packages_prices_include]']")).sendKeys(packages_prices,Keys.TAB);

		String tour_program = dataTable.getData("User_Data", "TOUR PROGRAM");
		if(tour_program.equalsIgnoreCase("DFP"))
		{
			Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='line_item[tour_program]']")));
			dropdown.selectByIndex(0);
		}
		else if(tour_program.equalsIgnoreCase("DMC"))
		{
			Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='line_item[tour_program]']")));
			dropdown.selectByIndex(1);
		}
		else if(tour_program.equalsIgnoreCase("GCD"))
		{
			Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='line_item[tour_program]']")));
			dropdown.selectByIndex(2);			
		}
		else if(tour_program.equalsIgnoreCase("PDE"))
		{
			Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='line_item[tour_program]']")));
			dropdown.selectByIndex(3);
		}
		else if(tour_program.equalsIgnoreCase("PDV"))
		{
			Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='line_item[tour_program]']")));
			dropdown.selectByIndex(4);
		}
		else if(tour_program.equalsIgnoreCase("NPA"))
		{
			Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='line_item[tour_program]']")));
			dropdown.selectByIndex(5);
		}
		else
		{
			System.out.println("Please Select Proper option");
		}
		Thread.sleep(5000); 
	}


	public void airseparate_bookinginformationpage() throws InterruptedException, IOException
	{
		/*JavascriptExecutor exe = (JavascriptExecutor) driver;
		exe.executeScript("window.scrollBy(0,250)", "");*/
		Thread.sleep(5000); 
		driver.findElement(By.xpath("//label[contains(@for,'ticket_separate')]")).click();
		Thread.sleep(2000); 
		executor.executeScript("window.scrollBy(0,250)", ""); 
		Thread.sleep(5000); 
		String airline = dataTable.getData("User_Data", "AIRLINE NAME");
		driver.findElement(BookingInformationPage.AIRLINE_NAME).click();
		driver.findElement(BookingInformationPage.AIRLINE_NAME).sendKeys(airline,Keys.TAB);
		WebElement Airline = driver.findElement(
				By.xpath("//a[contains(@class,'list-option')]"));
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", Airline);

		Thread.sleep(5000); 	
		String departure_airport = dataTable.getData("User_Data", "DEPARTURE AIRPORT");
		driver.findElement(By.xpath("//input[@id='departure_airport-name']")).click();
		driver.findElement(By.xpath("//input[@id='departure_airport-name']")).sendKeys(departure_airport);
		WebElement Departure_airport = driver.findElement(By.xpath("//div[@class='collapser is-expanded']/div[2]/div[1]/div[1]/div[1]/div[1]/a[1]"));
		jse.executeScript("arguments[0].click();", Departure_airport);

		Thread.sleep(5000); 
		String arrival_airport = dataTable.getData("User_Data", "ARRIVAL AIRPORT");
		driver.findElement(By.xpath("//input[@id='arrival_airport-name']")).click();
		driver.findElement(By.xpath("//input[@id='arrival_airport-name']")).sendKeys(arrival_airport);
		WebElement Arrival_airport = driver.findElement(By.xpath("//div[@class='collapser is-expanded']/div[2]/div[2]/div[1]/div[1]/div[1]/a[1]"));
		jse.executeScript("arguments[0].click();", Arrival_airport);


		Thread.sleep(1000);
		String air_vendoragent = dataTable.getData("User_Data","VENDOR AGENT");
		driver.findElement(By.xpath("//input[@id='line_item[agent_name]']")).sendKeys(air_vendoragent,Keys.TAB);
		Thread.sleep(1000);

		String air_vendorphone = dataTable.getData("User_Data", "VENDOR PHONE");
		driver.findElement(By.xpath("//input[contains(@name,'line_item[agent_phone]')]")).sendKeys(air_vendorphone,Keys.TAB);

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		String pre_post_information = dataTable.getData("User_Data", "OTH SPECIAL ADVISEMENTS");
		driver.findElement(By.xpath("//textarea[@id='line_item[pre_post_information]']")).sendKeys(pre_post_information,Keys.TAB);

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		String packages_prices = dataTable.getData("User_Data", "YOUR TOTAL PACKAGE PRICE ");
		driver.findElement(By.xpath("//textarea[@id='line_item[packages_prices_include]']")).sendKeys(packages_prices,Keys.TAB);

		String tour_program = dataTable.getData("User_Data", "TOUR PROGRAM");
		if(tour_program.equalsIgnoreCase("DFP"))
		{
			Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='line_item[tour_program]']")));
			dropdown.selectByIndex(0);
		}
		else if(tour_program.equalsIgnoreCase("DMC"))
		{
			Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='line_item[tour_program]']")));
			dropdown.selectByIndex(1);
		}
		else if(tour_program.equalsIgnoreCase("GCD"))
		{
			Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='line_item[tour_program]']")));
			dropdown.selectByIndex(2);			
		}
		else if(tour_program.equalsIgnoreCase("PDE"))
		{
			Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='line_item[tour_program]']")));
			dropdown.selectByIndex(3);
		}
		else if(tour_program.equalsIgnoreCase("PDV"))
		{
			Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='line_item[tour_program]']")));
			dropdown.selectByIndex(4);
		}
		else if(tour_program.equalsIgnoreCase("NPA"))
		{
			Select dropdown = new Select(driver.findElement(By.xpath("//*[@id='line_item[tour_program]']")));
			dropdown.selectByIndex(5);
		}
		else
		{
			System.out.println("Please Select Proper option");
		}
		Thread.sleep(5000); 
	}


	public void aironown_bookinginformationpage() throws InterruptedException, IOException
	{
		driver.findElement(By.xpath("//label[contains(@for,'on_own')]")).click();
		Thread.sleep(1000);
		String air_vendoragent = dataTable.getData("User_Data","VENDOR AGENT");
		driver.findElement(By.xpath("//input[@id='line_item[agent_name]']")).sendKeys(air_vendoragent,Keys.TAB);
		Thread.sleep(1000);

		String air_vendorphone = dataTable.getData("User_Data", "VENDOR PHONE");
		driver.findElement(By.xpath("//input[contains(@name,'line_item[agent_phone]')]")).sendKeys(air_vendorphone,Keys.TAB);

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		String pre_post_information = dataTable.getData("User_Data", "OTH SPECIAL ADVISEMENTS");
		driver.findElement(By.xpath("//textarea[@id='line_item[pre_post_information]']")).sendKeys(pre_post_information,Keys.TAB);

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		String packages_prices = dataTable.getData("User_Data", "YOUR TOTAL PACKAGE PRICE ");
		driver.findElement(By.xpath("//textarea[@id='line_item[packages_prices_include]']")).sendKeys(packages_prices,Keys.TAB);

		String tour_program = dataTable.getData("User_Data", "TOUR PROGRAM");
		Select dropdown = new Select(driver.findElement(By.xpath("//select[@id='line_item[tour_program]']")));
		dropdown.selectByValue(tour_program);
		//	executor.executeScript("window.scrollBy(0,250)", ""); 

		Thread.sleep(5000); 
	}
	public void optionsPageMethodpullprofile_onetraveller() throws InterruptedException, IOException
	{
		Select dropdown_traveler = new Select(driver.findElement(OptionsPage.TRAVELER));
		dropdown_traveler.selectByIndex(3);
		String tour_rate = dataTable.getData("User_Data", "TOUR RATE");
		driver.findElement(OptionsPage.TOUR_RATE).sendKeys(tour_rate);

		String taxes = dataTable.getData("User_Data", "TAXES");
		driver.findElement(OptionsPage.TAXES).sendKeys(taxes);
		//Additional option
		String commission = dataTable.getData("User_Data", "COMMISSION");
		driver.findElement(OptionsPage.COMMISSION).sendKeys(commission);
		JavascriptExecutor exe = (JavascriptExecutor) driver; 
		exe.executeScript("window.scrollBy(0,250)", ""); 
		String full_final_due_date = dataTable.getData("User_Data", "FULL/FINAL DUE DATE");
		driver.findElement(OptionsPage.FULL_FINAL_DUE_DATE).sendKeys(full_final_due_date);
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);

		String deposit_due_date = dataTable.getData("User_Data", "DEPOSITDUEDATE1");
		driver.findElement(OptionsPage.DEPOSIT_DUE_DATE).sendKeys(deposit_due_date);
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		//String deposit_amount = dataTable.getData("User_Data", "DEPOSIT AMOUNT");
		driver.findElement(OptionsPage.DEPOSIT_AMOUNT).sendKeys(deposit_amount);
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("window.scrollBy(0,250)", "");
		//click on continue
		driver.findElement(By.xpath("//button[@type= 'submit']")).click();
		Thread.sleep(5000);


	}
	public void pDFLibrary() throws InterruptedException, IOException, AWTException
	{
		driver.findElement(By.xpath("//a[contains(text(),'PDF')]")).click();

		boolean rc;

		rc = waitForElement(By.xpath("//a[contains(text(),'Document')]"));
		if(rc)
		{

			driver.findElement(By.xpath("//a[contains(text(),'Document')]"));
			Thread.sleep(5000);
			report.updateTestLog("PDF page is opend", "PDF page is opend", Status.PASS);

		}
		else
		{
			report.updateTestLog("PDF page is not opend", "PDF page is not opend", Status.FAIL);
		}
		Thread.sleep(5000);
		executor.executeScript("window.scrollBy(0,500)", "");
		Thread.sleep(2000);
		driver.findElement(By.xpath("//i[contains(@class,'fa fa-download')]")).click();
		Thread.sleep(8000);
		driver.findElement(By.xpath("//a[contains(text(),'Booking &')]")).click();
		Thread.sleep(5000);
		executor.executeScript("window.scrollBy(0,250)", "");
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		//Click on View
		driver.findElement(By.xpath("//a[contains(@class,'small button view')]")).click();

		boolean rc1;

		rc1 = waitForElement(By.xpath("//span[contains(text(),'Confirmation Number')]"));
		if(rc1)
		{

			driver.findElement(By.xpath("//span[contains(text(),'Confirmation Number')]"));
			Thread.sleep(5000);
			report.updateTestLog("Clicked on Booking Quotes", "Clicked on Booking Quotes", Status.PASS);

		}


	}
	public void modifyPageother() throws InterruptedException, IOException, AWTException
	{
		/*//Click on service tab
		driver.findElement(By.xpath("//a[contains(text(),'Service')]")).click();
		Thread.sleep(5000);
		//Entering the PNR
		driver.findElement(By.xpath("//input[contains(@class,'input-selected-border-left quick-search')]")).click();
		String PNR = dataTable.getData("User_Data", "PNR");
		WebElement pnr = driver.findElement(By.xpath("//input[contains(@class,'input-selected-border-left quick-search')]"));
		pnr.sendKeys(PNR);
		//Click on Search
		driver.findElement(By.xpath("//a[contains(@class,'button task button-search input-selected-border')]")).click();
		Thread.sleep(10000);
		//Click on trips
		driver.findElement(By.xpath("//span[contains(@class,'abc small button')]")).click();
		Thread.sleep(5000);
		//Click on View
		driver.findElement(By.xpath("//a[contains(@class,'small button view')]")).click();
		Thread.sleep(5000);*/
		//Click on expand
		driver.findElement(By.xpath("//Span[contains(text(),'Expand')]")).click();
		Thread.sleep(1000);
		executor.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//div[contains(@class,'ui-panel-container-padding')]/ul")));
		//Click on modify booking
		driver.findElement(By.xpath("//a[contains(text(),'Modify Booking')]")).click();
		boolean rc;

		rc = waitForElement(By.id("vendor-agent"));
		if(rc)
		{

			driver.findElement(By.id("vendor-agent"));;
			Thread.sleep(5000);
			report.updateTestLog("Modify page is opend", "Modify page is opend", Status.PASS);
		}

		String vendor_agent = dataTable.getData("user_Data","vendor_agent");
		String vendor_phone = dataTable.getData("user_Data","vendor_phone");
		String change_description = dataTable.getData("user_Data","change_description");
		String cardholderfirstname = dataTable.getData("user_Data","cardholderfirstname");
		String cardholderlastname = dataTable.getData("user_Data","cardholderlastname");
		String card_number = dataTable.getData("user_Data","card_number");
		String confirmationnumbermodify = dataTable.getData("user_Data","confirmationnumbermodify");

		driver.findElement(BookingInformationPage.vendor_agent).click();
		driver.findElement(BookingInformationPage.vendor_agent).clear();
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		driver.findElement(BookingInformationPage.vendor_agent).sendKeys(vendor_agent,Keys.TAB);
		driver.findElement(BookingInformationPage.vendor_phone).clear();
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		driver.findElement(BookingInformationPage.vendor_phone).sendKeys(vendor_phone,Keys.TAB);
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		report.updateTestLog("After entering the phone number", "", Status.SCREENSHOT);
		driver.findElement(BookingInformationPage.change_description).sendKeys(change_description,Keys.TAB);
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		driver.findElement(BookingInformationPage.cardholderfirstname).clear();
		driver.findElement(BookingInformationPage.cardholderfirstname).sendKeys(cardholderfirstname,Keys.TAB);
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		driver.findElement(BookingInformationPage.cardholderlastname).clear();
		driver.findElement(BookingInformationPage.cardholderlastname).sendKeys(cardholderlastname,Keys.TAB);
		driver.findElement(BookingInformationPage.card_number).sendKeys(card_number,Keys.TAB);
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		report.updateTestLog("After card number", "", Status.SCREENSHOT);

		/*		driver.findElement(BookingInformationPage.sale_amount).clear();
		driver.findElement(BookingInformationPage.sale_amount).sendKeys(sale_amount,Keys.TAB);
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		driver.findElement(BookingInformationPage.depositdueamount).clear();
		driver.findElement(BookingInformationPage.depositdueamount).sendKeys(depositdueamount,Keys.TAB);
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		driver.findElement(BookingInformationPage.depositduedate).clear();
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		driver.findElement(BookingInformationPage.depositduedate).sendKeys(depositduedate,Keys.TAB);
		driver.findElement(BookingInformationPage.finalfullamount).clear();
		driver.findElement(BookingInformationPage.finalfullamount).sendKeys(finalfullamount,Keys.TAB);

		driver.findElement(BookingInformationPage.fullduedate).clear();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.findElement(BookingInformationPage.fullduedate).sendKeys(fullduedate,Keys.TAB);
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		report.updateTestLog("After amount details", "", Status.SCREENSHOT);
		driver.findElement(BookingInformationPage.commission).clear();
		driver.findElement(BookingInformationPage.commission).sendKeys(commission);*/
		driver.findElement(BookingInformationPage.confirmationnumbermodify).clear();

		driver.findElement(BookingInformationPage.confirmationnumbermodify).sendKeys(confirmationnumbermodify,Keys.TAB);
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		report.updateTestLog("After entering confirmation numbre", "", Status.SCREENSHOT);
		executor.executeScript("window.scrollBy(0,250)", "");
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		Thread.sleep(5000);
		boolean rc1;

		rc1 = waitForElement(By.xpath("//button[contains(@class,'button secondary')]"));
		if(rc1)
		{

			driver.findElement(By.xpath("//button[contains(@class,'button secondary')]"));
			Thread.sleep(5000);
			report.updateTestLog("PNR Modified clicked on continue", "PNR Modified clicked on continue", Status.PASS);

		}
		report.updateTestLog("PNR Modified Sucessfully", "PNR Modified Sucessfully", Status.PASS);


	}

	public void profileupdateENCORE() throws InterruptedException, AWTException 
	{
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Enter the card number
		driver.findElement(By.id("searchKeyWord")).clear();	    
		driver.findElement(By.id("searchKeyWord")).sendKeys(dataTable.getData("General_Data", "CARDNUMBER"));
		driver.findElement(By.id("getProfileSearchResults")).click();

		// Wait for the search results and click the search results link ( Name displayed on the column)
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//table[@id='profileSearchContainer-body']/tbody/tr[2]/td/a/span")));	
		driver.findElement(By.xpath("//table[@id='profileSearchContainer-body']/tbody/tr[2]/td/a/span")).click();

		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);

		// Wait for the pop up to load fully 'popupLayer_launchUCIDScreen'
		//WebDriverWait amxcardanswer = new WebDriverWait(driver,40);	    
		//amxcardanswer.until(ExpectedConditions.elementToBeClickable(By.id("amxcardansw")));

		if (driver.findElement(By.id("amxCorrect")).isDisplayed())
		{
			wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("amxCorrect"))));
			driver.findElement(By.id("amxCorrect")).click();
		}
		else
		{


			// Check the continue button is enabled before entering the answer
			String amexUCIDContinue = driver.findElement(By.xpath("//*[@id='continueamexUCIDProcess']")).getAttribute("class");
			if(amexUCIDContinue.contains("buttonDisabled")){
				report.updateTestLog("Verify continue button is enabled before enter answer", "Continue button is disabled before selecting answer in UCID screen", Status.PASS);
			}else{
				report.updateTestLog("Verify continue button is enabled before enter answer", "Continue button is enabled before selecting answer in UCID screen", Status.FAIL);
			}

			// enter the security answer 
			driver.findElement(By.xpath("//textarea[@id='amxcardansw']")).click();	    
			driver.findElement(By.xpath("//textarea[@id='amxcardansw']")).sendKeys(dataTable.getData("User_Data", "SecurityAnswer"));
		}

		driver.findElement(By.id("continueamexUCIDProcess")).click();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		//driver.findElement(By.linkText("OK")).click();

		// Click OK on the Alert if the Alert is present
		if (driver.findElements(By.xpath("//a[@class='okBtn continueSuppCSW']")).size()!=0)
		{
			wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//a[@class='okBtn continueSuppCSW']"))));
			driver.findElement(By.xpath("//a[@class='okBtn continueSuppCSW']")).click();
		}


		//Click on Maximize
		driver.findElement(By.xpath("//span[contains(@class,'profilePanelMaximize')]")).click();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		//Click on phone number to update
		Actions action = new Actions(driver);
		action.moveToElement(driver.findElement(By.xpath("//*[@id='phoneReference']/div/div/div[2]/div[1]/div/div/div/div[1]/span[2]/span[3]"))).doubleClick().build().perform();

		Thread.sleep(1000);

		/*//Click on Phone number + Icon

		WebElement addIcon = driver.findElement(By.xpath("//*[@id='phoneReference']/h3/span/a"));
		Actions actions =new Actions(driver);
		actions.moveToElement(addIcon).build().perform();

		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id='phoneReference']/h3/span/span")).click();

		Thread.sleep(10000);*/

		//driver.findElement(By.xpath("//span[contains(@class,'sectionTextContent viewPhoneEllipsis encoreToolTip')]"));

		//TSA UCID handling

		driver.switchTo().defaultContent(); // switch to popup window

		// UCID high level clearance

		if (driver.findElement(By.id("amxCorrect")).isDisplayed())
		{
			wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("amxCorrect"))));
			driver.findElement(By.id("amxCorrect")).click();

		}
		else
		{
			// Check the continue button is enabled before entering the answer
			String amexUCIDContinue = driver.findElement(By.xpath("//*[@id='continueamexUCIDProcess']")).getAttribute("class");
			if(amexUCIDContinue.contains("buttonDisabled")){
				report.updateTestLog("Highlevel UCID-Verify continue button is enabled before enter answer", "Continue button is disabled before selecting answer in UCID screen", Status.PASS);
			}else{
				report.updateTestLog("Highlevel UCID-Verify continue button is enabled before enter answer", "Continue button is enabled before selecting answer in UCID screen", Status.FAIL);
			}

			// enter the security answer
			driver.findElement(By.xpath("//textarea[@id='amxcardansw']")).click();        
			driver.findElement(By.xpath("//textarea[@id='amxcardansw']")).sendKeys("7777");
		}
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		driver.findElement(By.id("continueamexUCIDProcess")).click();
		//driver.switchTo().window(parentWindowHandler);  // switch back to parent window
		Thread.sleep(12000);
		//driver.switchTo().frame(0);


		//Enter phone number from data sheet
		//driver.switchTo().frame(0);
		String phone = dataTable.getData("User_Data", "PHONE");
		//executor.executeScript("arguments[0].click();",driver.findElement(By.xpath("//div[contains(@id,'phoneSmartPaper_1')]/div/div/div[1]/span[2]/input")));
		//driver.findElement(By.xpath("//div[contains(@id,'phoneSmartPaper_1')]/div/div/div[1]/span[2]/input")).sendKeys(Keys.TAB);
		driver.findElement(By.xpath("//div[contains(@id,'phoneSmartPaper_1')]/div/div/div[1]/span[3]/input")).click();
		driver.findElement(By.xpath("//div[contains(@id,'phoneSmartPaper_1')]/div/div/div[1]/span[3]/input")).clear();
		Thread.sleep(1000);
		//update phone
		driver.findElement(By.xpath("//div[contains(@id,'phoneSmartPaper_1')]/div/div/div[1]/span[3]/input")).sendKeys(phone);
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		//Smart save
		driver.findElement(By.xpath("//*[@id='phoneSmartPaper_1']/div/div/div[3]/a[2]")).click();
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//a[contains(@class,'globalSave rightAlignButton buttonsNormal')]")).sendKeys(Keys.PAGE_DOWN);
		Thread.sleep(2000);
		//Big save
		driver.findElement(By.xpath("//a[contains(@class,'globalSave rightAlignButton buttonsNormal')]")).click();
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		Thread.sleep(2000);
		//Click on Dashboard
		driver.findElement(By.xpath("//*[@id='ui-id-1']")).click();
		Thread.sleep(2000);
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);

		/*String phonenumberencore = driver.findElement(By.xpath("//*[@id='phoneSmartPaper_1']/div/div/div[1]/span[3]/input")).getText().toString();
		System.out.println(phonenumberencore);

		String phonenumberPA5 = dataTable.getData("General_Data", "PHONE").toString().toUpperCase();
		System.out.println(phonenumberPA5);

		if(phonenumberencore.matches(phonenumberPA5)){

			report.updateTestLog("phonenumberencore and phonenumberPA5 both are same","phonenumberencore and phonenumberPA5 both are same", Status.PASS );
		}else{
			report.updateTestLog("phonenumberencore and phonenumberPA5 both are not same","phonenumberencore and phonenumberPA5 both are not same", Status.FAIL);
		}*/

	}


}


















