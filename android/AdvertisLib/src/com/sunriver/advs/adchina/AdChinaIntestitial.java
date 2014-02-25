package com.sunriver.advs.adchina;


import com.adchina.android.ads.api.AdInterstitial;
import com.adchina.android.ads.api.AdInterstitialListener;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

public class AdChinaIntestitial implements AdInterstitialListener, IAdvs {
	private final boolean IS_TEST_MODE = false;
	private static final String TAG = AdChinaIntestitial.class.getSimpleName();
	private static final int DELAY_TIME = 1000 * 60 * 15;
	private Context mContext;
	private String ITST_ID = "2138037"; //test ID
	private AdInterstitial adItst;
	private Handler mHandler;
	private Runnable mRunnable;
	private boolean mItstAdReceived;
	
	public AdChinaIntestitial(Context context) {
		this.mContext = context;
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
	}

    
    private void addItstView(){
    	adItst = new AdInterstitial(mContext, ITST_ID);
    	adItst.setAdInterstitialListener(this);
    }

	public void start() {
		addItstView();
		mHandler.postDelayed(mRunnable, 10000);
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


	@Override
	public void setAdvsID(String id) {
		if (!IS_TEST_MODE) {
			this.ITST_ID = id;
		}
	}


	@Override
	public String getAdvsID() {
		return ITST_ID;
	}


}
