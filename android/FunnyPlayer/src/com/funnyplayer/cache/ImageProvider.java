package com.funnyplayer.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

public class ImageProvider {
	private static ImageProvider mInstance;
	private Context mContext;
	
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
	}
	
	
	public interface LoadCallback {
		public void onLoadFinished(ImageView v, Bitmap bitmap);
	}
	
}
