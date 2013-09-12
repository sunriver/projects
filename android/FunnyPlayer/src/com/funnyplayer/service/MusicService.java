package com.funnyplayer.service;


import java.util.ArrayList;
import java.util.List;

import com.funnyplayer.util.MusicUtil.FilterAction;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.util.Log;

public class MusicService extends Service {
	
	private static final String TAG = "MusicService";
	
    private final IBinder mBinder = new MusicBinder();
    
    public class MusicBinder extends Binder {
    	public MusicService getService() {
            return MusicService.this;
        }
    }
    
    
    private MusicPlayer mMusicPlayer;
    
    private int mCurrentPos;
    
    private final String[] mCursorCols = new String[] {
            "audio._id AS _id", MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.MIME_TYPE, MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.ARTIST_ID, MediaStore.Audio.Media.IS_PODCAST,
            MediaStore.Audio.Media.BOOKMARK
    };
    
    private List<Long> mMusicIdList = new ArrayList<Long>();
    
	@Override
	public void onCreate() {
		mMusicPlayer = new MusicPlayer();
		super.onCreate();
	}

	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	public void addPlayList(List<Long> idList) {
		mMusicIdList.clear();
		for (int i = 0 , len = idList.size(); i < len; i++) {
			mMusicIdList.add(idList.get(i));
		}
	}
	
	
	public boolean isPlaying() {
		return mMusicPlayer.isPlaying();
	}
	
	public boolean isPaused() {
		return mMusicPlayer.isPaused();
	}
	
	public int getCurrentPos() {
		return mMusicPlayer.getCurrentPos();
	}
	
	public int getDuration() {
		return mMusicPlayer.getDuration();
	}

	public boolean start(int pos) {
		if (mMusicPlayer.isPaused()) {
			mMusicPlayer.start();
			sendBroadcast(new Intent(FilterAction.PLAYER_PLAYING));
			return true;
		}
		 
		if (pos >= mMusicIdList.size() || pos < 0) {
			return false;
		}
		
		mCurrentPos = pos;
		long id = mMusicIdList.get(pos);
		Cursor cursor = getCursorForId(id);
		if (null == cursor) {
			return false;
		}
		String path = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI + "/" + cursor.getLong(0);
		Log.v(TAG, "MusicService::start() path:" + path);
		if (path != null) {
			mMusicPlayer.setDataSource(path);
			mMusicPlayer.start();
			sendBroadcast(new Intent(FilterAction.PLAYER_PLAYING));
			return true;
		}
		
		return false;
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
		return start(mCurrentPos);
	}

	public boolean next() {
		return start(mCurrentPos + 1);
	}

	public boolean previous() {
		return start(mCurrentPos - 1);
	}
	
	
    private Cursor getCursorForId(long lid) {
        String id = String.valueOf(lid);
        Cursor c = getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                mCursorCols, "_id=" + id , null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }
    
    
    private  class MusicPlayer implements MediaPlayer.OnCompletionListener {
    	private MediaPlayer mPlayer;
    	
    	private boolean mPaused = false;
    	
    	public MusicPlayer() {
    		mPlayer = new MediaPlayer();
    		mPlayer.setOnCompletionListener(this);
    		mPlayer.setWakeMode(MusicService.this, PowerManager.PARTIAL_WAKE_LOCK);
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
