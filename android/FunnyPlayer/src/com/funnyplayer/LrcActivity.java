package com.funnyplayer;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.funnyplayer.cache.lrc.LrcProvider;
import com.funnyplayer.cache.lrc.LrcProvider.LrcSearchCompletedListener;
import com.funnyplayer.cache.lrc.LrcUtils;
import com.funnyplayer.ui.adapter.LrcAdapter;
import com.funnyplayer.util.ViewUtil;

public class LrcActivity extends Activity implements OnItemClickListener,
		View.OnClickListener, LrcSearchCompletedListener {
	private final static String TAG = LrcActivity.class.getSimpleName();
	
	private final static String LRC_SETTING = "lrc_setting";
	private final static String LRC_SEARCH_TYPE = "lrc_search_type";

	private ListView mLrcListView;
	private LrcAdapter mLrcAdapter;
	private EditText mLrcSongNameEt;
	private ImageButton mLrcSearchBtn;
	private LrcProvider mLrcProvider;
	private SharedPreferences mPreferences;
	private MenuItem mLocalItem;
	private MenuItem mInternetItem;
	private SearchType mSearchType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.lrc);
		mPreferences = getSharedPreferences(LRC_SETTING, Context.MODE_PRIVATE);
		String type = mPreferences.getString(LRC_SEARCH_TYPE, SearchType.LOCAL.name());
		mSearchType = SearchType.valueOf(type);
		initActionBar();
		init();
		super.onCreate(savedInstanceState);
	}

	private void init() {
		mLrcSongNameEt = (EditText) findViewById(R.id.lrcSongName);

		mLrcSearchBtn = (ImageButton) findViewById(R.id.lrcSearch);
		mLrcSearchBtn.setOnClickListener(this);

		mLrcListView = (ListView) findViewById(R.id.lrcListView);
		mLrcAdapter = new LrcAdapter(getApplicationContext(), LrcUtils
				.getLrcDir(this).getPath());
		mLrcListView.setAdapter(mLrcAdapter);
		mLrcListView.setOnItemClickListener(this);

		mLrcProvider = LrcProvider.getInstance(getApplicationContext());
	}

	private void initActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(R.string.lrc_title);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP
				| ActionBar.DISPLAY_SHOW_TITLE, ActionBar.DISPLAY_HOME_AS_UP
				| ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_SHOW_HOME);
		ViewUtil.setActionBarBackgroundRepeat(this, actionBar);
	}

	@Override
	protected void onStart() {
		searchLrc();
		super.onStart();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// File f = mLrcAdapter.getLrcFile(position);
		// String lrc = LrcUtils.getLrcFromFile(f);
		// mLrcTextView.setText(lrc);
	}

	@Override
	public void onClick(View v) {
		mLrcAdapter.removeAllItems();
		searchLrc();
	}
	
	private void searchLrc() {
		String searchText = mLrcSongNameEt.getText().toString();
		switch (mSearchType) {
		case LOCAL:
			mLrcProvider.searchLrcFromDisk(null, searchText, this);
			break;
		case INTERNET:
			mLrcProvider.searchLrcFromWeb(null, searchText, this);
			break;
		}
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
		case R.id.menu_local_search:
			updateSearchType(SearchType.LOCAL);
			break;
		case R.id.menu_internet_search:
			updateSearchType(SearchType.INTERNET);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.lrc, menu);
		mLocalItem= menu.findItem(R.id.menu_local_search);
		mInternetItem = menu.findItem(R.id.menu_internet_search);
		updateSearchType(mSearchType);
		return true;
	}
	
	private void updateSearchType(SearchType type) {
		mSearchType = type;
		mPreferences.edit()
		.putString(LRC_SEARCH_TYPE, type.name())
		.commit();
		switch (type) {
		case LOCAL:
			mLocalItem.setChecked(true);
			mInternetItem.setChecked(false);
			break;
		case INTERNET:
			mLocalItem.setChecked(false);
			mInternetItem.setChecked(true);
			break;
		}
	}

	private static enum SearchType {
		LOCAL, INTERNET
	}

}
