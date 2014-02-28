package com.sunriver.advs.youmi;


import net.youmi.android.AdManager;
import net.youmi.android.smart.SmartBannerManager;

import android.content.Context;
import android.view.ViewGroup;

public class YoumiAdvsManager {
	
	public static void setTestMode(Context ctx, boolean mode) {
		AdManager.getInstance(ctx).setEnableDebugLog(mode);
	}
	
	public static void showAdvsOfSmartBanner(Context ctx, String id, String key) {
		AdManager.getInstance(ctx).init(id, key, false); 
		SmartBannerManager.init(ctx);
		SmartBannerManager.show(ctx);
	}
	
	public static void showAdvsOfBanner(Context ctx, String id, String key, ViewGroup parent) {
		AdManager.getInstance(ctx).init(id, key, false); 
		YoumiBanner banner = new YoumiBanner(ctx);
		parent.addView(banner.getBannerView());
	}



}
