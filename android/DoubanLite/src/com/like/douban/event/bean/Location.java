package com.like.douban.event.bean;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONObject;
import android.util.Log;
public class Location implements Serializable {
	private static final String TAG = Location.class.getSimpleName();
	private static final long serialVersionUID = 1L;

	private interface Property {
		public static final String PARENT = "parent";
		public static final String HABITABLE = "habitable";
		public static final String ID = "id";
		public static final String NAME = "name";
		public static final String UID = "uid";
	}

	public String parent;
	public String habitable;
	public String id;
	public String name;
	public String uid;

	public static Location fromJSONObject(JSONObject obj) {
		Location evt = new Location();
		try {
			if (obj.has(Property.PARENT)) {
				evt.parent = obj.getString(Property.PARENT);
			}
			if (obj.has(Property.HABITABLE)) {
				evt.habitable = obj.getString(Property.HABITABLE);
			}
			if (obj.has(Property.ID)) {
				evt.id = obj.getString(Property.ID);
			}
			if (obj.has(Property.NAME)) {
				evt.name = obj.getString(Property.NAME);
			}
			if (obj.has(Property.UID)) {
				evt.uid = obj.getString(Property.UID);
			}

		} catch (Exception any) {
			Log.w(TAG, "Fail to parse json object", any);
		}
		return evt;

	}
}
