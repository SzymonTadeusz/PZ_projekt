package pl.edu.wat.wcy.model.entities;

import java.util.HashSet;

public class TransportHistory {

	private int transportHistoryID;
	private HashSet<Transport> transportHistory;
	
	public TransportHistory(){}
	
	public TransportHistory(HashSet<Transport> tH)
	{
		this.setTransportHistory(tH);
	}
	
	public HashSet<Transport> getTransportHistory() {
		return transportHistory;
	}
	public void setTransportHistory(HashSet<Transport> transportHistory) {
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
