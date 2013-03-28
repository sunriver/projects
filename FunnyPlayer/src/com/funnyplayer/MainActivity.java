package com.funnyplayer;


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
import android.widget.ImageView;
import android.view.View;

public class MainActivity extends Activity implements View.OnClickListener {
	private static final String TAG = "AsyncPlayer";
	private static final int NOTIFICATION_ID = 133948384;

	private AsyncPlayer mAsyncPlayer;
	private ImageView mPreviousImg;
	private ImageView mNextImg;
	private ImageView mStartOrStopImg;

	private boolean isPlaying;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mPreviousImg = (ImageView) findViewById(R.id.previous);
		mNextImg = (ImageView) findViewById(R.id.next);
		mStartOrStopImg = (ImageView) findViewById(R.id.startOrStop);
		mAsyncPlayer = new AsyncPlayer(TAG);
		initListener();
		play();
		showNotification();
	}
	

    
    @Override
    protected void onPause() {
    	super.onPause();
    }

	private void initListener() {
		mPreviousImg.setOnClickListener(this);
		mNextImg.setOnClickListener(this);
		mStartOrStopImg.setOnClickListener(this);
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
				play();
			}
			break;
		}
	}

	private void previous() {

	}

	private void next() {

	}
	
	private void play() {
		isPlaying = true;
		mAsyncPlayer.play(this,Uri.parse("file://"
				+ Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES)
				+ "/test.mp3"), false, AudioManager.STREAM_MUSIC);
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
