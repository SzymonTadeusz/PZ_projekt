package pl.edu.wat.wcy.main;

import pl.edu.wat.wcy.model.dao.*;
import pl.edu.wat.wcy.model.entities.*;
import pl.edu.wat.wcy.view.CreateWindow;

public class Main {
	
	public static VehicleDao pojazdDao = new VehicleDao();
	public static CargoDao cargoDao = new CargoDao();
	public static TransportDao transportDao = new TransportDao();
	public static WarehouseDao warehouseDao = new WarehouseDao();
	public static DriverDao driverDao = new DriverDao();
	public static CountryDao countryDao = new CountryDao();
	
	

	public static void main(String[] args) {
		CreateWindow window = new CreateWindow();

		
		Driver d = new Driver("Szymon", "M", "12345", "KatA-1234");
		Truck tir = new Truck("LU12345", true, 1000, 100, 100);
		tir.setCurrentDriver(d);
		Airplane sam = new Airplane("Boeing 363", 10000, 150, 150);
		pojazdDao.create(tir);
		pojazdDao.create(sam);
		
		Vehicle obj2 = pojazdDao.retrieve(tir.getVehicleID());
		Vehicle obj = pojazdDao.retrieve(sam.getVehicleID());
		System.out.println(obj2);
		System.out.println(obj);
			
	}



	public static VehicleDao getPojazdDao() {
		return pojazdDao;
	}



	public static CargoDao getCargoDao() {
		return cargoDao;
	}



	public static TransportDao getTransportDao() {
		return transportDao;
	}



	public static WarehouseDao getWarehouseDao() {
		return warehouseDao;
	}



	public static DriverDao getDriverDao() {
		return driverDao;
	}



	public static CountryDao getCountryDao() {
		return countryDao;
	}

	
}
