package com.funnyplayer;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.renderscript.Type;
import android.support.v4.view.ViewPager;
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
	
	private void registerReceiver() {
		mReceiver = new BroadcastReceiver(){
			@Override
			public void onReceive(Context context, Intent intent) {
				if (intent.getAction().equals(MusicUtil.FilterAction.PLAYER_PAUSED)) {
				} else if (intent.getAction().equals(MusicUtil.FilterAction.PLAYER_PLAYING))  {
					Bundle bundle = intent.getExtras();
					updateCustomeTitle(bundle);
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
		// Initiate PagerAdapter
		PagerAdapter pagerAdapter = new PagerAdapter(getFragmentManager());

		// add fragment
		pagerAdapter.addFragment(new AlbumFragment());
		pagerAdapter.addFragment(new ArtistFragment());
		pagerAdapter.addFragment(new PlaylistFragment());

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
		String[] subs = itemPath.split(":");
		int fragmentIndex = Integer.valueOf(subs[0]);
		mViewPager.setCurrentItem(fragmentIndex, true);
		PagerAdapter pageAdapter = (PagerAdapter) mViewPager.getAdapter();
//		IFragment fragment = (IFragment) pageAdapter.getItem(fragmentIndex);
//		int gridIndex = Integer.valueOf(subs[1]);
//		fragment.selectChild(gridIndex);
	}

}
