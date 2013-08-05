package com.funnyplayer.ui.adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.funnyplayer.R;
import com.funnyplayer.ui.widgets.LrcItemLayout;

public class LrcAdapter extends BaseAdapter {
	private static final String TAG = "LrcAdapter";
	
	private List<Item> mItemList;
	private final String mLrcPath;
	private LayoutInflater mInflater;
	
	public LrcAdapter(Context context, final String lrcPath) {
		mItemList = new ArrayList<Item>();
		mLrcPath = lrcPath;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
	
	
	public void add(String artist, String song, String file) {
		mItemList.add(new Item(song, artist, file));
	}
	
	
	public void removeAllItems() {
		mItemList.clear();
		notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LrcItemLayout itemLayout = (LrcItemLayout) convertView;
		if (null == itemLayout) {
			itemLayout = (LrcItemLayout) mInflater.inflate(R.layout.lrc_item, null);
			itemLayout.init();
		} 
		Item item = mItemList.get(position);
		itemLayout.setArtist(item.artist);
		itemLayout.setSong(item.song);
		itemLayout.setUrl(item.file);
		return itemLayout;
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
