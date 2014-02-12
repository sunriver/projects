package com.coco.reader.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Document {
	private final static String TAG = Document.class.getSimpleName();
	private String mDocName;
	private String mDocFile;
	private Map<Integer, Page> mPages;
	private InputStream mInputStream;
	private StringBuffer mContent;
	private int mAvaiableSize;
	private Context mContext;
	private int mSelectedPageIndex;
	private BufferedReader mReader;
	private int mSelectedPageScrollY;

	public Document(Context context, String path, String name) {
		mContext = context;
		mSelectedPageIndex = 0;
		this.mDocName = name;
		mDocFile = path + "/" + mDocName + ".txt";
		mPages = new HashMap<Integer, Page>();
		openDocument();
	}

	public void openDocument() {
		try {
			mInputStream = mContext.getAssets().open(mDocFile, AssetManager.ACCESS_RANDOM);
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
			Page page = mPages.get(pageIndex);
			if (null == page) {
				page = new Page(pageIndex);
				mPages.put(pageIndex, page);
				int avaiableSize = page.read(mReader, true);
				Log.d(TAG, "getPage() pageIndex=" + pageIndex + " avaiableSize=" + avaiableSize);
			}
			if (pageIndex == mSelectedPageIndex) {
				page.setScrollY(mSelectedPageScrollY);
			}
			return page;
		} catch (IOException e) {
			Log.e(TAG, "Can't read file", e);
		}
		return null;
	}
	
	public int getSelectPageIndex() {
		return mSelectedPageIndex;
	}
	
	public void setSelectPageIndex(int index) {
		this.mSelectedPageIndex = index;
	}
	
	
	public int getSelectPageScrollY() {
		return mSelectedPageScrollY;
	}
	
	public void setSelectPageScrollY(int index) {
		this.mSelectedPageScrollY = index;
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
		int pageIndex = (mSelectedPageIndex > 0) ? (mSelectedPageIndex - 1) : 0;
		return getPage(pageIndex);
	}
	
	public Page nextPage() {
		int pageIndex = mSelectedPageIndex + 1;
		return getPage(pageIndex);
	}

}
