package com.funnyplayer.cache.lrc;

import android.text.TextUtils;

public class LrcInfo {
	private static final String DEMIT = "-";
	private String mArtist;
	private String mSong;
	private String mUrl;
	
	public LrcInfo(String artist, String song) {
		this(artist, song, null);
	}
	
	public LrcInfo(String artist, String song, String url) {
		mArtist = artist;
		mSong = song;
		mUrl = url;
	}
	
	public String getArtist() {
		return mArtist;
	}
	
	public String getSong() {
		return mSong;
	}
	
	public void setSong(String song) {
		this.mSong = song;
	}
	
	public void setArtist(String artist) {
		this.mArtist = artist;
	}
	
	public String getUrl() {
		return mUrl;
	}
	
	public void setUrl(final String url) {
		this.mUrl = url;
	}

	@Override
	public String toString() {
		return escapeForFileSystem(mSong + DEMIT + mArtist);
	}
	
	public String toFileName() {
		return toString() + ".lrc";
	}
	
	public static String getSongByFileName(final String fileName) {
		if (TextUtils.isEmpty(fileName)) {
			return null;
		}
		int end = fileName.indexOf(".lrc");
		String tempName = fileName.substring(0, end);
		String[] values = tempName.split(DEMIT);
		if (values != null && values.length > 0) {
			return values[1];
		}
		return null;
	}
	
	public static String getArtistByFileName(final String fileName) {
		if (TextUtils.isEmpty(fileName)) {
			return null;
		}
		int end = fileName.indexOf(".lrc");
		String tempName = fileName.substring(0, end);
		String[] values = tempName.split(DEMIT);
		if (values != null && values.length > 1) {
			return values[0];
		}
		return null;
	}
	
	 /**
     * Replace the characters not allowed in file names with underscore
     * @param name
     * @return
     */
    private static String escapeForFileSystem(String name) {
        return name.replaceAll("[\\\\/:*?\"<>|]+", "_");
    }
	
}
