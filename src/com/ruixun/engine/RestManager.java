package com.ruixun.engine;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.EBean.Scope;
import org.androidannotations.annotations.rest.RestService;

import com.alibaba.fastjson.JSONObject;
import com.ruixun.R;
import com.ruixun.base.App;
import com.ruixun.base.BaseEngine;
import com.ruixun.base.BaseProtocol;
import com.ruixun.base.RestListener;
import com.ruixun.base.Utils;
import com.ruixun.entity.RestStatus;
import com.ruixun.protocol.Account;
import com.ruixun.protocol.AccoutList;
import com.ruixun.protocol.Default;
import com.ruixun.protocol.Device;
import com.ruixun.protocol.DeviceList;
import com.ruixun.protocol.MessageList;
import com.ruixun.protocol.MessageModleList;

import android.util.Log;

@EBean(scope = Scope.Singleton)
public class RestManager extends BaseEngine {

	@RestService
	RestClient restClient;

	public void setRootUrl(String ip) {
		restClient.setRootUrl(ip);
	}

	@AfterInject
	void initRestClient() {

	}

	/**
	 * 请求前验证
	 * 
	 * @param listener
	 * @return
	 */
	private boolean prepare(RestListener listener) {
		if (null != listener) {
			if (checkNet(listener))
				return true;
			else
				return false;
		} else {
			return false;
		}
	}

	/**
	 * check network
	 * 
	 * @return
	 */
	private boolean checkNet(RestListener listener) {
		return Utils.isConnectingToInternet(App.getInstance());
	}

	/**
	 * 网络请求结果
	 * 
	 * @param listener
	 * @param protocol
	 */
	private void notifyResult(RestListener listener, BaseProtocol protocol) {
		dumpProtocol(protocol);
		RestStatus status = protocol.getResultStatus();
		if (status.getCode() == 1) {
			listener.onRest(protocol);
		} else {
			listener.restFail(status.getMessage());
		}
	}

	/**
	 * 输出网络请求日志
	 * 
	 * @param protocol
	 */
	private void dumpProtocol(BaseProtocol protocol) {
		JSONObject jsonObject = (JSONObject) JSONObject.toJSON(protocol);
		Log.v("RestManager", "dumpObject:");
		Log.v("RestManager", jsonObject.toString());
	}

	/**
	 * 设备列表
	 * 
	 * @param listener
	 */
	@Background(delay = 1000)
	public void getDeviceList(RestListener listener) {
		try {
			if (prepare(listener)) {
				DeviceList protocol = restClient.getDeviceList();
				notifyResult(listener, protocol);
			} else {
				listener.restFail(App.getInstance().getString(R.string.config_wrong));
			}
		} catch (Exception e) {
			Log.e("restmanager", e.toString());
			listener.restFail(App.getInstance().getString(R.string.net_wrong));
		}
	}

	/**
	 * 注册并关联设备
	 * 
	 * @param name
	 * @param pwd
	 * @param currentDevice
	 * @param listener
	 */
	@Background
	public void register(String name, String pwd, int deviceid, RestListener listener) {
		try {
			if (prepare(listener)) {
				Account protocol = restClient.register(name, pwd, deviceid);
				notifyResult(listener, protocol);
			} else {
				listener.restFail(App.getInstance().getString(R.string.config_wrong));
			}
		} catch (Exception e) {
			Log.e("restmanager", e.toString());
			listener.restFail(App.getInstance().getString(R.string.net_wrong));
		}
	}
	/**
	 * 注册并关联设备
	 * 
	 * @param name
	 * @param pwd
	 * @param currentDevice
	 * @param listener
	 */
	@Background
	public void register(String name, String pwd, String device, RestListener listener) {
		try {
			if (prepare(listener)) {
				Account protocol = restClient.register(name, pwd, device);
				notifyResult(listener, protocol);
			} else {
				listener.restFail(App.getInstance().getString(R.string.config_wrong));
			}
		} catch (Exception e) {
			Log.e("restmanager", e.toString());
			listener.restFail(App.getInstance().getString(R.string.net_wrong));
		}
	}

	/**
	 * 登陆关联的设备管理
	 * 
	 * @param name
	 * @param pwd
	 * @param currentDevice
	 * @param restListener
	 */
	@Background
	public void login(String name, String pwd, String device, RestListener listener) {
		try {
			if (prepare(listener)) {
				Account protocol = restClient.login(name, pwd, device);
				notifyResult(listener, protocol);
			} else {
				listener.restFail(App.getInstance().getString(R.string.config_wrong));
			}
		} catch (Exception e) {
			Log.e("restmanager", e.toString());
			listener.restFail(App.getInstance().getString(R.string.net_wrong));
		}
	}
	/**
	 * 登陆关联的设备管理
	 * 
	 * @param name
	 * @param pwd
	 * @param currentDevice
	 * @param restListener
	 */
	@Background
	public void login(String name, String pwd, int deviceid, RestListener listener) {
		try {
			if (prepare(listener)) {
				Account protocol = restClient.login(name, pwd, deviceid);
				notifyResult(listener, protocol);
			} else {
				listener.restFail(App.getInstance().getString(R.string.config_wrong));
			}
		} catch (Exception e) {
			Log.e("restmanager", e.toString());
			listener.restFail(App.getInstance().getString(R.string.net_wrong));
		}
	}
	/**
	 * 获取数据列表
	 * 
	 * @param accountId
	 * @param deviceId
	 * @param page
	 * @param listener
	 * @return
	 */
	@Background
	public void getMsgList(int accountId, int deviceId, int page, RestListener listener) {
		try {
			if (prepare(listener)) {
				MessageList protocol = restClient.getMsgList(accountId, deviceId, page);
				notifyResult(listener, protocol);
			} else {
				listener.restFail(App.getInstance().getString(R.string.config_wrong));
			}
		} catch (Exception e) {
			Log.e("restmanager", e.toString());
			listener.restFail(App.getInstance().getString(R.string.net_wrong));
		}
	}

