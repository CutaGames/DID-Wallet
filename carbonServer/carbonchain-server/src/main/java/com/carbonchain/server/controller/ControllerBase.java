package com.carbonchain.server.controller;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;

public abstract class ControllerBase {
	@Context 
	protected ContainerRequestContext crc;
	
	protected String currentMemberId() {
		try {
			return (String)crc.getProperty("memberId");
		}catch (Exception e) {
			return null;
		}
	}
	
	protected String currentSignPuk() {
		try {
			return (String)crc.getProperty("signPuk");
		}catch (Exception e) {
			return null;
		}
	}
}
