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


public class SlideMenuAdapter extends ArrayAdapter<SlideMenuItem> {
	public SlideMenuAdapter(Context context) {
		super(context, 0);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, null);
		}
		SlideMenuItem item = getItem(position);
		ImageView icon = (ImageView) convertView.findViewById(R.id.row_icon);
		icon.setImageResource(item.iconRes);
		TextView title = (TextView) convertView.findViewById(R.id.row_title);
		title.setText(item.title);
		convertView.setTag(item);
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
