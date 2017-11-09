package com.ruixun.protocol;

import java.util.List;

import com.ruixun.base.BaseProtocol;
import com.ruixun.entity.MessageModle;

public class MessageModleList extends BaseProtocol {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6680900125335547361L;
	protected List<MessageModle> resultValue;

	public List<MessageModle> getResultValue() {
		return resultValue;
	}

	public void setResultValue(List<MessageModle> resultValue) {
		this.resultValue = resultValue;
	}
}
