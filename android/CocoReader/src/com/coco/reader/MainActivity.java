package com.coco.reader;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.sunriver.common.utils.ViewUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBar;
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
import android.widget.TextView;
import android.widget.Toast;

import com.aphidmobile.flip.FlipViewController;
import com.coco.reader.R;
import com.coco.reader.adapter.PageAdapter;
import com.coco.reader.adapter.PageAdapter.PageChnageListener;
import com.coco.reader.view.MenuListFragment;
import com.coco.reader.view.MenuListFragment.OnSlideItemSelectListener;
import com.coco.reader.view.PageView;
import com.codo.reader.data.Document;

public class MainActivity extends ActionBarActivity implements OnSlideItemSelectListener, PageChnageListener {
	private static final String TAG = MainActivity.class.getSimpleName();
	
	private FlipViewController mFlipView;
	private PageAdapter mPageAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setTitle(R.string.app_name);
		mFlipView = new FlipViewController(getApplicationContext());
		mPageAdapter = new PageAdapter(getApplicationContext(), this); 
		mFlipView.setAdapter(mPageAdapter);
		mFlipView.setFlipByTouchEnabled(false);
		
		mFlipView.setOnViewFlipListener(new FlipViewController.ViewFlipListener() {
	        @Override
	        public void onViewFlipped(View view, int position) {
	          Toast.makeText(view.getContext(), "Flipped to page " + position, Toast.LENGTH_SHORT).show();
	          mFlipView.setFlipByTouchEnabled(false);
	        }
	      });
	    
		setContentView(mFlipView);

		SlidingMenu menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
		menu.setMenu(R.layout.menu);
		
		initActionBar();
		
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
	
	
	private void flipNextPage() {
		int selectionItemPos = mFlipView.getSelectedItemPosition();
		int itemCount = mFlipView.getCount();
		if (selectionItemPos < itemCount) {
//			mFlipView.postFlippedToView(selectionItemPos + 1);
		}
//			mFlipView.setSelection(selectionItemPos + 1);
		mFlipView.setFlipByTouchEnabled(true);
		sendMotionEvent(200, 200, -1);
//		mFlipView.refreshPage(selectionItemPos + 1);
	}
	
	private void flipPreviousPage() {
		int selectionItemPos = mFlipView.getSelectedItemPosition();
		if (selectionItemPos > 0) {
//			mFlipView.postFlippedToView(selectionItemPos - 1);
		}
//			mFlipView.setSelection(selectionItemPos - 1);
		mFlipView.setFlipByTouchEnabled(true);
		sendMotionEvent(200, 200, 1);
//		mFlipView.refreshPage(selectionItemPos - 1);
	}
	
	private void sendMotionEvent(int x, int y, int dy) {
		long time = SystemClock.uptimeMillis();
		MotionEvent downEvent = MotionEvent.obtain(time, time, MotionEvent.ACTION_DOWN, x, y, 0);
		mFlipView.onTouchEvent(downEvent);
        
        for (int i = 0; i < 60; i++) {
        	y = y + dy;
        	time = SystemClock.uptimeMillis();
        	MotionEvent moveEvent = MotionEvent.obtain(time, time, MotionEvent.ACTION_MOVE, x, y, 0);
        	mFlipView.onTouchEvent(moveEvent);
        }
        
        
        time = SystemClock.uptimeMillis();
		MotionEvent upEvent = MotionEvent.obtain(time, time, MotionEvent.ACTION_UP, x, y, 0);
		mFlipView.onTouchEvent(upEvent);
	}

    private void initActionBar() {
    	ActionBar actionBar = getSupportActionBar();
    	
		ViewUtil.setActionBarBackgroundRepeat(this, actionBar, R.drawable.shadow);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_TITLE);
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
//		mFlipView.setFlipByTouchEnabled(true);
	}
	
	private void onPageScrollToTop() {
		Log.d(TAG, "onPageScrollToTop()+");
//		mFlipView.setFlipByTouchEnabled(true);
	}



	@Override
	public void onSlideItemSelect(Document doc) {
		mPageAdapter.setDocument(doc);
	}

	@Override
	public void onPreviousPage() {
		flipPreviousPage();
	}

	@Override
	public void onNextPage() {
		flipNextPage();
	}

}
