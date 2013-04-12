package com.oobe.palette;

import com.oobe.palette.CustomView;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;

public class PaletteActivity extends Activity {
	private CustomView mCustomView;
	
	private final static int SETTING = 0;
	
	private final static int CLEAR = 1;
	
	private final static int SAVE = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Palette for drawing");
		mCustomView = new CustomView(this);
		setContentView(mCustomView);
		registerForContextMenu(mCustomView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem item = menu.add(0, SETTING , 0, "Setting");
		item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		
		item = menu.add(0, CLEAR, 0, "Clear");
		item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		item = menu.add(0, SAVE, 0, "Save");
		item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case SETTING:
				break;
			case CLEAR:
				mCustomView.clear();
				break;
			case SAVE:
				mCustomView.save();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		MenuItem item = menu.add("context_menu1");
		item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		
		item = menu.add("context_menu2");
		item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	
	
	
}
