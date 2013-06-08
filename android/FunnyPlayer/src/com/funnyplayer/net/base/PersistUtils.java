package com.funnyplayer.net.base;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.util.Log;

public class PersistUtils {
	private static final String TAG = "PersistUtils";
	private static final int BUFFER_SIZE = 1024;
	
	public static File persistInputStream(InputStream in, final String filePath) {
		FileOutputStream out = null;
		File file = null;
		try {
			 file = new File(filePath);
			if (file.exists()) {
				return file;
			}
			out = new FileOutputStream(file);
			byte buf[] = new byte[BUFFER_SIZE];
			for (int len = in.read(); len > 0; len = in.read()) {
				out.write(buf, 0, len);
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (Exception e) {
				Log.e(TAG, e.getMessage());
			}
		}
		return file;
	}
}
