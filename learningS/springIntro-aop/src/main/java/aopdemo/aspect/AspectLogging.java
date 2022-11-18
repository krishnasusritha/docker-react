package aopdemo.aspect;

import java.util.List;
import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import aopdemo.Account;

@Aspect
@Component
@Order(1)
public class AspectLogging {
	
	// Using Spring outputStream instead of console for logging
	private Logger logger = Logger.getLogger(getClass().getName());
	

	@Pointcut("execution(* aopdemo.dao.*.add*(..))")
	private void pointcut1() {
		logger.info("Entered @PoinCut dec");
	}
	
	@Pointcut("execution(* aopdemo.dao.*.get*(..))")
	private void getter() {
		logger.info("Entered @PoinCut dec");
	}
	
	@Pointcut("execution(* aopdemo.dao.*.set*(..))")
	private void setter() {
		logger.info("Entered @PoinCut dec");
	}
	
	@Pointcut("setter() || getter()")
	private void getterSetter() {
		logger.info("Entered @PoinCut dec");
	}
	
	@Pointcut("pointcut1() && !(getterSetter())")
	private void pointcutExcGetAndSet() {
		logger.info("Entered @PoinCut dec");
	}
	//modifier is optional here
	// Can write it  public void addAccount(), - for all add accounts method
	//public void add*() , - for all method starting with add
	//public void aopdemo.dao.AccountDAO.addAccount(),  - for all the add acounts method in this AccountDAo class
	//public void aopdemo.dao.*.addAccount(),  - for all the add acounts method in this package
	//public void aopdemo.dao.*.*(),  - for all the methods in this package
	// public * add*(), - for any return type starting with add
	//* add*() - for any modifier for any return type starting with add
	//() - match a method with no args
	//(*) - match a method with one arg with any typee
	//(..) - match a method with any number of args with any typee
	//(aopdemo.Account) -  match a method with one arg with Account typee
	//(aopdemo.Account, ..) -  match a method with one arg with Account typee and any args after Account
	@Before("pointcutExcGetAndSet()")
	public void logging(JoinPoint jp) {
		Object[] args =  jp.getArgs();
		for(Object arg : args) {
			if(arg instanceof Account) {
				Account acc = (Account) arg;
				logger.info(acc.getName() + " " + acc.getLevel());
			}
			logger.info(arg.toString());
		}
		logger.info("-------------------Executing Aspect @Before");
	}
	
	@AfterReturning(pointcut = "execution(* aopdemo.dao.AccountDAO.findAccounts(..))",  returning="result")
	public void afterReturnLog(JoinPoint jp, List<Account> result) {
		for(Account arg : result) {
			logger.info(arg.toString());
		}
		logger.info("==========================Executing Aspect @@AfterReturning");
	}
	
	@AfterThrowing(pointcut = "execution(* aopdemo.dao.AccountDAO.findAccounts(..))",  throwing="result")
	public void afterExceptionLog(JoinPoint jp, Throwable result) {

		logger.info("==========================Executing Aspect @@AfterThrowing " + result.getMessage());
	}
	
	// After advise is always executed after After throwing / After Returning from Spring 5.2.7
	@After("execution(* aopdemo.dao.AccountDAO.findAccounts(..))")
	public void afterLog(JoinPoint jp) {
		
		logger.info("==========================Executing Aspect @After");
	}
	
	@Around("execution(* aopdemo.dao.AccountDAO.findAccounts(..))")
	public void aroundLog(ProceedingJoinPoint jp) {
		
		logger.info("==========================Executing Aspect @@Around");
		
		long begin = System.currentTimeMillis();
		
		try {
			Object result =  jp.proceed();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			logger.info("Handled the exception");
		}
		
		long end = System.currentTimeMillis();
		
		long duration = end - begin;
		
		logger.info("==========================Executing Aspect @@Around return " + duration);
	}
}
