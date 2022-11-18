package aopdemo;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import aopdemo.dao.AccountDAO;
import aopdemo.dao.BankAccountDAO;

public class MainApplication {

	public static void main(String[] args) {
		// Read spring config java class
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
		
		AccountDAO dao =  context.getBean("accountDAO" , AccountDAO.class);
		
		Account acc =  context.getBean("account" , Account.class);
		
		dao.addAccount(acc);
		dao.addAccount();
		
		try {
			List<Account> accs = dao.findAccounts(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dao.setName("Acc1");
		
		dao.setNumber(123);
		
		dao.getName();
		
		dao.getNumber();
		
		
		dao.addUser();
				
		BankAccountDAO dao1 =  context.getBean("bankAccountDAO" , BankAccountDAO.class);
		
		dao1.addAccount();
		
		context.close();

	}

}
