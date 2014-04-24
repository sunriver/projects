package com.like.douban.login;

import com.android.volley.RequestQueue;
import com.like.MyApplication;
import com.like.R;
import com.like.douban.api.ResponseListener;
import com.like.douban.login.api.GetAccessToken;
import com.like.douban.login.api.TokenResult;
import com.sunriver.common.utils.ViewUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class LoginActivity extends ActionBarActivity {
	private final static String TAG = LoginActivity.class.getSimpleName();
	private final static String SCOPE = "event_basic_r,event_basic_w,douban_basic_common";
	private final static String GET_AUTHORIZATION_CODE_URL = "https://www.douban.com/service/auth2/auth?client_id=${API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code&scope=" + SCOPE;
	private final static String API_KEY = "0feda5e1f219879d2fc8f1a5d64c96d7";
	private final static String CLIENT_SECRET = "24e8af830d5eb113";
	private final static String REDIRECT_URI = "http://www.douban.com/callback";
	private WebView mLoginWv;
	private RequestQueue mRequestQueue;
	private ResponseListener<TokenResult> mResponseListener = new ResponseListener<TokenResult>() {
		
		@Override
		public void onFailure() {
			// TODO Auto-generated method stub
		}

		
		@Override
		public  void onSuccess(TokenResult result) {
			LoginUtil.saveToken(getApplicationContext(), result);
			finish();
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_login);
		init();
	}
	
	private void requestAccessToken(final String authCode) {
		Log.d(TAG, "requestAccessToken()+");
		GetAccessToken.Builder builder = new GetAccessToken.Builder(getApplicationContext(), mRequestQueue, mResponseListener);
		GetAccessToken request = builder.setAuthCode(authCode)
			   .setClicentSecret(CLIENT_SECRET)
			   .setClientID(API_KEY)
			   .setRedirectUri(REDIRECT_URI)
			   .build();
		request.query();
	}
	
	private void requestAuthCode() {
		mLoginWv.loadUrl(getAuthorizationCodeUrl());
	}
	
	private void init() {
		initActionBar();
		initViews();
		MyApplication myApp = (MyApplication) getApplication();
		mRequestQueue = myApp.getRequestQueue();
		requestAuthCode();
	}
	
	private void initActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle(R.string.douban_login);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP
				| ActionBar.DISPLAY_SHOW_TITLE, ActionBar.DISPLAY_HOME_AS_UP
				| ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_SHOW_HOME);
		ViewUtil.setActionBarBackgroundRepeat(this, actionBar,
				R.drawable.bg_base);
	}

	private void initViews() {
		mLoginWv = (WebView) findViewById(R.id.wv_login);
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
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d(TAG, "onOptionsItemSelected()+");
		switch (item.getItemId()) {
		case android.R.id.home:
			super.onBackPressed();
			break;
		}
		return super.onOptionsItemSelected(item);
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

}
