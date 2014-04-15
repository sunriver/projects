package com.like.douban.event.api;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.like.R;

public class ApiUtils {
	public static void checkError(Context ctx, final VolleyError error) {
		if (error instanceof NoConnectionError) {
			Toast.makeText(ctx, R.string.error_network_disconnect, Toast.LENGTH_SHORT).show();
		} else if (error instanceof TimeoutError) {
			Toast.makeText(ctx, R.string.error_connection_timeout, Toast.LENGTH_SHORT).show();
		}

	}
}
