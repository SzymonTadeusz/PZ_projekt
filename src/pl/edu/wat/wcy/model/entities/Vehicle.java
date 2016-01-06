package pl.edu.wat.wcy.model.entities;

import java.io.Serializable;
import java.util.HashSet;

public abstract class Vehicle implements Serializable {
	private static final long serialVersionUID = 1L;
	private int vehicleID;
	private int capacity;
	private Driver currentDriver;
	private HashSet<Cargo> currentCargo;
	private int xCoord;
	private int yCoord;

	public Vehicle() {
		super();
	}

	public Vehicle(int capacity, int x, int y) {
		this.setCapacity(capacity);
		this.setxCoord(x);
		this.setyCoord(y);
	}

	@Override
	public String toString()
	{
		String cargo=null;
		if(currentCargo!=null)
			for(Cargo c: currentCargo)
				cargo+=c;
		return ("Pojazd "+this.vehicleID+", pojemnosc: "+this.capacity+", kierowca: "+this.currentDriver+". Przewozi: "+cargo);
	}

	public int getVehicleID() {
		return vehicleID;
	}

	public void setVehicleID(int vehicleID) {
		this.vehicleID = vehicleID;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public Driver getCurrentDriver() {
		return currentDriver;
	}

	public void setCurrentDriver(Driver currentDriver) {
		this.currentDriver = currentDriver;
	}

	public HashSet<Cargo> getCurrentCargo() {
		return currentCargo;
	}

	public void setCurrentCargo(HashSet<Cargo> currentCargo) {
		this.currentCargo = currentCargo;
	}

	public void addToCargo(Cargo addCargo) {
		this.currentCargo.add(addCargo);
	}

	public void removeFromCargo(Cargo remCargo) {
		this.currentCargo.remove(remCargo);
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

}
