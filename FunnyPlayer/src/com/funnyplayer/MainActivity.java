package com.funnyplayer;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.media.AsyncPlayer;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.view.View;

public class MainActivity extends Activity implements View.OnClickListener {
	private static final String TAG = "AsyncPlayer";
	private static final int NOTIFICATION_ID = 133948384;
	
	
	private File  mMusicDir;
	private AsyncPlayer mAsyncPlayer;
	private ImageView mPreviousImg;
	private ImageView mNextImg;
	private ImageView mStartOrStopImg;
	private ListView  mMusicListView;
	private ArrayAdapter<String> mAdapter;
	private boolean isPlaying;
	private int mCurrentPosition = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mPreviousImg = (ImageView) findViewById(R.id.previous);
		mNextImg = (ImageView) findViewById(R.id.next);
		mStartOrStopImg = (ImageView) findViewById(R.id.startOrStop);
		mMusicListView = (ListView) findViewById(R.id.listview);
		mAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, new ArrayList<String>());
		mAsyncPlayer = new AsyncPlayer(TAG);
		mMusicListView.setAdapter(mAdapter);
		mMusicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES);
		init();
	}
	
	private void init() {
		initListener();
		initAdapter();
		showNotification();
	}
	
	private void initAdapter() {
		for (String fileName : mMusicDir.list()) {
			if (fileName.endsWith(".mp3")) {
				mAdapter.add(fileName);
			}
		}
		mAdapter.notifyDataSetChanged();
	}


	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	
	
    @Override
    protected void onPause() {
    	super.onPause();
    }

	private void initListener() {
		mPreviousImg.setOnClickListener(this);
		mNextImg.setOnClickListener(this);
		mStartOrStopImg.setOnClickListener(this);
		
		mMusicListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mCurrentPosition = position;
				play();
			}
			
		});
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.previous:
			previous();
			break;
		case R.id.next:
			next();
			break;
		case R.id.startOrStop:
			if (isPlaying) {
				mAsyncPlayer.stop();
				isPlaying = false;
			} else {
				isPlaying = true;
			}
			break;
		}
	}

	private void previous() {
		mCurrentPosition = Math.max(0, mCurrentPosition - 1);
		play();
	}

	private void next() {
		mCurrentPosition = Math.min(mAdapter.getCount(), mCurrentPosition + 1);
		play();
	}

	private void play() {
		isPlaying = true;
		String fileName = "/" + mAdapter.getItem(mCurrentPosition);
		Uri uri = Uri.parse("file://"+ mMusicDir + fileName);
		mAsyncPlayer.play(getApplication(), uri,  false, AudioManager.STREAM_MUSIC);
	}
	
	private void showNotification() {
		NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		Notification notification = new Notification(R.drawable.ic_launcher, "FunnyPlayer", System.currentTimeMillis());
		notification.defaults = Notification.DEFAULT_ALL;

		PendingIntent pendintIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
		String title = getString(R.string.app_name);
		notification.setLatestEventInfo(this, title, title, pendintIntent);
		nm.notify(NOTIFICATION_ID, notification);
	}



}
