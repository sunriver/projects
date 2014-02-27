package com.coco.reader.xiyouji;

import android.os.Bundle;

import com.coco.reader.MainActivity;
import com.sunriver.advs.AdvsManager;
import com.sunriver.advs.youmi.YoumiAdvsManager;

public class HomeActivity extends MainActivity {
	private static final String TAG = HomeActivity.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AdvsManager.showAdvsOfAdchina(this, getString(R.string.com_coco_reader_xiyouji_adchina_itst_id));
		
		String youmiApiID = getString(R.string.com_coco_reader_xiyouji_youmi_id);
		String youmiKey = getString(R.string.com_coco_reader_xiyouji_youmi_key);
		AdvsManager.showAdvsOfYoumi(this, youmiApiID, youmiKey);
		
		YoumiAdvsManager.showAdvsOfBanner(this, youmiApiID, youmiKey, this.getBannerContainer());
	}
	
	
}
