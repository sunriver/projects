package fi.harism.curl.doc;

public class Page {
	private final int mSize;
	private final String mText;
	
	public Page(int size, String content) {
		this.mSize = size;
		this.mText = content;
	}
	
	public final int size() {
		return mSize;
	}
	
	public final String getText() {
		return mText;
	}
}
