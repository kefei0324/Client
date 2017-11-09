package com.ruixun.widget;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import com.ruixun.R;
import com.ruixun.base.BaseLinearLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

@EViewGroup(R.layout.item_controlle)
public class ItemControlle extends BaseLinearLayout {

	public ItemControlle(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ItemControlle(Context context) {
		super(context);
	}

	public void setContentUp(String up) {

	}

	public void setContentCenture(String centure) {
		tv_centure.setText(centure);
	}

	public void setContentDown(String down) {

	}

	@ViewById(R.id.img_up)
	ImageView img_up;
	@ViewById(R.id.img_down)
	ImageView img_down;
	@ViewById(R.id.tv_centure)
	TextView tv_centure;
	private ItemControlleListener listener;

	@Click
	void img_up() {
		if (null != listener)
			listener.imgUp();
	}

	@Click
	void img_down() {
		if (null != listener)
			listener.imgDown();
	}

	@Click
	void tv_centure() {
		if (null != listener) {
			listener.updateTvCenture();
		}
	}

	public interface ItemControlleListener {
		void imgUp();

		void imgDown();

		void updateTvCenture();
	}

	public void setListener(ItemControlleListener listener) {
		if (null != listener)
			this.listener = listener;
	}

}
