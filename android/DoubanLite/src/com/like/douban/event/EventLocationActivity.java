package com.like.douban.event;

import com.like.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class EventLocationActivity extends ActionBarActivity {
	final static String STATE_EVENT_LOCATION_NAME = "state_event_location_name";
	final static String STATE_EVENT_LOCATION_VALUE = "state_event_location_value";
	
	private ListView mEventLocationLv;
	private String[] mLocationValus;
	private String[] mLocationNames;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_location);
		init();
	}
	
	private void init() {
		mEventLocationLv = (ListView) this.findViewById(R.id.lv_event_location);
		mLocationValus = getResources().getStringArray(R.array.event_location_values);
		mLocationNames = getResources().getStringArray(R.array.event_location_names);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.event_location_names, R.layout.listview_item_event_location);
		
		mEventLocationLv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				finishSelf(mLocationNames[position], mLocationValus[position]);
			}
		});

		
		mEventLocationLv.setAdapter(adapter);
	}
	
	private void finishSelf(String locationName, String locationValue) {
		Intent result = new Intent();
		Bundle bundle = new Bundle();
		bundle.putString(STATE_EVENT_LOCATION_NAME, locationName);
		bundle.putString(STATE_EVENT_LOCATION_VALUE, locationValue);
		result.putExtras(bundle);
		setResult(RESULT_OK, result);
		this.finish();
	}

}
