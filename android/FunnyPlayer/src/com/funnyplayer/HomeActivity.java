package com.funnyplayer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.funnyplayer.ui.adapter.PagerAdapter;
import com.funnyplayer.ui.adapter.ScrollTabAdapter;
import com.funnyplayer.ui.fragment.AlbumFragment;
import com.funnyplayer.ui.fragment.ArtistFragment;
import com.funnyplayer.ui.fragment.IFragment;
import com.funnyplayer.ui.fragment.PlaylistFragment;
import com.funnyplayer.ui.widgets.ScrollTabView;
import com.funnyplayer.util.MusicUtil;
import com.funnyplayer.util.UncatchExceptionHandler;
import com.funnyplayer.util.ViewUtil;

public class HomeActivity extends ActionBarActivity implements OnClickListener {
	private final static  int NOTIFICATION_ID = 19172439;
	private ViewPager mViewPager;
	private ScrollTabView mTabView;
	private TextView mCustomTitleView;
	private BroadcastReceiver mReceiver;
	private AlbumFragment mAlbumFrament;
	private ArtistFragment mArtistFrament;
	private PlaylistFragment mPlaylistFrament;
	NotificationManager nm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        // Scan for music
        setContentView(R.layout.home);
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		mViewPager = (ViewPager) findViewById(R.id.viewPager);
		mTabView = (ScrollTabView) findViewById(R.id.scrollTabs);
		registerReceiver();
		init();
		mViewPager.setCurrentItem(1, true);
		Thread.setDefaultUncaughtExceptionHandler(new UncatchExceptionHandler(getApplicationContext(), Thread.getDefaultUncaughtExceptionHandler()));
	}
	
	private void updateCustomeTitle(Bundle bundle) {
		if (null == bundle) {
			return;
		}
		String artist = bundle.getString("music_artist");
		String name = bundle.getString("music_name");
		String playItemPath = bundle.getString("music_item_path");
		mCustomTitleView.setText(artist + "  " + name);
		mCustomTitleView.setTag(playItemPath);
	}
	
	private void updatePlayItemState(Bundle bundle) {
		if (null == bundle) {
			return;
		}
		String playItemPath = bundle.getString("music_item_path");
		scrollToItem(playItemPath);
	}
	
	private void updateNotification(Bundle bundle) {
		 NotificationCompat.Builder builder = new NotificationCompat.Builder(this); 
         builder.setSmallIcon(R.drawable.listen);
         if (bundle != null) {
     		String artist = bundle.getString("music_artist");
    		String name = bundle.getString("music_name");
            if (!TextUtils.isEmpty(artist)) {
            	builder.setContentTitle(name);
            	builder.setContentText(artist);
             }
         }
		builder.setDefaults(Notification.DEFAULT_ALL);
    	Intent intent = new Intent(this, HomeActivity.class);
    	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
    	PendingIntent pt = PendingIntent.getActivity(this, 0, intent, 0);
    	builder.setContentIntent(pt);
    	nm.notify(NOTIFICATION_ID, builder.build());
	}
	
	private void registerReceiver() {
		mReceiver = new BroadcastReceiver(){
			@Override
			public void onReceive(Context context, Intent intent) {
				if (intent.getAction().equals(MusicUtil.FilterAction.PLAYER_PAUSED)) {
				} else if (intent.getAction().equals(MusicUtil.FilterAction.PLAYER_PLAYING))  {
					Bundle bundle = intent.getExtras();
					updateCustomeTitle(bundle);
					updateNotification(bundle);
//					updatePlayItemState(bundle);
				}
			}
		};
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(MusicUtil.FilterAction.PLAYER_PLAYING);
		intentFilter.addAction(MusicUtil.FilterAction.PLAYER_PAUSED);
		registerReceiver(mReceiver, intentFilter);
	}
	
	private void unregisterReceiver() {
		if (mReceiver != null) {
			unregisterReceiver(mReceiver);
			mReceiver = null;
		}
	}

	
	private void init() {
		this.setTitle("");
		// Initiate PagerAdapter
		PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
		mAlbumFrament = new AlbumFragment();
		mArtistFrament = new ArtistFragment();
		mPlaylistFrament = new PlaylistFragment();
		// add fragment
		pagerAdapter.addFragment(mAlbumFrament);
		pagerAdapter.addFragment(mArtistFrament);
		pagerAdapter.addFragment(mPlaylistFrament);

		mViewPager.setAdapter(pagerAdapter);
		mViewPager.setOffscreenPageLimit(pagerAdapter.getCount());
		mViewPager.setOnPageChangeListener(mTabView);

		ScrollTabAdapter tabAdapter = new ScrollTabAdapter(this);
		mTabView.setViewPager(mViewPager);
		mTabView.setAdapter(tabAdapter);
		
		initActionBar();
	}
	
    /**
     * Set the ActionBar title
     */
    private void initActionBar() {
    	ActionBar actionBar = getSupportActionBar();
    	
		ViewUtil.setActionBarBackgroundRepeat(this, actionBar);
		actionBar.setCustomView(R.layout.custom_title);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_TITLE);
		mCustomTitleView = (TextView) actionBar.getCustomView();
		mCustomTitleView.setOnClickListener(this);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver();
//		MusicUtil.unBindService(getApplicationContext());
		super.onDestroy();
	}


	@Override
	public void onClick(View v) {
		String itemPath = (String) v.getTag();
		scrollToItem(itemPath);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// case R.id.menu_search:
		// onSearchRequested();
		// break;
		case R.id.menu_lrc:
			startActivity(new Intent(this, LrcActivity.class));
			break;
		default:
			super.onOptionsItemSelected(item);
		}
		return true;
	}

	private void scrollToItem(String playItemPath) {
		if (TextUtils.isEmpty(playItemPath)) {
			return;
		}
		String[] subs = playItemPath.split(":");
		int fragmentIndex = Integer.valueOf(subs[0]);
		int gridIndex = Integer.valueOf(subs[1]);
		int itemIndex = MusicUtil.getItemPos();
		scrollToItem(fragmentIndex, gridIndex, itemIndex);
	}
	
	private void scrollToItem(int fragmentIndex, int gridIndex, int itemIndex) {
		mViewPager.setCurrentItem(fragmentIndex, true);
		PagerAdapter pageAdapter = (PagerAdapter) mViewPager.getAdapter();
		IFragment fragment = (IFragment) pageAdapter.getItem(fragmentIndex);
		fragment.selectItem(gridIndex, itemIndex);
	}

}
