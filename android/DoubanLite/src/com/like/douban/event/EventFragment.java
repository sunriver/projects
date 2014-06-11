package com.like.douban.event;

import java.lang.reflect.Field;
import java.util.ArrayList;
import com.like.MyApplication;
import com.like.R;
import com.android.volley.RequestQueue;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.like.douban.api.ResponseListener;
import com.like.douban.event.api.GetEvents;
import com.like.douban.event.api.GetParticipantedEvents;
import com.like.douban.event.api.GetWisheredEvents;
import com.like.douban.event.bean.Event;
import com.like.douban.event.bean.EventList;
import com.like.douban.event.bean.LocationList;
import com.like.douban.account.AccountManager;
import com.like.douban.account.bean.TokenResult;
import com.sunriver.common.utils.ApiLevel;
import com.sunriver.common.utils.ViewUtil;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.Spinner;

public class EventFragment extends Fragment implements OnClickListener {
	private static final String TAG = EventFragment.class.getSimpleName();
	private static final int POPUP_HEIGHT = 700;
	private static final String FILE_EVENT_PREF = "event.pref";
	private static final String PREF_SELECTED_CITY = "pref.selected.city";
	private static final String PREF_SELECTED_CITY_INDEX = "pref.selected.city.index";
	private PullToRefreshListView mPullRefreshListView;
	private GetEvents mGetEvents;
	private EventAdapter mEventAdapter;
	private SpinnerPair mLocPair;
	private SpinnerPair mDateTypePair;
	private SpinnerPair mTypePair;
	private SharedPreferences mSharedPreferences;
	private ImageView mLoginIv;
	private ImageView mMapIv;

	private static class SpinnerPair {
		String selectedValue;
		int selectedPos;
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
		mSharedPreferences = ctx.getSharedPreferences(FILE_EVENT_PREF, Context.MODE_PRIVATE);
		mLoginIv = (ImageView) contentView.findViewById(R.id.iv_account);
		mLoginIv.setOnClickListener(this);
		mMapIv = (ImageView) contentView.findViewById(R.id.iv_map);
		mMapIv.setOnClickListener(this);
		
		initLocationSpinner(ctx, contentView);
		initDayTypeSpinner(ctx, contentView);
		initEventTypeSpinner(ctx, contentView);
		initPullRefreshListView(ctx);
		return contentView;
	}
	
	private void initParticipantedEvents(Context ctx, RequestQueue queue) {
		if (!AccountManager.checkAccessValidity(ctx)) {
			return ;
		}
		TokenResult tokenResult = AccountManager.getToken(ctx);
		ResponseListener<EventList> listener = new ResponseListener<EventList> () {
			@Override
			public void onSuccess(EventList result) {
				EventManager.getInstance().saveParticipantEvents(result);
			}

			@Override
			public void onFailure() {
				// TODO Auto-generated method stub
				
			}
		};
		
		GetParticipantedEvents request = new GetParticipantedEvents(ctx, queue, listener);
		request.query(tokenResult.getUserID());
	}
	
	
	private void initWisheredEvents(Context ctx, RequestQueue queue) {
		if (!AccountManager.checkAccessValidity(ctx)) {
			return ;
		}
		TokenResult tokenResult = AccountManager.getToken(ctx);
		ResponseListener<EventList> listener = new ResponseListener<EventList> () {
			@Override
			public void onSuccess(EventList result) {
				EventManager.getInstance().saveWisheredEvents(result);
			}
			
			@Override
			public void onFailure() {
				// TODO Auto-generated method stub
				
			}
		};
		
		GetWisheredEvents request = new GetWisheredEvents(ctx, queue, listener);
		request.query(tokenResult.getUserID());
	}
	
	
	private void initEvents(Context ctx, RequestQueue queue) {
		ResponseListener<EventList> listener = new ResponseListener<EventList> () {
			@Override
			public  void onSuccess(EventList result) {
				mEventAdapter.updateEventList(result);
				mPullRefreshListView.onRefreshComplete();
			}

			@Override
			public void onFailure() {
				mPullRefreshListView.onRefreshComplete();
			}
		};
		
		mGetEvents = new GetEvents(ctx, queue, listener);
		mPullRefreshListView.setRefreshing();
	}
	

