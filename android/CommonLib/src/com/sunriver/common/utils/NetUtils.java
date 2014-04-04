package com.sunriver.common.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetUtils {

	public static boolean isNetworkAvaiable(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (null == manager) {
			return false;
		}
		NetworkInfo networkinfo = manager.getActiveNetworkInfo();
		if (null == networkinfo|| !networkinfo.isAvailable()) {
			return false;
		}
		return true;
	}
}
