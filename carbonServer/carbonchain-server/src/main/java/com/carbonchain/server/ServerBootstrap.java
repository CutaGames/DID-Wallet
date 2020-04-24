package com.carbonchain.server;

import com.carbonchain.server.service.wallet.eth.Web3jUtil;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.carbonchain.server.util.AppContextUtil;
import com.carbonchain.server.util.LogUtil;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthFilter;
import org.web3j.protocol.core.methods.response.EthLog;

import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ServerBootstrap
{
	public static int httpPort = 3535;
	private static Server server;
	
	static{
		Runtime.getRuntime().addShutdownHook(new Thread(){
			@Override
	          public void run() {
				ServerBootstrap.stop();
				AppContextUtil.close();
	          }
		});
	}
	
    public static void main( String[] args)
    {
    	try {
	    	ServletHolder servletHolder = new ServletHolder(new ServletContainer());
	    	servletHolder.setInitParameter("javax.ws.rs.Application", ApplicationConfig.class.getCanonicalName());
	    	ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
	    	context.addServlet(servletHolder, "/*");
	    	
	    	AnnotationConfigWebApplicationContext wac = new AnnotationConfigWebApplicationContext();
	    	wac.register(SpringConfig.class);
	    	wac.refresh();
	    	context.addEventListener(new RequestContextListener());
	    	context.addEventListener(new ContextLoaderListener(wac));
	    	AppContextUtil.setApplicationContext(wac);
	    	
	    	String port = ContextPropertyConfigurer.getProperty("server.http.port");
	    	if(StringUtils.isNotBlank(port)){
	    		try{
	    			httpPort = Integer.parseInt(port);
	    		}catch(Exception e){}
	    	}
	    	server = new Server(httpPort);
	    	server.setStopAtShutdown(true);
	    	
	    	httpPort = ((ServerConnector)(server.getConnectors()[0])).getPort();
	        /*FilterHolder filterHolder = context.addFilter(CrossOriginFilter.class, "*", EnumSet.of(DispatcherType.REQUEST));
	    	filterHolder.setInitParameter("allowedOrigins", "*");
	    	filterHolder.setInitParameter("allowedMethods", "GET,POST,OPTIONS");*/
	    	
	    	server.setHandler(context);
			server.start();
			LogUtil.businessLog.info("Server startup on " + httpPort + "...");
			server.join();

			try {
				Web3j clint = Web3jUtil.getClint();
				EthBlock.Block block = clint.blockFlowable(false).blockingLast().getBlock();
				LogUtil.businessLog.info(block.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}catch (Exception e) {
			LogUtil.businessLog.error("Server failed to start!", e);
		}
    }
    
    private static void stop() {
        if (server != null) {
        	try {
				server.stop();
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
    }
}