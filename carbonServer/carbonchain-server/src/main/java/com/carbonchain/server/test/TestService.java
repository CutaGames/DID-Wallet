package com.carbonchain.server.test;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.carbonchain.server.SpringConfig;
import com.carbonchain.server.model.MemberInfo;
import com.carbonchain.server.service.MemberService;
import com.carbonchain.server.util.SecurityUtil;


public class TestService {

	public static void main(String[] args) throws Exception {
		AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
		ctx.register(SpringConfig.class);
		ctx.refresh();
		MemberService memberService = ctx.getBean(MemberService.class);
		MemberInfo member = new MemberInfo();
		//member.setPassword("123456");
		//member.setSecurityQuestions("asdfsdfsd");
		//memberService.addMember(member);
		//System.out.println(DigestUtils.md5Hex("123456"));
		System.out.println(SecurityUtil.encode3Des("a132e370a98fe09c4777dbe12060c43e", "{password:123456}"));
	}
}