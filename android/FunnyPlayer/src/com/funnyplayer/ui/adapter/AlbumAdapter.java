package com.funnyplayer.ui.adapter;


import java.lang.ref.WeakReference;

import com.funnyplayer.R;
import com.funnyplayer.cache.ImageInfo;
import com.funnyplayer.cache.ImageProvider;
import com.funnyplayer.cache.ImageProvider.LoadCallback;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;

public class AlbumAdapter extends SimpleCursorAdapter implements LoadCallback {
	
    private int mAlbumIdIndex;
    private int mAlbumNameIndex;
    private int mArtistNameIndex;
    private ImageProvider mImageProvider;

	public AlbumAdapter(Context context, int layout) {
		super(context, layout,  null, new String[] {}, new int[] {}, 0);
		mImageProvider = ImageProvider.getInstance(context);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null ;
		View v = null;
		if (convertView != null) {
			holder = (ViewHolder) convertView.getTag();
			v = convertView;
		} else {
			v = super.getView(position, convertView, parent);
			if (v != null) {
				holder = new ViewHolder();
				WeakReference<ViewHolder> holdRef = new WeakReference<ViewHolder>(holder);
				holder.image = (ImageView) v.findViewById(R.id.album_gridview_image);
				v.setTag(holdRef.get());
			}
		}
		if (null == holder) {
			return null;
		}
		
        Cursor cursor = (Cursor) getItem(position);
        String albumName = cursor.getString(mAlbumNameIndex);
        String mrtistName = cursor.getString(mArtistNameIndex);
        
        ImageInfo imageInfo = new ImageInfo();
//        mageInfo.type = TYPE_ALBUM;
//        mageInfo.size = SIZE_THUMB;
//        mageInfo.source = SRC_FIRST_AVAILABLE;
//        mageInfo.data = new String[]{ albumId , artistName, albumName };
        
        mImageProvider.loadImage(imageInfo, holder.image, this);
        
		return v;
	}
	
	public void setAlbumIdIndex(int index) {
		this.mAlbumIdIndex = index;
	}
	
	public void setAlbumNameIndex(int index) {
		this.mAlbumNameIndex = index;
	}
	
	public void setArtistNameIndex(int index) {
		this.mArtistNameIndex = index;
	}
	
	
	private static class ViewHolder {
		ImageView image;
	}


	@Override
	public void onLoadFinished(ImageView v, Bitmap bitmap) {
		v.setImageBitmap(bitmap);
	}
	

}
