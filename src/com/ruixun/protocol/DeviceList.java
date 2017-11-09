package com.ruixun.protocol;

import java.util.List;

import com.ruixun.base.BaseProtocol;
import com.ruixun.entity.Device;

public class DeviceList extends BaseProtocol {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1170353187189329777L;
	protected List<Device> resultValue;

	public List<Device> getResultValue() {
		return resultValue;
	}

	public void setResultValue(List<Device> resultValue) {
		this.resultValue = resultValue;
	}

}