	/**
	 * 获取模板列表
	 * 
	 * @param accountId
	 * @param deviceId
	 * @param page
	 * @param listener
	 */
	@Background
	public void getMsgModleList(int accountId, int deviceId, int page, RestListener listener) {
		try {
			if (prepare(listener)) {
				MessageModleList protocol = restClient.getMsgModleList(accountId, deviceId, page);
				notifyResult(listener, protocol);
			} else {
				listener.restFail(App.getInstance().getString(R.string.config_wrong));
			}
		} catch (Exception e) {
			Log.e("restmanager", e.toString());
			listener.restFail(App.getInstance().getString(R.string.net_wrong));
		}
	}

	/**
	 * 修改用户权限
	 * 
	 * @param accountId
	 *            用户id
	 * @param deviceId
	 *            设备id
	 * @param identity
	 *            权限代码 1 为操作，0为默认
	 */
	@Background
	public void modifyCompetence(int accountId, int deviceId, RestListener listener) {
		try {
			if (prepare(listener)) {
				Account protocol = restClient.modifyIdentity(accountId, deviceId);
				notifyResult(listener, protocol);
			} else {
				listener.restFail(App.getInstance().getString(R.string.config_wrong));
			}
		} catch (Exception e) {
			Log.e("restmanager", e.toString());
			listener.restFail(App.getInstance().getString(R.string.net_wrong));
		}
	}

	// 创建新设备
	@Background
	public void createNewDevice(String name, String des, int identity, RestListener listener) {
		try {
			if (prepare(listener)) {
				Device protocol = restClient.createNewDevice(name, des, identity);
				notifyResult(listener, protocol);
			} else {
				listener.restFail(App.getInstance().getString(R.string.config_wrong));
			}
		} catch (Exception e) {
			Log.e("restmanager", e.toString());
			listener.restFail(App.getInstance().getString(R.string.net_wrong));
		}
	}

	// 获取设备信息
	@Background
	public void getDeviceInfo(int deviceId, RestListener listener) {
		try {
			if (prepare(listener)) {
				Device protocol = restClient.getDeviceInfo(deviceId);
				notifyResult(listener, protocol);
			} else {
				listener.restFail(App.getInstance().getString(R.string.config_wrong));
			}
		} catch (Exception e) {
			Log.e("restmanager", e.toString());
			listener.restFail(App.getInstance().getString(R.string.net_wrong));
		}
	}

	// 通过id删除设备
	@Background
	public void deleteDevice(int deviceid, int identity, RestListener listener) {
		try {
			if (prepare(listener)) {
				Default protocol = restClient.deleteDevice(deviceid, identity);
				notifyResult(listener, protocol);
			} else {
				listener.restFail(App.getInstance().getString(R.string.config_wrong));
			}
		} catch (Exception e) {
			Log.e("restmanager", e.toString());
			listener.restFail(App.getInstance().getString(R.string.net_wrong));
		}
	}

	// 通过设备id查询用户列表
	@Background
	public void getAccountList(int deviceid, int id, RestListener listener) {
		try {
			if (prepare(listener)) {
				AccoutList protocol = restClient.getAccountList(deviceid, id);
				notifyResult(listener, protocol);
			} else {
				listener.restFail(App.getInstance().getString(R.string.config_wrong));
			}
		} catch (Exception e) {
			Log.e("restmanager", e.toString());
			listener.restFail(App.getInstance().getString(R.string.net_wrong));
		}
	}

	// 通过用户id删除用户
	@Background
	public void deleteAccount(int accountid, int status, RestListener listener) {
		try {
			if (prepare(listener)) {
				Default protocol = restClient.deleteAccount(accountid, status);
				notifyResult(listener, protocol);
			} else {
				listener.restFail(App.getInstance().getString(R.string.config_wrong));
			}
		} catch (Exception e) {
			Log.e("restmanager", e.toString());
			listener.restFail(App.getInstance().getString(R.string.net_wrong));
		}
	}

	// 修改密码
	@Background
	public void resetPwd(String name, String pwd_old, String pwd_new, int deviceid, RestListener listener) {
		try {
			if (prepare(listener)) {
				Account protocol = restClient.modifyPWD(name, pwd_old, pwd_new, deviceid);
				notifyResult(listener, protocol);
			} else {
				listener.restFail(App.getInstance().getString(R.string.config_wrong));
			}
		} catch (Exception e) {
			Log.e("restmanager", e.toString());
			listener.restFail(App.getInstance().getString(R.string.net_wrong));
		}
	}

	// 获取数据及模板
	@Background
	public void getMsgModle(int msgid, int userid, RestListener listener) {
		try {
			if (prepare(listener)) {
				MessageList protocol = restClient.getMsgModle(msgid, userid);
				notifyResult(listener, protocol);
			} else {
				listener.restFail(App.getInstance().getString(R.string.config_wrong));
			}
		} catch (Exception e) {
			Log.e("restmanager", e.toString());
			listener.restFail(App.getInstance().getString(R.string.net_wrong));
		}
	}

}
