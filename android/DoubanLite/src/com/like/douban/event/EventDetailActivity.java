package com.like.douban.event;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.like.R;
import com.like.MyApplication;
import com.like.douban.api.ResponseListener;
import com.like.douban.event.api.GetParticipantedUsers;
import com.like.douban.event.api.JoinEvent;
import com.like.douban.event.api.UnJoinEvent;
import com.like.douban.event.api.UnWishEvent;
import com.like.douban.event.api.WishEvent;
import com.like.douban.event.bean.Event;
import com.like.douban.account.AccountManager;
import com.like.douban.account.bean.TokenResult;
import com.like.douban.account.bean.UserList;
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
	public final static int REQUEST_CODE = 0x01;
			
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
	private EventManager mEventManager;
	private boolean mParticipanted = false;
	private boolean mWished = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.activity_event_detail);
		mEventManager = EventManager.getInstance();
		init();
	}

	private void init() {
		MyApplication myApp = (MyApplication) this.getApplication();
		mImageLoader = myApp.getImageLoader();
		mRequestQueue = myApp.getRequestQueue();
		mEvent = extractEventFromBundle();
		initActionBar();
		initViews(mEvent);
	}
	
	private void initParticipantedUsers(final String eventID) {
		ResponseListener<UserList> listener = new ResponseListener<UserList> () {
			@Override
			public void onSuccess(UserList result) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onFailure() {
				// TODO Auto-generated method stub
				
			}
		};
		
		GetParticipantedUsers request = new GetParticipantedUsers(getApplicationContext(), mRequestQueue, listener);
		request.query(eventID);
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
	
	private Event extractEventFromBundle() {
		Bundle bundle = this.getIntent().getExtras();
		if (bundle != null) {
			Event evt = (Event) bundle.getSerializable(STATE_EVENT);
			return evt;
		}
		return null;
	}

	private void initViews(Event evt) {
		mEventNameTv = (TextView) this.findViewById(R.id.tv_event_name);
		mEventContentTv = (TextView) this.findViewById(R.id.tv_event_content);
		mEventTimeTv = (TextView) this.findViewById(R.id.tv_event_time);
		mEventAddressTv = (TextView) this.findViewById(R.id.tv_event_address);
		mEventThumbNiv = (NetworkImageView) this.findViewById(R.id.niv_event_thumb);
		
		mEventWisherTv = (TextView) this.findViewById(R.id.tv_event_wisher);
		mEventParticipantTv = (TextView) this.findViewById(R.id.tv_event_participant);
		mParticipanted= mEventManager.isParticipantedEvent(evt);
		updateParticipantTextView(mParticipanted);
		mEventParticipantTv.setOnClickListener(this);

		mWished = mEventManager.isWisheredEvent(evt);
		updateWishTextView(mWished);
		mEventWisherTv.setOnClickListener(this);
		if (evt != null) {
			mEventNameTv.setText(evt.title);
			mEventContentTv.setText(evt.content);
			mEventAddressTv.setText(evt.address);
			mEventTimeTv.setText(evt.getEventTime());
			mEventThumbNiv.setImageUrl(evt.image, mImageLoader);
		}
	}
	
	private void updateParticipantTextView(boolean isParticipanted) {
		mEventParticipantTv.setText((isParticipanted ? getString(R.string.event_unparticipant) : getString(R.string.event_participant)));
	}
	
	private void updateWishTextView(boolean isWished) {
		mEventWisherTv.setText((isWished ? getString(R.string.event_unwish) : getString(R.string.event_wish)));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d(TAG, "onOptionsItemSelected()+");
		switch (item.getItemId()) {
		case android.R.id.home:
			setResult(RESULT_OK);
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
		case R.id.tv_event_wisher:
			onClickWishView();
			break;
		case R.id.tv_event_participant: 
			onClickParticipantView();
			break;
		}
	}
	
	private void onClickWishView() {
		TokenResult tokenResult = AccountManager.getToken(getApplicationContext());
		String accessToken = tokenResult.getAccessToken();
		if (TextUtils.isEmpty(accessToken)) {
			AccountManager.doLogin(this);
			return;
		}
		if (mWished) {
			doUnWish(accessToken);
		} else {
			doWish(accessToken);
		}
	}
	
	private void doUnWish(final String accessToken) {
		ResponseListener<Void> unWishlistener = new ResponseListener<Void>() {
			@Override
			public void onSuccess(Void result) {
				mEventManager.removeWishedEvent(mEvent);
				mWished = false;
				updateWishTextView(false);
			}

			@Override
			public void onFailure() {
				// TODO Auto-generated method stub
				
			}
		};
		UnWishEvent unwishEvent = new UnWishEvent(getApplicationContext(), mRequestQueue, unWishlistener);
		unwishEvent.unWish(accessToken, mEvent.id);
	}
	
	private void doWish(final String accessToken) {
		ResponseListener<Void> wishListener = new ResponseListener<Void>() {
			@Override
			public void onSuccess(Void result) {
				mEventManager.saveWishedEvent(mEvent);
				mWished = true;
				updateWishTextView(true);
			}

			@Override
			public void onFailure() {
				// TODO Auto-generated method stub
				
			}
		};
		WishEvent wishEvent = new WishEvent(getApplicationContext(), mRequestQueue, wishListener);
		wishEvent.wish(accessToken, mEvent.id);
		
	}
	
	private void onClickParticipantView() {
		TokenResult tokenResult = AccountManager.getToken(getApplicationContext());
		String accessToken = tokenResult.getAccessToken();
		if (TextUtils.isEmpty(accessToken)) {
			AccountManager.doLogin(this);
			return;
		}
		if (mParticipanted) {
			doUnJoin(accessToken);
		} else {
			doJoin(accessToken);
		}
	}
	
	private void doUnJoin(final String accessToken) {
		ResponseListener<Void> unJoinListener = new ResponseListener<Void>() {
			@Override
			public void onSuccess(Void result) {
				mEventManager.removeParticipantEvent(mEvent);
				mParticipanted = false;
				updateParticipantTextView(false);
			}

			@Override
			public void onFailure() {
				// TODO Auto-generated method stub
				
			}
		};
		UnJoinEvent unJoinEvent = new UnJoinEvent(getApplicationContext(), mRequestQueue, unJoinListener);
		unJoinEvent.unJoin(accessToken, mEvent.id);
		
	}
	
	private void doJoin(final String accessToken) {
		ResponseListener<Void> joinListener = new ResponseListener<Void>() {
			@Override
			public void onSuccess(Void result) {
				mEventManager.saveParticipantEvent(mEvent);
				mParticipanted = true;
				updateParticipantTextView(true);
			}

			@Override
			public void onFailure() {
				// TODO Auto-generated method stub
				
			}
		};
		JoinEvent joinEvent = new JoinEvent(getApplicationContext(), mRequestQueue, joinListener);
		joinEvent.join(accessToken, mEvent.id);
		
	}
}
