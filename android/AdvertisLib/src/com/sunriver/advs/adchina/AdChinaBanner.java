package com.sunriver.advs.adchina;

import com.adchina.android.ads.AdManager;
import com.adchina.android.ads.api.AdBannerListener;
import com.adchina.android.ads.api.AdView;
import android.content.Context;
import android.location.LocationManager;
import android.util.AttributeSet;
import android.util.Log;

public class AdChinaBanner extends AdView implements AdBannerListener {

	private static final String TAG = AdChinaBanner.class.getSimpleName();
	private static final String AD_ID = "69327"; //Test ID
//	private static final String AD_ID = "2134693";

	
	public AdChinaBanner(Context ctx, AttributeSet attrs) {
		super(ctx, attrs);
		init(ctx);
	}

	private void init(Context ctx) {
		this.setAdBannerListener(this);
		this.setAdReferenceSize(480, 72);
		this.setAdRefreshTime(20);
		AdManager.setRelateScreenRotate(ctx, true);
		AdManager.setCanHardWare(true);
		AdManager.setEnableLbs(true);
		AdManager.setAnimation(true);
		LocationManager locationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
		AdManager.setLocationManager(locationManager);
	}

	@Override
	public void onClickBanner(AdView v) {
		Log.d(TAG, "onClickBanner()+");
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
