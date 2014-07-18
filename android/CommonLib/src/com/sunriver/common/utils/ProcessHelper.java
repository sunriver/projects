package com.sunriver.common.utils;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.text.TextUtils;
public class ProcessHelper {

	public static int getProcessUid(Context ctx, final String pakName) {
		ActivityManager activityManager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> infos = activityManager.getRunningAppProcesses();
		for (RunningAppProcessInfo info : infos) {
			String name = info.processName;
			if (!TextUtils.isEmpty(name) && name.equals(pakName)) {
				return info.uid;
			}
		}
		return -1;
	}
	
	
	public static RunningAppProcessInfo getProcessInfoByPackageName(Context ctx, final String pakName) {
		ActivityManager activityManager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> infos = activityManager.getRunningAppProcesses();
		for (RunningAppProcessInfo info : infos) {
			String name = info.processName;
			if (!TextUtils.isEmpty(name) && name.equals(pakName)) {
				return info;
			}
		}
		return null;
	}
	
	
}
