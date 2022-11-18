package businesscomponents;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import supportlibraries.Reports.Reports2;

import org.junit.Assert;

import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class PassportFlow2 {

	//System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\BrowserServers\\chromedriver.exe");
	static WebDriver driver;
	static List<String> PassedTestCases = new LinkedList<String>();
	static List<String> FailedTestCases = new LinkedList<String>();
	
	//Reports2 report = new Reports2();

	public static void main(String[] args) throws InterruptedException {
		try {
			
			//Reports2 report = new Reports2();
			
			;
			
			Reports2.createReportsDirectory();
			Reports2.createExecutionsDirectory();
			Reports2.createCurrentExecutionDirectory();

			Properties prop = new Properties();
			prop.load(new FileInputStream("LoginDetails.properties"));
			
			
			PassportFlow2 flow = new PassportFlow2();
			// TODO Auto-generated method stub
			//Instantiate browser
			//System.setProperty("webdriver.chrome.driver","C:\\Users\\mg185281\\Downloads\\chromedriver_win32\\chromedriver.exe");
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\BrowserServers\\chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			//Launch browser
			driver.get(prop.getProperty("login.URL"));
			Thread.sleep(3000);

			//Identify Objects
			/*Select dropdown = new Select(driver.findElement(By.id("identifier")));
		dropdown.selectByVisibleText("Customer");
		//driver.findElement(By.xpath("//input[@id=\"Form:StartButton\"]")).click();*/

			//check box for failed image
			//driver.findElement(By.xpath("//input[@id="EditCheckForm:ImageOverride"]")).click();

			driver.findElement(By.id("Form:StartButton")).click();
			Thread.sleep(2000);

			//Enter credentials

			driver.findElement(By.id("Form:UserIDInput")).sendKeys(prop.getProperty("login.userName"));
			Thread.sleep(2000);

			driver.findElement(By.id("Form:PasswordInput")).sendKeys(prop.getProperty("login.passWord"));
			Thread.sleep(2000);

			driver.findElement(By.id("Form:LoginButton")).click();
			Thread.sleep(10000);

			//home page
			driver.findElement(By.xpath("//img[@src=\"images/welcomelink_newdeposit.gif\"]")).click();
			Thread.sleep(3000);

			//Validate Deposite Inprogress
			try {

				List<WebElement> tableMessage1 = driver.findElements(By.xpath("//table[@id='Form:TableMessage']"));
				if(!tableMessage1.isEmpty()) {
					String tableMessage = driver.findElement(By.xpath("//table[@id='Form:TableMessage']")).getText();
					Reports2.writeToLog(tableMessage);

					if(tableMessage.contains("There is a deposit currently in process")) {

						int colNum_delete = flow.getDepositeListColumnNumber("Tasks");
						int colNum_state =  flow.getDepositeListColumnNumber("State");

						String state = driver.findElement(By.xpath("//table[@id='Form:depositList']/tbody/tr[1]/td["+colNum_state+"]")).getText();

						if(state.equalsIgnoreCase("Open-Processing")) { 

							driver.findElement(By.xpath("//table[@id='Form:depositList']/tbody/tr[1]/td["+colNum_delete+"]//input[contains(@id,':EndDeleteDepositButton')]")).click();
							Thread.sleep(3000);
							Alert alert1 = driver.switchTo().alert();
							alert1.accept();
							Thread.sleep(3000);
							Assert.assertTrue(driver.findElement(By.xpath("//table[@id='Form:TableMessage']/tbody/tr/td")).getText().contains("has been deleted."));

							driver.findElement(By.id("Form:CreateDepositButton")).click();
							Thread.sleep(3000);
							
							//location selection
							
							WebElement location = driver.findElement(By.id("Form:LocationInput"));
							
							Select select = new Select(location);
							select.selectByIndex(0);
							Thread.sleep(3000);

							driver.findElement(By.id("Form:ExpectedAmountInput")).sendKeys("10");
							driver.findElement(By.id("Form:StartDepositButton")).click();
							Thread.sleep(10000);


							Assert.assertEquals(driver.findElement(By.xpath("//span[contains(./text(),'Deposit Item List')]"))!=null,true);

						}else if(state.equalsIgnoreCase("Open-Jammed")) {

							driver.findElement(By.xpath("//table[@id='Form:depositList']/tbody/tr[1]/td[1]//input[contains(@id,':EndDeleteDepositButton')]")).click();
							Thread.sleep(3000);
							Alert alert1 = driver.switchTo().alert();
							alert1.accept();

							Assert.assertTrue(driver.findElement(By.xpath("//table[@id='Form:TableMessage']/tbody/tr/td")).getText().contains("has been deleted."));

							driver.findElement(By.id("Form:CreateDepositButton")).click();
							Thread.sleep(3000);
							
							//location selection
							
							WebElement location = driver.findElement(By.id("Form:LocationInput"));
							
							Select select = new Select(location);
							select.selectByIndex(0);
							Thread.sleep(3000);

							driver.findElement(By.id("Form:ExpectedAmountInput")).sendKeys("10");
							driver.findElement(By.id("Form:StartDepositButton")).click();
							Thread.sleep(10000);

						}else {
							Reports2.writeToLog("state : "+ state);
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

						driver.findElement(By.id("Form:ExpectedAmountInput")).sendKeys("10");
						driver.findElement(By.id("Form:StartDepositButton")).click();
						Thread.sleep(10000);

						Assert.assertEquals(driver.findElement(By.xpath("//span[contains(./text(),'Deposit Item List')]"))!=null,true);

					}

				}else {
					
					//location selection
					
					WebElement location = driver.findElement(By.id("Form:LocationInput"));
					
					Select select = new Select(location);
					select.selectByIndex(0);
					Thread.sleep(3000);


					driver.findElement(By.id("Form:ExpectedAmountInput")).sendKeys("10");
					driver.findElement(By.id("Form:StartDepositButton")).click();
					Thread.sleep(10000);

					Assert.assertEquals(driver.findElement(By.xpath("//span[contains(./text(),'Deposit Item List')]"))!=null,true);
				}
			}catch(Exception e) {
				System.out.println(e);
			}


			List<WebElement> tableMessage2 = driver.findElements(By.xpath("//table[@id='Form:TableMessage']"));
			if(!tableMessage2.isEmpty()) {
				String tableMessage = driver.findElement(By.xpath("//table[@id='Form:TableMessage']")).getText();
				Reports2.writeToLog(tableMessage);

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
				Reports2.writeToLog("error : "+error.size());
				if(!error.isEmpty()) {

					error.get(0).click();
					Thread.sleep(10000);

					String message = driver.findElement(By.id("EditCheckForm:EditItemErrorFlags")).getText();

					if(message.contains("Confirm Amount")) {
						driver.findElement(By.id("EditCheckForm:SaveButton")).click();
						Thread.sleep(10000);
					}else if(message.contains("Failed Image Quality")) {

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

								int colNum_delete2 = flow.getDepositeListColumnNumber("Tasks");
								//int colNum_state =  flow.getDepositeListColumnNumber("State");

								driver.findElement(By.xpath("//table[@id='Form:depositList']/tbody/tr[1]/td["+colNum_delete2+"]//input[contains(@id,':EndDeleteDepositButton')]")).click();
								Thread.sleep(3000);
								Alert alert2 = driver.switchTo().alert();
								alert2.accept();

								Assert.assertTrue(driver.findElement(By.xpath("//table[@id='Form:TableMessage']/tbody/tr/td")).getText().contains("has been deleted."));

								driver.findElement(By.linkText("Logoff")).click();
								driver.quit();
								Reports2.writeToLog("Test Passed");
								PassedTestCases.add("Deposit Flow");
								//Reports2.writeToHtml_PassedTestCases(PassedTestCases);
								Reports2.writeToHtmlReports(PassedTestCases, FailedTestCases);
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

				int colNum_delete3 = flow.getDepositeListColumnNumber("Tasks");
				int colNum_state3 =  flow.getDepositeListColumnNumber("State");

				String state = driver.findElement(By.xpath("//table[@id='Form:depositList']/tbody/tr[1]/td["+colNum_state3+"]")).getText();

				if(state.equalsIgnoreCase("Open-Jammed")) {

					driver.findElement(By.xpath("//table[@id='Form:depositList']/tbody/tr[1]/td["+colNum_delete3+"]//input[contains(@id,':EndDeleteDepositButton')]")).click();
					Thread.sleep(3000);
					Alert alert1 = driver.switchTo().alert();
					alert1.accept();

					Assert.assertTrue(driver.findElement(By.xpath("//table[@id='Form:TableMessage']/tbody/tr/td")).getText().contains("has been deleted."));

				}else {
					Thread.sleep(20000);
					Reports2.writeToLog("state : "+ state);
				}

				driver.findElement(By.linkText("Logoff")).click();

				driver.quit();
				
				Reports2.writeToLog("Test Passed"+"\n");
				Reports2.writeToLog("Simple Deposit is successful ");
				PassedTestCases.add("Simple Deposit is successful ");
				//Reports2.writeToHtml_PassedTestCases(PassedTestCases);
				Reports2.writeToHtmlReports(PassedTestCases, FailedTestCases);
			}
		}catch(Exception e) {
			Reports2.writeToLog("Test Failed");
			Reports2.writeToLog(e.toString());
			driver.quit();
			
			FailedTestCases.add("Simple Deposit flow");
			//Reports2.writeToHtml_PassedTestCases(FailedTestCases);
			Reports2.writeToHtmlReports(PassedTestCases, FailedTestCases);
			
			

		}
	}

	public int getDepositeListColumnNumber(String colName) {

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
}
