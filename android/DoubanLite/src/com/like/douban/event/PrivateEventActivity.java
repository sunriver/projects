package com.like.douban.event;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.like.R;
import com.like.MyApplication;
import com.like.douban.event.api.GetEvents;
import com.like.douban.event.api.JoinEvent;
import com.like.douban.event.bean.Event;
import com.like.douban.login.LoginUtil;
import com.like.douban.login.api.TokenResult;
import com.sunriver.common.utils.ViewUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class PrivateEventActivity extends ActionBarActivity implements OnClickListener {
	private final static String TAG = PrivateEventActivity.class.getSimpleName();

	private PullToRefreshListView mPullRefreshListView;
	private ImageLoader mImageLoader;
	private RequestQueue mRequestQueue;
	private GetEvents mGetEvents;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.activity_event_private);
		init();
	}

	private void init() {
		MyApplication myApp = (MyApplication) this.getApplication();
		mImageLoader = myApp.getImageLoader();
		mRequestQueue = myApp.getRequestQueue();
		Context ctx = this.getApplicationContext();
//		mGetEvents = new GetEvents(ctx, myApp.getRequestQueue(), this);
//		mEventAdapter = new EventAdapter(ctx, myApp.getImageLoader());
//		ListView actualListView = mPullRefreshListView.getRefreshableView();
//		actualListView.setAdapter(mEventAdapter);
		initViews();
		initActionBar();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.event_detail, menu);
//		MenuItem mapItem = menu.findItem(R.id.action_map);
//		MenuItem shareItem = menu.findItem(R.id.action_share);
//
//		MenuItemCompat.setShowAsAction(mapItem,
//				MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
//		MenuItemCompat.setShowAsAction(shareItem,
//				MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
		return true;
	}

	private void initActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle(R.string.event_detail);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP
				| ActionBar.DISPLAY_SHOW_TITLE, ActionBar.DISPLAY_HOME_AS_UP
				| ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_SHOW_HOME);
		ViewUtil.setActionBarBackgroundRepeat(this, actionBar,
				R.drawable.bg_base);
	}

	private void initViews() {
		mPullRefreshListView = (PullToRefreshListView) this.findViewById(R.id.prlv_event);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d(TAG, "onOptionsItemSelected()+");
		switch (item.getItemId()) {
		case android.R.id.home:
			super.onBackPressed();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void initPullRefreshListView(final Context ctx) {
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
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);

						// Do work to refresh the list here.
//						mGetEvents.query(mLocPair.selectedValue, mDateTypePair.selectedValue, mTypePair.selectedValue);
					}
				});

		// Add an end-of-list listener
		mPullRefreshListView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

					@Override
					public void onLastItemVisible() {
//						Toast.makeText(ctx, "End of List!", Toast.LENGTH_SHORT)
//								.show();
					}
				});

		mPullRefreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
//				Event evt = (Event) mEventAdapter.getItem(position - 1);
//				showEventDetail(evt);
			}

		});
		ListView actualListView = mPullRefreshListView.getRefreshableView();
//		actualListView.setAdapter(mEventAdapter);

		// Need to use the Actual ListView when registering for Context Menu
		registerForContextMenu(actualListView);
	}



	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_event_wisher_count:
			doIntersting();
			break;
		case R.id.tv_event_participant_count:
			doParticipant();
			break;
		}
	}

	private void doParticipant() {
		JoinEvent joinEvent = new JoinEvent(getApplicationContext(), mRequestQueue);
		TokenResult tokenResult = LoginUtil.getToken(getApplicationContext());
		String accessToken = tokenResult.getAccessToken();
		if (TextUtils.isEmpty(accessToken)) {
			LoginUtil.doLogin(this);
			return;
		}
//		joinEvent.join(tokenResult.getAccessToken(), mEvent.id);
	}
	
	private void doIntersting() {
		
	}
}
