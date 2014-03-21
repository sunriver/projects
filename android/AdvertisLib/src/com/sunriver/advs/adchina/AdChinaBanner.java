package com.sunriver.advs.adchina;


import com.adchina.android.ads.AdManager;
import com.adchina.android.ads.api.AdBannerListener;
import com.adchina.android.ads.api.AdView;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class AdChinaBanner extends Fragment implements AdBannerListener {
	private static final String TAG = AdChinaBanner.class.getSimpleName();
	
	private static final String AD_ID = "2134693";
//	private static final String AD_ID = "69327"; //Test ID
	private View mAdView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mAdView = createAdView();
		return mAdView;
	}
	
	
	
	private LinearLayout createAdView() {
		Context ctx = getActivity().getApplicationContext();
		LinearLayout parent = new LinearLayout(ctx);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,  LinearLayout.LayoutParams.WRAP_CONTENT);
		parent.setLayoutParams(lp);

		AdView view = new AdView(ctx, AD_ID, true, true);
		view.setAdBannerListener(this);
		view.setAdReferenceSize(480, 72);
		parent.addView(view);

		view.setAdRefreshTime(20);
		AdManager.setRelateScreenRotate(ctx, true);
		AdManager.setCanHardWare(true);
		AdManager.setEnableLbs(true); 
		AdManager.setAnimation(true);
		LocationManager locationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
		AdManager.setLocationManager(locationManager);

		return parent;
	}



	@Override
	public void onClickBanner(AdView v) {
		Log.d(TAG, "onClickBanner()+");
//		if (mAdView != null) {
//			mAdView.setVisibility(View.INVISIBLE);
//		}
	}



	@Override
	public void onFailedToReceiveAd(AdView v) {
		Log.d(TAG, "onFailedToReceiveAd()+");
	}



	@Override
	public void onReceiveAd(AdView v) {
		Log.d(TAG, "onReceiveAd()+");
	}

}
