package com.oobe;

import com.oobe.common.DataBaseHelper.OOBEContent;
import com.oobe.common.DataBaseHelper;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.PopupMenu;
import android.widget.Toast;

public class MainActivity extends Activity  {
	final private int menuMode = Menu.FIRST;
	final private int menuExit = Menu.FIRST + 1;
	final private int menuMedia = Menu.FIRST + 2;
	
	
	final private static int NOTIFICATION_ID = 1;
	
	private final static String TAG = "MainActivity";
	
	
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Log.v("ReceiveActivity", "Receive action = " + intent.getAction());
		}
		
	};
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		this.unregisterReceiver(mReceiver);
	}



	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		this.registerReceiver(mReceiver, mIntentFilter);
	}

	private IntentFilter mIntentFilter;
	
	
	class MainAsyncTask extends AsyncTask<Integer, Void, Long> {
		@Override
		protected Long doInBackground(Integer... types) {
//			doInsertDB();
//			
//			doQueryDB();
			
			doMusicStart();
			
			NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
			Notification notification = new Notification(R.drawable.icon, "notification", System.currentTimeMillis());
//			Intent notificationIntent = new Intent(Intent.ACTION_CALL);
			Intent notificationIntent = new Intent(android.content.Intent.ACTION_SEND);
			notificationIntent.setType("plain/text");
			PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0);
			notification.setLatestEventInfo(getApplicationContext(), "contentTitle", "contentText",contentIntent);
			notificationManager.notify(NOTIFICATION_ID, notification);
			//			notification.
			return 0L;
		}
		
		private void doMusicStart() {
			MediaPlayer.create(getApplicationContext(), R.raw.test).start();
		}
		
		private void  doInsertDB() {
			ContentValues values = new ContentValues();
			values.put(OOBEContent.KEY_NAME, "me");
			values.put(OOBEContent.KEY_COUNT, "5");
			values.put(OOBEContent.KEY_TIME, "111111");
			
			Uri uri = getIntent().getData();
			Log.v(TAG, uri.getScheme());
			Log.v(TAG, uri.getAuthority());
			getContentResolver().insert(uri, values);
		}
		
		private void doQueryDB() {
			Uri uri = getIntent().getData();
			String[] projection = {"name", "count"};
			String selection = "name=?" ;
			String[] selectionArgs = {"me"};
			String sortOrder = null;
			Cursor cursor = managedQuery(uri, projection, selection, selectionArgs, sortOrder);
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
				int columnIndex = cursor.getColumnIndex(DataBaseHelper.OOBEContent.KEY_NAME);
				Log.v(TAG, "name = " + cursor.getString(columnIndex));
			}
		}

		@Override
		protected void onPostExecute(Long result) {
			// TODO Auto-generated method stub
			SpannableString ss = new SpannableString("create task to insert data into DB");
			ss.setSpan(new ForegroundColorSpan(Color.GREEN) , 0, ss.length(), 0);
			Toast toast = Toast.makeText(MainActivity.this, ss, 1000);
			toast.show();
			super.onPostExecute(result);
		}
		
	};
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
        menu.add(Menu.NONE, R.id.a_item, Menu.NONE, "Menu A");
        menu.add(Menu.NONE, R.id.b_item, Menu.NONE, "Menu B");
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		mIntentFilter = new IntentFilter();
		mIntentFilter.addAction("com.oobe.my.action");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		this.registerForContextMenu(findViewById(R.id.add_entry_button));
		Toast.makeText(this, "ReceiveActivity onCreate", 5000).show();
        
    }
    
    

    @Override
	protected void onStart() {
    	super.onStart();
    	testBoradCast();
//    	  ActionBar actionBar = this.getActionBar(); 
//    	  actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP, ActionBar.DISPLAY_HOME_AS_UP); 
    }



	@Override
    public boolean onCreateOptionsMenu(Menu menu){
//    	menu.add(R.string.menu_item1);
//    	menu.add(R.string.menu_item2);
//    	return super.onCreateOptionsMenu(menu);
    	menu.add(0, menuMode, Menu.NONE, "Map Mode");
    	menu.add(0, menuExit, Menu.NONE, "Back");
    	menu.add(0, menuMedia, Menu.NONE, "Media");
    	
        getMenuInflater().inflate(R.menu.start_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case menuMode:
				new MainAsyncTask().execute(1);
				break;
			case menuExit:
				new MainAsyncTask().execute(1);
				break;
			case menuMedia:
				this.registerReceiver(mReceiver, mIntentFilter);
//				testBoradCast();
				break;
			case android.R.id.home:
				Intent intent = new Intent(this, HomeActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void testBoradCast() {
		Intent intent = new Intent("com.oobe.my.action");
		intent.setFlags(1);
		this.sendStickyBroadcast(intent);
//		this.sendBroadcast(intent);
	}
	
	public  void onClickToShowPopuMenu(View v) {
	    PopupMenu popup = new PopupMenu(this, v);
	    MenuInflater inflater = popup.getMenuInflater();
	    inflater.inflate(R.menu.action_option, popup.getMenu());
	    popup.show();
	}


}