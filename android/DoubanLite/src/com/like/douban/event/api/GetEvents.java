package com.like.douban.event.api;


import org.json.JSONObject;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.like.douban.api.AbstractDoubanApi;
import com.like.douban.api.Consts;
import com.like.douban.api.ResponseListener;
import com.like.douban.event.bean.EventList;


public class GetEvents extends AbstractDoubanApi {
	private final static String TAG = GetEvents.class.getSimpleName();
	private final static String BASE_URL = "https://api.douban.com/v2/event/list";


	public GetEvents(Context ctx, RequestQueue queue, ResponseListener listener) {
		super(ctx, queue, listener);
	}

	public void query(final String loc, final String dayType,
			final String eventType, final int start, final int count) {
		StringBuffer urlBuf = new StringBuffer(BASE_URL);
		urlBuf.append("?apikey=" + Consts.API_KEY);
		urlBuf.append("&loc=" + loc);
		if (!TextUtils.isEmpty(dayType)) {
			urlBuf.append("&day_type=" + dayType);
		}
		if (!TextUtils.isEmpty(eventType)) {
			urlBuf.append("&type=" + eventType);
		}
		urlBuf.append("&start=" + start);
		urlBuf.append("&count=" + count);
		Request request = createRequest(Request.Method.GET, urlBuf.toString());
		sendRequest(request);
	}
	
	public void query(final String loc, final String dayType, final String eventType) {
		query(loc, dayType, eventType, 0, Consts.RESULT_COUNT);
	}
	
	public void query(final String loc, final int start, final int count) {
		query(loc, null, null, start, count);
	}
	
	public void query(final String loc) {
		query(loc, null, null, 0, Consts.RESULT_COUNT);
	}

	@SuppressWarnings("unchecked")
	@Override
	public EventList parseResponse(JSONObject response) {
		Log.d(TAG, "parseResponse()+");
		EventList eventList = EventList.fromJSONObject(response);
		return eventList;
	}

}
