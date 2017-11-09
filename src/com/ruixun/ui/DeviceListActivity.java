package com.ruixun.ui;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ItemLongClick;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import com.ruixun.R;
import com.ruixun.adapter.DeviceAdapter;
import com.ruixun.base.BaseActivity;
import com.ruixun.base.BaseProtocol;
import com.ruixun.base.RestListener;
import com.ruixun.engine.ActionBarListener;
import com.ruixun.entity.Device;
import com.ruixun.protocol.Default;
import com.ruixun.protocol.DeviceList;
import com.ruixun.ui.dialog.TipDialog;
import com.ruixun.ui.dialog.TipDialog.DialogListener;
import com.ruixun.widget.ActionBar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ListView;

@EActivity(R.layout.activity_devicelist)
public class DeviceListActivity extends BaseActivity implements ActionBarListener, RestListener, DialogListener {

	@ViewById(R.id.actionbar)
	ActionBar actionbar;

	@ViewById(R.id.device_listview)
	ListView listview;
	private DeviceAdapter adapter;

	@AfterViews
	void init() {
		actionbar.setListener(this);
		actionbar.setTitleText(getString(R.string.device_list));
		adapter = new DeviceAdapter(this);
		listview.setAdapter(adapter);
	}

	@Override
	protected void loadData() {
		super.loadData();
		getDevicelist();
		progressDialog.show();
	}

	private void getDevicelist() {
		restManager.getDeviceList(this);
	}

	@ItemLongClick(R.id.device_listview)
	void deleteDevice(Device device) {
		this.deviceid_deleted = device.getId();
		tipToUser();
	}

	@ItemClick(R.id.device_listview)
	void deviceSelected(Device device) {
		// 跳转设备详细信息界面，包括已注册用户列表
		DeviceInfoActivity_.intent(DeviceListActivity.this).extra("deviceid", device.getId())
				.extra("devicename", device.getName()).extra("deviceip", device.getRemark())
				.extra("device_createtime", device.getCreatetime().substring(0,device.getCreatetime().lastIndexOf(".0"))).start();
	}

	private boolean WINDOW_FLAG = true;

	private int deviceid_deleted;

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
			b.putString("tip", getString(R.string.confirm_delete_device));
			fragment.setArguments(b);
			fragment.show(getFragmentManager(), "delete_device");
		}
	}

	@Override
	@UiThread
	public void onRest(BaseProtocol protocol) {
		if (null != progressDialog && progressDialog.isShowing()) {
			progressDialog.dismiss();
			progressDialog = null;
		}
		if (protocol instanceof DeviceList) {
			if (protocol.getResultStatus().getCode() == 1) {
				// 获取数据信息成功
				DeviceList deviceList = (DeviceList) protocol;
				List<Device> list = deviceList.getResultValue();
				if (null != list && list.size() > 1) {
//					list.remove(0);
					adapter.setData(list);
				}
			}
		}
		if (protocol instanceof Default) {
			if (protocol.getResultStatus().getCode() == 1) {
				// 删除设备成功
				Default d = (Default) protocol;
				com.ruixun.entity.Default df = d.getResultValue();
				showToast(df.getMsg());
				getDevicelist();
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
		goCreateDeviceActivity();
	}

	private static final int CREATE_DEVICE_CODE = 0x11;

	public static final int CREATE_DEVICE_COMPLETE = 0x22;

	private void goCreateDeviceActivity() {
		CreateDeviceActivity_.intent(DeviceListActivity.this).startForResult(CREATE_DEVICE_CODE);
	}

	@OnActivityResult(CREATE_DEVICE_CODE)
	void onResult(int resultCode, Intent data) {
		if (resultCode == CREATE_DEVICE_COMPLETE) {
			getDevicelist();
		}
	}

	@Override
	public void onLeftAction() {
		finish();
	}

	@Override
	public void confirm(boolean confirm) {
		if (confirm) {
			deleteDevice();
		} else {
			// doNothing();
		}
	}

	private void deleteDevice() {
		restManager.deleteDevice(deviceid_deleted, appManager.getUser().getStatus(), this);
	}

	@Override
	public void timeOut() {
		// doNothing();
	}
}
