package com.funnyplayer.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public interface MusicService {
	public void addPlayList(long[] list);

	public void stat(int pos);

	public void next();

	public void previous();
}
