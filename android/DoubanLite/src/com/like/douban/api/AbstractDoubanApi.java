package com.like.douban.api;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.like.api.AbstractWebApi;
import com.like.api.ResponseListener;

@SuppressWarnings("rawtypes")
public abstract class AbstractDoubanApi extends AbstractWebApi {

	public AbstractDoubanApi(Context ctx, RequestQueue queue,
			ResponseListener listener) {
		super(ctx, queue, listener);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected DoubanJsonRequest createRequest(int method, String url) {
		DoubanJsonRequest request = new DoubanJsonRequest(method, url, null, mSuccessListener, mFailureListener);
		return request;
	}
	
}
