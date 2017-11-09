package com.ruixun.ui.msg;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.ruixun.R;
import com.ruixun.base.BaseFragment;
import com.ruixun.base.BaseProtocol;
import com.ruixun.base.RestListener;
import com.ruixun.entity.MessageModle;
import com.ruixun.protocol.MessageModleList;

import android.graphics.Color;
import android.text.TextUtils;
import android.widget.TextView;

@EFragment(R.layout.fragment_charts_msg)
public class MsgInfoChartsFragment extends BaseFragment implements RestListener {

	@ViewById(R.id.spread_line_chart)
	LineChart chart;
	@ViewById(R.id.pingjia)
	TextView pingjia;

	private int index;

	@AfterViews
	void loadData() {
		getModle(index);
	}

	private void getModle(int index) {
		restManager.getMsgModleList(appManager.getUser().getId(), appManager.getUser().getDeviceid(), index, this);
	}

	private void initData(List<MessageModle> list) {
		ArrayList<String> xValues = new ArrayList<String>();
		ArrayList<Entry> yValues = new ArrayList<Entry>();
		// for (int i = 0; i < count; i++) {
		// // x轴显示的数据，这里默认使用数字下标显示
		// xValues.add("月日" + i);
		// }
//		ArrayList<Float>yF=new ArrayList<Float>();
		float tt=0f;
		for (int i = 0; i < list.size(); i++) {
			MessageModle mm = list.get(i);
			xValues.add(mm.getCreatetime().substring(5, 10));
			float value = mm.getTotal();
			tt+=value;
			yValues.add(new Entry(value, i));
		}
		// create a dataset and give it a type
		// y轴的数据集合
		LineDataSet lineDataSet = new LineDataSet(yValues, "历史纪录" /* 显示在比例图上 */);
		// mLineDataSet.setFillAlpha(110);
		// mLineDataSet.setFillColor(Color.RED);

		// 用y轴的集合来设置参数
		lineDataSet.setLineWidth(1.75f); // 线宽
		lineDataSet.setCircleSize(3f);// 显示的圆形大小
		lineDataSet.setColor(Color.WHITE);// 显示颜色
		lineDataSet.setCircleColor(Color.WHITE);// 圆形的颜色
		lineDataSet.setHighLightColor(Color.WHITE); // 高亮的线的颜色

		ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
		lineDataSets.add(lineDataSet); // add the datasets
		LineData lineData = new LineData(xValues, lineDataSets);

		chart.setDrawBorders(true); // 是否在折线图上添加边框
		// no description text
		chart.setDescription("");// 数据描述
		// 如果没有数据的时候，会显示这个，类似listview的emtpyview
		chart.setNoDataTextDescription("You need to provide data for the chart.");
		// enable / disable grid background
		chart.setDrawGridBackground(false); // 是否显示表格颜色
		chart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF); // 表格的的颜色，在这里是是给颜色设置一个透明度
		// enable touch gestures
		chart.setTouchEnabled(false); // 设置是否可以触摸
		// enable scaling and dragging
		chart.setDragEnabled(false);// 是否可以拖拽
		chart.setScaleEnabled(false);// 是否可以缩放
		// if disabled, scaling can be done on x- and y-axis separately
		chart.setPinchZoom(false);//
		// chart.setBackgroundColor(color);// 设置背景
		chart.getAxisRight().setEnabled(false);
		// add data
		chart.setData(lineData); // 设置数据
		// get the legend (only possible after setting data)
		Legend mLegend = chart.getLegend(); // 设置比例图标示，就是那个一组y的value的
		// modify the legend ...
		// mLegend.setPosition(LegendPosition.LEFT_OF_CHART);
		mLegend.setForm(LegendForm.CIRCLE);// 样式
		mLegend.setFormSize(6f);// 字体
		mLegend.setTextColor(Color.WHITE);// 颜色
		// mLegend.setTypeface(mTf);// 字体
		chart.animateX(2500); // 立即执行的动画,x轴
		if(tt>0)
		pingjia.setText(getString(R.string.pingjia_content,initPingjia(tt/yValues.size())));
	}

	private String initPingjia(float gole) {
		String pingjia="";
		if(gole>=90.0f){
			pingjia=getResources().getString(R.string.pingjia_1);
//			return;
		}else
		if(gole>=70.0f){
			pingjia=getResources().getString(R.string.pingjia_2);
//			return;
		}else{
			pingjia=getResources().getString(R.string.pingjia_3);
		}
		return pingjia;
		
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
					initData(list);
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
