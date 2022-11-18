package com.sample3;


import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

public class DataFetch {
	
	 public static void main(String[] args) {    
	      
	     StandardServiceRegistry ssr=new StandardServiceRegistryBuilder().configure("hibernat.cfg.xml").build();  
	     Metadata meta=new MetadataSources(ssr).getMetadataBuilder().build();  
	       
	     SessionFactory factory=meta.buildSessionFactory();  
	     Session session=factory.openSession();  
	     
	     Query query=session.createQuery("from Question");    
	     List<ListSample> list=query.getResultList();    
	         
	     Iterator<ListSample> itr=list.iterator();    
	     while(itr.hasNext()){    
	    	 ListSample q=itr.next();    
	         System.out.println("Question Name: "+q.getQname());    
	                
	         List<String> list2=q.getAnswers();    
	         Iterator<String> itr2=list2.iterator();    
	         while(itr2.hasNext()){    
	             System.out.println(itr2.next());    
	         }    
	             
	     }    
	     session.close();    
	     System.out.println("success");    
	         
	 }       

	    

}
