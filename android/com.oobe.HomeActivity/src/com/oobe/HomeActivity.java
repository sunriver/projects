package com.oobe;


import java.util.ArrayList;
import java.util.HashMap;

import com.oobe.animation.AnimationActivity;
import com.oobe.bar.HotBarActivity;
import com.oobe.bar.ProgressbarActivity;
import com.oobe.bill.BillActivity;
import com.oobe.bitmap.BitmapActivity;
import com.oobe.common.OOBEConst;
import com.oobe.fragment.FragmentActivity;
import com.oobe.key.KeyActivity;
import com.oobe.loadbitmap.DanamicLoadBitmapActivity;
import com.oobe.opengl.OpenGlActivity;
import com.oobe.palette.PaletteActivity;
import com.oobe.shortcut.ShortCutActivity;
import com.oobe.store.StoreActivity;
import com.oobe.tab.OOBETabActivity;
import com.oobe.touch.TouchActivity;
import com.oobe.video.VideoPlayerActivity;
import com.oobe.webservice.WebserviceActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;


public class HomeActivity extends Activity {
	
	private GridView mGridView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		mGridView = (GridView) this.findViewById(R.id.home_gridview);
		init();
	}
	
	private void init() {
		ArrayList<HashMap<String, Object>> dataArray = new ArrayList<HashMap<String, Object>>();
		
		for (int i = 0, len = OOBEConst.ACTIVITY_NAME.length; i < len; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("icon", R.drawable.icon);
			map.put("name", OOBEConst.ACTIVITY_NAME[i]);
			dataArray.add(map);
		}
		SimpleAdapter adapter = new SimpleAdapter(this, dataArray, R.layout.home_item, 
				                                  new String[] {"icon", "name"}, 
				                                  new int[]{R.id.home_item_image, R.id.home_item_text});
		
		mGridView.setAdapter(adapter);
		
		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.v("HomeActivity", "onItemClick+: position=" + position + ", id=" + id);
				lanuchActivity(position, id);
//				lanuchActivity(position);

			}});
	}
	
	private void lanuchActivity(int position) {
		String className = OOBEConst.ACTIVITY_NAME[position];
		Class<?> classEntity;
		try {
			classEntity = Class.forName(className);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			classEntity = MainActivity.class;
		}
		
		startActivity(new Intent(HomeActivity.this, classEntity));
	}
	
	private void lanuchActivity(int position, long id) {
		Intent intent = null;
		switch (position) {
		
		case 0:
			intent = new Intent(this, KeyActivity.class);
			break;
		case 1:
			intent = new Intent(this, BitmapActivity.class);
			break;
		case 2:
			intent = new Intent(this, StartActivity.class);
			break;
		case 3:
			intent = new Intent(this, ReceiveActivity.class);
			break;
		case 4:
			intent = new Intent(this, PaletteActivity.class);
			break;
		case 5:
			intent = new Intent(this, AnimationActivity.class);
			break;
		case 6:
			intent = new Intent(this, OOBETabActivity.class);
			break;
		case 7:
			intent = new Intent(this, MainActivity.class);
			break;
		case 8:
			intent = new Intent(this, FragmentActivity.class);
			break;
		case 9:
			intent = new Intent(this, ShortCutActivity.class);
			break;
		case 10:
			intent = new Intent(this, DanamicLoadBitmapActivity.class);
			break;
		case 11:
			intent = new Intent(this, VideoPlayerActivity.class);
			break;
		case 12:
			intent = new Intent(this, StoreActivity.class);
			break;
		case 13:
			intent = new Intent(this, TouchActivity.class);
			break;
		case 14:
			intent = new Intent(this, BillActivity.class);
			break;
		case 15:
			intent = new Intent(this, WebserviceActivity.class);
			//come into play with taskAffinite in manifest.xml
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			break;	
		case 16:
			//test intent flag, lanuchMode, taskAffinity
			//1. test intent flag
			intent = new Intent("android.intent.action.image.show");
//			intent.addCategory("android.intent.category.ImageTest");
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			break;
		case 17:
			intent = new Intent(this, ProgressbarActivity.class);
			break;
		case 18:
			intent = new Intent(this, OpenGlActivity.class);
			break;
		case 19:
			intent = new Intent(this, HotBarActivity.class);
			break;
		default:
			intent = new Intent(this, MainActivity.class);	
		}
		startActivity(intent);
	}

}
