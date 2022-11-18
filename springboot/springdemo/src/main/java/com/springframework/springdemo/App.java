package com.springframework.springdemo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Hello world!
 *
 */
//@Configuration
//@ComponentScan(basePackages = "com.springframework.springdemo")
public class App 
{
    public static void main( String[] args )
    {
       ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
       Samsung s = context.getBean(Samsung.class);
       s.config();
    }
}
