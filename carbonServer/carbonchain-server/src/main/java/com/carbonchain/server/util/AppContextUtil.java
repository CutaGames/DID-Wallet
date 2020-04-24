package com.carbonchain.server.util;

import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class AppContextUtil {
	private static AnnotationConfigWebApplicationContext context;
	private AppContextUtil() {
	}
	public static void setApplicationContext(AnnotationConfigWebApplicationContext ctx) {
		AppContextUtil.context = ctx;
	}
	public static AnnotationConfigWebApplicationContext getApplicationContext() {
		return context;
	}
	public static <T> T getBean(Class<T> type) {
		return context.getBean(type);
	}
	public static void close() {
		if(context != null) {
			try {
				context.close();
			}catch (Exception e) {}
		}
	}
}