package com.funnyplayer.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

public class ImageCache {
	private final static int DEFAULT_MAX_SIZE = 1024 * 4;
	
	private LruCache<String, Bitmap> mCache;
	
	public ImageCache() {
		this(DEFAULT_MAX_SIZE);
	}
	
	public ImageCache(int maxSize) {
		mCache = new LruCache<String, Bitmap>(maxSize) {
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getByteCount();
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
