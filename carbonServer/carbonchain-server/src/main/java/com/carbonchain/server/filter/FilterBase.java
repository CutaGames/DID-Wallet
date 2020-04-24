package com.carbonchain.server.filter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;
import com.carbonchain.server.constant.ErrorCode;
import com.carbonchain.server.dto.GeneralJsonResult;
import com.carbonchain.server.service.MemberService;
import com.carbonchain.server.util.AppContextUtil;

public class FilterBase {

	protected MemberService memberService;
	
	@PostConstruct
	public void init(){
		if(memberService == null) {
			memberService = AppContextUtil.getBean(MemberService.class);
		}
	}
	
	protected String streamToString(InputStream entityStream) throws IOException{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = entityStream.read(buffer)) > -1) {
            baos.write(buffer, 0, len);
        }
        baos.flush();
        InputStream in = new ByteArrayInputStream(baos.toByteArray());
        StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = in.read(b)) != -1;) {
			out.append(new String(b, 0, n));
		}
		return out.toString();
	}
	
	protected void unAuthorized(ContainerRequestContext requestContext,String errorMsg){
		if(StringUtils.isBlank(errorMsg)){
			errorMsg = "request unauthorized";
		}
		requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(GeneralJsonResult.error(ErrorCode.REQUEST_UNAUTHORIZED, errorMsg))
				.type(MediaType.APPLICATION_JSON).build());
	}
}
