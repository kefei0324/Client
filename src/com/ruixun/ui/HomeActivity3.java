package com.ruixun.ui;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemSelect;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import com.ruixun.BuildConfig;
import com.ruixun.R;
import com.ruixun.base.BaseActivity;
import com.ruixun.base.BaseProtocol;
import com.ruixun.base.Constants;
import com.ruixun.base.MD5;
import com.ruixun.base.RestListener;
import com.ruixun.entity.Device;
import com.ruixun.entity.UserInfo;
import com.ruixun.protocol.Account;
import com.ruixun.protocol.DeviceList;
import com.ruixun.ui.accout.LoginFragment;
import com.ruixun.ui.accout.LoginFragment.LoginDialogListener;
import com.ruixun.ui.accout.RegisterFragment;
import com.ruixun.ui.accout.ResetPwdFragment;
import com.ruixun.ui.accout.RegisterFragment.RegisterDialogListener;
import com.ruixun.ui.accout.ResetPwdFragment.ResetDialogListener;
import com.ruixun.ui.welcome.WelcomeDialog;
import com.ruixun.ui.welcome.WelcomeDialog.WelcomeListener;
import com.ruixun.widget.ActionBar;
import com.ruixun.widget.DelayButton;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

@EActivity(R.layout.act_home)
public class HomeActivity3 extends BaseActivity
		implements RestListener, /*RegisterDialogListener, LoginDialogListener, */WelcomeListener/*, ResetDialogListener*/ {

	// /************************/
	//
	// @ViewById(R.id.home_spinner)
	// Spinner spinner;
	// @ViewById(R.id.account_controller)
	// LinearLayout controller;
	// @ViewById(R.id.button_register)
	// DelayButton button_register;
	// @ViewById(R.id.actionbar)
	// ActionBar actionbar;

	@ViewById(R.id.tv_user)
	TextView user;
	@ViewById(R.id.tv_pwd)
	TextView pwd;
	@ViewById(R.id.tv_mt)
	TextView mt;
	// @ViewById(R.id.btn_login)
	// Button login;
	// @ViewById(R.id.btn_register)
	// Button register;
	WelcomeDialog welcome;
	@Override
	protected void loadData() {
		String action = getIntent().getAction();
		if ("reset_or_forget_login_name".equals(action)) {
		} else {
			welcome = new WelcomeDialog();
			welcome.show(getFragmentManager(), "welcome");
		}
		if(appManager.getPrefsBoolean(Constants.APP_KEY_LOGIN_STATE)) {
			login(appManager.getPrefsStr(Constants.APP_KEY_USER_NAME), appManager.getPrefsStr(Constants.APP_KEY_USER_PWD), appManager.getPrefsStr(Constants.APP_KEY_DEVICE_NAME));
		}
	}

	@AfterViews
	void init() {
		String action = getIntent().getAction();
		if ("reset_or_forget_login_name".equals(action)) {
			// selectedDevice(appManager.getCurrentDevice());
			// saveDeviceInfo(appManager.getCurrentDevice());
			// actionbar.setTitleText(appManager.getCurrentDevice().getName());
			appManager.setPrefsBoolean(Constants.APP_KEY_LOGIN_STATE, false);
//			getDeviceList();

		} else {

		}
	}

	boolean verifyName(String name) {
		// String name = et_name.getText().toString();
		// Pattern pattern =
		// Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[01236789]))\\d{8}$");
		// Matcher matcher = pattern.matcher(name);
		return (!TextUtils.isEmpty(name)/* && matcher.matches() && name.length() == 11 */)
				|| (!TextUtils.isEmpty(name) && name.equals("admin"));
	}

	boolean verifyPwd(String pwd) {
		// String pwd = et_pwd.getText().toString();
		return !TextUtils.isEmpty(pwd) && pwd.length() >= 6;
	}

	boolean verifyMt(String mt) {
		return !TextUtils.isEmpty(mt);
	}
	@Click(R.id.btn_login)
	void login() {
		final String name =/*BuildConfig.DEBUG?"18182545812":*/ user.getText().toString();
		final String _pwd =/*BuildConfig.DEBUG?"123456":*/ pwd.getText().toString();
		final String device = mt.getText().toString();
		if (verifyName(name) && verifyPwd(_pwd)&&verifyMt(device)/*||BuildConfig.DEBUG*/) {
			initProgress();
			progressDialog.show();
			login(name, _pwd, device);
		}
	}

	private void login(final String name, final String _pwd, final String device) {
		restManager.login(name,MD5.getMD5(_pwd), device, new RestListener() {

			@Override
			@UiThread
			public void restFail(String res) {
				if (!TextUtils.isEmpty(res)) {
					showToast(res);
				}
			}

			@Override
			@UiThread
			public void onRest(BaseProtocol protocol) {
				Account account = (Account) protocol;
				UserInfo userinfo = account.getResultValue();
				saveAccountInfo(name, _pwd, userinfo);
//					saveDeviceInfo(deviceSelected);
//					if (appManager.getUser().getStatus() == Constants.ADMIN_ACCOUNT_STATUS) {
//						// 管理员登陆
//						// 进入新建设备界面
//						goDeviceListActivity();
//					} else {
//						goMsgInfoActivity();
				getDeviceInfoByid(userinfo.getDeviceid());
//					}
			}
		});
	}

	@Click(R.id.btn_register)
	void register() {
		final String name =/*BuildConfig.DEBUG?"18182545812":*/ user.getText().toString();
		final String _pwd =/*BuildConfig.DEBUG?"123456":*/ pwd.getText().toString();
		final String device = mt.getText().toString();
		if (verifyName(name) && verifyPwd(_pwd)&&verifyMt(device)/*||BuildConfig.DEBUG*/) {
			initProgress();
			progressDialog.show();
			restManager.register(name, MD5.getMD5(_pwd), /* appManager.getCurrentDevice().getId() */device,
			new RestListener() {

				@Override
				@UiThread
				public void restFail(String res) {
					if (!TextUtils.isEmpty(res)) {
						showToast(res);
						// ControlleActivity_.intent(HomeActivity.this).start();
					}
				}

				@Override
				@UiThread
				public void onRest(BaseProtocol protocol) {
					Account account = (Account) protocol;
					UserInfo userinfo = account.getResultValue();
					saveAccountInfo(name, _pwd, userinfo);
					getDeviceInfoByid(userinfo.getDeviceid());
				}

			});
		}
	}

	private void directHome() {
		if(appManager.getPrefsBoolean(Constants.APP_KEY_LOGIN_STATE)) {
//			goMsgInfoActivity();
//			login()
		}
		
		// int deviceStatus = appManager.getPrefsInt(Constants.APP_KEY_DEVICE_STATUS);
//		super.loadData();
		// if (deviceStatus == Constants.ADMIN_DEVICE_STATUS) {
		// // 管理员设备登陆
		// getDeviceList();
		// } else {
		// // 普通用户登陆
		// int deviceId = appManager.getPrefsInt(Constants.APP_KEY_DEVICE_ID);
		// if (deviceId != appManager.getPrefsInt(Constants.APP_KEY_DEVICE_ID))
		// getDeviceList();
		// else
		// getDeviceInfoByid(deviceId);
		// }
		// progressDialog.show();
		// // }
//		progressDialog.show();
//		if (appManager.getPrefsBoolean(Constants.APP_KEY_LOGIN_STATE)) {// 是否已经登录
//			try {
//				if (appManager.getUser().getStatus() == Constants.ADMIN_ACCOUNT_STATUS) {// 是否管理员
//					getDeviceList();
//				} else {// 非管理员
//					int deviceId = appManager.getPrefsInt(Constants.APP_KEY_DEVICE_ID);
//
//					getDeviceInfoByid(deviceId);
//				}
//			} catch (NullPointerException e) {
//				getDeviceList();
//			}
//		} else {// 未登录
//			getDeviceList();
//		}
	}

	private void getDeviceInfoByid(int deviceId) {
		restManager.getDeviceInfo(deviceId, this);// 获取设备详情
	}

	private void getDeviceList() {
		// restManager.getDeviceList(this);// 获取设备列表
	}

	@Override
	@UiThread
	public void onRest(BaseProtocol protocol) {
		if (null != progressDialog && progressDialog.isShowing()) {
			progressDialog.dismiss();
			progressDialog = null;
		}
		if (protocol instanceof DeviceList) {
			// spinner.setVisibility(View.VISIBLE);
			// actionbar.setTitleText(getResources().getString(R.string.device_selected_tip));
			// DeviceList deviceList = (DeviceList) protocol;
			// ArrayAdapter<Device> adapter = new ArrayAdapter<Device>(this,
			// android.R.layout.simple_spinner_dropdown_item,
			// deviceList.getResultValue());
			// spinner.setAdapter(adapter);
		} else if (protocol instanceof com.ruixun.protocol.Device) {
			com.ruixun.protocol.Device device = (com.ruixun.protocol.Device) protocol;
//			if (appManager.getPrefsBoolean(Constants.APP_KEY_LOGIN_STATE)) {
//				selectedDevice(device.getResultValue());
			saveDeviceInfo(device.getResultValue());
			
			goMsgInfoActivity();
//			canGo();
//				loginComplete(appManager.getPrefsStr(Constants.APP_KEY_USER_NAME),
//						appManager.getPrefsStr(Constants.APP_KEY_USER_PWD));
//			} else {
				// spinner.setVisibility(View.INVISIBLE);
				// actionbar.setTitleText(device.getResultValue().getName());
				// selectedDevice(device.getResultValue());
//			}
		}
	}


	@Override
	@UiThread
	public void restFail(String res) {
		if (null != progressDialog && progressDialog.isShowing()) {
			progressDialog.dismiss();
			progressDialog = null;
		}
		if (!TextUtils.isEmpty(res)) {
			showToast(res);
		}
	}

//	@ItemSelect(R.id.home_spinner)
//	void itemSelected(boolean selected, Device device) {
//		selectedDevice(device);
//	}

//	private Device deviceSelected;

//	private void selectedDevice(Device device) {
//		showTestToast(device.getName());
//		// saveDeviceInfo(device);
////		this.deviceSelected = device;
//		// if (device.getStatus() == Constants.ADMIN_DEVICE_STATUS) {
//		// button_register.setEnabled(false);
//		// } else {
//		// button_register.setEnabled(true);
//		// }
//	}

	// 保存设备信息到prefs
	private void saveDeviceInfo(Device device) {
		appManager.setCurrentDevice(device);
		appManager.setPrefsInt(Constants.APP_KEY_DEVICE_STATUS, device.getStatus());
		appManager.setPrefsStr(Constants.APP_KEY_DEVICE_NAME, device.getName());
	}

	private void saveAccountInfo(final String name, final String pwd, UserInfo userinfo) {
		appManager.setUser(userinfo);
		appManager.setPrefsStr(Constants.APP_KEY_USER_NAME, name);
		appManager.setPrefsStr(Constants.APP_KEY_USER_PWD, pwd);
	}

//	@Click
//	void button_register() {
//		RegisterFragment fragment = new RegisterFragment();
//		fragment.show(getFragmentManager(), "register");
//	}
//
//	@Click
//	void button_login() {
//		LoginFragment fragment = new LoginFragment();
//
//		Bundle b = new Bundle();
//		// if (null != appManager.getCurrentDevice()) {
//		b.putString("name", appManager.getPrefsStr(Constants.APP_KEY_USER_NAME));
//		b.putString("pwd", appManager.getPrefsStr(Constants.APP_KEY_USER_PWD));
//		fragment.setArguments(b);
//		fragment.show(getFragmentManager(), "login");
//		// } else {
//		// showToast(R.string.no_device);
//		// }
//	}

//	@Override
//	public void registerComplete(final String name, final String pwd) {
//		restManager.register(name, MD5.getMD5(pwd), /* appManager.getCurrentDevice().getId() */deviceSelected.getId(),
//				new RestListener() {
//
//					@Override
//					@UiThread
//					public void restFail(String res) {
//						if (!TextUtils.isEmpty(res)) {
//							showToast(res);
//							// ControlleActivity_.intent(HomeActivity.this).start();
//						}
//					}
//
//					@Override
//					@UiThread
//					public void onRest(BaseProtocol protocol) {
//						Account account = (Account) protocol;
//						UserInfo userinfo = account.getResultValue();
//						saveAccountInfo(name, pwd, userinfo);
//						saveDeviceInfo(deviceSelected);
//						goMsgInfoActivity();
//					}
//
//				});
//	}

	@UiThread(delay=500)
	protected void goMsgInfoActivity() {
		appManager.setPrefsBoolean(Constants.APP_KEY_LOGIN_STATE, true);
//		if (appManager.getUser().getStatus() == 1) {
//			// 进入操作界面
//			ControlleActivity_.intent(HomeActivity3.this).start();
//		} else
			// 进入第一个信息查询界面
			MessageInfoActivity_.intent(HomeActivity3.this).start();
		finish();
	}

	protected void goDeviceListActivity() {
		DeviceListActivity_.intent(HomeActivity3.this).start();
	}

//	@Override
//	public void showMsg(String string) {
//		showToast(string);
//	}

//	@Override
//	public void loginComplete(final String name, final String pwd) {
//		restManager.login(name, MD5.getMD5(pwd), /* appManager.getCurrentDevice().getId() */deviceSelected.getId(),
//				new RestListener() {
//
//					@Override
//					@UiThread
//					public void restFail(String res) {
//						if (!TextUtils.isEmpty(res)) {
//							showToast(res);
//						}
//					}
//
//					@Override
//					@UiThread
//					public void onRest(BaseProtocol protocol) {
//						Account account = (Account) protocol;
//						UserInfo userinfo = account.getResultValue();
//						saveAccountInfo(name, pwd, userinfo);
//						saveDeviceInfo(deviceSelected);
//						if (appManager.getUser().getStatus() == Constants.ADMIN_ACCOUNT_STATUS) {
//							// 管理员登陆
//							// 进入新建设备界面
//							goDeviceListActivity();
//						} else {
//							goMsgInfoActivity();
//						}
//					}
//				});
//	}

	@Override
	public void welcomeCompleted() {
		// 启动home
		directHome();
	}

//	@Override
//	public void forgetPwd() {
//		// 忘记密码
//		ResetPwdFragment fragment = new ResetPwdFragment();
//		fragment.show(getFragmentManager(), "resetpwd");
//	}

//	@Override
//	public void resetComplete(final String name, final String pwd_old, final String pwd_new) {
//		// TODO Auto-generated method stub
//		restManager.resetPwd(name, MD5.getMD5(pwd_old), MD5.getMD5(pwd_new), appManager.getCurrentDevice().getId(),
//				new RestListener() {
//
//					@Override
//					public void restFail(String res) {
//						if (!TextUtils.isEmpty(res)) {
//							showToast(res);
//						}
//					}
//
//					@Override
//					public void onRest(BaseProtocol protocol) {
//						Account account = (Account) protocol;
//						appManager.setUser(account.getResultValue());
//						appManager.setPrefsStr(Constants.APP_KEY_USER_NAME, name);
//						appManager.setPrefsStr(Constants.APP_KEY_USER_PWD, pwd_new);
//						// TODO
//						// appManager.setCurrentDevice(device);
//						// appManager.setPrefsStr(Constants.APP_KEY_DEVICE_NAME,
//						// device.getName());
//						if (appManager.getUser().getStatus() == -1) {
//							// 管理员登陆
//							// 进入新建设备界面
//							goDeviceListActivity();
//						} else {
//							goMsgInfoActivity();
//						}
//					}
//				});
//	}

//	@Override
//	public void callService() {
//		// 直接给客服打电话
//		// TODO
//		Intent phoneIntent = new Intent("android.intent.action.CALL" /* Intent.ACTION_DIAL */,
//				Uri.parse("tel:" + Constants.BASE_TELEPHONE));
//		startActivity(phoneIntent);
//	}

}
