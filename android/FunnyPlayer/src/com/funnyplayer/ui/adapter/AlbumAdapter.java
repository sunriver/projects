package com.funnyplayer.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;

public class AlbumAdapter extends SimpleCursorAdapter {

	public AlbumAdapter(Context context, int layout) {
		super(context, layout,  null, new String[] {}, new int[] {}, 0);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
	    final View view = super.getView(position, convertView, parent);
	    //load attached bitmap for view;
		return view;
	}

}
