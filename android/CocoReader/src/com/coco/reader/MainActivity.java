package com.coco.reader;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.sunriver.advs.AdvsManager;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import com.aphidmobile.flip.FlipViewController;
import com.coco.reader.R;
import com.coco.reader.adapter.PageAdapter;
import com.coco.reader.adapter.PageAdapter.LoadStateChangeListener;
import com.coco.reader.view.ChapterFragment;
import com.coco.reader.view.ChapterFragment.ChapterSelectListener;
import com.coco.reader.view.OptionFragment;
import com.coco.reader.view.OptionFragment.TextSizeChangeListener;
import com.coco.reader.view.PageView;
import com.coco.reader.view.ThemeSwitcher;
import com.coco.reader.data.Document;
import com.coco.reader.data.DocumentManager;
import com.coco.reader.data.OptionSetting;

public class MainActivity extends ActionBarActivity implements
		ChapterSelectListener, LoadStateChangeListener, TextSizeChangeListener, View.OnClickListener {
	private static final String TAG = MainActivity.class.getSimpleName();

	private FlipViewController mFlipView;
	private PageAdapter mPageAdapter;
	private DocumentManager mDocManager;
	private ActionBarCustomView mAbCustomView;
	private SlidingMenu mSlidingMenu;
	private SlidingMenuTabs mSlidingMenuTabs;
	private ThemeSwitcher mThemeSwitcher;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mDocManager = DocumentManager.getInstance(getApplicationContext());
		restoreState();
		super.onCreate(savedInstanceState);
		initActionBar();
		initFlipView();
		initSlidingMenu();
		AdvsManager.showAdvs(this);
	}
	
	private void restoreState() {
		OptionSetting ops = mDocManager.getOptionSetting();
		// "setTheme" must be called before "super.onCreate(), otherwise it will not work.
		setTheme(ops.getThemeId());
	}
	
	@Override
    protected void onStart() {
		super.onStart();
    	mPageAdapter.loadDefaultDocument(getApplicationContext());
    }
	

	private void initFlipView() {
		setContentView(R.layout.activity_main);
		ViewGroup mainContent = (ViewGroup) this.findViewById(R.id.main_content);
		mFlipView = new FlipViewController(getApplicationContext());
		mPageAdapter = new PageAdapter(getApplicationContext());
		mPageAdapter.setLoadStateChangeListener(this);
		mFlipView.setAdapter(mPageAdapter);
		mFlipView.setFlipByTouchEnabled(false);
		mainContent.addView(mFlipView, 0);
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
		
		mThemeSwitcher = new ThemeSwitcher(getApplication(), getSupportActionBar(), mFlipView);
		mSlidingMenuTabs.option.setThemeSwitcher(mThemeSwitcher);
		
		OptionSetting ops = mDocManager.getOptionSetting();
		mSlidingMenuTabs.option.setOptionSetting(ops);
	}
	
	private static class ActionBarCustomView {
		ImageView homeImage;
		TextView titleTv;
	}

	private void initActionBar() {
		ActionBar actionBar = getSupportActionBar();
//		Drawable d = getResources().getDrawable(
//				R.drawable.actionbar_bg_selector);
//		actionBar.setBackgroundDrawable(d);
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
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.nav_home || id == R.id.iv_title_home) {
			mSlidingMenu.showMenu(true);
		}
//		switch (v.getId()) {
//		case R.id.nav_home:
//		case R.id.iv_title_home:
//			mSlidingMenu.showMenu(true);
//			break;
//		default:
//			;
//		}
	}
	

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// mFlipView.onPause();
	}

	@Override
	protected void onDestroy() {
		saveState();
		mDocManager.closeAllDocument();
		DocumentManager.GcInstance();
		super.onDestroy();
	}

	private void saveState() {
		OptionSetting ops = mSlidingMenuTabs.option.getOptionSetting();
		mDocManager.persistOptions(ops);
		
		Document doc = mPageAdapter.getDocument();
		if (doc != null) {
			PageView pv = (PageView) mFlipView.getSelectedView();
			if (pv != null) {
				int pageY = pv.getPageScrollDy();
				doc.setSelectPageScrollDy(pageY);
			}
			mDocManager.persistDocument(doc);
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
		PageView prevPageView = (PageView) mFlipView.getSelectedView();
		if (prevPageView != null) {
			prevPageView.savePageScrollDy();
		}
		
		mAbCustomView.titleTv.setText(doc.getTitile());
		int textSize = (int) mSlidingMenuTabs.option.getTextSize();
		doc.setTextSize(textSize);
		mPageAdapter.setDocument(doc);
	}

	@Override
	public void onDocumentLoadCompleted(Document doc) {
		mAbCustomView.titleTv.setText(doc.getTitile());
		mSlidingMenuTabs.option.setTextSize(doc.getTextSize());
		OptionSetting ops = mSlidingMenuTabs.option.getOptionSetting();
		PageView pv = (PageView) mFlipView.getSelectedView();
		if (pv != null) {
			pv.setPageBackground(ops.getPageBackgroundResId());
		}
	}

}
