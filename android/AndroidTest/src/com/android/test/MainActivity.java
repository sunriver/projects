package com.android.test;

import net.sf.andpdf.pdfviewer.PdfViewerActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	private void openPDF() {
	     Intent intent = new Intent(this, TestPdfViewerActivity.class);
	     intent.putExtra(PdfViewerActivity.EXTRA_PDFFILENAME, "PATH TO PDF GOES HERE");
	     startActivity(intent);
	}

	@Override
	protected void onStart() {
		openPDF();
		super.onStart();
	}
	
	
	

}
