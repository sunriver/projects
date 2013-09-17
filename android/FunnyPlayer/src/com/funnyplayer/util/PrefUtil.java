package com.funnyplayer.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class PrefUtil {
	private static class PlayInfo {
		public static final String FRAGMENT_TYPE = "fragment_type";
	}
	private static final String NAME = "listen.pref";
	
	
	private static SharedPreferences getSharePreference(Context context) {
		return context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
	}
}
