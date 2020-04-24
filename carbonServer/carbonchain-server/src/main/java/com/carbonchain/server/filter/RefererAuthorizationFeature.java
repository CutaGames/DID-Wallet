package com.carbonchain.server.filter;

import java.lang.reflect.Method;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

import com.carbonchain.server.annotation.NotAuthorization;
import com.carbonchain.server.annotation.OnlyLocalRequest;
import com.carbonchain.server.annotation.OnlyRemoteRequest;

@Provider
public class RefererAuthorizationFeature implements DynamicFeature{
	@Override
	public void configure(ResourceInfo resourceInfo,
			FeatureContext featurecontext) {
		Method method = resourceInfo.getResourceMethod();
		if(method.isAnnotationPresent(OnlyLocalRequest.class)){
			//调用者来自本机
			featurecontext.register(LocalRequestFilter.class);
		}
		if(method.isAnnotationPresent(OnlyRemoteRequest.class)){
			//调用者来自远程
			featurecontext.register(RemoteRequestFilter.class);
		}
		if(!method.isAnnotationPresent(NotAuthorization.class)){
			//验证身份
			featurecontext.register(AuthorizationFilter.class);
		}
	}
}