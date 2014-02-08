package com.coco.reader.view;

import com.coco.reader.R;
import com.coco.reader.adapter.SlideMenuAdapter;
import com.coco.reader.adapter.SlideMenuAdapter.SlideMenuItem;
import com.codo.reader.data.Document;
import com.codo.reader.data.DocumentManager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class MenuListFragment extends ListFragment {
	private DocumentManager mDocManager;
	private OnSlideItemSelectListener mOnSlideItemSelectListener;
	private SlideMenuAdapter mSlideMenuAdapter;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list, null);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Activity activity = getActivity();
		mDocManager = DocumentManager.getInstance(activity);
		if (activity instanceof OnSlideItemSelectListener) {
			this.mOnSlideItemSelectListener = (OnSlideItemSelectListener) activity;
		}
		mSlideMenuAdapter = new SlideMenuAdapter(activity);
		String[] docNames = mDocManager.getAllDocuments();
		if (docNames != null) {
			for (String docName : docNames) {
				final String tempDocName = docName.substring(0, docName.length() - 4);
				mSlideMenuAdapter.add(new SlideMenuItem(tempDocName, android.R.drawable.ic_menu_search));
			}
		}
		setListAdapter(mSlideMenuAdapter);
	}
	
	

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		SlideMenuItem item = (SlideMenuItem) v.getTag();
		Document doc = mDocManager.getDocumentByName(item.title);
		if (doc != null && mOnSlideItemSelectListener != null) {
			mDocManager.setSelectDocument(doc);
			mOnSlideItemSelectListener.onSlideItemSelect(doc);
		}
	}
	
	
	public interface OnSlideItemSelectListener {
		public void onSlideItemSelect(Document doc);
	}
	
}
