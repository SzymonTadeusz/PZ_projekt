package pl.edu.wat.wcy.model.entities;

import java.util.HashSet;
import java.util.Set;

public class TransportHistory {

	private int transportHistoryID;
	private Set<Transport> transportHistory = new HashSet<Transport>();
	
	public TransportHistory(){}
	
	public TransportHistory(HashSet<Transport> tH)
	{
		this.setTransportHistory(tH);
	}
	
	public Set<Transport> getTransportHistory() {
		return transportHistory;
	}
	public void setTransportHistory(Set<Transport> transportHistory) {
		this.transportHistory = transportHistory;
	}
	
	public void addToTH(Transport t)
	{
		this.transportHistory.add(t);
	}
	
	public void removeFromTH(Transport t)
	{
		this.transportHistory.remove(t);
	}

	public int getTransportHistoryID() {
		return transportHistoryID;
	}

	public void setTransportHistoryID(int transportHistoryID) {
		this.transportHistoryID = transportHistoryID;
	}
	
	
}
