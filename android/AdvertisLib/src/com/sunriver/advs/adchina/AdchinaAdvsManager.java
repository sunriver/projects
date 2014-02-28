package com.sunriver.advs.adchina;

import com.sunriver.advs.adchina.AdChinaIntestitial;
import android.content.Context;

public class AdchinaAdvsManager {
	
	
	public static void showAdvsOfIntestitial(Context ctx, String id) {
		AdChinaIntestitial ad = new AdChinaIntestitial(ctx, id);
		ad.start();
	}
	


}
