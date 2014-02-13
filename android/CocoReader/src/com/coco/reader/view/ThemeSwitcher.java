package com.coco.reader.view;

import com.coco.reader.R;
import com.coco.reader.data.ThemeType;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;

public class ThemeSwitcher {
	private Context mContext;
	private ActionBar mActionBar;
	private Resources mResources;
	private ThemeType mThemeType;


	public ThemeSwitcher(Context ctx, ActionBar actionBar) {
		this.mContext = ctx;
		this.mResources = ctx.getResources();
		this.mActionBar = actionBar;
	}
	
	
	public void switchLightBlue() {
		mThemeType = ThemeType.LightBlue;
		Drawable d = mResources.getDrawable(R.drawable.actionbar_bg_selector);
		mActionBar.setBackgroundDrawable(d);
	}
	
	public void switchLightGreen() {
		mThemeType = ThemeType.LightGreen;
		Drawable d = mResources.getDrawable(android.R.color.holo_green_light);
		mActionBar.setBackgroundDrawable(d);
	}
	
	public ThemeType getThemeType() {
		return mThemeType;
	}
	
	public void setThemeType(ThemeType type) {
		this.mThemeType = type;
	}
}