	private void initLocationSpinner(Context ctx, ViewGroup contentView) {
		SpinnerPair pair = new SpinnerPair();
		mLocPair = pair;
		pair.values = ctx.getResources().getStringArray(R.array.event_location_values);
	
		pair.selectedValue = mSharedPreferences.getString(PREF_SELECTED_CITY, mLocPair.values[0]);
		pair.selectedPos = mSharedPreferences.getInt(PREF_SELECTED_CITY_INDEX, 0);
		
		pair.sp = (Spinner) contentView.findViewById(R.id.sp_loc);
		setDropDownHeight(pair.sp, POPUP_HEIGHT);
		String[] locations = this.getResources().getStringArray(R.array.event_location_names);
		pair.adapter = new ArrayAdapter<CharSequence>(ctx, R.layout.spinner_item, R.id.tv_spinner_item, locations);
		pair.sp.setAdapter(pair.adapter);
		pair.sp.setSelection(pair.selectedPos);
		pair.sp.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				mLocPair.selectedPos = position;
				mLocPair.selectedValue = mLocPair.values[position];
				mPullRefreshListView.setRefreshing();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		
	}
	
	@Override
	public void onDestroy() {
		mSharedPreferences.edit().putString(PREF_SELECTED_CITY, mLocPair.selectedValue)
		.putInt(PREF_SELECTED_CITY_INDEX, mLocPair.selectedPos)
		.commit();
		EventManager.clearInstance();
		super.onDestroy();
	}

