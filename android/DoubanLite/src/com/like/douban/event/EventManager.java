package com.like.douban.event;

import com.like.douban.event.bean.Event;
import com.like.douban.event.bean.EventList;

public class EventManager {
	private static EventManager mInstance = new EventManager();
	
	private EventList mParticipantedEvents;
	
	private EventList mWishedEvents;
	
	public static EventManager getInstance() {
		return mInstance;
	}
	
	public void clear() {
		mInstance = null;
	}
	
	public  EventList getParticipantedEvents() {
		return mParticipantedEvents;
	}
	
	public void saveParticipantEvents(final EventList events) {
		this.mParticipantedEvents = events;
	}
	
	public  EventList getWisheredEvents() {
		return mWishedEvents;
	}
	
	public void saveWisheredEvents(final EventList events) {
		this.mWishedEvents = events;
	}
	
	public boolean isParticipantedEvent(final String eventID) {
		if (mParticipantedEvents != null) {
			for (Event evt : mParticipantedEvents.events) {
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
			for (Event evt : mWishedEvents.events) {
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
