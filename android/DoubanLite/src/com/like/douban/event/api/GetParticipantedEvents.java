package com.like.douban.event.api;


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
import com.like.douban.api.ApiUtils;
import com.like.douban.api.ResponseListener;
import com.like.douban.event.EventFragment;
import com.like.douban.event.bean.EventList;

/**
 * 
 * @author alu
 *
 */
public class GetParticipantedEvents {
	private final static String TAG = GetParticipantedEvents.class.getSimpleName();
	private final static String BASE_URL = "https://api.douban.com/v2/event/user_participated/:id";

	private Context mContext;
	private RequestQueue mRequestQueue;
	private ResponseListener mResponseListener;

	private  class GetEventsListener implements Listener<JSONObject> {

		@Override
		public void onResponse(JSONObject response) {
			if (null == response) {
				return;
			}
			EventList eventList = EventList.fromJSONObject(response);
			mResponseListener.onSuccess(eventList);
			Log.d(TAG, "onResponse()-");
		}

	};

	private  class GetEventsErrorListener implements ErrorListener {

		@Override
		public void onErrorResponse(VolleyError error) {
			ApiUtils.checkError(mContext, error);
			mResponseListener.onFailure();
		}

	};

	public GetParticipantedEvents(Context ctx, RequestQueue queue, ResponseListener listener) {
		this.mContext = ctx;
		this.mRequestQueue = queue;
		this.mResponseListener = listener;
	}

	public void query(final String userID) {
		String url = BASE_URL.replace(":id", userID);
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Request.Method.GET, url, null, new GetEventsListener(),
				new GetEventsErrorListener());
		mRequestQueue.add(jsonObjectRequest);
	}
	


}
