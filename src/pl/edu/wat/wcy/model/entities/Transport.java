package pl.edu.wat.wcy.model.entities;

public class Transport {

	private int transportID;
	private Warehouse destination;
	
	public Transport(){}
	
	public Transport(Warehouse dest)
	{
		this.setDestination(dest);
	}
	
	
	public Warehouse getDestination() {
		return destination;
	}
	public void setDestination(Warehouse destination) {
		this.destination = destination;
	}
	public int getTransportID() {
		return transportID;
	}
	public void setTransportID(int transportID) {
		this.transportID = transportID;
	}
	
}
