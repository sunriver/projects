package com.sunriver.advs.youmi;


import com.sunriver.advs.AbstractBanner;

import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.banner.AdViewListener;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class YoumiBanner extends AbstractBanner implements AdViewListener, View.OnClickListener {
	private static final String TAG = YoumiBanner.class.getSimpleName();
	private View mAdView;

	public YoumiBanner(Context ctx) {
		mContext = ctx.getApplicationContext();
		mAdView = createAdView(mContext);
	}
	
	private View createAdView(Context ctx) {
	    AdView adView = new AdView(ctx, AdSize.FIT_SCREEN);
	    adView.setAdListener(this);
	    registerClickListener(adView);
		return adView;
	}
	
	private void registerClickListener(View v) {
		if (v instanceof ViewGroup) {
			ViewGroup vg = (ViewGroup) v;
			for (int childCount = vg.getChildCount(), i = 0; i < childCount; i++) {
				View child = vg.getChildAt(i);
				registerClickListener(child);
			}
		} else if (v instanceof View) {
			v.setOnClickListener(this);
		}
	}



	@Override
	public void onFailedToReceivedAd(net.youmi.android.banner.AdView adview) {
		Log.d(TAG, "onFailedToReceivedAd()+");
	}



	@Override
	public void onReceivedAd(net.youmi.android.banner.AdView adview) {
		Log.d(TAG, "onReceivedAd()+");
		
	}



	@Override
	public void onSwitchedAd(net.youmi.android.banner.AdView adview) {
		Log.d(TAG, "onSwitchedAd()+");
	}

	@Override
	public View getBannerView() {
		return mAdView;
	}

	@Override
	public void onClick(View v) {
		mAdView.setVisibility(View.INVISIBLE);
	}

}
