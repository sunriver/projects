package com.codo.reader.data;

import java.io.IOException;
import java.io.Reader;

import android.util.Log;

public class Page {
	private final static String TAG = Page.class.getSimpleName();
	public final static int PAGE_SIZE = 1024;
	private int mPageIndex;
	private char[] mPageBuffer;
	private int mAvaiableSize;
	
	public Page(int index) {
		mPageIndex = index;
		mAvaiableSize = -1;
		mPageBuffer = new char[PAGE_SIZE];
	}
	
	public int getIndex() {
		return mPageIndex;
	}
	
	public String getContent() {
		if (mAvaiableSize > 0) {
			return String.valueOf(mPageBuffer);
		}
		return "";
	}
	
	public char[] getBuffer() {
		return mPageBuffer;
	}
	
	public int getAvaiableSize() {
		return mAvaiableSize;
	}
	
	
	public int read(Reader reader) throws IOException {
		int offset = mPageIndex * Page.PAGE_SIZE;
//		reader.reset();
//		reader.skip(offset);
		mAvaiableSize = reader.read(mPageBuffer, 0, Page.PAGE_SIZE);
		return mAvaiableSize;
	}
}
