package com.ruixun.engine;

import org.androidannotations.annotations.rest.Accept;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.ruixun.base.Constants;
import com.ruixun.protocol.Account;
import com.ruixun.protocol.AccoutList;
import com.ruixun.protocol.Default;
import com.ruixun.protocol.Device;
import com.ruixun.protocol.DeviceList;
import com.ruixun.protocol.MessageList;
import com.ruixun.protocol.MessageModleList;

@Rest(rootUrl = Constants.ROOT_URL, converters = { FormHttpMessageConverter.class, StringHttpMessageConverter.class,
		MappingJackson2HttpMessageConverter.class }, interceptors = { HttpBasicAuthenticatorInterceptor.class })

public interface RestClient {

	void setRootUrl(String rootUrl);

	// 获取设备列表
	@Get("/device/getDeviceList")
	@Accept(MediaType.APPLICATION_JSON)
	DeviceList getDeviceList();

	// 注册关联设备
	@Post("/account/register?name={name}&pwd={pwd}&deviceId={deviceId}")
	@Accept(MediaType.APPLICATION_JSON)
	Account register(String name, String pwd, int deviceId);

	// 登陆关联设备
	@Post("/account/login?name={name}&pwd={pwd}&deviceId={deviceId}")
	@Accept(MediaType.APPLICATION_JSON)
	Account login(String name, String pwd, int deviceId);

	// 获取信息列表
	@Post("/msg/getMsg?accountId={accountid}&deviceId={deviceid}&page={page}")
	@Accept(MediaType.APPLICATION_JSON)
	MessageList getMsgList(int accountid, int deviceid, int page);
	// 获取信息列表
	@Post("/msg/getMsgModleList?accountId={accountid}&deviceId={deviceid}&page={page}")
	@Accept(MediaType.APPLICATION_JSON)
	MessageModleList getMsgModleList(int accountid, int deviceid, int page);

	// 获取信息及模板
	@Post("/msg/getMsgModle?msgid={msgid}&userid={userid}")
	@Accept(MediaType.APPLICATION_JSON)
	MessageList getMsgModle(int msgid,int userid);

	// 修改用户操作设备权限
	@Post("/account/modifyIdenity?accountid={accountid}&deviceid={deviceid}")
	@Accept(MediaType.APPLICATION_JSON)
	Account modifyIdentity(int accountid, int deviceid);

	// 创建新设备
	@Post("/device/createNewDevice?name={name}&remark={remark}&identity={identity}")
	@Accept(MediaType.APPLICATION_JSON)
	Device createNewDevice(String name, String remark, int identity);

	// 获取设备信息
	@Post("/device/getDeviceInfo?deviceid={deviceId}")
	@Accept(MediaType.APPLICATION_JSON)
	Device getDeviceInfo(int deviceId);

	// 删除设备
	@Post("/device/deleteDevice?deviceid={deviceid}&identity={identity}")
	@Accept(MediaType.APPLICATION_JSON)
	Default deleteDevice(int deviceid, int identity);

	// 获取用户列表
	@Post("/device/getAccountList?deviceid={deviceid}&identity={identity}")
	@Accept(MediaType.APPLICATION_JSON)
	AccoutList getAccountList(int deviceid, int identity);

	// 删除用户
	@Post("/device/deleteAccount?accountid={accountid}&identity={identity}")
	@Accept(MediaType.APPLICATION_JSON)
	Default deleteAccount(int accountid, int identity);

	// 修改密码
	@Post("/account/modifyPWD?name={name}&pwd={pwd_old}&new_pwd={new_pwd}&deviceId={deviceid}")
	@Accept(MediaType.APPLICATION_JSON)
	Account modifyPWD(String name, String pwd_old, String new_pwd, int deviceid);

}
