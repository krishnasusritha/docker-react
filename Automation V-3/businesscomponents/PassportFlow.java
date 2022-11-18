package businesscomponents;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import org.junit.Assert;

import java.io.FileInputStream;		
import java.io.FileNotFoundException;		
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class PassportFlow {

	public static void main(String[] args) throws InterruptedException {
		try {
			// TODO Auto-generated method stub
			//Instantiate browser
			//System.setProperty("webdriver.chrome.driver","C:\\Users\\mg185281\\Downloads\\chromedriver_win32\\chromedriver.exe");
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\BrowserServers\\chromedriver.exe");
			WebDriver driver = new ChromeDriver();
			driver.manage().window().maximize();
			//Launch browser
			driver.get("https://153.71.28.119:9443/CPWECompletion/StartLogin.faces?");
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

			driver.findElement(By.id("Form:UserIDInput")).sendKeys("wm_op1");
			Thread.sleep(2000);

			driver.findElement(By.id("Form:PasswordInput")).sendKeys("oooo");
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
					System.out.println(tableMessage);

					if(tableMessage.contains("There is a deposit currently in process")) {

						String state = driver.findElement(By.xpath("//table[@id='Form:depositList']/tbody/tr[1]/td[10]")).getText();

						if(state.equalsIgnoreCase("Open-Processing")) { 

							driver.findElement(By.xpath("//table[@id='Form:depositList']/tbody/tr[1]/td[1]//input[contains(@id,':EndDeleteDepositButton')]")).click();
							Thread.sleep(3000);
							Alert alert1 = driver.switchTo().alert();
							alert1.accept();
							Thread.sleep(3000);
							Assert.assertTrue(driver.findElement(By.xpath("//table[@id='Form:TableMessage']/tbody/tr/td")).getText().contains("has been deleted."));

							driver.findElement(By.id("Form:CreateDepositButton")).click();
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

							driver.findElement(By.id("Form:ExpectedAmountInput")).sendKeys("10");
							driver.findElement(By.id("Form:StartDepositButton")).click();
							Thread.sleep(10000);

						}else {
							System.out.println("state : "+ state);
						}

					}else if(tableMessage.contains("An end capture has been performed")) {

						driver.findElement(By.xpath("//input[@value='Add Items']")).click();
						Thread.sleep(3000);
					}else {

						//Enter deposit amount


						driver.findElement(By.id("Form:ExpectedAmountInput")).sendKeys("10");
						driver.findElement(By.id("Form:StartDepositButton")).click();
						Thread.sleep(10000);

						Assert.assertEquals(driver.findElement(By.xpath("//span[contains(./text(),'Deposit Item List')]"))!=null,true);

					}

				}else {

					driver.findElement(By.id("Form:ExpectedAmountInput")).sendKeys("10");
					driver.findElement(By.id("Form:StartDepositButton")).click();
					Thread.sleep(10000);

					Assert.assertEquals(driver.findElement(By.xpath("//span[contains(./text(),'Deposit Item List')]"))!=null,true);
				}
			}catch(Exception e) {
				System.out.println(e);
			}


			
			

			int num_of_check = driver.findElements(By.xpath("//table[@id='Form:itemList']/tbody/tr")).size();
			System.out.println(num_of_check);
			for(int i=2;i<=num_of_check;i++) {

				//driver.findElements(By.xpath("//table[@id='Form:itemList']/tbody/tr["+i+"]"));

				////table[@id='Form:itemList']/tbody/tr[2]/td[1]//input[contains(@name,'RecoFailed')]

				List<WebElement> error = driver.findElements(By.xpath("//table[@id='Form:itemList']/tbody/tr["+i+"]/td[1]//input[contains(@name,'RecoFailed')]"));
				System.out.println("error : "+error.size());
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

			String state = driver.findElement(By.xpath("//table[@id='Form:depositList']/tbody/tr[1]/td[10]")).getText();

			if(state.equalsIgnoreCase("Open-Jammed")) {

				driver.findElement(By.xpath("//table[@id='Form:depositList']/tbody/tr[1]/td[1]//input[contains(@id,':EndDeleteDepositButton')]")).click();
				Thread.sleep(3000);
				Alert alert1 = driver.switchTo().alert();
				alert1.accept();

				Assert.assertTrue(driver.findElement(By.xpath("//table[@id='Form:TableMessage']/tbody/tr/td")).getText().contains("has been deleted."));

			}else {
				System.out.println("state : "+ state);
			}

			driver.findElement(By.linkText("Logoff")).click();

			driver.quit();

		}catch(Exception e) {
			System.out.println(e);

		}
	}
}
