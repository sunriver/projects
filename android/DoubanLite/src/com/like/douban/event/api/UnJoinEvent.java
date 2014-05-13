package com.like.douban.event.api;




import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.like.douban.api.AbstractDoubanApi;
import com.like.douban.api.DoubanJsonRequest;
import com.like.douban.api.ResponseListener;

/**
 * Delete Method
 * @author alu
 *
 */
public class UnJoinEvent extends AbstractDoubanApi {
	private final static String TAG = UnJoinEvent.class.getSimpleName();
	private final static String BASE_URL = "https://api.douban.com/v2/event/:id/participants";

	public UnJoinEvent(Context ctx, RequestQueue queue, ResponseListener listener) {
		super(ctx, queue, listener);
	}
	
	
	public void unJoin(final String accessToken, final String eventID) {
		String url = BASE_URL.replace(":id", eventID);
		DoubanJsonRequest request = createRequest(Request.Method.DELETE, url);
		request.putHeader("Authorization", "Bearer " + accessToken);
		sendRequest(request);
	}
	

}
