package com.funnyplayer.ui.widgets;

import com.funnyplayer.R;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

public class LrcToast {
	private PopupWindow mWin;
	private Context mContext;
	private TextView mToastView;
	private View mParent;
	public LrcToast(Context context, View parent) {
		mContext = context;
		mParent = parent;
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
		View v = inflater.inflate(R.layout.lrc_toast, null);
		mToastView = (TextView) v.findViewById(R.id.lrc_toast);
		mWin = new PopupWindow(v, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	}
	
	public void show(final String msg) {
		if (TextUtils.isEmpty(msg)) {
			return;
		}
		mToastView.setText(msg);
		mWin.showAtLocation(mParent, Gravity.LEFT | Gravity.RIGHT, 0, 0);
	}
}
