package com.carbonchain.server.exception;

import com.carbonchain.server.constant.ErrorCode;


public class InvalidParamException extends ApplicationException {
	private static final long serialVersionUID = 8075816574212266774L;
	public InvalidParamException() {
		super(ErrorCode.API_PARAM_ERROR);
	}
	public InvalidParamException(String message) {
		super(ErrorCode.API_PARAM_ERROR,message);
	}
}
