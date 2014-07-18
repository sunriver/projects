package com.sunriver.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	
	
	public static List<Thread> getAllThreads() {
		List<Thread> threads = new ArrayList<Thread>();
		Map<Thread, StackTraceElement[]> maps = Thread.getAllStackTraces();
		for (Map.Entry<Thread, StackTraceElement[]> entry : maps.entrySet()) {
			Thread td = entry.getKey();
			threads.add(td);
		}
		return threads;
	}
	
}
