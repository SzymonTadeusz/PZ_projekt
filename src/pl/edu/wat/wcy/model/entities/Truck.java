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
public class Truck extends Vehicle {

	private String regNumber;
	private boolean isAbleToWork;

	public Truck() {

	}

	public Truck(String regNr, boolean able) {
		this.setRegNumber(regNr);
		this.setAbleToWork(able);
		Image img = null;
		try {
			if (this.isAbleToWork)
				img = ImageIO.read(new File("./resources/truckAble.jpg"));
			else
				img = ImageIO.read(new File("./resources/truckDisabled.jpg"));
		} catch (IOException e) {
			Main.eventLog.warning("Nie zaladowano ikony!");
			Calendar now = GregorianCalendar.getInstance();
			Main.eventDao.create(new EventLog(now.getTime() + " WARN: " + Main.loggedUser.getName()
					+ " - konstruktor Truck() - nie za³adowano ikony. Zalogowany user: " + Main.loggedUser.getName()));
		}
		this.icon = img;
		ImageIcon i = new ImageIcon(img);
		this.label = new JLabel(i);
		this.label.setSize(30, 30);
		this.label.setVisible(true);
		CreateWindow.getMapPanel().add(this.label);
	}

	public Truck(String regNr, boolean able, int capacity, int x, int y, Set<Cargo> c, Transport t) {
		super(capacity, x, y);
		this.setRegNumber(regNr);
		this.setAbleToWork(able);
		this.setCurrentCargo(c);
		this.setTransport(t);
		Image img = null;
		try {
			if (this.isAbleToWork)
				img = ImageIO.read(new File("./resources/truckAble.jpg"));
			else
				img = ImageIO.read(new File("./resources/truckDisabled.jpg"));
		} catch (IOException e) {
			Main.eventLog.warning("Nie zaladowano ikony!");
			Calendar now = GregorianCalendar.getInstance();
			Main.eventDao.create(new EventLog(now.getTime() + "WARN: " + Main.loggedUser.getName()
					+ " - konstruktor Truck() - nie za³adowano ikony. Zalogowany user: " + Main.loggedUser.getName()));
		}
		this.icon = img;
		ImageIcon i = new ImageIcon(img);
		this.label = new JLabel(i);
		this.label.setSize(30, 30);
		this.label.setVisible(true);
		CreateWindow.getMapPanel().add(this.label);
	}

	public Truck(String regNr, boolean able, int capacity, int x, int y) {
		super(capacity, x, y);
		this.setRegNumber(regNr);
		this.setAbleToWork(able);
		Image img = null;
		try {
			if (this.isAbleToWork)
				img = ImageIO.read(new File("./resources/truckAble.jpg"));
			else
				img = ImageIO.read(new File("./resources/truckDisabled.jpg"));
		} catch (IOException e) {
			Main.eventLog.warning("Nie zaladowano ikony!");
			Calendar now = GregorianCalendar.getInstance();
			Main.eventDao.create(new EventLog(now.getTime() + "WARN: " + Main.loggedUser.getName()
					+ " - konstruktor Truck() - nie za³adowano ikony. Zalogowany user: " + Main.loggedUser.getName()));
		}
		this.icon = img;
		ImageIcon i = new ImageIcon(img);
		this.label = new JLabel(i);
		this.label.setSize(30, 30);
		this.label.setVisible(true);
		CreateWindow.getMapPanel().add(this.label);
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

	public String toTooltipText() {
		String cargo = " {";
		String sprawny = " ";
		sprawny = (this.isAbleToWork == true) ? " sprawny" : " niesprawny";
		if (this.getCurrentCargo() != null)
			for (Cargo c : this.getCurrentCargo())
				cargo += (c + " ");
		cargo += "}";
		return ("<html>" + sprawny + " TIR - nr: " + this.getRegNumber() + "     (" + this.getxCoord() + ","
				+ this.getyCoord() + ")<br>pojemnosc: " + this.getCapacity() + "<br>kierowca: "
				+ this.getCurrentDriver() + "<br>Przewozi: " + cargo + "</html>");
	}

	public void paint(Graphics g) {
		this.label.setToolTipText(this.toTooltipText());
		this.label.setLocation(this.getxCoord(), this.getyCoord());
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
				if ((Math.abs(this.getxCoord() - xDest) < 5) && (Math.abs(this.getyCoord() - yDest) < 5))
					this.informAboutTheArrival(new VehicleArrivedEvent(this));
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
