package com.oobe.loadbitmap;


import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

public class BitmapAdapter extends SimpleAdapter {
	
	private Handler mHandler = new Handler();
	
	private Map<String, SoftReference<Bitmap>> mImageCache = new HashMap<String,SoftReference<Bitmap>>();
    
	public BitmapAdapter(Context context, List<? extends Map<String, ?>> data,
			int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
	}

	@Override
	public void setViewImage(ImageView v, String value) {
		final String imageUrl = value;
		final ImageView image = v;
		new Thread() {
			public void run() {
				Bitmap tempBitmap = null;
				if (mImageCache.containsKey(imageUrl)) {
					SoftReference<Bitmap> bitmapRef = mImageCache.get(imageUrl);
					tempBitmap = bitmapRef.get();
				}
				if ( null == tempBitmap) {
					tempBitmap = loadBitmapFromUrl(imageUrl);
					mImageCache.put(imageUrl, new SoftReference<Bitmap>(tempBitmap));
				}
					
				final Bitmap bitmap = tempBitmap;
				
				mHandler.post(new Runnable(){
					@Override
					public void run() {
						image.setImageBitmap(bitmap);
					}});
			}
		}.start();
	}
	
	protected Bitmap loadBitmapFromUrl(String imageUrl) {
		try {
			return BitmapFactory.decodeStream(new URL(imageUrl).openStream());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}		
	}
	
	protected Drawable loadImageFromUrl(String imageUrl) {
		try {
			return Drawable.createFromStream(new URL(imageUrl).openStream(), "src");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
