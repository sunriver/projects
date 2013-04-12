package com.oobe.video;

import java.io.IOException;
import java.io.InputStream;

import com.oobe.R;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoPlayerActivity extends Activity {
	
	private VideoView mVideoView;
    private Dialog mProgressDialog;
	public static int mSecond = 0;
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_RIGHT_ICON);
//		int height = getResources().getDisplayMetrics().heightPixels;
//		int width = getResources().getDisplayMetrics().widthPixels;
		setContentView(R.layout.video);
		
		mVideoView = (VideoView) findViewById(R.id.video);
		mProgressDialog = ProgressDialog.show(this, "Video Player", "Loading...");
//		String path = "/sdcard/Movies/Angels.mp3";
//		mVideoView.setVideoPath(path);
		String path = "http://v.youku.com/v_show/id_XMzQ3MzgwMzM2.html";
		mVideoView.setVideoPath(path);
//		Uri uri = new Uri();
//		mVideoView.setVideoURI(uri);

		mVideoView.setMediaController(new MediaController(this));
        mVideoView.setOnPreparedListener(new OnPreparedListener(){
			@Override
			public void onPrepared(MediaPlayer mp) {
				mVideoView.setBackgroundColor(Color.BLACK);
				mProgressDialog.dismiss();
		}}); 
        
        mVideoView.setOnCompletionListener(new OnCompletionListener(){
			@Override
			public void onCompletion(MediaPlayer mp) {
				Toast.makeText(VideoPlayerActivity.this, "Video is over, thanks!", 2000).show();
			}});
		
	}
	@Override
	protected void onResume() {
		super.onResume();
		mVideoView.seekTo(mSecond);
		mVideoView.start();
	}
	@Override
	protected void onStop() {
		super.onStop();
		mVideoView.pause();
		mSecond = mVideoView.getCurrentPosition();
	}
	
	private static final int  ITEM_START = 0;
	private static final int ITEM_STOP = 1;
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case ITEM_STOP:
			mVideoView.pause();
			mSecond = mVideoView.getCurrentPosition();
			break;
		case ITEM_START:
			mVideoView.seekTo(mSecond);
			mVideoView.start();
		}
		return true;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	   MenuItem item = menu.add(0, ITEM_START, 0, "Start");
	   item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
	   item = menu.add(0, ITEM_STOP, 0, "Stop");
	   item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
	   return super.onCreateOptionsMenu(menu);
	}
	
}
