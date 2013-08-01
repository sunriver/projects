package com.funnyplayer.cache.lrc;

public class LrcInfo {
	private String mArtist;
	private String mSong;
	private String mUrl;
	
	public LrcInfo(String artist, String song) {
		mArtist = artist;
		mSong = song;
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
		return escapeForFileSystem(mArtist + "_" + mSong);
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
