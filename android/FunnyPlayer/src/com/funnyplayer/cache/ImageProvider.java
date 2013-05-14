package com.funnyplayer.cache;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class ImageProvider {
	private static ImageProvider mInstance;
	private Context mContext;
	private ImageCache mCache;
	
	private ImageProvider(Context context) {
		mContext = context.getApplicationContext();
		mCache = new ImageCache();
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
		Bitmap bm = mCache.get(ImageUtils.getIndentifier(imageInfo));
		if (bm != null) {
			setImageDrawable(v, bm);
		}
		//Create a thread to get bitmap from media store.
		Task task = new Task(imageInfo, v, callback);
		task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	}
	
	private void setImageDrawable(ImageView v, Bitmap bitmap) {
		v.setImageBitmap(bitmap);
	}
	
	
	public interface LoadCallback {
		public void onLoadFinished(ImageView v, Bitmap bitmap);
	}
	
	
	class Task extends AsyncTask<Void, Void, Bitmap> {
		private ImageInfo mImageInfo;
		private ImageView mImageView;
		private LoadCallback mCallback;
		private Context mContext;
		
		public Task(ImageInfo info, ImageView v, LoadCallback callback) {
			this.mImageInfo = info;
			this.mImageView = v;
			this.mCallback = callback;
			this.mContext = v.getContext().getApplicationContext();
		}

		@Override
		protected Bitmap doInBackground(Void... params) {
			File f = ImageUtils.getImageFromMediaStore(mContext, mImageInfo);
			if (f != null) {
				return BitmapFactory.decodeFile(f.getAbsolutePath());
			}
			return null;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			if (result != null) {
				mCache.put(ImageUtils.getIndentifier(mImageInfo), result);
				setImageDrawable(mImageView, result);
				mCallback.onLoadFinished(mImageView, result);
			}
		}
		
	}
	
}
