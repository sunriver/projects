package com.coco.reader.view;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

public class PageView extends EditText {
	private final static String TAG = PageView.class.getSimpleName();
	
	public PageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}


	public PageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
}
