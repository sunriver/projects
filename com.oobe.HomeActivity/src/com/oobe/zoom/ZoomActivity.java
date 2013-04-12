package com.oobe.zoom;

import android.app.Activity;
import android.os.Bundle;

public class ZoomActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new ZoomView(this));
	}

}
