package com.sunriver.common.exception;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import android.text.format.DateFormat;

public class UncatchExceptionHandler implements Thread.UncaughtExceptionHandler {
	private PrintWriter mLogWriter;
	private File mLogFile;
	private OnUncatchExeptionListener mOnUncatchExeptionListener;
	
	public UncatchExceptionHandler(final String filePath, final String fileName, OnUncatchExeptionListener listener) {
		try {
			File dir = new File(filePath);
			if (!dir.exists()) {
				dir.mkdir();
			}
			mLogFile = new File(dir, fileName);
			if (!mLogFile.exists()) {
				mLogFile.createNewFile();
			}
			this.mOnUncatchExeptionListener = listener;
		} catch (IOException ignored) {
		}
	}

    
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if (!mLogFile.exists()) {
			return;
		}
		try {
			mLogWriter = new PrintWriter(new FileOutputStream(mLogFile, true));
			CharSequence currTime = DateFormat.format("yyyy-MM-dd hh:mm:ss", System.currentTimeMillis());
			mLogWriter.append("------" + currTime + "\n");
			mLogWriter.append("Thread: id=" + thread.getId() + ", name=" + thread.getName());
			ex.printStackTrace(mLogWriter);
			mLogWriter.append("\n");
			mLogWriter.flush();
			if (mOnUncatchExeptionListener != null) {
				mOnUncatchExeptionListener.onUncacth(ex);
			}
		} catch (FileNotFoundException e) {
		} finally {
			if (mLogWriter != null) {
				mLogWriter.flush();
				mLogWriter.close();
			}
		}
	}
	
	
	public  interface OnUncatchExeptionListener {
		public void onUncacth(Throwable ex);
	}

}
