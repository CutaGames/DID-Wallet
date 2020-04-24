package com.carbonchain.server.exception;

public class ApplicationException extends ExceptionBase
{
	private static final long serialVersionUID = 1419252059845737624L;

	public ApplicationException(int errorId) 
	{
		super(errorId);
	}
	
	public ApplicationException(int errorId, String errorMessage) 
	{
		super(errorId, errorMessage);
	}

}
