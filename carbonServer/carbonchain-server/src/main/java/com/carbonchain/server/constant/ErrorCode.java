package com.carbonchain.server.constant;

public interface ErrorCode {
	int PARAM_ERROR = 1;
	int DATABASE_FAILURE = 2;
	int API_CALL_FAILURE = 3;
	int REQUEST_UNAUTHORIZED = 4;
	
	//业务异常
	int PASSWORD_NOT_EMPTY = 100;				//密码不能为空
	int SECURITY_QUESTIONS_NOT_EMPTY = 101;		//密保问题不能为空
	int PASSWORD_ERROR = 102;					//密码错误
	
	//系统通用错误码
	int API_PARAM_ERROR = 100000;				//参数错误，详情见message
	
}
