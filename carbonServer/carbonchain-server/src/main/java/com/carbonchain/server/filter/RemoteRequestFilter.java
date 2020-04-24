package com.carbonchain.server.filter;

import java.io.IOException;
import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.Priorities;

@Priority(Priorities.AUTHENTICATION)
public class RemoteRequestFilter implements ContainerRequestFilter {
	@Override
	public void filter(ContainerRequestContext requestContext)
			throws IOException {
		System.out.println("RemoteRequestAuthorizationFilter");
	}
}
