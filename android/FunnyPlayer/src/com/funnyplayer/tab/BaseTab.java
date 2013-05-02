package com.funnyplayer.tab;

import android.view.View;

public class BaseTab {
	protected View mContentView;
	
	public BaseTab(View contentView) {
		mContentView = contentView;
	}
	
	public void show() {
		mContentView.setVisibility(View.VISIBLE);
	}
	
	public void hide() {
		mContentView.setVisibility(View.INVISIBLE);
	}

}
