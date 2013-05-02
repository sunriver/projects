package com.funnyplayer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.funnyplayer.net.DownLoadThread;
import com.funnyplayer.net.HttpCallback;
import com.funnyplayer.tab.FolderTab;
import com.funnyplayer.tab.PlaylistTab;

import android.media.AsyncPlayer;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.VideoView;
import android.view.View;

public class MainActivity extends Activity implements View.OnClickListener {
	private static final String TAG = "AsyncPlayer";

	private static final int NOTIFICATION_ID = 133948384;

	private File mMusicDir;

	private FolderTab mFolderTab;
	private PlaylistTab mPlaylistTab;

	private ImageButton mFolderIndicator;
	private ImageButton mPlaylistIndicator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mFolderIndicator = (ImageButton) findViewById(R.id.tab_indicator_folder);
		mPlaylistIndicator = (ImageButton) findViewById(R.id.tab_indicator_playlist);
		mFolderTab = new FolderTab(findViewById(R.id.tab_folder));
		mPlaylistTab = new PlaylistTab(findViewById(R.id.tab_playlist));
		
		init();
	}

	private void init() {
		mFolderIndicator.setOnClickListener(this);
		mPlaylistIndicator.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tab_indicator_folder:
			mFolderTab.show();
			mPlaylistTab.hide();
			break;
		case R.id.tab_indicator_playlist:
			mPlaylistTab.show();
			mFolderTab.hide();
			break;
		}
	}

	private void playFromUrl() {
		// String url =
		// "http://down.51voa.com/201303/0_k9ewwln6_0_2euw79ko.mp3";
		// Uri uri = Uri.parse(url);
		// mAsyncPlayer.play(getApplication(), uri, false,
		// AudioManager.STREAM_MUSIC);

		// mDownLoadThread.add(url, new HttpCallback() {
		// @Override
		// public int callback(String fileName, long totalSize, long progress) {
		// Log.v(TAG, "MainActivity::HttpCallback(): totalSize = " + totalSize +
		// "  progress:" + progress);
		// Uri uri = Uri.parse(fileName);
		// mAsyncPlayer.play(getApplication(), uri, false,
		// AudioManager.STREAM_MUSIC);
		// return 0;
		// }});
	}

	private void showNotification() {
		NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		Notification notification = new Notification(R.drawable.ic_launcher,
				"FunnyPlayer", System.currentTimeMillis());
		notification.defaults = Notification.DEFAULT_ALL;

		PendingIntent pendintIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, MainActivity.class),
				PendingIntent.FLAG_UPDATE_CURRENT);
		String title = getString(R.string.app_name);
		notification.setLatestEventInfo(this, title, title, pendintIntent);
		nm.notify(NOTIFICATION_ID, notification);
	}

}
