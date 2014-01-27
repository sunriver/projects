package com.coco.reader.view;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.coco.reader.R;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

public class ReadView extends EditText {
	private final static String TAG = ReadView.class.getSimpleName();
	private final static String ASSET_DOCS = "docs";
	private String mDocName;
	private InputStream mInputStream;
	private StringBuffer mContent;
	private int mAvaiableSize;
	
	public ReadView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}


	public ReadView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public final String getDocName() {
		return mDocName;
	}
	
	
	private void openDocument(Context ctx) {
		try {
			mDocName = ctx.getString(R.string.app_name);
			mDocName = "test";
			mInputStream = ctx.getAssets().open(ASSET_DOCS + "/" + mDocName + ".txt", AssetManager.ACCESS_RANDOM);
			mAvaiableSize = mInputStream.available();
			mContent = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(mInputStream));
			for (String line = reader.readLine(); line != null; line = reader.readLine()) {
				mContent.append(line + "\n");
			}
			mContent.append("\n");
			this.setText(mContent.toString());
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


	private void init(final Context context) {
		Handler handler = new Handler();
		handler.post(new Runnable() {
			@Override
			public void run() {
				openDocument(context);
			}
		});
	}

}
