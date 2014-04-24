package com.like.douban.event.api;


import org.json.JSONObject;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.like.douban.api.AbstractDoubanApi;
import com.like.douban.api.ResponseListener;
import com.like.douban.event.bean.EventList;


public class GetEvents extends AbstractDoubanApi {
	private final static String TAG = GetEvents.class.getSimpleName();
	private final static String BASE_URL = "https://api.douban.com/v2/event/list";


	public GetEvents(Context ctx, RequestQueue queue, ResponseListener listener) {
		super(ctx, queue, listener);
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
		Request request = createRequest(Request.Method.GET, urlBuf.toString());
		sendRequest(request);
	}
	
	public void query(final String loc) {
		query(loc, null, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public EventList parseResponse(JSONObject response) {
		Log.d(TAG, "parseResponse()+");
		EventList eventList = EventList.fromJSONObject(response);
		return eventList;
	}

}
