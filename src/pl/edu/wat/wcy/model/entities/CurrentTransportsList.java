package pl.edu.wat.wcy.model.entities;

import java.util.HashSet;

public class CurrentTransportsList {

	private int currentTransportsListID;
	private HashSet<Transport> transports;
	
	public CurrentTransportsList(){}
	
	public CurrentTransportsList(HashSet<Transport> t)
	{
		this.setTransports(t);
	}
	
	@Override
	public String toString()
	{
		String trans=null;
		if(transports != null)
			for(Transport t : transports)
				trans+=t.toString();
		return trans;
	}
	
	public int getCurrentTransportsListID() {
		return currentTransportsListID;
	}
	public void setCurrentTransportsListID(int currentTransportsListID) {
		this.currentTransportsListID = currentTransportsListID;
	}
	public HashSet<Transport> getTransports() {
		return transports;
	}
	public void setTransports(HashSet<Transport> transports) {
		this.transports = transports;
	}
	public void addToTransports(Transport t)
	{
		this.transports.add(t);
	}
	public void removeFromTransports(Transport t)
	{
		this.transports.remove(t);
	}
}
