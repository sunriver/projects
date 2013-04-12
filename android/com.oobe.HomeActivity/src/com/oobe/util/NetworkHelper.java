package com.oobe.util;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class NetworkHelper {
	public static int getLocalIpAddress(Context context) {
		WifiManager wifiManager = (WifiManager) context.getSystemService(Activity.WIFI_SERVICE);
		return wifiManager.getConnectionInfo().getIpAddress();
	}
}
