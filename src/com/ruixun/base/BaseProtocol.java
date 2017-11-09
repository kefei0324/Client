package com.ruixun.base;

import java.io.Serializable;

import com.ruixun.entity.RestStatus;

public class BaseProtocol implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7006214449597119380L;
	protected RestStatus resultStatus;
	public RestStatus getResultStatus() {
		return resultStatus;
	}
	public void setResultStatus(RestStatus resultStatus) {
		this.resultStatus = resultStatus;
	}

}
