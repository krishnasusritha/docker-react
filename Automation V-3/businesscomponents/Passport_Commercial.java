package businesscomponents;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import objectrepository.Objectrepository;
import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;
import supportlibraries.Reports.Reports;
import supportlibraries.com.ncr.framework.Status;
import objectrepository.*;

public class Passport_Commercial extends ReusableLibrary
{

	boolean rc;
	JavascriptExecutor executor = (JavascriptExecutor) driver; 
	//private boolean acceptNextAlert = true;
	Robot rb = null;

	/**
	 * Constructor to initialize the component library
	 * @param scriptHelper The {@link ScriptHelper} object passed from the {@link DriverScript}
	 */
	public Passport_Commercial(ScriptHelper scriptHelper)
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
	public void application() throws InterruptedException, AWTException
	{
		if(properties.getProperty("DefaultBrowser").equalsIgnoreCase("InternetExplorer"))
		{
			driver.get(properties.getProperty("ApplicationUrl"));
			Thread.sleep(10000);
		}
		else
		{
			
			String url = properties.getProperty("ApplicationUrl");
			driver.get(url);
			Thread.sleep(10);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			report.updateTestLog("Open Application", "Application URL:" +
			properties.getProperty("ApplicationUrl"), Status.DONE);
		}

			Thread.sleep(2000);
	}
	
