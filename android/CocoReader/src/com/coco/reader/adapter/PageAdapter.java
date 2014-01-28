package com.coco.reader.adapter;

import java.util.ArrayList;
import java.util.List;

import com.coco.reader.R;
import com.coco.reader.view.PageView;
import com.codo.reader.data.Document;
import com.codo.reader.data.Page;
import com.sunriver.common.utils.ApiUtil;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class PageAdapter extends BaseAdapter {
	private static final String TAG = PageAdapter.class.getSimpleName();
	private Document mDocument;
	private List<Page> mPageList;
	private LayoutInflater mInflater;
	private final int mPageCapacity;
	
	public PageAdapter(Context ctx, int pageCapacity) {
		mDocument = new Document(ctx);
		mPageCapacity = pageCapacity;
		mPageList = new ArrayList<Page>();
		mInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		init();
	}
	
	public PageAdapter(Context ctx) {
		this(ctx, 5);
	}
	
	private void init() {
		Handler handler = new Handler();
		handler.post(new Runnable() {
			@Override
			public void run() {
				GetPageTask task = new GetPageTask(0, 5);
				if (ApiUtil.hasHoneycomb()) {
					task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
				} else {
					task.execute();
				}
				
			}});
		
	}
	
	private void loadPages(int pageOffset, int pageCapacity) {
		for (int pageIndex = pageOffset; pageIndex < pageCapacity; pageIndex ++) {
			Page page = mDocument.getPage(pageIndex);
			mPageList.add(page);
		}
	}
	
	@Override
	public int getCount() {
		return mPageCapacity;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		PageView pv = (PageView) convertView;
		if (null == pv) {
			 pv = (PageView) mInflater.inflate(R.layout.page, parent, false);
		}
		if (mPageList != null && mPageList.size() > 0) {
			Page page = mPageList.get(position);
			pv.setText(page.getContent());
		}
		return pv;
	}

	
	private class GetPageTask extends AsyncTask<Void, Integer, Void> {
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
		}
		
		
	}
}
