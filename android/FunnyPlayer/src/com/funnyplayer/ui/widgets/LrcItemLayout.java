package com.funnyplayer.ui.widgets;

import java.io.File;

import com.funnyplayer.R;
import com.funnyplayer.cache.lrc.LrcProvider;
import com.funnyplayer.cache.lrc.LrcProvider.LrcDownloadCompletedListener;
import com.funnyplayer.util.NetUtils;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class LrcItemLayout extends LinearLayout implements OnClickListener, LrcDownloadCompletedListener {
	private static final String TAG = LrcItemLayout.class.getSimpleName();
	
	private String mSong;
	private String mArtist;
	private String mUrl;
	private ImageView mDownloadImg;
	private TextView mArtistTv;
	private TextView mSongTv;
	private LrcProvider mLrcProvider;
	private Toast mLoadingToast;
	private Animation mRefreshAnim;
	private Handler mHandler;

	public LrcItemLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mLrcProvider = LrcProvider.getInstance(context.getApplicationContext());
		mRefreshAnim = AnimationUtils.loadAnimation(context, R.anim.refresh); 
		mHandler = new Handler();
	}
	
	
	public void init() {
		mArtistTv = (TextView) findViewById(R.id.lrc_item_artist);
		mSongTv = (TextView) findViewById(R.id.lrc_item_song);
		mDownloadImg = (ImageView) findViewById(R.id.lrc_item_download);
		mDownloadImg.setOnClickListener(LrcItemLayout.this);
		this.setOnClickListener(this);
	}

	
	public String getSong() {
		return mSong;
	}

	public void setSong(String song) {
		this.mSong = song;
		mSongTv.setText(mSong);
	}


	public String getArtist() {
		return mArtist;
	}

	public void setArtist(String artist) {
		this.mArtist = artist;
		mArtistTv.setText(mArtist);
	}

	public String getUrl() {
		return mUrl;
	}

	public void setUrl(String url) {
		this.mUrl = url;
		if (!isHttpUrl(url)) {
			mDownloadImg.setImageDrawable(null);
		}
	}

	private boolean isHttpUrl(String url) {
		return (!TextUtils.isEmpty(url) && url.startsWith("http"));
	}
	
	private void downloadLrc() {
		if (!NetUtils.isNetworkAvaiable(getContext())) {
			Toast.makeText(getContext(), R.string.netowrk_unavaible, Toast.LENGTH_SHORT).show();
			return;
		}
		mDownloadImg.startAnimation(mRefreshAnim);
		if (mLoadingToast != null) {
			mLoadingToast.cancel();
		}
		mLoadingToast = Toast.makeText(getContext(), R.string.lrc_toast_loading, 100);
		mLoadingToast.show();
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				mLrcProvider.downloadLrc(mArtist, mSong, mUrl, LrcItemLayout.this);
			}
		}, 1000);
	}
	
	@Override
	public void onClick(View v) {
		Log.v(TAG, "onClick()+");
		if (isHttpUrl(mUrl)) {
			downloadLrc();
		} else {
			String msg = mLrcProvider.getLrcFromFile(mUrl);
			LrcToast lt = LrcToast.makeToast(getContext(), (ViewGroup) v.getRootView(), msg);
			lt.show();
		}
	}

	@Override
	public void onDownloadFinished(String artist, String song, File file) {
		Log.v(TAG, TAG + ".onDownloadFinished()+ file = " + file.getAbsolutePath());
		mUrl = file.getAbsolutePath();
		mDownloadImg.clearAnimation();
		mDownloadImg.setImageResource(R.drawable.downloadedd);
		if (mLoadingToast != null) {
			mLoadingToast.cancel();
		}
	}

 
}