	public void commercialLogin() throws InterruptedException, AWTException
	{

			Thread.sleep(2000);
			String UserType=dataTable.getData("General_Data", "UserType");
			String userName = dataTable.getData("General_Data", "Username");
			String password = dataTable.getData("General_Data", "Password");
			
			Select domain = new Select(driver.findElement(Commercial_CreateDeposit.UserType));
			//domain.selectByVisibleText("Customer");
			//domain.selectByVisibleText("Bank");
			//domain.selectByVisibleText("Host");
			
			if(dataTable.getData("General_Data", "UserType").equals("Customer"))
			{
				domain.selectByVisibleText("Customer");
				driver.findElement(Commercial_CreateDeposit.Start).click();
				Thread.sleep(3000);

				//Enter customer username and password
				driver.findElement(Commercial_CreateDeposit.UserID).clear();
				driver.findElement(Commercial_CreateDeposit.UserID).sendKeys(userName);
				driver.findElement(Commercial_CreateDeposit.Password).clear();
				driver.findElement(Commercial_CreateDeposit.Password).sendKeys(password);
				driver.findElement(Commercial_CreateDeposit.LoginButton).click();
				report.updateTestLog("Login User","User Name : "+ userName,Status.DONE);
				Thread.sleep(3000);
				
			}
			
			if(dataTable.getData("General_Data", "UserType").equals("Bank"))
			{
				domain.selectByVisibleText("Bank");
				driver.findElement(Commercial_CreateDeposit.Start).click();
				Thread.sleep(3000);

				//Enter Bank username and password
				driver.findElement(Commercial_CreateDeposit.UserID).clear();
				driver.findElement(Commercial_CreateDeposit.UserID).sendKeys(userName);
				driver.findElement(Commercial_CreateDeposit.Password).clear();
				driver.findElement(Commercial_CreateDeposit.Password).sendKeys(password);
				driver.findElement(Commercial_CreateDeposit.LoginButton).click();
				report.updateTestLog("Login User","User Name : "+ userName,Status.DONE);
				Thread.sleep(3000);
				
			}
			
			if(dataTable.getData("General_Data", "UserType").equals("Host"))
			{
				domain.selectByVisibleText("Host");
				driver.findElement(Commercial_CreateDeposit.Start).click();
				Thread.sleep(3000);

				//Enter Bank username and password
				driver.findElement(Commercial_CreateDeposit.UserID).clear();
				driver.findElement(Commercial_CreateDeposit.UserID).sendKeys(userName);
				driver.findElement(Commercial_CreateDeposit.Password).clear();
				driver.findElement(Commercial_CreateDeposit.Password).sendKeys(password);
				driver.findElement(Commercial_CreateDeposit.LoginButton).click();
				report.updateTestLog("Login User","User Name : "+ userName,Status.DONE);
				Thread.sleep(3000);
			}
			
			}
	public void createDeposit() throws InterruptedException, IOException, AWTException
	{
		//Customer home page
		driver.findElement(Commercial_CreateDeposit.CreateDeposit).click();
		Thread.sleep(3000);
		report.updateTestLog("Clicked on Create New Deposit", "New Deposit Creation page is opened", Status.PASS);

		String ExpectedAmount=dataTable.getData("User_Data", "ExpectedAmount");

		try {
		Passport_Commercial flow = new Passport_Commercial(scriptHelper);
		
		//Validate Deposit In-progress and Error messages found if any
		try {

			List<WebElement> tableMessage1 = driver.findElements(By.xpath("//table[@id='Form:TableMessage']"));
			if(!tableMessage1.isEmpty()) {
				String tableMessage = driver.findElement(By.xpath("//table[@id='Form:TableMessage']")).getText();
				report.updateTestLog("Table Message Found","Error message is" + tableMessage, Status.SCREENSHOT);

				if(tableMessage.contains("There is a deposit currently in process")) {

					int colNum_delete = flow.getDepositListColumnNumber("Tasks");
					int colNum_state =  flow.getDepositListColumnNumber("State");

					String state = driver.findElement(By.xpath("//table[@id='Form:depositList']/tbody/tr[1]/td["+colNum_state+"]")).getText();
					report.updateTestLog("Status of Deposit in Process","Existing Deposit Status is" + state, Status.SCREENSHOT);

					if(state.equalsIgnoreCase("Open-Processing")) { 

						driver.findElement(By.xpath("//table[@id='Form:depositList']/tbody/tr[1]/td["+colNum_delete+"]//input[contains(@id,':EndDeleteDepositButton')]")).click();
						Thread.sleep(3000);
						Alert alert1 = driver.switchTo().alert();
						alert1.accept();
						Thread.sleep(3000);
						Assert.assertTrue(driver.findElement(By.xpath("//table[@id='Form:TableMessage']/tbody/tr/td")).getText().contains("has been deleted."));

						driver.findElement(Commercial_CreateDeposit.CreateNewDeposit).click();
						Thread.sleep(3000);
							//location selection
							WebElement location = driver.findElement(By.id("Form:LocationInput"));
							Select select = new Select(location);
							select.selectByIndex(0);
							Thread.sleep(3000);
						driver.findElement(Commercial_CreateDeposit.ExpectedAmount).sendKeys(ExpectedAmount);
						driver.findElement(Commercial_CreateDeposit.StartDepositButton).click();
							Thread.sleep(10000);
							Assert.assertEquals(driver.findElement(By.xpath("//span[contains(./text(),'Deposit Item List')]"))!=null,true);
					}else if(state.equalsIgnoreCase("Open-Jammed")) {

						driver.findElement(By.xpath("//table[@id='Form:depositList']/tbody/tr[1]/td[1]//input[contains(@id,':EndDeleteDepositButton')]")).click();
						Thread.sleep(3000);
						Alert alert1 = driver.switchTo().alert();
						alert1.accept();

						Assert.assertTrue(driver.findElement(By.xpath("//table[@id='Form:TableMessage']/tbody/tr/td")).getText().contains("has been deleted."));

						driver.findElement(Commercial_CreateDeposit.CreateNewDeposit).click();
						Thread.sleep(3000);
						
						//location selection
						
						WebElement location = driver.findElement(By.id("Form:LocationInput"));
						
						Select select = new Select(location);
						select.selectByIndex(0);
						Thread.sleep(3000);

						driver.findElement(Commercial_CreateDeposit.ExpectedAmount).sendKeys(ExpectedAmount);
						driver.findElement(Commercial_CreateDeposit.StartDepositButton).click();
						Thread.sleep(10000);

					}else {
						report.updateTestLog("Deposit State","Deposit State is" + state, Status.SCREENSHOT);
					}

				}else if(tableMessage.contains("An end capture has been performed")) {

					driver.findElement(By.xpath("//input[@value='Add Items']")).click();
					Thread.sleep(10000);
					}else {
						
						//location selection
						
						WebElement location = driver.findElement(By.id("Form:LocationInput"));
						
						Select select = new Select(location);
						select.selectByIndex(0);
						Thread.sleep(3000);

						//driver.findElement(By.id("Form:ExpectedAmountInput")).sendKeys("10");
						driver.findElement(Commercial_CreateDeposit.ExpectedAmount).sendKeys(ExpectedAmount);
						driver.findElement(Commercial_CreateDeposit.StartDepositButton).click();
						//driver.findElement(By.id("Form:StartDepositButton")).click();
						Thread.sleep(10000);


						Assert.assertEquals(driver.findElement(By.xpath("//span[contains(./text(),'Deposit Item List')]"))!=null,true);

					}
				}else {

					
					//location selection
					
					WebElement location = driver.findElement(By.id("Form:LocationInput"));
					
					Select select = new Select(location);
					select.selectByIndex(0);
					Thread.sleep(3000);

					//Enter deposit amount

					driver.findElement(Commercial_CreateDeposit.ExpectedAmount).sendKeys(ExpectedAmount);
					driver.findElement(Commercial_CreateDeposit.StartDepositButton).click();
					Thread.sleep(10000);

					Assert.assertEquals(driver.findElement(By.xpath("//span[contains(./text(),'Deposit Item List')]"))!=null,true);

				}
		}catch(Exception e) {
			System.out.println(e);
		}


		List<WebElement> tableMessage2 = driver.findElements(By.xpath("//table[@id='Form:TableMessage']"));
		if(!tableMessage2.isEmpty()) {
			String tableMessage = driver.findElement(By.xpath("//table[@id='Form:TableMessage']")).getText();
			report.updateTestLog("Table messge ","Table Message is" + tableMessage, Status.SCREENSHOT);

			if(tableMessage.contains("An end capture has been performed")) {

				driver.findElement(By.xpath("//input[@value='Add Items']")).click();
				Thread.sleep(3000);
			}
		}

		int num_of_check = driver.findElements(By.xpath("//table[@id='Form:itemList']/tbody/tr")).size();
		System.out.println(num_of_check);
		for(int i=2;i<=num_of_check;i++) {

			//driver.findElements(By.xpath("//table[@id='Form:itemList']/tbody/tr["+i+"]"));

			////table[@id='Form:itemList']/tbody/tr[2]/td[1]//input[contains(@name,'RecoFailed')]

			List<WebElement> error = driver.findElements(By.xpath("//table[@id='Form:itemList']/tbody/tr["+i+"]/td[1]//input[contains(@name,'RecoFailed')]"));
			report.updateTestLog("Error","Error is" + error.size(), Status.SCREENSHOT);
			if(!error.isEmpty()) {

				error.get(0).click();
				Thread.sleep(10000);

				String message = driver.findElement(By.id("EditCheckForm:EditItemErrorFlags")).getText();

				if(message.contains("Confirm Amount")) {
					driver.findElement(By.id("EditCheckForm:SaveButton")).click();
					Thread.sleep(10000);
					
				}else if(message.contains("Override Duplicate")) {
					driver.findElement(By.id("EditCheckForm:DuplicateOverride")).click();
					Thread.sleep(2000);
					driver.findElement(By.id("EditCheckForm:SaveButton")).click();
					Thread.sleep(5000);
				}
				
				else if(message.contains("Failed Image Quality")) {

					List<WebElement> override = driver.findElements(By.id("EditCheckForm:ImageOverride"));
					if(override.isEmpty()) {

						driver.findElement(By.id("EditCheckForm:DeleteButton")).click();
						Thread.sleep(10000);

						Alert alert1 = driver.switchTo().alert();
						alert1.accept();
						Thread.sleep(5000);
						i--;

						int num_of_check2 = driver.findElements(By.xpath("//table[@id='Form:itemList']/tbody/tr")).size();

						if(num_of_check2==1) {

							driver.findElement(By.id("Form:ReturnToDepositsButton")).click();
							Thread.sleep(10000);

							int colNum_delete2 = flow.getDepositListColumnNumber("Tasks");
							//int colNum_state =  flow.getDepositeListColumnNumber("State");

							driver.findElement(By.xpath("//table[@id='Form:depositList']/tbody/tr[1]/td["+colNum_delete2+"]//input[contains(@id,':EndDeleteDepositButton')]")).click();
							Thread.sleep(3000);
							Alert alert2 = driver.switchTo().alert();
							alert2.accept();

							Assert.assertTrue(driver.findElement(By.xpath("//table[@id='Form:TableMessage']/tbody/tr/td")).getText().contains("has been deleted."));
							report.updateTestLog("Failed Image Quality Error", "Deposit can't be created due to the error and hence Deposit is deleted", Status.SCREENSHOT);
							driver.findElement(By.linkText("Logoff")).click();
							driver.quit();
							
						}

					}else {

						override.get(0).click();
						Thread.sleep(2000);
						driver.findElement(By.id("EditCheckForm:SaveButton")).click();
						Thread.sleep(10000);
					}



				}
				//handle erro
			}
		}

		if(driver!=null) {

			Thread.sleep(3000);

			String currentAmount = driver.findElement(By.id("Form:currentAmount")).getText();

			Thread.sleep(3000);

			driver.findElement(By.id("Form:ExpAmount")).clear();
			driver.findElement(By.id("Form:ExpAmount")).sendKeys(currentAmount);

			Thread.sleep(3000);

			driver.findElement(By.id("Form:SaveButton")).click();

			Thread.sleep(3000);

			driver.findElement(By.id("Form:CompleteButton")).click();

			Thread.sleep(3000);

			Alert alert = driver.switchTo().alert();
			alert.accept();

			Thread.sleep(10000);

			int colNum_delete3 = flow.getDepositListColumnNumber("Tasks");
			int colNum_state3 =  flow.getDepositListColumnNumber("State");

			String state = driver.findElement(By.xpath("//table[@id='Form:depositList']/tbody/tr[1]/td["+colNum_state3+"]")).getText();

			if(state.equalsIgnoreCase("Open-Jammed")) {

				driver.findElement(By.xpath("//table[@id='Form:depositList']/tbody/tr[1]/td["+colNum_delete3+"]//input[contains(@id,':EndDeleteDepositButton')]")).click();
				Thread.sleep(3000);
				Alert alert1 = driver.switchTo().alert();
				alert1.accept();

				Assert.assertTrue(driver.findElement(By.xpath("//table[@id='Form:TableMessage']/tbody/tr/td")).getText().contains("has been deleted."));

			}else {
				Thread.sleep(20000);
			
				report.updateTestLog("Deposit State","Deposit State is" +state, Status.SCREENSHOT);
			}

			driver.findElement(By.linkText("Logoff")).click();
			report.updateTestLog("Logoff","User Loggedout" , Status.PASS);
			//driver.quit();
			
		}
	}catch(Exception e) {
		System.out.println(e);
		//driver.quit();
		
	}
	}

