package com.funnyplayer.cache;

import java.io.File;
import java.io.InputStream;

import org.apache.http.client.methods.HttpGet;

import com.funnyplayer.net.api.*;
import com.funnyplayer.net.base.HttpAgent;
import com.funnyplayer.net.base.PersistUtils;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.MediaStore.Audio;
import android.util.Log;

public class ImageUtils {
	public static enum ImageSize {
		SMALL, MEDIUM, LARGE
	}

	private static final String TAG = "ImageUtils";

	public static File getImageFromMediaStore(Context context,
			ImageInfo imageInfo) {
		String mAlbum = imageInfo.data[0];
		String[] projection = { BaseColumns._ID, Audio.Albums._ID,
				Audio.Albums.ALBUM_ART, Audio.Albums.ALBUM };
		Uri uri = Audio.Albums.EXTERNAL_CONTENT_URI;
		Cursor cursor = null;
		try {
			cursor = context.getContentResolver()
					.query(uri,
							projection,
							BaseColumns._ID + "="
									+ DatabaseUtils.sqlEscapeString(mAlbum),
							null, null);
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

	public static File getImageFromWeb(Context context, ImageInfo imageInfo) {
		HttpAgent httpAgent = HttpAgent.getInstance(context);
		
		LastFmAPI<String> api = new com.funnyplayer.net.api.artist.GetInfoAPI();
		api.setParamter("artist", "Cher");
		Log.v(TAG, "api url :" + api.toURL());
		
		httpAgent.execute(api);
		
		String imageUrl = api.getResult();
		Log.v(TAG, "imageUrl :" + imageUrl);

		HttpGet request = new HttpGet(imageUrl);
		InputStream in = httpAgent.execute(request);
		
		String filePath = context.getExternalCacheDir() + imageInfo.toString() + ".png";
		Log.v(TAG, "filePath :" + filePath);
		
		return PersistUtils.persistInputStream(in, filePath);
	}
	

}
