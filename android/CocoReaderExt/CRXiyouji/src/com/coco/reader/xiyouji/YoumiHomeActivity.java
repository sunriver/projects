package com.coco.reader.xiyouji;

import net.youmi.android.offers.OffersManager;
import android.os.Bundle;
import android.os.Handler;

import com.coco.reader.MainActivity;
import com.coco.reader.data.Document;
import com.sunriver.advs.youmi.YoumiAdvsManager;

public class YoumiHomeActivity extends MainActivity {
	private static final String TAG = YoumiHomeActivity.class.getSimpleName();
	private Handler mHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final String apiID = this.getString(R.string.com_coco_reader_xiyouji_youmi_api_id);
		final String apiKey = this.getString(R.string.com_coco_reader_xiyouji_youmi_key);
		YoumiAdvsManager.setEnableTestMode(this, apiID, apiKey, false);
		OffersManager.getInstance(this).onAppLaunch(); 
		mHandler = new Handler();
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				OffersManager.getInstance(YoumiHomeActivity.this).showOffersWall();
			}
		}, 10000);
	}
	
	


	@Override
	public void onDocumentLoadCompleted(Document doc) {
		super.onDocumentLoadCompleted(doc);
	}
	

	@Override
	protected void onStop() {
		super.onStop();

	}
	
	

	@Override
	protected void onDestroy() {
		OffersManager.getInstance(this).onAppExit();
		super.onDestroy();
	}
	
}
