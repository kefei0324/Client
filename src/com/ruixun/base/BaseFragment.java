package com.ruixun.base;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

import com.ruixun.engine.AppManager;
import com.ruixun.engine.RestManager;

import android.app.Fragment;

@EFragment
public class BaseFragment extends Fragment{

	@Bean
	protected RestManager restManager;
	@Bean
	protected AppManager appManager;
	protected void showToast(String res) {
		((BaseActivity)getActivity()).showToast(res);
	}
	protected void showToast(int res) {
		((BaseActivity)getActivity()).showToast(res);
	}
	protected void showTestToast(String res) {
		((BaseActivity)getActivity()).showTestToast(res);
	}
	protected void showTestToast(int res) {
		((BaseActivity)getActivity()).showTestToast(res);
	}
}
