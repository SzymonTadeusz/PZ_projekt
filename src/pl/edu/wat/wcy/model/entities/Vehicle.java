package pl.edu.wat.wcy.model.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import pl.edu.wat.wcy.events.Arrivable;
import pl.edu.wat.wcy.events.VehicleArrivedEvent;

public abstract class Vehicle implements Serializable {
	private static final long serialVersionUID = 1L;
	private int vehicleID;
	private int capacity;
	private Driver currentDriver;
	private Set<Cargo> currentCargo = new HashSet<Cargo>();
	private int xCoord;
	private int yCoord;
	private Transport transport;
	private Arrivable listener;

	public Vehicle() {
		super();
	}

	public Vehicle(int capacity, int x, int y) {
		this.setCapacity(capacity);
		this.setxCoord(x);
		this.setyCoord(y);
	}

	public Vehicle(int capacity, int x, int y, Transport t) {
		this.setCapacity(capacity);
		this.setxCoord(x);
		this.setyCoord(y);
		this.setTransport(t);
	}

	@Override
	public String toString() {
		String cargo = " ";
		if (currentCargo != null)
			for (Cargo c : currentCargo)
				cargo += (c + ", ");
		return ("Pojazd " + this.vehicleID + ", pojemnosc: " + this.capacity + ", kierowca: " + this.currentDriver
				+ ". Przewozi: " + cargo + "Transport do: " + this.getTransport().getDestination());
	}
	

    public void setArrivalListener(Arrivable obserwator){
        listener = obserwator;
    }

	public void informAboutTheArrival(VehicleArrivedEvent e) { //informuje o zdarzeniach
                listener.handleArrival(e);
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
		if (this.currentDriver != null && this.currentDriver != currentDriver)
			currentDriver.setDriverOf(this);
	}

	public Set<Cargo> getCurrentCargo() {
		return currentCargo;
	}

	public void setCurrentCargo(Set<Cargo> currentCargo) {
		this.currentCargo = currentCargo;
	}

	public void addToCargo(Cargo addCargo) {
		if (this.currentCargo == null)
			this.currentCargo = new HashSet<Cargo>();
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

	public Transport getTransport() {
		return transport;
	}

	public void setTransport(Transport transport) {
		this.transport = transport;
	}

	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(Color.GREEN);
		g2d.fillOval(this.xCoord, this.yCoord, 10, 10);
	}

	public void move() {
		if (this.getTransport() != null && this.getTransport().getDestination() != null) {
			int delta = 1;
			int xDest = this.getTransport().getDestination().getxCoord();
			int yDest = this.getTransport().getDestination().getyCoord();
			if (this.getxCoord() < xDest)
				this.setxCoord(getxCoord() + delta);
			if (this.getxCoord() > xDest)
				this.setxCoord(getxCoord() - delta);
			if (this.getyCoord() < yDest)
				this.setyCoord(getyCoord() + delta);
			if (this.getyCoord() > yDest)
				this.setyCoord(getyCoord() - delta);

		}
	}

}
