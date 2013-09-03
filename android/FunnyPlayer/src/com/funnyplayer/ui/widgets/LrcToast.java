package com.funnyplayer.ui.widgets;

import com.funnyplayer.R;

import android.content.Context;
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
	private View mContent;
	private LrcToast(Context context, View parent) {
		mContext = context;
		mParent = parent;
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
		mContent = inflater.inflate(R.layout.lrc_toast, null);
		mToastView = (TextView) mContent.findViewById(R.id.lrc_toast);
		mWin = new PopupWindow(mContent, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
	}
	
	public LrcToast(Context context) {
		this(context, null);
	}
	
	public void show() {
		mWin.showAtLocation(mParent, Gravity.LEFT | Gravity.TOP, 0, 0);
	}
	

	public View getView() {
		return mContent;
	}

	public void hide() {
		if (mWin.isShowing()) {
			mWin.dismiss();
		}
	}
	
	public static LrcToast makeToast(Context context) {
		return makeToast(context, null, null);
	}
	
	public static LrcToast makeToast(Context context, final String msg) {
		return makeToast(context, null, msg);
	}
	
	public static LrcToast makeToast(Context context, View parent, final String msg) {
		LrcToast lt = new LrcToast(context, parent);
		lt.mToastView.setText(msg);
		return lt;
	}

}
