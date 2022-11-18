package com.springframework.springdemo;

import org.springframework.stereotype.Component;

@Component
public class MediaTeck  implements Processor{
	

	public void process() {
		System.out.println("Media Teck Processor");
		
	}
}
