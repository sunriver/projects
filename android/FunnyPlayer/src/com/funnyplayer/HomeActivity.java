package com.funnyplayer;

import com.funnyplayer.ui.adapter.PagerAdapter;
import com.funnyplayer.ui.adapter.ScrollTabAdapter;
import com.funnyplayer.ui.fragment.AlbumFragment;
import com.funnyplayer.ui.widgets.ScrollTabView;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

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
		initTabs();
		initViewPager();
	}
	
	private void initViewPager() {
        // Initiate PagerAdapter
        PagerAdapter pagerAdapter = new PagerAdapter(getFragmentManager());
        
        //add fragment
        pagerAdapter.addFragment(new AlbumFragment());
        pagerAdapter.addFragment(new AlbumFragment());
        
        mViewPager.setAdapter(pagerAdapter);
		mViewPager.setOnPageChangeListener(mTabView);
	}
	
	private void initTabs() {
        ScrollTabAdapter tabAdapter = new ScrollTabAdapter(this);
        mTabView.setAdapter(tabAdapter);
	}

}
