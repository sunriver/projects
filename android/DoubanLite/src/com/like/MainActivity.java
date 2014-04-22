package com.like;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.like.common.BitmapCache;
import com.like.douban.event.EventFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TabHost;

public class MainActivity extends ActionBarActivity {
	private final static String TAG = MainActivity.class.getSimpleName();
	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().hide();
		setContentView(R.layout.activity_main);
		init();
	}
	
	private void init() {
		MyApplication myApp = (MyApplication) getApplication();
		Context appCtx = this.getApplicationContext();
		mRequestQueue = Volley.newRequestQueue(appCtx);
		mImageLoader = new ImageLoader(mRequestQueue, new BitmapCache(appCtx));
		myApp.setImageLoader(mImageLoader);
		myApp.setRequestQueue(mRequestQueue);
		initTabHost();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
//	@Override
//    public void onAttachFragment(Fragment fragment) {
//		switch (fragment.getId()) {
//		case R.id.fragment_event:
//			((EventFragment) fragment).init(mRequestQueue, mImageLoader);
//			break;
//		}
//    }
	

	@Override
	protected void onDestroy() {
		MyApplication myApp = (MyApplication) getApplication();
		myApp.clear();
		super.onDestroy();
	}

	
	private void initTabHost() {

		TabHost tabHost = (TabHost) findViewById(R.id.th_main);
		tabHost.setup();
		
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator(getString(R.string.douban_tongchen))
				.setContent(R.id.fragment_event));
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator(getString(R.string.action_settings))
				.setContent(R.id.fragment_setting));
	}
	

}
