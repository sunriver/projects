package com.funnyplayer.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.funnyplayer.service.MusicService;
import com.funnyplayer.service.MusicService.MusicBinder;

public class MusicUtil {
	private static MusicService mService;
	
	private static class ServiceConnection implements android.content.ServiceConnection {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			if (service != null) {
				MusicBinder binder = (MusicBinder) service;
				mService = binder.getService();
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			mService = null;
		}
		
	}
	
	public static void bindService(Context context) {
		Intent intent = new Intent();
		intent.setClass(context, MusicService.class);
		context.bindService(intent, new ServiceConnection(), Context.BIND_AUTO_CREATE);
	}
	
	public static MusicService getService(Context context) {
		if (null == mService) {
			bindService(context);
		}
		return mService;
	}
}
