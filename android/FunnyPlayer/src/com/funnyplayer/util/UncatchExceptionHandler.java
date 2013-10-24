package com.funnyplayer.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Date;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class UncatchExceptionHandler implements Thread.UncaughtExceptionHandler {
	private final static String TAG = UncatchExceptionHandler.class.getSimpleName();
	private final static String FILE_NAME = "listen.log";
	private PrintWriter mLogWriter;
	private StringBuilder mBuilder = new StringBuilder();
	private UncaughtExceptionHandler mDefaultHandler;
	
	public UncatchExceptionHandler(Context context, UncaughtExceptionHandler defaultHandler) {
		try {
			mDefaultHandler = defaultHandler;
			File dir = Environment.getExternalStorageDirectory();
			File logFile = new File(dir, FILE_NAME);
			if (!logFile.exists()) {
				logFile = dir.canWrite() ? dir.createTempFile("listen", "log") : null;
			}
			mLogWriter = logFile != null ?  new PrintWriter(logFile) : null;
		} catch (IOException e) {
			Log.d(TAG, "Fail to create file listen.log", e);
		}
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		mDefaultHandler.uncaughtException(thread, ex);
		if (null == mLogWriter) {
			return;
		}
		mBuilder.setLength(0);
		Date now = new Date();
		String threadName = thread.getName();
		long threadId = thread.getId();
		String errMsg = ex.getMessage();
		String stackStr = Log.getStackTraceString(ex);
		mBuilder.append(now.toString())
				.append(threadId)
				.append(":").append(threadName)
				.append(errMsg + "\n" + stackStr);
		mLogWriter.write(mBuilder.toString());
	}

}
