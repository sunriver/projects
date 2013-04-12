package com.oobe.bar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;

import com.oobe.R;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class HotkeyPanel implements Observer {
	final static boolean DEBUG = true;
	final static String TAG = "HotKey";
	
	private View mHotkeyView;
	private HotkeyControler mHotkeyControler;
	private HotkeyModel mHotkeyModel;
	private Activity mActivity;
	
	private View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			int keyId = v.getId();
			if (DEBUG) {
				Log.d(TAG, "HotkeyPanel::OnTouchListener()  keyId:" + keyId);
			}
			mHotkeyControler.onKeyPress(mHotkeyModel, event, keyId);
	
			return true;
		}	
	};
	
	public HotkeyPanel(Activity activity, final int serverType) {
		mHotkeyView = activity.getLayoutInflater().inflate(R.layout.hotkeybar_common, null);
		mHotkeyModel = new HotkeyModel(0);
		mHotkeyControler = new HotkeyControler();
		mHotkeyControler.registerObserver(this);
		mActivity = activity;
		init();
	}
	
	private void findViewsWithText(ArrayList<View> outViews, View v, CharSequence searchedText) {
		if (v instanceof ViewGroup) {
			int count = ((ViewGroup) v).getChildCount();
			for (int index = 0 ; index < count; index++) {
				View child = ((ViewGroup) v).getChildAt(index);
				findViewsWithText(outViews, child, searchedText);
			}
		} else if (v instanceof Button) {
			Button button = (Button) v;
			CharSequence contentDesc = v.getContentDescription();
			if (DEBUG) {
				Log.d(TAG, "HotkeyPanel::findViewsWithText() text:" + button.getText());
			}
			if (contentDesc.equals(searchedText)) {
				outViews.add(v);
			}
		}
	}

	private void init() {
		ArrayList<View> outViews = new ArrayList<View>();
		CharSequence text = mActivity.getResources().getString(R.string.key_content_description);
		findViewsWithText(outViews, mHotkeyView, text);
		for (View view : outViews) {
			view.setOnTouchListener(mOnTouchListener);
		}
	}
	
	@Override
	public void update(Observable observable, Object data) {
		Entry<Integer, Boolean> statusEntry = null;
		Iterator<Entry<Integer, Boolean>> statusIterator = mHotkeyModel.getStatusIterator();
		while (statusIterator.hasNext()) {
			statusEntry = statusIterator.next();
			int keyId = statusEntry.getKey();
			boolean status = statusEntry.getValue();
			if (DEBUG) {
				Log.d(TAG, "HotkeyPanel::update() keyId:" + keyId + " status:" + status);
			}
			View view = mHotkeyView.findViewById(keyId);
			if (view instanceof Button) {
				Button button = (Button) mHotkeyView.findViewById(keyId);
				int colorResId = status ? R.color.Blue : R.color.Green;
				button.setBackgroundResource(colorResId);
			} else {
				int visbile = status ? View.VISIBLE : View.GONE;
				view.setVisibility(visbile);
			}
		}
			
	}
	
	public View  getHotkeyBar() {
		return mHotkeyView;
	}


}
