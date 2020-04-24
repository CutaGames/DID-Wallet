package com.carbonchain.server.controller;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import com.carbonchain.server.dto.GeneralJsonResult;
import com.carbonchain.server.exception.ApplicationException;

@Provider
public class ExceptionMapperSupport implements ExceptionMapper<Exception> {
    
	@Override
	public Response toResponse(Exception exception) {
		exception.printStackTrace();
		String message = "error";
        int code = -1;
        if (exception instanceof ApplicationException) {  
        	
        }else if (exception instanceof WebApplicationException) {  
        	WebApplicationException webException = (WebApplicationException)exception;
        	return webException.getResponse();
        }
        else{  
        }
        return Response.ok(GeneralJsonResult.error(code, message), MediaType.APPLICATION_JSON).status(Status.OK)  
                .build();
	}
}