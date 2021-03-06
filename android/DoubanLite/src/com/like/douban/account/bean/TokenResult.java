package com.like.douban.account.bean;

import org.json.JSONObject;

import android.util.Log;

public class TokenResult {
	private static final String TAG = TokenResult.class.getSimpleName();
	
	public interface Property {
		public static final String ACCESS_TOKEN = "access_token";
		public static final String EXPIRES_IN = "expires_in";
		public static final String REFRESH_TOKEN = "refresh_token";
		public static final String DOUBAN_USER_ID = "douban_user_id";
	}

	private String access_token;
	private int expires_in;
	private String refresh_token;
	private String douban_user_id;
	
	public String getAccessToken() {
		return access_token;
	}
	
	public void setAccessToken(final String token) {
		this.access_token = token;
	}
	
	public int getExpiresIn() {
		return expires_in;
	}
	
	public void setExpiresIn(final int expires) {
		this.expires_in = expires;
	}
	
	public String getRefreshToken() {
		return refresh_token;
	}
	
	public void setRefreshToken(final String token) {
		this.refresh_token = token;
	}
	
	public String getUserID() {
		return douban_user_id;
	}
	
	public void setUserID(final String userID) {
		this.douban_user_id = userID;
	}
	
	public static TokenResult fromJSONObject(JSONObject obj) {
		TokenResult result = new TokenResult();
		try {
			if (obj.has(Property.ACCESS_TOKEN)) {
				result.access_token = obj.getString(Property.ACCESS_TOKEN);
			}
			if (obj.has(Property.EXPIRES_IN)) {
				result.expires_in = obj.getInt(Property.EXPIRES_IN);
			}
			if (obj.has(Property.REFRESH_TOKEN)) {
				result.refresh_token = obj.getString(Property.REFRESH_TOKEN);
			}
			if (obj.has(Property.DOUBAN_USER_ID)) {
				result.douban_user_id = obj.getString(Property.DOUBAN_USER_ID);
			}

		} catch (Exception any) {
			Log.w(TAG, "Fail to parse json object", any);
		}
		return result;

	}

}
