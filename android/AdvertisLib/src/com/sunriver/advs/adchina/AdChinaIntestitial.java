package com.sunriver.advs.adchina;


import com.adchina.android.ads.api.AdInterstitial;
import com.adchina.android.ads.api.AdInterstitialListener;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

public class AdChinaIntestitial implements AdInterstitialListener {
	private static final String TAG = AdChinaIntestitial.class.getSimpleName();
	private Context mContext;
	private final String ITST_ID = "2138037";
	private AdInterstitial adItst;
	private Handler mHandler;
	private Runnable mRunnable;
	
	public AdChinaIntestitial(Context context) {
		this.mContext = context;
		mHandler = new Handler();
		mRunnable = new Runnable() {

				@Override
				public void run() {
					adItst.start();
					mHandler.postDelayed(this, 30000);
				}
				
			};
		addItstView();
	}

    
    private void addItstView(){
    	adItst = new AdInterstitial(mContext, ITST_ID);
    	adItst.setAdInterstitialListener(this);
    }

	public void start() {
		mHandler.postDelayed(mRunnable, 10000);
	}

    @Override
	public void onReceivedItstAd() {
    	if(adItst != null)adItst.showItst();
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
