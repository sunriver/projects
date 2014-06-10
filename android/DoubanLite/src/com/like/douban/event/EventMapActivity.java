package com.like.douban.event;

import java.util.ArrayList;
import java.util.HashMap;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.like.MyApplication;
import com.like.R;
import com.like.douban.event.bean.Event;
import com.sunriver.common.utils.ViewUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class EventMapActivity extends ActionBarActivity implements OnMarkerClickListener , OnMapClickListener {
	public static final String BUNDLE_KEY_EVENT = "bundle.key.event";
	private BaiduMap mBaiduMap;
	private MapView mMapView;
	private TextView mEventNameTv;
	private ViewGroup mEventVg;
	private TextView mEventCategoryNameTv;
	private TextView mEventAddressTv;
	private TextView mEventTimeTv;
	private TextView mEventWisherCountTv;
	private TextView mEventParticipantCountTv;
	private NetworkImageView mEventThumbIv;
	private HashMap<String, Event> mLatLngHashMap;
	private ImageLoader mImageLoader;
	private  BitmapDescriptor mRedMarkerBitmap;
	private  BitmapDescriptor mBlueMarkerBitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_baidu_map_event);
		initView();
		initMarker(getIntent().getExtras());
	}

	private void initView() {
		MyApplication myApp = (MyApplication) this.getApplication();
		mImageLoader = myApp.getImageLoader();
		
		mRedMarkerBitmap = BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_red);
		mBlueMarkerBitmap = BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_blue);
		
		mLatLngHashMap = new HashMap<String, Event>();
		MapView mv = (MapView) this.findViewById(R.id.bmv_event);
		mBaiduMap = mv.getMap();
		mBaiduMap.setOnMarkerClickListener(this);
		mBaiduMap.setOnMapClickListener(this);
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		mMapView = mv;
		
		mEventNameTv = (TextView) this.findViewById(R.id.tv_event_name);
		mEventVg = (ViewGroup) this.findViewById(R.id.ll_event);
		mEventCategoryNameTv = (TextView) this.findViewById(R.id.tv_event_category_name);
		mEventAddressTv = (TextView) this.findViewById(R.id.tv_event_address);
		mEventTimeTv = (TextView) this.findViewById(R.id.tv_event_time);
		mEventWisherCountTv = (TextView) this.findViewById(R.id.tv_event_wisher_count);
		mEventParticipantCountTv = (TextView) this.findViewById(R.id.tv_event_participant_count);
		mEventThumbIv = (NetworkImageView) this.findViewById(R.id.niv_event_thumb);
		
		mEventVg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final Event evt = (Event) v.getTag();
				if (evt != null) {
					EventUtil.showEventDetail(EventMapActivity.this, evt);
				}
			}
		});
		
		initActionBar();
	}

	private void updateCity(LatLng latLng) {
		MapStatusUpdate msUpdate = MapStatusUpdateFactory.newLatLng(latLng);
		mBaiduMap.setMapStatus(msUpdate);
	}

	private void initMarker(Bundle bundle) {
		@SuppressWarnings("unchecked")
		ArrayList<Event> events = (ArrayList<Event>) bundle.getSerializable(BUNDLE_KEY_EVENT);

		if (events != null && events.size() > 0) {
			Event initEvt = events.get(0);
			updateCity(new LatLng(initEvt.getLatitude(), initEvt.getLongitude()));
			for (Event evt : events) {
				String key = evt.getLatitude() + "_" + evt.getLongitude();
				mLatLngHashMap.put(key, evt);
				LatLng point = new LatLng(evt.getLatitude(), evt.getLongitude());
				OverlayOptions option = new MarkerOptions().position(point).icon(mRedMarkerBitmap);
				mBaiduMap.addOverlay(option);
			}
		}

	}
	
	private void initActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle(R.string.event_map);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP
				| ActionBar.DISPLAY_SHOW_TITLE, ActionBar.DISPLAY_HOME_AS_UP
				| ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_SHOW_HOME);
		ViewUtil.setActionBarBackgroundRepeat(this, actionBar,
				R.drawable.bg_base);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			super.onBackPressed();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mMapView.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mMapView.onPause();
	}

	private boolean showEvent(LatLng latLng) {
		String key = latLng.latitude + "_" + latLng.longitude;
		Event evt = mLatLngHashMap.get(key);
		if (evt != null) {
			Context appCtx = this.getApplicationContext();
	        Animation animation = AnimationUtils.loadAnimation(appCtx, R.anim.slide_from_bottom_event_map);
			mEventNameTv.setText(evt.title);
			mEventCategoryNameTv.setText(" < " + evt.category_name + " > ");
			mEventAddressTv.setText(evt.address);
			mEventParticipantCountTv.setText(String.valueOf(evt.participant_count));
			mEventWisherCountTv.setText(String.valueOf(evt.wisher_count));
			
			mEventTimeTv.setText(evt.getEventTime());
			mEventThumbIv.setImageUrl(evt.image, mImageLoader);
			
			
			mEventVg.startAnimation(animation);
			mEventVg.setVisibility(View.VISIBLE);
			mEventVg.setTag(evt);
			return true;
		} else {
			//hide event tip
			mEventVg.setTag(null);
			mEventVg.setVisibility(View.INVISIBLE);
			return false;
		}
	}
	
	private Marker mPreMarker;

	@Override
	public boolean onMarkerClick(Marker marker) {
		if (mPreMarker != null) {
			mPreMarker.setIcon(mRedMarkerBitmap);
		}
		marker.setIcon(mBlueMarkerBitmap);
		mPreMarker = marker;
		return showEvent(marker.getPosition());
	}

	@Override
	public void onMapClick(LatLng latLng) {
		if (!showEvent(latLng)) {
			if (mPreMarker != null) {
				mPreMarker.setIcon(mRedMarkerBitmap);
				mPreMarker = null;
			}
		}
	}

	@Override
	public boolean onMapPoiClick(MapPoi arg0) {
		// TODO Auto-generated method stub
		return false;
	}


}
