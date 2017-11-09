package com.ruixun.base;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.UiThread;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

@EViewGroup
public class BaseLinearLayout extends LinearLayout {

	protected String TAG;
	
	public BaseLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		TAG=getClass().getSimpleName();
	}

	public BaseLinearLayout(Context context) {
		super(context);
	}

	@UiThread
	protected void showTestToast(String res) {
		App.getInstance().showTestToast(res);
		Log.v(TAG, res);
	}

	@UiThread
	protected void showToast(String res) {
		App.getInstance().showToast(res);
		Log.v(TAG, res);
	}

	@UiThread
	protected void showTestToast(int resId) {
		App.getInstance().showTestToast(resId);
		Log.v(TAG, getContext().getString(resId));
	}

	@UiThread
	protected void showToast(int resId) {
		App.getInstance().showToast(resId);
		Log.v(TAG, getContext().getString(resId));
	}
	
}
