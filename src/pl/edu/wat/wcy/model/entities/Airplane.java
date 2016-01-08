package pl.edu.wat.wcy.model.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Set;

@SuppressWarnings("serial")
public class Airplane extends Vehicle {

	private String name;

	public Airplane() {

	}

	public Airplane(String name) {
		this.setName(name);
	}

	public Airplane(String name, int capacity, int x, int y, Set<Cargo> c, Transport t) {
		super(capacity, x, y);
		this.setName(name);
		this.setCurrentCargo(c);
		this.setTransport(t);
	}

	public Airplane(String name, int capacity, int x, int y) {
		super(capacity, x, y);
		this.setName(name);
	}

	@Override
	public String toString() {
		String cargo = " ";
		if (this.getCurrentCargo() != null)
			for (Cargo c : this.getCurrentCargo())
				cargo += (c + " ");
		return ("Pojazd " + this.getVehicleID() + " SAMOLOT - nazwa: " + this.name + ", pojemnosc: "
				+ this.getCapacity() + ", kierowca: " + this.getCurrentDriver() + ". Przewozi: " + cargo);
	}

	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(Color.BLUE);
		g2d.fillOval(this.getxCoord(), this.getyCoord(), 10, 10);
	}

	public void move() {
		if (this.getTransport() != null && this.getTransport().getDestination() != null) {
			int delta = 10;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
