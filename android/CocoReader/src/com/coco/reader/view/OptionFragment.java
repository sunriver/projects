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
	private SeekBar mSeekBar;
	private RadioGroupEx mThemeRg;
	private TextSizeChangeListener mTextSizeChangeListener;
	private ThemeSwitcher mThemeSwitcher;
	private OptionSetting mOptionSetting;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Activity activity = getActivity();
		if (activity instanceof TextSizeChangeListener) {
			this.mTextSizeChangeListener = (TextSizeChangeListener) activity;
		}
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.option, null);
		mSeekBar = (SeekBar) v.findViewById(R.id.sb_text_size);
		mThemeRg = (RadioGroupEx) v.findViewById(R.id.rg_theme);

		mSeekBar.setOnSeekBarChangeListener(this);
		mThemeRg.setOnCheckedChangeListener(this);
		return v;
	}

	public float getTextSize() {
		return mOptionSetting.getTextSize();
	}

	public void setTextSize(int size) {
		mSeekBar.setProgress(size);
		mOptionSetting.setTextSize(size);
	}
	
	public void setOptionSetting(OptionSetting ops) {
		this.mOptionSetting = ops;
		switch (ops.getThemeType()) {
		case LightGreen:
			mThemeRg.check(R.id.rb_theme_default);
			break;
		case GrayGreen:
			mThemeRg.check(R.id.rb_theme_gray_green);
			break;
		case DeepYellow:
			mThemeRg.check(R.id.rb_theme_deep_yellow);
			break;
		}
	}
	
	public OptionSetting getOptionSetting() {
		return mOptionSetting;
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		mOptionSetting.setTextSize(progress);
		mTextSizeChangeListener.onSizeChangeing(progress);
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		int size = seekBar.getProgress();
		mOptionSetting.setTextSize(size);
		mTextSizeChangeListener.onSizeChangeing(size);
	}

	@Override
	public void onCheckedChanged(RadioGroupEx group, int checkedId) {
		switch (checkedId) {
		case R.id.rb_theme_default:
			mOptionSetting.setThemeType(ThemeType.LightGreen);
			mThemeSwitcher.switchLightGreen();
			break;
		case R.id.rb_theme_gray_green:
			mOptionSetting.setThemeType(ThemeType.GrayGreen);
			mThemeSwitcher.switchGrayGreen();
			break;
		case R.id.rb_theme_deep_yellow:
			mOptionSetting.setThemeType(ThemeType.DeepYellow);
			mThemeSwitcher.switchDeepYellow();
			break;
		default:
			;
		}
	}

	public interface TextSizeChangeListener {
		public void onSizeChangeing(int size);

		public void onSizeChanged(int size);
	}

	public ThemeSwitcher getThemeSwitcher() {
		return this.mThemeSwitcher;
	}

	public void setThemeSwitcher(ThemeSwitcher switcher) {
		this.mThemeSwitcher = switcher;
	}

	
}
