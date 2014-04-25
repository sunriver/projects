package com.like.douban.event.api;


import org.json.JSONObject;
import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.like.douban.account.bean.UserList;
import com.like.douban.api.AbstractDoubanApi;
import com.like.douban.api.Consts;
import com.like.douban.api.ResponseListener;

/**
 * Get Method
 * @author alu
 *
 */
public class GetParticipantedUsers extends AbstractDoubanApi {
	private final static String TAG = GetParticipantedUsers.class.getSimpleName();
	private final static String BASE_URL = "https://api.douban.com/v2/event/:id/participants";


	public GetParticipantedUsers(Context ctx, RequestQueue queue, ResponseListener listener) {
		super(ctx, queue, listener);
	}

	public void query(final String eventID) {
		StringBuffer urlBuf = new StringBuffer(BASE_URL);
		urlBuf.append("?apikey=" + Consts.API_KEY);
		String url = urlBuf.toString().replace(":id", eventID);
		Request request = createRequest(Request.Method.GET, url);
		sendRequest(request);
	}

	@SuppressWarnings("unchecked")
	@Override
	public UserList parseResponse(JSONObject response) {
		UserList userList = UserList.fromJSONObject(response);
		return userList;
	}
	


}
