package com.android.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Application;
import android.content.res.Resources;

public class TestApplication extends Application {
	public final static String CACHE_PDF_FILE_NAME = "content.pdf";
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		loadPdf();
	}
	
	private void loadPdf() {
		try {
			File cacheDir = this.getCacheDir();
			File pdfFile = new File(cacheDir, CACHE_PDF_FILE_NAME);
			if (!pdfFile.exists()) { 
				copyRawPdfToCachePdf(getResources(), pdfFile);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// move pdf file under /res/raw to /data/data/$package/cache/
	private static void copyRawPdfToCachePdf(final Resources res, final File cachePdfFile) {
		Thread thread = new Thread() {
			@Override
			public void run() {
				InputStream source = null;
				FileOutputStream target = null;
				try {
					source = res.openRawResource(R.raw.ffmpeg_sdk);
					target = new FileOutputStream(cachePdfFile);
					byte[] buffer = new byte[1024];
					for (int len; (len = source.read(buffer)) != -1;) {
						target.write(buffer, 0, len);
					}
					target.flush();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					if (source != null) {
						try {
							source.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if (target != null) {
						try {
							target.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		};
		thread.start();
	}
}
