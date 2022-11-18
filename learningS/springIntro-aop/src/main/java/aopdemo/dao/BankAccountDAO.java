package aopdemo.dao;

import org.springframework.stereotype.Component;

@Component
public class BankAccountDAO {
	
	public String addAccount() {

		System.out.println(getClass() + "Entered Add Account method");
		return null;
	}
}
