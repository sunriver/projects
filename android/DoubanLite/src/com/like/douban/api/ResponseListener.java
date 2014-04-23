package com.like.douban.api;

public interface ResponseListener {
	public <T> void onSuccess(T result);
	
	public void onFailure();
}
