package com.ruixun.base;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.UiThread.Propagation;

import com.ruixun.R;
import com.ruixun.engine.AppManager;
import com.ruixun.engine.RestManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

@EActivity
public abstract class BaseActivity extends FragmentActivity {

	protected ProgressDialog progressDialog;
	protected String TAG;

	@Bean
	protected RestManager restManager;
	@Bean
	protected AppManager appManager;

	@AfterInject
	protected void loadData() {
		initProgress();
		Log.v(TAG, "loadData_METHOD");
	}

	protected void initProgress() {
		if (null == progressDialog){
			progressDialog = new ProgressDialog(this);
		}
		progressDialog.setMessage(getString(R.string.progress_on));
	}

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		TAG = getClass().getSimpleName();
		App.getInstance().addToApp(this);
		Log.v(TAG, "add to app list");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		App.getInstance().removeFromApp(this);
		Log.v(TAG, "remove from app list");
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
		Log.v(TAG, getString(resId));
	}

	@UiThread
	protected void showToast(int resId) {
		App.getInstance().showToast(resId);
		Log.v(TAG, getString(resId));
	}

	@UiThread(propagation = Propagation.REUSE)
	protected void showView(View view) {
		view.setVisibility(View.VISIBLE);
	}

	@UiThread(propagation = Propagation.REUSE)
	protected void goneView(View view) {
		view.setVisibility(View.GONE);
	}

}
