package com.funnyplayer.net;

import java.lang.reflect.Method;


import org.apache.http.HttpVersion;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;


import android.content.Context;
import android.util.Log;

/**
 * Common http client
 * @author leon
 *
 */

public class AppHttpClient extends DefaultHttpClient {
	
	private final static String TAG = "Network";
	
	// Wait this many milliseconds max for the TCP connection to be established
	private static final int CONNECTION_TIMEOUT = 60 * 1000;
	 
	// Wait this many milliseconds max for the server to send us data once the connection has been established
	private static final int SO_TIMEOUT = 5 * 60 * 1000;
	
	private static AppHttpClient mInstance;
	
	private Context mContext;
	
	public static synchronized AppHttpClient getSingleInstance(Context context) {
		if (null == mInstance) {
			mInstance = new AppHttpClient(context);
		}
		return mInstance;
	}
	
	private AppHttpClient(Context context) {
		mContext = context;
	}
	
	@Override
	protected ClientConnectionManager createClientConnectionManager() {
		SchemeRegistry registry = new SchemeRegistry();
		registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		registry.register(new Scheme("https", getHttpsSocketFactory(), 443));
		HttpParams params = getParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
		HttpConnectionParams.setConnectionTimeout(params, CONNECTION_TIMEOUT);
		HttpConnectionParams.setSoTimeout(params, SO_TIMEOUT);
		return new ThreadSafeClientConnManager(params, registry);
	}

	/**
	 * Gets an HTTPS socket factory with SSL Session Caching if such support
	 * is available, otherwise falls back to a non-caching factory
	 * 
	 * @return
	 */
	protected SocketFactory getHttpsSocketFactory() {
		try {
			Class sslSessionCacheClass = Class.forName("android.net.SSLSessionCache");
			Object sslSessionCache = sslSessionCacheClass.getConstructor(Context.class).newInstance(mContext);
			Method getHttpSocketFactory = Class.forName("android.net.SSLCertificateSocketFactory").getMethod("getHttpSocketFactory", new Class[] { int.class, sslSessionCacheClass });
			return (SocketFactory) getHttpSocketFactory.invoke(null, CONNECTION_TIMEOUT, sslSessionCache);
		} catch (Exception e) {
			Log.e(TAG, "BaseHttpUtils::STRHttpClient::getHttpsSocketFactory Unable to use android.net.SSLCertificateSocketFactory to get a SSL session caching socket factory, falling back to a non-caching socket factory");
			return SSLSocketFactory.getSocketFactory();
		}
	}

}
