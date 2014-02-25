package com.sunriver.advs;

import com.sunriver.advs.adchina.AdChinaIntestitial;
import com.sunriver.advs.adchina.IAdvs;

import android.content.Context;

public class AdvsManager {
	public static void showAdvs(Context ctx, final String id) {
		AdChinaIntestitial ad = new AdChinaIntestitial(ctx);
		ad.setAdvsID(id);
		ad.start();
	}
	

}
