package com.like.douban.login;

import com.android.volley.RequestQueue;
import com.like.MyApplication;
import com.like.R;
import com.like.douban.login.api.GetAccessToken;
import com.like.douban.login.api.GetAccessToken.OnTokenRequestListener;
import com.like.douban.login.api.TokenResult;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.HttpAuthHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;

public class LoginActivity extends ActionBarActivity implements OnTokenRequestListener {
	private final static String TAG = LoginActivity.class.getSimpleName();
	private final static String  GET_AUTHORIZATION_CODE_URL = "https://www.douban.com/service/auth2/auth?client_id=${API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code&scope=shuo_basic_r,shuo_basic_w,douban_basic_common";
	private final static String API_KEY = "0feda5e1f219879d2fc8f1a5d64c96d7";
	private final static String CLIENT_SECRET = "24e8af830d5eb113";
	private final static String REDIRECT_URI = "http://www.douban.com/callback";
	private WebView mLoginWv;
	private RequestQueue mRequestQueue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_login);
		init();
	}
	
	private void requestAccessToken(final String authCode) {
		GetAccessToken.Builder builder = new GetAccessToken.Builder(getApplicationContext(), mRequestQueue);
		GetAccessToken request = builder.setAuthCode(authCode)
			   .setClicentSecret(CLIENT_SECRET)
			   .setClientID(API_KEY)
			   .setRedirectUri(REDIRECT_URI)
			   .build();
		request.setTokenRequestListener(this);
		request.query();
	}
	
	private void requestAuthCode() {
		mLoginWv.loadUrl(getAuthorizationCodeUrl());
	}
	
	private void init() {
		initViews();
		MyApplication myApp = (MyApplication) getApplication();
		mRequestQueue = myApp.getRequestQueue();
	}

	private void initViews() {
		requestAuthCode();
		mLoginWv.setWebViewClient(new WebViewClient(){

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				Log.d(TAG, "request url:" + url);
				if (!TextUtils.isEmpty(url) && url.startsWith(REDIRECT_URI)) {
					Uri uri = Uri.parse(url);
					String code = uri.getQueryParameter("code");
					if (!TextUtils.isEmpty(code)) {
						requestAccessToken(code);
					}
				}
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
			}
			
			
		}); 
	}
	
	
	/**
	 * Get Method
	 * @return
	 */
	private static String getAuthorizationCodeUrl() {
		String url = GET_AUTHORIZATION_CODE_URL.replace("${API_KEY}", API_KEY);
		url = url.replace("${REDIRECT_URI}", REDIRECT_URI);
		return url;
	}
	

	@Override
	public void onSuccess(TokenResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFailure() {
		// TODO Auto-generated method stub
		
	}



}
