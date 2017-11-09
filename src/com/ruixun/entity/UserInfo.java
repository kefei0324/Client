package com.ruixun.entity;

public class UserInfo extends Protocol {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3114014170237929017L;
	private int id;
	private int status;
	private String createtime;
	private String updatetime;
	private String name;
	private String pwd;
	private String date;
	private int deviceid;
//	private int idenity;// 用户权限

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(int deviceid) {
		this.deviceid = deviceid;
	}
//
//	public int getIdenity() {
//		return idenity;
//	}
//
//	public void setIdenity(int idenity) {
//		this.idenity = idenity;
//	}

}
