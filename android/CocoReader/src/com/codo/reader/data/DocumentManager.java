package com.codo.reader.data;

import java.io.IOException;
import java.util.WeakHashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Log;

public class DocumentManager {
	private final static String TAG = Document.class.getSimpleName();
	private final static String ASSET_DOCS = "docs";
	
	private Context mContext;
	private WeakHashMap<String, Document> mDocMap;
	private static DocumentManager mInstance;
	private SharedPreferences mPreference;
	private Document mCurrDocument;
	
	private DocumentManager(Context context) {
		mContext = context.getApplicationContext();
		mDocMap = new WeakHashMap<String, Document>();
		mPreference = context.getSharedPreferences(Consts.PREF_FILE, Context.MODE_PRIVATE);
	}

	public String[] getAllDocuments() {
		try {
			return mContext.getAssets().list(ASSET_DOCS);
		} catch (IOException e) {
			Log.e(TAG, "Can't get all documents", e);
		}
		return null;
	}
	
	public synchronized Document getDocumentByName(String docName) {
		if (TextUtils.isEmpty(docName)) {
			return null;
		}
		Document doc = mDocMap.get(docName);
		if (null == doc) {
			doc = new Document(mContext, ASSET_DOCS, docName);
			mDocMap.put(docName, doc);
		} else {
			doc.reset();
		}
		persistDocument();
		mCurrDocument = doc;
		return doc;
	}
	
	public void persistDocument() {
		final Document doc = mCurrDocument;
		if (null == doc) {
			return;
		}
		Editor editor = mPreference.edit();
		editor.putString(Consts.PREF_PROPERTY_DOCUMENT_DEFALUT, doc.getDocName())
			  .putInt(Consts.PREF_PROPERTY_PAGE_DEFALUT, doc.getSelectPageIndex())
			  .commit();
	}
	
	public Document getDefaultDocument() {
		String defaultDocName = mPreference.getString(Consts.PREF_PROPERTY_DOCUMENT_DEFALUT, null);
		Document doc = getDocumentByName(defaultDocName);
		if (doc != null) {
			int defaultPageIndex = mPreference.getInt(Consts.PREF_PROPERTY_PAGE_DEFALUT, 0);
			doc.setSelectPageIndex(defaultPageIndex);
		}
		return doc;
	}
	
	public static synchronized DocumentManager getInstance(Context ctx) {
		if (null == mInstance) {
			mInstance = new DocumentManager(ctx);
		}
		return mInstance;
	}
}
