package sample2;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Data {

	 StandardServiceRegistry ssr=new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();  
	    Metadata meta=new MetadataSources(ssr).getMetadataBuilder().build();  
	      
	    SessionFactory factory=meta.getSessionFactoryBuilder().build();  
	    Session session=factory.openSession();  
	    
	    Transaction t=session.beginTransaction();    
	        
	    User u1=new User();    
	    u1.setName("jsbakf");
	        
	    BankUser b2=new BankUser();    
	    b2.setBankName("xbfjsd");    
	    b2.setNoOfCustomers(50);    
	        
	    CustomerUser c3=new CustomerUser();    
	    c3.setName("dmsknr");    
	    c3.setCustomerType("skjuifh");    
	        
	    session.persist(u1);    
	    session.persist(b2);    
	    session.persist(c3);    
	        
	    t.commit();    
	    session.close();   
	    System.out.println("success");   
	}    
