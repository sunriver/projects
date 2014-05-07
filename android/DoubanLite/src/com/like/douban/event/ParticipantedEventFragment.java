package com.like.douban.event;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.like.MyApplication;
import com.like.R;
import com.like.douban.account.AccountManager;
import com.like.douban.api.ResponseListener;
import com.like.douban.event.api.GetParticipantedEvents;
import com.like.douban.event.api.GetWisheredEvents;
import com.like.douban.event.bean.Event;
import com.like.douban.event.bean.EventList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ParticipantedEventFragment extends Fragment {
	private PullToRefreshListView mPullRefreshListView;
	private GetParticipantedEvents mGetParticipantedEvents;
	private ImageLoader mImageLoader;
	private RequestQueue mRequestQueue;
	private EventAdapter mParticipantEventAdapter;
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

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initData();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_event_participanted, container, false);
		initPullRefreshListView(root);

		return root;
	}

	private void initData() {
		Activity act = this.getActivity();
		MyApplication myApp = (MyApplication) act.getApplication();
		mImageLoader = myApp.getImageLoader();
		mRequestQueue = myApp.getRequestQueue();
		Context ctx = act.getApplicationContext();
		mParticipantEventAdapter = new EventAdapter(ctx, mImageLoader);
		ListView actualListView = mPullRefreshListView.getRefreshableView();
		actualListView.setAdapter(mParticipantEventAdapter);
		
		mGetParticipantedEvents = new GetParticipantedEvents(ctx, mRequestQueue, mGetParticipantedEventsListener);
		String userID = AccountManager.getLoginUserID(ctx);
		mGetParticipantedEvents.query(userID);
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
						mGetParticipantedEvents.query(AccountManager.getLoginUserID(ctx));
					}
				});

		mPullRefreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				EventAdapter adapter = (EventAdapter) parent.getAdapter();
				Event evt = (Event) adapter.getItem(position - 1);
//				showEventDetail(evt);
			}

		});
		ListView actualListView = mPullRefreshListView.getRefreshableView();
		registerForContextMenu(actualListView);
//		selectParticipantItem();

		// Need to use the Actual ListView when registering for Context Menu
	}

}
