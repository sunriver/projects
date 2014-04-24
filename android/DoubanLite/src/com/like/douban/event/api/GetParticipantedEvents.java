package com.like.douban.event.api;


import org.json.JSONObject;
import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.like.douban.api.AbstractDoubanApi;
import com.like.douban.api.ResponseListener;
import com.like.douban.event.bean.EventList;

/**
 * 
 * @author alu
 *
 */
public class GetParticipantedEvents extends AbstractDoubanApi {
	private final static String TAG = GetParticipantedEvents.class.getSimpleName();
	private final static String BASE_URL = "https://api.douban.com/v2/event/user_participated/:id";


	public GetParticipantedEvents(Context ctx, RequestQueue queue, ResponseListener listener) {
		super(ctx, queue, listener);
	}

	public void query(final String userID) {
		String url = BASE_URL.replace(":id", userID);
		Request request = createRequest(Request.Method.GET, url);
		sendRequest(request);
	}

	@SuppressWarnings("unchecked")
	@Override
	public EventList parseResponse(JSONObject response) {
		EventList eventList = EventList.fromJSONObject(response);
		return eventList;
	}
	


}
