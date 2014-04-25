package com.sunriver.common.exception;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import android.text.format.DateFormat;

public class UncatchExceptionHandler implements Thread.UncaughtExceptionHandler {
	private PrintWriter mLogWriter;
	private File mLogFile;
	public UncatchExceptionHandler(final String filePath, final String fileName) {
		try {
			File dir = new File(filePath);
			if (!dir.exists()) {
				dir.mkdir();
			}
			mLogFile = new File(dir, fileName);
			if (!mLogFile.exists()) {
				mLogFile.createNewFile();
			}
	
		} catch (IOException ignored) {
		}
	}

    
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if (!mLogFile.exists()) {
			return;
		}
		try {
			mLogWriter = new PrintWriter(mLogFile);
			CharSequence currTime = DateFormat.format("yyyy-MM-dd hh:ss", System.currentTimeMillis());
			mLogWriter.append("\n" + currTime);
			ex.printStackTrace(mLogWriter);
			
		} catch (FileNotFoundException e) {
		} finally {
			if (mLogWriter != null) {
				mLogWriter.flush();
				mLogWriter.close();
			}
		}
	}

}
