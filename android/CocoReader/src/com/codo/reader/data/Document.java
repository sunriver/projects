package com.codo.reader.data;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import com.coco.reader.R;

public class Document {
	private final static String TAG = Document.class.getSimpleName();
	private final static String ASSET_DOCS = "docs";
	private String mDocName;
	private InputStream mInputStream;
	private StringBuffer mContent;
	private int mAvaiableSize;
	private Context mContext;
	private int mPageIndex;
	private BufferedReader mReader;

	public Document(Context context) {
		mContext = context;
		mPageIndex = 0;
		openDocument();
	}

	private void openDocument() {
		try {
			mDocName = mContext.getString(R.string.app_name);
			mDocName = "test";
			mInputStream = mContext.getAssets().open(
					ASSET_DOCS + "/" + mDocName + ".txt",
					AssetManager.ACCESS_RANDOM);
			mAvaiableSize = mInputStream.available();
			mReader = new BufferedReader(new InputStreamReader(mInputStream));
		} catch (IOException e) {
			Log.e(TAG, "Can't open document", e);
		}
	}
	
	public void closeDocument() {
		if (mInputStream != null) {
			try {
				mInputStream.close();
			} catch (IOException e) {
				Log.e(TAG, "Can't close document", e);
			}
		}
	}

	public Page getPage(int pageIndex) {
		try {
			Page page = new Page(pageIndex);
			int avaiableSize = page.read(mReader);
			Log.d(TAG, "getPage() pageIndex=" + pageIndex + " avaiableSize=" + avaiableSize);
			mPageIndex = pageIndex;
			return page;
		} catch (IOException e) {
			Log.e(TAG, "Can't read file", e);
		}
		return null;

	}

	@Override
	protected void finalize() throws Throwable {
		if (mInputStream != null) {
			mInputStream.close();
		}
		super.finalize();
	}
	

	public final String getDocName() {
		return mDocName;
	}
	
	public Page prevPage() {
		int pageIndex = (mPageIndex > 0) ? (mPageIndex - 1) : 0;
		return getPage(pageIndex);
	}
	
	public Page nextPage() {
		int pageIndex = mPageIndex + 1;
		return getPage(pageIndex);
	}

}
