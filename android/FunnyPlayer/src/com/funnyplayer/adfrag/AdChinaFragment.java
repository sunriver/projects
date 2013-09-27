package com.funnyplayer.adfrag;


import com.adchina.android.ads.AdManager;
import com.adchina.android.ads.views.AdView;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class AdChinaFragment extends Fragment  {
	private static final String TAG = AdChinaFragment.class.getSimpleName();
	
	private static final String AD_ID = "2134693";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return createAdView();
	}
	
	
	
	private LinearLayout createAdView() {
		LinearLayout parent = new LinearLayout(getActivity());
		Context ctx = getActivity().getApplicationContext();

		AdView view = new AdView(ctx, AD_ID, true, true);
//		view.setAdReferenceSize(480, 72);
		parent.addView(view);

		AdManager.setRefershinterval(20);
		AdManager.setmVideoPlayer(true);
		AdManager.setRelateScreenRotate(ctx, true);
		return parent;
	}

}
