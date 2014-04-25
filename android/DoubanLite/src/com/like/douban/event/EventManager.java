package com.like.douban.event;

import java.util.ArrayList;
import java.util.List;

import com.like.douban.event.bean.Event;
import com.like.douban.event.bean.EventList;

/**
 * Note: this class is not thread safe.
 * @author alu
 *
 */
public class EventManager {
	private static EventManager sInstance = null;
	
	private List<Event> mParticipantedEvents;
	
	private List<Event> mWishedEvents;
	
	public static EventManager getInstance() {
		if (null == sInstance) {
			sInstance = new EventManager();
		}
		return sInstance;
	}
	
	private EventManager() {
		mParticipantedEvents = new ArrayList<Event>();
		mWishedEvents = new ArrayList<Event>();
	}
	
	public  static void clearInstance() {
		sInstance = null;
	}
	
	public List<Event> getParticipantedEvents() {
		return mParticipantedEvents;
	}
	
	public void saveParticipantEvents(final EventList events) {
		if (events != null ) {
			for (Event evt : events.events) {
				mParticipantedEvents.add(evt);
			}
		}
	}
	
	public void removeAllParticipantEvents() {
		mParticipantedEvents.clear();
	}
	
	public void removeParticipantEvent(final Event event) {
		mParticipantedEvents.remove(event);
	}
	
	public void removeAllWishEvents() {
		mWishedEvents.clear();
	}
	
	public void saveParticipantEvent(final Event event) {
		if (event != null ) {
			mParticipantedEvents.add(event);
		}
	}
	
	public  List<Event> getWisheredEvents() {
		return mWishedEvents;
	}
	
	public void saveWisheredEvents(final EventList events) {
		if (events != null ) {
			for (Event evt : events.events) {
				mWishedEvents.add(evt);
			}
		}
	}
	
	public void saveWishedEvent(final Event event) {
		if (event != null ) {
			mWishedEvents.add(event);
		}
	}
	
	
	public void removeWishedEvent(final Event event) {
		mWishedEvents.remove(event);
	}
	
	public boolean isParticipantedEvent(final String eventID) {
		if (mParticipantedEvents != null) {
			for (Event evt : mParticipantedEvents) {
				if (evt.id.equals(eventID)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isParticipantedEvent(final Event event) {
		return isParticipantedEvent(event.id);
	}
	
	public boolean isWisheredEvent(final String eventID) {
		if (mWishedEvents != null) {
			for (Event evt : mWishedEvents) {
				if (evt.id.equals(eventID)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isWisheredEvent(final Event event) {
		return isWisheredEvent(event.id);
	}
}
