package com.funnyplayer;

import java.io.File;

import com.funnyplayer.cache.lrc.LrcUtils;
import com.funnyplayer.ui.adapter.LrcAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class LrcActivity extends Activity implements OnItemClickListener {
	private ListView mLrcListView;
	private LrcAdapter mLrcAdapter;
	private TextView mLrcTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.lrc);
		mLrcListView = (ListView) findViewById(R.id.lrcListView);
		mLrcTextView = (TextView) findViewById(R.id.lrcText);
		mLrcAdapter = new LrcAdapter(getApplicationContext(), LrcUtils.getLrcDir(this).getPath());
		mLrcListView.setAdapter(mLrcAdapter);
		mLrcListView.setOnItemClickListener(this);
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		File f = mLrcAdapter.getLrcFile(position);
		String lrc = LrcUtils.getLrcFromFile(f);
		mLrcTextView.setText(lrc);
	}
	
	
	
}
