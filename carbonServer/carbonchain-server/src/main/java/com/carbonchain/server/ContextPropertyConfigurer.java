package com.carbonchain.server;

import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.google.common.collect.Maps;

public class ContextPropertyConfigurer extends PropertyPlaceholderConfigurer {
	private static Map<String,String> ctxPropMap = Maps.newHashMap();
	
	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException {
		super.processProperties(beanFactoryToProcess, props);
		for (Object key : props.keySet()){
			String keyStr = key.toString();
			String value = String.valueOf(props.get(keyStr));
            ctxPropMap.put(keyStr,value);
        }
	}
	
	public static String getProperty(String key){
		return ctxPropMap.get(key);
	}
}
