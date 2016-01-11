package pl.edu.wat.wcy.model.dao;

import pl.edu.wat.wcy.model.entities.CurrentTransportsList;
import pl.edu.wat.wcy.model.entities.Transport;
import pl.edu.wat.wcy.model.entities.TransportHistory;

public class TransportDao extends GenericDaoImpl<Transport> {

	public CurrentTransportsList current=new CurrentTransportsList();
	public TransportHistory history = new TransportHistory();
	
	public TransportDao() {
		super(Transport.class);
	}
}
