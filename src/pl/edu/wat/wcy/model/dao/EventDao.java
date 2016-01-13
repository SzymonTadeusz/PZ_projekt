package pl.edu.wat.wcy.model.dao;

import pl.edu.wat.wcy.model.entities.EventLog;

public class EventDao extends GenericDaoImpl<EventLog> {

	public EventDao()
	{
		super(EventLog.class);
	}

}
