package com.carbonchain.server.exception;

public abstract class ExceptionBase extends Exception {
	private static final long serialVersionUID = 6701872032562149798L;
	protected int errorId;
	protected String errorMessage;
	protected Exception raw;

	public ExceptionBase(int errorId) {
		this.errorId = errorId;
	}

	public ExceptionBase(int errorId, Exception raw) {
		this.errorId = errorId;
		this.raw = raw;
	}

	public ExceptionBase(int errorId, String errorMessage) {
		this.errorId = errorId;
		this.errorMessage = errorMessage;
	}

	public ExceptionBase(int errorId, String errorMessage, Exception raw) {
		this.errorId = errorId;
		this.errorMessage = errorMessage;
		this.raw = raw;
	}

	public int getErrorId() {
		return this.errorId;
	}

	public String getErrorMessage() {
		return this.errorMessage;
	}

	public Exception getRaw() {
		return this.raw;
	}

	public void setRaw(Exception e) {
		this.raw = e;
	}

	@Override
	public String getMessage() {
		return this.getErrorMessage();
	}
}
