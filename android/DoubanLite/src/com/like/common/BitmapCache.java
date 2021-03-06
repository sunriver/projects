package com.like.common;


import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.sunriver.common.utils.ApiLevel;

public class BitmapCache implements ImageCache {
	private final static String TAG = BitmapCache.class.getSimpleName();
	private LruCache<String, Bitmap> mCache;

	public BitmapCache(final Context context) {
		final ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		int maxSize = Math.round(0.25f * activityManager.getMemoryClass() * 1024 * 1024);
		mCache = new LruCache<String, Bitmap>(maxSize) {
			@Override
			protected int sizeOf(String key, Bitmap value) {
				if (ApiLevel.hasHoneycombMR1()) {
					return value.getByteCount();
				} else {
					return value.getRowBytes() * value.getHeight();
				}
			}

		};
	}

	@Override
	public Bitmap getBitmap(String url) {
		return mCache.get(url);
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		mCache.put(url, bitmap);
	}
	
	public void clear() {
		Log.d(TAG, "clear()+");
		mCache.evictAll();
	}

}