package com.sunriver.advs.youmi;


import net.youmi.android.AdManager;
import net.youmi.android.smart.SmartBannerManager;

import android.content.Context;
import android.view.ViewGroup;

public class YoumiAdvsManager {
	
	public static void setEnableTestMode(Context ctx, String apiID, String key, boolean enable) {
		AdManager adManager = AdManager.getInstance(ctx);
		adManager.init(apiID, key, enable);

	}
	
	public static void setEnableLogMode(Context ctx, boolean enable) {
		AdManager.getInstance(ctx).setEnableDebugLog(enable);
	}
	
	
	public static void showAdvsOfSmartBanner(Context ctx) {
		SmartBannerManager.show(ctx);
	}
	
	public static void showAdvsOfBanner(Context ctx, ViewGroup parent) {
		YoumiBanner banner = new YoumiBanner(ctx);
		parent.addView(banner.getBannerView());
	}



}
