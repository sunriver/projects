package com.coco.reader;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
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
import com.coco.reader.adapter.PageAdapter.PageChangeListener;
import com.coco.reader.view.ChapterFragment;
import com.coco.reader.view.ChapterFragment.ChapterSelectListener;
import com.coco.reader.view.OptionFragment;
import com.coco.reader.view.OptionFragment.TextSizeChangeListener;
import com.coco.reader.view.PageView;
import com.coco.reader.data.Document;
import com.coco.reader.data.DocumentManager;

public class MainActivity extends ActionBarActivity implements
		ChapterSelectListener, PageChangeListener, TextSizeChangeListener,
		FlipViewController.ViewFlipListener, View.OnClickListener {
	private static final String TAG = MainActivity.class.getSimpleName();

	private FlipViewController mFlipView;
	private PageAdapter mPageAdapter;
	private DocumentManager mDocManager;
	private ActionBarCustomView mAbCustomView;
	private SlidingMenu mSlidingMenu;
	private SlidingMenuTabs mSlidingMenuTabs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDocManager = DocumentManager.getInstance(getApplicationContext());
		initActionBar();
		initFlipView();
		initSlidingMenu();
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

	private static class SlidingMenuTabs {
		ChapterFragment chapter;
		OptionFragment option;
	}

	private void initSlidingMenuTab() {
		TabHost tabHost = (TabHost) findViewById(R.id.myTabHost);
		tabHost.setup();
		String navDirectory = this.getString(R.string.nav_chapter);
		String navBookmark = this.getString(R.string.nav_option);
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator(navDirectory)
				.setContent(R.id.nav_chapter));
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator(navBookmark)
				.setContent(R.id.nav_option));

		mSlidingMenuTabs = new SlidingMenuTabs();
		FragmentManager fragmentManager = getSupportFragmentManager();
		mSlidingMenuTabs.chapter = (ChapterFragment) fragmentManager
				.findFragmentById(R.id.nav_chapter);
		mSlidingMenuTabs.option = (OptionFragment) fragmentManager
				.findFragmentById(R.id.nav_option);
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		MenuInflater inflater = getMenuInflater();
//		inflater.inflate(R.menu.main, menu);
//		return true;
//	}

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
		saveState();
		super.onDestroy();
	}

	private void saveState() {
		int selectedPos = mFlipView.getSelectedItemPosition();
	}

	private void flipNextPage() {
		mFlipView.setFlipByTouchEnabled(true);
		sendMotionEvent(200, 500, -1);
	}

	private void flipPreviousPage() {
		mFlipView.setFlipByTouchEnabled(true);
		sendMotionEvent(200, 200, 1);
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

	@Override
	public void onSizeChangeing(int size) {
		PageView pv = (PageView) mFlipView.getSelectedView();
		if (pv != null) {
			pv.setTextSize(size);
		}
	}

	@Override
	public void onSizeChanged(int size) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onChanpterSelect(Document doc) {
		mAbCustomView.titleTv.setText(doc.getDocName());
		
		float textSize = mSlidingMenuTabs.option.getTextSize();
		mPageAdapter.setTextSize(textSize);
		mPageAdapter.setDocument(doc);
	}

}
