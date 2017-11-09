package com.ruixun.adapter;

import java.util.ArrayList;
import java.util.List;

import com.ruixun.R;
import com.ruixun.entity.Message;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MessageAdapter extends BaseAdapter {

	private List<Message> list;
	private Context context;

	//must be called after constuct
	public void setData(List<Message> list) {
		if (null != list) {
			this.list.clear();
			this.list.addAll(list);
		}
		notifyDataSetChanged();
	}

	public void addData(List<Message> list) {
		if (null != list) {
			this.list.addAll(list);
		}
		notifyDataSetChanged();
	}

	public MessageAdapter(Context context) {
		this.context = context;
		this.list = new ArrayList<Message>();		
	}

	@Override
	public int getCount() {
		if (null != list) {
			return list.size();
		}
		return 0;
	}

	@Override
	public Message getItem(int position) {
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
			convertView = View.inflate(context, R.layout.item_message, null);
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.device_name);
			holder.date = (TextView) convertView.findViewById(R.id.update_date);
			holder.data1 = (TextView) convertView.findViewById(R.id.data_1);
			holder.data2 = (TextView) convertView.findViewById(R.id.data_2);
			holder.data3 = (TextView) convertView.findViewById(R.id.data_3);
			holder.data4 = (TextView) convertView.findViewById(R.id.data_4);
			holder.data5 = (TextView) convertView.findViewById(R.id.data_5);
			holder.data6 = (TextView) convertView.findViewById(R.id.data_6);
			holder.data7 = (TextView) convertView.findViewById(R.id.data_7);
			holder.data8 = (TextView) convertView.findViewById(R.id.data_8);
			holder.data9 = (TextView) convertView.findViewById(R.id.data_9);
			holder.data10 = (TextView) convertView.findViewById(R.id.data_10);
			holder.data11 = (TextView) convertView.findViewById(R.id.data_11);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Message msg = getItem(position);
		holder.name.setText(context.getString(R.string.item_msg_device_name, msg.getDevicename()));
		holder.date.setText(context.getString(R.string.item_msg_update_time, msg.getCreatetime().substring(0,msg.getCreatetime().lastIndexOf(".0"))));
		holder.data1.setText(context.getString(R.string.item_msg_data_1, msg.getData1()));
		holder.data2.setText(context.getString(R.string.item_msg_data_2, msg.getData2()));
		holder.data3.setText(context.getString(R.string.item_msg_data_3, msg.getData3()));
		holder.data4.setText(context.getString(R.string.item_msg_data_4, msg.getData4()));
		holder.data5.setText(context.getString(R.string.item_msg_data_5, msg.getData5()));
		holder.data6.setText(context.getString(R.string.item_msg_data_6, msg.getData6()));
		holder.data7.setText(context.getString(R.string.item_msg_data_7, msg.getData7()));
		holder.data8.setText(context.getString(R.string.item_msg_data_8, msg.getData8()));
		holder.data9.setText(context.getString(R.string.item_msg_data_9, msg.getData9()));
		holder.data10.setText(context.getString(R.string.item_msg_data_10, msg.getData10()));
		holder.data11.setText(context.getString(R.string.item_msg_data_11, msg.getData11()));
		return convertView;
	}

	class ViewHolder {
		TextView name, date, data1, data2, data3, data4, data5, data6, data7, data8, data9, data10, data11;
	}

}
