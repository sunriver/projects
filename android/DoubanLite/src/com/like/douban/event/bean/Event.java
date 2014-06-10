package com.like.douban.event.bean;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONObject;

import android.graphics.Point;
import android.graphics.PointF;
import android.text.TextUtils;
import android.util.Log;
public final class Event implements Serializable {
	private static final String TAG = Event.class.getSimpleName();
	private static final long serialVersionUID = 1L;

	private interface Property {
		public static final String TITLE = "title";
		public static final String ID = "id";
		public static final String ADDRESS = "address";
		public static final String LOC_NAME = "loc_name";
		public static final String LOC_ID = "loc_id";
		public static final String CONTENT = "content";
		public static final String BEGIN_TIME = "begin_time";
		public static final String END_TIME = "end_time";
		public static final String IMAGE_LMOBILE = "image_lmobile";
		public static final String IMAGE = "image";
		public static final String GEO = "geo";
		public static final String PARTICIPANT_COUNT = "participant_count";
		public static final String WISHER_COUNT = "wisher_count";
		public static final String CATEGORY = "category";
		public static final String CATEGORY_NAME = "category_name";
		public static final String ADAPT_URL = "adapt_url";
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Event) {
			Event evt = (Event) o;
			return id.equals(evt.id);
		}
		return false;
	}
	
	

	@Override
	public int hashCode() {
		return Integer.valueOf(id);
	}
	
	private double longitude = -1;
	private double latitude = -1;


	public String id;
	public String adapt_url;
	public String geo;
	public int participant_count;
	public int wisher_count;
	public String category;
	public String category_name;
	public String title;
	public String image_lmobile;
	public String image;
	public String address;
	public String loc_name;
	public String loc_id;
	public String content;
	public Date begin_time;
	public Date end_time;
	
	public double getLongitude() {
		return longitude;
	}
	
	public double getLatitude() {
		return latitude;
	}

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
		this.address = address;
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
	
	private static Date convertToDate(String sDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sdf.parse(sDate);
		} catch (ParseException ignored) {
		}
		return null;
	}
	
	
	public String getEventTime() {
		if (null == begin_time || null == end_time) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd HH:mm");
		String sBeginTime = sdf.format(begin_time);
		String sEndTime = sdf.format(end_time);
		return sBeginTime + " - " + sEndTime;
	}
	
	public PointF getGeoPoint() {
		PointF p = new PointF();
		String[] subs = this.geo.split(" ");
		p.x = Float.valueOf(subs[0]);
		p.y = Float.valueOf(subs[1]);
		return p;
	}

	public static Event fromJSONObject(JSONObject obj) {
		Event evt = new Event();
		try {
			if (obj.has(Property.TITLE)) {
				evt.title = obj.getString(Property.TITLE);
			}
			if (obj.has(Property.ID)) {
				evt.id = obj.getString(Property.ID);
			}
			if (obj.has(Property.IMAGE_LMOBILE)) {
				evt.image_lmobile = obj.getString(Property.IMAGE_LMOBILE);
			}
			if (obj.has(Property.IMAGE)) {
				evt.image = obj.getString(Property.IMAGE);
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
				String beginTime = obj.getString(Property.BEGIN_TIME);
				evt.begin_time = convertToDate(beginTime);
			}
			if (obj.has(Property.END_TIME)) {
				String endTime = obj.getString(Property.END_TIME);
				evt.end_time = convertToDate(endTime);
			}
			if (obj.has(Property.GEO)) {
				evt.geo = obj.getString(Property.GEO);
				if (!TextUtils.isEmpty(evt.geo)) {
					String[] subs = evt.geo.split("\\s+");
					if (subs.length == 2) {
						evt.latitude = Double.valueOf(subs[0]);
						evt.longitude = Double.valueOf(subs[1]);
					}
				}
			}
			if (obj.has(Property.PARTICIPANT_COUNT)) {
				evt.participant_count  = obj.getInt(Property.PARTICIPANT_COUNT);
			}
			if (obj.has(Property.WISHER_COUNT)) {
				evt.wisher_count  = obj.getInt(Property.WISHER_COUNT);
			}
			if (obj.has(Property.CATEGORY)) {
				evt.category = obj.getString(Property.CATEGORY);
			}
			if (obj.has(Property.CATEGORY_NAME)) {
				evt.category_name = obj.getString(Property.CATEGORY_NAME);
			}
			if (obj.has(Property.ADAPT_URL)) {
				evt.adapt_url = obj.getString(Property.ADAPT_URL);
			}

		} catch (Exception any) {
			Log.w(TAG, "Fail to parse json object", any);
		}
		return evt;

	}
}
