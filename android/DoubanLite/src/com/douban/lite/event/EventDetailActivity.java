package com.douban.lite.event;

import com.android.volley.toolbox.NetworkImageView;
import com.douban.lite.R;
import com.douban.lite.event.bean.Event;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class EventDetailActivity extends ActionBarActivity {
	private final static String TAG = EventDetailActivity.class.getSimpleName();

	final static String STATE_EVENT = "state_event";
	private TextView mEventNameTv;
	private TextView mEventContentTv;
	private TextView mEventTimeTv;
	private TextView mEventTypeTv;
	private TextView mEventAddressTv;
	private NetworkImageView mEventThumbNiv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_detail);
		initViews();
		initActionBar();
	}

	private void initActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);
	}

	private void initViews() {
		mEventNameTv = (TextView) this.findViewById(R.id.tv_event_name);
		mEventContentTv = (TextView) this.findViewById(R.id.tv_event_content);
		mEventTimeTv = (TextView) this.findViewById(R.id.tv_event_time);
		mEventAddressTv = (TextView) this.findViewById(R.id.tv_event_address);
		mEventTypeTv = (TextView) this.findViewById(R.id.tv_event_type);
		mEventThumbNiv = (NetworkImageView) this
				.findViewById(R.id.niv_event_thumb);

		Bundle bundle = this.getIntent().getExtras();
		if (bundle != null) {
			Event evt = (Event) bundle.getSerializable(STATE_EVENT);
			mEventNameTv.setText(evt.title);
			mEventContentTv.setText(evt.content);
			mEventAddressTv.setText(evt.address);
			mEventTimeTv.setText(evt.getEventTime());
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d(TAG, "onOptionsItemSelected()+");
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
