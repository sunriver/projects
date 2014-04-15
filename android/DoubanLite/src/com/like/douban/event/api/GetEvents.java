package com.like.douban.event.api;


import org.json.JSONObject;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.like.R;
import com.like.douban.event.EventFragment;
import com.like.douban.event.bean.EventList;


public class GetEvents {
	private final static String TAG = GetEvents.class.getSimpleName();
	private final static String BASE_URL = "https://api.douban.com/v2/event/list";

	private Context mContext;
	private RequestQueue mRequestQueue;
	private EventFragment mFragment;

	private  class GetEventsListener implements Listener<JSONObject> {

		@Override
		public void onResponse(JSONObject response) {
			if (null == response) {
				return;
			}
			EventList eventList = EventList.fromJSONObject(response);
			mFragment.updateEvents(eventList);
			Log.d(TAG, "onResponse()-");
		}

	};

	private  class GetEventsErrorListener implements ErrorListener {

		@Override
		public void onErrorResponse(VolleyError error) {
			if (error instanceof NoConnectionError) {
				Toast.makeText(mContext, R.string.error_network_disconnect, Toast.LENGTH_SHORT).show();
			} else if (error instanceof TimeoutError) {
				Toast.makeText(mContext, R.string.error_connection_timeout, Toast.LENGTH_SHORT).show();
			}
			mFragment.updateEvents(null);
		}

	};

	public GetEvents(Context ctx, RequestQueue queue, EventFragment fragment) {
		this.mContext = ctx;
		this.mRequestQueue = queue;
		this.mFragment = fragment;
	}

	public void query(final String loc, final String dayType,
			final String eventType) {
		StringBuffer urlBuf = new StringBuffer();
		urlBuf.append(BASE_URL + "?loc=" + loc);
		if (!TextUtils.isEmpty(dayType)) {
			urlBuf.append("&day_type=" + dayType);
		}
		if (!TextUtils.isEmpty(eventType)) {
			urlBuf.append("&type=" + eventType);
		}

		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Request.Method.GET, urlBuf.toString(), null, new GetEventsListener(),
				new GetEventsErrorListener());
		mRequestQueue.add(jsonObjectRequest);
	}
	
	public void query(final String loc) {
		query(loc, null, null);
	}

}
