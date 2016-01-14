package pl.edu.wat.wcy.events;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import pl.edu.wat.wcy.main.Main;
import pl.edu.wat.wcy.model.entities.EventLog;
import pl.edu.wat.wcy.model.entities.Truck;

public class VehicleDamagedEvent {
	public VehicleDamagedEvent(Truck vehicleDamaged) {
		Thread thread = new Thread(){
			@Override
			public void run() {
				Image imageDisabled=null, imageAbled;
				imageAbled = vehicleDamaged.icon;
				try {
					imageDisabled = ImageIO.read(new File("./resources/truckDisabled.jpg"));
				} catch (IOException e) {
					Main.eventLog.warning("Nie zaladowano ikony!");
					Calendar now = GregorianCalendar.getInstance();
					Main.eventDao.create(new EventLog(now.getTime()+" WARN: " + Main.loggedUser.getName() + " - VehicleDamaged - nie za³adowano ikony. Zalogowany user: "+Main.loggedUser.getName()));
				}
				if(imageDisabled!=null)
				vehicleDamaged.label.setIcon(new ImageIcon(imageDisabled));
				Random rand = new Random();
				int delay = rand.nextInt(5000)+3000;
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e){}
				vehicleDamaged.setAbleToWork(true);
				vehicleDamaged.label.setIcon(new ImageIcon(imageAbled));
			}
		};
		thread.start();
	}
	
}
