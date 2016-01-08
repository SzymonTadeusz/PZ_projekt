package pl.edu.wat.wcy.main;

import javax.persistence.PersistenceException;

import pl.edu.wat.wcy.model.dao.*;
import pl.edu.wat.wcy.model.entities.*;
import pl.edu.wat.wcy.view.CreateWindow;

public class Main {

	public static VehicleDao vehicleDao = new VehicleDao();
	public static CargoDao cargoDao = new CargoDao();
	public static TransportDao transportDao = new TransportDao();
	public static WarehouseDao warehouseDao = new WarehouseDao();
	public static DriverDao driverDao = new DriverDao();
	public static CountryDao countryDao = new CountryDao();

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		CreateWindow window = new CreateWindow();

		addVehicles();
		addDrivers();
		addWarehouses();
		addCargos();
		for (Vehicle v : vehicleDao.getList()) {
			Driver d = (Driver) driverDao.getList().get(vehicleDao.getList().indexOf(v));
			Cargo c = (Cargo) cargoDao.getList().get(vehicleDao.getList().indexOf(v));
			d.setDriverOf(v);
			c.addToVehicles(v);
			v.setCurrentDriver(d);
		}
	}

	public static void addWarehouses() {
		Warehouse w = new Warehouse("Berlin, Weierstraße", 3, "Magazyn Berlin 1", 195, 165);
		Warehouse w2 = new Warehouse("Warszawa, Marszałkowska", 102, "Magazyn Warszawa 1", 505, 185);
		Warehouse w3 = new Warehouse("Wrocław, Pl. Katedralny", 1, "Magazyn Wrocław 1", 340, 260);
		Warehouse w4 = new Warehouse("Praha, Karluv Pl.", 30, "Magazyn Praga 1", 235, 325);
		Warehouse w5 = new Warehouse("Budapest, Szegetek", 1, "Magazyn Budapeszt 1", 430, 490);
		Warehouse w6 = new Warehouse("Львов, Вернигори", 11, "Magazyn Lwów 1", 630, 345);
		Warehouse w7 = new Warehouse("Vilnius, Šimašiusai", 25, "Magazyn Wilno 1", 685, 15);
		Warehouse w8 = new Warehouse("Stuttgart, Kohlstraße", 25, "Magazyn Stuttgart 1", 20, 410);
		try {
			warehouseDao.create(w);
			warehouseDao.create(w2);
			warehouseDao.create(w3);
			warehouseDao.create(w4);
			warehouseDao.create(w5);
			warehouseDao.create(w6);
			warehouseDao.create(w7);
			warehouseDao.create(w8);
		} catch (PersistenceException e) {
			System.out.println("Blad Hibernate'a: " + e.getMessage());
		}
	}

	public static void addCargos() {
		Cargo c1 = new Cargo("Cegły", 8000);
		Cargo c2 = new Cargo("Marchew", 1000);
		Cargo c3 = new Cargo("Drewno", 5000);
		Cargo c4 = new Cargo("Pszenica", 5000);
		Cargo c5 = new Cargo("Monitory", 800);
		Cargo c6 = new Cargo("Art. papiernicze", 300);
		try {
			cargoDao.create(c1);
			cargoDao.create(c2);
			cargoDao.create(c3);
			cargoDao.create(c4);
			cargoDao.create(c5);
			cargoDao.create(c6);
		} catch (PersistenceException e) {
			System.out.println("Blad Hibernate'a: " + e.getMessage());
		}
	}

	public static void addVehicles() {
		Truck tir1 = new Truck("LU12345", true, 1000, 195, 175);
		Airplane sam = new Airplane("Boeing 363", 10000, 685, 25);
		Truck tir2 = new Truck("WB99102", true, 1000, 100, 100);
		Truck tir3 = new Truck("NO1503C", true, 5000, 505, 195);
		Airplane sam2 = new Airplane("Airbus 150", 10000, 440, 500);
		Truck tir4 = new Truck("CT13021", false, 1000, 515, 195);
		try {
			vehicleDao.create(tir1);
			vehicleDao.create(sam);
			vehicleDao.create(tir2);
			vehicleDao.create(tir3);
			vehicleDao.create(sam2);
			vehicleDao.create(tir4);
		} catch (PersistenceException e) {
			System.out.println("Blad Hibernate'a: " + e.getMessage());
		}
	}

	public static void addDrivers() {
		Driver d1 = new Driver("Szymon", "M.", "94111112345", "C/00883/2013", null);
		Driver d2 = new Driver("Piotr", "Naspirski", "941026419", "Pil/021/2014", null);
		Driver d3 = new Driver("Marian", "Skórski", "9341693210", "C+E/89221/2011", null);
		Driver d4 = new Driver("Anna", "Rafalska", "74120201234", "C/01023/1991", null);
		Driver d5 = new Driver("Grzegorz", "Grubski", "78103054321", "Pil/029/2005", null);
		Driver d6 = new Driver("Cezary", "Bryngel", "52011909876", "C+E/15991/2000", null);

		try {
			driverDao.create(d1);
			driverDao.create(d2);
			driverDao.create(d3);
			driverDao.create(d4);
			driverDao.create(d5);
			driverDao.create(d6);
		} catch (PersistenceException e) {
			System.out.println("Blad Hibernate'a: " + e.getMessage());
		}
	}

	public static VehicleDao getVehicleDao() {
		return vehicleDao;
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
