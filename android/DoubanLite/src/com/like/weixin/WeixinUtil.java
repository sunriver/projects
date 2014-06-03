package com.like.weixin;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.content.Context;

public class WeixinUtil {
	private final static String APP_ID = ""	;
	
	public static void registerAppToWX(Context ctx, final String appID) {
		IWXAPI api = WXAPIFactory.createWXAPI(ctx, appID, true);
		api.registerApp(appID);
	}
	
	public static void registerAppToWX(Context ctx) {
		registerAppToWX(ctx, APP_ID);
	}
	
}
