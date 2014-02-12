package com.coco.reader.data;

import java.io.IOException;
import java.util.Map;
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
	private Document mSelectDocument;
	
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
	
	public synchronized void setSelectDocument(Document document) {
		this.mSelectDocument = document;
	}
	
	public synchronized Document getSelectDocument() {
		if (null == mSelectDocument) {
			mSelectDocument = getDefaultDocument();
		}
		return mSelectDocument;
	}
	
	public synchronized Document getDocumentByName(String docName) {
		if (TextUtils.isEmpty(docName)) {
			return null;
		}
		Document doc = mDocMap.get(docName);
		if (null == doc) {
			doc = new Document(mContext, ASSET_DOCS, docName);
			mDocMap.put(docName, doc);
		}
		return doc;
	}
	
	public void closeAllDocument() {
		for (Map.Entry<String,Document> entry : mDocMap.entrySet()) {
			Document doc = entry.getValue();
			doc.closeDocument();
		}
	}
	
	public void persistDocument(Document doc) {
		if (null == doc) {
			return;
		}
		Editor editor = mPreference.edit();
		editor.putString(Consts.PREF_DOCUMENT_DEFALUT, doc.getDocName())
			  .putInt(Consts.PREF_PAGE_DEFALUT_INDEX, doc.getSelectPageIndex())
			  .putInt(Consts.PREF_PAGE_DEFALUT_SCROLLY, doc.getSelectPageScrollY())
			  .commit();
	}
	
	public Document getDefaultDocument() {
		String defaultDocName = mPreference.getString(Consts.PREF_DOCUMENT_DEFALUT, null);
		Document doc = getDocumentByName(defaultDocName);
		if (doc != null) {
			int defaultPageIndex = mPreference.getInt(Consts.PREF_PAGE_DEFALUT_INDEX, 0);
			doc.setSelectPageIndex(defaultPageIndex);
			int defaultPageScrollY = mPreference.getInt(Consts.PREF_PAGE_DEFALUT_SCROLLY, 0);
			doc.setSelectPageScrollY(defaultPageScrollY);
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
