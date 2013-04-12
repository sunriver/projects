package com.funnyplayer.net;

public interface HttpCallback {
	public int callback(String fileName, long totalSize, long progress);
}
