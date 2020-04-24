package com.carbonchain.server.dto;

import com.carbonchain.server.model.MemberInfo;

public class MemberInfoDto extends MemberInfo {
	private String privateKey;
	private String authSignPukMD5;

	public String getAuthSignPukMD5() {
		return authSignPukMD5;
	}

	public void setAuthSignPukMD5(String authSignPukMD5) {
		this.authSignPukMD5 = authSignPukMD5;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
}
