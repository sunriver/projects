package com.funnyplayer.util;

import java.util.List;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.funnyplayer.service.MusicService;
import com.funnyplayer.service.MusicService.MusicBinder;

public class MusicUtil {
	
	public interface FilterAction {
		public static final String PLAYER_PAUSED = "com.funnyplayer.paused";
		public static final String PLAYER_PLAYING = "com.funnyplayer.playing";
	}
	
	private static MusicService mService;
	private static AbstractRequest mLastRequest;
	
	private static class ServiceConnection implements android.content.ServiceConnection {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			if (service != null) {
				MusicBinder binder = (MusicBinder) service;
				mService = binder.getService();
				if (mLastRequest != null) {
					mLastRequest.run();
					mLastRequest = null;
				}
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			mService = null;
		}
		
	}
	
	private static boolean bindService(Context context) {
		Intent intent = new Intent();
		intent.setClass(context, MusicService.class);
		return context.bindService(intent, new ServiceConnection(), Context.BIND_AUTO_CREATE);
	}
	
	public static void addPlaylist(Context context, List<Long> idList) {
		if (mService != null) {
			mService.addPlayList(idList);
		} else {
			mLastRequest = new AddPlaylist(idList);
			bindService(context);
		}
	}
	
	public static void start(Context context, int pos) {
		if (mService != null) {
			mService.start(pos);
		} else {
			mLastRequest = new StartRequest(pos);
			bindService(context);
		}
	}
	
	public static boolean isPlaying() {
		if (null == mService) {
			return false;
		}
		return mService.isPlaying();
	}
	
	public static boolean isPaused() {
		if (null == mService) {
			return false;
		}
		return mService.isPaused();
	}
	
	public static int getCurrentPos() {
		return mService.getCurrentPos();
	}
	
	public static int getDuration() {
		return mService.getDuration();
	}
	
	
	public static void seekTo(int progress) {
		mService.seekTo(progress);
	}
	
	public static void play(Context context) {
		if (mService != null) {
			mService.play();
		} else {
			mLastRequest = new PlayRequest();
			bindService(context);
		}
	}
	
	public static void previouse(Context context) {
		if (mService != null) {
			mService.previous();
		} else {
			mLastRequest = new PrevRequest();
			bindService(context);
		}
	}
	
	public static void next(Context context) {
		if (mService != null) {
			mService.next();
		} else {
			mLastRequest = new NextRequest();
			bindService(context);
		}
	}
	
	public static void pause(Context context) {
		if (mService != null) {
			mService.pause();
		} else {
			mLastRequest = new PauseRequest();
			bindService(context);
		}
	}
	
	private static abstract class AbstractRequest {
		public abstract void run();
	}
	
	
	private static class PlayRequest extends AbstractRequest {
		@Override
		public void run() {
			mService.play();
		}
	}
	
	private static class NextRequest extends AbstractRequest {
		@Override
		public void run() {
			mService.next();
		}
	}
	
	private static class PrevRequest extends AbstractRequest {
		@Override
		public void run() {
			mService.previous();
		}
	}
	
	private static class PauseRequest extends AbstractRequest {
		@Override
		public void run() {
			mService.pause();
		}
	}
	
	private static class AddPlaylist extends AbstractRequest {
		private List<Long> mIdList;
		
		public AddPlaylist(List<Long> idList) {
			mIdList = idList;
		}
		@Override
		public void run() {
			mService.addPlayList(mIdList);
		}
	}
	
	private static class StartRequest extends AbstractRequest {
		private int mPos;
		
		public StartRequest(int pos) {
			mPos = pos;
		}
		@Override
		public void run() {
			mService.start(mPos);
		}
	}
	
	
}
