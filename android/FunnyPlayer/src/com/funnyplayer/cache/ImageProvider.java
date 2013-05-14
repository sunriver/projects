package com.funnyplayer.cache;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

public class ImageProvider {
	private static ImageProvider mInstance;
	private Context mContext;
	private LruCache
	
	private ImageProvider(Context context) {
		mContext = context.getApplicationContext();
	}
	
	/**
	 * Note that this function will be called only in main thread.
	 * @param context
	 * @return
	 */
	public static ImageProvider getInstance(Context context) {
		if (null == mInstance) {
			mInstance = new ImageProvider(context);
		}
		return mInstance;
	}
	
	/**
	 * 
	 * @param imageInfo
	 * @param callback
	 */
	public void loadImage(ImageInfo imageInfo, ImageView v, LoadCallback callback) {
		//Get bitmap from cache if exists.
		//Create a thread to get bitmap from media store.
		Task task = new Task(v, callback);
		task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, imageInfo);
	}
	
	
	public interface LoadCallback {
		public void onLoadFinished(ImageView v, Bitmap bitmap);
	}
	
	class Task extends AsyncTask<ImageInfo, Void, Bitmap> {
		private ImageView mImageView;
		private LoadCallback mCallback;
		private Context mContext;
		
		public Task(ImageView v, LoadCallback callback) {
			this.mImageView = v;
			this.mCallback = callback;
			this.mContext = v.getContext().getApplicationContext();
		}

		@Override
		protected Bitmap doInBackground(ImageInfo... params) {
			ImageInfo imageInfo = params[0];
			ImageUtils.getImageFromMediaStore(mContext, imageInfo);
			return null;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			mCallback.onLoadFinished(mImageView, result);
		}
	}
	
}
