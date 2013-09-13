package com.funnyplayer.ui.widgets;

import com.adwo.adsdk.AdListener;
import com.adwo.adsdk.AdwoAdView;
import com.adwo.adsdk.ErrorCode;
import com.funnyplayer.R;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
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
	private static final String ADWO_PID = "8101ed73466a407a83c5a9953315a47b";
	
	private static final boolean AD_TEST_MODE = false;
	
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
		mAdView = createAdView(mContent);
		mAdView.setListener(this);
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
		
		AdwoAdView adView = new AdwoAdView(mContext, ADWO_PID, AD_TEST_MODE, 40);
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
		lt.mToastView.setText(msg);
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

}