	public void depositDeposit() throws InterruptedException, IOException, AWTException
	{
		//Customer home page
		driver.findElement(Commercial_CreateDeposit.CreateDeposit).click();
		Thread.sleep(3000);
		report.updateTestLog("Clicked on Create New Deposit", "New Deposit Creation page is opened", Status.PASS);

		String ExpectedAmount=dataTable.getData("User_Data", "ExpectedAmount");

		try {
		Passport_Commercial flow = new Passport_Commercial(scriptHelper);
		
		//Validate Deposit In-progress and Error messages found if any
		try {

			List<WebElement> tableMessage1 = driver.findElements(By.xpath("//table[@id='Form:TableMessage']"));
			if(!tableMessage1.isEmpty()) {
				String tableMessage = driver.findElement(By.xpath("//table[@id='Form:TableMessage']")).getText();
				report.updateTestLog("Table Message Found","Error message is" + tableMessage, Status.SCREENSHOT);

				if(tableMessage.contains("There is a deposit currently in process")) {

					int colNum_delete = flow.getDepositListColumnNumber("Tasks");
					int colNum_state =  flow.getDepositListColumnNumber("State");

					String state = driver.findElement(By.xpath("//table[@id='Form:depositList']/tbody/tr[1]/td["+colNum_state+"]")).getText();
					report.updateTestLog("Status of Deposit in Process","Existing Deposit Status is" + state, Status.SCREENSHOT);

					if(state.equalsIgnoreCase("Open-Processing")) { 

						driver.findElement(By.xpath("//table[@id='Form:depositList']/tbody/tr[1]/td["+colNum_delete+"]//input[contains(@id,':EndDeleteDepositButton')]")).click();
						Thread.sleep(3000);
						Alert alert1 = driver.switchTo().alert();
						alert1.accept();
						Thread.sleep(3000);
						Assert.assertTrue(driver.findElement(By.xpath("//table[@id='Form:TableMessage']/tbody/tr/td")).getText().contains("has been deleted."));

						driver.findElement(Commercial_CreateDeposit.CreateNewDeposit).click();
						Thread.sleep(3000);
						
						//location selection
						
						WebElement location = driver.findElement(By.id("Form:LocationInput"));
						
						Select select = new Select(location);
						select.selectByIndex(0);
						Thread.sleep(3000);

						//driver.findElement(By.id("Form:ExpectedAmountInput")).sendKeys("10");
						driver.findElement(Commercial_CreateDeposit.ExpectedAmount).sendKeys(ExpectedAmount);
						driver.findElement(Commercial_CreateDeposit.StartDepositButton).click();
						//driver.findElement(By.id("Form:StartDepositButton")).click();
						Thread.sleep(10000);


						Assert.assertEquals(driver.findElement(By.xpath("//span[contains(./text(),'Deposit Item List')]"))!=null,true);

					}else if(state.equalsIgnoreCase("Open-Jammed")) {

						driver.findElement(By.xpath("//table[@id='Form:depositList']/tbody/tr[1]/td[1]//input[contains(@id,':EndDeleteDepositButton')]")).click();
						Thread.sleep(3000);
						Alert alert1 = driver.switchTo().alert();
						alert1.accept();

						Assert.assertTrue(driver.findElement(By.xpath("//table[@id='Form:TableMessage']/tbody/tr/td")).getText().contains("has been deleted."));

						driver.findElement(Commercial_CreateDeposit.CreateNewDeposit).click();
						Thread.sleep(3000);
						
						//location selection
						
						WebElement location = driver.findElement(By.id("Form:LocationInput"));
						
						Select select = new Select(location);
						select.selectByIndex(0);
						Thread.sleep(3000);

						driver.findElement(Commercial_CreateDeposit.ExpectedAmount).sendKeys(ExpectedAmount);
						driver.findElement(Commercial_CreateDeposit.StartDepositButton).click();
						Thread.sleep(10000);

					}else {
						report.updateTestLog("Deposit State","Deposit State is" + state, Status.SCREENSHOT);
					}

				}else if(tableMessage.contains("An end capture has been performed")) {

					driver.findElement(By.xpath("//input[@value='Add Items']")).click();
					Thread.sleep(10000);
					
				}else {

					
					//location selection
					
					WebElement location = driver.findElement(By.id("Form:LocationInput"));
					
					Select select = new Select(location);
					select.selectByIndex(0);
					Thread.sleep(3000);

					//Enter deposit amount

					driver.findElement(Commercial_CreateDeposit.ExpectedAmount).sendKeys(ExpectedAmount);
					driver.findElement(Commercial_CreateDeposit.StartDepositButton).click();
					Thread.sleep(10000);

					Assert.assertEquals(driver.findElement(By.xpath("//span[contains(./text(),'Deposit Item List')]"))!=null,true);

				}

			}else {
				
				//location selection
				
				WebElement location = driver.findElement(By.id("Form:LocationInput"));
				
				Select select = new Select(location);
				select.selectByIndex(0);
				Thread.sleep(3000);


				driver.findElement(Commercial_CreateDeposit.ExpectedAmount).sendKeys(ExpectedAmount);
				driver.findElement(Commercial_CreateDeposit.StartDepositButton).click();
				Thread.sleep(10000);

				Assert.assertEquals(driver.findElement(By.xpath("//span[contains(./text(),'Deposit Item List')]"))!=null,true);
			}
		}catch(Exception e) {
			System.out.println(e);
		}


		List<WebElement> tableMessage2 = driver.findElements(By.xpath("//table[@id='Form:TableMessage']"));
		if(!tableMessage2.isEmpty()) {
			String tableMessage = driver.findElement(By.xpath("//table[@id='Form:TableMessage']")).getText();
			report.updateTestLog("Table messge ","Table Message is" + tableMessage, Status.SCREENSHOT);

			if(tableMessage.contains("An end capture has been performed")) {

				driver.findElement(By.xpath("//input[@value='Add Items']")).click();
				Thread.sleep(3000);
			}
		}

		int num_of_check = driver.findElements(By.xpath("//table[@id='Form:itemList']/tbody/tr")).size();
		System.out.println(num_of_check);
		for(int i=2;i<=num_of_check;i++) {

			//driver.findElements(By.xpath("//table[@id='Form:itemList']/tbody/tr["+i+"]"));

			////table[@id='Form:itemList']/tbody/tr[2]/td[1]//input[contains(@name,'RecoFailed')]

			List<WebElement> error = driver.findElements(By.xpath("//table[@id='Form:itemList']/tbody/tr["+i+"]/td[1]//input[contains(@name,'RecoFailed')]"));
			report.updateTestLog("Error","Error is" + error.size(), Status.SCREENSHOT);
			if(!error.isEmpty()) {

				error.get(0).click();
				Thread.sleep(10000);

				String message = driver.findElement(By.id("EditCheckForm:EditItemErrorFlags")).getText();

				if(message.contains("Confirm Amount")) {
					driver.findElement(By.id("EditCheckForm:SaveButton")).click();
					Thread.sleep(10000);
					
				}else if(message.contains("Override Duplicate")) {
					driver.findElement(By.id("EditCheckForm:DuplicateOverride")).click();
					Thread.sleep(2000);
					driver.findElement(By.id("EditCheckForm:SaveButton")).click();
					Thread.sleep(5000);
				}
				
				else if(message.contains("Failed Image Quality")) {

					List<WebElement> override = driver.findElements(By.id("EditCheckForm:ImageOverride"));
					if(override.isEmpty()) {

						driver.findElement(By.id("EditCheckForm:DeleteButton")).click();
						Thread.sleep(10000);

						Alert alert1 = driver.switchTo().alert();
						alert1.accept();
						Thread.sleep(5000);
						i--;

						int num_of_check2 = driver.findElements(By.xpath("//table[@id='Form:itemList']/tbody/tr")).size();

						if(num_of_check2==1) {

							driver.findElement(By.id("Form:ReturnToDepositsButton")).click();
							Thread.sleep(10000);

							int colNum_delete2 = flow.getDepositListColumnNumber("Tasks");
							//int colNum_state =  flow.getDepositeListColumnNumber("State");

							driver.findElement(By.xpath("//table[@id='Form:depositList']/tbody/tr[1]/td["+colNum_delete2+"]//input[contains(@id,':EndDeleteDepositButton')]")).click();
							Thread.sleep(3000);
							Alert alert2 = driver.switchTo().alert();
							alert2.accept();

							Assert.assertTrue(driver.findElement(By.xpath("//table[@id='Form:TableMessage']/tbody/tr/td")).getText().contains("has been deleted."));
							report.updateTestLog("Failed Image Quality Error", "Deposit can't be created due to the error and hence Deposit is deleted", Status.SCREENSHOT);
							driver.findElement(By.linkText("Logoff")).click();
							driver.quit();
							
						}

					}else {

						override.get(0).click();
						Thread.sleep(2000);
						driver.findElement(By.id("EditCheckForm:SaveButton")).click();
						Thread.sleep(10000);
					}



				}
				//handle erro
			}
		}

		if(driver!=null) {

			Thread.sleep(3000);

			String currentAmount = driver.findElement(By.id("Form:currentAmount")).getText();

			Thread.sleep(3000);

			driver.findElement(By.id("Form:ExpAmount")).clear();
			driver.findElement(By.id("Form:ExpAmount")).sendKeys(currentAmount);

			Thread.sleep(3000);

			driver.findElement(By.id("Form:SaveButton")).click();

			Thread.sleep(3000);

			driver.findElement(By.id("Form:CompleteButton")).click();

			Thread.sleep(3000);

			Alert alert = driver.switchTo().alert();
			alert.accept();

			Thread.sleep(10000);

			int colNum_delete3 = flow.getDepositListColumnNumber("Tasks");
			int colNum_state3 =  flow.getDepositListColumnNumber("State");

			String state = driver.findElement(By.xpath("//table[@id='Form:depositList']/tbody/tr[1]/td["+colNum_state3+"]")).getText();

			if(state.equalsIgnoreCase("Open-Jammed")) {

				driver.findElement(By.xpath("//table[@id='Form:depositList']/tbody/tr[1]/td["+colNum_delete3+"]//input[contains(@id,':EndDeleteDepositButton')]")).click();
				Thread.sleep(3000);
				Alert alert1 = driver.switchTo().alert();
				alert1.accept();

				Assert.assertTrue(driver.findElement(By.xpath("//table[@id='Form:TableMessage']/tbody/tr/td")).getText().contains("has been deleted."));

			}else {
				Thread.sleep(20000);
			
				report.updateTestLog("Deposit State","Deposit State is" +state, Status.SCREENSHOT);
			}

			driver.findElement(By.linkText("Logoff")).click();
			report.updateTestLog("Logoff","User Loggedout" , Status.PASS);
			//driver.quit();
			
		}
	}catch(Exception e) {
		System.out.println(e);
		driver.quit();
		
	}
	}

