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
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class PrivateEventActivity extends ActionBarActivity {
	private final static String TAG = PrivateEventActivity.class.getSimpleName();

	private PullToRefreshListView mPullRefreshListView;
	private ImageLoader mImageLoader;
	private RequestQueue mRequestQueue;
	private GetParticipantedEvents mGetParticipantedEvents;
	private GetWisheredEvents mGetWisheredEvents;
	private EventAdapter mParticipantEventAdapter;
	private EventAdapter mWishEventAdapter;
	private ResponseListener<EventList> mGetParticipantedEventsListener = new ResponseListener<EventList>() {
		@Override
		public void onSuccess(EventList result) {
			mParticipantEventAdapter.updateEventList(result);
			mPullRefreshListView.onRefreshComplete();
			EventManager manager = EventManager.getInstance();
			manager.removeAllParticipantEvents();
			manager.saveParticipantEvents(result);
		}

		@Override
		public void onFailure() {
			mPullRefreshListView.onRefreshComplete();
		}

	};

	private ResponseListener<EventList> mGetWishedEventsListener = new ResponseListener<EventList>() {
		@Override
		public void onSuccess(EventList result) {
			mWishEventAdapter.updateEventList(result);
			mPullRefreshListView.onRefreshComplete();
			EventManager manager = EventManager.getInstance();
			manager.removeAllWishEvents();
			manager.saveWisheredEvents(result);
		}
		
		@Override
		public void onFailure() {
			mPullRefreshListView.onRefreshComplete();
		}
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.activity_event_private);
		initActionBar();
		initViews();
		initData();
	}

	private void initData() {
		MyApplication myApp = (MyApplication) this.getApplication();
		mImageLoader = myApp.getImageLoader();
		mRequestQueue = myApp.getRequestQueue();
		Context ctx = this.getApplicationContext();
		mGetParticipantedEvents = new GetParticipantedEvents(ctx,  mRequestQueue, mGetParticipantedEventsListener);
		mParticipantEventAdapter = new EventAdapter(ctx, mImageLoader);
		mGetWisheredEvents = new GetWisheredEvents(ctx, mRequestQueue, mGetWishedEventsListener);
		mWishEventAdapter = new EventAdapter(ctx, mImageLoader);
		
		final String userID = AccountManager.getLoginUserID(ctx);
		mGetParticipantedEvents.query(userID);
		mGetWisheredEvents.query(userID);
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		 getMenuInflater().inflate(R.menu.event_private, menu);
		 MenuItem item = menu.findItem(R.id.menu_private);
		// MenuItem shareItem = menu.findItem(R.id.action_share);
		//
		 MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
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

	private void initViews() {
		initPullRefreshListView(this.getApplicationContext());
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d(TAG, "onOptionsItemSelected()+");
		switch (item.getItemId()) {
		case android.R.id.home:
			super.onBackPressed();
			break;
		case R.id.menu_private_participanted:
			selectParticipantItem();
			break;
		case R.id.menu_private_wished:
			selectWishItem();
			break;
			
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void selectParticipantItem() {
		ListView actualListView = mPullRefreshListView.getRefreshableView();
		mPullRefreshListView.setTag(mGetParticipantedEvents);
		actualListView.setAdapter(mParticipantEventAdapter);
		mParticipantEventAdapter.notifyDataSetChanged();
	}

	private void selectWishItem() {
		ListView actualListView = mPullRefreshListView.getRefreshableView();
		mPullRefreshListView.setTag(mGetWisheredEvents);
		actualListView.setAdapter(mWishEventAdapter);
		mWishEventAdapter.notifyDataSetChanged();
	}
	
	private void initPullRefreshListView(final Context ctx) {
		mPullRefreshListView = (PullToRefreshListView) this.findViewById(R.id.prlv_event);
		// Set a listener to be invoked when the list should be refreshed.
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						String label = DateUtils.formatDateTime(ctx,
								System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);

						// Update the LastUpdatedLabel
						refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

						// Do work to refresh the list here.
						Object obj = mPullRefreshListView.getTag();
						if (obj instanceof GetParticipantedEvents) {
							mGetParticipantedEvents.query(AccountManager.getLoginUserID(getApplicationContext()));
						} else if (obj instanceof GetWisheredEvents) {
							mGetWisheredEvents.query(AccountManager.getLoginUserID(getApplicationContext()));
						}
					}
				});

		mPullRefreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				EventAdapter adapter = (EventAdapter) parent.getAdapter();
				Event evt = (Event) adapter.getItem(position - 1);
				showEventDetail(evt);
			}

		});
		ListView actualListView = mPullRefreshListView.getRefreshableView();
		registerForContextMenu(actualListView);
		selectParticipantItem();

		
		// Need to use the Actual ListView when registering for Context Menu
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (EventDetailActivity.REQUEST_CODE == requestCode) {
			EventManager manager = EventManager.getInstance();
			List<Event> participantEvents = manager.getParticipantedEvents();
			mParticipantEventAdapter.updateEventList(participantEvents.toArray(new Event[participantEvents.size()]));
			
			List<Event> wishedEvents = manager.getWisheredEvents();
			mWishEventAdapter.updateEventList(wishedEvents.toArray(new Event[wishedEvents.size()]));
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
