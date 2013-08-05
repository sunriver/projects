package com.funnyplayer;

import com.funnyplayer.cache.lrc.LrcProvider;
import com.funnyplayer.cache.lrc.LrcProvider.LrcSearchCompletedListener;
import com.funnyplayer.cache.lrc.LrcUtils;
import com.funnyplayer.ui.adapter.LrcAdapter;
import com.funnyplayer.util.ViewUtil;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class LrcActivity extends Activity implements OnItemClickListener, View.OnClickListener, LrcSearchCompletedListener {
	private ListView mLrcListView;
	private LrcAdapter mLrcAdapter;
	private TextView mLrcTextView;
	private EditText mLrcSongNameEt;
	private EditText mLrcSongArtistEt;
	private Button mLrcSearchBtn;
	private LrcProvider mLrcProvider;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.lrc);
		initActionBar();
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
		
		mLrcProvider = LrcProvider.getInstance(getApplicationContext());
	}

    private void initActionBar() {
    	ActionBar actionBar = getActionBar();
    	actionBar.setTitle(R.string.lrc_search);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE,
                ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE
                        | ActionBar.DISPLAY_SHOW_HOME);
        ViewUtil.setActionBarBackgroundRepeat(this, actionBar);
    }
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
//		File f = mLrcAdapter.getLrcFile(position);
//		String lrc = LrcUtils.getLrcFromFile(f);
//		mLrcTextView.setText(lrc);
	}

	@Override
	public void onClick(View v) {
		String searchArtist = mLrcSongArtistEt.getText().toString();
		String searchSong = mLrcSongNameEt.getText().toString();
		mLrcAdapter.removeAllItems();
		mLrcProvider.searchLrc(searchSong, searchArtist, this);
	}

	@Override
	public void onSearchFinished(String artist, String song, String url) {
		mLrcAdapter.add(artist, song, url);
		mLrcAdapter.notifyDataSetChanged();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			super.onBackPressed();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
