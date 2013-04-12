package com.oobe.bar;

import com.oobe.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class HotBarActivity extends Activity {
	final static String TAG = "HOTBAR";
	Button mButton;
	PopupWindow mPopupWindow;
	View mHotBar;
	HotkeyPanel mHotkeyPanel; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mButton = new Button(this);
		int WRAP_CONTENT = LinearLayout.LayoutParams.WRAP_CONTENT;
		int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
		mButton.setText("show or hide hot key bar");
		mHotBar = getLayoutInflater().inflate(R.layout.hotkeybar_common, null);
		init();
		setContentView(mButton, lp);
		super.onCreate(savedInstanceState);
	}
	
	private void init() {
		mButton.setOnClickListener(new View.OnClickListener() {
			boolean isVisible = false;
			@Override
			public void onClick(View v) {
				if (isVisible) {
					mPopupWindow.dismiss();
				} else {
					mHotkeyPanel = new HotkeyPanel(HotBarActivity.this, 0);
					mPopupWindow = new PopupWindow(mHotkeyPanel.getHotkeyBar(), LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
					mPopupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
				}
				isVisible = !isVisible;
			}
		});
		
	}
	
	
}
