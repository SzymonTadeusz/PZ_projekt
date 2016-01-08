package pl.edu.wat.wcy.model.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashSet;
import java.util.Set;

public class Warehouse {

	private int warehouseID;
	private String street;
	private int streetNumber;
	private String warehouseName;
	private int xCoord;
	private int yCoord;
	private Set<Transport> awaitingTransport = new HashSet<Transport>();
	
	@Override
	public String toString()
	{
		return ("Nazwa: "+this.warehouseName + ", " + this.street + "/" 
				+ this.streetNumber + " (" + this.xCoord + ", " + this.yCoord + ")");
	}
	
	public Warehouse(){}
	
	public Warehouse(String street, int streetNr, String wName, int x, int y)
	{
		this.setStreet(street);
		this.setStreetNumber(streetNr);
		this.setWarehouseName(wName);
		this.setxCoord(x);
		this.setyCoord(y);
	}
	
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setColor(Color.DARK_GRAY);
		g2d.fillOval(this.xCoord, this.yCoord, 15,15);
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

	public Set<Transport> getAwaitingTransport() {
		return awaitingTransport;
	}

	public void setAwaitingTransport(Set<Transport> awaitingTransport) {
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
