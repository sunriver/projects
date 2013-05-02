package com.funnyplayer.tab;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.funnyplayer.R;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ListView;

public class FolderTab extends BaseTab {
	private ListView mListView;
	private FolderAdapter mAdapter;
	private Context mContext;

	
	public FolderTab(View contentView) {
		super(contentView);
		mContext = contentView.getContext().getApplicationContext();
		mAdapter = new FolderAdapter(mContext);
		mListView = (ListView) contentView.findViewById(R.id.tab_folder_listview);
		mListView.setAdapter(mAdapter);
		init();
	}
	
	private void init() {
		new FoloderAysncTask().execute();
	}
	
	class FoloderAysncTask extends AsyncTask<Void, Void, List<String>> {

		@Override
		protected List<String> doInBackground(Void... params) {
			List<String> list = new ArrayList<String>();
			//Obtain all music file in the file system.
			String[] projection = new String[]{MediaStore.Video.Media.TITLE, MediaStore.Video.Media.DATA};
			Cursor cursor = mContext.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
					projection, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
			try {
				cursor.moveToFirst();
				int counter = cursor.getCount();
				for (int i = 0; i < counter; i++) {
					String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
					String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
					list.add(path);
					cursor.moveToNext();
				}
			} finally {
				cursor.close();
			}
			return list;
		}

		@Override
		protected void onPostExecute(List<String> result) {
			mAdapter.addPath(result);
		}
		
		
		
	}
	
	
}
