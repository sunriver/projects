package com.oobe.touch;


import android.app.Activity;
import android.os.Bundle;

public class TouchActivity extends Activity {
	private TouchView mTouchView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mTouchView = new TouchView(this);
		setContentView(mTouchView);
	}

}
