package com.ruixun.base;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.EApplication;

import com.ruixun.BuildConfig;
import com.ruixun.engine.CrashHandler;

import android.app.Application;
import android.widget.Toast;

@EApplication
public class App extends Application {
	private static App instance;
	private static List<BaseActivity> mList;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		mList = new ArrayList<BaseActivity>();
//		CrashHandler crashHandler = CrashHandler.getInstance();  
//		crashHandler.init(getApplicationContext());  
	}

	public static App getInstance() {
		if (null == instance) {
			instance = new App();
		}
		return instance;
	}

	/**
	 * 默认提示信息
	 * 
	 * @param tip
	 */
	public void showToast(String tip) {
		Toast.makeText(this, tip, Toast.LENGTH_SHORT).show();
//		Log.v("App", tip);
	}

	/**
	 * 默认提示信息
	 * 
	 * @param tip
	 */
	public void showToast(int tipResId) {
		Toast.makeText(this, getResources().getString(tipResId), Toast.LENGTH_SHORT).show();
//		Log.v("App", getResources().getString(tipResId));
	}

	/**
	 * 测试提示信息
	 * 
	 * @param tip
	 */
	public void showTestToast(String tip) {
		if (BuildConfig.DEBUG) {
			Toast.makeText(this, tip, Toast.LENGTH_SHORT).show();
//			Log.v("App", tip);
		}
	}

	/**
	 * 测试提示信息
	 * 
	 * @param tip
	 */
	public void showTestToast(int tipResId) {
		if (BuildConfig.DEBUG) {
			Toast.makeText(this, getResources().getString(tipResId), Toast.LENGTH_SHORT).show();
//			Log.v("App", getResources().getString(tipResId));
		}
	}

	/**
	 * 从activity列表移除指定activity
	 * 
	 * @param activity
	 */
	public void removeFromApp(BaseActivity activity) {
		if (null != mList) {
			if (null == activity) {
				return;
			}
			if (mList.contains(activity)) {
				mList.remove(activity);
			}
		}
	}

	/**
	 * 向activity列表添加指定activity
	 * 
	 * @param activity
	 */
	public void addToApp(BaseActivity activity) {
		if (null != mList) {
			if (null == activity) {
				return;
			}
			if (mList.contains(activity)) {
				return;
			}
			mList.add(activity);
		}
	}
}
