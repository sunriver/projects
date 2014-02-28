package com.sunriver.advs.adchina;

import com.sunriver.advs.adchina.AdChinaIntestitial;

import android.app.Activity;
import android.content.Context;

public class AdchinaAdvsManager {
	
	
	public static void showAdvsOfIntestitial(Activity act, String id) {
		AdChinaIntestitial ad = new AdChinaIntestitial(act, id);
		ad.start();
	}
	


}
