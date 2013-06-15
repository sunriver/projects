package com.funnyplayer;

import com.funnyplayer.ui.adapter.PlaylistAdapter;

import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio;
import android.provider.MediaStore.MediaColumns;
import android.provider.MediaStore.Audio.AudioColumns;
import android.provider.MediaStore.Audio.PlaylistsColumns;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

public class PlayActivity extends Activity implements LoaderCallbacks<Cursor>, OnItemClickListener, OnCompletionListener, View.OnClickListener {
	private final static String TAG = "FunnyPlayer";
	private ListView mPlayListView;
	private ImageView mPlayImgView;
	private PlaylistAdapter mAdapter;
	private Cursor mCursor;
    private MediaPlayer mCurrentMediaPlayer;
    private int mCurrPos;
    private ImageView mPrevImg;
    private ImageView mPauseImg;
    private ImageView mNextImg;

    private String[] mCursorCols = new String[] {
            "audio._id AS _id", MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.MIME_TYPE, MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.ARTIST_ID, MediaStore.Audio.Media.IS_PODCAST,
            MediaStore.Audio.Media.BOOKMARK
    };
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.playlist);
		mPlayListView = (ListView) findViewById(R.id.playListView);

		mPlayImgView = (ImageView) findViewById(R.id.playImageView);
		mAdapter = new PlaylistAdapter(this, R.layout.playlist_item);
		
		mPrevImg = (ImageView) findViewById(R.id.playPrevious);
		mPauseImg = (ImageView) findViewById(R.id.playOrPause);
		mNextImg = (ImageView) findViewById(R.id.playNext);
		mPrevImg.setOnClickListener(this);
		mPauseImg.setOnClickListener(this);
		mNextImg.setOnClickListener(this);
		
		mPlayListView.setAdapter(mAdapter);
		mPlayListView.setOnItemClickListener(this);
		
		mCurrentMediaPlayer = new MediaPlayer();
        mCurrentMediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
		mCurrentMediaPlayer.setOnCompletionListener(this);
		
		mCurrPos = 0;
		
		Intent intent = getIntent();
		Bundle args = (intent != null) ? intent.getExtras() : null; 
		
		getLoaderManager().initLoader(0, args, this);
	}
	

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        StringBuilder where = new StringBuilder();
        where.append(AudioColumns.IS_MUSIC + "=1").append(" AND " + MediaColumns.TITLE + " != ''");
		if (args != null) {
			long albumId = args.getLong(BaseColumns._ID);
//			String albumName = args.getString(Consts.ALBUM_KEY);
			where.append(" AND " + AudioColumns.ALBUM_ID + "=" + albumId);
		}
        String[] projection = new String[] {
                BaseColumns._ID, MediaColumns.TITLE, AudioColumns.ALBUM, AudioColumns.ARTIST
        };
        Uri uri = Audio.Media.EXTERNAL_CONTENT_URI;
        String sortOrder = Audio.Media.DEFAULT_SORT_ORDER;
        sortOrder = Audio.Media.TRACK + ", " + sortOrder;
        return new CursorLoader(this, uri, projection, where.toString(), null, sortOrder);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Check for database errors
        if (data == null) {
            return;
        }

       int  mMediaIdIndex = data.getColumnIndexOrThrow(BaseColumns._ID);
        int mTitleIndex = data.getColumnIndexOrThrow(MediaColumns.TITLE);
        int mArtistIndex = data.getColumnIndexOrThrow(AudioColumns.ARTIST);
        mAdapter.setPlaylistIdIndex(mMediaIdIndex);
        mAdapter.setPlaylistNameIndex(mTitleIndex);
        mAdapter.changeCursor(data);
        mCursor = data;
        
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		if (mAdapter != null) {
			mAdapter.changeCursor(null);
			mCursor = null;
		}
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		mCurrPos = position;
		view.setSelected(true);
		startPlay(id);
	}
	
	
	private void startPlay(long id) {
		Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
		Cursor cursor = getCursorForId(id);
        open(uri + "/" + cursor.getLong(0));
	}
	
	
	
	private void next() {
		int count = mAdapter.getCount();
		int nextPos = mCurrPos + 1;
		if (nextPos < count) {
			View currView = mPlayListView.getChildAt(mCurrPos);
			currView.setSelected(false);
			View nextView = mPlayListView.getChildAt(nextPos);
			nextView.setSelected(true);
			startPlay(mAdapter.getItemId(nextPos));
			mCurrPos = nextPos;
		}
	}
	
	private void previous() {
		int count = mAdapter.getCount();
		int prevPos = mCurrPos - 1;
		if (prevPos >= 0) {
			View currView = mPlayListView.getChildAt(mCurrPos);
			currView.setSelected(false);
			View nextView = mPlayListView.getChildAt(prevPos);
			nextView.setSelected(true);
			startPlay(mAdapter.getItemId(prevPos));
			mCurrPos = prevPos;
		}
	}
	
	private void playOrPause() {
		if (mCurrentMediaPlayer.isPlaying()) {
			mCurrentMediaPlayer.pause();
		} else {
			mCurrentMediaPlayer.start();
		}
	}
	
	
    private Cursor getCursorForId(long lid) {
        String id = String.valueOf(lid);

        Cursor c = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,  mCursorCols, "_id=" + id , null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }
    
    public boolean open(String path) {
    	Log.v(TAG, "PlayActivity::open() path:" + path);
    	try {
    		mCurrentMediaPlayer.reset();

    		mCurrentMediaPlayer.setOnPreparedListener(null);
            if (path.startsWith("content://")) {
            	mCurrentMediaPlayer.setDataSource(this, Uri.parse(path));
            } else {
            	mCurrentMediaPlayer.setDataSource(path);
            }
            mCurrentMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mCurrentMediaPlayer.prepare();
			mCurrentMediaPlayer.start();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			return true;
		}
    }


	@Override
	public void onCompletion(MediaPlayer mp) {
		mp.release();
		next();
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.playPrevious:
			previous();
			break;
		case R.id.playOrPause:
			playOrPause();
			break;
		case R.id.playNext:
			next();
			break;
		}
	}

}
