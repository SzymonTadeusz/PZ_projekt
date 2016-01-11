package pl.edu.wat.wcy.events;

import pl.edu.wat.wcy.model.entities.Cargo;

public interface Arrivable {
	
	public default void handleArrival(VehicleArrivedEvent e)
	{
		System.out.println("Dojechał " + e.getVehicleArrived());
		String cargo = " ";
		if(e.getVehicleArrived().getCurrentCargo() != null)
			for (Cargo c : e.getVehicleArrived().getCurrentCargo())
				cargo += (c + ", ");
		System.out.println("Rozładowano: " + cargo);
		e.getVehicleArrived().setCurrentCargo(null);
		e.getVehicleArrived().setArrivalListener(null);
		e.getVehicleArrived().setTransport(null);
	}

}
