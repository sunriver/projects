package com.coco.reader.view;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

public class ReadView extends EditText {
	private final static String TAG = ReadView.class.getSimpleName();
	private final static String ASSET_DOCS = "docs";
	private String mDocName;
	private InputStream mInputStream;
	
	public ReadView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		postContent(context);
	}


	public ReadView(Context context, AttributeSet attrs) {
		super(context, attrs);
		postContent(context);
	}
	
	public final String getDocName() {
		return mDocName;
	}
	
	private void init() {
		this.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return false;
			}
			
		});
	}
	

	
	private void openDocument(Context ctx) {
		try {
			String[] docs = ctx.getAssets().list(ASSET_DOCS);
			if (docs != null) {
				for (String doc : docs) {
					if (!TextUtils.isEmpty(doc)) {
						mDocName = doc;
						break;
					}
				}
			}
			
			if (mDocName != null) {
				mInputStream = new FileInputStream(new File(mDocName));
			}
		} catch (IOException e) {
			Log.d(TAG, "Can't open document", e);
		}
	}
	
	
	@Override
	protected void finalize() throws Throwable {
		if (mInputStream != null) {
			mInputStream.close();
		}
		super.finalize();
	}


	private void postContent(Context context) {
		Handler handler = new Handler();
		handler.post(new Runnable() {

			@Override
			public void run() {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < 1000; i++) {
					sb.append("content" +i + "\n");
				}
				ReadView.this.setText(sb.toString());
			}
			
		});
	}
	
//	private GestureDetector mGestureDetector = new GestureDetector();
//	private SimpleOnGestureListener mGestureListener  = new  SimpleOnGestureListener();
 

}
