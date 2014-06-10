package com.like.douban.event;

import java.util.ArrayList;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.like.R;
import com.like.douban.event.bean.Event;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class EventMapActivity extends ActionBarActivity implements
		OnMapClickListener {
	public static final String BUNDLE_KEY_EVENT = "bundle.key.event";
	private BaiduMap mBaiduMap;
	private MapView mMapView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_baidu_map_event);
		init();
		testMarker(getIntent().getExtras());
	}

	private void init() {
		MapView mv = (MapView) this.findViewById(R.id.bmv_event);
		mBaiduMap = mv.getMap();
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		mMapView = mv;
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

	@Override
	public void onMapClick(LatLng latLng) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onMapPoiClick(MapPoi mapPoi) {
		return false;
	}

}
