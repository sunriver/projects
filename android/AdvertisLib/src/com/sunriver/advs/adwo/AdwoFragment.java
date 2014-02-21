package com.sunriver.advs.adwo;

import com.adwo.adsdk.AdListener;
import com.adwo.adsdk.AdwoAdView;
import com.adwo.adsdk.ErrorCode;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;

public class AdwoFragment extends Fragment implements AdListener {
	private static final String TAG = AdwoFragment.class.getSimpleName();
	private static final boolean AD_TEST_MODE = false;
	private static final String Adwo_PID = "70e074f4698745ab9f349becac751441";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return createAdView();
	}
	

	private RelativeLayout createAdView() {
		RelativeLayout paprent = new RelativeLayout(getActivity());
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
		AdwoAdView.setBannerMatchScreenWidth(true);
		AdwoAdView adView = new AdwoAdView(getActivity(), Adwo_PID, AD_TEST_MODE, 30);
		adView.setListener(this);
		paprent.addView(adView, lp);
		return paprent;
	}


	@Override
	public void onFailedToReceiveAd(AdwoAdView arg0, ErrorCode arg1) {
		Log.e(TAG, "onFailedToReceiveAd() errorCode:" + arg1.getErrorCode());
	}


	@Override
	public void onReceiveAd(AdwoAdView arg0) {
		Log.v(TAG, "onReceiveAd()+");
	}
}
