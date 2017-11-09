package com.ruixun.ui.msg;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import com.ruixun.R;
import com.ruixun.adapter.MsgModelAdapter;
import com.ruixun.base.BaseFragment;
import com.ruixun.base.BaseProtocol;
import com.ruixun.base.RestListener;
import com.ruixun.entity.MessageModle;
import com.ruixun.protocol.MessageModleList;
import com.ruixun.widget.SwipeRefreshUpAndDownLayout;
import com.ruixun.widget.SwipeRefreshUpAndDownLayout.OnLoadListener;

import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.TextUtils;
import android.widget.ListView;

@EFragment(R.layout.fragment_model_msg)
public class MsgInfoModelFragment extends BaseFragment implements OnRefreshListener, OnLoadListener, RestListener {
	@ViewById(R.id.msg_model_listview)
	ListView listview;
	@ViewById(R.id.swipe)
	SwipeRefreshUpAndDownLayout swip;
	private MsgModelAdapter adapter;
	protected int index;

	@AfterViews
	protected void init() {
		adapter = new MsgModelAdapter(MsgInfoModelFragment.this.getActivity());
		listview.setAdapter(adapter);
		swip.setOnRefreshListener(this);
		swip.setOnLoadListener(this);
		 index=0;
		getModle(index);
	}

	private void getModle(int index) {
		restManager.getMsgModleList(appManager.getUser().getId(), appManager.getUser().getDeviceid(),index, this);
	}

	@Override
	public void onRefresh() {
		index = 0;
		getModle(index);
	}

	@Override
	public void onLoad() {
		getModle(index);
	}

	@Override
	@UiThread
	public void onRest(BaseProtocol protocol) {
		if (protocol.getResultStatus().getCode() == 1) {
			if (protocol instanceof MessageModleList) {
				if (protocol.getResultStatus().getCode() == 1) {
					// 获取数据信息成功
					MessageModleList msgList = (MessageModleList) protocol;
					List<MessageModle> list = msgList.getResultValue();
					if (index == 0) {
						swip.setRefreshing(false);
						adapter.setData(list);
					} else {
						swip.setLoading(false);
						adapter.addData(list);
					}
					index++;
				}
			}
		}
	}

	@Override
	public void restFail(String res) {
		if (!TextUtils.isEmpty(res)) {
			showToast(res);
		}
	}

}
