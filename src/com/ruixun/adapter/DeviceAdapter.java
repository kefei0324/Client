package com.ruixun.adapter;

import java.util.ArrayList;
import java.util.List;

import com.ruixun.R;
import com.ruixun.entity.Device;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DeviceAdapter extends BaseAdapter {
	private List<Device> list;
	private Context context;

	// must be called after constuct
	public void setData(List<Device> list) {
		if (null != list) {
			this.list.clear();
			this.list.addAll(list);
		}
		notifyDataSetChanged();
	}

	public void addData(List<Device> list) {
		if (null != list) {
			this.list.addAll(list);
		}
		notifyDataSetChanged();
	}

	public DeviceAdapter(Context context) {
		this.context = context;
		this.list = new ArrayList<Device>();
	}

	@Override
	public int getCount() {
		if (null != list) {
			return list.size();
		}
		return 0;
	}

	@Override
	public Device getItem(int position) {
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
			convertView = View.inflate(context, R.layout.item_device, null);
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.device_name);
			holder.date = (TextView) convertView.findViewById(R.id.update_date);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Device device = getItem(position);
		holder.name.setText(context.getString(R.string.item_device_name, device.getName()));
		holder.date.setText(context.getString(R.string.item_device_update_time, device.getCreatetime().substring(0,device.getCreatetime().lastIndexOf(".0"))));
		return convertView;
	}

	class ViewHolder {
		TextView name, date;
	}

}
