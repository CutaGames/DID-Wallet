package com.carbonchain.server.exception;

public class DataAccessException extends ExceptionBase {
	private static final long serialVersionUID = 2848826117996358087L;
	public DataAccessException(int errorId) {
		super(errorId);
	}
	public DataAccessException(int errorId, String errorMessage) {
		super(errorId, errorMessage);
	}
	public DataAccessException(int errorId, String errorMessage, Exception raw) {
		super(errorId, errorMessage, raw);
	}
}
