package com.funnyplayer;

import com.funnyplayer.ui.adapter.PagerAdapter;
import com.funnyplayer.ui.adapter.ScrollTabAdapter;
import com.funnyplayer.ui.fragment.AlbumFragment;
import com.funnyplayer.ui.fragment.ArtistFragment;
import com.funnyplayer.ui.fragment.PlaylistFragment;
import com.funnyplayer.ui.widgets.ScrollTabView;
import com.funnyplayer.util.ViewUtil;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class HomeActivity extends Activity {
	private ViewPager mViewPager;
	private ScrollTabView mTabView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		mViewPager = (ViewPager) findViewById(R.id.viewPager);
		mTabView = (ScrollTabView) findViewById(R.id.scrollTabs);
		init();
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
    	
    	actionBar.setDisplayUseLogoEnabled(true);
//    	actionBar.setDisplayShowTitleEnabled(false);
    }

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_search:
			onSearchRequested();
			break;
		default:
			super.onOptionsItemSelected(item);
		}
		return true;
	}

}
