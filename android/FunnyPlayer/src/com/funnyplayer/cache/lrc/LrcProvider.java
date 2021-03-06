package com.funnyplayer.cache.lrc;


import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.funnyplayer.net.api.geci.bean.LrcBean;
import com.funnyplayer.util.ApiUtil;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

public class LrcProvider {
	private static final String TAG = "LrcProvider";
    private static LrcProvider mInstance;
    private Context mContext;
    private AsyncTask mCurrentSearchTask;
    
	private LrcProvider(Context context) {
		this.mContext = context;
	}
	
	/**
	 * Note that this function will be called only in main thread.
	 * @param context
	 * @return
	 */
	public static LrcProvider getInstance(Context context) {
		if (null == mInstance) {
			mInstance = new LrcProvider(context);
		}
		return mInstance;
	}
	

	public void searchLrcFromWeb(final String artist, final String song, LrcSearchCompletedListener l) {
		SearchTask task = new SearchTaskFromWeb(new LrcInfo(artist, song), l);
		if (mCurrentSearchTask != null) {
			mCurrentSearchTask.cancel(true);
		}
		mCurrentSearchTask = task;
		if (ApiUtil.hasHoneycomb()) {
			task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		} else {
			task.execute();
		}
	}
	public void searchLrcFromDisk(final String artist, final String song, LrcSearchCompletedListener l) {
		SearchTask task = new SearchTask(new LrcInfo(artist, song), l);
		if (mCurrentSearchTask != null) {
			mCurrentSearchTask.cancel(true);
		}
		mCurrentSearchTask = task;
		if (ApiUtil.hasHoneycomb()) {
			task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		} else {
			task.execute();
		}
	}
	
	public String getLrcFromFile(String filePath) {
		return LrcUtils.getLrcFromFile(new File(filePath));
	}
	
	
	public String getLrcFromInputStream(InputStream in) {
		return LrcUtils.getLrcFromInpuStream(in);
	}
	
	
	public void downloadLrc(String artist, String song, String url, LrcDownloadCompletedListener l) {
		DownloadTask task = new DownloadTask(new LrcInfo(song, artist, url), l);
		if (ApiUtil.hasHoneycomb()) {
			task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		} else {
			task.execute();
		}
	}
	
	
	public interface LrcSearchCompletedListener {
		public void onSearchFinished(String artist, String song, String url);
	}
	
	
	public interface LrcDownloadCompletedListener {
		public void onDownloadFinished(String artist, String song, File file);
	}
	
	private class DownloadTask extends AsyncTask<Void, Void, File> {
		private LrcInfo mLrcInfo;
		private LrcDownloadCompletedListener mListener;
		
		public DownloadTask(LrcInfo info, LrcDownloadCompletedListener l) {
			this.mLrcInfo = info;
			this.mListener = l;
		}

		@Override
		protected File doInBackground(Void... params) {
			File result = LrcUtils.downloadLrcFromWeb(mContext, mLrcInfo);
			return result;
		}
		

		@Override
		protected void onPostExecute(File result) {
			if (null == result) {
				return;
			}
			mListener.onDownloadFinished(mLrcInfo.getArtist(), mLrcInfo.getSong(), result);
		}
	}
	
	
	
   /*
    * Search lrc from disk at default.
    */
	private class SearchTask extends AsyncTask<Void, Void, LrcBean> {
		protected LrcInfo mLrcInfo;
		protected LrcSearchCompletedListener mListener;
		protected Set<String> mLrcSet;
		
		public SearchTask(LrcInfo info, LrcSearchCompletedListener l) {
			this.mLrcInfo = info;
			this.mListener = l;
			this.mLrcSet = new HashSet<String>();
		}

		@Override
		protected LrcBean doInBackground(Void... params) {
			LrcBean result = LrcUtils.searchLrcFromDisk(mContext, mLrcInfo);
			return result;
		}
		

		@Override
		protected void onPostExecute(LrcBean result) {
			mCurrentSearchTask = null;
			if (null == result) {
				return;
			}
			List<LrcBean.LrcUrl> lrcUrls = result.getResult();
			if (null == lrcUrls) {
				return;
			}
			for (LrcBean.LrcUrl lrcUrl : result.getResult()) {
				String key = lrcUrl.toString(); 
				if (!mLrcSet.contains(key)) {
					mLrcSet.add(key);
					mListener.onSearchFinished(lrcUrl.getArtist(), lrcUrl.getSong(), lrcUrl.getLrc());
				}
			}
			mLrcSet.clear();
		}
		
	}
	
	   /*
	    * Search lrc from web 
	    */
	private class SearchTaskFromWeb extends SearchTask {

		private SearchTaskFromWeb(LrcInfo info, LrcSearchCompletedListener l) {
			super(info, l);
		}
		
		@Override
		protected LrcBean doInBackground(Void... params) {
			LrcBean result = LrcUtils.searchLrcFromWeb(mContext, mLrcInfo);
			return result;
		}
		
	}
}
