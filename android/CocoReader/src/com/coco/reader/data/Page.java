package com.coco.reader.data;

import java.io.IOException;
import java.io.Reader;

public class Page {
	private final static String TAG = Page.class.getSimpleName();
	public final static int PAGE_SIZE = 1024;
	private int mPageIndex;
	private char[] mPageBuffer;
	private int mAvaiableSize;
	private StringBuffer mStrBuf;
	private int mScrollDy;
	
	public Page(int index) {
		mPageIndex = index;
		mScrollDy = 0;
		mAvaiableSize = -1;
		mPageBuffer = new char[PAGE_SIZE];
		mStrBuf = new StringBuffer();
	}
	
	public int getIndex() {
		return mPageIndex;
	}
	
	public String getContent() {
		if (mAvaiableSize > 0) {
			return mStrBuf.toString();
		}
		return "";
	}
	
	public String getContent(int start, int end) {
		if (mAvaiableSize > 0 && start >= 0 && end < mAvaiableSize) {
			return mStrBuf.substring(start, end);
		}
		return "";
	}
	
	public int getAvaiableSize() {
		return mAvaiableSize;
	}
	
	public void setScrollDy(int scrollDy) {
		this.mScrollDy = scrollDy;
	}
	
	public int getScrollDY() {
		return mScrollDy;
	}
	
	
	public int read(Reader reader, boolean readAll) throws IOException {
		int count = reader.read(mPageBuffer, 0, Page.PAGE_SIZE);
		mAvaiableSize = count;
		if (count > 0) {
			mStrBuf.append(mPageBuffer, 0, count);
		}
		if (readAll) {
			while ((count = reader.read(mPageBuffer, 0, Page.PAGE_SIZE)) >= 0) {
				mAvaiableSize += count;
				mStrBuf.append(mPageBuffer, 0, count);
			}
		} 
		return mAvaiableSize;

	}
}
