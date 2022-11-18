package com.sample3;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Data {
	
	 public static void main(String[] args) {    
	      
	     StandardServiceRegistry ssr=new StandardServiceRegistryBuilder().configure("hibernat.cfg.xml").build();  
	     Metadata meta=new MetadataSources(ssr).getMetadataBuilder().build();  
	       
	     SessionFactory factory=meta.buildSessionFactory();  
	     Session session=factory.openSession();  
	       
	    Transaction t=session.beginTransaction();    
	        
	    ArrayList<String> list1=new ArrayList<String>();    
	    list1.add("Java is a programming language");    
	    list1.add("Java is a platform");    
	        
	    ArrayList<String> list2=new ArrayList<String>();    
	    list2.add("Servlet is an Interface");    
	    list2.add("Servlet is an API");    
	        
	    ListSample question1=new ListSample();    
	    question1.setQname("What is Java?");    
	    question1.setAnswers(list1);    
	        
	    ListSample question2=new ListSample();    
	    question2.setQname("What is Servlet?");    
	    question2.setAnswers(list2);    
	        
	    session.persist(question1);    
	    session.persist(question2);    
	        
	    t.commit();    
	    session.close();    
	    System.out.println("success");    
	 }    

}
