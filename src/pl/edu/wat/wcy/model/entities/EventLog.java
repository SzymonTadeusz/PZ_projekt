package pl.edu.wat.wcy.model.entities;


public class EventLog {

	private int eventID;
	private String event;

	public EventLog() {
	}
	
	public EventLog(String msg)
	{
		this.event = msg;
	}

	public int getEventLogID() {
		return eventID;
	}

	public void setEventLogID(int eventLogID) {
		this.eventID = eventLogID;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

}
