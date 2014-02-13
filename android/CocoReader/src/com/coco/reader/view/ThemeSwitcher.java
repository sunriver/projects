package com.coco.reader.view;

import com.coco.reader.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;

public class ThemeSwitcher {
	private Context mContext;
	private ActionBar mActionBar;

	public ThemeSwitcher(Context ctx, ActionBar actionBar) {
		this.mContext = ctx;
		this.mActionBar = actionBar;
	}
	
	
	public void switchLightBlue() {
		Drawable d = mContext.getResources().getDrawable(R.drawable.actionbar_bg_selector);
		mActionBar.setBackgroundDrawable(d);
	}
	
	public void switchLightGreen() {
		Drawable d = mContext.getResources().getDrawable(android.R.color.holo_green_light);
		mActionBar.setBackgroundDrawable(d);
	}
}
