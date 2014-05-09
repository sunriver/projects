package com.like.douban.event;


import com.like.R;
import com.sunriver.common.utils.ViewUtil;
import com.viewpagerindicator.IconPageIndicator;
import com.viewpagerindicator.PageIndicator;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.internal.widget.ListPopupWindow;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

public class PrivateEventActivity extends ActionBarActivity {
	private final static String TAG = PrivateEventActivity.class.getSimpleName();
	private ViewPager mEventViewPager;
	private  PageIndicator mEventIndicator;;
	private ParticipantedEventFragment mParticipantedFragment;
	private WishedEventFragment mWishedFragment;


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.activity_event_private);
		initActionBar();
		initViews();
	}


	private void initViews() {
		mEventViewPager = (ViewPager) this.findViewById(R.id.vp_private);
		
		PageAdapter pagerAdapter = new PageAdapter(getSupportFragmentManager());
		mParticipantedFragment = new ParticipantedEventFragment();
		mWishedFragment = new WishedEventFragment();
		pagerAdapter.addFragment(mParticipantedFragment);
		pagerAdapter.addFragment(mWishedFragment);
		mEventViewPager.setAdapter(pagerAdapter);
		
		mEventIndicator = (IconPageIndicator) findViewById(R.id.ic_indicator);
		mEventIndicator.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPageSelected(int index) {
				ActionBar actionBar = getSupportActionBar();
				switch (index) {
				case 0:
					actionBar.setTitle(R.string.event_private_participanted);
					break;
				case 1:
					actionBar.setTitle(R.string.event_private_wished);
					break;
				}
				
			}
			
		});
		mEventIndicator.setViewPager(mEventViewPager);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		 MenuInflater inflater = new MenuInflater(getApplicationContext());
		inflater.inflate(R.menu.event_private, menu);
		MenuItem settingItem = menu.findItem(R.id.menu_item_setting);
		MenuItemCompat.setShowAsAction(settingItem, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
		return super.onCreateOptionsMenu(menu);
	}
	



	private void initActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle(R.string.event_private_participanted);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP
				| ActionBar.DISPLAY_SHOW_TITLE, ActionBar.DISPLAY_HOME_AS_UP
				| ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_SHOW_HOME);
		ViewUtil.setActionBarBackgroundRepeat(this, actionBar,
				R.drawable.bg_base);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d(TAG, "onOptionsItemSelected()+");
		switch (item.getItemId()) {
		case android.R.id.home:
			super.onBackPressed();
			break;
		case R.id.menu_item_setting:
			showSettings();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private boolean mSubMenuClosed = true;
	public void showSettings() {
		if (!mSubMenuClosed) {
			return;
		}
		Context ctx = this.getApplicationContext();
		final ListPopupWindow lpw = new ListPopupWindow(ctx);
		lpw.setModal(true);
		lpw.setBackgroundDrawable(getResources().getDrawable(R.drawable.dropdown_panel_dark));
//		lpw.setInputMethodMode(ListPopupWindow.INPUT_METHOD_NOT_NEEDED);  
		
        final String[] strs = { getString(R.string.douban_logout) };  
        lpw.setOnItemClickListener(new OnItemClickListener() {  
            @Override  
            public void onItemClick(AdapterView<?> parent, View view,  
                    int position, long id) {  
            	lpw.dismiss();
            }  
        }); 
        
        lpw.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
				mSubMenuClosed = true;
			}
        	
        });
        
        lpw.setContentWidth(250);

        lpw.setAdapter(new ArrayAdapter<String>(ctx, R.layout.actionview_private_participanted, strs));  
        View archor = findViewById(R.id.menu_item_setting);
        lpw.setAnchorView(archor);
        mSubMenuClosed = false;
        lpw.show();
	}
	

}
