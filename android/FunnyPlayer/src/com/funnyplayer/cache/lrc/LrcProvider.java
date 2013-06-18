package com.funnyplayer.cache.lrc;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class LrcProvider {
	private static final String TAG = "LrcProvider";
	
    private Set<String> unavailable = new HashSet<String>();
    private Set<String> pendingLrc = new HashSet<String>();
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
	
	public void loadLrc(LrcInfo lrcInfo, LrcReadyListener l) {
		final String tag = lrcInfo.toString();		
		if (unavailable.contains(tag)) {
			return;
		}
		if (pendingLrc.contains(tag)) {
			return;
		}
		
		//Create a thread to get bitmap from media store.
		Task task = new Task(lrcInfo, l);
		task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	}
	
	public interface LrcReadyListener {
		public void onReady(final String lrc);
	}
	
	private class Task extends AsyncTask<Void, Void, String> {
		private LrcInfo mLrcInfo;
		private LrcReadyListener mListener;
		private Context mContext;
		
		public Task(LrcInfo info, LrcReadyListener l) {
			this.mLrcInfo = info;
			this.mListener = l;
		}

		@Override
		protected String doInBackground(Void... params) {
			File f = LrcUtils.getLrcFromWeb(mContext, mLrcInfo);
			if (f != null) {
				return LrcUtils.getLrcFromFile(f);
			}

			return null;
		}
		

		@Override
		protected void onPostExecute(String result) {
			final String tag = mLrcInfo.toString();
			if (result != null) {
				Log.v(TAG, "onPostExecute imageInfo.toString():" + mLrcInfo.toString());
				mListener.onReady(result);
			} else {
				unavailable.add(tag);
			}
			if (pendingLrc.contains(tag)) {
				pendingLrc.remove(tag);
			}
		}
		
	}
}
