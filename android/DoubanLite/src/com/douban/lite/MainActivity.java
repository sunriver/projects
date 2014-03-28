package com.douban.lite;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.douban.lite.event.fragment.EventFragment;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;

public class MainActivity extends ActionBarActivity {
	private RequestQueue mRequestQueue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		init();
	}
	
	private void init() {
		mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		EventFragment eventFg = (EventFragment) this.getSupportFragmentManager().findFragmentById(R.id.fragment_event);
		eventFg.bindRequetQueue(mRequestQueue);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
