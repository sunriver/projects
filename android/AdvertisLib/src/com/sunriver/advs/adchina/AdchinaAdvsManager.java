package com.sunriver.advs.adchina;

import com.adchina.android.ads.AdManager;
import com.sunriver.advs.adchina.AdChinaIntestitial;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;

public class AdchinaAdvsManager {
	
	
	public static void showAdvsOfIntestitial(Activity act, String id) {
		AdChinaIntestitial ad = new AdChinaIntestitial(act, id);
		ad.start();
	}
	
	public static void setEnableTestMode(Context ctx, boolean enable) {
		AdManager.setDebugMode(enable);
		AdManager.setLogMode(enable);
		AdManager.setRelateScreenRotate(ctx, true);
		AdManager.setCanHardWare(true);
		AdManager.setEnableLbs(true); 
		AdManager.setAnimation(true);
		LocationManager locationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
		AdManager.setLocationManager(locationManager);
	}

}
