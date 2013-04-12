package com.oobe;

import com.oobe.ui.CustomAlertDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MidActivity extends Activity  implements DialogInterface.OnClickListener {
	private Button backBtn;
	private Button nextBtn;
	private Button alertBtn;
	
	private static Uri mUri = Uri.parse("content://com.oobe.provider/people");
    
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		if(savedInstanceState!=null){
			if(savedInstanceState.containsKey("name")){
				Log.v("MidActivity", "name====="+savedInstanceState.getString("name"));
			}
		}
		this.setContentView(R.layout.nav_2);
		backBtn=(Button)this.findViewById(R.id.back_2);
		nextBtn=(Button)this.findViewById(R.id.next_2);
		alertBtn=(Button)this.findViewById(R.id.alert);
		this.addListeners();
	}
	
	@Override
	protected void onSaveInstanceState (Bundle outState){
		outState.putString("name", "hello world");
		super.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onStart(){
		super.onStart();
	}
	
	@Override
	protected void onResume(){
		super.onResume();
	}	
	
	@Override
	protected void onPause(){
		super.onPause();
	}
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onStop(){
		super.onStop();
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onDestroy(){
		super.onDestroy();
	}
	
	
	private void addListeners(){
		nextBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MidActivity.this,MainActivity.class);
//				intent.setData(mUri);
//				Intent intent=new Intent(MidActivity.this,TouchActivity.class);
//				Intent intent=new Intent(MidActivity.this,FragmentActivity.class);
				startActivity(intent);
			}
		});
		
		backBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Context appContext=getApplicationContext();
//				Toast toast=Toast.makeText(appContext, "back activity", Toast.LENGTH_LONG);
//				toast.setGravity(Gravity.TOP|Gravity.LEFT, 0, 0);
//				toast.show();
				
				NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
				Notification notification=new Notification();
				Intent notifyIntent=new Intent(Intent.ACTION_GET_CONTENT);
				PendingIntent contentIntent=PendingIntent.getActivity(appContext, 0, notifyIntent, 0);
				notification.setLatestEventInfo(appContext, "title", "text", contentIntent);
				notificationManager.notify(1, notification);
				
				Intent intent=new Intent(MidActivity.this,StartActivity.class);
				//Intent intent=new Intent(android.content.Intent.ACTION_MAIN);
				intent.setPackage(getPackageName());
				intent.addCategory(android.content.Intent.CATEGORY_LAUNCHER);
				startActivity(intent);
			}
		});
		
		alertBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog alertDialog=new CustomAlertDialog(MidActivity.this);
//				LayoutInflater layoutInflater=LayoutInflater.from(MidActivity.this);
//				View alertUI=layoutInflater.inflate(R.layout.alert_ui, null);	
//				alertDialog.show();
//				alertDialog.setContentView(alertUI);
				alertDialog.show();
			}
			
		});
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		Log.v("ALERT", "show alert dialog");
	}

}
