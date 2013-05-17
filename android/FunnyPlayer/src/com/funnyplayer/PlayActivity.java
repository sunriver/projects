package com.funnyplayer;

import com.funnyplayer.cache.Consts;
import com.funnyplayer.ui.adapter.PlaylistAdapter;

import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.MediaStore.Audio;
import android.provider.MediaStore.MediaColumns;
import android.provider.MediaStore.Audio.AudioColumns;
import android.provider.MediaStore.Audio.PlaylistsColumns;
import android.widget.ImageView;
import android.widget.ListView;

public class PlayActivity extends Activity implements LoaderCallbacks<Cursor> {
	private ListView mPlayListView;
	private ImageView mPlayImgView;
	private PlaylistAdapter mAdapter;
	private Cursor mCursor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.playlist);
		mPlayListView = (ListView) findViewById(R.id.playListView);
		mPlayImgView = (ImageView) findViewById(R.id.playImageView);
		mAdapter = new PlaylistAdapter(this, R.layout.playlist_item);
		
		mPlayListView.setAdapter(mAdapter);
		
		Intent intent = getIntent();
		Bundle args = (intent != null) ? intent.getExtras() : null; 
		
		getLoaderManager().initLoader(0, args, this);
	}
	
	
	private void extractValues(Bundle bundle) {
		
	}
	
	

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        StringBuilder where = new StringBuilder();
        where.append(AudioColumns.IS_MUSIC + "=1").append(" AND " + MediaColumns.TITLE + " != ''");
		if (args != null) {
			long albumId = args.getLong(BaseColumns._ID);
//			String albumName = args.getString(Consts.ALBUM_KEY);
			where.append(" AND " + AudioColumns.ALBUM_ID + "=" + albumId);
		}
        String[] projection = new String[] {
                BaseColumns._ID, MediaColumns.TITLE, AudioColumns.ALBUM, AudioColumns.ARTIST
        };
        Uri uri = Audio.Media.EXTERNAL_CONTENT_URI;
        String sortOrder = Audio.Media.DEFAULT_SORT_ORDER;
        sortOrder = Audio.Media.TRACK + ", " + sortOrder;
        return new CursorLoader(this, uri, projection, where.toString(), null, sortOrder);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Check for database errors
        if (data == null) {
            return;
        }

       int  mMediaIdIndex = data.getColumnIndexOrThrow(BaseColumns._ID);
        int mTitleIndex = data.getColumnIndexOrThrow(MediaColumns.TITLE);
        int mArtistIndex = data.getColumnIndexOrThrow(AudioColumns.ARTIST);
        mAdapter.setPlaylistIdIndex(mMediaIdIndex);
        mAdapter.setPlaylistNameIndex(mTitleIndex);
        mAdapter.changeCursor(data);
        mCursor = data;
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		if (mAdapter != null) {
			mAdapter.changeCursor(null);
			mCursor = null;
		}
	}

}
