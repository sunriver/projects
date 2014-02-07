package com.coco.reader.view;

import com.coco.reader.R;
import com.codo.reader.data.DocumentManager;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuListFragment extends ListFragment {
	private DocumentManager mDocManager;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list, null);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mDocManager = DocumentManager.getInstance(getActivity());
		MenuAdapter adapter = new MenuAdapter(getActivity());
		String[] docNames = mDocManager.getAllDocuments();
		if (docNames != null) {
			for (String docName : docNames) {
				adapter.add(new ListItem(docName, android.R.drawable.ic_menu_search));
			}
		}
		setListAdapter(adapter);
	}

	private class ListItem {
		public String tag;
		public int iconRes;
		public ListItem(String tag, int iconRes) {
			this.tag = tag; 
			this.iconRes = iconRes;
		}
	}

	public class MenuAdapter extends ArrayAdapter<ListItem> {

		public MenuAdapter(Context context) {
			super(context, 0);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, null);
			}
			ImageView icon = (ImageView) convertView.findViewById(R.id.row_icon);
			icon.setImageResource(getItem(position).iconRes);
			TextView title = (TextView) convertView.findViewById(R.id.row_title);
			title.setText(getItem(position).tag);

			return convertView;
		}

	}
}
