package com.funnyplayer.net.api.geci;

import java.io.InputStream;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.json.JSONObject;
import com.funnyplayer.net.api.JsonParser;
import android.util.Log;


public  class GeciAPI <T> {
	
	protected static final boolean DEBUG = true;
	
	protected static final String TAG = "GeciAPI";
	
	private static final String BASE_URL = "http://geci.me/api/lyric";

	private final String name;
	
	protected T mResult;
	

	public GeciAPI(final String name) {
		this.name = name;
	}
	
	public void handleResponse(InputStream in) {
		try {
			if (null == in) {
				return;
			}
			JSONObject jsonObject = JsonParser.parse(in);
			onHandleResponse(jsonObject);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}
	
	protected void onHandleResponse(JSONObject jsonObject) {
		
	}
	
	/**
	 * Must be override
	 * @return
	 */
	protected HttpRequestBase onCreateHttpRequestInited() {
		return new HttpPost(toURL());
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
		return BASE_URL;
	}

	public String getName() {
		return name;
	}
	
	public T getResult() {
		return mResult;
	}
}
