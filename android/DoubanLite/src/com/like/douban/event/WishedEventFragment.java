package com.like.douban.event;

import java.util.List;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.like.MyApplication;
import com.like.R;
import com.like.douban.account.AccountManager;
import com.like.douban.api.DoubanResponseListener;
import com.like.douban.event.api.GetWisheredEvents;
import com.like.douban.event.bean.Event;
import com.like.douban.event.bean.EventList;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class WishedEventFragment extends Fragment {
	private final static String TAG = WishedEventFragment.class.getSimpleName();
	private PullToRefreshListView mPullRefreshListView;
	private GetWisheredEvents mGetWisheredEvents;
	private EventAdapter mWishEventAdapter;
	private ImageLoader mImageLoader;
	private RequestQueue mRequestQueue;
	private DoubanResponseListener<EventList> mGetWishedEventsListener = new DoubanResponseListener<EventList>() {
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
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initData();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_event_private, container, false);
		initPullRefreshListView(root);

		return root;
	}

	private void initData() {
		Activity act = this.getActivity();
		MyApplication myApp = (MyApplication) act.getApplication();
		mImageLoader = myApp.getImageLoader();
		mRequestQueue = myApp.getRequestQueue();
		Context ctx = act.getApplicationContext();
		mWishEventAdapter = new EventAdapter(ctx, mImageLoader);
		ListView actualListView = mPullRefreshListView.getRefreshableView();
		actualListView.setAdapter(mWishEventAdapter);
		
		mGetWisheredEvents = new GetWisheredEvents(ctx, mRequestQueue, mGetWishedEventsListener);
		String userID = AccountManager.getLoginUserID(ctx);
		mGetWisheredEvents.query(userID);
	}

	private void initPullRefreshListView(final ViewGroup content) {
		final Context ctx = this.getActivity().getApplicationContext();
		mPullRefreshListView = (PullToRefreshListView) content
				.findViewById(R.id.prlv_event_participanted);
		// Set a listener to be invoked when the list should be refreshed.
		mPullRefreshListView
				.setOnRefreshListener(new OnRefreshListener<ListView>() {
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
						mGetWisheredEvents.query(AccountManager.getLoginUserID(ctx));
					}
				});

		mPullRefreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Event evt = (Event) mWishEventAdapter.getItem(position - 1);
				EventUtil.showEventDetail(getActivity(), evt);
			}

		});
		mPullRefreshListView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				if (mWishEventAdapter.hasNextEvent()) {
					mGetWisheredEvents.query(AccountManager.getLoginUserID(ctx), mWishEventAdapter.getCount(), 20);
				}
			}});
		ListView actualListView = mPullRefreshListView.getRefreshableView();
		registerForContextMenu(actualListView);
//		selectParticipantItem();

		// Need to use the Actual ListView when registering for Context Menu
	}

	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (EventDetailActivity.REQUEST_CODE == requestCode) {
			Log.d(TAG, "onActivityResult()+");
			EventManager manager = EventManager.getInstance();
			List<Event> wishedEvents = manager.getWisheredEvents();
			mWishEventAdapter.updateEventList(wishedEvents);
		}
	}
}
