package com.funnyplayer;

import com.funnyplayer.cache.lrc.LrcUtils;
import com.funnyplayer.ui.adapter.LrcAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class LrcActivity extends Activity {
	private ListView mLrcListView;
	private LrcAdapter mLrcAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.lrc);
		mLrcListView = (ListView) findViewById(R.id.lrcListView);
		mLrcAdapter = new LrcAdapter(getApplicationContext(), LrcUtils.getLrcDir(this).getPath());
		mLrcListView.setAdapter(mLrcAdapter);
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	
	
	
}
