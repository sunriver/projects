package com.oobe.loadbitmap;

import java.util.ArrayList;
import java.util.HashMap;

import com.oobe.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

public class DanamicLoadBitmapActivity extends Activity {
	
	private GridView mGridView;
	
	private BitmapAdapter mAdapter ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitle("Load icon from internet lazily!");
		setContentView(R.layout.home);
		mGridView = (GridView) findViewById(R.id.home_gridview);
		mGridView.setAdapter(getAdapter());
	}

    private  BitmapAdapter getAdapter() {
    	if (this.mAdapter != null) {
    		return this.mAdapter;
    	}
		ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> image1 = new HashMap<String, String>();
//		image1.put("name", "image1");
		image1.put("name", "http://upload.wikimedia.org/wikipedia/commons/thumb/7/72/Torchlight_help_icon.svg/128px-Torchlight_help_icon.svg.png");
		image1.put("icon", "http://upload.wikimedia.org/wikipedia/commons/thumb/7/72/Torchlight_help_icon.svg/128px-Torchlight_help_icon.svg.png");
		data.add(image1);
		
		HashMap<String, String> image2 = new HashMap<String, String>();
//		image2.put("name", "image2");
		image2.put("name", "http://www.tompda.com/images/uploads/2011_10_09/20_58_52_653.png");
		image2.put("icon", "http://www.tompda.com/images/uploads/2011_10_09/20_58_52_653.png");
		data.add(image2);
		
		HashMap<String, String> image3 = new HashMap<String, String>();
//		image3.put("name", "image3");
		image3.put("name", "http://guides.webbynode.com/articles/security/images/google-authenticator-logo.png");
		image3.put("icon", "http://guides.webbynode.com/articles/security/images/google-authenticator-logo.png");
		data.add(image3);
		mAdapter = new BitmapAdapter(this, data, R.layout.home_item, 
				new String[]{"name", "icon"}, 
				new int[]{R.id.home_item_text, R.id.home_item_image});
		return mAdapter;
    }
	

}
