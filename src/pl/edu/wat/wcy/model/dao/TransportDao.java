package pl.edu.wat.wcy.model.dao;

import java.util.HashSet;

import pl.edu.wat.wcy.model.entities.Transport;

public class TransportDao extends GenericDaoImpl<Transport> {

	public HashSet<Transport> current=new HashSet<Transport>();
	public HashSet<Transport> history = new HashSet<Transport>();
	
	public TransportDao() {
		super(Transport.class);
	}
}
