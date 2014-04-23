package com.like.douban.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.like.douban.login.api.TokenResult;
import com.like.douban.login.api.TokenResult.Property;

public class LoginUtil {
	private static final String TOKEN_FILE = "token.file";
	
	public static void saveToken(Context ctx, TokenResult result) {
		SharedPreferences prefs = ctx.getSharedPreferences(TOKEN_FILE, Context.MODE_PRIVATE);
		prefs.edit().putString(Property.ACCESS_TOKEN, result.getAccessToken())
					.putString(Property.DOUBAN_USER_ID, result.getUserID())
					.putInt(Property.EXPIRES_IN, result.getExpiresIn())
					.putString(Property.REFRESH_TOKEN, result.getRefreshToken())
					.commit();
	}
	
	public static TokenResult getToken(Context ctx) {
		SharedPreferences prefs = ctx.getSharedPreferences(TOKEN_FILE, Context.MODE_PRIVATE);
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
	
	
	public static void doLogin(Activity act) {
		Intent intent = new Intent(act, LoginActivity.class);
		act.startActivity(intent);
	}
	
}
