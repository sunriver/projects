package com.coco.reader.xiyouji;

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
		String youmiApiID = getString(R.string.com_coco_reader_xiyouji_youmi_api_id);
		String youmiKey = getString(R.string.com_coco_reader_xiyouji_youmi_key);
		net.youmi.android.AdManager.getInstance(getApplicationContext()).setEnableDebugLog(true);
		YoumiAdvsManager.showAdvsOfSmartBanner(getApplicationContext(), youmiApiID, youmiKey);
		YoumiAdvsManager.showAdvsOfBanner(getApplicationContext(), youmiApiID, youmiKey, getBannerContainer());
	}
	
	private void addAdvsOfAdchina() {
		AdchinaAdvsManager.showAdvsOfIntestitial(this, getString(R.string.com_coco_reader_xiyouji_adchina_itst_id));
	}
	
	
}
