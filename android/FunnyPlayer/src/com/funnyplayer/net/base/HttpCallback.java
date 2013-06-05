package com.funnyplayer.net.base;

public interface HttpCallback {
	public int callback(String fileName, long totalSize, long progress);
}
