package com.funnyplayer.net.api;

import java.util.Map;
import java.util.Map.Entry;

import android.text.TextUtils;


public final class LastFmAPI {

	private static final String BASE_URL = "http://ws.audioscrobbler.com/2.0/?";

	private static final String API_KEY = "df7df24745b85942b9ca8d360f055615";

	private final String name;

	public LastFmAPI(final String name) {
		this.name = name;
	}

	public String toURL() {
		StringBuilder builder = new StringBuilder();
		builder.append(BASE_URL);
		builder.append("api_key=" + API_KEY);
		builder.append("&method=" + name);
		return builder.toString();
	}
	
	public  String toURL(String key, String value) {
		if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) {
			return toURL();
		}
		
		String url = toURL();
		return url + "&" + key + "=" + value;
	}
	
	public String toURL(Map<String, String> params) {
		if (null == params) {
			return toURL();
		}
		StringBuilder builder = new StringBuilder();
		builder.append(toURL());
		for (Entry<String, String> entry : params.entrySet()) {
			builder.append("&" + entry.getKey() + "=" + entry.getValue());
		}
		return builder.toString();
	}

	public String getName() {
		return name;
	}
	
	public static class Artist {
		public static final LastFmAPI GetInfo = new LastFmAPI("artist.getInfo");
	}
	
	public static class Album {
		public static final LastFmAPI GetInfo = new LastFmAPI("album.getInfo");
	}
}
