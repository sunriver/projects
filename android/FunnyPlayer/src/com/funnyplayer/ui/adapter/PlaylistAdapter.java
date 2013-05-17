package com.funnyplayer.ui.adapter;

import com.funnyplayer.R;

import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.provider.MediaStore.Audio.PlaylistsColumns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class PlaylistAdapter extends SimpleCursorAdapter {

    
    private int mPlaylistIdIndex;
    private int mPlaylistNameIndex;
    
    public void setPlaylistIdIndex(int index) {
    	this.mPlaylistIdIndex = index;
    }

    public void setPlaylistNameIndex(int index) {
    	this.mPlaylistNameIndex = index;
    }
    
	static class ViewHolder {
		TextView mTitleView;
	}

	public PlaylistAdapter(Context context, int layout) {
		super(context.getApplicationContext(), layout,  null, new String[] {}, new int[] {}, 0);
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
				v.setTag(holder);
			}
		}
		
		Cursor cursor = (Cursor) getItem(position);
		return v;
	}

}
