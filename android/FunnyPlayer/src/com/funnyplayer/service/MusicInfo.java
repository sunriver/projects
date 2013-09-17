package com.funnyplayer.service;

public class MusicInfo {
	private String mArtist;
	
	private String mName;
	
	private long mId;
	
	private String mPlayItemPath;
	
	public MusicInfo(String artist, String name, long id, String path) {
		this.mArtist = artist;
		this.mName = name;
		this.mId = id;
		this.mPlayItemPath = path;
	}

	public String getArtist() {
		return mArtist;
	}

	public void setArtist(String artist) {
		this.mArtist = artist;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		this.mName = name;
	}

	public Long getId() {
		return mId;
	}

	public void setId(Long id) {
		this.mId = id;
	}
	
	public String getPlayItemPath() {
		return mPlayItemPath;
	}
	
	public void setPlayItemPath(String path) {
		this.mPlayItemPath = path;
	}
	
	
}
