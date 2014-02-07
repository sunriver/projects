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
import android.widget.Button;

public class PageAdapter extends BaseAdapter {
	private static final String TAG = PageAdapter.class.getSimpleName();
	private Document mDocument;
	private List<Page> mPageList;
	private LayoutInflater mInflater;
	Handler mHandler;
	
	public PageAdapter(Context ctx) {
		mPageList = new ArrayList<Page>();
		mInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mHandler = new Handler();
	}
	
	public void setDocument(Document doc) {
		this.mDocument = doc;
		mPageList.clear();
		init();
	}
	
	
	private void init() {
		mHandler.post(new GetPageTask(0, 5));
		mHandler.postDelayed(new GetPageTask(5, Integer.MAX_VALUE), 3000);
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
	
	private static class ViewHolder {
		private Button prevBtn;
		private Button nextBtn;
		private PageView pageView;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh = null;
		if (null == convertView) {
			vh = new ViewHolder();
			convertView = mInflater.inflate(R.layout.page, parent, false);
			vh.pageView = (PageView) convertView.findViewById(R.id.tv_content);
			vh.prevBtn = (Button) convertView.findViewById(R.id.btn_prev);
			vh.nextBtn = (Button) convertView.findViewById(R.id.btn_next);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		
		if (mPageList != null && mPageList.size() > 0) {
			Page page = mPageList.get(position);
			vh.pageView.setText(page.getContent());
		}
		return convertView;
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


}
