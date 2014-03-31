package com.douban.lite;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.douban.lite.common.BitmapCache;
import com.douban.lite.event.EventFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;

public class MainActivity extends ActionBarActivity {
	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
		getSupportActionBar().hide();
		setContentView(R.layout.activity_main);
	}
	
	private void init() {
		Context ctx = getApplicationContext();
		mRequestQueue = Volley.newRequestQueue(ctx);
		mImageLoader = new ImageLoader(mRequestQueue, new BitmapCache(ctx));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
    public void onAttachFragment(Fragment fragment) {
		switch (fragment.getId()) {
		case R.id.fragment_event:
			((EventFragment) fragment).init(mRequestQueue, mImageLoader);
			break;
		}
    }

}
