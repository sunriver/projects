package com.funnyplayer.net.api.geci;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class LyricAPI extends GeciAPI<String> {
	
	private String mArtist;
	
	private String mSong;

	public LyricAPI(String name) {
		super(name);
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
		try {
			String url = super.toURL();
			if (mSong != null) {
				url += ("/" + mSong);
			}
			if (mArtist != null) {
				url += ("/" + mArtist);
			}
			if (DEBUG) {
				Log.v(TAG, url);
			}
			return URLEncoder.encode(url, "utf-8");
		} catch (UnsupportedEncodingException e) {
			Log.e(TAG, e.getMessage());
		}
		return null;
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
