import org.hibernate.HibernateException;

import pl.edu.wat.wcy.model.dao.EMStorage;
import pl.edu.wat.wcy.model.dao.VehicleDao;
import pl.edu.wat.wcy.model.entities.*;

public class Main {

	public static void main(String[] args) {
		VehicleDao pojazdDao = new VehicleDao();
		
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
		
		try {
			EMStorage.getEm().close();
			EMStorage.getEmf().close();
		} catch (HibernateException e1) {
			System.out.println("Blad Hibernate'a!");
		}
		catch(IllegalStateException e2)
		{
			System.out.println("Blad - menedzer zostal wczesniej zamkniety!");
		}
		
	}

}
