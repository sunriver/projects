package fi.harism.curl.doc;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import fi.harism.curl.R;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.util.Log;

public class Document {
	private final static String TAG = Document.class.getSimpleName();
	private String mDocName;
	private String mDocTitle;
	private String mDocFile;
	private InputStream mInputStream;
//	private FileInputStream mInputStream;
//	private FileChannel mFileChannel;
	private ByteBuffer mByteBuffer;
	private Context mContext;
	private BufferedReader mReader;
	private final int mAvaiableSize;
	//number of char which has been readed.
	private int mOffset;
	private boolean mHasRemaining = true;
	private StringBuffer mContentBuffer;

	public Document(Context context, String path)  throws Throwable {
		mContext = context;
		mDocFile = path;
		mInputStream = mContext.getAssets().open(mDocFile, AssetManager.ACCESS_RANDOM);
//		mInputStream = mContext.getAssets().openFd(path).createInputStream();
//		mFileChannel = mInputStream.getChannel();
//		mByteBuffer = ByteBuffer.allocate(2048);
		mContentBuffer = new StringBuffer();
		mAvaiableSize = mInputStream.available();
		mOffset = 0;
		mHasRemaining = true;
		mReader = new BufferedReader(new InputStreamReader(mInputStream));
	}
	
	
	public String nextPage(int len) throws IOException {
		int start = mOffset;
		if (start < 0) {
			start = 0;
		}
		int end = start + len;
		while (end > mContentBuffer.length()) {
			int trimLen = end - mContentBuffer.length();
			char[] buffer = new char[trimLen];
			int num = mReader.read(buffer, 0, trimLen);
			if (num >= 0) {
				mHasRemaining = true;
				String text = String.valueOf(buffer, 0, num);
				mContentBuffer.append(text);
			} else {
				mHasRemaining = false;
				break;
			}
		}
		if (end > mContentBuffer.length()) {
			end = mContentBuffer.length();
		}
		mOffset = end;
		return mContentBuffer.substring(start, end);
	}
//	public String nextPage(int len) throws IOException {
//		mFileChannel.
//		mHasRemaining = false;
//		return null;
//	}
	
	public String prevPage(int len) throws IOException {
		int start = mOffset - len;
		if (start < 0) {
			start = 0;
		}
		int end = start + len;
		if (end > mContentBuffer.length()) {
			end = mContentBuffer.length();
		}
		String text = mContentBuffer.substring(start, end);
		mOffset = start;
		return text;
	}
	
	private boolean hasRemaining() {
		return mHasRemaining;
	}
	
	public boolean hasNextPage() {
		return hasRemaining();
	}
	
	public boolean hasPrevPage() {
		return mContentBuffer.length() > 0;
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


	public final String getDocName() {
		return mDocName;
	}
	
	public final String getTitile() {
		return mDocTitle;
	}
	

}
