package com.like.douban.event.bean;

import java.io.Serializable;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;


public class LocationList implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String TAG = LocationList.class.getSimpleName();

	public interface Property {
		public static final String COUNT = "count";
		public static final String START = "start";
		public static final String TOTAL = "total";
		public static final String LOCS = "locs";
	}

	public int count;
	public int start;
	public int total;
	public Location[] locs;

	
	public LocationList() {
		this.count = 0;
		this.start = 0;
		this.total = 0;
		this.locs = new Location[0];
	}
	
	
	public String[] getAllLocationNames() {
		String[] names = new String[locs.length];
		for (int i = 0, len = locs.length; i < len; i++) {
			names[i] = locs[i].name;
		}
		return names;
	}
	
	public String[] getAllLocationUids() {
		String[] uids = new String[locs.length];
		for (int i = 0, len = locs.length; i < len; i++) {
			uids[i] = locs[i].uid;
		}
		return uids;
	}

	public static LocationList fromJSONObject(JSONObject obj) {
		LocationList locList = new LocationList();
		try {
			if (obj.has(Property.COUNT)) {
				locList.count = obj.getInt(Property.COUNT);
			}
			if (obj.has(Property.START)) {
				locList.start = obj.getInt(Property.START);
			}
			if (obj.has(Property.TOTAL)) {
				locList.total = obj.getInt(Property.TOTAL);
			}
			if (obj.has(Property.LOCS)) {
				JSONArray jsonArray = obj.getJSONArray(Property.LOCS);
				int len = jsonArray.length(); 
				locList.locs = new Location[len];
				for (int i = 0; i < len; i++) {
					JSONObject locJsonObj = jsonArray.getJSONObject(i);
					locList.locs[i] = Location.fromJSONObject(locJsonObj);
				}
			}
		} catch (Exception any) {
			Log.w(TAG, "Fail to parse json object", any);
		}
		return locList;

	}

}
