package com.funnyplayer.net.api.geci;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class LrcAPI extends GeciAPI<String> {
	
	private String mArtist;
	
	private String mSong;

	public LrcAPI() {
		super("Lrc");
	}

	/**
	 * Get lyrc url from json object
	 */
	@Override
	protected void onHandleResponse(JSONObject jsonObject) {
		try {
			int count = jsonObject.getInt("count");
			int code = jsonObject.getInt("code");
			JSONArray jsonArray = jsonObject.getJSONArray("result");
			if (count > 0) {
				JSONObject lyricObject = (org.json.JSONObject) jsonArray.opt(0);
				mResult = lyricObject.getString("lrc");
			}
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage());
		}
	}
	
	public void setArtistName(final String artist) {
		mArtist = artist;
	}
	
	public void setSongName (final String song) {
		mSong = song;
	}

	@Override
	public String toURL() {
//		try {
			String url = super.toURL();
			if (mArtist != null) {
				url += ("/" + mArtist);
			}
			if (mSong != null) {
				url += ("/" + mSong);
			}
			if (DEBUG) {
				Log.v(TAG, url);
			}
			return url;
//			return URLEncoder.encode(url, "utf-8");
//		} catch (UnsupportedEncodingException e) {
//			Log.e(TAG, e.getMessage());
//		}
//		return null;
	}

	@Override
	protected HttpRequestBase onCreateHttpRequestInited() {
		final String url = toURL();
		if (null == url) {
			return null;
		}
		HttpGet request = new HttpGet(url);
		return request;
	}
	

}
