package pl.edu.wat.wcy.model.entities;

import java.util.HashSet;
import java.util.Set;

public class Cargo {
	private int cargoID;
	private Set<Vehicle> vehicle = new HashSet<Vehicle>();
	private String name;
	private int unitWeight;

	public Cargo() {
	}

	@Override
	public String toString()
	{
		return (this.name + "("+ this.unitWeight + ")");
	}
	
	
	public Cargo(String name, int weight) {
		this.setName(name);
		this.setUnitWeight(weight);
//		this.setVehicle(new HashSet<Vehicle>());

	}

	public Cargo(String name, int weight, HashSet<Vehicle> vs) {
		this.setName(name);
		this.setUnitWeight(weight);
		this.setVehicle(vs);
	}

	public int getCargoID() {
		return cargoID;
	}

	public void setCargoID(int cargoID) {
		this.cargoID = cargoID;
	}

	public Set<Vehicle> getVehicle() {
		return vehicle;
	}

	public void setVehicle(Set<Vehicle> vehicle) {
		this.vehicle = vehicle;
	}

	public void addToVehicles(Vehicle v) {
		if(this.vehicle == null) this.vehicle = new HashSet<Vehicle>();
		this.vehicle.add(v);
	}

	public void removeFromVehicles(Vehicle v) {
		if(this.vehicle != null)
		this.vehicle.remove(v);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getUnitWeight() {
		return unitWeight;
	}

	public void setUnitWeight(int unitWeight) {
		this.unitWeight = unitWeight;
	}

}
