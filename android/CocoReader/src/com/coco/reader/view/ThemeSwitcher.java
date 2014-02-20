package com.coco.reader.view;

import com.aphidmobile.flip.FlipViewController;
import com.coco.reader.R;
import com.coco.reader.data.ThemeType;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.widget.ScrollView;

public class ThemeSwitcher {
	private Context mContext;
	private ActionBar mActionBar;
	private FlipViewController mFlipView;
	private Resources mResources;


	public ThemeSwitcher(Context ctx, ActionBar actionBar, FlipViewController flipView) {
		this.mContext = ctx;
		this.mResources = ctx.getResources();
		this.mActionBar = actionBar;
		this.mFlipView = flipView;
	}
	
	
//	public void switchLightBlue() {
//		Drawable d = mResources.getDrawable(R.drawable.actionbar_bg_selector);
//		mActionBar.setBackgroundDrawable(d);
//		PageView pv = (PageView) mFlipView.getSelectedView();
//		if (pv != null) {
//			pv.setPageBackground(android.R.color.background_light);
//		}
//	}
	
	public void switchGrayGreen() {
		Drawable d = mResources.getDrawable(R.drawable.page_bg_gray_green);
		mActionBar.setBackgroundDrawable(d);
		PageView pv = (PageView) mFlipView.getSelectedView();
		if (pv != null) {
			pv.setPageBackground(R.drawable.page_bg_gray_green);
		}
	}
	
	public void switchLightGreen() {
		Drawable d = mResources.getDrawable(R.drawable.page_bg_lightgreen);
		mActionBar.setBackgroundDrawable(d);
		PageView pv = (PageView) mFlipView.getSelectedView();
		if (pv != null) {
			pv.setPageBackground(R.drawable.page_bg_lightgreen);
		}
	}
	
	public void switchDeepYellow() {
		Drawable d = mResources.getDrawable(R.drawable.page_bg_deep_yellow);
		mActionBar.setBackgroundDrawable(d);
		PageView pv = (PageView) mFlipView.getSelectedView();
		if (pv != null) {
			pv.setPageBackground(R.drawable.page_bg_deep_yellow);
		}
	}
}
