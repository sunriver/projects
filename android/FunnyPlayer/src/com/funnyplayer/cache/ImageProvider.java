package com.funnyplayer.cache;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.funnyplayer.BuildConfig;
import com.funnyplayer.R;
import com.funnyplayer.util.ApiUtil;
import com.funnyplayer.util.Consts;
import com.funnyplayer.util.NetUtils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.ImageView;

public class ImageProvider {
	private static final String TAG = "ImageProvider";
	private static ImageProvider mInstance;
	private Context mContext;
	private ImageCache mCache;
	
	//Container of images failing to get
    private Set<String> unavailable = new HashSet<String>();
    
    private Map<String, Set<ImageView>> pendingImagesMap = new HashMap<String, Set<ImageView>>();
	
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
	@TargetApi(11)
	public void loadImage(ImageInfo imageInfo, ImageView v, ImageReadyListener callback) {
		//Get bitmap from cache if exists.
		Log.v(TAG, "loadImage imageInfo.toString():" + imageInfo.toString());
		final String tag = imageInfo.toString();
		
		Bitmap bm = mCache.get(tag);
		if (bm != null) {
			setImageDrawable(v, bm);
			return;
		}
		
		v.setBackgroundResource(R.drawable.no_art_small);
		
		if (unavailable.contains(tag)) {
			return;
		}
		
		Set<ImageView> imageViewSet = pendingImagesMap.get(tag);
		if (imageViewSet != null) {
			imageViewSet.add(v);
			return;
		} 
		
		imageViewSet = new HashSet<ImageView>();
		imageViewSet.add(v);
		pendingImagesMap.put(tag, imageViewSet);
		
		//Create a thread to get bitmap from media store.
		Task task = new Task(imageInfo, v, callback);
		if (ApiUtil.hasHoneycomb() ) {
			task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		} else {
			task.execute();
		}
	}
	
	private void setImageDrawable(ImageView v, Bitmap bitmap) {
		v.setBackgroundDrawable(new BitmapDrawable(bitmap));
	}
	
	
	public interface ImageReadyListener {
		public void onLoadFinished(ImageView v, Bitmap bitmap);
	}
	
	
	private class Task extends AsyncTask<Void, Void, Bitmap> {
		private ImageInfo mImageInfo;
		private ImageView mImageView;
		private ImageReadyListener mCallback;
		private Context mContext;
		
		public Task(ImageInfo info, ImageView v, ImageReadyListener callback) {
			this.mImageInfo = info;
			this.mImageView = v;
			this.mCallback = callback;
			this.mContext = v.getContext().getApplicationContext();
		}

		@Override
		protected Bitmap doInBackground(Void... params) {
			File f = ImageUtils.getImageFromMediaStore(mContext, mImageInfo);
			
			if (null == f) {
				f = ImageUtils.getImageFromDisk(mContext, mImageInfo);
			}
			if (null == f && NetUtils.isNetworkAvaiable(mContext)) {
				f = ImageUtils.getImageFromWeb(mContext, mImageInfo);
			}
			
			if (f != null) {
				return BitmapFactory.decodeFile(f.getAbsolutePath());
			}
			return null;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			final String tag = mImageInfo.toString();
			if (result != null) {
				Log.v(TAG, "onPostExecute imageInfo.toString():" + mImageInfo.toString());
				
				mCache.put(tag, result);
				setImageDrawable(mImageView, result);
				mCallback.onLoadFinished(mImageView, result);
			} else {
				unavailable.add(tag);
			}
	        Set<ImageView> pendingImages = pendingImagesMap.get(tag);
	        if (pendingImages != null) {
	            pendingImagesMap.remove(tag);
	        }
		}
		
	}
	
}
