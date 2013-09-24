package com.funnyplayer.ui.adapter;


import com.funnyplayer.R;
import com.funnyplayer.util.MusicUtil;

import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class PlaylistAdapter extends SimpleCursorAdapter {
	private int mSelectItemPos;
    
    private int mPlaylistIdIndex;
    private int mPlaylistNameIndex;
    private int mPlaylistArtistIndex;
    private String mPlayItemPath;
    
    public void setPlaylistIdIndex(int index) {
    	this.mPlaylistIdIndex = index;
    }

    public void setPlaylistNameIndex(int index) {
    	this.mPlaylistNameIndex = index;
    }
    
    public void setPlaylistArtistIndex(int index) {
    	this.mPlaylistArtistIndex = index;
    }
    
    
    public void setPlayItemPath(String path) {
    	this.mPlayItemPath = path;
    }
    
    public void setSelectItem(int pos) {
    	this.mSelectItemPos = pos;
    }
    
	static class ViewHolder {
		TextView mTitleView;
		TextView mArtistView;
		ImageView mVolumnImg;
	}

	public PlaylistAdapter(Context context, int layout) {
		this(context, layout, null);
	}
	
	public PlaylistAdapter(Context context, int layout, String playItemPath) {
		super(context.getApplicationContext(), layout,  null, new String[] {}, new int[] {}, 0);
		mPlayItemPath = playItemPath;
	}
	

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View v = null;
		ViewHolder holder = null;
		if (convertView != null) {
			v = convertView;
			holder = (ViewHolder) v.getTag();
		} else {
			v = super.getView(position, convertView, parent);
			if (v != null) {
				holder = new ViewHolder();
				holder.mTitleView = (TextView) v.findViewById(R.id.playlist_item_name);
				holder.mArtistView = (TextView) v.findViewById(R.id.playlist_item_artist);
				holder.mVolumnImg = (ImageView) v.findViewById(R.id.playlist_item_state);
				v.setTag(holder);
			}
		}

		
		Cursor cursor = (Cursor) getItem(position);
		String title =  cursor.getString(mPlaylistNameIndex);
		String artist =  cursor.getString(mPlaylistArtistIndex);
		holder.mTitleView.setText(title);
		holder.mArtistView.setText(artist);
//		String currPlayItemPath = MusicUtil.getPlayItemPath();
//		if (!TextUtils.isEmpty(currPlayItemPath) && currPlayItemPath.equals(mPlayItemPath) && MusicUtil.getItemPos() == position) {
//			holder.mVolumnImg.setVisibility(View.VISIBLE);
//		} else {
//			holder.mVolumnImg.setVisibility(View.INVISIBLE);
//		}
		return v;
	}

}
