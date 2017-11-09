package com.ruixun.adapter;

import java.util.ArrayList;
import java.util.List;

import com.ruixun.R;
import com.ruixun.entity.MessageModle;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MsgModelAdapter extends BaseAdapter {

	private List<MessageModle> list;
	private Context context;

	// must be called after constuct
	public void setData(List<MessageModle> list) {
		if (null != list) {
			this.list.clear();
			this.list.addAll(list);
		}
		notifyDataSetChanged();
	}

	public void addData(List<MessageModle> list) {
		if (null != list) {
			this.list.addAll(list);
		}
		notifyDataSetChanged();
	}

	public MsgModelAdapter(Context context) {
		this.context = context;
		this.list = new ArrayList<MessageModle>();
	}

	@Override
	public int getCount() {
		if (null != list) {
			return list.size();
		}
		return 0;
	}

	@Override
	public MessageModle getItem(int position) {
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
		MessageModle msg = getItem(position);
		holder.name.setText(context.getString(R.string.item_msg_device_name, msg.getDevicename()));
		holder.date.setText(context.getString(R.string.item_msg_update_time,
				msg.getCreatetime().substring(0, msg.getCreatetime().lastIndexOf(".0"))));
		holder.data1.setTextColor(msg.isData1f() ? Color.BLACK : Color.RED);
		holder.data2.setTextColor(msg.isData2f() ? Color.BLACK : Color.RED);
		holder.data3.setTextColor(msg.isData3f() ? Color.BLACK : Color.RED);
		holder.data4.setTextColor(msg.isData4f() ? Color.BLACK : Color.RED);
		holder.data5.setTextColor(msg.isData5f() ? Color.BLACK : Color.RED);
		holder.data6.setTextColor(msg.isData6f() ? Color.BLACK : Color.RED);
		holder.data7.setTextColor(msg.isData7f() ? Color.BLACK : Color.RED);
		holder.data8.setTextColor(msg.isData8f() ? Color.BLACK : Color.RED);
		holder.data9.setTextColor(msg.isData9f() ? Color.BLACK : Color.RED);
		holder.data10.setTextColor(msg.isData10f() ? Color.BLACK : Color.RED);
		holder.data11.setTextColor(msg.isData11f() ? Color.BLACK : Color.RED);
		holder.data1.setText(context.getString(R.string.item_msg_data_1, msg.getData1()) + "     " + msg.getData1s());
		holder.data2.setText(context.getString(R.string.item_msg_data_2, msg.getData2()) + "     " + msg.getData2s());
		holder.data3.setText(context.getString(R.string.item_msg_data_3, msg.getData3()) + "     " + msg.getData3s());
		holder.data4.setText(context.getString(R.string.item_msg_data_4, msg.getData4()) + "     " + msg.getData4s());
		holder.data5.setText(context.getString(R.string.item_msg_data_5, msg.getData5()) + "     " + msg.getData5s());
		holder.data6.setText(context.getString(R.string.item_msg_data_6, msg.getData6()) + "     " + msg.getData6s());
		holder.data7.setText(context.getString(R.string.item_msg_data_7, msg.getData7()) + "     " + msg.getData7s());
		holder.data8.setText(context.getString(R.string.item_msg_data_8, msg.getData8()) + "     " + msg.getData8s());
		holder.data9.setText(context.getString(R.string.item_msg_data_9, msg.getData9()) + "     " + msg.getData9s());
		holder.data10
				.setText(context.getString(R.string.item_msg_data_10, msg.getData10()) + "     " + msg.getData10s());
		holder.data11
				.setText(context.getString(R.string.item_msg_data_11, msg.getData11()) + "     " + msg.getData11s());
		return convertView;
	}

	class ViewHolder {
		TextView name, date, data1, data2, data3, data4, data5, data6, data7, data8, data9, data10, data11;
	}

}
