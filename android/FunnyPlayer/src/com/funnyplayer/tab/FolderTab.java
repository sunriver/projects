package com.funnyplayer.tab;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.funnyplayer.R;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;

public class FolderTab extends BaseTab {
	private ListView mListView;
	private List<String> mPathList;
	
	public FolderTab(View contentView) {
		super(contentView);
		
		mPathList = new ArrayList<String>();
		mListView = (ListView) contentView.findViewById(R.id.tab_folder_listview);
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
			return list;
		}

		@Override
		protected void onPostExecute(List<String> result) {
			Collections.copy(mPathList, result);
		}
		
	}
	
	
}
