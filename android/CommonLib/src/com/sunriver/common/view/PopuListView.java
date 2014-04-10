package com.sunriver.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.view.View.OnClickListener;

public class PopuListView extends LinearLayout implements OnClickListener {
	private ViewGroup mContentView;
	private PopupWindow mPopup;
	private int mPopuWidth;
	private int mPopuHeight;
	private int mPopuMaxHeight;
	private Drawable mPopuDrawable;
	private ListView mListView;
	private OnItemClickListener mOnItemClickListener;
	
	public PopuListView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	

	public PopuListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, com.sunriver.R.styleable.com_sunriver_common_view_PopuMenuLayout, defStyle, 0);
        mPopuWidth = a.getLayoutDimension(com.sunriver.R.styleable.com_sunriver_common_view_PopuMenuLayout_popupWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopuHeight = a.getLayoutDimension(com.sunriver.R.styleable.com_sunriver_common_view_PopuMenuLayout_popupHeight, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopuMaxHeight = a.getLayoutDimension(com.sunriver.R.styleable.com_sunriver_common_view_PopuMenuLayout_popupMaxHeight, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopuDrawable = a.getDrawable(com.sunriver.R.styleable.com_sunriver_common_view_PopuMenuLayout_popupBackground);
        mListView = new ListView(context);
        LayoutParams lp = new LinearLayout.LayoutParams(mPopuWidth, mPopuHeight);
        mListView.setLayoutParams(lp);
        for (int i = 0, len = this.getChildCount(); i < len; i++) {
        	View child = this.getChildAt(i);
        	child.setOnClickListener(this);
        }
        this.setOnClickListener(this);
	}
	
	public void setAdapter(ListAdapter adapter) {
		mListView.setAdapter(adapter);
	}
	
	public void setOnItemClickListener(OnItemClickListener listener) {
		this.mOnItemClickListener = listener;
		if (listener != null) {
			mListView.setOnItemClickListener(new WrapOnItemClickListener());
		} else {
			mListView.setOnItemClickListener(null);
		}
	}
	
	private void show() {
		mPopup = new PopupWindow(mListView, mPopuWidth, mPopuHeight);
		mPopup.setFocusable(true);
		mPopup.setBackgroundDrawable(mPopuDrawable);
		mPopup.showAsDropDown(this);
	}
	
	private void dismiss() {
        if (mPopup != null && mPopup.isShowing()) {
            mPopup.dismiss();
        }
	}
	
	private boolean isShowing() {
		if (mPopup != null && mPopup.isShowing()) {
			return true;
		}
		return false;
	}
	
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        dismiss();
    }

	@Override
	public void onClick(View v) {
		if (isShowing()) {
			mPopup.dismiss();
			mPopup = null;
		} else {
			show();
		}
	}

	private class WrapOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if (mOnItemClickListener != null) {
				mOnItemClickListener.onItemClick(parent, view, position, id);
				dismiss();
			}
		}
		
	}

}
