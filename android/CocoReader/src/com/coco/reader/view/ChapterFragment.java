package com.coco.reader.view;

import com.coco.reader.R;
import com.coco.reader.adapter.ChapterAdapter;
import com.coco.reader.data.ChapterItem;
import com.coco.reader.data.Document;
import com.coco.reader.data.DocumentManager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ChapterFragment extends ListFragment {
	private DocumentManager mDocManager;
	private ChapterSelectListener mChapterSelectListener;
	private ChapterAdapter mChapterAdapter;
	private ListView mListView;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list, null);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Activity activity = getActivity();
		mDocManager = DocumentManager.getInstance(activity);
		if (activity instanceof ChapterSelectListener) {
			this.mChapterSelectListener = (ChapterSelectListener) activity;
		}
		mListView = this.getListView();
		mChapterAdapter = new ChapterAdapter(activity, mListView);
		String[] docTitles = mDocManager.getAllTitles();
		if (docTitles != null) {
			for (int index = 0, len = docTitles.length; index < len; index ++) {
				mChapterAdapter.add(new ChapterItem(docTitles[index], String.valueOf(index + 1)));
			}
		}
		setListAdapter(mChapterAdapter);
	}
	
	

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		ChapterItem item = (ChapterItem) v.getTag();
		Document doc = mDocManager.getDocument(item);
		if (doc != null && mChapterSelectListener != null) {
			updateChapterItem(v);
			mDocManager.setSelectDocument(doc);
			mChapterSelectListener.onChanpterSelect(doc);
		}
	}
	
	private void updateChapterItem(View v) {
		View lastedSelectView = (View) mListView.getTag();
		if (lastedSelectView != null) {
			lastedSelectView.setActivated(false);
		}
		v.setActivated(true);
		mListView.setTag(v);
	}
	
	
	public interface ChapterSelectListener {
		public void onChanpterSelect(Document doc);
	}
	
}
