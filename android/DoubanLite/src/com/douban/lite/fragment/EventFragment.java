package com.douban.lite.fragment;

import java.util.LinkedList;

import com.android.volley.RequestQueue;
import com.douban.lite.R;
import com.douban.lite.api.event.Event;
import com.douban.lite.api.event.EventList;
import com.douban.lite.api.event.GetEvents;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class EventFragment extends Fragment {
	private PullToRefreshListView mPullRefreshListView;
	private GetEvents mGetEvents;
	private ArrayAdapter<String> mAdapter;
	private LinkedList<String> mListItems;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup contentView = (ViewGroup) inflater.inflate(
				R.layout.fragment_event, null, false);
		mPullRefreshListView = (PullToRefreshListView) contentView
				.findViewById(R.id.lv_event);
		initPullRefreshListView(getActivity().getApplicationContext());
		return contentView;
	}

	public void bindRequetQueue(RequestQueue queue) {
		mGetEvents = new GetEvents(getActivity().getApplicationContext(),
				queue, this);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mGetEvents.query("hangzhou");
	}

	public void updateEvents(EventList eventList) {
		if (eventList != null) {
			Event[] events = eventList.events;
			for (int i = 0, len = events.length; i < len; i++) {
				Event evt = events[i];
				mListItems.add(evt.title);
			}
		}
		mAdapter.notifyDataSetChanged();
		mPullRefreshListView.onRefreshComplete();
	}

	private void initPullRefreshListView(final Context ctx) {
		mListItems = new LinkedList<String>();
		mAdapter = new ArrayAdapter<String>(ctx,
				android.R.layout.simple_list_item_1, mListItems);

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
						mGetEvents.query("hangzhou");
					}
				});

		// Add an end-of-list listener
		mPullRefreshListView
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

					@Override
					public void onLastItemVisible() {
						Toast.makeText(ctx, "End of List!", Toast.LENGTH_SHORT)
								.show();
					}
				});

		ListView actualListView = mPullRefreshListView.getRefreshableView();
		actualListView.setAdapter(mAdapter);

		// Need to use the Actual ListView when registering for Context Menu
		registerForContextMenu(actualListView);
	}

}
