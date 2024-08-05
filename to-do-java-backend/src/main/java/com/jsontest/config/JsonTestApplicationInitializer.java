package com.jsontest.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/*public class JsonTestApplicationInitializer implements WebApplicationInitializer*/

public class JsonTestApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		Class arr[]= { CommonConfig.class};
		
		return arr;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		
		Class arr[]= {JsonTestAppConfig.class};
		
		return arr;
	}

	@Override
	protected String[] getServletMappings() {
		// TODO Auto-generated method stub
		
		String arr[]= {"/"};
		return arr;
	}

	/*@Override*/
	/*public void onStartup(ServletContext servletContext) throws ServletException {
		// TODO Auto-generated method stub
		
		
		 * XmlWebApplicationContext webApplicationContext=new
		 * XmlWebApplicationContext(); webApplicationContext.setConfigLocation(null);
		 
		
		AnnotationConfigWebApplicationContext webApplicationContext=new AnnotationConfigWebApplicationContext();
		webApplicationContext.register(JsonTestAppConfig.class);
		
		
		DispatcherServlet dispatcherServlet=new DispatcherServlet(webApplicationContext);
		ServletRegistration.Dynamic myCustomDispatcherServlet=servletContext.addServlet("myDispatcherServlet", dispatcherServlet);
		myCustomDispatcherServlet.setLoadOnStartup(1);
		myCustomDispatcherServlet.addMapping("/");

	}*/
	
	
	
	

}
