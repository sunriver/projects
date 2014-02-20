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
		case LightGreen:
			return R.style.Reader_Theme_LightGreen;
		case GrayGreen:
			return R.style.Reader_Theme_GrayGreen;
		case DeepYellow:
			return R.style.Reader_Theme_DeepYellow;
		}
		return R.style.Reader_Theme_LightGreen;
	}

	public int getPageBackgroundResId() {
		switch (mThemeType) {
		case LightGreen:
			return R.drawable.page_bg_light_green;
		case GrayGreen:
			return R.drawable.page_bg_gray_green;
		case DeepYellow:
			return R.drawable.page_bg_deep_yellow;
		}
		return R.drawable.page_bg_light_green;
	}

}
