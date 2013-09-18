package com.funnyplayer.ui.adapter;


import com.funnyplayer.R;
import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class PlaylistAdapter extends SimpleCursorAdapter {
	private int mSelectItemPos;
    
    private int mPlaylistIdIndex;
    private int mPlaylistNameIndex;
    private int mPlaylistArtistIndex;
    
    public void setPlaylistIdIndex(int index) {
    	this.mPlaylistIdIndex = index;
    }

    public void setPlaylistNameIndex(int index) {
    	this.mPlaylistNameIndex = index;
    }
    
    public void setPlaylistArtistIndex(int index) {
    	this.mPlaylistArtistIndex = index;
    }
    
    public void setSelectItem(int pos) {
    	this.mSelectItemPos = pos;
    }
    
	static class ViewHolder {
		TextView mTitleView;
	}

	public PlaylistAdapter(Context context, int layout) {
		super(context.getApplicationContext(), layout,  null, new String[] {}, new int[] {}, 0);
		mSelectItemPos = -1;
	}
	
	private Handler mHandler = new Handler();
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
		final View tempView = v;
		if (position == mSelectItemPos) {
			mHandler.post(new Runnable() {
				public void run() {
					tempView.setPressed(true);
				}});
		}
		
		Cursor cursor = (Cursor) getItem(position);
		String musicName =  cursor.getString(mPlaylistNameIndex);
		holder.mTitleView.setText(musicName);
		return v;
	}

}
