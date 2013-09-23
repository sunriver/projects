package com.funnyplayer;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.renderscript.Type;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.funnyplayer.ui.adapter.PagerAdapter;
import com.funnyplayer.ui.adapter.ScrollTabAdapter;
import com.funnyplayer.ui.fragment.AlbumFragment;
import com.funnyplayer.ui.fragment.ArtistFragment;
import com.funnyplayer.ui.fragment.IFragment;
import com.funnyplayer.ui.fragment.PlaylistFragment;
import com.funnyplayer.ui.widgets.ScrollTabView;
import com.funnyplayer.util.MusicUtil;
import com.funnyplayer.util.ViewUtil;
import common.Consts;

public class HomeActivity extends Activity implements OnClickListener {
	private ViewPager mViewPager;
	private ScrollTabView mTabView;
	private TextView mCustomTitleView;
	private BroadcastReceiver mReceiver;
	private AlbumFragment mAlbumFrament;
	private ArtistFragment mArtistFrament;
	private PlaylistFragment mPlaylistFrament;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		mViewPager = (ViewPager) findViewById(R.id.viewPager);
		mTabView = (ScrollTabView) findViewById(R.id.scrollTabs);
		registerReceiver();
		init();
	}
	
	private void updateCustomeTitle(Bundle bundle) {
		if (null == bundle) {
			return;
		}
		String artist = bundle.getString("music_artist");
		String name = bundle.getString("music_name");
		String playItemPath = bundle.getString("music_item_path");
		mCustomTitleView.setText(artist + "  " + name);
		mCustomTitleView.setTag(playItemPath);
	}
	
	private void updatePlayItemState(Bundle bundle) {
		if (null == bundle) {
			return;
		}
		String playItemPath = bundle.getString("music_item_path");
		scrollToItem(playItemPath);
	}
	
	private void registerReceiver() {
		mReceiver = new BroadcastReceiver(){
			@Override
			public void onReceive(Context context, Intent intent) {
				if (intent.getAction().equals(MusicUtil.FilterAction.PLAYER_PAUSED)) {
				} else if (intent.getAction().equals(MusicUtil.FilterAction.PLAYER_PLAYING))  {
					Bundle bundle = intent.getExtras();
					updateCustomeTitle(bundle);
					updatePlayItemState(bundle);
				}
			}
		};
		
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(MusicUtil.FilterAction.PLAYER_PLAYING);
		intentFilter.addAction(MusicUtil.FilterAction.PLAYER_PAUSED);
		registerReceiver(mReceiver, intentFilter);
	}
	
	private void unregisterReceiver() {
		if (mReceiver != null) {
			unregisterReceiver(mReceiver);
			mReceiver = null;
		}
	}

	
	private void init() {
		this.setTitle("");
		// Initiate PagerAdapter
		PagerAdapter pagerAdapter = new PagerAdapter(getFragmentManager());
		mAlbumFrament = new AlbumFragment();
		mArtistFrament = new ArtistFragment();
		mPlaylistFrament = new PlaylistFragment();
		// add fragment
		pagerAdapter.addFragment(mAlbumFrament);
		pagerAdapter.addFragment(mArtistFrament);
		pagerAdapter.addFragment(mPlaylistFrament);

		mViewPager.setAdapter(pagerAdapter);
		mViewPager.setOffscreenPageLimit(pagerAdapter.getCount());
		mViewPager.setOnPageChangeListener(mTabView);

		ScrollTabAdapter tabAdapter = new ScrollTabAdapter(this);
		mTabView.setViewPager(mViewPager);
		mTabView.setAdapter(tabAdapter);
		
		initActionBar();
	}
	
    /**
     * Set the ActionBar title
     */
    private void initActionBar() {
    	ActionBar actionBar = getActionBar();
    	
		ViewUtil.setActionBarBackgroundRepeat(this, actionBar);
		actionBar.setCustomView(R.layout.custom_title);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_TITLE);
		mCustomTitleView = (TextView) actionBar.getCustomView();
		mCustomTitleView.setOnClickListener(this);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	private void showLrc() {
		startActivity(new Intent(this, LrcActivity.class));
	}


	@Override
	protected void onDestroy() {
		unregisterReceiver();
		super.onDestroy();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
//		case R.id.menu_search:
//			onSearchRequested();
//			break;
		case R.id.menu_lrc:
			showLrc();
			break;
		default:
			super.onOptionsItemSelected(item);
		}
		return true;
	}

	@Override
	public void onClick(View v) {
		String itemPath = (String) v.getTag();
		scrollToItem(itemPath);
	}
	
	private void scrollToItem(String playItemPath) {
		if (TextUtils.isEmpty(playItemPath)) {
			return;
		}
		String[] subs = playItemPath.split(":");
		int fragmentIndex = Integer.valueOf(subs[0]);
		int gridIndex = Integer.valueOf(subs[1]);
		int itemIndex = MusicUtil.getItemPos();
		scrollToItem(fragmentIndex, gridIndex, itemIndex);
	}
	
	private void removeSelection() {
		mAlbumFrament.selectItem(0, -1);
		mArtistFrament.selectItem(0, -1);
		mPlaylistFrament.selectItem(0, -1);
	}
	
	private void scrollToItem(int fragmentIndex, int gridIndex, int itemIndex) {
		removeSelection();
		mViewPager.setCurrentItem(fragmentIndex, true);
		PagerAdapter pageAdapter = (PagerAdapter) mViewPager.getAdapter();
		IFragment fragment = (IFragment) pageAdapter.getItem(fragmentIndex);
		fragment.selectItem(gridIndex, itemIndex);
	}

}
