package com.funnyplayer;

import java.io.File;

import com.funnyplayer.cache.lrc.LrcUtils;
import com.funnyplayer.ui.adapter.LrcAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class LrcActivity extends Activity implements OnItemClickListener, View.OnClickListener {
	private ListView mLrcListView;
	private LrcAdapter mLrcAdapter;
	private TextView mLrcTextView;
	private EditText mLrcSongNameEt;
	private EditText mLrcSongArtistEt;
	private Button mLrcSearchBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.lrc);
		init();
		super.onCreate(savedInstanceState);
	}
	
	private void init() {
		mLrcSongNameEt = (EditText) findViewById(R.id.lrcSongName);
		mLrcSongArtistEt = (EditText) findViewById(R.id.lrcSongArtist);
		
		mLrcSearchBtn = (Button) findViewById(R.id.lrcSearch);
		mLrcSearchBtn.setOnClickListener(this);
		
		mLrcListView = (ListView) findViewById(R.id.lrcListView);
		mLrcAdapter = new LrcAdapter(getApplicationContext(), LrcUtils.getLrcDir(this).getPath());
		mLrcListView.setAdapter(mLrcAdapter);
		mLrcListView.setOnItemClickListener(this);
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

	@Override
	public void onClick(View v) {
		String songName = mLrcSongNameEt.getText().toString();
		if (TextUtils.isEmpty(songName)) {
			
		}
			
			String songArtist = mLrcSongNameEt.getText().toString();
	}
	
	
	
}
