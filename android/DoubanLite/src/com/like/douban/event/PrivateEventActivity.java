package com.like.douban.event;

import java.util.List;

import com.like.R;
import com.like.douban.event.bean.Event;
import com.sunriver.common.utils.ViewUtil;
import com.viewpagerindicator.IconPageIndicator;
import com.viewpagerindicator.PageIndicator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;

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
		
		PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
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
//		inflater.inflate(R.menu.event_private, menu);
//		 MenuItem item = menu.findItem(R.id.menu_private);
//		 item.setActionView(R.layout.actionview_private_participanted);
//		 MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
//		 participantedMenuItem.setActionView(view);
//		 participantedMenuItem.set
//		 participantedMenuItem.getActionView().setBackgroundResource(R.drawable.dark_half_transparent);
		 
		 
		// MenuItemCompat.setShowAsAction(shareItem,
		// MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
		return true;
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
//		case R.id.menu_private_participanted:
//			selectParticipantItem();
//			break;
//		case R.id.menu_private_wished:
//			selectWishItem();
//			break;
			
		}
		return super.onOptionsItemSelected(item);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (EventDetailActivity.REQUEST_CODE == requestCode) {
			EventManager manager = EventManager.getInstance();
			List<Event> participantEvents = manager.getParticipantedEvents();
//			mParticipantEventAdapter.updateEventList(participantEvents.toArray(new Event[participantEvents.size()]));
//			
//			List<Event> wishedEvents = manager.getWisheredEvents();
//			mWishEventAdapter.updateEventList(wishedEvents.toArray(new Event[wishedEvents.size()]));
		}
	}


}
