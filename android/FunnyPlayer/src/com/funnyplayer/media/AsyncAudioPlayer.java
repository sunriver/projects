package com.funnyplayer.media;

import java.util.LinkedList;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.SystemClock;
import android.util.Log;

public class AsyncAudioPlayer implements MediaPlayer.OnCompletionListener {
	private static final int PLAY = 1;
	private static final int STOP = 2;
	private static final int PAUSE = 3;
	private static final int RESUME = 4;

	private static final class Command {
		int code;
		Context context;
		Uri uri;
		boolean looping;
		int stream;
		long requestTime;

		public String toString() {
			return "{ code=" + code + " looping=" + looping + " stream="
					+ stream + " uri=" + uri + " }";
		}
	}

	private LinkedList<Command> mCmdQueue = new LinkedList();

	private MediaPlayer mPlayer;

	public AsyncAudioPlayer() {
		mPlayer = new MediaPlayer();
		mPlayer.setOnCompletionListener(this);
		new Thread().start();
	}

	public void play(Uri uri, boolean looping, int stream) {

	}

	public void stop() {

	}

	public void pause() {

	}

	public void resume() {

	}

	public void restart() {

	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub

	}

	private final class Thread extends java.lang.Thread {
		Thread() {
			super("AsyncAudioPlayer");
		}

		@Override
		public void run() {

			while (true) {
				Command cmd = null;

				synchronized (mCmdQueue) {
					cmd = mCmdQueue.removeFirst();
				}

				switch (cmd.code) {
				case PLAY:
					break;
				case STOP:
					break;
				}

				synchronized (mCmdQueue) {
					if (mCmdQueue.size() == 0) {
						// nothing left to do, quit
						// doing this check after we're done prevents the case
						// where they
						// added it during the operation from spawning two
						// threads and
						// trying to do them in parallel.
//						mThread = null;
//						releaseWakeLock();
						return;
					}
				}
			}

		}
	}

}
