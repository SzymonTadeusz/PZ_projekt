import org.hibernate.HibernateException;

import pl.edu.wat.wcy.model.dao.EMStorage;
import pl.edu.wat.wcy.model.dao.PojazdDao;
import pl.edu.wat.wcy.model.entities.*;

public class Main {

	public static void main(String[] args) {
		PojazdDao pojazdDao = new PojazdDao();
		
		Truck tir = new Truck();
		tir.setCapacity(1000);	
		Airplane sam = new Airplane();
		sam.setCapacity(5000);
		pojazdDao.create(tir);
		pojazdDao.create(sam);
		
		Vehicle obj2 = pojazdDao.retrieve(tir.getVehicleID());
		Vehicle obj = pojazdDao.retrieve(sam.getVehicleID());
		System.out.println(obj2.getCapacity());
		System.out.println(obj.getCapacity());
		
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
