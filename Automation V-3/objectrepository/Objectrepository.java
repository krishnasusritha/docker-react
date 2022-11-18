package objectrepository;

import org.openqa.selenium.By;

/*
 * UI objects for login page
*/
public class Objectrepository {

	    // Text boxes
		public static final By txtUsername = By.id("Form:UserIDInput");
		public static final By txtPassword = By.id("Form:PasswordInput");
		
		// Buttons
		public static final By btnLogin = By.id("Form:LoginButton");
		
		// Log out link
		public static final By linkLogOut = By.linkText("Logout");
		
		// Continue button in the  logout pop up
		public static final By linkContinueLogOut = By.linkText("Continue");
		
	}
	

