package com.funnyplayer.tab;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.funnyplayer.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FolderAdapter extends BaseAdapter {
	
	private List<String> mPathList;
    private LayoutInflater mInflater;
	private Context mContext;
	
	public FolderAdapter(Context context) {
		super();
		mContext = context.getApplicationContext();
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mPathList = new ArrayList<String>();
	}
	
	public void addPath(List<String> list) {
		for (String s : list) {
			mPathList.add(s);
		}
		notifyDataSetChanged();
	}
	
	
	@Override
	public int getCount() {
		return mPathList.size();
	}

	@Override
	public Object getItem(int position) {
		return mPathList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (null == convertView) {
			convertView = mInflater.inflate(R.layout.tab_folder_listview_item, null);
			TextView tv = (TextView) convertView.findViewById(R.id.tab_folder_listview_item_text);
			tv.setText(mPathList.get(position));
		}
		return convertView;
	}

}
