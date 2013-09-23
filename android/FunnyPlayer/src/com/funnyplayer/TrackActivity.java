package com.funnyplayer;


import java.util.ArrayList;
import java.util.List;

import com.funnyplayer.cache.lrc.LrcProvider;
import com.funnyplayer.service.MusicInfo;
import com.funnyplayer.ui.adapter.PlaylistAdapter;
import com.funnyplayer.util.MusicUtil;
import com.funnyplayer.util.ViewUtil;
import common.Consts;

import android.app.ActionBar;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.BaseColumns;
import android.provider.MediaStore.Audio;
import android.provider.MediaStore.MediaColumns;
import android.provider.MediaStore.Audio.AlbumColumns;
import android.provider.MediaStore.Audio.ArtistColumns;
import android.provider.MediaStore.Audio.AudioColumns;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class TrackActivity extends Activity implements LoaderCallbacks<Cursor>, OnItemClickListener {
	
 	private final static String TAG = "TrackActivity";
	private ListView mPlayListView;
	private PlaylistAdapter mAdapter;
	private Consts.TYPE mMiniType;
	private int mMediaIdIndex;
	private int mTitleIndex;
	private int mArtistIndex;
	private LrcProvider mLrcProvider;
	private String mPlayItemPath;
	private Handler mHandler;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.track);
		mPlayListView = (ListView) findViewById(R.id.playListView);

		mAdapter = new PlaylistAdapter(this, R.layout.playlist_item);
		mLrcProvider = LrcProvider.getInstance(getApplicationContext());

		mPlayListView.setAdapter(mAdapter);
		mPlayListView.setOnItemClickListener(this);
		
		mHandler = new Handler();
		
		
		initActionBar();

		Intent intent = getIntent();
		Bundle args = (intent != null) ? intent.getExtras() : null;
		mMiniType = args != null ? Consts.TYPE.valueOf(args.getString(Consts.MIME_TYPE)) : Consts.TYPE.ALBUM;
		int gridIndex = args != null ? args.getInt(Consts.PLAY_GRID_INDEX) : 0;
		int itemIndex = args != null ? args.getInt(Consts.PLAY_ITEM_INDEX) : -1;
		mPlayItemPath = mMiniType.getIndex() +":" + gridIndex;
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

	private CursorLoader createLoaderForAlbum(Bundle args) {
		String album = (args != null) ? args.getString(AlbumColumns.ALBUM) : "";
		this.setTitle(album);
		StringBuilder where = new StringBuilder();
		where.append(AudioColumns.IS_MUSIC + "=1")
		.append(" AND " + MediaColumns.TITLE + " != ''");
		if (args != null) {
			long albumId = args.getLong(BaseColumns._ID);
			// String albumName = args.getString(Consts.ALBUM_KEY);
			where.append(" AND " + AudioColumns.ALBUM_ID + "=" + albumId);
		}
		String[] projection = new String[] { BaseColumns._ID,
				MediaColumns.TITLE, AudioColumns.ALBUM, AudioColumns.ARTIST };
		Uri uri = Audio.Media.EXTERNAL_CONTENT_URI;
		String sortOrder = Audio.Media.DEFAULT_SORT_ORDER;
		sortOrder = Audio.Media.TRACK + ", " + sortOrder;
		return new CursorLoader(this, uri, projection, where.toString(), null,
				sortOrder);
	}
	
	
	private CursorLoader createLoaderForArtist(Bundle args) {
		String artist = (args != null) ? args.getString(ArtistColumns.ARTIST) : "";
		this.setTitle(artist);
		StringBuilder where = new StringBuilder();
		where.append(AudioColumns.IS_MUSIC + "=1")
		.append(" AND " + MediaColumns.TITLE + " != ''");
		if (args != null) {
			long artistId = args.getLong(BaseColumns._ID);
			where.append(" AND " + AudioColumns.ARTIST_ID + "=" + artistId);
		}
		String[] projection = new String[] { BaseColumns._ID,
				MediaColumns.TITLE, AudioColumns.ALBUM, AudioColumns.ARTIST };
		Uri uri = Audio.Media.EXTERNAL_CONTENT_URI;
		String sortOrder = Audio.Media.DEFAULT_SORT_ORDER;
		sortOrder = Audio.Media.TRACK + ", " + sortOrder;
		return new CursorLoader(this, uri, projection, where.toString(), null,
				sortOrder);
	}
	
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		switch (mMiniType) {
		case ALBUM:
			return createLoaderForAlbum(args);
		case ARTIST:
			return createLoaderForArtist(args);
		}
		return null;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		// Check for database errors
		if (data == null) {
			return;
		}

		mMediaIdIndex = data.getColumnIndexOrThrow(BaseColumns._ID);
		mTitleIndex = data.getColumnIndexOrThrow(MediaColumns.TITLE);
		mArtistIndex = data.getColumnIndexOrThrow(AudioColumns.ARTIST);

		mAdapter.setPlaylistIdIndex(mMediaIdIndex);
		mAdapter.setPlaylistNameIndex(mTitleIndex);
		mAdapter.setPlaylistArtistIndex(mArtistIndex);
		mAdapter.changeCursor(data);
		mPlayListView.invalidateViews();

	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		if (mAdapter != null) {
			mAdapter.changeCursor(null);
		}
	}
	
	private void addPlaylist() {
		Cursor cursor = mAdapter.getCursor();
		if (null == cursor) {
			return;
		}
		List<MusicInfo> infoList = new ArrayList<MusicInfo>();
		for (boolean hasNext = cursor.moveToFirst(); hasNext; hasNext = cursor.moveToNext()) {
			String title = cursor.getString(mTitleIndex);
			String artist = cursor.getString(mArtistIndex);
			long mediaId = cursor.getLong(mMediaIdIndex);
			infoList.add(new MusicInfo(artist, title, mediaId, mPlayItemPath));
		}
		MusicUtil.addPlaylist(getApplicationContext(), infoList);
	}
	
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (!mPlayItemPath.equals(MusicUtil.getPlayItemPath())) {
			MusicUtil.setPlayItemPath(mPlayItemPath);
			addPlaylist();		
			MusicUtil.start(getApplicationContext(), position, true);
		} else {
			MusicUtil.start(getApplicationContext(), position);
		}
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
