package com.carbonchain.server.filter;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.Priorities;
import org.apache.commons.beanutils.BeanUtils;
import com.carbonchain.server.ServerBootstrap;
import com.carbonchain.server.constant.Constants;
import com.carbonchain.server.dto.RequestHeadParameter;
import com.carbonchain.server.util.JsonUtil;
import com.carbonchain.server.util.LogUtil;
import com.carbonchain.server.util.ParameterUtili;
import com.google.common.collect.Lists;

@PreMatching
@Priority(Priorities.AUTHENTICATION)
public class LocalRequestFilter extends FilterBase implements ContainerRequestFilter {
	private List<String> localHost = Lists.newArrayList("127.0.0.1","localhost","0.0.0.0");
	
	@Override
	public void filter(ContainerRequestContext requestContext)
			throws IOException {
		try{
			URI baseUri = requestContext.getUriInfo().getBaseUri();
			String host = baseUri.getHost();
			int port = baseUri.getPort();
			
			if(!localHost.contains(host) || port != ServerBootstrap.httpPort){
				unAuthorized(requestContext,null);
				return;
			}
			MultivaluedMap<String, String> headers = requestContext.getHeaders();
			RequestHeadParameter parameter = new RequestHeadParameter();
			try {
				BeanUtils.populate(parameter, headers);
			} catch (Exception e) {
				LogUtil.businessLog.error(JsonUtil.obj2Json(headers), e);
			}
			ParameterUtili.notNullAndEmpty(new Object[]{parameter,parameter.getAuth(),parameter.getPlatform(),parameter.getPuk()});
			
			if(!parameter.getPlatform().equals(Constants.CLIENT_LOCAL)){
				unAuthorized(requestContext,null);
				return;
			}
		}catch (Exception e) {
			unAuthorized(requestContext,null);
			return;
		}
	}
}