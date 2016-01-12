package pl.edu.wat.wcy.model.entities;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import pl.edu.wat.wcy.events.VehicleArrivedEvent;
import pl.edu.wat.wcy.main.Main;
import pl.edu.wat.wcy.view.CreateWindow;

@SuppressWarnings("serial")
public class Airplane extends Vehicle {

	private String name;

	public Airplane() {

	}

	public Airplane(String name) {
		this.setName(name);
		Image img = null;
		try {
			img = ImageIO.read(new File("./resources/airplane.jpg"));
		} catch (IOException e) {
			Main.eventLog.warning("Nie zaladowano ikony!");
			Calendar now = GregorianCalendar.getInstance();
			Main.eventDao.create(new EventLog(now.getTime() + " WARN: " + Main.loggedUser.getName()
					+ " - konstruktor Airplane() - nie za³adowano ikony. Zalogowany user: "
					+ Main.loggedUser.getName()));
		}
		this.icon = img;
		ImageIcon i = new ImageIcon(img);
		this.label = new JLabel(i);
		this.label.setSize(30, 30);
		this.label.setVisible(true);
		CreateWindow.getMapPanel().add(this.label);
	}

	public Airplane(String name, int capacity, int x, int y, Set<Cargo> c, Transport t) {
		super(capacity, x, y);
		Image img = null;
		try {
			img = ImageIO.read(new File("./resources/airplane.jpg"));
		} catch (IOException e) {
			Main.eventLog.warning("Nie zaladowano ikony!");
			Calendar now = GregorianCalendar.getInstance();
			Main.eventDao.create(new EventLog(now.getTime() + " WARN: " + Main.loggedUser.getName()
					+ " - konstruktor Airplane() - nie za³adowano ikony. Zalogowany user: "
					+ Main.loggedUser.getName()));
		}
		this.icon = img;
		this.setName(name);
		this.setCurrentCargo(c);
		this.setTransport(t);
		ImageIcon i = new ImageIcon(img);
		this.label = new JLabel(i);
		this.label.setSize(30, 30);
		this.label.setVisible(true);
		CreateWindow.getMapPanel().add(this.label);
	}

	public Airplane(String name, int capacity, int x, int y) {
		super(capacity, x, y);
		Image img = null;
		try {
			img = ImageIO.read(new File("./resources/airplane.jpg"));
		} catch (IOException e) {
			Main.eventLog.warning("Nie zaladowano ikony!");
			Calendar now = GregorianCalendar.getInstance();
			Main.eventDao.create(new EventLog(now.getTime() + " WARN: " + Main.loggedUser.getName()
					+ " -  konstruktor Airplane() - nie za³adowano ikony. Zalogowany user: "
					+ Main.loggedUser.getName()));
		}
		this.icon = img;
		this.setName(name);
		ImageIcon i = new ImageIcon(img);
		this.label = new JLabel(i);
		this.label.setSize(30, 30);
		this.label.setVisible(true);
		CreateWindow.getMapPanel().add(this.label);
	}

	@Override
	public String toString() {
		String cargo = " ";
		if (this.getCurrentCargo() != null)
			for (Cargo c : this.getCurrentCargo())
				cargo += (c + " ");
		return ("Pojazd " + this.getVehicleID() + " SAMOLOT - nazwa: " + this.name + " (" + this.getxCoord() + ","
				+ this.getyCoord() + "), pojemnosc: " + this.getCapacity() + ", kierowca: " + this.getCurrentDriver()
				+ ". Przewozi: {" + cargo + "}");
	}

	public String toTooltipText() {
		String cargo = " ";
		if (this.getCurrentCargo() != null)
			for (Cargo c : this.getCurrentCargo())
				cargo += (c + " ");
		return ("<html>SAMOLOT - nazwa: " + this.name + "     (" + this.getxCoord() + "," + this.getyCoord()
				+ ")<br>pojemnosc: " + this.getCapacity() + "<br>pilot: " + this.getCurrentDriver() + "<br>Przewozi: {"
				+ cargo + "}</html>");
	}

	public void paint(Graphics g) {
		this.label.setToolTipText(this.toTooltipText());
		this.label.setLocation(this.getxCoord(), this.getyCoord());
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
			if (Math.abs(this.getxCoord() - xDest) < 10 && Math.abs(this.getyCoord() - yDest) < 10)
				this.informAboutTheArrival(new VehicleArrivedEvent(this));
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
