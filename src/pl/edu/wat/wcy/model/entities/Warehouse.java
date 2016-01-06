package pl.edu.wat.wcy.model.entities;

import java.util.HashSet;

public class Warehouse {

	private int warehouseID;
	private String street;
	private int streetNumber;
	private String warehouseName;
	private int xCoord;
	private int yCoord;
	private HashSet<Transport> awaitingTransport;
	
	public Warehouse(){}
	
	public Warehouse(String street, int streetNr, String wName, int x, int y)
	{
		this.setStreet(street);
		this.setStreetNumber(streetNr);
		this.setWarehouseName(wName);
		this.setxCoord(x);
		this.setyCoord(y);
	}
	
	public int getWarehouseID() {
		return warehouseID;
	}
	public void setWarehouseID(int warehouseID) {
		this.warehouseID = warehouseID;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public int getStreetNumber() {
		return streetNumber;
	}
	public void setStreetNumber(int streetNumber) {
		this.streetNumber = streetNumber;
	}
	public String getWarehouseName() {
		return warehouseName;
	}
	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public int getxCoord() {
		return xCoord;
	}

	public void setxCoord(int xCoord) {
		this.xCoord = xCoord;
	}

	public int getyCoord() {
		return yCoord;
	}

	public void setyCoord(int yCoord) {
		this.yCoord = yCoord;
	}

	public HashSet<Transport> getAwaitingTransport() {
		return awaitingTransport;
	}

	public void setAwaitingTransport(HashSet<Transport> awaitingTransport) {
		this.awaitingTransport = awaitingTransport;
	}
	
	public void addToTransports(Transport t)
	{
		this.getAwaitingTransport().add(t);
	}
	
	public void removeFromTransports(Transport t)
	{
		this.getAwaitingTransport().remove(t);
	}
	
}
