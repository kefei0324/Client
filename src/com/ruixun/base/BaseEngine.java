package com.ruixun.base;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;

@EBean
public class BaseEngine {

	@UiThread
	protected void showToast(int resId) {
		App.getInstance().showToast(resId);
	}

	@UiThread
	protected void showTestToast(int resId) {
		App.getInstance().showTestToast(resId);
	}

	@UiThread
	protected void showToast(String resId) {
		App.getInstance().showToast(resId);
	}

	@UiThread
	protected void showTestToast(String resId) {
		App.getInstance().showTestToast(resId);
	}
	
}
