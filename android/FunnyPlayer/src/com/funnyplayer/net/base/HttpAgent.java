package com.funnyplayer.net.base;

import java.io.InputStream;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpRequestBase;
import com.funnyplayer.net.api.LastFmAPI;
import com.funnyplayer.net.api.geci.GeciAPI;

import android.content.Context;
import android.util.Log;

public class HttpAgent {
	protected static final String TAG = "HttpAgent";
	
	private static HttpAgent mInstance;

	private final AppHttpClient mHttpClient;

	private HttpAgent(Context context) {
		mHttpClient = new AppHttpClient(context);
	}

	public static synchronized HttpAgent getInstance(Context context) {
		if (null == mInstance) {
			mInstance = new HttpAgent(context);
		}
		return mInstance;
	}
	
	public void execute(LastFmAPI<?> api) {
		HttpRequestBase request = api.createHttpRequest();
		InputStream in = onExecuteStart(request);
		api.handleResponse(in);
	}
	
	public void execute(GeciAPI<?> api) {
		HttpRequestBase request = api.createHttpRequest();
		InputStream in = onExecuteStart(request);
		api.handleResponse(in);
	}
	
	private InputStream onExecuteStart(HttpRequestBase request) {
		InputStream in = null;
		try {
			HttpResponse res = mHttpClient.execute(request);
			int statusCode = res.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				in = res.getEntity().getContent();
				return in;
			}
		} catch (Exception e) {
			Log.e(TAG, "fail to start http request", e);
		}
		return null;
	}
	
	public InputStream execute(HttpRequestBase request) {
		InputStream in = onExecuteStart(request);
		return in;
	}
	
	public interface Callback {
		public void onExecuteFinished(InputStream in);
	}
	
	
	
}
