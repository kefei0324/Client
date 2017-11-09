package com.ruixun.ui;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import com.ruixun.R;
import com.ruixun.adapter.MessageAdapter;
import com.ruixun.base.BaseActivity;
import com.ruixun.base.BaseProtocol;
import com.ruixun.base.Constants;
import com.ruixun.base.RestListener;
import com.ruixun.engine.ActionBarListener;
import com.ruixun.entity.Message;
import com.ruixun.protocol.Account;
import com.ruixun.protocol.MessageList;
import com.ruixun.widget.ActionBar;
import com.ruixun.widget.SwipeRefreshUpAndDownLayout;
import com.ruixun.widget.SwipeRefreshUpAndDownLayout.OnLoadListener;

import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.TextUtils;
import android.widget.ListView;

@EActivity(R.layout.activity_msginfo)
public class MessageInfoActivity extends BaseActivity
		implements RestListener, ActionBarListener, OnRefreshListener, OnLoadListener {

	@ViewById(R.id.actionbar)
	ActionBar actionbar;

	@ViewById(R.id.swipe)
	SwipeRefreshUpAndDownLayout swip;
	@ViewById(R.id.msg_listview)
	ListView listview;
	private MessageAdapter adapter;

	@Override
	protected void onStop() {
		super.onStop();
	}

	@AfterViews
	void init() {
		actionbar.setListener(this);
		actionbar.setTitleText(appManager.getCurrentDevice().getName());
		adapter = new MessageAdapter(this);
		listview.setAdapter(adapter);
		swip.setOnRefreshListener(this);
		swip.setOnLoadListener(this);
	}

	@Click
	void control_btn() {
		// 请求服务器获取操作设备权限
		restManager.modifyCompetence(appManager.getUser().getId(), appManager.getCurrentDevice().getId(), this);
	}

	@Override
	protected void loadData() {
		super.loadData();
		getMsgList(page);
		progressDialog.show();
	}

	private int page;

	private void getMsgList(int page) {
		restManager.getMsgList(appManager.getUser().getId(), appManager.getUser().getDeviceid(), page, this);
	}

	@Override
	@UiThread
	public void onRest(BaseProtocol protocol) {
		if (null != progressDialog && progressDialog.isShowing()) {
			progressDialog.dismiss();
			progressDialog = null;
		}
		if (protocol instanceof MessageList) {
			if (protocol.getResultStatus().getCode() == 1) {
				// 获取数据信息成功
				MessageList msgList = (MessageList) protocol;
				List<Message> list = msgList.getResultValue();
				swip.setRefreshing(false);
				adapter.addData(list);
				if (page == 0) {
					adapter.setData(list);
				} else {
					swip.setLoading(false);
					adapter.addData(list);
				}
				page++;
			}
		}
		// TODO
		if (protocol instanceof Account) {
			if (protocol.getResultStatus().getCode() == 1) {
				// 修改权限成功
				Account account = (Account) protocol;
				if (account.getResultValue().getStatus() == Constants.APP_KEY_USER_STATUS_ON) {
					appManager.setUser(account.getResultValue());
					ControlleActivity_.intent(MessageInfoActivity.this).start();
				}
			}
		}
	}

	@Override
	public void restFail(String res) {
		if (null != progressDialog && progressDialog.isShowing()) {
			progressDialog.dismiss();
			progressDialog = null;
		}
		if (!TextUtils.isEmpty(res)) {
			showToast(res);
		}
	}

	@Override
	public void onRightAction() {
	}

	@Override
	public void onLeftAction() {
		// 返回登陆界面
		HomeActivity2_.intent(this).action("reset_or_forget_login_name").start();
		finish();
	}

	@Override
	public void onRefresh() {
		page = 0;
		getMsgList(page);
	}

	@Override
	public void onLoad() {
		getMsgList(page);
	}

}
