package com.funnyplayer.ui.widgets;

import java.io.File;

import com.funnyplayer.R;
import com.funnyplayer.cache.lrc.LrcProvider;
import com.funnyplayer.cache.lrc.LrcProvider.LrcDownloadCompletedListener;

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

public class LrcItemLayout extends LinearLayout implements OnClickListener, LrcDownloadCompletedListener {
	private static final String TAG = LrcItemLayout.class.getSimpleName();
	
	private String mSong;
	private String mArtist;
	private String mUrl;
	private ImageView mDownloadImg;
	private TextView mArtistTv;
	private TextView mSongTv;
	private LrcProvider mLrcProvider;

	public LrcItemLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mLrcProvider = LrcProvider.getInstance(context.getApplicationContext());
	}
	
	
	public void init() {
		mArtistTv = (TextView) findViewById(R.id.lrc_item_artist);
		mSongTv = (TextView) findViewById(R.id.lrc_item_song);
		mDownloadImg = (ImageView) findViewById(R.id.lrc_item_download);
		mDownloadImg.setOnClickListener(LrcItemLayout.this);
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
	}

	@Override
	public void onClick(View v) {
		Log.v(TAG, TAG + ".onClick()+");
		if (!TextUtils.isEmpty(mUrl) && mUrl.startsWith("http")) {
			mDownloadImg.setImageResource(R.drawable.downloading);
			mLrcProvider.downloadLrc(mArtist, mSong, mUrl, this);
		} else {
			String msg = mLrcProvider.getLrcFromFile(mUrl);
			LrcToast lt = new LrcToast(getContext(), (View) v.getParent());
			lt.show(msg);
		}
	}

	@Override
	public void onDownloadFinished(String artist, String song, File file) {
		Log.v(TAG, TAG + ".onDownloadFinished()+ file = " + file.getAbsolutePath());
		mDownloadImg.setImageResource(R.drawable.downloaded);
		mUrl = file.getAbsolutePath();
		String msg = mLrcProvider.getLrcFromFile(mUrl);
		View v = LrcToast.makeToastView(getContext(), msg);
		Toast t = Toast.makeText(getContext(), msg, 2000);
		t.setView(v);
		t.show();
	}

 
}
