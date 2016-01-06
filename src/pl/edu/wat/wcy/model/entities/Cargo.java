package pl.edu.wat.wcy.model.entities;

import java.util.HashSet;

public class Cargo {
	private int cargoID;
	private HashSet<Vehicle> vehicle;
	private String name;
	private int unitWeight;

	public Cargo() {
	}

	@Override
	public String toString()
	{
		return ("Nazwa: " + this.name + ", liczba: "+ this.unitWeight);
	}
	
	
	public Cargo(String name, int weight) {
		this.setName(name);
		this.setUnitWeight(weight);
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

	public HashSet<Vehicle> getVehicle() {
		return vehicle;
	}

	public void setVehicle(HashSet<Vehicle> vehicle) {
		this.vehicle = vehicle;
	}

	public void addToVehicles(Vehicle v) {
		this.vehicle.add(v);
	}

	public void removeFromVehicles(Vehicle v) {
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
