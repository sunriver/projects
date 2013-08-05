package com.funnyplayer.cache.lrc;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.methods.HttpGet;
import android.content.Context;
import android.util.Log;
import com.funnyplayer.net.api.geci.LrcAPI;
import com.funnyplayer.net.api.geci.bean.LrcBean;
import com.funnyplayer.net.base.HttpAgent;
import com.funnyplayer.net.base.PersistUtils;

public class LrcUtils {
	private static final String TAG = "LrcUtils";
	
	public static File getLrcFromDisk(Context context, LrcInfo lrcInfo){
		return null;
	}
	
	public static String getLrcFromFile(final File file) {
		if (null == file || !file.exists()) {
			return null;
		}
		BufferedReader reader = null;
		try {
			StringBuilder builder = new StringBuilder();
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			for (String s = reader.readLine(); s != null; s = reader.readLine()) {
				builder.append(s);
				builder.append("\n");
			}
			return builder.toString();
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					Log.e(TAG, e.getMessage());;
				}
			}
		}
		
		return null;
	}
	
	
	public static File getLrcDir(Context context) {
		File dir = new File(context.getExternalCacheDir().getPath() + "/lrc");
		if (dir.exists()) {
			return dir;
		}
		return (dir.mkdir() ? dir : context.getExternalCacheDir());
		
	}
	
	public static LrcBean searchLrcFromWeb(Context context, LrcInfo lrcInfo) {
		HttpAgent httpAgent = HttpAgent.getInstance(context);
		LrcAPI lrcApi = new LrcAPI();
		lrcApi.setArtistName(lrcInfo.getArtist());
		lrcApi.setSongName(lrcInfo.getSong());
		httpAgent.execute(lrcApi);
		return lrcApi.getResult();
	}
	
	
	public static File downloadLrcFromWeb(Context context, LrcInfo lrcInfo) {
		File file = new File(getLrcDir(context), lrcInfo.toString() + ".lrc");
		if (file.exists()) {
			return file;
		}
		
		HttpAgent httpAgent = HttpAgent.getInstance(context);
		
		HttpGet request = new HttpGet(lrcInfo.getUrl());
		InputStream in = httpAgent.execute(request);
		if (null == in) {
			return null;
		}
		return PersistUtils.persistInputStream(new BufferedInputStream(in), file);

	}
	
	
//	public static File getLrcFromWeb(Context context, LrcInfo lrcInfo) {
//		HttpAgent httpAgent = HttpAgent.getInstance(context);
//		LrcAPI lrcApi = new LrcAPI();
//		lrcApi.setArtistName(lrcInfo.getArtist());
//		lrcApi.setSongName(lrcInfo.getSong());
//		httpAgent.execute(lrcApi);
//		
//		final String lrcUrl = lrcApi.getResult();
//		
//		Log.v(TAG, "lrcUrl :" + lrcUrl);
//		if (null == lrcUrl) {
//			return null;
//		}
//
//		HttpGet request = new HttpGet(lrcUrl);
//		InputStream in = httpAgent.execute(request);
//		if (null == in) {
//			return null;
//		}
//		
//		
//		File file = new File(getLrcDir(context), lrcInfo.toString() + ".lrc");
//		if (file.exists()) {
//			return file;
//		}
//		
//		return PersistUtils.persistInputStream(new BufferedInputStream(in), file);
//		
//	}
}
