package com.ruixun.entity;

import java.io.Serializable;

public class RestStatus implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6336518513829202543L;
	private int code;
	private String message;
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
	
	
}
