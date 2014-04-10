package com.sunriver.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.view.View.OnClickListener;

public class PopuMenuLayout extends LinearLayout implements OnClickListener {
	private ViewGroup mContentView;
	private PopupWindow mPopup;
	private int mPopuWidth;
	private int mPopuHeight;
	private int mPopuMaxHeight;
	private Drawable mPopuDrawable;
	
	public PopuMenuLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	

	public PopuMenuLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, com.sunriver.R.styleable.com_sunriver_common_view_PopuMenuLayout, defStyle, 0);
        mPopuWidth = a.getLayoutDimension(com.sunriver.R.styleable.com_sunriver_common_view_PopuMenuLayout_popupWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopuHeight = a.getLayoutDimension(com.sunriver.R.styleable.com_sunriver_common_view_PopuMenuLayout_popupHeight, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopuMaxHeight = a.getLayoutDimension(com.sunriver.R.styleable.com_sunriver_common_view_PopuMenuLayout_popupMaxHeight, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopuDrawable = a.getDrawable(com.sunriver.R.styleable.com_sunriver_common_view_PopuMenuLayout_popupBackground);
        
        for (int i = 0, len = this.getChildCount(); i < len; i++) {
        	View child = this.getChildAt(i);
        	child.setOnClickListener(this);
        }
        this.setOnClickListener(this);
	}
	
	
	public void setContentView(ViewGroup contentView) {
		this.mContentView = contentView;
	}

	public void show() {
		mPopup = new PopupWindow(mContentView, mPopuWidth, mPopuHeight);
		mPopup.setFocusable(true);
		mPopup.setBackgroundDrawable(mPopuDrawable);
		mPopup.showAsDropDown(this);
	}
	
	public void dimiss() {
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
        dimiss();
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

}
