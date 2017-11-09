package com.ruixun.engine;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.EBean.Scope;

import com.ruixun.base.App;
import com.ruixun.base.BaseEngine;
import com.ruixun.base.Constants;
import com.ruixun.entity.Device;
import com.ruixun.entity.UserInfo;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

@EBean(scope = Scope.Singleton)
public class AppManager extends BaseEngine {

	private SharedPreferences mPreferences;
	private Editor mEditor;

	/************************************/
	protected Device currentDevice;

	public Device getCurrentDevice() {
		return currentDevice;
	}

	public void setCurrentDevice(Device currentDevice) {
		this.currentDevice = currentDevice;
		setPrefsInt(Constants.APP_KEY_DEVICE_ID, currentDevice.getId());
//		setPrefsStr(Constants.APP_KEY_DEVICE_NAME, "");
	}

	/************************************/

	protected UserInfo user;

	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
		setPrefsInt(Constants.APP_KEY_USER_ID, user.getId());
//		setPrefsStr(Constants.APP_KEY_USER_NAME, "");
//		setPrefsStr(Constants.APP_KEY_USER_PWD, "");
	}

	/************************************/

	@AfterInject
	void init() {
		Log.v("AppManager", "init_Method");
		this.mPreferences = App.getInstance().getSharedPreferences(Constants.APP_KEY_SHARED_PREFS,
				Context.MODE_PRIVATE);
	}

	public void setPrefsStr(String key, String value) {
		if (null != mPreferences) {
			mEditor = mPreferences.edit();
			mEditor.putString(key, value);
			mEditor.apply();
		}
	}

	public String getPrefsStr(String key) {
		if (null != mPreferences) {
			return mPreferences.getString(key, "");
		}
		return "";
	}

	public void setPrefsInt(String key, int value) {
		if (null != mPreferences) {
			mEditor = mPreferences.edit();
			mEditor.putInt(key, value);
			mEditor.apply();
		}
	}

	public int getPrefsInt(String key) {
		if (null != mPreferences) {
			return mPreferences.getInt(key, -1);
		}
		return -1;
	}

	public void setPrefsBoolean(String key, boolean value) {
		if (null != mPreferences) {
			mEditor = mPreferences.edit();
			mEditor.putBoolean(key, value);
			mEditor.apply();
		}
	}

	public boolean getPrefsBoolean(String key) {
		if (null != mPreferences) {
			return mPreferences.getBoolean(key, false);
		}
		return false;
	}
}
