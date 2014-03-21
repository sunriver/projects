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
	private AdView mAdView;
	private ViewGroup mParent;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mParent = new LinearLayout(this.getActivity());
		show(AD_ID);
		return mParent;
	}
	
	public void show(String adsID) {
		if (null == mAdView) {
			mAdView = createAdView(AD_ID);
			mParent.addView(mAdView);
			mAdView.start();
		}
	}
	
	
	
	@Override
	public void onDestroyView() {
		if (mAdView != null) {
			mAdView.stop();
			mAdView = null;
		}
		super.onDestroyView();
	}

	private AdView createAdView(final String adsID) {
		Context ctx = getActivity().getApplicationContext();
		AdView view = new AdView(ctx, AD_ID, false, true); 
		view.setAdBannerListener(this);
		view.setAdReferenceSize(480, 72);
		view.setAdRefreshTime(20);
		AdManager.setRelateScreenRotate(ctx, true);
		AdManager.setCanHardWare(true);
		AdManager.setEnableLbs(true); 
		AdManager.setAnimation(true);
		LocationManager locationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
		AdManager.setLocationManager(locationManager);

		return view;
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
