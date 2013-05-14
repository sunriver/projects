package com.funnyplayer.cache;

import java.util.LinkedHashMap;
import java.util.Map;

import android.graphics.Bitmap;

public class ImageCache {
	private final static int DEFAULT_MAX_SIZE = 1024 * 4;
	
	private final int mMaxSize;
	private final LinkedHashMap<String, Bitmap> mMap;
	private int mSize;
	
	public ImageCache() {
		this(DEFAULT_MAX_SIZE);
	}
	
	public ImageCache(int maxSize) {
		mMaxSize = maxSize;
		mSize = 0;
		mMap = new LinkedHashMap<String, Bitmap>(0, 0.75f, true);;
	}
	
	public void put(String key, Bitmap value) {
		if (null == key || null == value) {
			return;
		}
		mSize += sizeOf(key, value);
		Bitmap previous = mMap.put(key, value);
		if (previous != null) {
			mSize -= sizeOf(key, previous);
			mMap.remove(key);
		}
        trimToSize(mMaxSize);
	}
	
	public Bitmap get(String key) {
		return mMap.get(key);
	}
	
    private int sizeOf(final String string, final Bitmap bitmap) {
        return bitmap.getByteCount();
    }
    
    /**
     * Store the extra size 
     * @param maxSize
     */
    private void trimToSize(int maxSize) {
    	while (true) {
    		if (mSize < 0 || mMap.isEmpty()) {
    			break;
    		}
    		if (mSize <= mMaxSize) {
    			break;
    		}
    		
            Map.Entry<String, Bitmap> toEvict = mMap.eldest();
            if (toEvict == null) {
                break;
            }
    	}
    }
}
