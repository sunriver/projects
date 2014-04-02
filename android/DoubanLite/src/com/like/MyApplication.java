package com.like;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import android.app.Application;
import android.util.Log;

public class MyApplication extends Application {
	private final static String TAG = MyApplication.class.getSimpleName();
	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;
	
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
	
	
	public void clear() {
		if (mRequestQueue != null) {
			mRequestQueue.stop();
		}
		mRequestQueue = null;
		mImageLoader = null;
	}


	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate()+");
	}
	
}
