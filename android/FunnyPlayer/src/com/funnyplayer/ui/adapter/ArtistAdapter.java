package com.funnyplayer.ui.adapter;


import com.funnyplayer.R;
import com.funnyplayer.cache.ImageInfo;
import com.funnyplayer.cache.ImageProvider;
import com.funnyplayer.cache.ImageProvider.ImageReadyListener;
import com.funnyplayer.util.Consts;
import com.funnyplayer.util.Consts.TYPE;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ArtistAdapter extends SimpleCursorAdapter implements ImageReadyListener {
	private static final String TAG = "ArtistAdapter";
	private int mArtistIdIndex;
	private int mArtistNumAlbumsIndex;
	private int mArtistNameIndex;
	private ImageProvider mImageProvider;

	public ArtistAdapter(Context context, int layout) {
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
			holder.num = (TextView) v.findViewById(R.id.album_name);
			holder.artist = (TextView) v.findViewById(R.id.album_artist);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}			

		Cursor cursor = (Cursor) getItem(position);
		String artistId = cursor.getString(mArtistIdIndex);
		String artistNumAlbums = cursor.getString(mArtistNumAlbumsIndex);
		String artistName = cursor.getString(mArtistNameIndex);
		
		Log.v(TAG, "position:" + position  + "  albumId:" + artistId
				+ "  albumName:" + artistName + "  artistName:" + artistName);
		
//		holder.num.setText(artistNumAlbums);
		holder.num.setText(null);
		holder.artist.setText(artistName);

		ImageInfo imageInfo = new ImageInfo();
		imageInfo.type = TYPE.ARTIST.toString();
		imageInfo.size = Consts.SIZE_THUMB;
		imageInfo.source = Consts.SRC_FIRST_AVAILABLE;
		imageInfo.data = new String[] {artistName};

		mImageProvider.loadImage(imageInfo, holder.image, this);

		return v;
	}

	public void setArtistIdIndex(int index) {
		this.mArtistIdIndex = index;
	}

	public void setArtistNumAlbumsIndexIndex(int index) {
		this.mArtistNumAlbumsIndex = index;
	}

	public void setArtistNameIndex(int index) {
		this.mArtistNameIndex = index;
	}

	private static class ViewHolder {
		ImageView image;
		TextView num;
		TextView artist;
	}

	@Override
	public void onLoadFinished(ImageView v, Bitmap bitmap) {
	}

}
