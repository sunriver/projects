package com.oobe.common;

import java.util.HashMap;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.provider.BaseColumns;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {
	
	private static DataBaseHelper helper = null;
	
	private final static String TABLE_NAME = "OOBE";
	
	private  static StringBuilder SQL = new StringBuilder();
	
	private static String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME
			+ " (" 
			+ OOBEContent._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
			+ OOBEContent.KEY_NAME + " TEXT NOT NULL, " 
			+ OOBEContent.KEY_COUNT + " INTEGER, "
			+ OOBEContent.KEY_TIME + " INTEGER "
			+ " );";
	
	private DataBaseHelper(Context context, String name, CursorFactory factory, int version){
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	
	private DataBaseHelper(Context context){
		this(context, TABLE_NAME, null, 1);
	}
	
	private DataBaseHelper(Context context, String name){
		this(context, name, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Log.v("MainActivity", SQL.toString());
//		db.execSQL(CREATE_TABLE_SQL);
		db.execSQL(SQL.toString());

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
	
	
	public static synchronized DataBaseHelper Instance(Context context, String tableName){
		if (null == helper) {
			helper = new DataBaseHelper(context, tableName);
		}
		return helper;
		
	}
	
	public static class OOBEContent implements BaseColumns{
		public final static String KEY_NAME = "name";
		
		public final static String KEY_COUNT = "count";
		
		public final static String KEY_TIME = "time";
	}
	
	public final static String[][] COLUMNS = {
		{OOBEContent._ID, " INTEGER PRIMARY KEY AUTOINCREMENT, "},
		{OOBEContent.KEY_NAME, " TEXT NOT NULL, " },
		{OOBEContent.KEY_COUNT, " INTEGER, "},
		{OOBEContent.KEY_TIME, " INTEGER "}
	};

   public static HashMap<String, Integer> COLUMNS_INDEXS =  new HashMap<String, Integer>();
   
   static {
	   SQL.append("CREATE TABLE " + TABLE_NAME + "( ");
	   for (int i=0, len=COLUMNS.length; i<len; i++) {
		   String columnName = COLUMNS[i][0];
		   String columnDes = COLUMNS[i][1];
		   SQL.append(columnName + columnDes);
		   COLUMNS_INDEXS.put(columnName, i);
	   }
	   SQL.append(" ); ");
   }
	

}
