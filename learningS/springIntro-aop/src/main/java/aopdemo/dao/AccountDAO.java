package aopdemo.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import aopdemo.Account;

@Component
public class AccountDAO {

	public String name;
	
	public int number;
	
	public void addAccount() {

		System.out.println(getClass() + "Entered Add Account method");
	}

	public void addAccount(Account acc) {

		System.out.println(getClass() + "Entered Add Account method" +  acc.toString());
	}
	
	public void addAccount(String acc) {

		System.out.println(getClass() + "Entered Add Account method " +  acc);
	}

	public void addUser() {

		System.out.println(getClass() + "Entered Add User method");
	}

	public String getName() {
		System.out.println(getClass() + "Entered getName method");
		return name;
	}

	public void setName(String name) {
		System.out.println(getClass() + "Entered setName method");
		this.name = name;
	}

	public int getNumber() {
		System.out.println(getClass() + "Entered getNumber method");
		return number;
	}

	public void setNumber(int number) {
		System.out.println(getClass() + "Entered setNumber method");
		this.number = number;
	}
	
	public List<Account> findAccounts(boolean b) throws Exception {
		if(b) {
			throw new Exception("Cannot access this method");
		}
		List<Account> accs = new ArrayList<Account>();
		
		Account temp = new Account("acc1" , "1");
		Account temp2 = new Account("acc2" , "2");
		Account temp3= new Account("acc3" , "3");
		Account temp4 = new Account("acc4" , "4");
		
		accs.add(temp);
		accs.add(temp2);
		accs.add(temp3);
		accs.add(temp4);
		
		return accs;
	}
	
}
