package com.like.douban.event;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.like.R;
import com.like.MyApplication;
import com.like.douban.event.bean.Event;
import com.sunriver.common.utils.ViewUtil;

import android.content.Intent;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class EventDetailActivity extends ActionBarActivity {
	private final static String TAG = EventDetailActivity.class.getSimpleName();
	
	private ImageLoader mImageLoader;
	final static String STATE_EVENT = "state_event";
	private TextView mEventNameTv;
	private TextView mEventContentTv;
	private TextView mEventTimeTv;
	private TextView mEventTypeTv;
	private TextView mEventAddressTv;
	private NetworkImageView mEventThumbNiv;
	private Event mEvent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_detail);
		init();
	}
	
	private void init() {
		MyApplication myApp = (MyApplication) this.getApplication();
		mImageLoader = myApp.getImageLoader();
		
		initViews();
		initActionBar();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.event_detail, menu);
	    MenuItem mapItem = menu.findItem(R.id.action_map);
	    MenuItem shareItem = menu.findItem(R.id.action_share);
	    
	    MenuItemCompat.setShowAsAction(mapItem, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
	    MenuItemCompat.setShowAsAction(shareItem, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
		return true;
	}
	
	

	private void initActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle(R.string.event_detail);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP
				| ActionBar.DISPLAY_SHOW_TITLE, ActionBar.DISPLAY_HOME_AS_UP
				| ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_SHOW_HOME);
		ViewUtil.setActionBarBackgroundRepeat(this, actionBar, R.drawable.bg_base);
	}

	private void initViews() {
		mEventNameTv = (TextView) this.findViewById(R.id.tv_event_name);
		mEventContentTv = (TextView) this.findViewById(R.id.tv_event_content);
		mEventTimeTv = (TextView) this.findViewById(R.id.tv_event_time);
		mEventAddressTv = (TextView) this.findViewById(R.id.tv_event_address);
		mEventTypeTv = (TextView) this.findViewById(R.id.tv_event_type);
		mEventThumbNiv = (NetworkImageView) this.findViewById(R.id.niv_event_thumb);

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
		String sAddress = "geo:" + p.x + "," + p.y;
		Uri uri = Uri.parse(sAddress);
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}
	
	
	private void shareEvent() {
		Intent intent=new Intent(Intent.ACTION_SEND); 
		intent.setType("text/plain");
//		intent.setPackage("com.sina.weibo"); 
		intent.putExtra(Intent.EXTRA_SUBJECT, "分享"); 
		intent.putExtra(Intent.EXTRA_TEXT, "你好 ");
		intent.putExtra(Intent.EXTRA_TITLE, "我是标题");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
		startActivity(Intent.createChooser(intent, "请选择")); 
		
	}

}
