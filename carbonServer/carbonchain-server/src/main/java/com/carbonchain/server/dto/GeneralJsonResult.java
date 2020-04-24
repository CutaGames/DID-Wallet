package com.carbonchain.server.dto;

/**
 * 
 * @author dengpan
 *
 */
public class GeneralJsonResult extends DtoBase{
	private int code;
	private String message;
	private Object data;

	public static GeneralJsonResult success() {
		return build(0, "success", null);
	}

	public static GeneralJsonResult success(String message, Object object) {
		return build(0, message, object);
	}

	public static GeneralJsonResult success(Object object) {
		return build(0, "success", object);
	}

	public static GeneralJsonResult error() {
		return error(-1);
	}

	public static GeneralJsonResult error(String message) {
		return build(-1, message, null);
	}

	public static GeneralJsonResult error(int code) {
		return build(code, "error", null);
	}

	public static GeneralJsonResult error(int code, String message) {
		return build(code, message, null);
	}

	public static GeneralJsonResult build(int code, String message, Object object) {
		GeneralJsonResult result = new GeneralJsonResult();
		result.setCode(code);
		result.setMessage(message);
		result.setData(object);
		return result;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
