package com.sunriver.advs;

import com.sunriver.advs.adchina.AdChinaIntestitial;

import android.content.Context;

public class AdvsManager {
	public static void showAdvs(Context ctx) {
		AdChinaIntestitial ad = new AdChinaIntestitial(ctx);
		ad.start();
	}

}
