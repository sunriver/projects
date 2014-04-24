package com.like.douban.event;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.like.R;
import com.like.MyApplication;
import com.like.douban.api.ResponseListener;
import com.like.douban.event.api.JoinEvent;
import com.like.douban.event.bean.Event;
import com.like.douban.event.bean.EventList;
import com.like.douban.account.LoginUtil;
import com.like.douban.account.bean.TokenResult;
import com.sunriver.common.utils.ViewUtil;

import android.content.Intent;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class EventDetailActivity extends ActionBarActivity implements OnClickListener {
	private final static String TAG = EventDetailActivity.class.getSimpleName();

	private ImageLoader mImageLoader;
	private RequestQueue mRequestQueue;
	final static String STATE_EVENT = "state_event";
	private TextView mEventNameTv;
	private TextView mEventContentTv;
	private TextView mEventTimeTv;
	private TextView mEventAddressTv;
	private NetworkImageView mEventThumbNiv;
	private TextView mEventWisherTv;
	private TextView mEventParticipantTv;
	private Event mEvent;
	private ResponseListener mJoinEventResListener = new ResponseListener<Void> () {
		@Override
		public void onSuccess(Void result) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onFailure() {
			// TODO Auto-generated method stub
			
		}
		
	};
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.activity_event_detail);
		init();
	}

	private void init() {
		MyApplication myApp = (MyApplication) this.getApplication();
		mImageLoader = myApp.getImageLoader();
		mRequestQueue = myApp.getRequestQueue();
		initViews();
		initActionBar();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.event_detail, menu);
		MenuItem mapItem = menu.findItem(R.id.action_map);
		MenuItem shareItem = menu.findItem(R.id.action_share);

		MenuItemCompat.setShowAsAction(mapItem,
				MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
		MenuItemCompat.setShowAsAction(shareItem,
				MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
		return true;
	}

	private void initActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle(R.string.event_detail);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP
				| ActionBar.DISPLAY_SHOW_TITLE, ActionBar.DISPLAY_HOME_AS_UP
				| ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_SHOW_HOME);
		ViewUtil.setActionBarBackgroundRepeat(this, actionBar,
				R.drawable.bg_base);
	}

	private void initViews() {
		mEventNameTv = (TextView) this.findViewById(R.id.tv_event_name);
		mEventContentTv = (TextView) this.findViewById(R.id.tv_event_content);
		mEventTimeTv = (TextView) this.findViewById(R.id.tv_event_time);
		mEventAddressTv = (TextView) this.findViewById(R.id.tv_event_address);
		mEventThumbNiv = (NetworkImageView) this.findViewById(R.id.niv_event_thumb);
		
		mEventWisherTv = (TextView) this.findViewById(R.id.tv_event_wisher);
		mEventParticipantTv = (TextView) this.findViewById(R.id.tv_event_participant);
		mEventWisherTv.setOnClickListener(this);
		mEventParticipantTv.setOnClickListener(this);

		Bundle bundle = this.getIntent().getExtras();
		if (bundle != null) {
			Event evt = (Event) bundle.getSerializable(STATE_EVENT);
			mEventNameTv.setText(evt.title);
			mEventContentTv.setText(evt.content);
			mEventAddressTv.setText(evt.address);
			mEventTimeTv.setText(evt.getEventTime());
			mEventThumbNiv.setImageUrl(evt.image, mImageLoader);
			mEvent = evt;
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d(TAG, "onOptionsItemSelected()+");
		switch (item.getItemId()) {
		case android.R.id.home:
			super.onBackPressed();
			break;
		case R.id.action_share:
			shareEvent();
			break;
		case R.id.action_map:
			locateAddressInMap();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void locateAddressInMap() {
		PointF p = mEvent.getGeoPoint();
		String sAddress = "geo:" + p.x + "," + p.y + "?q=" + Uri.encode(mEvent.address);
		Uri uri = Uri.parse(sAddress);
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	private void shareEvent() {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.event_share_subject));
		
		StringBuffer shareText = new StringBuffer();
		shareText.append(mEvent.title + "\n");
		shareText.append(getString(R.string.event_date) + " ： " + mEvent.getEventTime() + "\n");
		shareText.append(getString(R.string.event_address) + " ： " + mEvent.address + "\n");
		shareText.append(getString(R.string.event_detail) + " ： " + mEvent.adapt_url + "\n");
		
		intent.putExtra(Intent.EXTRA_TEXT, shareText.toString());
		intent.putExtra(Intent.EXTRA_TITLE, mEvent.title);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(Intent.createChooser(intent, getString(R.string.event_share_chooser)));

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_event_wisher_count:
			doIntersting();
			break;
		case R.id.tv_event_participant_count:
			doParticipant();
			break;
		}
	}

	
	private void doParticipant() {
		JoinEvent joinEvent = new JoinEvent(getApplicationContext(), mRequestQueue, mJoinEventResListener);
		TokenResult tokenResult = LoginUtil.getToken(getApplicationContext());
		String accessToken = tokenResult.getAccessToken();
		if (TextUtils.isEmpty(accessToken)) {
			LoginUtil.doLogin(this);
			return;
		}
		joinEvent.join(tokenResult.getAccessToken(), mEvent.id);
	}
	
	private void doIntersting() {
		
	}
}
