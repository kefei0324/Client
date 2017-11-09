package com.ruixun.ui.welcome;

import com.ruixun.R;

import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class WelcomeDialog extends DialogFragment implements Runnable {

//	@ViewById(R.id.viewpager_welcome)
//	ViewPager viewpager;// TODO edit by demand

	private Handler handler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_NoTitleBar_Fullscreen);
		handler = new Handler();
		handler.postDelayed(this, 3000);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.act_login, container);
		return view;
//		return super.onCreateView(inflater, container, savedInstanceState);
	}

	public interface WelcomeListener {
		void welcomeCompleted();
	}

	@Override
	public void run() {
		if (WelcomeDialog.this.isResumed()) {
			((WelcomeListener) getActivity()).welcomeCompleted();
			handler.removeCallbacks(this);
			this.dismiss();
		}
	}

}
