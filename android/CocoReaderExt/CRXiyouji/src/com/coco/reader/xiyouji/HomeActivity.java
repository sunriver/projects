package com.coco.reader.xiyouji;

import android.os.Bundle;

import com.coco.reader.MainActivity;
import com.sunriver.advs.AdvsManager;

public class HomeActivity extends MainActivity {
	private static final String TAG = HomeActivity.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AdvsManager.showAdvs(this, getString(R.string.com_coco_reader_xiyouji_itst_id));
	}
	
	
}
