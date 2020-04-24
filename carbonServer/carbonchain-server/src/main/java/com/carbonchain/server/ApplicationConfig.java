package com.carbonchain.server;

import javax.servlet.ServletConfig;
import javax.ws.rs.core.Context;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class ApplicationConfig extends ResourceConfig {
	public static String controllerPackage = "com.carbonchain.server";
	
	public ApplicationConfig(@Context ServletConfig servletConfig){
		packages(controllerPackage);
		register(JacksonFeature.class);
		register(CustomFeature.class);
		//EncodingFilter.enableFor(this, GZipEncoder.class);
	}
}