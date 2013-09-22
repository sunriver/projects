package com.funnyplayer.ui.widgets;

import java.io.StringReader;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.adwo.adsdk.AdListener;
import com.adwo.adsdk.AdwoAdView;
import com.adwo.adsdk.ErrorCode;
import com.funnyplayer.R;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LrcToast implements AdListener {
	private static final String TAG = LrcToast.class.getSimpleName();
	
	private static final boolean AD_TEST_MODE = true;
	
	private PopupWindow mWin;
	private Context mContext;
	private TextView mToastView;
	private View mParent;
	private RelativeLayout mContent;
	private AdwoAdView mAdView;
	
	private LrcToast(Context context, ViewGroup parent) {
		mContext = context;
		mParent = parent;
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
		mContent = (RelativeLayout) inflater.inflate(R.layout.lrc_toast, parent, false);
		mToastView = (TextView) mContent.findViewById(R.id.lrc_toast);
//		mAdView = createAdView(mContent);
//		mAdView.setListener(this);
		mWin = new PopupWindow(mContent, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
		mWin.setBackgroundDrawable(new BitmapDrawable());
	}
	
	
	public LrcToast(Context context) {
		this(context, null);
	}
	
	private AdwoAdView createAdView(RelativeLayout parent) {
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		
		params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		AdwoAdView.setBannerMatchScreenWidth(true);
		String pid = mContext.getString(R.string.adwo_pid);
		AdwoAdView adView = new AdwoAdView(mContext, pid, AD_TEST_MODE, 40);
		parent.addView(adView, params);
		return adView;
	}
	
	public void show() {
		mWin.showAtLocation(mParent, Gravity.CENTER, 0, 0);
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
	
	public static LrcToast makeToast(Context context, ViewGroup parent, final String msg) {
		LrcToast lt = new LrcToast(context, parent);
		LrcInfo lrcInfo = lt.filterMessage(msg);
		String content = lrcInfo.content.toString();
		if (!TextUtils.isEmpty(content)) {
			lt.mToastView.setText(content.trim());
		}
		return lt;
	}


	@Override
	public void onFailedToReceiveAd(AdwoAdView arg0, ErrorCode arg1) {
		Log.e(TAG, "onFailedToReceiveAd() errorCode:" + arg1.getErrorCode());
	}


	@Override
	public void onReceiveAd(AdwoAdView arg0) {
		Log.v(TAG, "onReceiveAd()+");
	}
	
	private void parseLine(LrcInfo lrc, String str) {
		if (TextUtils.isEmpty(str)) {
			lrc.content.append("\n");
			return;
		}
		int lIndex = str.lastIndexOf("]");
		if (lIndex < 0) {
			return;
		}
		String c = str.substring(lIndex + 1, str.length());
		lrc.content.append(c + "\n");
	}  
	
	
	private  LrcInfo filterMessage(String rawMsg) {
		if (TextUtils.isEmpty(rawMsg)) {
			return null;
		}
		Scanner sc = new Scanner(rawMsg);
		sc.useDelimiter("\n");
		LrcInfo lrc = new LrcInfo();
		while (sc.hasNext()) {
			String line = sc.next();
			parseLine(lrc, line);
		}
		return lrc;
	}
	
	private static class LrcInfo {
		String title;
		String artist;
		String album;
		StringBuilder content = new StringBuilder();
	}
}
