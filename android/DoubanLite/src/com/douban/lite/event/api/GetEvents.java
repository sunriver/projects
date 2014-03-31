package com.douban.lite.event.api;

import java.lang.reflect.Field;

import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.douban.lite.event.bean.EventList;
import com.douban.lite.event.fragment.EventFragment;

/**
 * 获取活动列表 # GET https://api.douban.com/v2/event/list 返回格式:Event List
 * 
 * 参数 : loc:城市 id day_type: 时间类型 future, week, weekend, today, tomorrow
 * type:活动类型 all,music, film, drama, commonweal, salon, exhibition, party,
 * sports,travel, others;
 * ex: https://api.douban.com/v2/event/list?loc=108288 or https://api.douban.com/v2/event/list?loc=beijing
 * 
 * @author alu
 * 
 */

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
			// TODO Auto-generated method stub

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
