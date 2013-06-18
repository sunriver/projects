package com.funnyplayer.cache.lrc;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;

import org.apache.http.client.methods.HttpGet;

import android.content.Context;
import android.util.Log;

import com.funnyplayer.net.api.geci.LrcAPI;
import com.funnyplayer.net.base.HttpAgent;
import com.funnyplayer.net.base.PersistUtils;

public class LrcUtils {
	private static final String TAG = "LrcUtils";
	
	public static File getImageFromDisk( Context context, LrcInfo lrcInfo){
		return null;
	}
	
	public static File getLrcFromWeb(Context context, LrcInfo lrcInfo) {
		HttpAgent httpAgent = HttpAgent.getInstance(context);
		LrcAPI lrcApi = new LrcAPI();
		lrcApi.setArtistName(lrcInfo.getArtist());
		lrcApi.setSongName(lrcInfo.getSong());
		httpAgent.execute(lrcApi);
		
		final String lrcUrl = lrcApi.getResult();
		
		Log.v(TAG, "imageUrl :" + lrcUrl);
		if (null == lrcUrl) {
			return null;
		}

		HttpGet request = new HttpGet(lrcUrl);
		InputStream in = httpAgent.execute(request);
		if (null == in) {
			return null;
		}
		
		File dir = context.getExternalCacheDir();
		
		File file = new File(dir.getPath() + "/lrc", lrcInfo.toString() + ".lrc");
		if (file.exists()) {
			return file;
		}
		
		return PersistUtils.persistInputStream(new BufferedInputStream(in), file);
		
	}
}
