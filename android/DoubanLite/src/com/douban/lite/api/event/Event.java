package com.douban.lite.api.event;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.douban.lite.api.event.EventList.Property;

public class Event implements Serializable {
	private static final String TAG = Event.class.getSimpleName();
	private static final long serialVersionUID = 1L;

	public interface Property {
		public static final String TITLE = "title";
		public static final String ADDRESS = "address";
		public static final String LOC_NAME = "loc_name";
		public static final String LOC_ID = "loc_id";
		public static final String CONTENT = "content";
		public static final String BEGIN_TIME = "begin_time";
		public static final String END_TIME = "end_time";
	}

	public String title;
	public String address;
	public String loc_name;
	public String loc_id;
	public String content;
	public Date begin_time;
	public Date end_time;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		address = address;
	}

	public String getLoc_name() {
		return loc_name;
	}

	public void setLoc_name(String loc_name) {
		this.loc_name = loc_name;
	}

	public String getLoc_id() {
		return loc_id;
	}

	public void setLoc_id(String loc_id) {
		this.loc_id = loc_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getBegin_time() {
		return begin_time;
	}

	public void setBegin_time(Date begin_time) {
		this.begin_time = begin_time;
	}

	public Date getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}

	public static Event fromJSONObject(JSONObject obj) {
		Event evt = new Event();
		try {
			if (obj.has(Property.TITLE)) {
				evt.title = obj.getString(Property.TITLE);
			}
			if (obj.has(Property.ADDRESS)) {
				evt.address = obj.getString(Property.ADDRESS);
			}
			if (obj.has(Property.LOC_NAME)) {
				evt.loc_name = obj.getString(Property.LOC_NAME);
			}
			if (obj.has(Property.LOC_ID)) {
				evt.loc_id = obj.getString(Property.LOC_ID);
			}
			if (obj.has(Property.CONTENT)) {
				evt.content = obj.getString(Property.CONTENT);
			}
			if (obj.has(Property.BEGIN_TIME)) {
				evt.begin_time = new Date(obj.getString(Property.BEGIN_TIME));
			}
			if (obj.has(Property.END_TIME)) {
				evt.end_time = new Date(obj.getString(Property.END_TIME));
			}

		} catch (Exception any) {
			Log.w(TAG, "Fail to parse json object", any);
		}
		return evt;

	}
}
