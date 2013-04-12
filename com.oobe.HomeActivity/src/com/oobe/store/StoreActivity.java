package com.oobe.store;

import com.oobe.R;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class StoreActivity extends Activity {
	
	private IStoreService mStoreService;
	
	private EditText mEditText;
	
	private Button mButton ;
	
	private ServiceConnection mServiceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mStoreService = (IStoreService) service;
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			mStoreService = null;			
		}
		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Call service to save text into file");
//		mEditText = new EditText(this, null);
//		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//		this.setContentView(mEditText, lp);
//		mButton = new Button(this);
//		mButton.setText("send");
//		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//		this.setContentView(mButton, lp);
		this.setContentView(R.layout.store_main);
		mButton = (Button) findViewById(R.id.store_save);
		mEditText = (EditText) findViewById(R.id.store_edit_text);
		
		init();
		this.bindService(new Intent(this, StoreService.class), mServiceConnection, Service.BIND_AUTO_CREATE);
	}
	
	private void init() {
		mButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				String content = mEditText.getText().toString();
				mStoreService.write(content);
				Toast.makeText(StoreActivity.this, "Test has been saved!", 2000).show();
			}});
	}

}
