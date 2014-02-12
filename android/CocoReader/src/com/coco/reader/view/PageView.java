package com.coco.reader.view;

import com.coco.reader.R;
import com.coco.reader.data.Page;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.RelativeLayout;
public class PageView extends RelativeLayout {
	private final static String TAG = PageView.class.getSimpleName();
	private EditText mContentEt;
	private Page mPage;
	private Handler mHandler;
	private Runnable mScrollTask;
	
	public PageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mHandler = new Handler();
		mScrollTask = new Runnable() {
			@Override
			public void run() {
				mContentEt.scrollBy(0, mPage.getScrollDY());
			}
		};
	}
	
    public PageView(Context context) {
        this(context, null);
    }

    public PageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
	
	public void init() {
		mContentEt = (EditText) PageView.this.findViewById(R.id.tv_content);
	}
	
	public void setPage(Page page) {
		this.mPage = page;
		mContentEt.setText(mPage.getContent());
		mHandler.postDelayed(mScrollTask, 500);
	}
	
	public void setTextSize(final float size) {
		mContentEt.setTextSize(size + 15);
	}
	
	public void setText(String text) {
		mContentEt.setText(text);
	}
	
	public int getPageScrollDy() {
		return mContentEt.getScrollY();
	}
	

	
}
