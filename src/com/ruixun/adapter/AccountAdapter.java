package com.ruixun.adapter;

import java.util.ArrayList;
import java.util.List;

import com.ruixun.R;
import com.ruixun.entity.UserInfo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AccountAdapter extends BaseAdapter {

	private List<UserInfo> list;
	private Context context;

	// must be called after constuct
	public void setData(List<UserInfo> list) {
		if (null != list) {
			this.list.clear();
			this.list.addAll(list);
		}
		notifyDataSetChanged();
	}

	public void addData(List<UserInfo> list) {
		if (null != list) {
			this.list.addAll(list);
		}
		notifyDataSetChanged();
	}

	public AccountAdapter(Context context) {
		this.context = context;
		this.list = new ArrayList<UserInfo>();
	}

	@Override
	public int getCount() {
		if (null != list) {
			return list.size();
		}
		return 0;
	}

	@Override
	public UserInfo getItem(int position) {
		if (null != list) {
			return list.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		if (null != list) {
			return position;
		}
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (null == convertView) {
			convertView = View.inflate(context, R.layout.item_account, null);
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.account_name);
			holder.date = (TextView) convertView.findViewById(R.id.update_date);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		UserInfo userinfo = getItem(position);
		holder.name.setText(context.getString(R.string.item_account_name, userinfo.getName()));
		holder.date.setText(context.getString(R.string.item_account_update_time, userinfo.getCreatetime().substring(0,userinfo.getCreatetime().lastIndexOf(".0"))));
		return convertView;
	}

	class ViewHolder {
		TextView name, date;
	}
}
