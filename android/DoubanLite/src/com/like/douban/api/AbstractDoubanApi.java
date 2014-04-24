package com.like.douban.api;

import org.json.JSONObject;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

@SuppressWarnings("rawtypes")
public abstract class AbstractDoubanApi {
	protected Context mContext;
	protected RequestQueue mRequestQueue;

	protected ResponseListener mResponseListener;

	private  class WrapResponseListener implements Listener<JSONObject> {

		@SuppressWarnings("unchecked")
		@Override
		public void onResponse(JSONObject response) {
			if (null == response) {
				return;
			}
			mResponseListener.onSuccess(parseResponse(response));
		}

	};

	private  class WrapErrorListener implements ErrorListener {

		@Override
		public void onErrorResponse(VolleyError error) {
			ApiUtils.checkError(mContext, error);
			mResponseListener.onFailure();
		}

	};
	
	
	public AbstractDoubanApi(Context ctx, RequestQueue queue, ResponseListener listener) {
		this.mContext = ctx;
		this.mRequestQueue = queue;
		this.mResponseListener = listener;
	}
	

	protected DoubanJsonRequest createRequest(final int method, final String url) {
		DoubanJsonRequest request = new DoubanJsonRequest(method, url, null, new WrapResponseListener(), new WrapErrorListener());
		return request;
	}
	
	@SuppressWarnings("unchecked")
	protected void sendRequest(Request request) {
		mRequestQueue.add(request);
	}

	
	protected <T> T parseResponse(JSONObject response) {
		return null;
	}

}
