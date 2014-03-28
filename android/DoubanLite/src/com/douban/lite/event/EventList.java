package com.douban.lite.event;

import java.io.Serializable;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;


public class EventList implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String TAG = EventList.class.getSimpleName();

	public interface Property {
		public static final String COUNT = "count";
		public static final String START = "start";
		public static final String TOTAL = "total";
		public static final String EVENTS = "events";
	}

	public int count;
	public int start;
	public int total;
	public Event[] events;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public Event[] getEvents() {
		return events;
	}

	public void setEvents(Event[] events) {
		this.events = events;
	}

	public static EventList fromJSONObject(JSONObject obj) {
		EventList eventList = new EventList();
		try {
			if (obj.has(Property.COUNT)) {
				eventList.count = obj.getInt(Property.COUNT);
			}
			if (obj.has(Property.START)) {
				eventList.start = obj.getInt(Property.START);
			}
			if (obj.has(Property.TOTAL)) {
				eventList.total = obj.getInt(Property.TOTAL);
			}
			if (obj.has(Property.EVENTS)) {
				JSONArray jsonArray = obj.getJSONArray(Property.EVENTS);
				int len = jsonArray.length(); 
				eventList.events = new Event[len];
				for (int i = 0; i < len; i++) {
					JSONObject eventJsonObj = jsonArray.getJSONObject(i);
					eventList.events[i] = Event.fromJSONObject(eventJsonObj);
				}
			}
		} catch (Exception any) {
			Log.w(TAG, "Fail to parse json object", any);
		}
		return eventList;

	}

}
