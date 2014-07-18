package com.sunriver.common.utils;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

public class PlatformUtils {
	private final static String LOG_TAG = PackageUtils.class.getSimpleName();
	
	/**
	 * retrieve the property-value by using the reflection api
	 * 
	 * @param propertyName
	 * @return
	 */
	public static String getSystemProperty(String propertyName) {
		String propertyValue = "";
		try {
			Class<?> c = Class.forName("android.os.SystemProperties");
			java.lang.reflect.Method get = c.getMethod("get", String.class);
			propertyValue = (String) get.invoke(c, propertyName);
		} catch (Exception ignored) {
			Log.e(LOG_TAG, "Failed to get the property-name: " + propertyName, ignored);
		}

		return propertyValue;
	}

	public static String getSystemProperty(String propertyName, String defValue) {
		String value = getSystemProperty(propertyName);
		if (TextUtils.isEmpty(value))
			return defValue;

		return value;
	}
	

	public static String getSerialNO() {
		return getSystemProperty("ro.serialno");
	}
	
	public static boolean isScreenLocked(Context context) {
		KeyguardManager keyGuardInstant = (KeyguardManager) context
				.getSystemService(Context.KEYGUARD_SERVICE);

		boolean isLocked = false;
		boolean isSecure = false;

		if (Build.VERSION.SDK_INT >= 14) {
			try {
				isLocked = (Boolean) (KeyguardManager.class.getMethod(
						"isKeyguardLocked", (Class<?>[]) null).invoke(
						keyGuardInstant, (Object[]) null));
				isSecure = (Boolean) (KeyguardManager.class.getMethod(
						"isKeyguardSecure", (Class<?>[]) null).invoke(
						keyGuardInstant, (Object[]) null));
			} catch (Throwable any) {
				Log.e(LOG_TAG,
						"Failed to determine the isKeyguardSecure or isKeyguardLocked");
			}
		}

		return isLocked && isSecure;
	}
}
