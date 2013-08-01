package com.funnyplayer.cache.lrc;


import com.funnyplayer.net.api.geci.bean.LrcBean;
import android.content.Context;
import android.os.AsyncTask;

public class LrcProvider {
	private static final String TAG = "LrcProvider";
    private static LrcProvider mInstance;
    private Context mContext;
    
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
	
	private void loadLrc(LrcInfo lrcInfo, LrcSearchCompletedListener l) {
		//Create a thread to get bitmap from media store.
		SearchTask task = new SearchTask(lrcInfo, l);
		task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	}
	
	public void loadLrc(final String song, final String artist, LrcSearchCompletedListener l) {
		loadLrc(new LrcInfo(song, artist), l);
	}
	
	public interface LrcSearchCompletedListener {
		public void onSearchFinished(String artist, String song);
	}
	
	private class SearchTask extends AsyncTask<Void, Void, LrcBean> {
		private LrcInfo mLrcInfo;
		private LrcSearchCompletedListener mListener;
		
		public SearchTask(LrcInfo info, LrcSearchCompletedListener l) {
			this.mLrcInfo = info;
			this.mListener = l;
		}

		@Override
		protected LrcBean doInBackground(Void... params) {
			LrcBean result = LrcUtils.searchLrcFromWeb(mContext, mLrcInfo);
			return result;
		}
		

		@Override
		protected void onPostExecute(LrcBean result) {
			if (null == result) {
				return;
			}
			for (LrcBean.LrcUrl lrcUrl : result.getResult()) {
				mListener.onSearchFinished(lrcUrl.getArtist(), lrcUrl.getSong());
			}
		}
		
	}
}
