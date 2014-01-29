package com.coco.reader.view;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

public class PageView extends EditText {
	private final static String TAG = PageView.class.getSimpleName();
	
	public final static String ACTION_PAGE_BOTTOM = "com.coco.reader.action.PAGE_BOTTOM";
	public final static String ACTION_PAGE_TOP = "com.coco.reader.action.PAGE_TOP";
	
	private GestureDetector mGestureDetector;
	private SimpleOnGestureListener mGestureListener = new SimpleOnGestureListener() {
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			int scrollX = getScrollX();
			int scrollY = getScrollY();
			int top = getTop();
			int bottom = getBottom();
			int vsRange = computeVerticalScrollRange();
			Log.d(TAG, "onScroll()+ scrollX=" + scrollX + "  scrollY=" + scrollY + " top=" + top + "  bottom=" + bottom + " vsRange=" + vsRange);
			if (scrollY == 0) {
//				mScrollByTouchEnabled = false;
				sendBroadcastForScrollEvent(ACTION_PAGE_TOP);
//				mPageScrollChangeListener.onPageScrollToTop();
			} else if (isScrollToBottom(scrollY)) {
//				mScrollByTouchEnabled = false;
//				mPageScrollChangeListener.onPageScrollToBottom();
				sendBroadcastForScrollEvent(ACTION_PAGE_BOTTOM);
			}
			return super.onScroll(e1, e2, distanceX, distanceY);
		}
	};
	
	private PageScrollChangeListener mPageScrollChangeListener;
	
	private boolean mScrollByTouchEnabled;

	public PageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mScrollByTouchEnabled = true;
		mGestureDetector = new GestureDetector(mGestureListener);
		setOnTouchListener(new OnTouchListener () {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (mScrollByTouchEnabled) {
					return mGestureDetector.onTouchEvent(event);
				}
				return false;
			}
		});
	}
	
	private boolean  isScrollToBottom(int scrollY) {
		int vsRange = this.computeVerticalScrollRange();
		if (scrollY + this.getBottom() >= vsRange) {
			return true;
		}
		return false;
	}

	public PageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public void setOnPageScrollChangeListener(PageScrollChangeListener l) {
		this.mPageScrollChangeListener = l;
	}

	
	public void reset() {
		mScrollByTouchEnabled = true;
	}
	
	
	private void sendBroadcastForScrollEvent(String action) {
		Context ctx = this.getContext();
		Intent intent = new Intent(action);
		ctx.sendBroadcast(intent);
	}

}
