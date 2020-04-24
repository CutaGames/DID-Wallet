package com.carbonchain.server.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

public class LogUtil {
	public static Logger businessLog;
	public static Logger taskLog;
	
	static {
		businessLog = LoggerFactory.getLogger("business");
		taskLog = LoggerFactory.getLogger("task");
		DefaultLogger defaultLog = new DefaultLogger();
		if (businessLog == null) {
			println("WARNING: Can not get business logger.");
			businessLog = defaultLog;
		}
		if(taskLog == null){
			println("WARNING: Can not get task logger.");
			taskLog = defaultLog;
		}
	}

	private LogUtil() {
	}
	
	public static void println(String msg) {
		System.out.println(msg);
	}
	
	static class DefaultLogger implements Logger  {
		@Override
		public void debug(String arg0) {
			println(arg0);
		}

		@Override
		public void debug(String arg0, Object arg1) {
			println(arg0);
		}

		@Override
		public void debug(String arg0, Throwable arg1) {
			println(arg0);
		}

		@Override
		public void debug(Marker arg0, String arg1) {
			println(arg1);
		}

		@Override
		public void debug(String arg0, Object arg1, Object arg2) {
			println(arg0);
		}

		@Override
		public void debug(Marker arg0, String arg1, Object arg2) {
			println(arg1);
		}

		@Override
		public void debug(Marker arg0, String arg1, Object... arg2) {
			println(arg1);
		}

		@Override
		public void debug(Marker arg0, String arg1, Throwable arg2) {
			println(arg1);
		}

		@Override
		public void debug(Marker arg0, String arg1, Object arg2, Object arg3) {
			println(arg1);
		}

		@Override
		public void error(String arg0) {
			println(arg0);
		}

		@Override
		public void error(String arg0, Object arg1) {
			println(arg0);
		}

		@Override
		public void error(String arg0, Object... arg1) {
			println(arg0);
		}

		@Override
		public void error(String arg0, Throwable arg1) {
			println(arg0);
		}

		@Override
		public void error(Marker arg0, String arg1) {
			println(arg1);
		}

		@Override
		public void error(String arg0, Object arg1, Object arg2) {
			println(arg0);
		}

		@Override
		public void error(Marker arg0, String arg1, Object arg2) {
			println(arg1);
		}

		@Override
		public void error(Marker arg0, String arg1, Object... arg2) {
			println(arg1);
		}

		@Override
		public void error(Marker arg0, String arg1, Throwable arg2) {
			println(arg1);
		}

		@Override
		public void error(Marker arg0, String arg1, Object arg2, Object arg3) {
			println(arg1);
		}

		@Override
		public String getName() {
			return "defualtLogger";
		}

		@Override
		public void info(String arg0) {
			println(arg0);
		}

		@Override
		public void info(String arg0, Object arg1) {
			println(arg0);
		}

		@Override
		public void info(String arg0, Object... arg1) {
			println(arg0);
		}

		@Override
		public void info(String arg0, Throwable arg1) {
			println(arg0);
		}

		@Override
		public void info(Marker arg0, String arg1) {
			println(arg1);
		}

		@Override
		public void info(String arg0, Object arg1, Object arg2) {
			println(arg0);
		}

		@Override
		public void info(Marker arg0, String arg1, Object arg2) {
			println(arg1);
		}

		@Override
		public void info(Marker arg0, String arg1, Object... arg2) {
			println(arg1);
		}

		@Override
		public void info(Marker arg0, String arg1, Throwable arg2) {
			println(arg1);
		}

		@Override
		public void info(Marker arg0, String arg1, Object arg2, Object arg3) {
			println(arg1);
		}

		@Override
		public boolean isDebugEnabled() {
			return false;
		}

		@Override
		public boolean isDebugEnabled(Marker arg0) {
			return false;
		}

		@Override
		public boolean isErrorEnabled() {
			return false;
		}

		@Override
		public boolean isErrorEnabled(Marker arg0) {
			return false;
		}

		@Override
		public boolean isInfoEnabled() {
			return false;
		}

		@Override
		public boolean isInfoEnabled(Marker arg0) {
			return false;
		}

		@Override
		public boolean isTraceEnabled() {
			return false;
		}

		@Override
		public boolean isTraceEnabled(Marker arg0) {
			return false;
		}

		@Override
		public boolean isWarnEnabled() {
			return false;
		}

		@Override
		public boolean isWarnEnabled(Marker arg0) {
			return false;
		}

		@Override
		public void trace(String arg0) {
			println(arg0);
		}

		@Override
		public void trace(String arg0, Object arg1) {
			println(arg0);
		}

		@Override
		public void trace(String arg0, Object... arg1) {
			println(arg0);
		}

		@Override
		public void trace(String arg0, Throwable arg1) {
			println(arg0);
		}

		@Override
		public void trace(Marker arg0, String arg1) {
			println(arg1);
		}

		@Override
		public void trace(String arg0, Object arg1, Object arg2) {
			println(arg0);
		}

		@Override
		public void trace(Marker arg0, String arg1, Object arg2) {
			println(arg1);
		}

		@Override
		public void trace(Marker arg0, String arg1, Object... arg2) {
			println(arg1);
		}

		@Override
		public void trace(Marker arg0, String arg1, Throwable arg2) {
			println(arg1);
		}

		@Override
		public void trace(Marker arg0, String arg1, Object arg2, Object arg3) {
			println(arg1);
		}

		@Override
		public void warn(String arg0) {
			println(arg0);
		}

		@Override
		public void warn(String arg0, Object arg1) {
			println(arg0);
		}

		@Override
		public void warn(String arg0, Object... arg1) {
			println(arg0);
		}

		@Override
		public void warn(String arg0, Throwable arg1) {
			println(arg0);
		}

		@Override
		public void warn(Marker arg0, String arg1) {
			println(arg1);
		}

		@Override
		public void warn(String arg0, Object arg1, Object arg2) {
			println(arg0);
		}

		@Override
		public void warn(Marker arg0, String arg1, Object arg2) {
			println(arg1);
		}

		@Override
		public void warn(Marker arg0, String arg1, Object... arg2) {
			println(arg1);
		}

		@Override
		public void warn(Marker arg0, String arg1, Throwable arg2) {
			println(arg1);
		}

		@Override
		public void warn(Marker arg0, String arg1, Object arg2, Object arg3) {
			println(arg1);
		}

		@Override
		public void debug(String arg0, Object... arg1) {
			println(arg0);
		}
	}
}
