package com.ruixun.ui;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import com.ruixun.R;
import com.ruixun.base.BaseActivity;
import com.ruixun.base.BaseProtocol;
import com.ruixun.base.RestListener;
import com.ruixun.engine.ActionBarListener;
import com.ruixun.widget.ActionBar;

import android.text.TextUtils;
import android.widget.EditText;

@EActivity(R.layout.activity_createdevice)
public class CreateDeviceActivity extends BaseActivity implements RestListener, ActionBarListener {

	@ViewById
	ActionBar actionbar;
	@ViewById
	EditText et_devicename;
	@ViewById
	EditText et_devicedes;

	@AfterViews
	void init() {
		actionbar.setListener(this);
	}

	@Click
	void control_btn() {
		String name = et_devicename.getText().toString();
		String des = et_devicedes.getText().toString();

		restManager.createNewDevice(name, des, appManager.getUser().getStatus(), this);
	}

	@Override
	@UiThread
	public void onRest(BaseProtocol protocol) {
		if (null != protocol) {
			if (protocol.getResultStatus().getCode() == 1) {
				// 创建成功
				CreateDeviceActivity.this.setResult(DeviceListActivity.CREATE_DEVICE_COMPLETE);
				finish();
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
	public void onRightAction() {
	}

	@Override
	public void onLeftAction() {
		finish();
	}

}
