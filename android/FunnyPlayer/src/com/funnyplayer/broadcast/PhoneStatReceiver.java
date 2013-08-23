package com.funnyplayer.broadcast;

import com.funnyplayer.util.MusicUtil;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

public class PhoneStatReceiver extends BroadcastReceiver {

	private static final String TAG = PhoneStatReceiver.class.getSimpleName();
	private MusicState mLastState = MusicState.PAUSED;

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
			if (MusicUtil.isPlaying()) {
				mLastState = MusicState.PLAYING;
				MusicUtil.pause(context);
			}
		} else {
			TelephonyManager tm = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
			switch (tm.getCallState()) {
			case TelephonyManager.CALL_STATE_RINGING:
				if (MusicUtil.isPlaying()) {
					mLastState = MusicState.PLAYING;
					MusicUtil.pause(context);
				}
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:
				break;
			case TelephonyManager.CALL_STATE_IDLE:
				if (mLastState == MusicState.PLAYING) {
					MusicUtil.play(context);
				}
				break;
			}
		}
	}
	
	
	private static enum MusicState {
		PAUSED, PLAYING
	}
}