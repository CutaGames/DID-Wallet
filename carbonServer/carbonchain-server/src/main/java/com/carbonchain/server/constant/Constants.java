package com.carbonchain.server.constant;

public interface Constants {
	String CHARSET_NAME = "utf-8";
	//成员状态
	int MEMBER_STATE_ACTIVE = 1;			//活跃
	
	//成员角色
	String MEMBER_ROLE_ADMIN = "A";			//管理员
	String MEMBER_ROLE_GENERAL = "G";		//普通成员
	
	//客户端平台
	String CLIENT_REMOTE = "R";				//远程客户端
	String CLIENT_LOCAL = "L";				//本地客户端
}