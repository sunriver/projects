package com.funnyplayer.ui.fragment;


import com.funnyplayer.R;
import com.funnyplayer.TrackActivity;
import com.funnyplayer.ui.adapter.AlbumAdapter;
import common.Consts;
import common.Consts.TYPE;

import android.app.Fragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.MediaStore.Audio;
import android.provider.MediaStore.Audio.AlbumColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class AlbumFragment extends Fragment implements IFragment, OnItemClickListener, LoaderCallbacks<Cursor> {
    private GridView mGridView;
    
    private AlbumAdapter mAlbumAdapter;
    
    private Cursor mCursor;
    
    private int mAlbumIdIndex;
    private int mAlbumNameIndex;
    private int mArtistNameIndex;
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mGridView = (GridView) getView().findViewById(R.id.albumGridView);
        // AlbumAdapter
        mAlbumAdapter = new AlbumAdapter(getActivity().getApplicationContext(), R.layout.album_gridview_item);
        mGridView.setOnCreateContextMenuListener(this);
        mGridView.setOnItemClickListener(this);
        mGridView.setTextFilterEnabled(true);
        
        // Important!
        getLoaderManager().initLoader(0, null, this);
        
        mGridView.setAdapter(mAlbumAdapter);
    }
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_album, container, false);
		return root;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
        String artistName = mCursor.getString(mArtistNameIndex);
        String albumName = mCursor.getString(mAlbumNameIndex);

        Bundle bundle = new Bundle();
        bundle.putString(Consts.MIME_TYPE, TYPE.ALBUM.name());
        bundle.putInt(Consts.PLAY_GRID_INDEX, position);
        bundle.putLong(BaseColumns._ID, id);
        bundle.putString(AlbumColumns.ALBUM, albumName);
        bundle.putString(AlbumColumns.ARTIST, artistName);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setClass(getActivity(), TrackActivity.class);
        intent.putExtras(bundle);
        getActivity().startActivity(intent);
		
	}


	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = { BaseColumns._ID, AlbumColumns.ALBUM, AlbumColumns.ARTIST, AlbumColumns.ALBUM_ART };
        Uri uri = Audio.Albums.EXTERNAL_CONTENT_URI;
        String sortOrder = Audio.Albums.DEFAULT_SORT_ORDER;
        return new CursorLoader(getActivity(), uri, projection, null, null, sortOrder);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Check for database errors
        if (data == null) {
            return;
        }

        mAlbumIdIndex = data.getColumnIndexOrThrow(BaseColumns._ID);
        mAlbumNameIndex = data.getColumnIndexOrThrow(AlbumColumns.ALBUM);
        mArtistNameIndex = data.getColumnIndexOrThrow(AlbumColumns.ARTIST);
        
        mAlbumAdapter.setAlbumIdIndex(mAlbumIdIndex);
        mAlbumAdapter.setAlbumNameIndex(mAlbumNameIndex);
        mAlbumAdapter.setArtistNameIndex(mArtistNameIndex);
        mAlbumAdapter.changeCursor(data);
        mCursor = data;
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
        if (mAlbumAdapter != null) {
        	mAlbumAdapter.changeCursor(null);
        }
	}

	private void startTrack(int gridIndex, int itemIndex) {
		Cursor cursor = (Cursor)  mGridView.getItemAtPosition(gridIndex);
		if (null == cursor) {
			return;
		}
		long id = mGridView.getItemIdAtPosition(gridIndex);
        String artistName = mCursor.getString(mArtistNameIndex);
        String albumName = mCursor.getString(mAlbumNameIndex);

        Bundle bundle = new Bundle();
        bundle.putString(Consts.MIME_TYPE, TYPE.ALBUM.name());
        bundle.putInt(Consts.PLAY_GRID_INDEX, gridIndex);
        bundle.putInt(Consts.PLAY_ITEM_INDEX, itemIndex);
        bundle.putLong(BaseColumns._ID, id);
        bundle.putString(AlbumColumns.ALBUM, albumName);
        bundle.putString(AlbumColumns.ARTIST, artistName);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setClass(getActivity(), TrackActivity.class);
        intent.putExtras(bundle);
        getActivity().startActivity(intent);
	}

	@Override
	public void selectItem(int gridIndex, int itemIndex) {
		if (null == mGridView) {
			return;
		}
		mGridView.smoothScrollToPosition(gridIndex);
		startTrack(gridIndex, itemIndex);
	}
	
	
}
