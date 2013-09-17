package com.funnyplayer.cache;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;

import org.apache.http.client.methods.HttpGet;

import com.funnyplayer.net.api.*;
import com.funnyplayer.net.base.HttpAgent;
import com.funnyplayer.net.base.PersistUtils;
import common.Consts;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.MediaStore.Audio;
import android.text.TextUtils;
import android.util.Log;

public class ImageUtils {
	public static enum ImageSize {
		SMALL, MEDIUM, LARGE
	}

	private static final String TAG = "ImageUtils";

	public static File getImageFromMediaStore(Context context,
			ImageInfo imageInfo) {
		String mAlbum = imageInfo.data[0];
		String[] projection = { BaseColumns._ID, Audio.Albums._ID, Audio.Albums.ALBUM_ART, Audio.Albums.ALBUM };
		Uri uri = Audio.Albums.EXTERNAL_CONTENT_URI;
		Cursor cursor = null;
		try {
			cursor = context.getContentResolver().query(uri,
							projection,
							BaseColumns._ID + "=" + DatabaseUtils.sqlEscapeString(mAlbum),
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


	public static File getImageFromWeb(Context context, ImageInfo imageInfo) {
		HttpAgent httpAgent = HttpAgent.getInstance(context);
		LastFmAPI<String> api = createGetImageAPI(imageInfo);
		if (null == api) {
			return null;
		}
		Log.v(TAG, "api url :" + api.toURL());
		
		httpAgent.execute(api);
		
		String imageUrl = api.getResult();
		Log.v(TAG, "imageUrl :" + imageUrl);
		if (null == imageUrl) {
			return null;
		}

		HttpGet request = new HttpGet(imageUrl);
		InputStream in = httpAgent.execute(request);
		if (null == in) {
			return null;
		}
		
		File file = new File(context.getExternalCacheDir(), imageInfo.toString() + ".png");
		if (file.exists()) {
			return file;
		}
		
		return PersistUtils.persistInputStream(new BufferedInputStream(in), file);
	}
	
	public static File getImageFromDisk( Context context, ImageInfo imageInfo ){
		File file = new File(context.getExternalCacheDir(), imageInfo.toString() + ".png");
		return (file.exists() ? file : null);
	}
	
	
	private static LastFmAPI<String> createGetImageAPI(final ImageInfo imageInfo) {
		if (null == imageInfo || TextUtils.isEmpty(imageInfo.type)) {
			return null;
		}
		if (imageInfo.type.equals(Consts.TYPE.ARTIST.toString())) {
			LastFmAPI<String> api = new com.funnyplayer.net.api.artist.GetInfoAPI();
			api.setParamter("artist", imageInfo.data[0]);
			return api;
		}
		
		if (imageInfo.type.equals(Consts.TYPE.ALBUM.toString())) {
			LastFmAPI<String> api = new com.funnyplayer.net.api.album.GetInfoAPI();
			api.setParamter("album", imageInfo.data[2]);
			return api;
		}
		return null;
	}
	

}
