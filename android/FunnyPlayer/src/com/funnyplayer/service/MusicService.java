package com.funnyplayer.service;

import java.util.ArrayList;
import java.util.List;

import com.funnyplayer.HomeActivity;
import com.funnyplayer.R;
import com.funnyplayer.util.MusicUtil.FilterAction;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;

public class MusicService extends Service {
	private static final int NOTIFICATION_ID = 999;
	private static final String TAG = "MusicService";

	private final IBinder mBinder = new MusicBinder();

	public class MusicBinder extends Binder {
		public MusicService getService() {
			return MusicService.this;
		}
	}

	private MusicPlayer mMusicPlayer;

	private int mItemPos;

	private final String[] mCursorCols = new String[] { "audio._id AS _id",
			MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.ALBUM,
			MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DATA,
			MediaStore.Audio.Media.MIME_TYPE, MediaStore.Audio.Media.ALBUM_ID,
			MediaStore.Audio.Media.ARTIST_ID,
			MediaStore.Audio.Media.IS_PODCAST, MediaStore.Audio.Media.BOOKMARK };

	private List<MusicInfo> mMusicList = new ArrayList<MusicInfo>();

	@Override
	public void onCreate() {
		mMusicPlayer = new MusicPlayer();
		super.onCreate();
	}

	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	public void addPlayList(List<MusicInfo> musicList) {
		mMusicList = musicList;
	}

	public boolean isPlaying() {
		return mMusicPlayer.isPlaying();
	}

	public boolean isPaused() {
		return mMusicPlayer.isPaused();
	}

	public int getItemPos() {
		return mItemPos;
	}

	public int getCurrentPos() {
		return mMusicPlayer.getCurrentPos();
	}

	public int getDuration() {
		return mMusicPlayer.getDuration();
	}

	public boolean start(int pos) {
		return start(pos, false);
	}

	/**
	 * 
	 * @param pos
	 * @param force
	 *            play new song, default value is false.
	 * @return
	 */
	public boolean start(int pos, boolean force) {
		if (!force) {
			if (mMusicPlayer.isPaused()) {
				mMusicPlayer.start();
				sendBroadcast(new Intent(FilterAction.PLAYER_PLAYING));
				return true;
			} else {
				// pause the curent playing song
				if (pos == mItemPos) {
					mMusicPlayer.pause();
					sendBroadcast(new Intent(FilterAction.PLAYER_PAUSED));
					return true;
				}
			}
		}

		if (pos >= mMusicList.size() || pos < 0) {
			return false;
		}

		mItemPos = pos;
		MusicInfo musicInfo = mMusicList.get(pos);
		long id = musicInfo.getId();
		Cursor cursor = getCursorForId(id);
		if (null == cursor) {
			return false;
		}
		String path = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI + "/"
				+ cursor.getLong(0);
		Log.v(TAG, "MusicService::start() path:" + path);
		if (path != null) {
			mMusicPlayer.setDataSource(path);
			mMusicPlayer.start();
			Bundle bundle = new Bundle();
			bundle.putString("music_artist", musicInfo.getArtist());
			bundle.putString("music_name", musicInfo.getName());
			bundle.putString("music_item_path", musicInfo.getPlayItemPath());
			Intent intent = new Intent(FilterAction.PLAYER_PLAYING);
			intent.putExtras(bundle);
			sendBroadcast(intent);
			updateNotification(bundle);
			return true;
		}

		return false;
	}

