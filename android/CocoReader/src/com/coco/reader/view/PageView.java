package com.coco.reader.view;

import com.coco.reader.R;
import com.coco.reader.data.Page;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
public class PageView extends RelativeLayout {
	private final static String TAG = PageView.class.getSimpleName();
	private TextView mContentEt;
	private ScrollView mContentSv;
	private Page mPage;
	private Handler mHandler;
	private Runnable mScrollTask;
	
	public PageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mHandler = new Handler();
		mScrollTask = new Runnable() {
			@Override
			public void run() {
				mContentSv.scrollTo(0, mPage.getScrollDY());
			}
		};
	}
	
    public PageView(Context context) {
        this(context, null);
    }

    public PageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    @Override
    protected void onAttachedToWindow() {
    	super.onAttachedToWindow();
    	mHandler.postDelayed(mScrollTask, 500);
    }
    
	
	public void init() {
		mContentEt = (TextView) PageView.this.findViewById(R.id.tv_content);
		mContentSv = (ScrollView) PageView.this.findViewById(R.id.sv_content);
	}
	
	
	public void setPage(Page page) {
		this.mPage = page;
		mContentEt.setText(page.getContent());
	}
	
	
	
	public void setTextSize(final float size) {
		mContentEt.setTextSize(size + 15);
	}
	
	public void setText(String text) {
		mContentEt.setText(text);
	}
	
	public int getPageScrollDy() {
		return mContentSv.getScrollY();
	}
	
	
}
