package com.funnyplayer.net.api.geci;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.funnyplayer.net.api.geci.bean.LrcBean;

import android.text.TextUtils;
import android.util.Log;

public class LrcAPI extends GeciAPI<LrcBean> {
	
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
			if (null == jsonObject) {
				return;
			}
			mResult = new LrcBean();
			int count = jsonObject.getInt("count");
			mResult.setCount(count);
			
			int code = jsonObject.getInt("code");
			mResult.setCode(code);
			
			List<LrcBean.LrcUrl> urls = new ArrayList<LrcBean.LrcUrl>();
			JSONArray jsonArray = jsonObject.getJSONArray("result");
			for (int i = 0 ;i < count; i++) {
				JSONObject lrcObject = (org.json.JSONObject) jsonArray.opt(i);
				LrcBean.LrcUrl lrcUrl = new LrcBean.LrcUrl();
				if (lrcObject.has("lrc")) {
					String url = lrcObject.getString("lrc");
					lrcUrl.setLrc(url);
				}
				if (lrcObject.has("song")) {
					String song = lrcObject.getString("song");
					lrcUrl.setSong(song);
				}
				if (lrcObject.has("artist")) {
					String artist = lrcObject.getString("artist");
					lrcUrl.setArtist(artist);
				}
				if (lrcObject.has("sid")) {
					int sid = lrcObject.getInt("sid");
					lrcUrl.setSid(sid);
				}
				if (lrcObject.has("aid")) {
					int aid = lrcObject.getInt("aid");
					lrcUrl.setAid(aid);
				}
				urls.add(lrcUrl);
			}
			mResult.setResult(urls);

		} catch (JSONException e) {
			Log.e(TAG, "fail to parse json string", e);
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
			if (!TextUtils.isEmpty(mArtist)) {
				url += ("/" + URLEncoder.encode(mArtist));
			}
			if (!TextUtils.isEmpty(mSong)) {
				url += ("/" + URLEncoder.encode(mSong));
			}
			if (DEBUG) {
				Log.v(TAG, url);
			}
			return url;
		} catch (Exception e) {
			Log.e(TAG, "fail to form url", e);
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
