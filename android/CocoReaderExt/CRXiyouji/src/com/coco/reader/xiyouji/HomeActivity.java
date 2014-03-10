package com.coco.reader.xiyouji;

import android.os.Bundle;
import com.adchina.android.ads.AdManager;
import com.coco.reader.MainActivity;
import com.coco.reader.data.Document;
import com.sunriver.advs.adchina.AdChinaIntestitial;

public class HomeActivity extends MainActivity {
	private static final String TAG = HomeActivity.class.getSimpleName();
	private AdChinaIntestitial mAdChinaIntestitial;;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 initAdvsofAdchina();
	}
	
	private void initAdvsofAdchina() {
		AdManager.setDebugMode(false);
		AdManager.setLogMode(true);
		mAdChinaIntestitial = new AdChinaIntestitial(this, getString(R.string.com_coco_reader_xiyouji_adchina_itst_id));
		mAdChinaIntestitial.start();
	}

	@Override
	public void onDocumentLoadCompleted(Document doc) {
		super.onDocumentLoadCompleted(doc);
	}
	

	@Override
	protected void onStop() {
		super.onStop();
//		if (mAdChinaIntestitial != null) {
//			mAdChinaIntestitial.show();
//		}
	}
	
	

	@Override
	protected void onDestroy() {
		if (mAdChinaIntestitial != null) {
			mAdChinaIntestitial.stop();
		}
		super.onDestroy();
	}
	
}
