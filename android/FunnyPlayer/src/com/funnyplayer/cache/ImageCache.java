package com.funnyplayer.cache;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

public class ImageCache {
	private final static int DEFAULT_MAX_SIZE = 1024 * 4;
	
	private LruCache<String, Bitmap> mCache;
	
	public ImageCache(final Context context) {
		init(context);
	}
	
    private void init(final Context context) {
        final ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final int lruCacheSize = Math.round(0.25f * activityManager.getMemoryClass() * 1024 * 1024);
        mCache = new LruCache<String, Bitmap>(lruCacheSize) {
            @Override
            protected int sizeOf(final String paramString, final Bitmap paramBitmap) {
                return paramBitmap.getByteCount();
            }

        };
    }
	
	
	public void put(String key, Bitmap value) {
		mCache.put(key, value);
	}
	
	public Bitmap get(String key) {
		return mCache.get(key);
	}
}
