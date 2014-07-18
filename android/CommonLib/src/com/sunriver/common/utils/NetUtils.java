package com.sunriver.common.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

public class NetUtils {
	private final static String TAG = NetUtils.class.getSimpleName();

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
	
	
	/**
	 * Returns current available IP Address. This will be either WLAN or local host.
	 */
	public static InetAddress getAvailableAddress(final Context context) {
		InetAddress inetAdress = null;
		
		try {
			// create IP address
			inetAdress = InetAddress.getByAddress(new byte[] {127, 0, 0, 1});
			WifiManager wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
			if (wifiInfo != null) {
				// we have a WiFi connection, use this IP that we can send it to external devices
				final int numAddress = wifiInfo.getIpAddress();
				final byte byte1 = (byte)((numAddress >> 0x00) & 0xFF);
				final byte byte2 = (byte)((numAddress >> 0x08) & 0xFF);
				final byte byte3 = (byte)((numAddress >> 0x10) & 0xFF);
				final byte byte4 = (byte)((numAddress >> 0x18) & 0xFF);
				if ((byte1 != 0) || (byte2 != 0) || (byte3 != 0) || (byte4 != 0)) {
					inetAdress = InetAddress.getByAddress(new byte[] {byte1,byte2,byte3,byte4});
				}
			}
		} catch (UnknownHostException e) {
			Log.e(TAG, "Invalid internet address", e);
		}
		
		return inetAdress;
	}
	


}


