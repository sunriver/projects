package com.funnyplayer.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {
	private static final String NAME = "listen.pref";

	private static final String FRAGEMENT_TYPE = "fragment_type";
	
	private static final String PLAYING_SONG_PATH = "playing_song_path";
	
	private SharedPreferences mPreference;
	
	public SharedPreference(Context context) {
		mPreference = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
	}
	
	public void setPlayPath(String playPath) {
		mPreference.edit().putString(PLAYING_SONG_PATH, playPath);
	}
	
	public String getPlayPath() {
		return mPreference.getString(PLAYING_SONG_PATH, null);
	}
	
	
}
