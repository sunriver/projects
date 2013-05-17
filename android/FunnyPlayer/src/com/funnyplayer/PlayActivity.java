package com.funnyplayer;

import com.funnyplayer.ui.adapter.PlaylistAdapter;

import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.MediaStore.Audio;
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
		
		getLoaderManager().initLoader(0, null, this);
	}
	
	
	private void extractValues(Bundle bundle) {
		
	}
	
	

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = new String[] {BaseColumns._ID, PlaylistsColumns.NAME
        };
        Uri uri = Audio.Playlists.EXTERNAL_CONTENT_URI;
        String sortOrder = Audio.Playlists.DEFAULT_SORT_ORDER;
        return new CursorLoader(this, uri, projection, null, null, sortOrder);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Check for database errors
        if (data == null) {
            return;
        }

        int playlistIdIndex = data.getColumnIndexOrThrow(BaseColumns._ID);
        int playlistNameIndex = data.getColumnIndexOrThrow(PlaylistsColumns.NAME);
        mAdapter.setPlaylistIdIndex(playlistIdIndex);
        mAdapter.setPlaylistNameIndex(playlistNameIndex);
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
