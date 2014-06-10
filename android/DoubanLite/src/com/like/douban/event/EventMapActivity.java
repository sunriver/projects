package com.like.douban.event;

import java.util.ArrayList;
import java.util.HashMap;

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
import com.like.R;
import com.like.douban.event.bean.Event;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class EventMapActivity extends ActionBarActivity implements OnMarkerClickListener {
	public static final String BUNDLE_KEY_EVENT = "bundle.key.event";
	private BaiduMap mBaiduMap;
	private MapView mMapView;
	private TextView mEventNameTv;
	private ViewGroup mEventVg;
	private HashMap<String, Event> mLatLngHashMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_baidu_map_event);
		init();
		testMarker(getIntent().getExtras());
	}

	private void init() {
		mLatLngHashMap = new HashMap<String, Event>();
		MapView mv = (MapView) this.findViewById(R.id.bmv_event);
		mBaiduMap = mv.getMap();
		mBaiduMap.setOnMarkerClickListener(this);
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		mMapView = mv;
		
		mEventNameTv = (TextView) this.findViewById(R.id.tv_event_name);
		mEventVg = (ViewGroup) this.findViewById(R.id.ll_event);
	}

	private void updateCity(LatLng latLng) {
		MapStatusUpdate msUpdate = MapStatusUpdateFactory.newLatLng(latLng);
		mBaiduMap.setMapStatus(msUpdate);
	}

	private void testMarker(Bundle bundle) {
		@SuppressWarnings("unchecked")
		ArrayList<Event> events = (ArrayList<Event>) bundle.getSerializable(BUNDLE_KEY_EVENT);

		if (events != null && events.size() > 0) {
			Event initEvt = events.get(0);
			updateCity(new LatLng(initEvt.getLatitude(), initEvt.getLongitude()));
			for (Event evt : events) {
				String key = evt.getLatitude() + "_" + evt.getLongitude();
				mLatLngHashMap.put(key, evt);
				LatLng point = new LatLng(evt.getLatitude(), evt.getLongitude());
				BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.ic_marker);
				OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
				mBaiduMap.addOverlay(option);
			}
		}

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

	private void showEvent(LatLng latLng) {
		String key = latLng.latitude + "_" + latLng.longitude;
		Event evt = mLatLngHashMap.get(key);
		if (evt != null) {
			Context appCtx = this.getApplicationContext();
	        Animation animation = AnimationUtils.loadAnimation(appCtx, R.anim.slide_from_bottom_event_map);
			mEventNameTv.setText(evt.title);
			mEventVg.startAnimation(animation);
			mEventVg.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		// TODO Auto-generated method stub
		showEvent(marker.getPosition());
		return true;
	}
	
	


}
