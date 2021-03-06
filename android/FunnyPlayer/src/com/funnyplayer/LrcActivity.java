package com.funnyplayer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.adwo.adsdk.AdListener;
import com.adwo.adsdk.AdwoAdView;
import com.adwo.adsdk.ErrorCode;
import com.funnyplayer.cache.lrc.LrcProvider;
import com.funnyplayer.cache.lrc.LrcProvider.LrcSearchCompletedListener;
import com.funnyplayer.ui.adapter.LrcAdapter;
import com.funnyplayer.util.ViewUtil;

public class LrcActivity extends ActionBarActivity implements OnItemClickListener,
		View.OnClickListener, LrcSearchCompletedListener, AdListener {
	private final static String TAG = LrcActivity.class.getSimpleName();
	private final static String LRC_SETTING = "lrc_setting";
	private final static String LRC_SEARCH_TYPE = "lrc_search_type";
	private static final boolean AD_TEST_MODE = false;

	private ListView mLrcListView;
	private LrcAdapter mLrcAdapter;
	private EditText mLrcSongNameEt;
	private ImageButton mLrcSearchBtn;
	private LrcProvider mLrcProvider;
	private SharedPreferences mPreferences;
	private MenuItem mLocalItem;
	private MenuItem mInternetItem;
	private MenuItem mSearchTypeItem;
	private SearchType mSearchType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lrc);
		mPreferences = getSharedPreferences(LRC_SETTING, Context.MODE_PRIVATE);
		String type = mPreferences.getString(LRC_SEARCH_TYPE, SearchType.LOCAL.name());
		mSearchType = SearchType.valueOf(type);
		initActionBar();
		init();
		
	}

	private void init() {
		mLrcSongNameEt = (EditText) findViewById(R.id.lrcSongName);

		mLrcSearchBtn = (ImageButton) findViewById(R.id.lrcSearch);
		mLrcSearchBtn.setOnClickListener(this);

		mLrcListView = (ListView) findViewById(R.id.lrcListView);
		mLrcAdapter = new LrcAdapter(getApplicationContext());
		mLrcListView.setAdapter(mLrcAdapter);

		mLrcProvider = LrcProvider.getInstance(getApplicationContext());
		
	}
	

 
	private void initActionBar() {
		ActionBar actionBar = this.getSupportActionBar();
//		actionBar.setCustomView(resId);
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
	}

	@Override
	public void onClick(View v) {
		searchLrc();
	}
	
	private void searchLrc() {
		mLrcAdapter.removeAllItems();
		String searchText = mLrcSongNameEt.getText().toString();
		switch (mSearchType) {
		case LOCAL:
			mLrcProvider.searchLrcFromDisk(null, searchText, this);
			break;
		case INTERNET:
			if (!TextUtils.isEmpty(searchText)) {
				mLrcProvider.searchLrcFromWeb(null, searchText, this);
			}
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
		mSearchTypeItem = menu.findItem(R.id.menu_search_type);
		Menu sub = mSearchTypeItem.getSubMenu();
		mLocalItem= sub.findItem(R.id.menu_local_search);
		mInternetItem = sub.findItem(R.id.menu_internet_search);
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
			mSearchTypeItem.setTitle(mLocalItem.getTitle());
			mLocalItem.setChecked(true);
			mInternetItem.setChecked(false);
			break;
		case INTERNET:
			mSearchTypeItem.setTitle(mInternetItem.getTitle());
			mLocalItem.setChecked(false);
			mInternetItem.setChecked(true);
			break;
		}
		searchLrc();
	}

	private static enum SearchType {
		LOCAL, INTERNET
	}

	@Override
	public void onFailedToReceiveAd(AdwoAdView arg0, ErrorCode arg1) {
		Log.e(TAG, "onFailedToReceiveAd() errorCode:" + arg1.getErrorCode());
	}


	@Override
	public void onReceiveAd(AdwoAdView arg0) {
		Log.v(TAG, "onReceiveAd()+");
	}
	
}
