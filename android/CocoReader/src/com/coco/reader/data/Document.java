package com.coco.reader.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

public class Document {
	private final static String TAG = Document.class.getSimpleName();
	private String mDocName;
	private String mDocTitle;
	private String mDocFile;
	private List<Page> mPages;
	private InputStream mInputStream;
	private StringBuffer mContent;
	private int mAvaiableSize;
	private Context mContext;
	private int mSelectedPageIndex;
	private BufferedReader mReader;
	private int mSelectedPageScrollDy;
	private int mTextSize;
	private boolean mIsLoaded;

	public Document(Context context, String path, String title, String name)  throws Throwable {
		mContext = context;
		mSelectedPageIndex = 0;
		this.mDocName = name;
		this.mDocTitle = title;
		mDocFile = path + "/" + mDocName + ".txt";
		mPages = new ArrayList<Page>();
		mIsLoaded = false;
		openDocument();
	}
	
	public Document(Context context, String path, ChapterItem item)  throws Throwable {
		this(context, path, item.title, item.itemName);
	}

	private void openDocument() throws Throwable {
		mInputStream = mContext.getAssets().open(mDocFile, AssetManager.ACCESS_RANDOM);
		mAvaiableSize = mInputStream.available();
		mReader = new BufferedReader(new InputStreamReader(mInputStream));
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
	
	public boolean isLoaded() {
		return mIsLoaded;
	}
	
	public void loadPages(int start, int length) {
		try {
			for (int pageIndex = start; pageIndex < length; pageIndex ++) {
				Page page = new Page(pageIndex);
				int avaiableSize = page.read(mReader, true);
				if (avaiableSize < 0) {
					mIsLoaded = true;
					break;
				}
				if (pageIndex == mSelectedPageIndex) {
					page.setScrollDy(mSelectedPageScrollDy);
				}	
				mPages.add(page);
			}
		} catch (Exception e) {
			Log.e(TAG, "Can't read file", e);
		}
	}
	
	public Page getPage(int index) {
		if (index > mPages.size() || index < 0) {
			return null;
		}
		return mPages.get(index);
	}
	
	public int getPageCount() {
		return mPages.size();
	}
	
	public int getSelectPageIndex() {
		return mSelectedPageIndex;
	}
	
	public void setSelectPageIndex(int index) {
		this.mSelectedPageIndex = index;
	}
	
	public int getTextSize() {
		return mTextSize;
	}
	
	public void setTextSize(int size) {
		this.mTextSize = size;
	}
	
	public int getSelectPageScrollY() {
		return mSelectedPageScrollDy;
	}
	
	public void setSelectPageScrollDy(int dy) {
		this.mSelectedPageScrollDy = dy;
	}

	@Override
	protected void finalize() throws Throwable {
		Log.d(TAG, "finalize() +");
		if (mInputStream != null) {
			Log.d(TAG, "finalize() close input stream");
			mInputStream.close();
		}
		super.finalize();
	}
	

	public final String getDocName() {
		return mDocName;
	}
	
	public final String getTitile() {
		return mDocTitle;
	}
	
	public Page prevPage() {
		int pageIndex = (mSelectedPageIndex > 0) ? (mSelectedPageIndex - 1) : 0;
		return mPages.get(pageIndex);
	}
	
	public Page nextPage() {
		int pageIndex = mSelectedPageIndex + 1;
		return mPages.get(pageIndex);
	}

}
