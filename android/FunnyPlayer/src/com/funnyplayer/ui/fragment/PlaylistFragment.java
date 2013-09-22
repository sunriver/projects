package com.funnyplayer.ui.fragment;


import java.util.ArrayList;
import java.util.List;

import com.funnyplayer.R;
import com.funnyplayer.cache.lrc.LrcProvider;
import com.funnyplayer.service.MusicInfo;
import com.funnyplayer.ui.adapter.PlaylistAdapter;
import com.funnyplayer.util.MusicUtil;
import common.Consts;

import android.app.Fragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.MediaStore.Audio;
import android.provider.MediaStore.MediaColumns;
import android.provider.MediaStore.Audio.AudioColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class PlaylistFragment extends Fragment implements IFragment, OnItemClickListener, LoaderCallbacks<Cursor> {
 	private final static String TAG = "PlaylistFragment";
    private ListView mPlayListView;
    private Cursor mCursor;
    private PlaylistAdapter mAdapter;
    private int mMediaIdIndex ;
    private int mTitleIndex;
    private int mArtistIndex;
	private LrcProvider mLrcProvider;
	private String mPlayItemPath;
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

		mAdapter = new PlaylistAdapter(getActivity(), R.layout.playlist_item);
		mPlayListView.setAdapter(mAdapter);
		mPlayListView.setOnItemClickListener(this);
		mLrcProvider = LrcProvider.getInstance(getActivity().getApplicationContext());
		mPlayItemPath = Consts.TYPE.PLAYLIST.getIndex() +":" + 0;
        // Important!
        getLoaderManager().initLoader(0, null, this);
        
    }
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.playlist, container, false);
		mPlayListView = (ListView) root.findViewById(R.id.playListView);
		return root;
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
		MusicUtil.addPlaylist(getActivity(), infoList);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (!mPlayItemPath.equals(MusicUtil.getPlayItemPath())) {
			MusicUtil.setPlayItemPath(mPlayItemPath);
			addPlaylist();		
			MusicUtil.start(getActivity(), position, true);
		} else {
			MusicUtil.start(getActivity(), position);
		}
		mAdapter.setSelectItem(position);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        StringBuilder where = new StringBuilder();
        where.append(AudioColumns.IS_MUSIC + "=1").append(" AND " + MediaColumns.TITLE + " != ''");
        String[] projection = new String[] {
                BaseColumns._ID, MediaColumns.TITLE, AudioColumns.ALBUM, AudioColumns.ARTIST
        };
        Uri uri = Audio.Media.EXTERNAL_CONTENT_URI;
        String sortOrder = Audio.Media.DEFAULT_SORT_ORDER;
        sortOrder = Audio.Media.TRACK + ", " + sortOrder;
        return new CursorLoader(getActivity(), uri, projection, where.toString(), null, sortOrder);
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
		mAdapter.changeCursor(data);
		mCursor = data;

	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
        if (mAdapter != null) {
        	mAdapter.changeCursor(null);
        }
	}

	@Override
	public void selectItem(int gridIndex, int itemIndex) {
		if (mPlayListView != null) {
			mPlayListView.smoothScrollToPosition(itemIndex);
			mAdapter.setSelectItem(itemIndex);
			mAdapter.notifyDataSetChanged();
		}
	}
	
	
}
