package com.like.douban.event.api;


import org.json.JSONObject;
import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.like.douban.api.AbstractDoubanApi;
import com.like.douban.api.Consts;
import com.like.douban.api.DoubanResponseListener;
import com.like.douban.event.bean.EventList;

/**
 * Get Method
 * @author alu
 *
 */
public class GetWisheredEvents extends AbstractDoubanApi {
	private final static String TAG = GetWisheredEvents.class.getSimpleName();
	private final static String BASE_URL = "https://api.douban.com/v2/event/user_wished/:id";


	public GetWisheredEvents(Context ctx, RequestQueue queue, DoubanResponseListener listener) {
		super(ctx, queue, listener);
	}

	public void query(final String userID) {
		StringBuffer urlBuf = new StringBuffer(BASE_URL);
		urlBuf.append("?apikey=" + Consts.API_KEY);
		String url = urlBuf.toString().replace(":id", userID);
		Request request = createRequest(Request.Method.GET, url);
		sendRequest(request);
	}
	
	
	public void query(final String userID, final int start, final int count) {
		query(userID, start, count);
	}


	@SuppressWarnings("unchecked")
	@Override
	public EventList parseResponse(JSONObject response) {
		EventList eventList = EventList.fromJSONObject(response);
		return eventList;
	}
	


}
