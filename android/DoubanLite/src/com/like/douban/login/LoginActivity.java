package com.like.douban.login;

import com.like.R;

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

public class LoginActivity extends ActionBarActivity implements OnClickListener {
	private final static String TAG = LoginActivity.class.getSimpleName();
	private final static String  GET_AUTHORIZATION_CODE_URL = "https://www.douban.com/service/auth2/auth?client_id=${API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code&scope=shuo_basic_r,shuo_basic_w,douban_basic_common";
	private final static String  POST_ACCESS_TOKEN_URL = "https://www.douban.com/service/auth2/token?client_id=${API_KEY}&client_secret=${CLIENT_SECRET}&redirect_uri=${REDIRECT_URI}&grant_type=authorization_code&code=${AUTHORIZATION_CODE}";
	private final static String API_KEY = "0feda5e1f219879d2fc8f1a5d64c96d7";
	private final static String CLIENT_SECRET = "24e8af830d5eb113";
	private final static String REDIRECT_URI = "http://www.douban.com/callback";
	private String mAuthorizationCode;
	private ImageView mLoginIv;
	private WebView mLoginWv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_login);
		init();
	}
	
	private void requestAccessToken(final String authCode) {
		mLoginWv.postUrl(getAccessTokenUrl(authCode), null);
	}
	
	private void requestAuthCode() {
		mLoginWv.loadUrl(getAuthorizationCodeUrl());
	}

	private void init() {
		mLoginIv = (ImageView) this.findViewById(R.id.iv_login);
		mLoginIv.setOnClickListener(this);
		mLoginWv = (WebView) this.findViewById(R.id.wv_login);
		requestAuthCode();
		mLoginWv.setWebViewClient(new WebViewClient(){

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				Log.d(TAG, "request url:" + url);
				if (!TextUtils.isEmpty(url) && url.startsWith(REDIRECT_URI)) {
					Uri uri = Uri.parse(url);
					String code = uri.getQueryParameter("code");
					if (!TextUtils.isEmpty(code)) {
						mAuthorizationCode = code;
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
	
	/**
	 * Post Method
	 * @return
	 */
	private static String getAccessTokenUrl(String code) {
		String url = POST_ACCESS_TOKEN_URL.replace("${API_KEY}", API_KEY);
		url = url.replace("${CLIENT_SECRET}", CLIENT_SECRET);
		url = url.replace("${REDIRECT_URI}", REDIRECT_URI);
		url = url.replace("${AUTHORIZATION_CODE}", code);
		return url;
	}

	private void doLogin() {
		Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse(getAuthorizationCodeUrl()));
		startActivity(viewIntent);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_login:
			doLogin();
			break;
		default:
			;
		}
	}

}
