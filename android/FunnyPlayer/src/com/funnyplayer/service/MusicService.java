package com.funnyplayer.service;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
	
	private final static int IDCOLIDX = 0;
	
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
	
	
	public void start(int pos) {
		if (pos >= mMusicIdList.size() || pos < 0) {
			return;
		}
		
		mCurrentPos = pos;
		long id = mMusicIdList.get(pos);
		Cursor cursor = getCursorForId(id);
		if (null == cursor) {
			return;
		}
		String path = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI + "/" + cursor.getLong(IDCOLIDX);
		Log.v(TAG, "MusicService::start() path:" + path);
		if (path != null) {
			mMusicPlayer.setDataSource(path);
			mMusicPlayer.start();
		}
	}
	
	public void pause() {
		mMusicPlayer.pause();
	}

	public void play() {
		start(mCurrentPos);
	}

	public void next() {
		start(mCurrentPos + 1);
	}

	public void previous() {
		start(mCurrentPos - 1);
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
    
    
    private  class MusicPlayer  implements MediaPlayer.OnCompletionListener {
    	private MediaPlayer mPlayer;
    	
    	public MusicPlayer() {
    		mPlayer = new MediaPlayer();
    		mPlayer.setOnCompletionListener(this);
    		mPlayer.setWakeMode(MusicService.this, PowerManager.PARTIAL_WAKE_LOCK);
    	}
    	
    	public void pause() {
    		mPlayer.pause();
    	}
    	
    	public boolean isPlaying() {
    		return mPlayer.isPlaying();
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
				e.printStackTrace();
			}
        }
        
        public void start() {
        	mPlayer.start();
        }

		@Override
		public void onCompletion(MediaPlayer mp) {
			mPlayer.release();
			next();
		}
    }

}