package com.sunriver.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.view.View.OnClickListener;

public class PopuMenu extends LinearLayout implements OnClickListener {
	
	private ViewGroup mContentView;
	private PopupWindow mPopup;
	private int mPopuWidth;
	private int mPopuHeight;
	private int mPopuBackgroudResId;
	

	public PopuMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	
	public void setContentView(ViewGroup contentView) {
		this.mContentView = contentView;
	}

	private void show() {
		mPopup = new PopupWindow(mContentView, mPopuWidth, LayoutParams.WRAP_CONTENT);
		mPopup.showAsDropDown(this);
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
        
        if (mPopup != null && mPopup.isShowing()) {
            mPopup.dismiss();
        }
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
