package com.codo.reader.data;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import android.content.Context;
import android.util.Log;

public class DocumentManager {
	private final static String TAG = Document.class.getSimpleName();
	private final static String ASSET_DOCS = "docs";
	
	private Context mContext;
	private ConcurrentHashMap<String, Document> mDocMap;
	private static DocumentManager mInstance;
	
	private DocumentManager(Context context) {
		mContext = context.getApplicationContext();
		mDocMap = new ConcurrentHashMap<String, Document>();
	}

	public String[] getAllDocuments() {
		try {
			return mContext.getAssets().list(ASSET_DOCS);
		} catch (IOException e) {
			Log.e(TAG, "Can't get all documents", e);
		}
		return null;
	}
	
	public Document getDocumentByName(String docName) {
		Document doc = new Document(mContext, ASSET_DOCS, docName);
		if (null == doc) {
			doc = new Document(mContext, ASSET_DOCS, docName);
			mDocMap.put(docName, doc);
		} else {
			doc.reset();
		}
		return doc;
	}
	
	public void loadDefaultDocument() {
		
	}
	
	public static synchronized DocumentManager getInstance(Context ctx) {
		if (null == mInstance) {
			mInstance = new DocumentManager(ctx);
		}
		return mInstance;
	}
}
