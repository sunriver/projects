package com.coco.reader.adapter;

import java.util.ArrayList;
import java.util.List;

import com.coco.reader.R;
import com.coco.reader.view.PageView;
import com.coco.reader.data.Document;
import com.coco.reader.data.DocumentManager;
import com.coco.reader.data.Page;
import com.sunriver.common.utils.ApiUtil;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class PageAdapter extends BaseAdapter implements View.OnClickListener {
	private static final String TAG = PageAdapter.class.getSimpleName();
	private Document mDocument;
	private List<Page> mPageList;
	private LayoutInflater mInflater;
	private Handler mHandler;
	private PageChangeListener mPageChangeListener;
	private LoadStateChangeListener mLoadStateChangeListener;
	
	public PageAdapter(Context ctx, PageChangeListener l) {
		this.mPageChangeListener = l;
		mPageList = new ArrayList<Page>();
		mInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mHandler = new Handler();
	}
	
	public PageAdapter(Context ctx) {
		this (ctx, null);
	}
	
	public void setLoadStateChangeListener(LoadStateChangeListener l) {
		this.mLoadStateChangeListener = l;
	}
	
	public void setDocument(Document doc) {
		this.mDocument = doc;
		mPageList.clear();
		init();
	}
	
	public Document getDocument() {
		return mDocument;
	}
	
	public void loadDefaultDocument(Context ctx) {
		DocumentManager docManager = DocumentManager.getInstance(ctx);
		Document doc = docManager.getDefaultDocument();
		if (doc != null) {
			setDocument(doc);
		}
	}
	
	
	private void init() {
		mHandler.post(new GetPageTask(0, 1));
//		mHandler.postDelayed(new GetPageTask(5, Integer.MAX_VALUE), 3000);
	}
	
	private void loadPages(int pageOffset, int pageCapacity) {
		for (int pageIndex = pageOffset; pageIndex < pageCapacity; pageIndex ++) {
			Page page = mDocument.getPage(pageIndex);
			if (page.getAvaiableSize() < 0) {
				break;
			}
			mPageList.add(page);
		}
	}
	
	
	@Override
	public int getCount() {
		return mPageList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public Page getPage(int position) {
		return mPageList.get(position);
	}
	


	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		PageView pv = null;
		if (null == convertView) {
			pv = (PageView) mInflater.inflate(R.layout.page, parent, false);
			pv.init();
		} else {
			pv = (PageView) convertView;
		}
		int scrollDy = pv.getPageScrollDy();
		
		if (mPageList != null && mPageList.size() > 0) {
			final Page page = mPageList.get(position);
			pv.setTextSize(mDocument.getTextSize());
			pv.setPage(page);
		}
		return pv;
	}

	
	private class GetPageTask extends AsyncTask<Void, Integer, Void> implements Runnable {
		private int offset;
		private int capacity;
		
		public GetPageTask(int pageOffset, int pageCapacity) {
			offset = pageOffset;
			capacity = pageCapacity;
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			loadPages(offset, capacity);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			notifyDataSetChanged();
			if (mLoadStateChangeListener != null) {
				mLoadStateChangeListener.onDocumentLoadCompleted(mDocument);
			}
		}

		@Override
		public void run() {
			if (ApiUtil.hasHoneycomb()) {
				this.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
			} else {
				this.execute();
			}
		}
		
	}
	
	@Override
	public void onClick(View v) {
		if (null == mPageChangeListener) {
			return;
		}
		switch (v.getId()) {
		case R.id.btn_prev:
			mPageChangeListener.onPreviousPage();
			break;
		case R.id.btn_next:
			mPageChangeListener.onNextPage();
			break;
		}
	}

	public interface PageChangeListener {
		public void onPreviousPage();
		public void onNextPage();
	}
	
	public interface LoadStateChangeListener {
		public void onDocumentLoadCompleted(Document doc);
	}
}
