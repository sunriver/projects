package com.like.douban.login.api;


import org.json.JSONObject;
import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.like.douban.ApiUtils;


public class GetAccessToken {
	private final static String TAG = GetAccessToken.class.getSimpleName();
	private final static String  POST_ACCESS_TOKEN_URL = "https://www.douban.com/service/auth2/token?client_id=${API_KEY}&client_secret=${CLIENT_SECRET}&redirect_uri=${REDIRECT_URI}&grant_type=authorization_code&code=${AUTHORIZATION_CODE}";

	private Context mContext;
	private RequestQueue mRequestQueue;
	private String mClientID;
	private String mClientSecret;
	private String mRedirectUri;
	private String mAuthCode;
	private OnTokenRequestListener mOnTokenRequestListener;
	
	
	public interface OnTokenRequestListener {
		public void onSuccess(final TokenResult result);
		public void onFailure();
	}
	

	private  class GetTokenListener implements Listener<JSONObject> {

		@Override
		public void onResponse(JSONObject response) {
			if (null == response) {
				return;
			}
			TokenResult tokenResult = TokenResult.fromJSONObject(response);
			if (mOnTokenRequestListener != null) {
				mOnTokenRequestListener.onSuccess(tokenResult);
			}
//			mFragment.updateEvents(eventList);
			Log.d(TAG, "onResponse()-");
		}

	};

	private  class GetTokensErrorListener implements ErrorListener {

		@Override
		public void onErrorResponse(VolleyError error) {
			ApiUtils.checkError(mContext, error);
			if (mOnTokenRequestListener != null) {
				mOnTokenRequestListener.onFailure();
			}
		}

	};
	


	public GetAccessToken(Context ctx, RequestQueue queue) {
		this.mContext = ctx;
		this.mRequestQueue = queue;
	}
	
	
	public void setTokenRequestListener(OnTokenRequestListener listener) {
		this.mOnTokenRequestListener = listener;
	}

	public void query() {
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Request.Method.POST, getAccessTokenUrl(), null, new GetTokenListener(),
				new GetTokensErrorListener());
		mRequestQueue.add(jsonObjectRequest);
	}
	

	
	
	
	/**
	 * Post Method
	 * @return
	 */
	private  String getAccessTokenUrl() {
		String url = POST_ACCESS_TOKEN_URL.replace("${API_KEY}", mClientID);
		url = url.replace("${CLIENT_SECRET}", mClientSecret);
		url = url.replace("${REDIRECT_URI}", mRedirectUri);
		url = url.replace("${AUTHORIZATION_CODE}", mAuthCode);
		return url;
	}
	
	
	public static class Builder {
		private  Context context;
		private  RequestQueue queue;
		private  String clientID;
		private  String clientSecret;
		private  String redirectUri;
		private  String authCode;
		
		public Builder(Context ctx, RequestQueue queue) {
			this.context = ctx;
			this.queue = queue;
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
			GetAccessToken api = new GetAccessToken(context, queue);
			api.mClientID = clientID;
			api.mClientSecret = clientSecret;
			api.mRedirectUri = redirectUri;
			api.mAuthCode = authCode;
			return api;
		}
	}

}
