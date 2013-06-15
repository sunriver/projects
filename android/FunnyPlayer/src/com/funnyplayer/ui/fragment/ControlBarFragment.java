package com.funnyplayer.ui.fragment;

import com.funnyplayer.R;
import com.funnyplayer.service.MusicService;
import com.funnyplayer.util.MusicUtil;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class ControlBarFragment extends Fragment implements View.OnClickListener {
    private ImageView mPrevImg;
    private ImageView mPlayOrPauseImg;
    private ImageView mNextImg;
    private BroadcastReceiver mReceiver;
	
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
		super.onActivityCreated(savedInstanceState);
		mReceiver = new BroadcastReceiver(){
			@Override
			public void onReceive(Context context, Intent intent) {
				if (intent.getAction().equals(MusicUtil.FilterAction.PLAYER_STOPED)) {
					mPlayOrPauseImg.setSelected(false);
				} else if (intent.getAction().equals(MusicUtil.FilterAction.PLAYER_PLAYING))  {
					mPlayOrPauseImg.setSelected(true);
				}
			}
			
		};
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(MusicUtil.FilterAction.PLAYER_PLAYING);
		intentFilter.addAction(MusicUtil.FilterAction.PLAYER_STOPED);
		getActivity().registerReceiver(mReceiver, intentFilter);
		
		mPlayOrPauseImg.setSelected(MusicUtil.isPlaying());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.playPrevious:
			MusicUtil.play(getActivity());
			break;
		case R.id.playOrPause:
			if (MusicUtil.isPlaying()) {
				MusicUtil.Pause(getActivity());
				mPlayOrPauseImg.setSelected(false);
			} else {
				mPlayOrPauseImg.setSelected(true);
				MusicUtil.play(getActivity());
			}
			break;
		case R.id.playNext:
			MusicUtil.next(getActivity());
			break;
		}
	}

	@Override
	public void onDestroy() {
		getActivity().unregisterReceiver(mReceiver);
		super.onDestroy();
	}
	

}
