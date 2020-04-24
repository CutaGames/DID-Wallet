package com.carbonchain.server.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import com.carbonchain.server.dto.GeneralJsonResult;
import com.carbonchain.server.exception.ApplicationException;
import com.carbonchain.server.model.MemberInfo;
import com.carbonchain.server.service.MemberService;

@Path("/member")
@Consumes({MediaType.APPLICATION_JSON})
public class MemberController extends ControllerBase {
	@Autowired
	private MemberService memberService;
	
	/**
	 * 新增成员
	 * @param member
	 * @return
	 * @throws ApplicationException
	 */
	@POST
	@Path("/add")
	public GeneralJsonResult addMember(MemberInfo member) throws ApplicationException{
		return GeneralJsonResult.success(memberService.addMember(member));
	}


}