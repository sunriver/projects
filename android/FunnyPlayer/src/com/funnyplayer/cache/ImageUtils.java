package com.funnyplayer.cache;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpRequestBase;
import org.w3c.dom.NodeList;

import com.funnyplayer.net.api.LastFmAPI;
import com.funnyplayer.net.api.Result;
import com.funnyplayer.net.api.XmlParser;
import com.funnyplayer.net.base.AppHttpClient;
import com.funnyplayer.net.base.HttpUtils;
import com.funnyplayer.net.base.HttpUtils.InitRequestCallback;
import com.funnyplayer.net.base.HttpUtils.RequestInfo;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.MediaStore.Audio;
import android.util.Log;

public class ImageUtils {
	private static final String TAG = "ImageProvider";

	public static File getImageFromMediaStore(Context context, ImageInfo imageInfo) {
		String mAlbum = imageInfo.data[0];
		String[] projection = { BaseColumns._ID, Audio.Albums._ID,
				Audio.Albums.ALBUM_ART, Audio.Albums.ALBUM };
		Uri uri = Audio.Albums.EXTERNAL_CONTENT_URI;
		Cursor cursor = null;
		try {
			cursor = context.getContentResolver().query(uri, projection, BaseColumns._ID + "=" + DatabaseUtils.sqlEscapeString(mAlbum), null, null);
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
		LastFmAPI api = LastFmAPI.Artist.GetInfo;
		String url = api.toURL("artist", "Cher");
		Log.v(TAG, "getImageFromWeb url:" + url);
		
		RequestInfo info = new RequestInfo();
		info.url = url;
		
		try {
			HttpRequestBase request = HttpUtils.initPost(info, new InitRequestCallback() {

				@Override
				public void initRequest(HttpRequestBase request, boolean needAuth) {
					
				}
			});
			request.setHeader("User-Agent", "listen");
			
			AppHttpClient httpclient = AppHttpClient.getSingleInstance(context);
			HttpResponse res =  httpclient.execute(request);
			int code = res.getStatusLine().getStatusCode();
			Log.v(TAG, "code:" + code);
			
			if (code == HttpStatus.SC_OK) {
				InputStream in = res.getEntity().getContent();
				
				Result result = XmlParser.createResultFromInputStream(in);
				NodeList imageList = result.getResultDocument().getElementsByTagName("image");
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
		return null;
	}
}
