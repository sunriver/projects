package com.oobe.tab;

import java.util.ArrayList;
import java.util.List;

import com.oobe.MainActivity;
import com.oobe.R;
import com.oobe.R.layout;
import com.oobe.touch.TouchActivity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;

public class OOBETabActivity extends TabActivity {
	
	private final static String TAG = "OOBE";
	
	private TabHost mTabHost;
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabactivity);
		mTabHost = this.getTabHost();
		init();
	}
	
	private void addOneTab() {
		TabSpec tabSpec = mTabHost.newTabSpec("one");
		tabSpec.setIndicator("one");
		tabSpec.setContent(new Intent(this, MainActivity.class));
		mTabHost.addTab(tabSpec);
	}
	
	private void addTwoTab() {
		TabSpec tabSpec = mTabHost.newTabSpec("two");
		tabSpec.setIndicator("two");
		tabSpec.setContent(new Intent(this, TouchActivity.class));
//		tabSpec.setContent(new Intent(this, OpenGlActivity.class));
		mTabHost.addTab(tabSpec);
	}
	
	private void addThreeTab() {
		TabSpec tabSpec = mTabHost.newTabSpec("three");
		tabSpec.setIndicator("three");
		tabSpec.setContent(new TabContentFactory(){
			private List<String> items;
			{
				items = new ArrayList<String>();
				items.add("Tomorrow");
				items.add("Today");
				items.add("Yesterday");
			}
			@Override
			public View createTabContent(String tag) {
				ListView listView = new ListView(OOBETabActivity.this);
				listView.setAdapter(new ArrayAdapter<String>(OOBETabActivity.this, android.R.layout.simple_list_item_1, items));
				return listView;
			}});
		
		mTabHost.addTab(tabSpec);
	}
	
	private void addFourTab() {
		TabSpec tabSpec = mTabHost.newTabSpec("four");
		tabSpec.setIndicator("Four");
		tabSpec.setContent(R.id.Option01);
		mTabHost.addTab(tabSpec);
	}

	private void init() {
		addOneTab();
		addTwoTab();
		addThreeTab();
		addFourTab();
		mTabHost.setOnTabChangedListener(new OnTabChangeListener(){
			@Override
			public void onTabChanged(String tabId) {
				Log.i(TAG, "OnTabChangeListener.onTabChanged()+: tabId=" + tabId);
			}});
		mTabHost.setCurrentTab(1);
	}

}
