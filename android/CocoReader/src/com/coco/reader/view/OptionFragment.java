package com.coco.reader.view;

import com.coco.reader.R;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class OptionFragment extends Fragment implements
		OnSeekBarChangeListener, OnCheckedChangeListener {
	private SeekBar mSeekBar;
	private RadioGroup mThemeRg;
	private TextSizeChangeListener mTextSizeChangeListener;
	private ThemeSwitcher mThemeSwitcher;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Activity activity = getActivity();
		if (activity instanceof TextSizeChangeListener) {
			this.mTextSizeChangeListener = (TextSizeChangeListener) activity;
		}
		
//		mThemeSwitcher = new ThemeSwitcher(activity.getApplicationContext(), activity.getActionBar());

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.option, null);
		mSeekBar = (SeekBar) v.findViewById(R.id.sb_text_size);
		mThemeRg = (RadioGroup) v.findViewById(R.id.rg_theme);

		mSeekBar.setOnSeekBarChangeListener(this);
		mThemeRg.setOnCheckedChangeListener(this);
		return v;
	}

	public float getTextSize() {
		return mSeekBar.getProgress();
	}

	public void setTextSize(int size) {
		mSeekBar.setProgress(size);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		mTextSizeChangeListener.onSizeChangeing(progress);
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		mTextSizeChangeListener.onSizeChangeing(seekBar.getProgress());
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.rb_theme_default:
			mThemeSwitcher.switchLightGreen();
			break;
		case R.id.rb_theme_lightblue:
			mThemeSwitcher.switchLightBlue();
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
