package com.like.weixin;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXTextObject;
import com.tencent.mm.sdk.openapi.WXWebpageObject;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

public class WeixinUtil {
	public final static String APP_ID = "wx9ffcd7d57299099c";
	private final static String TAG = WeixinUtil.class.getSimpleName();
	
	public static IWXAPI registerAppToWX(Context ctx) {
		IWXAPI api = WXAPIFactory.createWXAPI(ctx, APP_ID, true);
		api.registerApp(APP_ID);
		return api;
	}
	
	
	public static void shareWXFriends(IWXAPI api, String text) {
		WXTextObject textObj = new WXTextObject();
		textObj.text = text;

		WXMediaMessage msg = new WXMediaMessage();
		msg.mediaObject = textObj;
		msg.description = text;
		
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("img");
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneTimeline;
		api.sendReq(req);
	}
	
	public static void shareToWeixinFriends(final IWXAPI wxApi, final String webUrl, final String title, final String description, final Bitmap thumbBitmap, final boolean isTimeLine ) {
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl =  webUrl;
		WXMediaMessage msg = new WXMediaMessage(webpage);
		try {
			float ratio = 0.5f;
			int width = (int) (thumbBitmap.getWidth() * ratio);
			int height = (int) (thumbBitmap.getHeight() * ratio);
			Bitmap tmpThumb = Bitmap.createScaledBitmap(thumbBitmap, width, height, true);
			msg.setThumbImage(tmpThumb);
			tmpThumb.recycle();
		} catch (Exception any) {
			Log.w(TAG, any);
		}
		msg.description = description;
		msg.title = title;

		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = String.valueOf(System.currentTimeMillis());
		req.message = msg;
		req.scene = isTimeLine ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
		wxApi.sendReq(req);
	}
	
	private static String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
	}

}
