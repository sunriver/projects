package com.funnyplayer.broadcast;

import com.funnyplayer.util.MusicUtil;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

public class PhoneStatReceiver extends BroadcastReceiver {

	private static final String TAG = "PhoneStatReceiver";

	private static boolean incomingFlag = false;

	private static String incoming_number = null;

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
			incomingFlag = false;
			String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
			Log.i(TAG, "call OUT:" + phoneNumber);
			MusicUtil.pause(context);
		} else {
			TelephonyManager tm = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
			switch (tm.getCallState()) {
			case TelephonyManager.CALL_STATE_RINGING:
				MusicUtil.pause(context);
				incomingFlag = true;
				incoming_number = intent.getStringExtra("incoming_number");
				Log.i(TAG, "RINGING :" + incoming_number);
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:
				if (incomingFlag) {
					Log.i(TAG, "incoming ACCEPT :" + incoming_number);
				}
				break;

			case TelephonyManager.CALL_STATE_IDLE:
				MusicUtil.play(context);
				if (incomingFlag) {
					Log.i(TAG, "incoming IDLE");
				}
				break;
			}
		}
	}
}