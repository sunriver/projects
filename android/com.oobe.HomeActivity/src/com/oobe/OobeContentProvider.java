package com.oobe;

import java.util.HashMap;

import com.oobe.common.DataBaseHelper;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class OobeContentProvider extends ContentProvider {
	
	private static final String TABLE_NAME = "OOBE";
	
	private static final String AUTHORITY = "com.oobe.provider";
	
	private static final String PATH = "people/";
	
	private static final int INIT_CODE = 0;
	
	private static  UriMatcher mUriMatcher;
	
	private SQLiteOpenHelper mOpenHelper = null;
	
	@Override
	public int delete(Uri uri, String where, String[] whereArgs) {
		SQLiteDatabase db = mOpenHelper.getReadableDatabase();
		db.delete(TABLE_NAME, where, whereArgs);
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
        if (mUriMatcher.match(uri) != INIT_CODE) {
        	return null;
        }
		if (null == values){
			values = new ContentValues();
		}
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		db.insert(TABLE_NAME, "", values);
		return uri;
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		mOpenHelper = DataBaseHelper.Instance(getContext(), TABLE_NAME);
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(TABLE_NAME);
        if (mUriMatcher.match(uri) != INIT_CODE) {
        	return null;
        }
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, null);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	static {
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mUriMatcher.addURI(AUTHORITY, PATH, INIT_CODE);
    }

}
