package com.like.douban.account;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.like.douban.account.bean.TokenResult;
import com.like.douban.account.bean.TokenResult.Property;

public class AccountManager {
	private static final String ACCOUNT_FILE = "account.file";
	private final static String IS_LOGINED = "logined";
	
	public static void saveToken(Context ctx, TokenResult result) {
		SharedPreferences prefs = ctx.getSharedPreferences(ACCOUNT_FILE, Context.MODE_PRIVATE);
		prefs.edit().putString(Property.ACCESS_TOKEN, result.getAccessToken())
					.putString(Property.DOUBAN_USER_ID, result.getUserID())
					.putInt(Property.EXPIRES_IN, result.getExpiresIn())
					.putString(Property.REFRESH_TOKEN, result.getRefreshToken())
					.putBoolean(IS_LOGINED, true)
					.commit();
	}
	
	public static TokenResult getToken(Context ctx) {
		SharedPreferences prefs = ctx.getSharedPreferences(ACCOUNT_FILE, Context.MODE_PRIVATE);
		TokenResult result = new TokenResult();
		String accessToken = prefs.getString(Property.ACCESS_TOKEN, "");
		result.setAccessToken(accessToken);
		
		String refreshToken = prefs.getString(Property.REFRESH_TOKEN, "");
		result.setRefreshToken(refreshToken);
		
		String userID = prefs.getString(Property.DOUBAN_USER_ID, "");
		result.setUserID(userID);
		
		int expires = prefs.getInt(Property.EXPIRES_IN, 0);
		result.setExpiresIn(expires);
		return result;
	}
	
	
	public static boolean checkAccessValidity(Context ctx) {
		TokenResult result = getToken(ctx);
		if (!TextUtils.isEmpty(result.getAccessToken())) {
			return true;
		}
		return false;
	}
	
	public static boolean isLogined(Context ctx) {
		SharedPreferences prefs = ctx.getSharedPreferences(ACCOUNT_FILE, Context.MODE_PRIVATE);
		return prefs.getBoolean(IS_LOGINED, false);
	}
	
	
	public static void doLogin(Activity act) {
		Intent intent = new Intent(act, LoginActivity.class);
		act.startActivity(intent);
	}
	
	public static String getLoginUserID(Context ctx){
		TokenResult result = getToken(ctx);
		return result.getUserID();
	}
	
	public static void logout(Context ctx) {
		SharedPreferences prefs = ctx.getSharedPreferences(ACCOUNT_FILE, Context.MODE_PRIVATE);
		prefs.edit().putBoolean(IS_LOGINED, false)
					.commit();
	}
	
}
