package com.like.douban.api;


import org.json.JSONObject;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.like.api.JsonRequest;

public class DoubanJsonRequest extends JsonRequest {

	public DoubanJsonRequest(int method, String url, JSONObject jsonRequest,
			Listener<JSONObject> listener, ErrorListener errorListener) {
		super(method, url, jsonRequest, listener, errorListener);
		// TODO Auto-generated constructor stub
	}
	
}
