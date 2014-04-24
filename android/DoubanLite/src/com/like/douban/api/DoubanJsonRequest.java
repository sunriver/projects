package com.like.douban.api;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;

public class DoubanJsonRequest extends JsonObjectRequest {
	private Map<String, String> mHeaderMap = new HashMap<String, String>();
	private Map<String, String> mPostParamMap = new HashMap<String, String>();

	public DoubanJsonRequest(String url, JSONObject jsonRequest,
			Listener<JSONObject> listener, ErrorListener errorListener) {
		super(url, jsonRequest, listener, errorListener);
		// TODO Auto-generated constructor stub
	}

	public DoubanJsonRequest(int method, String url, JSONObject jsonRequest,
			Listener<JSONObject> listener, ErrorListener errorListener) {
		super(method, url, jsonRequest, listener, errorListener);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		return mHeaderMap;
	}

	public void putHeader(final String key, final String value) {
		mHeaderMap.put(key, value);
	}

	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		return mPostParamMap;
	}

	public void putPostParam(final String key, final String value) {
		mPostParamMap.put(key, value);
	}

}
