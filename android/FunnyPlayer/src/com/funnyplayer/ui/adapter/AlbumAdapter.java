package com.funnyplayer.ui.adapter;

import java.lang.ref.WeakReference;

import static com.funnyplayer.cache.Consts.SIZE_THUMB;
import static com.funnyplayer.cache.Consts.SRC_FIRST_AVAILABLE;
import static com.funnyplayer.cache.Consts.TYPE_ALBUM;

import com.andrew.apolloMod.ui.fragments.grid.AlbumsFragment;
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
				holder.image = (ImageView) v.findViewById(R.id.album_gridview_image);
				v.setTag(holder);
			}
		}
		if (null == holder) {
			return null;
		}
		
        Cursor cursor = (Cursor) getItem(position);
        String albumId = cursor.getString(mAlbumIdIndex);
        String albumName = cursor.getString(mAlbumNameIndex);
        String martistName = cursor.getString(mArtistNameIndex);
        
        ImageInfo imageInfo = new ImageInfo();
        imageInfo.type = TYPE_ALBUM;
        imageInfo.size = SIZE_THUMB;
        imageInfo.source = SRC_FIRST_AVAILABLE;
        imageInfo.data = new String[]{ albumId , martistName, albumName };
        
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
