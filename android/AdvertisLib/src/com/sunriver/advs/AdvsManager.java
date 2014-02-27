package com.sunriver.advs;


import net.youmi.android.smart.SmartBannerManager;

import com.sunriver.advs.adchina.AdChinaIntestitial;

import android.content.Context;

public class AdvsManager {
	
	public static void showAdvsOfAdchina(Context ctx, final String id) {
		AdChinaIntestitial ad = new AdChinaIntestitial(ctx);
		ad.setAdvsID(id);
		ad.start();
	}
	
	public static void showAdvsOfYoumi(Context ctx, String id, String key) {
		net.youmi.android.AdManager.getInstance(ctx).init(id, key, false); 
		SmartBannerManager.init(ctx);
		SmartBannerManager.show(ctx);
	}


}
