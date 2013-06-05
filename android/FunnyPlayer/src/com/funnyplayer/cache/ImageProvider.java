package com.funnyplayer.cache;

import java.io.File;

import com.funnyplayer.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class ImageProvider {
	private static final String TAG = "ImageProvider";
	private static ImageProvider mInstance;
	private Context mContext;
	private ImageCache mCache;
	
	private ImageProvider(Context context) {
		mContext = context.getApplicationContext();
		mCache = new ImageCache(mContext);
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
		Log.v(TAG, "loadImage imageInfo.toString():" + imageInfo.toString());
		Bitmap bm = mCache.get(imageInfo.toString());
		if (bm != null) {
			setImageDrawable(v, bm);
			return;
		}
		//Create a thread to get bitmap from media store.
		v.setBackgroundResource(R.drawable.no_art_small);
		Task task = new Task(imageInfo, v, callback);
		task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	}
	
	private void setImageDrawable(ImageView v, Bitmap bitmap) {
		v.setBackgroundDrawable(new BitmapDrawable(bitmap));
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
//			File f = ImageUtils.getImageFromMediaStore(mContext, mImageInfo);
//			if (f != null) {
//				return BitmapFactory.decodeFile(f.getAbsolutePath());
//			}
			File f = ImageUtils.getImageFromWeb(mContext, mImageInfo);
			return null;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			if (result != null) {
				Log.v(TAG, "onPostExecute imageInfo.toString():" + mImageInfo.toString());
				mCache.put(mImageInfo.toString(), result);
				setImageDrawable(mImageView, result);
				mCallback.onLoadFinished(mImageView, result);
			}
		}
		
	}
	
}
