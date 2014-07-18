package com.sunriver.common.utils;

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
}
