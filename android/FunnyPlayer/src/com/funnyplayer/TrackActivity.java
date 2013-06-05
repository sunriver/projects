package com.funnyplayer;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.funnyplayer.cache.Consts;
import com.funnyplayer.service.MusicService;
import com.funnyplayer.ui.adapter.PlaylistAdapter;
import com.funnyplayer.util.MusicUtil;
import com.funnyplayer.util.ViewUtil;

import android.app.ActionBar;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Resources;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio;
import android.provider.MediaStore.MediaColumns;
import android.provider.MediaStore.Audio.AudioColumns;
import android.provider.MediaStore.Audio.PlaylistsColumns;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class TrackActivity extends Activity implements LoaderCallbacks<Cursor>,
		OnItemClickListener {
	private final static String TAG = "FunnyPlayer";
	private ListView mPlayListView;
	private PlaylistAdapter mAdapter;
	private Consts.TYPE mMiniType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.playlist);
		mPlayListView = (ListView) findViewById(R.id.playListView);

		mAdapter = new PlaylistAdapter(this, R.layout.playlist_item);

		mPlayListView.setAdapter(mAdapter);
		mPlayListView.setOnItemClickListener(this);
		
		initActionBar();

		Intent intent = getIntent();
		Bundle args = (intent != null) ? intent.getExtras() : null;
		if (args != null) {
			mMiniType = Consts.TYPE.valueOf(args.getString(Consts.MIME_TYPE));
		} else {
			mMiniType = Consts.TYPE.ALBUM;
		}

		getLoaderManager().initLoader(0, args, this);
	}
	
    /**
     * Set the ActionBar title
     */
    private void initActionBar() {
    	ActionBar actionBar = getActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE,
                ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE
                        | ActionBar.DISPLAY_SHOW_HOME);
        ViewUtil.setActionBarBackgroundRepeat(this, actionBar);
    }

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		StringBuilder where = new StringBuilder();
		where.append(AudioColumns.IS_MUSIC + "=1").append(
				" AND " + MediaColumns.TITLE + " != ''");
		if (args != null) {
			long albumId = args.getLong(BaseColumns._ID);
			// String albumName = args.getString(Consts.ALBUM_KEY);
			where.append(" AND " + AudioColumns.ALBUM_ID + "=" + albumId);
		}
		String[] projection = new String[] { BaseColumns._ID, MediaColumns.TITLE, AudioColumns.ALBUM, AudioColumns.ARTIST };
		Uri uri = Audio.Media.EXTERNAL_CONTENT_URI;
		String sortOrder = Audio.Media.DEFAULT_SORT_ORDER;
		sortOrder = Audio.Media.TRACK + ", " + sortOrder;
		return new CursorLoader(this, uri, projection, where.toString(), null,
				sortOrder);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		// Check for database errors
		if (data == null) {
			return;
		}

		int mMediaIdIndex = data.getColumnIndexOrThrow(BaseColumns._ID);
		int mTitleIndex = data.getColumnIndexOrThrow(MediaColumns.TITLE);
		int mArtistIndex = data.getColumnIndexOrThrow(AudioColumns.ARTIST);

		List<Long> idList = new ArrayList<Long>();
		if (data.moveToFirst()) {
			idList.add(data.getLong(mMediaIdIndex));
			while (data.moveToNext()) {
				idList.add(data.getLong(mMediaIdIndex));
			}
		}
		MusicUtil.addPlaylist(getApplicationContext(), idList);
		data.moveToFirst();

		mAdapter.setPlaylistIdIndex(mMediaIdIndex);
		mAdapter.setPlaylistNameIndex(mTitleIndex);
		mAdapter.changeCursor(data);

	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		if (mAdapter != null) {
			mAdapter.changeCursor(null);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		MusicUtil.start(getApplicationContext(), position);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			super.onBackPressed();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
