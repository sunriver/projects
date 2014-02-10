package com.coco.reader;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import com.aphidmobile.flip.FlipViewController;
import com.coco.reader.R;
import com.coco.reader.adapter.PageAdapter;
import com.coco.reader.adapter.PageAdapter.PageChnageListener;
import com.coco.reader.view.MenuListFragment.OnSlideItemSelectListener;
import com.coco.reader.view.PageView;
import com.coco.reader.data.Document;
import com.coco.reader.data.DocumentManager;

public class MainActivity extends ActionBarActivity implements
		OnSlideItemSelectListener, PageChnageListener,
		FlipViewController.ViewFlipListener, View.OnClickListener {
	private static final String TAG = MainActivity.class.getSimpleName();

	private FlipViewController mFlipView;
	private PageAdapter mPageAdapter;
	private DocumentManager mDocManager;
	private ActionBarCustomView mAbCustomView;
	private SlidingMenu mSlidingMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDocManager = DocumentManager.getInstance(getApplicationContext());
		initActionBar();
		initFlipView();
		initSlidingMenu();
		registerScrollReceiver(getApplicationContext());
	}

	private void initFlipView() {
		mFlipView = new FlipViewController(getApplicationContext());
		mPageAdapter = new PageAdapter(getApplicationContext(), this);
		mFlipView.setAdapter(mPageAdapter);
		mFlipView.setFlipByTouchEnabled(false);
		mFlipView.setOnViewFlipListener(this);
		setContentView(mFlipView);
	}

	private void initSlidingMenu() {
		SlidingMenu menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
		menu.setMenu(R.layout.menu);
		mSlidingMenu = menu;

		initSlidingMenuTab();
	}

	private void initSlidingMenuTab() {
		TabHost tabHost = (TabHost) findViewById(R.id.myTabHost);
		tabHost.setup();
		String navDirectory = this.getString(R.string.nav_directory);
		String navBookmark = this.getString(R.string.nav_option);
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator(navDirectory)
				.setContent(R.id.nav_directory));
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator(navBookmark)
				.setContent(R.id.nav_option));
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
		// mFlipView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// mFlipView.onPause();
	}

	@Override
	protected void onDestroy() {
		// this.unregisterReceiver(mPageScrollReceiver);
		super.onDestroy();
	}

	private void flipNextPage() {
		int selectionItemPos = mFlipView.getSelectedItemPosition();
		int itemCount = mFlipView.getCount();
		if (selectionItemPos < itemCount) {
			// mFlipView.postFlippedToView(selectionItemPos + 1);
		}
		// mFlipView.setSelection(selectionItemPos + 1);
		mFlipView.setFlipByTouchEnabled(true);
		sendMotionEvent(200, 200, -1);
		// mFlipView.refreshPage(selectionItemPos + 1);
	}

	private void flipPreviousPage() {
		int selectionItemPos = mFlipView.getSelectedItemPosition();
		if (selectionItemPos > 0) {
			// mFlipView.postFlippedToView(selectionItemPos - 1);
		}
		// mFlipView.setSelection(selectionItemPos - 1);
		mFlipView.setFlipByTouchEnabled(true);
		sendMotionEvent(200, 200, 1);
		// mFlipView.refreshPage(selectionItemPos - 1);
	}

	private void sendMotionEvent(int x, int y, int dy) {
		long time = SystemClock.uptimeMillis();
		MotionEvent downEvent = MotionEvent.obtain(time, time,
				MotionEvent.ACTION_DOWN, x, y, 0);
		mFlipView.onTouchEvent(downEvent);

		for (int i = 0; i < 60; i++) {
			y = y + dy;
			time = SystemClock.uptimeMillis();
			MotionEvent moveEvent = MotionEvent.obtain(time, time,
					MotionEvent.ACTION_MOVE, x, y, 0);
			mFlipView.onTouchEvent(moveEvent);
		}

		time = SystemClock.uptimeMillis();
		MotionEvent upEvent = MotionEvent.obtain(time, time,
				MotionEvent.ACTION_UP, x, y, 0);
		mFlipView.onTouchEvent(upEvent);
	}

	private static class ActionBarCustomView {
		ImageView homeImage;
		TextView titleTv;
	}

	private void initActionBar() {
		ActionBar actionBar = getSupportActionBar();
		Drawable d = getResources().getDrawable(
				R.drawable.actionbar_bg_selector);
		actionBar.setBackgroundDrawable(d);
		View customView = LayoutInflater.from(getApplicationContext()).inflate(
				R.layout.title, null);
		actionBar.setCustomView(customView);
		mAbCustomView = new ActionBarCustomView();
		mAbCustomView.homeImage = (ImageView) customView
				.findViewById(R.id.iv_title_home);
		mAbCustomView.titleTv = (TextView) customView
				.findViewById(R.id.tv_title_content);
		mAbCustomView.homeImage.setOnClickListener(this);
		customView.findViewById(R.id.nav_home).setOnClickListener(this);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
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

	private BroadcastReceiver mPageScrollReceiver = new BroadcastReceiver() {
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
		// mFlipView.setFlipByTouchEnabled(true);
	}

	private void onPageScrollToTop() {
		Log.d(TAG, "onPageScrollToTop()+");
		// mFlipView.setFlipByTouchEnabled(true);
	}

	@Override
	public void onSlideItemSelect(Document doc) {
		mAbCustomView.titleTv.setText(doc.getDocName());
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

	@Override
	public void onViewFlipped(View view, int position) {
		Toast.makeText(view.getContext(), "Flipped to page " + position,
				Toast.LENGTH_SHORT).show();
		mFlipView.setFlipByTouchEnabled(false);
		mDocManager.persistDocument();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.nav_home:
		case R.id.iv_title_home:
			mSlidingMenu.showMenu(true);
			break;
		default:
			;
		}
	}

}
