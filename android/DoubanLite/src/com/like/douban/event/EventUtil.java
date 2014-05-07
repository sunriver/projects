package com.like.douban.event;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.like.douban.event.bean.Event;

public class EventUtil {
	public static void showEventDetail(Activity act, Event evt) {
		Intent intent = new Intent(act, EventDetailActivity.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable(EventDetailActivity.STATE_EVENT, evt);
		intent.putExtras(bundle);
		act.startActivity(intent);
	}
}
