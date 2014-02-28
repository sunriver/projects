package com.coco.reader.xiyouji;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.coco.reader.MainActivity;
import com.sunriver.advs.adchina.AdchinaAdvsManager;
import com.sunriver.advs.youmi.YoumiAdvsManager;

public class HomeActivity extends MainActivity {
	private static final String TAG = HomeActivity.class.getSimpleName();
	private Handler mHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mHandler = new Handler();
//		addAdvsOfAdchina();
		addAdvsOfYoumi();
	}
	
	private void addAdvsOfYoumi() {
		final Context appCtx = this.getApplicationContext();
		String youmiApiID = getString(R.string.com_coco_reader_xiyouji_youmi_api_id);
		String youmiKey = getString(R.string.com_coco_reader_xiyouji_youmi_key);
		YoumiAdvsManager.setEnableTestMode(appCtx, youmiApiID, youmiKey, false);
		YoumiAdvsManager.setEnableLogMode(appCtx, true);
		
//		YoumiAdvsManager.showAdvsOfSmartBanner(appCtx);
//		YoumiAdvsManager.showAdvsOfBanner(appCtx, getBannerContainer());
//		YoumiAdvsManager.showAdvsOfIntestitial(appCtx);
		Runnable r = new Runnable() {
			@Override
			public void run() {
				YoumiAdvsManager.showAdvsOfIntestitial(appCtx);
				mHandler.postDelayed(this, 1000 * 60 * 15);
			}
		};
		mHandler.post(r);
	}
	
	private void addAdvsOfAdchina() {
		AdchinaAdvsManager.setEnableTestMode(getApplicationContext(), false);
		AdchinaAdvsManager.setEnableLogMode(true);
		AdchinaAdvsManager.showAdvsOfIntestitial(this, getString(R.string.com_coco_reader_xiyouji_adchina_itst_id));
	}
	
	
}
