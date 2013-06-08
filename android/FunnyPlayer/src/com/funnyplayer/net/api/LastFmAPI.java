package com.funnyplayer.net.api;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import android.text.TextUtils;
import android.util.Log;


public  class LastFmAPI <T> {
	
	public static enum HttpMethod {
		GET, POST;
	}
	
	protected static final String TAG = "LastFmAPI";
	
	private static final String BASE_URL = "http://ws.audioscrobbler.com/2.0/?";

	private static final String API_KEY = "df7df24745b85942b9ca8d360f055615";
	
	private final String name;
	
	protected final HttpMethod mMethod;
	
	protected T mResult;
	
	private Map<String, String> mParamMap;

	public LastFmAPI(final String name, final HttpMethod method) {
		this.name = name;
		this.mMethod = method;
		this.mParamMap = new HashMap<String, String>();
	}
	
	public void handleResponse(InputStream in) {
		try {
			if (null == in) {
				return;
			}
			Result result= XmlParser.createResultFromInputStream(in);
			onHandleResponse(result);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}
	
	protected void onHandleResponse(Result result) {
		
	}
	
	/**
	 * Must be override
	 * @return
	 */
	protected HttpRequestBase onCreateHttpRequestInited() {
		return new HttpGet(toURL());
	}
	
	public HttpRequestBase createHttpRequest() {
		HttpRequestBase request = onCreateHttpRequestInited();
		onCreateHttpRequestFinished(request);
		return request;
	}
	
	protected void onCreateHttpRequestFinished(HttpRequestBase request) {
		if (request != null) {
			request.setHeader("User-Agent", "listen");
			request.setHeader("Content-Type", "application/x-www-form-urlencoded");
		}
	}
	
	
	public String toURL() {
		StringBuilder builder = new StringBuilder();
		builder.append(BASE_URL);
		builder.append("api_key=" + API_KEY);
		builder.append("&method=" + name);
		for (Entry<String, String> entry : mParamMap.entrySet()) {
			builder.append("&" + entry.getKey() + "=" + entry.getValue());
		}
		return builder.toString();
	}
	
	public  void setParamter(String key, String value) {
		if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) {
		}
		mParamMap.put(key, value);
	}
	
	public void setParamter(Map<String, String> params) {
		if (null == params) {
			return ;
		}
		for (Entry<String, String> entry : params.entrySet()) {
			mParamMap.put(entry.getKey(), entry.getValue());
		}
	}

	public String getName() {
		return name;
	}
	
	public T getResult() {
		return mResult;
	}
}
