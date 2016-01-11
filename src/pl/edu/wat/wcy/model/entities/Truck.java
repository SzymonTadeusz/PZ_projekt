package pl.edu.wat.wcy.model.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Set;

import pl.edu.wat.wcy.events.VehicleArrivedEvent;

@SuppressWarnings("serial")
public class Truck extends Vehicle {

	private String regNumber;
	private boolean isAbleToWork;

	public Truck() {

	}

	public Truck(String regNr, boolean able) {
		this.setRegNumber(regNr);
		this.setAbleToWork(able);
	}

	public Truck(String regNr, boolean able, int capacity, int x, int y, Set<Cargo> c, Transport t) {
		super(capacity, x, y);
		this.setRegNumber(regNr);
		this.setAbleToWork(able);
		this.setCurrentCargo(c);
		this.setTransport(t);
	}

	public Truck(String regNr, boolean able, int capacity, int x, int y) {
		super(capacity, x, y);
		this.setRegNumber(regNr);
		this.setAbleToWork(able);
	}

	@Override
	public String toString() {
		String cargo = " {";
		String sprawny = " ";
		sprawny = (this.isAbleToWork == true) ? " sprawny" : " niesprawny";
		if (this.getCurrentCargo() != null)
			for (Cargo c : this.getCurrentCargo())
				cargo += (c + " ");
		cargo += "}";
		return ("Pojazd " + this.getVehicleID() + sprawny + " TIR - nr: " + this.getRegNumber() + " ("
				+ this.getxCoord() + "," + this.getyCoord() + "), pojemnosc: " + this.getCapacity() + ", kierowca: "
				+ this.getCurrentDriver() + ". Przewozi: " + cargo);
	}

	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		if (this.isAbleToWork == true)
			g2d.setColor(Color.GREEN);
		else
			g2d.setColor(Color.MAGENTA);
		g2d.fillOval(this.getxCoord(), this.getyCoord(), 10, 10);
	}

	public void move() {
		if (this.getTransport() != null && this.getTransport().getDestination() != null) {
			if (this.isAbleToWork == true) {
				int delta = 3;
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
				if((Math.abs(this.getxCoord()-xDest)<5) && (Math.abs(this.getyCoord()-yDest)<5)) this.informAboutTheArrival(new VehicleArrivedEvent(this)); ;
			}
		}

	}

	public String getRegNumber() {
		return regNumber;
	}

	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}

	public boolean isAbleToWork() {
		return isAbleToWork;
	}

	public void setAbleToWork(boolean isAbleToWork) {
		this.isAbleToWork = isAbleToWork;
	}

}
