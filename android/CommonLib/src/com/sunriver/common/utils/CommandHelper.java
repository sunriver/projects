package com.sunriver.common.utils;

import java.io.IOException;
import java.io.InputStream;

import android.text.TextUtils;
import android.util.Log;

public class CommandHelper {
	private final static String TAG = CommandHelper.class.getSimpleName();
	
	public static String runCmd(final String cmd) {
		if (TextUtils.isEmpty(cmd)) {
			return "";
		}
		StringBuffer result = new StringBuffer();
		InputStream input = null;
		try {
			Process process= Runtime.getRuntime().exec(cmd);
			input = process.getInputStream();
			byte[] bytes = new byte[1024];
			int len = 0;
			while ((len = (input.read(bytes))) > 0) {
				result.append(new String(bytes, 0, len));
			}
		} catch (IOException e) {
			Log.d(TAG, "", e);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					Log.d(TAG, "", e);
				}
			}
		}
		return result.toString();
	}
	
	public static String runCmdID() {
		return runCmd("id");
	}
}
