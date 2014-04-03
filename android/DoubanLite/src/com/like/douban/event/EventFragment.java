package com.like.douban.event;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.like.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.like.douban.event.api.GetEvents;
import com.like.douban.event.api.GetLocations;
import com.like.douban.event.bean.Event;
import com.like.douban.event.bean.EventList;
import com.like.douban.event.bean.LocationList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class EventFragment extends Fragment {
	private PullToRefreshListView mPullRefreshListView;
	private GetEvents mGetEvents;
	private EventAdapter mEventAdapter;
	private SpinnerPair mLocPair;
	private SpinnerPair mDateTypePair;
	private SpinnerPair mTypePair;

	private static class SpinnerPair {
		String selectedValue;
		String[] values;
		Spinner sp;
		ArrayAdapter<CharSequence> adapter;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Context ctx = getActivity().getApplicationContext();
		ViewGroup contentView = (ViewGroup) inflater.inflate(R.layout.fragment_event, null, false);
		mPullRefreshListView = (PullToRefreshListView) contentView.findViewById(R.id.lv_event);
		initLocationSpinner(ctx, contentView);
		initDayTypeSpinner(ctx, contentView);
		initEventTypeSpinner(ctx, contentView);
		initPullRefreshListView(ctx);
		return contentView;
	}
	
	private void initLocationSpinner(Context ctx, ViewGroup contentView) {
		mLocPair = new SpinnerPair();
		mLocPair.values = ctx.getResources().getStringArray(R.array.event_location_values);
		mLocPair.selectedValue = mLocPair.values[0];
		mLocPair.sp = (Spinner) contentView.findViewById(R.id.sp_loc);
		mLocPair.adapter = ArrayAdapter.createFromResource(ctx,R.array.event_location_names, R.layout.spinner_item);
		mLocPair.adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mLocPair.sp.setAdapter(mLocPair.adapter);

		mLocPair.sp.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				mLocPair.selectedValue = mLocPair.values[position];
				mGetEvents.query(mLocPair.selectedValue, mDateTypePair.selectedValue, mTypePair.selectedValue);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
	}
	
	private void initDayTypeSpinner(Context ctx, ViewGroup contentView) {
		mDateTypePair = new SpinnerPair();
		mDateTypePair.values = getResources().getStringArray(R.array.event_dayType_values);
		mDateTypePair.selectedValue = mDateTypePair.values[0];
		mDateTypePair.sp = (Spinner) contentView.findViewById(R.id.sp_dateType);
		mDateTypePair.adapter = ArrayAdapter.createFromResource(ctx,R.array.event_dayType_names, R.layout.spinner_item);
		mDateTypePair.adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mDateTypePair.sp.setAdapter(mDateTypePair.adapter);

		mDateTypePair.sp.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				mDateTypePair.selectedValue = mDateTypePair.values[position];
				mGetEvents.query(mLocPair.selectedValue, mDateTypePair.selectedValue, mTypePair.selectedValue);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
	}
	
	private void initEventTypeSpinner(Context ctx, ViewGroup contentView) {
		mTypePair = new SpinnerPair();
		mTypePair.values = getResources().getStringArray(R.array.event_type_values);
		mTypePair.selectedValue = mTypePair.values[0];
		mTypePair.sp = (Spinner) contentView.findViewById(R.id.sp_type);
		mTypePair.adapter = ArrayAdapter.createFromResource(ctx,R.array.event_type_names, R.layout.spinner_item);
		mTypePair.adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mTypePair.sp.setAdapter(mTypePair.adapter);
		
		mTypePair.sp.setOnItemSelectedListener(new OnItemSelectedListener() {
			
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				mTypePair.selectedValue = mTypePair.values[position];
				mGetEvents.query(mLocPair.selectedValue, mDateTypePair.selectedValue, mTypePair.selectedValue);
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	public void init(RequestQueue queue, ImageLoader imageLoader) {
		Context ctx = getActivity().getApplicationContext();
		mGetEvents = new GetEvents(ctx, queue, this);
		mEventAdapter = new EventAdapter(ctx, imageLoader);
	}
	

	@Override
	public void onStart() {
		super.onStart();
		mGetEvents.query(mLocPair.selectedValue);
	}

	public void updateEvents(EventList eventList) {
		mEventAdapter.updateEventList(eventList);
		mPullRefreshListView.onRefreshComplete();
	}
	
	public void updateLocations(LocationList locationList) {
		mLocPair.values = locationList.getAllLocationUids();
		mLocPair.adapter.addAll(locationList.getAllLocationNames());
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
						mGetEvents.query(mLocPair.selectedValue);
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
				Event evt = (Event) mEventAdapter.getItem(position - 1);
				showEventDetail(evt);
			}

		});
		ListView actualListView = mPullRefreshListView.getRefreshableView();
		actualListView.setAdapter(mEventAdapter);

		// Need to use the Actual ListView when registering for Context Menu
		registerForContextMenu(actualListView);
	}

	private void showEventDetail(Event evt) {
		Context ctx = this.getActivity();
		Intent intent = new Intent(ctx, EventDetailActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable(EventDetailActivity.STATE_EVENT, evt);
		intent.putExtras(bundle);
		ctx.startActivity(intent);
	}


}
