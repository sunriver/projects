package com.funnyplayer.ui.fragment;


import com.funnyplayer.R;
import com.funnyplayer.TrackActivity;
import com.funnyplayer.ui.adapter.AlbumAdapter;
import com.funnyplayer.ui.adapter.ArtistAdapter;

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
import android.provider.MediaStore.Audio.ArtistColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class ArtistFragment extends Fragment implements IFragment, OnItemClickListener, LoaderCallbacks<Cursor> {
    private GridView mGridView;
    
    private ArtistAdapter mArtistAdapter;
    
    private Cursor mCursor;
    
    private int mArtistIdIndex;
    private int mArtistNumAlbumsIndex;
    private int mArtistNameIndex;
 
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // AlbumAdapter
        mArtistAdapter = new ArtistAdapter(getActivity().getApplicationContext(), R.layout.album_gridview_item);
        mGridView.setOnCreateContextMenuListener(this);
        mGridView.setOnItemClickListener(this);
        mGridView.setTextFilterEnabled(true);
        
        // Important!
        getLoaderManager().initLoader(0, null, this);
        
        mGridView.setAdapter(mArtistAdapter);
    }
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_album, container, false);
		mGridView = (GridView) root.findViewById(R.id.albumGridView);
		return root;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
        String artistName = mCursor.getString(mArtistNameIndex);
        String artistNumAlbums= mCursor.getString(mArtistNumAlbumsIndex);
        
        Bundle bundle = new Bundle();
        bundle.putString(Consts.MIME_TYPE, TYPE.ARTIST.name());
        bundle.putInt(Consts.PLAY_GRID_INDEX, position);
        bundle.putLong(BaseColumns._ID, id);
        bundle.putString(ArtistColumns.ARTIST, artistName);
        bundle.putString(ArtistColumns.NUMBER_OF_ALBUMS, artistNumAlbums);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setClass(getActivity(), TrackActivity.class);
        intent.putExtras(bundle);
        getActivity().startActivity(intent);
		
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                BaseColumns._ID, ArtistColumns.ARTIST, ArtistColumns.NUMBER_OF_ALBUMS
        };
        Uri uri = Audio.Artists.EXTERNAL_CONTENT_URI;
        String sortOrder = Audio.Artists.DEFAULT_SORT_ORDER;
        return new CursorLoader(getActivity(), uri, projection, null, null, sortOrder);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Check for database errors
        if (data == null) {
            return;
        }

        mArtistIdIndex = data.getColumnIndexOrThrow(BaseColumns._ID);
        mArtistNameIndex = data.getColumnIndexOrThrow(ArtistColumns.ARTIST);
        mArtistNumAlbumsIndex = data.getColumnIndexOrThrow(ArtistColumns.NUMBER_OF_ALBUMS);
        
        mArtistAdapter.setArtistIdIndex(mArtistIdIndex);
        mArtistAdapter.setArtistNameIndex(mArtistNameIndex);
        mArtistAdapter.setArtistNumAlbumsIndexIndex(mArtistNumAlbumsIndex);
        mArtistAdapter.changeCursor(data);
        mCursor = data;
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
        if (mArtistAdapter != null) {
        	mArtistAdapter.changeCursor(null);
        }
	}
	


	@Override
	public void selectItem(int gridIndex, int itemIndex) {
		if (mGridView != null) {
			mGridView.smoothScrollToPosition(gridIndex);
			View child = mGridView.getChildAt(itemIndex);
		}
	}
}
