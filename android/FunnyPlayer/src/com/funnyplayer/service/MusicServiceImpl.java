package com.funnyplayer.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MusicServiceImpl extends Service implements MusicService {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void addPlayList(long[] list) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stat(int pos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void next() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void previous() {
		// TODO Auto-generated method stub
		
	}

}
