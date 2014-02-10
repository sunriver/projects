package com.coco.reader.view;

import com.coco.reader.R;
import com.coco.reader.data.DocumentManager;
import com.coco.reader.view.ChapterFragment.OnSlideItemSelectListener;

import android.support.v4.app.Fragment;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class OptionFragment extends Fragment {
	private SeekBar mSeekBar;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Activity activity = getActivity();
		if (activity instanceof OnSeekBarChangeListener) {
			mSeekBar.setOnSeekBarChangeListener((OnSeekBarChangeListener) activity);
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.option, null);
		mSeekBar = (SeekBar) v.findViewById(R.id.sb_text_size);
		return v;
	}

}
