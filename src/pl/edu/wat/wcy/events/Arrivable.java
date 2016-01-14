package pl.edu.wat.wcy.events;

import java.util.Calendar;
import java.util.GregorianCalendar;

import pl.edu.wat.wcy.main.Main;
import pl.edu.wat.wcy.model.entities.Cargo;
import pl.edu.wat.wcy.model.entities.EventLog;

public interface Arrivable {
	
	public default void handleArrival(VehicleArrivedEvent e)
	{
		Main.eventLog.info("Dojecha³ " + e.getVehicleArrived());
		String cargo = " ";
		if(e.getVehicleArrived().getCurrentCargo() != null)
			for (Cargo c : e.getVehicleArrived().getCurrentCargo())
				cargo += (c + ", ");
		Main.eventLog.info("Roz³adowano: " + cargo);
		Calendar now = GregorianCalendar.getInstance();
		Main.eventDao.create(new EventLog(now.getTime()+"INFO: " + Main.loggedUser.getName() + " - Dojecha³ pojazd " + e.getVehicleArrived()+ ". Zalogowany user: "+Main.loggedUser.getName()));
		Main.getTransportDao().current.remove(e.getVehicleArrived().getTransport());
		Main.getTransportDao().history.add(e.getVehicleArrived().getTransport());
		e.getVehicleArrived().setCurrentCargo(null);
		e.getVehicleArrived().setArrivalListener(null);
		e.getVehicleArrived().setTransport(null);
		Main.getVehicleDao().update(e.getVehicleArrived()); 
	}

}
