package pl.edu.wat.wcy.model.entities;

import java.util.HashSet;

public class EventLog {

	private int eventLogID;
	private HashSet<String> events;
	
	
	public int getEventLogID() {
		return eventLogID;
	}
	public void setEventLogID(int eventLogID) {
		this.eventLogID = eventLogID;
	}
	public HashSet<String> getEvents() {
		return events;
	}
	public void setEvents(HashSet<String> events) {
		this.events = events;
	} 
	
}