	public int getDepositListColumnNumber(String colName) {

		int colNum=0;
		int colCount = driver.findElements(By.xpath("//table[@id='Form:depositList']/thead/tr[1]/th")).size();

		for(int i=1;i<=colCount;i++) {

			String colNameText = driver.findElement(By.xpath("//table[@id='Form:depositList']/thead/tr[1]/th["+i+"]")).getText();
			if(colName.trim().equalsIgnoreCase(colNameText.trim())) {
				colNum=i;
				break;

			}

		}

		return colNum;
	}
	
	public void transmitDeposit() throws InterruptedException, AWTException
	{
		//String TransmitDeposit=dataTable.getData("User_Data", "TransmitDeposit");
		Thread.sleep(2000);
	if(dataTable.getData("User_Data", "TransmitDeposit").equalsIgnoreCase("Yes"))
	{
		String SupervisorId = dataTable.getData("User_Data", "SupervisorId");
		String SupervisorPwd = dataTable.getData("User_Data", "SupervisorPwd");
		
			//Enter customer username and password
			driver.findElement(Commercial_CreateDeposit.UserID).clear();
			driver.findElement(Commercial_CreateDeposit.UserID).sendKeys(SupervisorId);
			driver.findElement(Commercial_CreateDeposit.Password).clear();
			driver.findElement(Commercial_CreateDeposit.Password).sendKeys(SupervisorPwd);
			driver.findElement(Commercial_CreateDeposit.LoginButton).click();
			report.updateTestLog("Login User","User Name : "+ SupervisorId,Status.DONE);
			Thread.sleep(3000);	
			//driver.findElement(Commercial_CreateDeposit.Start).click();
		// click on DEposit List on Home page
			driver.findElement(Commercial_CreateDeposit.DepositList).click();
			Thread.sleep(3000);
		//select Location
		driver.findElement(By.id("Form:altLocationList:0:Select")).click();
		
		//Depositlist table
		Passport_Commercial flow = new Passport_Commercial(scriptHelper);
		int colNum_state =  flow.getDepositListColumnNumber("State");
		String state = driver.findElement(By.xpath("//table[@id='Form:depositList']/tbody/tr[1]/td["+colNum_state+"]")).getText();

		if(state.equalsIgnoreCase("Open-Balanced")) {
			driver.findElement(By.id("Form:depositList:0:transferDeposit")).click();
			driver.findElement(By.id("Form:TransferButton")).click();
			Alert alert1 = driver.switchTo().alert();
			alert1.accept();
			Thread.sleep(10000);
			String state1 = driver.findElement(By.xpath("//table[@id='Form:depositList']/tbody/tr[1]/td["+colNum_state+"]")).getText();
			Thread.sleep(2000);
			report.updateTestLog("Deposit is tramsmitted", "Deposit is transmitted "+state1, Status.SCREENSHOT);	
				
		}
		else if(state.equalsIgnoreCase("Received")){
			report.updateTestLog("Deposit is already tramsmitted", "Deposit is alreday Transmitted "+state, Status.SCREENSHOT);
			
		}
		else {
			report.updateTestLog("Deposit can't be tramsmitted", "Deposit Can not be Transmitted as it is "+state, Status.SCREENSHOT);
		}
		
			
	}
	
	}
}
	