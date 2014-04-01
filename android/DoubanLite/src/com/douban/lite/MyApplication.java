package com.douban.lite;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.douban.lite.common.BitmapCache;

import android.app.Application;
import android.content.Context;
import android.util.Log;

public class MyApplication extends Application {
	private final static String TAG = MyApplication.class.getSimpleName();
	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;
	private BitmapCache mBitmapCache;
	
	public ImageLoader getImageLoader() {
		return mImageLoader;
	}
	
	public void setImageLoader(ImageLoader loader) {
		this.mImageLoader = loader;
	}
	
	public RequestQueue getRequestQueue() {
		return mRequestQueue;
	}
	
	public void setRequestQueue(RequestQueue queue) {
		this.mRequestQueue = queue;
	}
	
	public BitmapCache getBitmapCache() {
		return mBitmapCache;
	}
	
	public void setBitmapCache(BitmapCache cache) {
		this.mBitmapCache = cache;
	}
	
	public void clear() {
		if (mBitmapCache != null) {
			mBitmapCache.clear();
		}
		if (mRequestQueue != null) {
			mRequestQueue.stop();
		}
		mBitmapCache = null;
		mRequestQueue = null;
		mImageLoader = null;
	}


	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate()+");
	}
	
}
