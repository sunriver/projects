package com.like.douban.event.api;



import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.like.douban.api.ApiUtils;

/**
 * Post Method
 * @author alu
 *
 */
public class JoinEvent {
	private final static String TAG = JoinEvent.class.getSimpleName();
	private final static String BASE_URL = "https://api.douban.com/v2/event/:id/participants";

	private Context mContext;
	private RequestQueue mRequestQueue;

	private  class ResponseListener implements Listener<JSONObject> {

		@Override
		public void onResponse(JSONObject response) {
			if (null == response) {
				return;
			}
			Log.d(TAG, "onResponse()-");
		}

	};

	private  class WrapErrorListener implements ErrorListener {

		@Override
		public void onErrorResponse(VolleyError error) {
			ApiUtils.checkError(mContext, error);
		}

	};

	public JoinEvent(Context ctx, RequestQueue queue) {
		this.mContext = ctx;
		this.mRequestQueue = queue;
	}
	
	public void join(final String accessToken, final String eventID) {
		String url = BASE_URL.replace(":id", eventID);
		String participate_date = (String) DateFormat.format("yyyy-MM-dd", System.currentTimeMillis());
//		url = url + "?participate_date=" + participate_date;
		JoinEventRequest request = new JoinEventRequest(
				Request.Method.POST, url, null, new ResponseListener(),
				new WrapErrorListener());
		request.putHeader("Authorization", "Bearer " + accessToken);
		request.putPostParam("participate_date", participate_date);
		mRequestQueue.add(request);
	}
	
	private static class JoinEventRequest extends JsonObjectRequest {
		 private Map<String, String> mHeaderMap = new HashMap<String, String>();
		 private Map<String, String> mPostParamMap = new HashMap<String, String>();

		public JoinEventRequest(String url, JSONObject jsonRequest,
				Listener<JSONObject> listener, ErrorListener errorListener) {
			super(url, jsonRequest, listener, errorListener);
		}

		public JoinEventRequest(int method, String url, JSONObject jsonRequest,
				Listener<JSONObject> listener, ErrorListener errorListener) {
			super(method, url, jsonRequest, listener, errorListener);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Map<String, String> getHeaders() throws AuthFailureError {
            return mHeaderMap;
		}
		
		public void putHeader(final String key, final String value) {
			mHeaderMap.put(key, value);
		}

		@Override
		protected Map<String, String> getParams() throws AuthFailureError {
			return mPostParamMap;
		}
		
		
		public void putPostParam(final String key, final String value) {
			mPostParamMap.put(key, value);
		}
		
		
		
	}

}
