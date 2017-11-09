package com.ruixun.widget;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import com.ruixun.R;
import com.ruixun.base.BaseLinearLayout;
import com.ruixun.engine.ActionBarListener;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

@EViewGroup(R.layout.custom_actionbar)
public class ActionBar extends BaseLinearLayout {

	private String titleText, rightText;
	private boolean showLeft, showRight;
	private ActionBarListener listener;

	public ActionBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initMembers(context, attrs);
	}

	public ActionBar(Context context) {
		this(context, null);
	}

	protected void initMembers(Context context, AttributeSet attrs) {
		TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.custom_actionbar);
		titleText = obtainStyledAttributes.getString(R.styleable.custom_actionbar_title_text);
		rightText = obtainStyledAttributes.getString(R.styleable.custom_actionbar_right_text);
		showLeft = obtainStyledAttributes.getBoolean(R.styleable.custom_actionbar_left_enable, true);
		showRight = obtainStyledAttributes.getBoolean(R.styleable.custom_actionbar_right_enable, true);
		obtainStyledAttributes.recycle();
	}

	@ViewById(R.id.left_action)
	ImageView ic_left;
	@ViewById(R.id.title)
	TextView title;
	@ViewById(R.id.right_action)
	DelayButton btn_right;

	@AfterViews
	void initUi() {
		if (!TextUtils.isEmpty(titleText))
			title.setText(titleText);
		if (showLeft)
			ic_left.setVisibility(View.VISIBLE);
		else
			ic_left.setVisibility(View.INVISIBLE);
		if (showRight)
			btn_right.setVisibility(View.VISIBLE);
		else
			btn_right.setVisibility(View.INVISIBLE);
		if (!TextUtils.isEmpty(rightText))
			btn_right.setText(rightText);
	}

	public void setListener(ActionBarListener listener) {
		this.listener = listener;
	}

	@Click
	void left_action() {
		if (null != listener) {
			listener.onLeftAction();
		}
	}

	@Click
	void right_action() {
		if (null != listener) {
			listener.onRightAction();
		}
	}

	public void setTitleText(String title){
		this.titleText=title;
		initUi();
	}
	public void setRightText(String text){
		this.rightText=text;
		initUi();
	}
	
}
