package com.ruixun.ui;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemLongClick;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import com.ruixun.R;
import com.ruixun.adapter.AccountAdapter;
import com.ruixun.base.BaseActivity;
import com.ruixun.base.BaseProtocol;
import com.ruixun.base.RestListener;
import com.ruixun.engine.ActionBarListener;
import com.ruixun.entity.UserInfo;
import com.ruixun.protocol.AccoutList;
import com.ruixun.protocol.Default;
import com.ruixun.ui.dialog.TipDialog;
import com.ruixun.ui.dialog.TipDialog.DialogListener;
import com.ruixun.widget.ActionBar;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ListView;
import android.widget.TextView;

@EActivity(R.layout.activity_deviceinfo)
public class DeviceInfoActivity extends BaseActivity implements RestListener, ActionBarListener, DialogListener {

	@ViewById
	TextView device_name,device_createtime,device_ip;
	
	@ViewById(R.id.actionbar)
	ActionBar actionbar;
	@ViewById(R.id.account_listview)
	ListView listview;

	private AccountAdapter adapter;

	@AfterViews
	void init() {
		// 加载设备已注册用户列表
		actionbar.setListener(this);
		actionbar.setTitleText(getIntent().getStringExtra("devicename"));
		adapter = new AccountAdapter(this);
		listview.setAdapter(adapter);
		device_createtime.setText(getString(R.string.item_device_update_time,getIntent().getStringExtra("device_createtime")));
		device_name.setText(getString(R.string.item_device_name,getIntent().getStringExtra("devicename")));
		device_ip.setText(getString(R.string.item_device_ip,getIntent().getStringExtra("deviceip")));
	}

	@Override
	protected void loadData() {
		super.loadData();
		getAccountList();
		progressDialog.show();
	}

	private boolean WINDOW_FLAG = true;
	private int account_deleted;

	@ItemLongClick(R.id.account_listview)
	void deleteDevice(UserInfo userinfo) {
		this.account_deleted = userinfo.getId();
		tipToUser();
	}

	@Override
	protected void onStop() {
		super.onStop();
		WINDOW_FLAG = false;
	}

	@Override
	protected void onStart() {
		super.onStart();
		WINDOW_FLAG = true;
	}

	private void tipToUser() {
		if (WINDOW_FLAG) {
			TipDialog fragment = new TipDialog();
			Bundle b = new Bundle();
			b.putString("tip", getString(R.string.confirm_delete_account));
			fragment.setArguments(b);
			fragment.show(getFragmentManager(), "delete_account");
		}
	}

	private void getAccountList() {
		int deviceid = getIntent().getIntExtra("deviceid", -1);
		restManager.getAccountList(deviceid, appManager.getUser().getStatus(), this);
	}

	@Override
	@UiThread
	public void onRest(BaseProtocol protocol) {
		if (null != progressDialog && progressDialog.isShowing()) {
			progressDialog.dismiss();
			progressDialog = null;
		}
		if (protocol instanceof AccoutList) {
			if (protocol.getResultStatus().getCode() == 1) {
				// 获取数据信息成功
				AccoutList accountList = (AccoutList) protocol;
				List<UserInfo> list = accountList.getResultValue();
				if (null != list && list.size() > 0) {
					// list.remove(0);
					adapter.setData(list);
				}
			}
		}
		if (protocol instanceof Default) {
			if (protocol.getResultStatus().getCode() == 1) {
				// 删除用户成功
				Default d = (Default) protocol;
				com.ruixun.entity.Default df = d.getResultValue();
				showToast(df.getMsg());
				getAccountList();
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
		finish();
	}

	@Override
	public void confirm(boolean confirm) {
		if (confirm) {
			deleteAccount();
		} else {
			// doNothing();
		}
	}

	private void deleteAccount() {
		restManager.deleteAccount(account_deleted, appManager.getUser().getStatus(), this);
	}

	@Override
	public void timeOut() {
	}

}
