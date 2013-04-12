package com.oobe;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class ReceiveActivity extends Activity {

	private BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Log.v("ReceiveActivity", "Receive action = " + intent.getAction());
		}
		
	};
	
	private IntentFilter mIntentFilter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mIntentFilter = new IntentFilter();
		mIntentFilter.addAction("com.oobe.my.action");
		Toast.makeText(this, "ReceiveActivity onCreate", 5000).show();
	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(mReceiver);
	}

	@Override
	protected void onResume() {
		super.onResume();  
		Toast.makeText(this, "ReceiveActivity start", 2000).show();
		registerReceiver(mReceiver, mIntentFilter);
	}

}
