package com.sunriver.common;

import de.greenrobot.event.EventBus;
import android.app.Application;

public class BaseApplication extends Application {
	private EventBus mEventBus;
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		mEventBus = new EventBus();
	}


	public EventBus getEventBus() {
		return mEventBus;
	}
}
