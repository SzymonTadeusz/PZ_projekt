package pl.edu.wat.wcy.main;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.persistence.PersistenceException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.SQLGrammarException;

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
	public static RoleDao roleDao = new RoleDao();
	public static UserDao userDao = new UserDao();
	public static EventDao eventDao = new EventDao();

	public static User loggedUser;
	public static boolean isLoggedIn;
	public static Logger eventLog = Logger.getLogger("PZProjekt_log");

	
	public static void main(String[] args) {
		addUsers();
		logInForm();
		
		try {
			FileHandler fh;
			fh = new FileHandler("./logZdarzen.log");
			eventLog.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void addUsers() {
		Role admin = new Role("admin");
		Role user = new Role("user");
		roleDao.create(admin);
		roleDao.create(user);
		User u1 = new User("admin", "sql", admin);
		User u2 = new User("user", "sql", user);
		userDao.create(u2);
		userDao.create(u1);
	}

	public static void logInForm()
	{
		JPanel panel = new JPanel(new GridLayout(3,1));
		JTextField textfieldLogin = new JTextField("Login", 17);
		panel.add(textfieldLogin);
		JTextField textfieldPassword = new JPasswordField("Hasło", 17);
		panel.add(textfieldPassword);
		JFrame addWindow = new JFrame("Zaloguj");
		addWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JButton bt = new JButton("Zatwierdź");
		bt.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = textfieldLogin.getText();
				String password = textfieldPassword.getText();
				addWindow.dispose();
				if(logIn(username, password)==true)
				{
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

				CreateWindow.getWindowPanel().setVisible(true);
				}
				else 
				{
					Main.eventLog.info(" Zamykam aplikację.");
					Calendar now = GregorianCalendar.getInstance();
					Main.eventDao.create(new EventLog(now.getTime()+" INFO: LogInForm() - zamknięto aplikację"));
					System.exit(1);
				}
			}
		});

		panel.add(bt, BorderLayout.AFTER_LAST_LINE);
		addWindow.getContentPane().add(panel);
		addWindow.setLocation(500,300);
		addWindow.pack();
		addWindow.setVisible(true);
		
	}

	@SuppressWarnings("unchecked")
	public static boolean logIn(String username, String password) {
		User probablyUser = new User(username, password);
		Session session = EMStorage.getEm().unwrap(Session.class);
		Query q = session.createSQLQuery("SELECT u.userid FROM Users AS u WHERE u.name=\"" + probablyUser.getName()
				+ "\" AND u.password = \"" + probablyUser.getPassword() + "\";");
		List<Integer> result = null;
		int resultid = 0;
		try {
			result = (List<Integer>) q.list();
			resultid = result.get(0);
			probablyUser = Main.userDao.retrieve(resultid);
		} catch (SQLGrammarException e1) {
			Main.eventLog.severe("Błąd logowania. Podane dane to - login: \"" + username + "\", hasło: \"" + password+"\"");
			Calendar now = GregorianCalendar.getInstance();
			Main.eventDao.create(new EventLog(now.getTime()+" ERROR: Błąd logowania. Podane dane to - login: \"" + username + "\", hasło: \"" + password+"\""));
		} catch (IndexOutOfBoundsException e1) {
			Main.eventLog.severe("Błąd logowania. Podane dane to - login: \"" + username + "\", hasło: \"" + password+"\"");
			Calendar now = GregorianCalendar.getInstance();
			Main.eventDao.create(new EventLog(now.getTime()+" ERROR: Błąd logowania. Podane dane to - login: \"" + username + "\", hasło: \"" + password+"\""));
		}
		if (result != null && result.size() == 1) {
			Main.eventLog.info("Logowanie udane! Login: " + username + ", hasło: " + password + " (rola: "+probablyUser.getRole().getRoleName()+")");
			Calendar now = GregorianCalendar.getInstance();
			Main.eventDao.create(new EventLog(now.getTime()+" INFO: Logowanie udane! Login: " + username + ", hasło: " + password + " (rola: "+probablyUser.getRole().getRoleName()+")"));
			Main.loggedUser = probablyUser;
			return true;
		} else
			return false;

	}

	public static void addWarehouses() {
		Country c1 = new Country("Polska  ", "PL");
		Country c2 = new Country("Niemcy  ", "DE");
		Country c3 = new Country("Litwa   ", "LT");
		Country c4 = new Country("Czechy  ", "CZ");
		Country c5 = new Country("Słowacja", "SK");
		Country c6 = new Country("Ukraina ", "UA");
		Country c7 = new Country("Węgry   ", "HU");
		Country c8 = new Country("Rosja   ", "RU");
		Country c9 = new Country("Austria ", "AT");
		Country c10 = new Country("Inne    ", "--");

		Warehouse w = new Warehouse("Berlin, Weierstraße", 3, "Magazyn Berlin 1", 195, 165, c2);
		Warehouse w2 = new Warehouse("Warszawa, Marszałkowska", 102, "Magazyn Warszawa 1", 505, 185, c1);
		Warehouse w3 = new Warehouse("Wrocław, Pl. Katedralny", 1, "Magazyn Wrocław 1", 340, 260, c1);
		Warehouse w4 = new Warehouse("Praha, Karluv Pl.", 30, "Magazyn Praga 1", 235, 325, c4);
		Warehouse w5 = new Warehouse("Budapest, Szegetek", 1, "Magazyn Budapeszt 1", 430, 490, c7);
		Warehouse w6 = new Warehouse("Львов, Вернигори", 11, "Magazyn Lwów 1", 630, 345, c6);
		Warehouse w7 = new Warehouse("Vilnius, Šimašiusai", 25, "Magazyn Wilno 1", 685, 15, c3);
		Warehouse w8 = new Warehouse("Stuttgart, Kohlstraße", 25, "Magazyn Stuttgart 1", 20, 410, c2);
		try {
			countryDao.create(c1);
			countryDao.create(c2);
			countryDao.create(c3);
			countryDao.create(c4);
			countryDao.create(c5);
			countryDao.create(c6);
			countryDao.create(c7);
			countryDao.create(c8);
			countryDao.create(c9);
			countryDao.create(c10);
			warehouseDao.create(w);
			warehouseDao.create(w2);
			warehouseDao.create(w3);
			warehouseDao.create(w4);
			warehouseDao.create(w5);
			warehouseDao.create(w6);
			warehouseDao.create(w7);
			warehouseDao.create(w8);
		} catch (PersistenceException e) {
			Main.eventLog.severe("Blad Hibernate'a: " + e.getMessage());
			Calendar now = GregorianCalendar.getInstance();
			Main.eventDao.create(new EventLog(now.getTime()+" ERROR: tworzenie wstępnych magazynów."));			
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
			Main.eventLog.severe("Blad Hibernate'a: " + e.getMessage());
			Calendar now = GregorianCalendar.getInstance();
			Main.eventDao.create(new EventLog(now.getTime()+" ERROR: tworzenie wstępnych towarów."));
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
			Main.eventLog.severe("Blad Hibernate'a: " + e.getMessage());
			Calendar now = GregorianCalendar.getInstance();
			Main.eventDao.create(new EventLog(now.getTime()+" ERROR: tworzenie wstępnych pojazdów."));
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
			Main.eventLog.severe("Blad Hibernate'a: " + e.getMessage());
			Calendar now = GregorianCalendar.getInstance();
			Main.eventDao.create(new EventLog(now.getTime()+" ERROR: tworzenie wstępnych kierowców."));
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
