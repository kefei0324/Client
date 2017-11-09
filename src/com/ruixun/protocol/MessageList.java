package com.ruixun.protocol;

import java.util.List;

import com.ruixun.base.BaseProtocol;
import com.ruixun.entity.Message;

public class MessageList extends BaseProtocol {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1669581073768828020L;
	protected List<Message> resultValue;

	public List<Message> getResultValue() {
		return resultValue;
	}

	public void setResultValue(List<Message> resultValue) {
		this.resultValue = resultValue;
	}
}
