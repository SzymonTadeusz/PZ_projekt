package pl.edu.wat.wcy.model.entities;

import java.io.Serializable;
import java.util.HashSet;

import javax.persistence.*;
import javax.persistence.MappedSuperclass;

/**
 * Entity implementation class for Entity: A
 *
 */
public abstract class Vehicle implements Serializable {
	private static final long serialVersionUID = 1L;
	private int vehicleID;
	private int capacity;
	private Driver currentDriver;
	private HashSet<Cargo> currentCargo;
	

	public Vehicle() {
		super();
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

   
}
