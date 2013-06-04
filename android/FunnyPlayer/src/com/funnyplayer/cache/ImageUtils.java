package com.funnyplayer.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;


import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.MediaStore.Audio;
import android.util.Log;

public class ImageUtils {
		private static final String TAG = "ImageProvider";
	
	public static File getImageFromMediaStore(Context context,
			ImageInfo imageInfo) {
		String mAlbum = imageInfo.data[0];
		String[] projection = { BaseColumns._ID, Audio.Albums._ID,
				Audio.Albums.ALBUM_ART, Audio.Albums.ALBUM };
		Uri uri = Audio.Albums.EXTERNAL_CONTENT_URI;
		Cursor cursor = null;
		try {
			cursor = context.getContentResolver().query(uri,projection, BaseColumns._ID + "=" + DatabaseUtils.sqlEscapeString(mAlbum), null, null);
			int column_index = cursor.getColumnIndex(Audio.Albums.ALBUM_ART);
			if (cursor.getCount() > 0) {
				cursor.moveToFirst();
				String albumArt = cursor.getString(column_index);
				if (albumArt != null) {
					File orgFile = new File(albumArt);
					return orgFile;
				}
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return null;
	}
	   
	   public static String getFileName(final ImageInfo imageInfo) {
		  return imageInfo.type + imageInfo.data[0] + ".png";
	   }
	    
	   public static String getIndentifier(final ImageInfo imageInfo) {
		  return imageInfo.type + imageInfo.data[0];
	   }
}
