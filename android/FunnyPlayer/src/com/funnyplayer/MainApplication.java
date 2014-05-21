package com.funnyplayer;


import com.sunriver.common.exception.UncatchExceptionHandler;
import com.sunriver.common.exception.UncatchExceptionHandler.OnUncatchExeptionListener;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

public class MainApplication extends Application {
	private static final String TAG = MainApplication.class.getSimpleName();


	private void registerUncatchExceptionHandler() {
		final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + getPackageName();
		final String fileName = "crash.log";
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
		Log.d(TAG, "onCreate()-");
	}
}
