package com.funnyplayer.ui.fragment;

import com.funnyplayer.R;
import com.funnyplayer.util.MusicUtil;
import android.support.v4.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;


public class ControlBarFragment extends Fragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private ImageView mPrevImg;
    private ImageView mPlayOrPauseImg;
    private ImageView mNextImg;
    private BroadcastReceiver mReceiver;
    private TextView mProgressText;
    private SeekBar mProgressBar;
    private Handler mHandler;
    private Runnable mRunnable;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.control_bar, null);
		mPrevImg = (ImageView) v.findViewById(R.id.playPrevious);
		mPlayOrPauseImg = (ImageView) v.findViewById(R.id.playOrPause);
		mNextImg = (ImageView) v.findViewById(R.id.playNext);
		mProgressText = (TextView) v.findViewById(R.id.playProgressText);
		mProgressBar = (SeekBar) v.findViewById(R.id.playProgressBar);
		mPrevImg.setOnClickListener(this);
		mPlayOrPauseImg.setOnClickListener(this);
		mNextImg.setOnClickListener(this);
		mProgressBar.setOnSeekBarChangeListener(this);
		return v;
	}

    private String ShowTime(int time) {  
        time /= 1000;  
        int minute = time / 60;  
        int hour = minute / 60;  
        int second = time % 60;  
        minute %= 60;  
        return String.format("%02d:%02d", minute, second);  
    }  
    
    private void updateProgress() {
        int currentPosition = MusicUtil.getCurrentPos();
        int mMax = MusicUtil.getDuration();  
        mProgressText.setText(ShowTime(currentPosition));  
        mProgressBar.setMax(mMax);  
        mProgressBar.setProgress(currentPosition);
    }
    
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mHandler = new Handler();
		mRunnable = new Runnable() {
			@Override
			public void run() {
				updateProgress();
		        if (MusicUtil.isPlaying()) {
		            mHandler.postDelayed(this, 100); 
		        }
			}
		};
		mReceiver = new BroadcastReceiver(){
			@Override
			public void onReceive(Context context, Intent intent) {
				if (intent.getAction().equals(MusicUtil.FilterAction.PLAYER_PAUSED)) {
					mPlayOrPauseImg.setSelected(false);
					mHandler.removeCallbacks(mRunnable);
				} else if (intent.getAction().equals(MusicUtil.FilterAction.PLAYER_PLAYING))  {
					mPlayOrPauseImg.setSelected(true);
					mHandler.postDelayed(mRunnable, 100);
				}
			}
			
		};
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(MusicUtil.FilterAction.PLAYER_PLAYING);
		intentFilter.addAction(MusicUtil.FilterAction.PLAYER_PAUSED);
		getActivity().registerReceiver(mReceiver, intentFilter);
		
		mPlayOrPauseImg.setSelected(MusicUtil.isPlaying());
		mRunnable.run();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.playPrevious:
			MusicUtil.previouse(getActivity());
			break;
		case R.id.playOrPause:
			if (MusicUtil.isPlaying()) {
				MusicUtil.pause(getActivity());
				mPlayOrPauseImg.setSelected(false);
			} else {
				MusicUtil.play(getActivity());
				if (MusicUtil.isPlaying()) {
					mPlayOrPauseImg.setSelected(true);
				}
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

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		mHandler.removeCallbacks(mRunnable);
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		int progress = seekBar.getProgress();
		if (MusicUtil.isPaused()) {
			MusicUtil.seekTo(progress);
            mProgressText.setText(ShowTime(progress));  
		} else if (MusicUtil.isPlaying()) {
			mHandler.postDelayed(mRunnable, 100);
			MusicUtil.seekTo(progress);
		}
	}
	

}