	private static void setDropDownHeight(Spinner sp, int height) {
		try {
			Field popupField = Spinner.class.getDeclaredField("mPopup");
			popupField.setAccessible(true);
			Object value = popupField.get(sp);
			Log.d(TAG, "setDropDownHeight()-");
			if (ApiLevel.hasHoneycomb()) {
				if (value instanceof ListPopupWindow) {
					((ListPopupWindow) value).setHeight(height);
				}
			}
		} catch (Throwable ignored) {
			Log.w(TAG, "Can't set spinner's height", ignored);
		}
		
	}
	
	


	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		setDropDownWidth();
		super.onConfigurationChanged(newConfig);
	}

	private void initDayTypeSpinner(Context ctx, ViewGroup contentView) {
		SpinnerPair pair = new SpinnerPair();
		mDateTypePair = pair;
		pair.values = getResources().getStringArray(R.array.event_dayType_values);
		pair.selectedValue = mDateTypePair.values[0];
		pair.sp = (Spinner) contentView.findViewById(R.id.sp_dateType);
//		setDropDownHeight(pair.sp, POPUP_HEIGHT);
		String[] dayTypes = this.getResources().getStringArray(R.array.event_dayType_names);
		pair.adapter = new ArrayAdapter<CharSequence>(ctx, R.layout.spinner_item, R.id.tv_spinner_item, dayTypes);
		pair.sp.setAdapter(pair.adapter);
		

		pair.sp.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				mDateTypePair.selectedValue = mDateTypePair.values[position];
				mPullRefreshListView.setRefreshing();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
	}
	
	private void initEventTypeSpinner(Context ctx, ViewGroup contentView) {
		SpinnerPair pair = new SpinnerPair();
		mTypePair = pair;
		
		pair.values = getResources().getStringArray(R.array.event_type_values);
		pair.selectedValue = mTypePair.values[0];
		pair.sp = (Spinner) contentView.findViewById(R.id.sp_type);
		setDropDownHeight(pair.sp, POPUP_HEIGHT);
		
		String[] typeNames = this.getResources().getStringArray(R.array.event_type_names);
		pair.adapter = new ArrayAdapter<CharSequence>(ctx, R.layout.spinner_item, R.id.tv_spinner_item, typeNames);
		
		pair.sp.setAdapter(pair.adapter);
		
		pair.sp.setOnItemSelectedListener(new OnItemSelectedListener() {
			
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				mTypePair.selectedValue = mTypePair.values[position];
				mPullRefreshListView.setRefreshing();
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	
	private static void setDropDownWidth(Spinner sp, int width) {
		try {
			Field dropDownWidthField = Spinner.class.getDeclaredField("mDropDownWidth");
			dropDownWidthField.setAccessible(true);
			dropDownWidthField.set(sp, width);
			Log.d(TAG, "setDropDownWidth()-");
		} catch (Throwable ignored) {
			Log.w(TAG, "Can't set spinner's width", ignored);
		}
	}
	
	private void setDropDownWidth() {
		WindowManager manager = getActivity().getWindow().getWindowManager();
		int width = ViewUtil.getScreenWidth(manager) / 3;
		if (ApiLevel.hasJellyBean()) {
			mLocPair.sp.setDropDownWidth(width);
			mDateTypePair.sp.setDropDownWidth(width);
			mTypePair.sp.setDropDownWidth(width);
		} else {
			setDropDownWidth(mLocPair.sp, width);
			setDropDownWidth(mDateTypePair.sp, width);
			setDropDownWidth(mTypePair.sp, width);
		}
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Context ctx = getActivity().getApplicationContext();
		MyApplication myApp = (MyApplication) getActivity().getApplication();
		RequestQueue queue = myApp.getRequestQueue();
		mEventAdapter = new EventAdapter(ctx, myApp.getImageLoader());
		ListView actualListView = mPullRefreshListView.getRefreshableView();
		actualListView.setAdapter(mEventAdapter);
		
		setDropDownWidth();
		
		initEvents(ctx, queue);
		initParticipantedEvents(ctx, queue);
		initWisheredEvents(ctx, queue);
		super.onActivityCreated(savedInstanceState);
	}
	
	
	public void updateLocations(LocationList locationList) {
		if (locationList != null) {
			mLocPair.values = locationList.getAllLocationUids();
		}
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
						refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

						// Do work to refresh the list here.
						mGetEvents.query(mLocPair.selectedValue, mDateTypePair.selectedValue, mTypePair.selectedValue);
					}
				});

		// Add an end-of-list listener
		mPullRefreshListView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

					@Override
					public void onLastItemVisible() {
						if (mEventAdapter.hasNextEvent()) {
							mGetEvents.query(mLocPair.selectedValue, mDateTypePair.selectedValue, mTypePair.selectedValue, mEventAdapter.getCount(), 20);
						}
					}
				});

		mPullRefreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Event evt = (Event) mEventAdapter.getItem(position - 1);
				EventUtil.showEventDetail(getActivity(), evt);
			}

		});
		ListView actualListView = mPullRefreshListView.getRefreshableView();
//		actualListView.setAdapter(mEventAdapter);

		// Need to use the Actual ListView when registering for Context Menu
		registerForContextMenu(actualListView);
	}


	private void openEventMap() {
		Activity act = this.getActivity();
		Intent intent = new Intent(act, EventMapActivity.class);
		ArrayList<Event> items = mEventAdapter.asItemList();
		Bundle bundle = new Bundle();
		bundle.putSerializable(EventMapActivity.BUNDLE_KEY_EVENT, items);
		bundle.putString(EventMapActivity.BUNDLE_KEY_EVENT_CITY, mLocPair.selectedValue);
		intent.putExtras(bundle);
		act.startActivity(intent);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_account:
			Activity act = getActivity();
			if (AccountManager.checkAccessValidity(act)) {
				Intent intent = new Intent(act, PrivateEventActivity.class);
				act.startActivity(intent);
			} else {
				AccountManager.doLogin(act);
			}
			break;
		case R.id.iv_map:
			openEventMap();
			break;
		default:
			break;
		}
	}


}
