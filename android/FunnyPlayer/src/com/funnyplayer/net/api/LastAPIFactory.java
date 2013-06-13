package com.funnyplayer.net.api;

public class LastAPIFactory {
	
	public static LastFmAPI<?> getApi(final APIMethod apiMethod) {
		if (apiMethod instanceof APIMethod.Album) {
			APIMethod.Album temp = (APIMethod.Album) apiMethod;
			switch (temp) {
			case GetInfo:
				return new com.funnyplayer.net.api.album.GetInfoAPI();
			}
		}
		if (apiMethod instanceof APIMethod.Artist) {
			APIMethod.Artist temp = (APIMethod.Artist) apiMethod;
			switch (temp) {
			case GetInfo:
				return new com.funnyplayer.net.api.artist.GetInfoAPI();
			}
		}
		return null;
	}
	

}
