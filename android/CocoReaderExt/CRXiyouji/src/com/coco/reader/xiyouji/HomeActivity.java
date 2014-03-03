package com.coco.reader.xiyouji;

import android.os.Bundle;
import android.os.Handler;
import com.adchina.android.ads.AdManager;
import com.adchina.android.ads.api.AdInterstitial;
import com.coco.reader.MainActivity;
import com.coco.reader.data.Document;

public class HomeActivity extends MainActivity {
	private static final String TAG = HomeActivity.class.getSimpleName();
	private Handler mHandler;
	private Runnable mAdvsRunnable;
	private AdInterstitial adItst;;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mHandler = new Handler();
		initAdvsofAdchina();
	}
	
	private void initAdvsofAdchina() {
		AdManager.setDebugMode(false);
		AdManager.setLogMode(true);
		adItst = new AdInterstitial(this, getString(R.string.com_coco_reader_xiyouji_adchina_itst_id));
		adItst.start();
		mAdvsRunnable = new Runnable() {
		@Override
		public void run() {
			adItst.showItst();
			mHandler.postDelayed(this, 1000 * 20);
		}
		};
		mHandler.post(mAdvsRunnable);
	}

	@Override
	public void onDocumentLoadCompleted(Document doc) {
		adItst.showItst();
		super.onDocumentLoadCompleted(doc);
	}

	@Override
	protected void onDestroy() {
		if (mAdvsRunnable != null) {
			mHandler.removeCallbacks(mAdvsRunnable);
		}
		if (adItst != null) {
			adItst.stop();
		}
		super.onDestroy();
	}
	
}
