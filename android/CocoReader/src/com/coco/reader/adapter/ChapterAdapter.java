package com.coco.reader.adapter;

import com.coco.reader.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.coco.reader.data.Document;
import com.coco.reader.data.DocumentManager;


public class ChapterAdapter extends ArrayAdapter<ChapterItem> {
	private DocumentManager mDocManager;
	private ListView mListView;
	
	public ChapterAdapter(Context context, ListView listView) {
		super(context, 0);
		mDocManager = DocumentManager.getInstance(context);
		mListView = listView;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, parent, false);
		}
		ChapterItem item = getItem(position);
		TextView tv = (TextView) convertView;
		tv.setText(item.title);
		convertView.setTag(item);
		Document doc = mDocManager.getSelectDocument();
		if (doc != null && doc.getDocName().equals(item.itemName)) {
			tv.setActivated(true);
			//Remember the select view.
			mListView.setTag(tv); 
		} else {
			tv.setActivated(false);
		}
		return convertView;
	}
	

}
