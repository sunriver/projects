package com.coco.reader.xiyouji;

import android.content.Context;
import android.os.Bundle;

import com.coco.reader.MainActivity;
import com.sunriver.advs.adchina.AdchinaAdvsManager;
import com.sunriver.advs.youmi.YoumiAdvsManager;

public class HomeActivity extends MainActivity {
	private static final String TAG = HomeActivity.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addAdvsOfAdchina();
		addAdvsOfYoumi();
	}
	
	private void addAdvsOfYoumi() {
		YoumiAdvsManager.setEnableTestMode(getApplicationContext(), true);
		String youmiApiID = getString(R.string.com_coco_reader_xiyouji_youmi_api_id);
		String youmiKey = getString(R.string.com_coco_reader_xiyouji_youmi_key);
		Context appCtx = this.getApplicationContext();
		YoumiAdvsManager.showAdvsOfSmartBanner(appCtx, youmiApiID, youmiKey);
		YoumiAdvsManager.showAdvsOfBanner(appCtx, youmiApiID, youmiKey, getBannerContainer());
	}
	
	private void addAdvsOfAdchina() {
		AdchinaAdvsManager.setEnableTestMode(getApplicationContext(), false);
		AdchinaAdvsManager.showAdvsOfIntestitial(this, getString(R.string.com_coco_reader_xiyouji_adchina_itst_id));
	}
	
	
}
