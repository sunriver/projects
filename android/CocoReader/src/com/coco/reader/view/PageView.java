package com.coco.reader.view;

import com.coco.reader.R;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.RelativeLayout;
public class PageView extends RelativeLayout {
	private final static String TAG = PageView.class.getSimpleName();
	private EditText mContentEt;
	
	public PageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		new Handler().post(new Runnable() {
			@Override
			public void run() {
				mContentEt = (EditText) PageView.this.findViewById(R.id.tv_content);
			}});
	}
	
	
    public PageView(Context context) {
        this(context, null);
    }

    public PageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
	
	
	public void setTextSize(float size) {
		if (mContentEt != null) {
			mContentEt.setTextSize(size + 15);
		}
	}
	
	public void setText(String text) {
		if (mContentEt != null) {
			mContentEt.setText(text);
		}
	}
	
}
