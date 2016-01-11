package pl.edu.wat.wcy.events;

import pl.edu.wat.wcy.model.entities.Vehicle;

public class VehicleArrivedEvent {
	
	private Vehicle vehicleArrived;
	
	public VehicleArrivedEvent() {}

	public VehicleArrivedEvent(Vehicle vehicleArrived) {
		this.vehicleArrived = vehicleArrived;
	}

	public Vehicle getVehicleArrived() {
		return vehicleArrived;
	}

	public void setVehicleArrived(Vehicle vehicleArrived) {
		this.vehicleArrived = vehicleArrived;
	}
	
}
