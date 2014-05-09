package com.like.douban.event;


import com.like.R;
import com.sunriver.common.utils.ViewUtil;
import com.viewpagerindicator.IconPageIndicator;
import com.viewpagerindicator.PageIndicator;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.PopupWindow;

public class PrivateEventActivity extends ActionBarActivity {
	private final static String TAG = PrivateEventActivity.class.getSimpleName();
	private ViewPager mEventViewPager;
	private  PageIndicator mEventIndicator;;
	private ParticipantedEventFragment mParticipantedFragment;
	private WishedEventFragment mWishedFragment;


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.activity_event_private);
		initActionBar();
		initViews();
	}


	private void initViews() {
		mEventViewPager = (ViewPager) this.findViewById(R.id.vp_private);
		
		PageAdapter pagerAdapter = new PageAdapter(getSupportFragmentManager());
		mParticipantedFragment = new ParticipantedEventFragment();
		mWishedFragment = new WishedEventFragment();
		pagerAdapter.addFragment(mParticipantedFragment);
		pagerAdapter.addFragment(mWishedFragment);
		mEventViewPager.setAdapter(pagerAdapter);
		
		mEventIndicator = (IconPageIndicator) findViewById(R.id.ic_indicator);
		mEventIndicator.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPageSelected(int index) {
				ActionBar actionBar = getSupportActionBar();
				switch (index) {
				case 0:
					actionBar.setTitle(R.string.event_private_participanted);
					break;
				case 1:
					actionBar.setTitle(R.string.event_private_wished);
					break;
				}
				
			}
			
		});
		mEventIndicator.setViewPager(mEventViewPager);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
//		MenuInflater inflater = getMenuInflater();
		 MenuInflater inflater = new MenuInflater(getApplicationContext());
		inflater.inflate(R.menu.event_private, menu);
//		ViewUtil.setMenuBackground(getLayoutInflater(), R.drawable.dark_half_transparent);
		MenuItem settingItem = menu.findItem(R.id.menu_item_setting);
		MenuItemCompat.setShowAsAction(settingItem, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
//		MenuItem logoutItem = settingItem.getSubMenu().findItem(R.id.submenu_item_logout);
//		MenuItemCompat.setShowAsAction(logoutItem, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
//		View v = MenuItemCompat.getActionView(logoutItem);
//		View v = logoutItem.getActionView();
//		v.setBackgroundResource(R.drawable.dark_half_transparent);
//		MenuItemCompat.setActionView(logoutItem, R.layout.actionview_private_participanted);
//		settingItem.getSubMenu().add(R.string.douban_logout);
//		final MenuItem logoutItem = menu.findItem(R.id.submenu_item_logout);
//		new Handler().post(new Runnable() {
//
//			@Override
//			public void run() {
//				logoutItem.setActionView(R.layout.actionview_private_participanted);
//			}
//			
//		});
//		MenuItemCompat.setShowAsAction(logoutItem, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
		return super.onCreateOptionsMenu(menu);
		
		
	}
	



	private void initActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle(R.string.event_private_participanted);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP
				| ActionBar.DISPLAY_SHOW_TITLE, ActionBar.DISPLAY_HOME_AS_UP
				| ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_SHOW_HOME);
		ViewUtil.setActionBarBackgroundRepeat(this, actionBar,
				R.drawable.bg_base);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d(TAG, "onOptionsItemSelected()+");
		switch (item.getItemId()) {
		case android.R.id.home:
			super.onBackPressed();
			break;
		case R.id.menu_item_setting:
			showSettings();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void showSettings() {
//        View popupView = getLayoutInflater().inflate(R.layout.layout_popupwindow, null);
//
//        PopupWindow mPopupWindow = new PopupWindow(popupView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
//        mPopupWindow.setTouchable(true);
//        mPopupWindow.setOutsideTouchable(true);
//        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
	}

}
