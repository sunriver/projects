package com.sunriver.advs.youmi;


import net.youmi.android.smart.SmartBannerManager;

import com.sunriver.advs.adchina.AdChinaIntestitial;

import android.content.Context;
import android.view.ViewGroup;

public class YoumiAdvsManager {
	
	
	public static void showAdvsOfSmartBanner(Context ctx, String id, String key) {
		net.youmi.android.AdManager.getInstance(ctx).init(id, key, false); 
		SmartBannerManager.init(ctx);
		SmartBannerManager.show(ctx);
	}
	
	public static void showAdvsOfBanner(Context ctx, String id, String key, ViewGroup parent) {
		net.youmi.android.AdManager.getInstance(ctx).init(id, key, false); 
		YoumiBanner banner = new YoumiBanner(ctx);
		parent.addView(banner.getBannerView());
	}



}
