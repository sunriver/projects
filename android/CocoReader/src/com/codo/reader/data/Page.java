package com.codo.reader.data;

public class Page {
	private final static String TAG = Page.class.getSimpleName();
	public final static int PAGE_SIZE = 1024 * 2;
	private int mPageIndex;
	private char[] mPageBuffer;
	private int avaiableSize;
	
	public Page(int index) {
		mPageIndex = index;
		avaiableSize = 0;
		mPageBuffer = new char[PAGE_SIZE];
	}
	
	public String getContent() {
		return String.valueOf(mPageBuffer);
	}
	
	public char[] getBuffer() {
		return mPageBuffer;
	}
	
	public void setAvaiableSize(int size) {
		this.avaiableSize = size;
	}
}
