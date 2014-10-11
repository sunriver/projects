package com.like.douban.account.api;


import org.json.JSONObject;
import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.like.douban.account.bean.TokenResult;
import com.like.douban.api.AbstractDoubanApi;
import com.like.douban.api.DoubanResponseListener;

/**
 * Post method
 * @author alu
 *
 */
public class GetAccessToken extends AbstractDoubanApi {
	private final static String TAG = GetAccessToken.class.getSimpleName();
	private final static String  POST_ACCESS_TOKEN_URL = "https://www.douban.com/service/auth2/token?client_id=${API_KEY}&client_secret=${CLIENT_SECRET}&redirect_uri=${REDIRECT_URI}&grant_type=authorization_code&code=${AUTHORIZATION_CODE}";

	private String mClientID;
	private String mClientSecret;
	private String mRedirectUri;
	private String mAuthCode;


	private GetAccessToken(Context ctx, RequestQueue queue, DoubanResponseListener listener) {
		super(ctx, queue, listener);
	}

	@SuppressWarnings("rawtypes")
	public void query() {
		Request request = this.createRequest(Request.Method.POST, getAccessTokenUrl());
		this.sendRequest(request);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	protected TokenResult parseResponse(JSONObject response) {
		TokenResult tokenResult = TokenResult.fromJSONObject(response);
		return tokenResult;
	}


	private  String getAccessTokenUrl() {
		String url = POST_ACCESS_TOKEN_URL.replace("${API_KEY}", mClientID);
		url = url.replace("${CLIENT_SECRET}", mClientSecret);
		url = url.replace("${REDIRECT_URI}", mRedirectUri);
		url = url.replace("${AUTHORIZATION_CODE}", mAuthCode);
		return url;
	}
	
	
	@SuppressWarnings("rawtypes")
	public static class Builder {
		private  Context context;
		private  RequestQueue queue;
		private DoubanResponseListener listener;
		private  String clientID;
		private  String clientSecret;
		private  String redirectUri;
		private  String authCode;
		
		public Builder(Context ctx, RequestQueue queue, DoubanResponseListener listener) {
			this.context = ctx;
			this.queue = queue;
			this.listener = listener;
		}
		
		public Builder setClientID(final String clientID) {
			this.clientID = clientID;
			return this;
		}
		
		public Builder setClicentSecret(final String clientSecret) {
			this.clientSecret = clientSecret;
			return this;
		}
		
		public Builder setRedirectUri(final String redirectUri) {
			this.redirectUri = redirectUri;
			return this;
		}
		
		public Builder setAuthCode(final String authCode) {
			this.authCode = authCode;
			return this;
		}

		
		public GetAccessToken build() {
			GetAccessToken api = new GetAccessToken(context, queue, listener);
			api.mClientID = clientID;
			api.mClientSecret = clientSecret;
			api.mRedirectUri = redirectUri;
			api.mAuthCode = authCode;
			return api;
		}
	}

}
