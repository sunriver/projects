package com.like.douban.event;

import java.util.List;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.like.R;
import com.like.MyApplication;
import com.like.douban.api.ResponseListener;
import com.like.douban.event.api.GetParticipantedEvents;
import com.like.douban.event.api.GetWisheredEvents;
import com.like.douban.event.bean.Event;
import com.like.douban.event.bean.EventList;
import com.like.douban.account.AccountManager;
import com.sunriver.common.utils.ViewUtil;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class PrivateEventActivity extends ActionBarActivity {
	private final static String TAG = PrivateEventActivity.class.getSimpleName();
	private ViewPager mEventViewPager;
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
		// add fragment
		pagerAdapter.addFragment(mParticipantedFragment);
		pagerAdapter.addFragment(mWishedFragment);

		mEventViewPager.setAdapter(pagerAdapter);
		mEventViewPager.setOffscreenPageLimit(pagerAdapter.getCount());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.event_private, menu);
		 MenuItem item = menu.findItem(R.id.menu_private);
		 item.setActionView(R.layout.actionview_private_participanted);
		 MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
//		 participantedMenuItem.setActionView(view);
//		 participantedMenuItem.set
//		 participantedMenuItem.getActionView().setBackgroundResource(R.drawable.dark_half_transparent);
		 
		 
		// MenuItemCompat.setShowAsAction(shareItem,
		// MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
		return true;
	}

	private void initActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle(R.string.event_private);
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

	private void showEventDetail(Event evt) {
		Intent intent = new Intent(this, EventDetailActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable(EventDetailActivity.STATE_EVENT, evt);
		intent.putExtras(bundle);
		startActivityForResult(intent, EventDetailActivity.REQUEST_CODE);
	}

}
