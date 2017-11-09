package com.ruixun.ui;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import com.ruixun.R;
import com.ruixun.base.BaseActivity;
import com.ruixun.base.BaseFragment;
import com.ruixun.base.BaseProtocol;
import com.ruixun.base.RestListener;
import com.ruixun.engine.ActionBarListener;
import com.ruixun.protocol.Account;
import com.ruixun.ui.dialog.TipDialog;
import com.ruixun.ui.dialog.TipDialog.DialogListener;
import com.ruixun.ui.msg.MsgInfoChartsFragment_;
import com.ruixun.ui.msg.MsgInfoFragment_;
import com.ruixun.ui.msg.MsgInfoModelFragment_;
import com.ruixun.util.BroadCastUdp;
import com.ruixun.widget.ActionBar;
import com.ruixun.widget.ItemControlle;
import com.ruixun.widget.ItemControlle.ItemControlleListener;
import com.ruixun.widget.SwipeRefreshUpAndDownLayout.OnLoadListener;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.TextUtils;
import android.widget.FrameLayout;

/**
 * 
 * @author feona
 *
 */
@EActivity(R.layout.activity_controlle)
public class ControlleActivity extends BaseActivity
		implements ActionBarListener, RestListener, DialogListener, OnRefreshListener, OnLoadListener {

	@ViewById
	ActionBar action_bar;
	@ViewById
	ItemControlle item_1;
	@ViewById
	ItemControlle item_2;
	@ViewById
	ItemControlle item_3;
	private Handler mHandler;
	@ViewById
	FrameLayout controller_container;

	protected FragmentTransaction getTF(FragmentManager fm) {
		if (null == fm)
			fm = getFragmentManager();
		tf = fm.beginTransaction();
		return tf;
	}

	@Click
	protected void chaxun() {
		// chaxunFragment = (BaseFragment) getFm().findFragmentByTag("chaxun");
		tf = getTF(fm);
		if (null == chaxunFragment) {
			chaxunFragment = new MsgInfoFragment_();
		}
		tf.detach(fenxiFragment);
		tf.detach(pingjiaFragment);
		tf.attach(chaxunFragment)/*
									 * (R.id.controller_container, chaxunFragment,
									 * "chaxun")
									 */;
		tf.commitAllowingStateLoss();
	}

	@Click
	protected void fenxi() {
		// fenxiFragment = (BaseFragment) getFm().findFragmentByTag("fenxi");
		if (null == fenxiFragment) {
			fenxiFragment = new MsgInfoModelFragment_();
		}
		tf = getTF(fm);
		tf.detach(pingjiaFragment);
		tf.detach(chaxunFragment);
		tf.attach(fenxiFragment)/*
								 * (R.id.controller_container, fenxiFragment,
								 * "fenxi")
								 */;
		tf.commitAllowingStateLoss();
	}

	@Click
	protected void pingjia() {
		// pingjiaFragment = (BaseFragment)
		// getFm().findFragmentByTag("pingjia");
		if (null == pingjiaFragment) {
			pingjiaFragment = new MsgInfoChartsFragment_();
		}
		tf = getTF(fm);
		tf.detach(fenxiFragment);
		tf.detach(chaxunFragment);
		tf.attach(pingjiaFragment);/*
									 * (R.id.controller_container,
									 * pingjiaFragment, "pingjia");
									 */
		tf.commitAllowingStateLoss();

	}

	protected BroadcastReceiver mScreenReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			System.out.println("接收到黑屏广播");
			modifyIdentity();
		}

	};

	protected ItemControlleListener item_1_listener = new ItemControlleListener() {

		@Override
		public void updateTvCenture() {
			sendUdp("2");
		}

		@Override
		public void imgUp() {
			sendUdp("1");
		}

		@Override
		public void imgDown() {
			sendUdp("3");
		}
	};
	protected ItemControlleListener item_2_listener = new ItemControlleListener() {

		@Override
		public void updateTvCenture() {
			sendUdp("5");
		}

		@Override
		public void imgUp() {
			sendUdp("4");
		}

		@Override
		public void imgDown() {
			sendUdp("6");
		}
	};
	protected ItemControlleListener item_3_listener = new ItemControlleListener() {

		@Override
		public void updateTvCenture() {
			sendUdp("8");
		}

		@Override
		public void imgUp() {
			sendUdp("7");
		}

		@Override
		public void imgDown() {
			sendUdp("9");
		}
	};

	FragmentTransaction tf = null;
	private BaseFragment chaxunFragment, fenxiFragment, pingjiaFragment;
	FragmentManager fm = null;

	@AfterViews
	protected void init() {
		action_bar.setListener(this);
		action_bar.setTitleText(appManager.getCurrentDevice().getName());
		item_1.setListener(item_1_listener);
		item_2.setListener(item_2_listener);
		item_3.setListener(item_3_listener);
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_SCREEN_OFF);// 屏幕关闭
		registerReceiver(mScreenReceiver, filter);
		// 启动定时任务
		mHandler = new Handler();
		mHandler.postDelayed(modify_60s, 60000);

		chaxunFragment = new MsgInfoFragment_();
		fenxiFragment = new MsgInfoModelFragment_();
		pingjiaFragment = new MsgInfoChartsFragment_();
		fm = getFm();
		tf = getTF(fm);
		tf.add(R.id.controller_container, pingjiaFragment, "pingjiaFragment");
		tf.add(R.id.controller_container, fenxiFragment, "fenxiFragment");
		tf.add(R.id.controller_container, chaxunFragment, "chaxunFragment");

		tf.commitAllowingStateLoss();
		initControlleItem();
		chaxun();
	}

	private void initControlleItem() {
		item_1.setContentUp(getString(R.string.item_1_btn_up));
		item_1.setContentCenture(getString(R.string.item_1_btn_centure));
		item_1.setContentDown(getString(R.string.item_1_btn_down));
		item_2.setContentUp(getString(R.string.item_2_btn_up));
		item_2.setContentCenture(getString(R.string.item_2_btn_centure));
		item_2.setContentDown(getString(R.string.item_2_btn_down));
		item_3.setContentUp(getString(R.string.item_3_btn_up));
		item_3.setContentCenture(getString(R.string.item_3_btn_centure));
		item_3.setContentDown(getString(R.string.item_3_btn_down));
		
	}

	private FragmentManager getFm() {
		if (null == fm) {
			fm = getFragmentManager();
		}
		return fm;
	}

	protected void sendUdp(String string) {
		BroadCastUdp udp = new BroadCastUdp(string,appManager.getCurrentDevice().getRemark());
		udp.start();
	}

	protected Runnable modify_60s = new Runnable() {

		@Override
		public void run() {
			tipToUser();
		}
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (null != mScreenReceiver) {
			unregisterReceiver(mScreenReceiver);
		}
		mHandler.removeCallbacks(modify_60s);
	}

	protected boolean WINDOW_FLAG = true;

	@Override
	protected void onStop() {
		super.onStop();
		WINDOW_FLAG = false;
	}

	@Override
	protected void onStart() {
		super.onStart();
		sendUdp("0");
		WINDOW_FLAG = true;
	}

	protected void tipToUser() {
		if (WINDOW_FLAG) {
			mHandler.removeCallbacks(modify_60s);
			TipDialog fragment = new TipDialog();
			Bundle b = new Bundle();
			b.putString("tip", getString(R.string.identity_confirm));
			fragment.setArguments(b);
			fragment.show(getFragmentManager(), "identity");
		} else {
			modifyIdentity();
		}
	}

	@Override
	public void onBackPressed() {
		tipToUser();
	}

	@Override
	public void onRightAction() {

	}

	@Override
	public void onLeftAction() {
		tipToUser();
	}

	@Override
	@UiThread
	public void onRest(BaseProtocol protocol) {
		if (protocol.getResultStatus().getCode() == 1) {
			if (protocol instanceof Account) {
				Account account = (Account) protocol;
				appManager.setUser(account.getResultValue());
				if (!ControlleActivity.this.isFinishing())
					ControlleActivity.this.finish();
			}
		}
	}

	@Override
	public void restFail(String res) {
		if (!TextUtils.isEmpty(res)) {
			showToast(res);
		}
	}

	@Override
	public void confirm(boolean confirm) {
		if (confirm) {
			// 用户确认修改权限
			modifyIdentity();
		} else {
			// 用户不修改权限
			mHandler.postDelayed(modify_60s, 60000);
		}
	}

	// 用户修改权限
	protected void modifyIdentity() {
		restManager.modifyCompetence(appManager.getUser().getId(), appManager.getCurrentDevice().getId(), this);
	}

	@Override
	public void timeOut() {
		modifyIdentity();
	}

	@Override
	public void onRefresh() {
	}

	@Override
	public void onLoad() {
	}

}
