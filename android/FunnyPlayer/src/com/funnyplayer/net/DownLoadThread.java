package com.funnyplayer.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.LinkedList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;


import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;


public class DownLoadThread extends Thread {
    private static final boolean mDebug = false;
    
    private static final String TAG = "DownLoadThread";
    
    
    private final int BUFFER_SIZE = 1024;
    
    private static class Task {
    	private String url;
    	private String fileName;
    	private HttpCallback callback;
    }
    
    private File mCacheDir;
    
    private LinkedList<Task>  mTaskQueue;
    private HttpClient mHttpClient;
    
	public DownLoadThread(Context context) {
        super(TAG);
		mTaskQueue = new LinkedList<Task>();
		mCacheDir = context.getDir("Download", Context.MODE_PRIVATE);
		Environment.getDataDirectory();
		mHttpClient = AppHttpClient.getSingleInstance(context);
	}

	@Override
	public void run() {
	
		FileOutputStream out = null;
		InputStream is = null;
		
		while (true) {
			try {
				Task task = null;
				synchronized (mTaskQueue) {
					while(mTaskQueue.size() == 0) {
						mTaskQueue.wait();
					}
					task = mTaskQueue.removeFirst();
				}
				
				HttpGet request = new HttpGet(task.url);
				HttpResponse response = mHttpClient.execute(request);
				int statusCode = response.getStatusLine().getStatusCode();
				if ( statusCode == HttpStatus.SC_OK) {
					out = new FileOutputStream(task.fileName);
					long mediaLength = response.getEntity().getContentLength();
					is = response.getEntity().getContent();
			        byte buf[] = new byte[BUFFER_SIZE];
			        long progress = 0;
			        for (int len = is.read(); len > 0; len = is.read()) {
			    		out.write(buf, 0, len);
			    		progress += len;
			        }
			        task.callback.callback(task.fileName, mediaLength, progress);

				}
			} catch (Exception e) {
				Log.e(TAG, "DownLoadThread::run() error:" + e.getMessage());
			} finally {
				try {
					if (is != null) {
						is.close();
					}
					if (out != null) {
						out.close();
					}
				} catch (IOException e) {
					Log.e(TAG, "DownLoadThread::run() error:" + e.getMessage());
				}
			}
		}
	}
	
	
	public void add(String url, HttpCallback callback) {
		try {
			Task task = new Task();
			task.url = url;
			task.callback = callback;
			byte[] bytes = MessageDigest.getInstance("MD5").digest(url.getBytes("UTF-8"));
			String fileName = Base64.encodeToString(bytes, Base64.DEFAULT);
			task.fileName = mCacheDir + "/tt.mp3";
			File f = new File(task.fileName);
			if (f.exists()) {
				f.delete();
			}
			synchronized (mTaskQueue) {
				mTaskQueue.add(task);
				mTaskQueue.notify();
			}
		} catch (Exception e) {
			Log.e(TAG, "DownLoadThread::add() error:" + e.getMessage());
		}
	}
	
	
}
