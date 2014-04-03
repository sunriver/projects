package com.like.douban.event.api;


import org.json.JSONObject;
import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.like.douban.event.EventFragment;
import com.like.douban.event.bean.LocationList;


public class GetLocations {
	private final static String TAG = GetLocations.class.getSimpleName();
	private final static String URL = "https://api.douban.com/v2/loc/list";
	private Context mContext;
	private RequestQueue mRequestQueue;
	private EventFragment mFragment;

	private  class GetEventsListener implements Listener<JSONObject> {

		@Override
		public void onResponse(JSONObject response) {
			if (null == response) {
				return;
			}
			LocationList locationList = LocationList.fromJSONObject(response);
			mFragment.updateLocations(locationList);
			Log.d(TAG, "onResponse()-");
		}

	};

	private  class GetEventsErrorListener implements ErrorListener {

		@Override
		public void onErrorResponse(VolleyError error) {
			// TODO Auto-generated method stub

		}

	};

	public GetLocations(Context ctx, RequestQueue queue, EventFragment fragment) {
		this.mContext = ctx;
		this.mRequestQueue = queue;
		this.mFragment = fragment;
	}

	public void query() {
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Request.Method.GET, URL, null, new GetEventsListener(),
				new GetEventsErrorListener());
		mRequestQueue.add(jsonObjectRequest);
	}
	
	

}
