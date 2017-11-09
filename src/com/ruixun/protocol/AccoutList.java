package com.ruixun.protocol;

import java.util.List;

import com.ruixun.base.BaseProtocol;
import com.ruixun.entity.UserInfo;

public class AccoutList extends BaseProtocol {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5435154132237480495L;
	protected List<UserInfo> resultValue;

	public List<UserInfo> getResultValue() {
		return resultValue;
	}

	public void setResultValue(List<UserInfo> resultValue) {
		this.resultValue = resultValue;
	}

}
