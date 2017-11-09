package com.ruixun.widget;
import org.androidannotations.annotations.EView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * 自定义延迟按钮
 * 
 * @author qinchi
 *
 */
@EView
public class DelayButton extends Button {

	private Long LAST_CLICK_TIME = 0l;

	public DelayButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public DelayButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DelayButton(Context context) {
		super(context);
	}

	@Override
	public boolean performClick() {
		if (isInDelay()) {
			return false;
		}
		return super.performClick();
	}

	private synchronized boolean isInDelay() {
		boolean isDelay = false;
		long currentTime = System.currentTimeMillis();
		if (currentTime - LAST_CLICK_TIME > 2000) {
			LAST_CLICK_TIME = currentTime;
			isDelay = false;
		} else {
			isDelay = true;
		}
		return isDelay;
	}

}
