package com.oobe;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;

public class HelloBroadReceiver extends BroadcastReceiver {
	private final String TAG="Receiver";
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.v(TAG,"Action="+intent.getAction());
//		new AlertDialog.Builder(context).setTitle("hello broad cast").show();
		//play music
		
	}

}
