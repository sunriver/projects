package com.coco.reader.xiyouji;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.coco.reader.MainActivity;
import com.sunriver.advs.AdvsManager;

public class HomeActivity extends MainActivity {
	private static final String TAG = HomeActivity.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final String itstID = getItstID();
		if (!TextUtils.isEmpty(itstID)) {
			Log.d(TAG, "onCreate() itst_id:" + itstID);
			AdvsManager.showAdvs(this, itstID);
		}
	}
	
	private final String getItstID() {
		ApplicationInfo info;
		try {
			info = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
			return info.metaData.getString("com.coco.reader.xiyouji.itst_id");
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
