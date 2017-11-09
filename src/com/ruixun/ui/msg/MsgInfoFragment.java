package com.ruixun.ui.msg;

import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import com.ruixun.R;
import com.ruixun.adapter.MessageAdapter;
import com.ruixun.base.BaseFragment;
import com.ruixun.base.BaseProtocol;
import com.ruixun.base.RestListener;
import com.ruixun.entity.Message;
import com.ruixun.protocol.MessageList;
import com.ruixun.widget.SwipeRefreshUpAndDownLayout;
import com.ruixun.widget.SwipeRefreshUpAndDownLayout.OnLoadListener;

import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.TextUtils;
import android.widget.ListView;

@EFragment(R.layout.fragment_swip_msg)
public class MsgInfoFragment extends BaseFragment implements RestListener, OnLoadListener, OnRefreshListener {

	@ViewById(R.id.msg_listview)
	ListView listview;
	@ViewById(R.id.swipe)
	SwipeRefreshUpAndDownLayout swip;
	private MessageAdapter adapter;
	protected int page;

	@AfterViews
	protected void init() {
		adapter = new MessageAdapter(MsgInfoFragment.this.getActivity());
		listview.setAdapter(adapter);
		swip.setOnRefreshListener(this);
		swip.setOnLoadListener(this);
		page=0;
		getMsgList(page);
	}

	protected void getMsgList(int page) {
		restManager.getMsgList(appManager.getUser().getId(), appManager.getUser().getDeviceid(), page,
				MsgInfoFragment.this);
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

	@Override
	@UiThread
	public void onRest(BaseProtocol protocol) {
		if (protocol.getResultStatus().getCode() == 1) {
			if (protocol instanceof MessageList) {
				if (protocol.getResultStatus().getCode() == 1) {
					// 获取数据信息成功
					MessageList msgList = (MessageList) protocol;
					List<Message> list = msgList.getResultValue();
					if (page == 0) {
						swip.setRefreshing(false);
						adapter.setData(list);
					} else {
						swip.setLoading(false);
						adapter.addData(list);
					}
					page++;
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
