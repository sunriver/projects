package com.like.douban.account;

import com.android.volley.RequestQueue;
import com.like.MyApplication;
import com.like.R;
import com.like.douban.api.Consts;
import com.like.douban.api.ResponseListener;
import com.like.douban.account.api.GetAccessToken;
import com.like.douban.account.api.GetAccountInfo;
import com.like.douban.account.bean.TokenResult;
import com.like.douban.account.bean.User;
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
	private WebView mLoginWv;
	private RequestQueue mRequestQueue;
	private ResponseListener<TokenResult> mResponseListener = new ResponseListener<TokenResult>() {
		
		@Override
		public void onFailure() {
			// TODO Auto-generated method stub
		}

		
		@Override
		public  void onSuccess(TokenResult result) {
			AccountManager.saveToken(getApplicationContext(), result);
			requestGetAccount(result.getAccessToken());
		}

	};
	
	private ResponseListener<User> mGetAccountResponseListener = new ResponseListener<User>() {
		
		@Override
		public void onFailure() {
			// TODO Auto-generated method stub
		}
		
		
		@Override
		public  void onSuccess(User result) {
			AccountManager.saveLoginUser(getApplicationContext(), result);
			finish();
		}
		
	};
	
	
	private void requestGetAccount(final String accessToken) {
		GetAccountInfo request = new GetAccountInfo(this.getApplicationContext(), mRequestQueue, mGetAccountResponseListener);
		request.query(accessToken);
	}
	

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
			   .setClicentSecret(Consts.CLIENT_SECRET)
			   .setClientID(Consts.API_KEY)
			   .setRedirectUri(Consts.REDIRECT_URI)
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
				if (!TextUtils.isEmpty(url) && url.startsWith(Consts.REDIRECT_URI)) {
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
		String url = GET_AUTHORIZATION_CODE_URL.replace("${API_KEY}", Consts.API_KEY);
		url = url.replace("${REDIRECT_URI}", Consts.REDIRECT_URI);
		return url;
	}

}
