package com.funnyplayer.net;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Proxy;
import android.text.TextUtils;


public class HttpUtils {
	private final static String TAG = "Network";

	public interface ResponseCallback {
		public void handleResponse(InputStream input, int retCode);
	}

	public interface InitRequestCallback {
		public void initRequest(HttpRequestBase request, boolean needAuth);
	}

	public static class RequestInfo {
		public String url;
		public boolean needAuth;
		public List<NameValuePair> params;

		public RequestInfo() {
			url = null;
			needAuth = false;
			params = null;
		}
	}
	

	public static HttpRequestBase initGet(RequestInfo info, InitRequestCallback callbk) {
		HttpGet request = new HttpGet(info.url);
		callbk.initRequest(request, info.needAuth);
		return request;
	}

	public static HttpRequestBase initPost(RequestInfo info, InitRequestCallback callbk) {
		HttpPost request = new HttpPost(info.url);
		callbk.initRequest(request, info.needAuth);
		request.setHeader("Content-Type", "application/x-www-form-urlencoded");
		try {
			if (null != info.params)
				request.setEntity(new UrlEncodedFormEntity(info.params,	HTTP.UTF_8));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return request;
	}

	public static HttpRequestBase initPut(RequestInfo info, InitRequestCallback callbk) {
		HttpPut request = new HttpPut(info.url);
		callbk.initRequest(request, info.needAuth);
		return request;
	}

	public static HttpRequestBase initDelete(RequestInfo info, InitRequestCallback callbk) {
		HttpDelete request = new HttpDelete(info.url);
		callbk.initRequest(request, info.needAuth);
		return request;
	}

	public static int exec(HttpClient client, HttpRequestBase request, ResponseCallback handler) {
		int statusCode = 0;
		try {
			((AbstractHttpClient) client).setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(0, false));
			HttpResponse response = client.execute(request);
			if (null != response) {
				statusCode = response.getStatusLine().getStatusCode();

				InputStream content = null;
				content = response.getEntity().getContent();

				handler.handleResponse(content, statusCode);

				if (HttpStatus.SC_PROXY_AUTHENTICATION_REQUIRED == statusCode) {
					// TODO: Need Proxy Auth
				}
			}
		} catch (Exception e) {
			// FIXME:IF not connect to internet
			// Are these exceptions enough?
			if ((e instanceof SocketTimeoutException)
					|| (e instanceof ConnectTimeoutException)
					|| (e instanceof HttpHostConnectException)
					|| (e instanceof UnknownHostException)) {
				statusCode = HttpStatus.SC_REQUEST_TIMEOUT;
			}
			e.printStackTrace();
		} finally {
			
		}
		return statusCode;
	}

	private static String convertStreamToString(InputStream input) throws UnsupportedEncodingException {
		/*
		 * To convert the InputStream to String we use the
		 * BufferedReader.readLine() method. We iterate until the BufferedReader
		 * return null which means there's no more data to read. Each line will
		 * appended to a StringBuilder and returned as String.
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(input,	"UTF-8"));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException ex) {
		}

		return sb.toString();
	}
}
