package com.funnyplayer.ui.fragment;


import com.funnyplayer.R;
import com.funnyplayer.ui.adapter.AlbumAdapter;

import android.app.Fragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class AlbumFragment extends Fragment implements OnItemClickListener, LoaderCallbacks<Cursor> {
    private GridView mGridView;
    
    private AlbumAdapter mAlbumAdapter;
    
    private Cursor mCursor;
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
		mGridView = (GridView) root.findViewById(R.id.albumGridView);
		return root;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
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

        int albumIdIndex = data.getColumnIndexOrThrow(BaseColumns._ID);
        int albumNameIndex = data.getColumnIndexOrThrow(AlbumColumns.ALBUM);
        int artistNameIndex = data.getColumnIndexOrThrow(AlbumColumns.ARTIST);
        
        mAlbumAdapter.setAlbumIdIndex(albumIdIndex);
        mAlbumAdapter.setAlbumNameIndex(albumNameIndex);
        mAlbumAdapter.setArtistNameIndex(artistNameIndex);
        mAlbumAdapter.changeCursor(data);
        mCursor = data;
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
        if (mAlbumAdapter != null) {
        	mAlbumAdapter.changeCursor(null);
        }
	}
}