	private void updateNotification(Bundle bundle) {
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		builder.setSmallIcon(R.drawable.listen);
		if (bundle != null) {
			String artist = bundle.getString("music_artist");
			String name = bundle.getString("music_name");
			if (!TextUtils.isEmpty(artist)) {
				builder.setContentTitle(name);
				builder.setContentText(artist);
			}
		}
		builder.setDefaults(Notification.DEFAULT_ALL);
		Intent intent = new Intent(this, HomeActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent pt = PendingIntent.getActivity(this, 0, intent, 0);
		builder.setContentIntent(pt);
		builder.setOngoing(true);
		startForeground(NOTIFICATION_ID, builder.build());
	}
	
//	public void promptACM(final Context context, boolean checked) {
//		Context applicationContext = context.getApplicationContext();
//		String packageName = applicationContext.getPackageName();
//
//		RemoteViews remoteViews = new RemoteViews(packageName, R.layout.acm_notification);
//		remoteViews.setImageViewResource(R.id.image, R.drawable.stat_notify_sync);
//		String appName = context.getString(R.string.app_name);
//		remoteViews.setTextViewText(R.id.title, appName);
//		if (checked) {
//			remoteViews.setViewVisibility(R.id.toggle_bg, View.VISIBLE);
////            remoteViews.setImageViewBitmap(R.id.toggle_bg, com.htc.R.drawable.;
//			remoteViews.setImageViewResource(R.id.toggle_src, com.htc.R.drawable.common_checkbox_on);
//		} else {
//			remoteViews.setViewVisibility(R.id.toggle_bg, View.INVISIBLE);
//			remoteViews.setImageViewResource(R.id.toggle_bg, 0);
//			remoteViews.setImageViewResource(R.id.toggle_src, com.htc.R.drawable.common_checkbox_rest);
//		}
//		
//		Intent intent = new Intent(context, ACMReceiver.class);
//		intent.setAction(INTENT_ACM_STATE_CHANGE);
//		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
//		remoteViews.setOnClickPendingIntent(R.id.imgbtn_toggle, pendingIntent);
//		
//		Notification notification = new Notification(R.drawable.stat_notify_sync, appName, System.currentTimeMillis());
//		notification.contentView = remoteViews;
//		// notification.contentIntent = contentIntent;
//		notification.flags = Notification.FLAG_ONGOING_EVENT;
//		NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//		nm.notify(Utilities.NOTIFICATION_ID, notification);
//	}

	
	private RemoteViews createRemoteViews(Context context) {
		final String pkgName = context.getPackageName();
		RemoteViews rv = new RemoteViews(pkgName, R.layout.cutom_notification);
		return rv;
	}

	public void pause() {
		mMusicPlayer.pause();
		sendBroadcast(new Intent(FilterAction.PLAYER_PAUSED));
	}

	public void resume() {
		mMusicPlayer.start();
	}

	public void seekTo(final int progress) {
		mMusicPlayer.seekTo(progress);
	}

	public boolean play() {
		return start(mItemPos);
	}

	public boolean next() {
		return start(mItemPos + 1);
	}

	public boolean previous() {
		return start(mItemPos - 1);
	}

	private Cursor getCursorForId(long lid) {
		String id = String.valueOf(lid);
		Cursor c = getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, mCursorCols,
				"_id=" + id, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	private class MusicPlayer implements MediaPlayer.OnCompletionListener {
		private MediaPlayer mPlayer;

		private boolean mPaused = false;

		public MusicPlayer() {
			mPlayer = new MediaPlayer();
			mPlayer.setOnCompletionListener(this);
			mPlayer.setWakeMode(MusicService.this,
					PowerManager.PARTIAL_WAKE_LOCK);
		}

		public void pause() {
			mPlayer.pause();
			mPaused = true;
		}

		public boolean isPlaying() {
			return mPlayer.isPlaying();
		}

		public int getCurrentPos() {
			return mPlayer.getCurrentPosition();
		}

		public int getDuration() {
			return mPlayer.getDuration();
		}

		public void seekTo(int progress) {
			mPlayer.seekTo(progress);
		}

		public void setDataSource(String path) {
			try {
				mPlayer.reset();
				mPlayer.setOnPreparedListener(null);
				if (path.startsWith("content://")) {
					mPlayer.setDataSource(MusicService.this, Uri.parse(path));
				} else {
					mPlayer.setDataSource(path);
				}
				mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
				mPlayer.prepare();
			} catch (Exception e) {
				Log.e(TAG, "fail to set data source", e);
			}
		}

		public void start() {
			mPlayer.start();
			mPaused = false;
		}

		public boolean isPaused() {
			return mPaused;
		}

		@Override
		public void onCompletion(MediaPlayer mp) {
			next();
		}
	}

}
