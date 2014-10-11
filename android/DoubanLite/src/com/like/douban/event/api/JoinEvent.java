package com.like.douban.event.api;




import android.content.Context;
import android.text.format.DateFormat;
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
public class JoinEvent extends AbstractDoubanApi {
	private final static String TAG = JoinEvent.class.getSimpleName();
	private final static String BASE_URL = "https://api.douban.com/v2/event/:id/participants";

	public JoinEvent(Context ctx, RequestQueue queue, DoubanResponseListener listener) {
		super(ctx, queue, listener);
	}
	
	
	public void join(final String accessToken, final String eventID) {
		String url = BASE_URL.replace(":id", eventID);
		String participate_date = (String) DateFormat.format("yyyy-MM-dd", System.currentTimeMillis());
//		url = url + "?participate_date=" + participate_date;
		
		DoubanJsonRequest request = createRequest(Request.Method.POST, url);
		request.putHeader("Authorization", "Bearer " + accessToken);
		request.putPostParam("participate_date", participate_date);
		sendRequest(request);
	}
	

}
