package com.funnyplayer.ui.adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.funnyplayer.R;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LrcAdapter extends BaseAdapter {
	private static final String TAG = "LrcAdapter";
	
	private List<Item> mItemList;
	private final String mLrcPath;
	private LayoutInflater mInflater;
	
	public LrcAdapter(Context context, final String lrcPath) {
		mItemList = new ArrayList<Item>();
		mLrcPath = lrcPath;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		new Task().execute();
	}
	
	@Override
	public int getCount() {
		return mItemList.size();
	}

	@Override
	public Object getItem(int position) {
		return mItemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (null == convertView) {
			convertView = mInflater.inflate(R.layout.lrc_item, null);
		}
		TextView tv = (TextView) convertView.findViewById(R.id.lrc_item_text);
		Item item = mItemList.get(position);
		tv.setText(item.song + " " + item.artist);
		return convertView;
	}


	private static class Item {
		final String artist;
		final String song;
		
		public Item(String artist, String song) {
			this.artist = artist;
			this.song = song;
		}
	}
	
	private class Task extends AsyncTask<Void, Void, List<Item>> {

		@Override
		protected List<Item> doInBackground(Void... params) {
			List<Item> list = new ArrayList<Item>();
			if (TextUtils.isEmpty(mLrcPath)) {
				return list;
			}
			File f = new File(mLrcPath);
			if (!f.exists() || !f.isDirectory()) {
				return list;
			}
			for (String file : f.list()) {
				Log.v(TAG, file);
				String[] temp = file.split("_");
				list.add(new Item(temp[0], temp[1]));
			}
			return list;
		}

		@Override
		protected void onPostExecute(List<Item> result) {
			if (result.size() > 0) {
				System.arraycopy(result, 0, mItemList, 0, result.size());
				notifyDataSetChanged();
			}
		}
		
	}
}
