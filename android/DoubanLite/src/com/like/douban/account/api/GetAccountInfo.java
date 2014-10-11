package com.like.douban.account.api;


import org.json.JSONObject;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.like.douban.account.bean.TokenResult;
import com.like.douban.account.bean.User;
import com.like.douban.api.AbstractDoubanApi;
import com.like.douban.api.Consts;
import com.like.douban.api.DoubanJsonRequest;
import com.like.douban.api.DoubanResponseListener;
import com.like.douban.event.api.GetEvents;
import com.like.douban.event.bean.EventList;

/**
 * GET method
 * @author alu
 *
 */
public class GetAccountInfo extends AbstractDoubanApi {
	private final static String TAG = GetAccountInfo.class.getSimpleName();
	private final static String BASE_URL = "https://api.douban.com/v2/user/~me";


	public GetAccountInfo(Context ctx, RequestQueue queue, DoubanResponseListener listener) {
		super(ctx, queue, listener);
	}

	public void query(final String accessToken) {
		DoubanJsonRequest request = createRequest(Request.Method.GET, BASE_URL);
		request.putHeader("Authorization", "Bearer " + accessToken);
		sendRequest(request);
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public User parseResponse(JSONObject response) {
		Log.d(TAG, "parseResponse()+");
		User user = User.fromJSONObject(response);
		Log.d(TAG, "parseResponse() user=" + user.toString());
		return user;
	}

}
