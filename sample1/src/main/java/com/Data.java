package com;
import org.hibernate.Session;    
import org.hibernate.SessionFactory;    
import org.hibernate.Transaction;  
import org.hibernate.boot.Metadata;  
import org.hibernate.boot.MetadataSources;  
import org.hibernate.boot.registry.StandardServiceRegistry;  
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class Data{

	public static void main(String[] args){
		
		/*
		 * StandardServiceRegistry ssr= new
		 * StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
		 * 
		 * Metadata m1= new MetadataSources(ssr).getMetadataBuilder().build();
		 */
		Configuration cfg= new Configuration();
		cfg.configure("hibernate.cfg.xml");
		SessionFactory sf= cfg.buildSessionFactory();

		Session s1=sf.openSession();
		Transaction t1= s1.beginTransaction();

		sample1 e1= new sample1();
		e1.setId(1);
		e1.setFname("dsnbf");
		e1.setLname("dsnbf");
		System.out.println("Object creation successful");
		s1.save(e1);
		System.out.println("Session saving successful");
		t1.commit();
		System.out.println("Transaction successful");
		sf.close();
		s1.close();
	}
}
