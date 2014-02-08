package com.coco.reader.adapter;

import com.coco.reader.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.coco.reader.adapter.SlideMenuAdapter.SlideMenuItem;
import com.coco.reader.data.Document;
import com.coco.reader.data.DocumentManager;


public class SlideMenuAdapter extends ArrayAdapter<SlideMenuItem> {
	private DocumentManager mDocManager;
	private int mSelectionPos ;
	
	public SlideMenuAdapter(Context context) {
		super(context, 0);
		mDocManager = DocumentManager.getInstance(context);
		mSelectionPos = 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, parent, false);
		}
		SlideMenuItem item = getItem(position);
		TextView title = (TextView) convertView;
		title.setText(item.title);
		convertView.setTag(item);
		
		Document doc = mDocManager.getSelectDocument();
		if (doc != null && doc.getDocName().equals(item.title)) {
			convertView.setBackgroundResource(R.drawable.nav_list_bg_pressed);
		} else {
			convertView.setBackgroundResource(android.R.color.transparent);
		}
		return convertView;
	}

	public static class SlideMenuItem {
		public String title;
		public int iconRes;

		public SlideMenuItem(String title, int iconRes) {
			this.title = title;
			this.iconRes = iconRes;
		}
	}

}
