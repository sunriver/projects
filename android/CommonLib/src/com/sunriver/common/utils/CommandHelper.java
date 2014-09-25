package com.sunriver.common.utils;

import java.io.DataOutputStream;
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
	
	public static boolean hasRootPermission() {
		Process process = null;
		DataOutputStream os = null;
		boolean rooted = true;
		try {
			process = Runtime.getRuntime().exec("su");
			os = new DataOutputStream(process.getOutputStream());
			os.writeBytes("exit\n");
			os.flush();
			process.waitFor();
			if (process.exitValue() != 0) {
				rooted = false;
			}
		} catch (Exception e) {
			rooted = false;
		} finally {
			if (os != null) {
				try {
					os.close();
					process.destroy();
				} catch (Exception e) {
				}
			}
		}
		return rooted;
	}

	public static boolean runRootCommand(String command) {
		Process process = null;
		DataOutputStream os = null;
		try {
			process = Runtime.getRuntime().exec("su");
			os = new DataOutputStream(process.getOutputStream());
			os.writeBytes(command + "\n");
			os.writeBytes("exit\n");
			os.flush();
			process.waitFor();
		} catch (Exception e) {
			return false;
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				process.destroy();
			} catch (Exception e) {
			}
		}
		return true;
	}
}
