package com.coco.reader.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import com.coco.reader.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class DocumentManager {
	private final static String TAG = Document.class.getSimpleName();
	private final static String ASSET_DOCS = "docs";
	
	private Context mContext;
	private WeakHashMap<String, Document> mDocMap;
	private static DocumentManager mInstance;
	private SharedPreferences mPreference;
	private Document mSelectDocument;
	private OptionSetting mOptionSetting;
	private String[] mOutLine;
	
	private DocumentManager(Context context) {
		mContext = context.getApplicationContext();
		mDocMap = new WeakHashMap<String, Document>();
		mPreference = context.getSharedPreferences(Consts.PREF_FILE, Context.MODE_PRIVATE);
		mOutLine = getDocOutline();
		initOptionSettings();
	}
	
	private void initOptionSettings() {
		OptionSetting op = new OptionSetting();
		String defaultDocTheme = mPreference.getString(Consts.PREF_DOCUMENT_DEFALUT_THEME, ThemeType.LightGreen.toString());
		op.setThemeType(ThemeType.valueOf(defaultDocTheme));
		
		int defaultTextSize = mContext.getResources().getInteger(R.integer.text_size_default);
		defaultTextSize = mPreference.getInt(Consts.PREF_PAGE_DEFALUT_TEXT_SIZE, defaultTextSize);
		op.setTextSize(defaultTextSize);
		
		int defaultLineSpace = mContext.getResources().getInteger(R.integer.line_space_default);
		defaultLineSpace = mPreference.getInt(Consts.PREF_PAGE_DEFALUT_LINE_SPACE, defaultLineSpace);
		op.setLineSpace(defaultLineSpace);
		
		int defaultScreenBrightness = mContext.getResources().getInteger(R.integer.screen_brightness_default);
		defaultScreenBrightness = mPreference.getInt(Consts.PREF_PAGE_DEFALUT_SCREEN_BRIGHTNESS, defaultScreenBrightness);
		op.setScreenBrightness(defaultScreenBrightness);
		
		mOptionSetting = op;
	}

	public String[] getAllTitles() {
		return mOutLine;
	}
	
	private String[] getDocOutline() {
		try {
			String outlineFileName = ASSET_DOCS + "/outline.txt";
			InputStream is = mContext.getAssets().open(outlineFileName);
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line = null;
			List<String> lines = new ArrayList<String>();
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
			return lines.toArray(new String[lines.size()]);
		} catch (IOException e) {
			Log.e(TAG, "Can't get document outline", e);
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
	
	public synchronized Document getDocument(ChapterItem item) {
		if (null == item) {
			return null;
		}
		if (mDocMap.containsKey(item.itemName)) {
			return mDocMap.get(item.itemName);
		}
		Document doc = null;
		try {
			doc = new Document(mContext, ASSET_DOCS, item);
			mDocMap.put(item.itemName, doc);
		} catch (Throwable e) {
			Log.e(TAG, "Can't create doucment", e);
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
		editor.putString(Consts.PREF_DOCUMENT_DEFALUT_NAME, doc.getDocName())
		.putString(Consts.PREF_DOCUMENT_DEFALUT_TITLE, doc.getTitile())
			  .putInt(Consts.PREF_PAGE_DEFALUT_INDEX, doc.getSelectPageIndex())
			  .putInt(Consts.PREF_PAGE_DEFALUT_SCROLLY, doc.getSelectPageScrollY())
			  .commit();
	}
	
	public Document getDefaultDocument() {
		String defaultDocTitle = mPreference.getString(Consts.PREF_DOCUMENT_DEFALUT_TITLE, mOutLine[0]);
		String defaultDocName = mPreference.getString(Consts.PREF_DOCUMENT_DEFALUT_NAME, "1");
		Document doc = getDocument(new ChapterItem(defaultDocTitle, defaultDocName));
		if (doc != null) {
			int defaultPageIndex = mPreference.getInt(Consts.PREF_PAGE_DEFALUT_INDEX, 0);
			doc.setSelectPageIndex(defaultPageIndex);
			int defaultPageScrollY = mPreference.getInt(Consts.PREF_PAGE_DEFALUT_SCROLLY, 0);
			doc.setSelectPageScrollDy(defaultPageScrollY);
			
			int defaultTextSize = mContext.getResources().getInteger(R.integer.text_size_default);
			defaultTextSize = mPreference.getInt(Consts.PREF_PAGE_DEFALUT_TEXT_SIZE, defaultTextSize);
			doc.setTextSize(defaultTextSize);
		}
		return doc;
	}
	
	public void persistOptions(OptionSetting ops) {
		if (null == ops) {
			return;
		}
		Editor editor = mPreference.edit();
		editor.putInt(Consts.PREF_PAGE_DEFALUT_TEXT_SIZE, ops.getTextSize())
			  .putInt(Consts.PREF_PAGE_DEFALUT_LINE_SPACE, ops.getLineSpace())
			  .putInt(Consts.PREF_PAGE_DEFALUT_SCREEN_BRIGHTNESS, ops.getScreenBrightness())
			  .putString(Consts.PREF_DOCUMENT_DEFALUT_THEME, ops.getThemeType().toString())
			  .commit();
	}
	
	public OptionSetting getOptionSetting() {
		return mOptionSetting;
	}
	
	public static synchronized DocumentManager getInstance(Context ctx) {
		if (null == mInstance) {
			mInstance = new DocumentManager(ctx);
		}
		return mInstance;
	}
	
	public static synchronized void GcInstance() {
		mInstance = null;
		System.gc();
	}
}
