package com.like;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.sunriver.common.exception.UncatchExceptionHandler;
import com.sunriver.common.exception.UncatchExceptionHandler.OnUncatchExeptionListener;

import android.app.Application;
import android.os.Environment;
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

	private void registerUncatchExceptionHandler() {
		final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + getPackageName();
		final String fileName = "uncatch_exception.log";
		OnUncatchExeptionListener listener = new OnUncatchExeptionListener() {
			@Override
			public void onUncacth(Throwable ex) {
				//kill process
				Log.e("Application", "", ex);
				int pid = android.os.Process.myPid();
				android.os.Process.killProcess(pid);
			}
		};
		
		UncatchExceptionHandler handler = new UncatchExceptionHandler(path, fileName, listener);
		Thread.currentThread().setUncaughtExceptionHandler(handler);
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		registerUncatchExceptionHandler();
		Log.d(TAG, "onCreate()+");
	}
	
}
