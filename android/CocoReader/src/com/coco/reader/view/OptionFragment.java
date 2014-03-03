package com.coco.reader.view;

import com.coco.reader.R;
import com.coco.reader.data.OptionSetting;
import com.coco.reader.data.ThemeType;
import com.sunriver.common.view.RadioGroupEx;
import com.sunriver.common.view.RadioGroupEx.OnCheckedChangeListener;

import android.support.v4.app.Fragment;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class OptionFragment extends Fragment implements
		OnSeekBarChangeListener, OnCheckedChangeListener {
	private SeekBar mTextSizeSeekBar;
	private SeekBar mLineSpaceSeekBar;
	private RadioGroupEx mThemeRg;
	private TextSizeChangeListener mTextSizeChangeListener;
	private LineSpaceChangeListener mLineSpaceChangeListener;
	private ThemeSwitcher mThemeSwitcher;
	private OptionSetting mOptionSetting;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Activity activity = getActivity();
		if (activity instanceof TextSizeChangeListener) {
			this.mTextSizeChangeListener = (TextSizeChangeListener) activity;
		}
		if (activity instanceof LineSpaceChangeListener) {
			this.mLineSpaceChangeListener = (LineSpaceChangeListener) activity;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.option, null);
		mTextSizeSeekBar = (SeekBar) v.findViewById(R.id.sb_text_size);
		mLineSpaceSeekBar = (SeekBar) v.findViewById(R.id.sb_line_space);
		mThemeRg = (RadioGroupEx) v.findViewById(R.id.rg_theme);

		mTextSizeSeekBar.setOnSeekBarChangeListener(this);
		mLineSpaceSeekBar.setOnSeekBarChangeListener(this);
		mThemeRg.setOnCheckedChangeListener(this);
		return v;
	}

	public float getTextSize() {
		return mOptionSetting.getTextSize();
	}

	public void setTextSize(int size) {
		mTextSizeSeekBar.setProgress(size);
		mOptionSetting.setTextSize(size);
	}

	public void setOptionSetting(OptionSetting ops) {
		this.mOptionSetting = ops;
		switch (ops.getThemeType()) {
		case LightGreen:
			mThemeRg.check(R.id.rb_theme_light_green);
			break;
		case GrayGreen:
			mThemeRg.check(R.id.rb_theme_gray_green);
			break;
		case DeepYellow:
			mThemeRg.check(R.id.rb_theme_deep_yellow);
			break;
		case LightYellow:
			mThemeRg.check(R.id.rb_theme_light_yellow);
			break;
		case GrayWhite:
			mThemeRg.check(R.id.rb_theme_gray_white);
			break;
		case DeepGray:
			mThemeRg.check(R.id.rb_theme_deep_gray);
			break;
		}
	}

	public OptionSetting getOptionSetting() {
		return mOptionSetting;
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		int id = seekBar.getId();
		if (id == R.id.sb_text_size) {
			updateTextSize(progress);
		} else if (id == R.id.sb_line_space) {
			updateLineSpace(progress);
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		int id = seekBar.getId();
		int size = seekBar.getProgress();
		if (id == R.id.sb_text_size) {
			updateTextSize(size);
		} else if (id == R.id.sb_line_space) {
			updateLineSpace(size);
		}

	}
	private void updateTextSize(int size) {
		mOptionSetting.setTextSize(size);
		mTextSizeChangeListener.onSizeChangeing(size);
	}
	
	private void updateLineSpace(int size) {
		mOptionSetting.setLineSpace(size);
		mLineSpaceChangeListener.onLineSpaceChanging(size);
	}

	@Override
	public void onCheckedChanged(RadioGroupEx group, int checkedId) {
		if (checkedId == R.id.rb_theme_light_green) {
			mOptionSetting.setThemeType(ThemeType.LightGreen);
			mThemeSwitcher.switchLightGreen();
		} else if (checkedId == R.id.rb_theme_gray_green) {
			mOptionSetting.setThemeType(ThemeType.GrayGreen);
			mThemeSwitcher.switchGrayGreen();
		} else if (checkedId == R.id.rb_theme_deep_yellow) {
			mOptionSetting.setThemeType(ThemeType.DeepYellow);
			mThemeSwitcher.switchDeepYellow();
		} else if (checkedId == R.id.rb_theme_light_yellow) {
			mOptionSetting.setThemeType(ThemeType.LightYellow);
			mThemeSwitcher.switchLightYellow();
		} else if (checkedId == R.id.rb_theme_gray_white) {
			mOptionSetting.setThemeType(ThemeType.GrayWhite);
			mThemeSwitcher.switchGrayWhite();
		} else if (checkedId == R.id.rb_theme_deep_gray) {
			mOptionSetting.setThemeType(ThemeType.DeepGray);
			mThemeSwitcher.switchDeepGray();
		}
		// switch (checkedId) {
		// case R.id.rb_theme_light_green:
		// mOptionSetting.setThemeType(ThemeType.LightGreen);
		// mThemeSwitcher.switchLightGreen();
		// break;
		// case R.id.rb_theme_gray_green:
		// mOptionSetting.setThemeType(ThemeType.GrayGreen);
		// mThemeSwitcher.switchGrayGreen();
		// break;
		// case R.id.rb_theme_deep_yellow:
		// mOptionSetting.setThemeType(ThemeType.DeepYellow);
		// mThemeSwitcher.switchDeepYellow();
		// break;
		// case R.id.rb_theme_light_yellow:
		// mOptionSetting.setThemeType(ThemeType.LightYellow);
		// mThemeSwitcher.switchLightYellow();
		// break;
		// case R.id.rb_theme_gray_white:
		// mOptionSetting.setThemeType(ThemeType.GrayWhite);
		// mThemeSwitcher.switchGrayWhite();
		// break;
		// case R.id.rb_theme_deep_gray:
		// mOptionSetting.setThemeType(ThemeType.DeepGray);
		// mThemeSwitcher.switchDeepGray();
		// break;
		// default:
		// ;
		// }
	}

	public interface TextSizeChangeListener {
		public void onSizeChangeing(int size);

		public void onSizeChanged(int size);
	}
	
	public interface LineSpaceChangeListener {
		public void onLineSpaceChanging(int size);
		
		public void onLineSpaceChanged(int size);
	}

	public ThemeSwitcher getThemeSwitcher() {
		return this.mThemeSwitcher;
	}

	public void setThemeSwitcher(ThemeSwitcher switcher) {
		this.mThemeSwitcher = switcher;
	}

}
