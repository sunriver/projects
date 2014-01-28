package com.coco.reader;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.aphidmobile.flip.FlipViewController;
import com.coco.reader.R;
import com.coco.reader.adapter.PageAdapter;
import com.coco.reader.view.PageView;

public class MainActivity extends ActionBarActivity {
	private static final String TAG = MainActivity.class.getSimpleName();
	
	protected FlipViewController mFlipView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setTitle(R.string.app_name);
		mFlipView = new FlipViewController(getApplicationContext());
		mFlipView.setAdapter(new PageAdapter(getApplicationContext()));
		mFlipView.setFlipByTouchEnabled(false);
		setContentView(mFlipView);

		SlidingMenu menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setMenu(R.layout.menu);
		
		registerScrollReceiver(getApplicationContext());
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
//		mFlipView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
//		mFlipView.onPause();
	}
	
	@Override
    protected void onDestroy() {
//		this.unregisterReceiver(mPageScrollReceiver);
    	super.onDestroy();
    }
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// case R.id.menu_search:
		// onSearchRequested();
		// break;
		case R.id.action_list:
			break;
		case R.id.action_settings:
			break;
		default:
			super.onOptionsItemSelected(item);
		}
		return true;
	}
	
	private void registerScrollReceiver(Context ctx) {
		IntentFilter filter = new IntentFilter(PageView.ACTION_PAGE_BOTTOM);
		filter.addAction(PageView.ACTION_PAGE_TOP);
		ctx.registerReceiver(mPageScrollReceiver, filter);
	}
	
	private  BroadcastReceiver mPageScrollReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			final String action = intent.getAction();
			if (PageView.ACTION_PAGE_BOTTOM.equals(action)) {
				onPageScrollToBottom();
			} else if (PageView.ACTION_PAGE_TOP.equals(action)) {
				onPageScrollToTop();
			}
			
		}
	};
	
	private void onPageScrollToBottom() {
		Log.d(TAG, "onPageScrollToBottom()+");
		mFlipView.setFlipByTouchEnabled(false);
	}
	
	private void onPageScrollToTop() {
		Log.d(TAG, "onPageScrollToTop()+");
		mFlipView.setFlipByTouchEnabled(false);
	}

}
