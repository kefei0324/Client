package com.ruixun.protocol;

import com.ruixun.base.BaseProtocol;
import com.ruixun.entity.UserInfo;

public class Account extends BaseProtocol {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4259272250712321505L;
	private UserInfo resultValue;

	public UserInfo getResultValue() {
		return resultValue;
	}

	public void setResultValue(UserInfo resultValue) {
		this.resultValue = resultValue;
	}

	
	
}
