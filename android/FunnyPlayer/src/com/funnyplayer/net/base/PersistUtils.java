package com.funnyplayer.net.base;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.util.Log;

public class PersistUtils {
	private static final String TAG = PersistUtils.class.getSimpleName();
	
	public static File persistInputStream(InputStream in,  File outFile) {
		BufferedOutputStream out = null;
		try {
	         out = new BufferedOutputStream(new FileOutputStream(outFile));
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
		} catch (Exception e) {
			Log.e(TAG, "fail to persist input stream", e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}

			} catch (Exception e) {
				Log.e(TAG, "fail to close input stream", e);
			}
		}
		return outFile;
	}

}
