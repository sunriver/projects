package com.oobe.store;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;

public class StoreService extends Service  {
	
	private StoreServiceBinder mServiceBinder = new StoreServiceBinder();
	
	public class StoreServiceBinder extends Binder implements IStoreService {
		@Override
		public void write(String msg) {
			try {
				File file = new File("/mnt/sdcard/msg.log");
				if (!file.exists()) {
					file.createNewFile();
				}
				FileOutputStream os = new FileOutputStream(file);
				
				os.write(msg.getBytes());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return mServiceBinder;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		
	}
	
	
}
