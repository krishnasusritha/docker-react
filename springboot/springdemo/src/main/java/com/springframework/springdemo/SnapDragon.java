package com.springframework.springdemo;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

//WHen there are two classes implementing an interface
@Primary
@Component
public class SnapDragon implements Processor{
	

	public void process() {
		System.out.println("Snap Dragon Processor");
		
	}

}
