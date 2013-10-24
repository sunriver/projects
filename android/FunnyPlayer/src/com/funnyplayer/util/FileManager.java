package com.funnyplayer.util;

import android.content.Context;
import android.os.Environment;

public class FileManager {
	private static final String TAG = FileManager.class.getSimpleName();

    public static String getSDCardPath(Context context) {
        if (isExternalStorageMounted()) {
            return context.getExternalCacheDir().getAbsolutePath();
        } else {
            return "";
        }
    }
    public static boolean isExternalStorageMounted() {
        boolean canRead = Environment.getExternalStorageDirectory().canRead();
        boolean onlyRead = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED_READ_ONLY);
        boolean unMounted = Environment.getExternalStorageState().equals(
                Environment.MEDIA_UNMOUNTED);
        return !(!canRead || onlyRead || unMounted);
    }

}
