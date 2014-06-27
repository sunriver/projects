package com.android.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.sf.andpdf.pdfviewer.PdfViewerActivity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;

public class TestPdfViewerActivity extends PdfViewerActivity {
	private final static String CACHE_PDF_FILE_NAME = "content.pdf";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		loadPdf();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void loadPdf() {
		try {
			File cacheDir = this.getCacheDir();
			File pdfFile = new File(cacheDir, CACHE_PDF_FILE_NAME);
			if (!pdfFile.exists()) { 
				copyRawPdfToCachePdf(getResources(), pdfFile);
			}
			openFile(pdfFile, null);
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
					for (int len ; (len = source.read(buffer)) != -1;){
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
					if ( target != null) {
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

	public int getPreviousPageImageResource() {
		return R.drawable.left_arrow;
	}

	public int getNextPageImageResource() {
		return R.drawable.right_arrow;
	}

	public int getZoomInImageResource() {
		return R.drawable.zoom_in;
	}

	public int getZoomOutImageResource() {
		return R.drawable.zoom_out;
	}

	public int getPdfPasswordLayoutResource() {
		return R.layout.pdf_file_password;
	}

	public int getPdfPageNumberResource() {
		return R.layout.dialog_pagenumber;
	}

	public int getPdfPasswordEditField() {
		return R.id.etPassword;
	}

	public int getPdfPasswordOkButton() {
		return R.id.btOK;
	}

	public int getPdfPasswordExitButton() {
		return R.id.btExit;
	}

	public int getPdfPageNumberEditField() {
		return R.id.pagenum_edit;
	}
}
