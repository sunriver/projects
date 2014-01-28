package com.codo.reader.data;

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

	public Document(Context context) {
		mContext = context;
		openDocument(context);
	}

	private void openDocument(Context ctx) {
		try {
			mDocName = ctx.getString(R.string.app_name);
			mDocName = "test";
			mInputStream = ctx.getAssets().open(
					ASSET_DOCS + "/" + mDocName + ".txt",
					AssetManager.ACCESS_RANDOM);
			mAvaiableSize = mInputStream.available();
		} catch (IOException e) {
			Log.d(TAG, "Can't open document", e);
		}
	}

	private String getPage(int start, int length) {
		try {
			mInputStream.skip(start);
			StringBuffer sb = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(mInputStream));
			for (String line = reader.readLine(); line != null; line = reader.readLine()) {
				sb.append(line + "\n");
			}
			sb.append("\n");
			return sb.toString();
		} catch (IOException e) {
			Log.d(TAG, "Can't read file", e);
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
	
	public String previousPage() {
		return getPage(0, mAvaiableSize);
	}
	
	public String nextPage() {
		return getPage(0, mAvaiableSize);
	}

}
