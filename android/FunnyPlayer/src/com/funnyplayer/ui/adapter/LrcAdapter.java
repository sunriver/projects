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
	
	public File getLrcFile(int position) {
		Item item = mItemList.get(position);
		String path = mLrcPath + "/" + item.file;
		return new File(path);
	}
	
	
	public void add(String artist, String song) {
		mItemList.add(new Item(song, artist, null));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		ViewHolder holder = null;
		if (null == v) {
			v = mInflater.inflate(R.layout.lrc_item, null);
			holder = new ViewHolder();
			holder.artistTv = (TextView) v.findViewById(R.id.lrc_item_artist);
			holder.songTv = (TextView) v.findViewById(R.id.lrc_item_song);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		Item item = mItemList.get(position);
		holder.artistTv.setText(item.artist);
		holder.songTv.setText(item.song);
		return v;
	}
	
	private static class ViewHolder {
		TextView artistTv;
		TextView songTv;
	}


	private static class Item {
		final String artist;
		final String song;
		final String file;
		
		public Item(String song, String artist, String file) {
			this.song = song;
			this.artist = artist;
			this.file = file;
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
				String[] temp = file.split(".lrc")[0].split("_");
				String song = temp[0];
				String artist = temp[1];
				list.add(new Item(song, artist, file));
			}
			return list;
		}

		@Override
		protected void onPostExecute(List<Item> result) {
			for (Item item : result) {
				mItemList.add(item);
			}
			notifyDataSetChanged();
		}
		
	}
}
