package com.sunriver.common;

import android.app.Application;
import android.support.v7.app.ActionBarActivity;

public abstract class BaseActivity extends ActionBarActivity {
	@Override
	protected void onResume() {
		super.onResume();
		Application app = (Application) this.getApplicationContext();
		if (app instanceof BaseApplication) {
			((BaseApplication) app).getEventBus().register(this);
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		Application app = (Application) this.getApplicationContext();
		if (app instanceof BaseApplication) {
			((BaseApplication) app).getEventBus().unregister(this);
		}
	}
}
