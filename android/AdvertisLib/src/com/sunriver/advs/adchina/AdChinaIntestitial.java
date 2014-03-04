package com.sunriver.advs.adchina;


import com.adchina.android.ads.api.AdInterstitial;
import com.adchina.android.ads.api.AdInterstitialListener;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

public class AdChinaIntestitial implements AdInterstitialListener{
	private static final String TAG = AdChinaIntestitial.class.getSimpleName();
	private static final int DELAY_TIME = 1000 * 60 * 15;
	private String ITST_ID = "2138037"; //test ID
	private AdInterstitial adItst;
	private Handler mHandler;
	private Runnable mRunnable;
	private boolean mItstAdReceived;
	
	public AdChinaIntestitial(Activity act, String id) {
		this.ITST_ID = id;
		mHandler = new Handler();
		mItstAdReceived = false;
		mRunnable = new Runnable() {

				@Override
				public void run() {
					try {
						if (mItstAdReceived) {
							adItst.showItst();
						} else {
							adItst.start();
						}
						mHandler.postDelayed(this, DELAY_TIME);
					} catch (Throwable any) {
						Log.d(TAG, "Error when show intert tial", any);
					}
				}
				
			};
			addItstView(act);
	}

    
    private void addItstView(Activity act){
    	adItst = new AdInterstitial(act, ITST_ID);
    	adItst.setAdInterstitialListener(this);
    }

	public void start() {
		mHandler.postDelayed(mRunnable, 10000);
	}
	
	public void stop() {
		if (mRunnable != null) {
			mHandler.removeCallbacks(mRunnable);
		}
		if (adItst != null) {
			adItst.stop();
		}
	}
	
	public void show() {
    	if(adItst != null) {
    		adItst.showItst();
    	}
	}

    @Override
	public void onReceivedItstAd() {
    	mItstAdReceived = true;
    	if(adItst != null) {
    		adItst.showItst();
    	}
    	Log.d(TAG, "onItstReceived()+");
	}
    
	@Override
	public void onClickItst() {
		Log.d(TAG, "onClickItst()+");
		
	}

	@Override
	public void onCloseItst() {
		Log.d(TAG, "onCloseItst()+");
		
	}

	@Override
	public void onDisplayItst() {
		Log.d(TAG, "onDisplayItst()+");
		
	}

	@Override
	public void onFailedToReceiveItstAd() {
		Log.d(TAG, "onFailedToReceiveItstAd()+");
	}

}
