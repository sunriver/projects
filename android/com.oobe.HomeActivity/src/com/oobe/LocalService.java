package com.oobe;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.text.SpannableString;
import android.util.Log;
import android.widget.Toast;

public class LocalService extends Service {
	private final static String TAG="LocalService";
	
	private NotificationManager mNotificationManager;
	
	private final int NOTIFICATION_ID = 0;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)
	 * @see android.app.Service#onCreate()
	 */
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		mNotificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		Toast.makeText(this, "Local Service is started!", Toast.LENGTH_SHORT);
		super.onCreate();
	}


	/* (non-Javadoc)
	 * @see android.app.Service#onDestroy()
	 */
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		mNotificationManager.cancel(NOTIFICATION_ID);
		Toast.makeText(this, "Local Service is stopped!", Toast.LENGTH_SHORT);
		super.onDestroy();
	}


	/* (non-Javadoc)
	 * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onStartCommand+");
		Log.i(TAG, "params: flags="+flags+", startId="+startId);
		showNotification();
		return START_STICKY;
		//return super.onStartCommand(intent, flags, startId);
	}

	private void showNotification(){
		Notification notification=new Notification();
		Context appContext=getApplicationContext();
		CharSequence title="notification title";
		CharSequence text="notification text";
		SpannableString ss = new SpannableString(text);
		ss.setSpan(Color.BLUE, 0, text.length(), 0);
		Intent intent=new Intent(Intent.ACTION_CALL);
		PendingIntent contentIntent=PendingIntent.getActivity(appContext, 0, intent, 0);
		notification.setLatestEventInfo(getApplicationContext(), title, text, contentIntent);
		
		mNotificationManager.notify(NOTIFICATION_ID, notification);
	}

	

}
