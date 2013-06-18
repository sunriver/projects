package com.funnyplayer.ui.adapter;

import static com.funnyplayer.cache.Consts.SIZE_THUMB;
import static com.funnyplayer.cache.Consts.SRC_FIRST_AVAILABLE;

import com.funnyplayer.R;
import com.funnyplayer.cache.Consts.TYPE;
import com.funnyplayer.cache.ImageInfo;
import com.funnyplayer.cache.ImageProvider;
import com.funnyplayer.cache.ImageProvider.ImageReadyListener;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class AlbumAdapter extends SimpleCursorAdapter implements ImageReadyListener {
	private static final String TAG = "AlbumAdapter";
	private int mAlbumIdIndex;
	private int mAlbumNameIndex;
	private int mArtistNameIndex;
	private ImageProvider mImageProvider;

	public AlbumAdapter(Context context, int layout) {
		super(context, layout, null, new String[] {}, new int[] {}, 0);
		mImageProvider = ImageProvider.getInstance(context);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		View v = super.getView(position, convertView, parent);;
		if (v != null) {
			holder = new ViewHolder();
			holder.image = (ImageView) v.findViewById(R.id.album_gridview_image);
			holder.album = (TextView) v.findViewById(R.id.album_name);
			holder.artist = (TextView) v.findViewById(R.id.album_artist);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}			

		Cursor cursor = (Cursor) getItem(position);
		String albumId = cursor.getString(mAlbumIdIndex);
		String albumName = cursor.getString(mAlbumNameIndex);
		String artistName = cursor.getString(mArtistNameIndex);
		
		Log.v(TAG, "position:" + position  + "  albumId:" + albumId
				+ "  albumName:" + albumName + "  artistName:" + artistName);
		
		holder.album.setText(albumName);
		holder.artist.setText(artistName);

		ImageInfo imageInfo = new ImageInfo();
		imageInfo.type = TYPE.ALBUM.toString();
		imageInfo.size = SIZE_THUMB;
		imageInfo.source = SRC_FIRST_AVAILABLE;
		imageInfo.data = new String[] { albumId, artistName, albumName };

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
		TextView album;
		TextView artist;
	}

	@Override
	public void onLoadFinished(ImageView v, Bitmap bitmap) {
	}

}
