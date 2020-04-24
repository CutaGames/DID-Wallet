package com.carbonchain.server.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import com.carbonchain.server.annotation.OnlyLocalRequest;
import com.carbonchain.server.dto.GeneralJsonResult;
import com.carbonchain.server.mapper.MemberInfoMapper;

@Path("/")
@Consumes({MediaType.APPLICATION_JSON})
public class TestController extends ControllerBase {
	@Autowired
	private MemberInfoMapper mapper;
	
	@GET
	@Path("/")
	public GeneralJsonResult test(String param){
		System.out.println(mapper.selectByPrimaryKey("123"));
		return GeneralJsonResult.success("hello world");
	}
}