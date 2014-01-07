package com.funnyplayer;

import com.funnyplayer.util.UncatchExceptionHandler;

import android.app.Application;
import android.util.Log;

public class MainApplication extends Application {
	private static final String TAG = MainApplication.class.getSimpleName();

	@Override
	public void onCreate() {
		Log.d(TAG, "onCreate()+");
		super.onCreate();
		Thread.setDefaultUncaughtExceptionHandler(new UncatchExceptionHandler(getApplicationContext(), Thread.getDefaultUncaughtExceptionHandler()));
	}
}
