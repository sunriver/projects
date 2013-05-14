package com.funnyplayer.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.funnyplayer.R;
import com.funnyplayer.ui.widgets.ScrollTabView.TabAdapter;

public class ScrollTabAdapter implements TabAdapter {
	private final String[] data;
	private final LayoutInflater mInflater;
	
	public ScrollTabAdapter(Context context) {
	      data = context.getResources().getStringArray(R.array.tab_titles);
	      mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position) {
		Button btn = (Button) mInflater.inflate(R.layout.tab_item, null);
		btn.setText(data[position]);
		return btn;
	}

}
