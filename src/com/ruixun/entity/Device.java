package com.ruixun.entity;

import android.text.TextUtils;

public class Device extends Protocol {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1011170791117031129L;
	private int id;
	private String name;
	// private String descrip;
	private String createtime;
	private String updatetime;
	private String remark;
	private int status;

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return remark;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {

		if (!TextUtils.isEmpty(name))
			return name;
		else
			return "";
	}

}
