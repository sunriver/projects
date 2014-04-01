package com.douban.lite;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.douban.lite.common.BitmapCache;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;
	
	public ImageLoader getImageLoader() {
		return mImageLoader;
	}
	
	public RequestQueue getRequestQueue() {
		return mRequestQueue;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Context appCtx = this.getApplicationContext();
		mRequestQueue = Volley.newRequestQueue(appCtx);
		mImageLoader = new ImageLoader(mRequestQueue, new BitmapCache(appCtx));
	}
	
}
