package com.coco.reader.data;

import com.coco.reader.R;

public class OptionSetting {
	private int mTextSize;
	private ThemeType mThemeType;

	public int getTextSize() {
		return mTextSize;
	}

	public void setTextSize(int size) {
		this.mTextSize = size;
	}

	public void setThemeType(ThemeType type) {
		this.mThemeType = type;
	}

	public ThemeType getThemeType() {
		return this.mThemeType;
	}

	public int getThemeId() {
		switch (mThemeType) {
		case LightBlue:
			return R.style.Reader_Theme_LightBlue;
		case LightGreen:
			return R.style.Reader_Theme_LightGreen;
		}
		return R.style.Reader_Theme_LightBlue;
	}

	public int getPageBackgroundResId() {
		switch (mThemeType) {
		case LightBlue:
			return android.R.color.background_light;
		case LightGreen:
			return R.drawable.page_bg_lightgreen;
		}
		return android.R.color.background_light;
	}

}
