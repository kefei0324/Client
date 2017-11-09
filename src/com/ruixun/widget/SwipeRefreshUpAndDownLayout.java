package com.ruixun.widget;

import org.androidannotations.annotations.EViewGroup;

import com.ruixun.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

@EViewGroup
public class SwipeRefreshUpAndDownLayout extends SwipeRefreshLayout implements OnScrollListener {

	public SwipeRefreshUpAndDownLayout(Context context) {
		super(context);
	}

	@SuppressLint("InflateParams")
	public SwipeRefreshUpAndDownLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop()*4;

		mListViewFooter = LayoutInflater.from(context).inflate(R.layout.listview_footer, null, false);
	}

	private int mTouchSlop;
	private ListView mListView;
	private OnLoadListener mOnLoadListener;
	private View mListViewFooter;
	private int mYDown;
	private int mLastY;
	private boolean isLoading = false;

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);

		if (mListView == null) {
			getListView();
		}
	}

	private void getListView() {
		int childs = getChildCount();
		if (childs > 0) {
			View childView = getChildAt(0);
			if (childView instanceof ListView) {
				mListView = (ListView) childView;
				mListView.setOnScrollListener(this);
			}
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		final int action = event.getAction();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			// 按下
			mYDown = (int) event.getRawY();
			break;

		case MotionEvent.ACTION_MOVE:
			// 移动
			mLastY = (int) event.getRawY();
			if(canLoad()){
//				loadData();
				setLoading(true);
			}
			break;

		case MotionEvent.ACTION_UP:
			// 抬起
			if (canLoad_afterMove()) {
				loadData();
			}
			break;
		default:
			break;
		}

		return super.dispatchTouchEvent(event);
	}

	private boolean canLoad_afterMove() {
		return mListViewFooter.isShown();
	}

	private boolean canLoad() {
		return isBottom() && !isLoading && isPullUp();
	}

	private boolean isBottom() {

		if (mListView != null && mListView.getAdapter() != null) {
			return mListView.getLastVisiblePosition() == (mListView.getAdapter().getCount() - 1);
		}
		return false;
	}

	private boolean isPullUp() {
		return (mYDown - mLastY) >= mTouchSlop;
	}

	private void loadData() {
		if (mOnLoadListener != null) {
//			setLoading(true);
			mOnLoadListener.onLoad();
		}
	}

	public void setLoading(boolean loading) {
		isLoading = loading;
		if (isLoading) {
			mListView.addFooterView(mListViewFooter);
		} else {
			mListView.removeFooterView(mListViewFooter);
			mYDown = 0;
			mLastY = 0;
		}
	}

	public void setOnLoadListener(OnLoadListener loadListener) {
		mOnLoadListener = loadListener;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//		if (canLoad()) {
//			loadData();
//		}
	}

	public static interface OnLoadListener {
		public void onLoad();
	}
}
