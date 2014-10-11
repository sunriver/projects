package com.like.douban.event.api;




import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.like.douban.api.AbstractDoubanApi;
import com.like.douban.api.DoubanJsonRequest;
import com.like.douban.api.DoubanResponseListener;

/**
 * Post Method
 * @author alu
 *
 */
public class WishEvent extends AbstractDoubanApi {
	private final static String TAG = WishEvent.class.getSimpleName();
	private final static String BASE_URL = "https://api.douban.com/v2/event/:id/wishers";

	@SuppressWarnings("rawtypes")
	public WishEvent(Context ctx, RequestQueue queue, DoubanResponseListener listener) {
		super(ctx, queue, listener);
	}
	
	
	public void wish(final String accessToken, final String eventID) {
		String url = BASE_URL.replace(":id", eventID);
		DoubanJsonRequest request = createRequest(Request.Method.POST, url);
		request.putHeader("Authorization", "Bearer " + accessToken);
		sendRequest(request);
	}
	

}
