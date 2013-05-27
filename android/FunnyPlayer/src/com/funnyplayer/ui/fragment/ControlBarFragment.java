package com.funnyplayer.ui.fragment;

import com.funnyplayer.R;
import com.funnyplayer.service.MusicService;
import com.funnyplayer.util.MusicUtil;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class ControlBarFragment extends Fragment implements View.OnClickListener {
    private MusicService mMusicService;
    private ImageView mPrevImg;
    private ImageView mPlayOrPauseImg;
    private ImageView mNextImg;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.control_bar, null);
		mPrevImg = (ImageView) v.findViewById(R.id.playPrevious);
		mPlayOrPauseImg = (ImageView) v.findViewById(R.id.playOrPause);
		mNextImg = (ImageView) v.findViewById(R.id.playNext);
		mPrevImg.setOnClickListener(this);
		mPlayOrPauseImg.setOnClickListener(this);
		mNextImg.setOnClickListener(this);
		
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		mMusicService = MusicUtil.getService(getActivity());
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.playPrevious:
			mMusicService.previous();
			break;
		case R.id.playOrPause:
			if (mMusicService.isPlaying()) {
				mMusicService.pause();
				mPlayOrPauseImg.setSelected(false);
			} else {
				mPlayOrPauseImg.setSelected(true);
				mMusicService.play();
			}
			break;
		case R.id.playNext:
			mMusicService.next();
			break;
		}
	}

}
