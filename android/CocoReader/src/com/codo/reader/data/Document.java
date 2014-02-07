package com.codo.reader.data;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.coco.reader.R;

public class Document implements Parcelable {
	private final static String TAG = Document.class.getSimpleName();
	private String mDocName;
	private InputStream mInputStream;
	private StringBuffer mContent;
	private int mAvaiableSize;
	private Context mContext;
	private int mPageIndex;
	private BufferedReader mReader;

	public Document(Context context, String path, String name) {
		mContext = context;
		mPageIndex = 0;
		this.mDocName = name;
		String fileName = path + "/" + mDocName + ".txt";
		openDocument(fileName);
	}

	private void openDocument(final String file) {
		try {
//			mDocName = mContext.getString(R.string.app_name);
//			mDocName = "test";
			mInputStream = mContext.getAssets().open(file, AssetManager.ACCESS_RANDOM);
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

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
	}

}
