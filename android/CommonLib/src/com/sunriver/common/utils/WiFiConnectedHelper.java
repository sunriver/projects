package com.sunriver.common.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Vector;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class WiFiConnectedHelper {
	private static final String LOG_TAG = WiFiConnectedHelper.class
			.getSimpleName();
	private Vector<WiFiConnectedReceiver> mReceiver = new Vector<WiFiConnectedReceiver>();
	private Context mContext;
	private WifiManager mWifiManager;
	private ConnectivityManager mConnectivityManager;
	private WiFiState mWiFiState = new WiFiState();
	private Handler mHandler;
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
				Bundle extras = intent.getExtras();
				int state = extras.getInt(WifiManager.EXTRA_WIFI_STATE);

				Log.v(LOG_TAG, "WIFI_STATE_CHANGED_ACTION - state: " + state);

				boolean enabled = false;
				switch (state) {
				case WifiManager.WIFI_STATE_DISABLED:
				case WifiManager.WIFI_STATE_DISABLING:
					enabled = false;
					break;
				case WifiManager.WIFI_STATE_ENABLED:
					enabled = true;
					break;
				case WifiManager.WIFI_STATE_ENABLING:
				case WifiManager.WIFI_STATE_UNKNOWN:
					enabled = false;
				default:
					enabled = false;
				}

				mWiFiState.mIsEnabled = enabled;
			} else if (action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
				WiFiState state = getWiFiState();
				mWiFiState.update(state);
			}
			synchronized (this) {
				if (mHandler != null) {
					final WiFiState s = new WiFiState(mWiFiState);
					mHandler.post(new Runnable() {
						@Override
						public void run() {
							dispatchState(s);
						}

					});
				} else {
					dispatchState(mWiFiState);
				}
			}
		}
	};

	private void dispatchState(WiFiState s) {
		for (WiFiConnectedReceiver rcv : mReceiver) {
			rcv.onWiFiChanged(s);
		}
	}

	private WiFiConnectedHelper(Context context, Handler handler) {
		mContext = context.getApplicationContext();
		mWifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
		mConnectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		mHandler = handler;
		mWiFiState = getWiFiState();

		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
		intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
		mContext.registerReceiver(mBroadcastReceiver, intentFilter);
	}

	public void registerReceiver(WiFiConnectedReceiver rcv) {
		mReceiver.add(0, rcv);
	}

	public void unregisterReceiver(WiFiConnectedReceiver rcv) {
		mReceiver.remove(rcv);
	}

	public static WiFiConnectedHelper start(Context context, Handler handler,
			WiFiConnectedReceiver rcv) {
		WiFiConnectedHelper helper = new WiFiConnectedHelper(context, handler);
		helper.registerReceiver(rcv);
		return helper;
	}

	public synchronized void stop() {
		mContext.unregisterReceiver(mBroadcastReceiver);
		mReceiver.clear();
	}

	public WiFiState getWiFiState() {
		WiFiState state = new WiFiState();
		WifiManager wifiManager = mWifiManager;
		boolean isConnected = false;
		InetAddress address = null;
		String bssid = null;
		String ssid = null;
		boolean enabled = wifiManager.isWifiEnabled();
		if (enabled) {
			NetworkInfo networkInfo = mConnectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (networkInfo != null && networkInfo.isConnected()) {
				isConnected = true;
				DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
				int ipAddr = dhcpInfo.ipAddress;
				address = getInetAddress(ipAddr, mWifiManager);
				WifiInfo wifiInfo = wifiManager.getConnectionInfo();
				bssid = wifiInfo.getBSSID();
				ssid = wifiInfo.getSSID();
				if (address != null) {
					Log.i(LOG_TAG, "WiFi address: " + address.toString());
				} else {
					Log.w(LOG_TAG, "WiFi is connected but has no address.");
				}
			} else {
				Log.v(LOG_TAG, "WiFi is not connected");
				isConnected = false;
			}
		}
		state.mIsEnabled = enabled;
		state.mIsConnected = isConnected;
		state.mAddress = address;
		state.mBSSID = bssid;
		state.mSSID = ssid;
		mWiFiState.update(state);
		return state;
	}
	
	private static InetAddress getInetAddress(int address,
			WifiManager wifiManager) {
		InetAddress inetAddress = null;
		WifiInfo wifiConnection = wifiManager.getConnectionInfo();
		address = wifiConnection.getIpAddress();
		if (address != 0) {
			byte[] byteaddr = new byte[] { (byte) (address & 0xff),
					(byte) (address >> 8 & 0xff),
					(byte) (address >> 16 & 0xff),
					(byte) (address >> 24 & 0xff) };
			try {
				inetAddress = InetAddress.getByAddress(byteaddr);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
		return inetAddress;
	}

	public static final class WiFiState {
		public WiFiState() {
		}

		public WiFiState(WiFiState s) {
			update(s);
		}

		public InetAddress mAddress;
		public boolean mIsConnected;
		public boolean mIsEnabled;
		public String mBSSID;
		public String mSSID;

		public synchronized void update(WiFiState s) {
			mAddress = s.mAddress;
			mIsConnected = s.mIsConnected;
			mIsEnabled = s.mIsEnabled;
			mBSSID = s.mBSSID;
			mSSID = s.mSSID;
		}

		@Override
		public String toString() {
			StringBuffer buf = new StringBuffer("WiFi State:");
			buf.append(" address=" + mAddress)
					.append(" mIsConnected=" + mIsConnected)
					.append(" mIsEnabled=" + mIsEnabled)
					.append(" mBSSID=" + mBSSID).append(" mSSID=" + mSSID);
			return buf.toString();
		}

	}
	
	private static boolean isWifiConnected(Context ctx) {
		// we could get device IP by system property "dhcp.tiwlan0.ipaddress"
		ConnectivityManager cm = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		return wifi.isConnected();
	}

	public interface WiFiConnectedReceiver {
		 void onWiFiChanged(WiFiState wifiState);
	}

}
