package com.coco.reader;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.aphidmobile.flip.FlipViewController;
import com.coco.reader.R;
import com.coco.reader.adapter.PageAdapter;

public class MainActivity extends ActionBarActivity {
	protected FlipViewController mFlipView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setTitle(R.string.app_name);
		mFlipView = new FlipViewController(getApplicationContext());
		mFlipView.setAdapter(new PageAdapter(getApplicationContext()));
		setContentView(mFlipView);
//		this.setContentView(R.layout.activity_main);

		SlidingMenu menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setMenu(R.layout.menu);
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

}
