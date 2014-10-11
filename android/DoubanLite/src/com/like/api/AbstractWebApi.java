package com.like.api;

import org.json.JSONObject;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

@SuppressWarnings("rawtypes")
public abstract class AbstractWebApi {
	protected Context mContext;
	protected RequestQueue mRequestQueue;

	protected ResponseListener mResponseListener;
	protected SuccessListener mSuccessListener;
	protected FailureListener mFailureListener;

	private class SuccessListener implements Listener<JSONObject> {

		@SuppressWarnings("unchecked")
		@Override
		public void onResponse(JSONObject response) {
			if (null == response) {
				return;
			}
			if (mResponseListener != null) {
				mResponseListener.onSuccess(parseResponse(response));
			}
		}

	};

	private  class FailureListener implements ErrorListener {

		@Override
		public void onErrorResponse(VolleyError error) {
			ApiUtils.checkError(mContext, error);
			if (mResponseListener != null) {
				mResponseListener.onFailure();
			}
		}

	};
	
	
	public AbstractWebApi(Context ctx, RequestQueue queue, ResponseListener listener) {
		this.mContext = ctx;
		this.mRequestQueue = queue;
		this.mResponseListener = listener;
		this.mSuccessListener = new SuccessListener();
		this.mFailureListener = new FailureListener();
	}
	

	protected JsonRequest createRequest(final int method, final String url) {
		JsonRequest request = new JsonRequest(method, url, null, mSuccessListener, mFailureListener);
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
