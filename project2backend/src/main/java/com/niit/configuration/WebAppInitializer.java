package com.niit.configuration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
// similar to web.xml file configuration
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	public WebAppInitializer() 
	{
		System.out.println("WebAppInitializer is loaded and instantiated..");
	}
	@Override
	protected Class<?>[] getRootConfigClasses() {
		//create beans for dataSource, Session factory, hibernateTransactionManager
		System.out.println("In getRootConfigClasses method");
		return new Class[]{WebAppConfig.class, DBConfiguration.class};
		
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		//Enable webmvc, handler mappings, scan all the components in the base package "com.niit"
		//TODO Auto-generated method stub
		System.out.println("In getServletConfigClasses method");
		return null;
	}
		// forward all the incoming request to dispatcherServlet by specifying the url pattern as '/'
	@Override
	protected String[] getServletMappings() {
		System.out.println("in getServletMappings method");
		return new String[]{"/"};

	}

}
