package com.controller;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.config.Config;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{
	
	@Override
	protected Class<?>[] getServletConfigClasses(){
		return new Class[] {Config.class};
	}

	@Override
	protected String[] getServletMappings(){
		return new String[] {"/"};
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return null;
	}

}
