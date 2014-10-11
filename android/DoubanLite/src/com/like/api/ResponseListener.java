package com.like.api;

public interface ResponseListener <T> {
	public void onSuccess(T result);
	
	public void onFailure();
}
