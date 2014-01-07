package com.funnyplayer.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Date;

import android.content.Context;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;

public class UncatchExceptionHandler implements Thread.UncaughtExceptionHandler {
	private final static String TAG = UncatchExceptionHandler.class.getSimpleName();
	private PrintWriter mLogWriter;
	private StringBuilder mBuilder = new StringBuilder();
	private UncaughtExceptionHandler mDefaultHandler;
	private File mLogFile;
	
	public UncatchExceptionHandler(Context context, UncaughtExceptionHandler defaultHandler) {
		try {
			mDefaultHandler = defaultHandler;
			String dirPath = Environment.getExternalStorageDirectory().getAbsoluteFile() + "/" + context.getPackageName();
			File dir = new File(dirPath);
			if (!dir.exists()) {
				dir.mkdir();
			}
			final String fileName = "crash.txt";
			mLogFile = new File(dir, fileName);
			if (!mLogFile.exists()) {
				mLogFile.createNewFile();
			}
	
		} catch (IOException e) {
			Log.w(TAG, "Fail to create exception log file", e);
		}
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		mDefaultHandler.uncaughtException(thread, ex);
		if (!mLogFile.exists()) {
			return;
		}
		try {
			mLogWriter = new PrintWriter(mLogFile);
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
		} catch (FileNotFoundException e) {
			Log.w(TAG, "fail to catch exception", e);
		} finally {
			if (mLogWriter != null) {
				mLogWriter.close();
			}
		}
	}

}
