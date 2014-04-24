package com.like.douban.api;

public interface ResponseListener <T> {
	public void onSuccess(T result);
	
	public void onFailure();
}
