package com.oobe;

import com.oobe.MainActivity.MainAsyncTask;
import com.oobe.common.HelloWorldNative;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ShareActionProvider;

public class StartActivity extends Activity {
	
	static {
		System.loadLibrary("hello-jni");
	}
	
	private Button nextBtn;
    
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		if(savedInstanceState!=null){
			if(savedInstanceState.containsKey("name")){
				Log.v("StartActivity", "name====="+savedInstanceState.getString("name"));
			}
		}
		
		this.setContentView(R.layout.nav);
		nextBtn=(Button)this.findViewById(R.id.next);
		this.addListeners();
		
		showContextItems();
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState){
		outState.putString("name", "start on ");
		super.onSaveInstanceState(outState);
	}
	
	private void addListeners(){
		nextBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				//start local service
				Log.v("start", "start local service");
				Context appContext=getApplicationContext();
				startService(new Intent(StartActivity.this, LocalService.class));
				// enter next step
				Log.v("start", "next-------------------");
				HelloWorldNative hw=new HelloWorldNative();
				hw.say("good night");
				Intent intent=new Intent(StartActivity.this, MidActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onContextItemSelected(item);
	}

	@Override
	public void onContextMenuClosed(Menu menu) {
		// TODO Auto-generated method stub
		super.onContextMenuClosed(menu);
	}


	private ShareActionProvider mShareActionProvider;
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu){
//    	menu.add(R.string.menu_item1);
//    	menu.add(R.string.menu_item2);
//    	return super.onCreateOptionsMenu(menu);
    	
        getMenuInflater().inflate(R.menu.start_activity_menu, menu);
        
//        mShareActionProvider = (ShareActionProvider) menu.findItem(R.id.men1).getActionProvider();
//        mShareActionProvider.setShareIntent(getDefaultShareIntent());
        return super.onCreateOptionsMenu(menu);
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}
	
	private void showContextItems() {
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2,new String[]{"a"});

	}
}
