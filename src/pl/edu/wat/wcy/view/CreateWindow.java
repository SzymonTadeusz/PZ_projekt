package pl.edu.wat.wcy.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.*;
import org.hibernate.HibernateException;

import pl.edu.wat.wcy.main.Main;
import pl.edu.wat.wcy.model.dao.EMStorage;
import pl.edu.wat.wcy.model.entities.*;

public class CreateWindow {
	static JFrame appWindow = new JFrame("PZ Projekt - Szymon Muszyñski");
	static JPanel windowPanel = new JPanel(new BorderLayout());
	static JPanel mapPanel = new MapPanel();
	public static JPanel getMapPanel() {
		return mapPanel;
	}

	public static void setMapPanel(JPanel mapPanel) {
		CreateWindow.mapPanel = mapPanel;
	}

	static JPanel buttonPanel = new JPanel(new GridLayout(10, 2));

	public CreateWindow() {
		addButtons();
		initializeWindowParameters();
		appWindow.revalidate();
		appWindow.repaint();
	}

	public static void initializeWindowParameters() {
		appWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		appWindow.setSize(1120, 640);
		appWindow.setLocation(100, 50);
		appWindow.setResizable(false);
		buttonPanel.setSize(200, 600);
		mapPanel.setSize(800, 600);
		windowPanel.add(mapPanel, BorderLayout.CENTER);
		windowPanel.add(buttonPanel, BorderLayout.EAST);
		appWindow.getContentPane().add(windowPanel);
		windowPanel.setVisible(false);
		appWindow.setVisible(true);
		appWindow.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Main.eventLog.info("**********************************\n" + "Zamykam polaczenie oraz aplikacje.\n"
						+ "**********************************");
				Calendar now = GregorianCalendar.getInstance();
				Main.eventDao.create(new EventLog(now.getTime()+" INFO: Zamkniêcie po³¹czenia w normalnym trybie."));
				try {
					EMStorage.getEm().close();
					EMStorage.getEmf().close();
				} catch (HibernateException e1) {
					Main.eventLog.severe("Blad Hibernate'a!");
					now = GregorianCalendar.getInstance();
					Main.eventDao.create(new EventLog(now.getTime()+" ERROR: " + Main.loggedUser.getName() + " - Zamkniêcie po³¹czenia w normalnym trybie."));
					} catch (IllegalStateException e2) {
					Main.eventLog.severe("Blad - menedzer zostal wczesniej zamkniety!");
					now = GregorianCalendar.getInstance();
					Main.eventDao.create(new EventLog(now.getTime()+" ERROR: " + Main.loggedUser.getName() + " - Zamkniêcie po³¹czenia w normalnym trybie."));
				} finally {
					System.exit(0);
				}
			}
		});
	}

	public static JFrame getAppWindow() {
		return appWindow;
	}

	public static void setAppWindow(JFrame appWindow) {
		CreateWindow.appWindow = appWindow;
	}

	public static JPanel getWindowPanel() {
		return windowPanel;
	}

	public static void setWindowPanel(JPanel windowPanel) {
		CreateWindow.windowPanel = windowPanel;
	}

	public static void addButtons() {
		if(Main.loggedUser.getRole().getRoleName()=="admin")
		{
		addDriverButton();
		removeDriverButton();
		addVehicleButton();
		removeVehicleButton();
		}
		addCargoToVehicleButton();
		addDriverToVehicleButton();
		if(Main.loggedUser.getRole().getRoleName()=="admin")
		{
		addWarehouseButton();
		removeWarehouseButton();
		addCargoButton();
		removeCargoButton();
		}
		addTransportButton();
		addTransportToVehicleButton();
	}

	public static void addDriverButton() {
		JButton addNewDriver = new JButton("+ Kierowca");
		addNewDriver.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel panel = new JPanel(new GridLayout());
				JTextField textFieldName = new JTextField("Imie", 10);
				panel.add(textFieldName);
				JTextField textFieldSurname = new JTextField("Nazwisko", 15);
				panel.add(textFieldSurname);
				JTextField textFieldID = new JTextField("PESEL", 11);
				panel.add(textFieldID);
				JTextField textFieldDrvLic = new JTextField("Nr Prawa Jazdy", 15);
				panel.add(textFieldDrvLic);
				JFrame addWindow = new JFrame("Dodaj kierowcê");
				JButton bt = new JButton("ZatwierdŸ");
				bt.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Driver d = new Driver(textFieldName.getText(), textFieldSurname.getText(),
								textFieldID.getText(), textFieldDrvLic.getText());
						Main.getDriverDao().create(d);
						Main.eventLog.info("Dodano kierowcê: " + d);
						Calendar now = GregorianCalendar.getInstance();
						Main.eventDao.create(new EventLog(now.getTime()+" INFO: " + Main.loggedUser.getName() + " - Dodanie kierowcy " + d));
						addWindow.dispose();
					}
				});

				panel.add(bt, BorderLayout.AFTER_LAST_LINE);
				addWindow.getContentPane().add(panel);
				addWindow.pack();
				addWindow.setVisible(true);
			}
		});
		buttonPanel.add(addNewDriver);
	}

	public static void removeDriverButton() {
		JButton removeDriver = new JButton("- Kierowca");
		removeDriver.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel panel = new JPanel(new BorderLayout());
				JComboBox<Driver> driverBox = new JComboBox<Driver>((Driver[]) Main.getDriverDao().getList()
						.toArray(new Driver[Main.getDriverDao().getList().size()]));
				panel.add(driverBox);

				JFrame addWindow = new JFrame("Usuñ kierowcê");
				JButton bt = new JButton("ZatwierdŸ");
				bt.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Driver d = ((Driver) driverBox.getSelectedItem());
						if (d.getDriverOf() != null)
							d.getDriverOf().setCurrentDriver(null);
						d.setDriverOf(null); // najpierw usun powiazanie
						Main.getDriverDao().update(d);
						Main.getDriverDao().delete(((Driver) driverBox.getSelectedItem()).getDriverID());
						Main.eventLog.info("Usuniêto kierowcê " + d + "  z bazy.");
						Calendar now = GregorianCalendar.getInstance();
						Main.eventDao.create(new EventLog(now.getTime()+" INFO: " + Main.loggedUser.getName() + " - Usuwanie kierowcy " + d));
						addWindow.dispose();
					}
				});
				panel.add(bt, BorderLayout.EAST);
				addWindow.getContentPane().add(panel);
				addWindow.pack();
				addWindow.setVisible(true);
			}
		});
		buttonPanel.add(removeDriver);
	}

	public static void addVehicleButton() {
		JButton addNewTruck = new JButton("+ Pojazd");
		addNewTruck.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel panel = new JPanel(new GridLayout());

				JCheckBox checkBoxType = new JCheckBox("Ciezarowy?", true);
				panel.add(checkBoxType);
				JTextField textFieldCapacity = new JTextField("Pojemnoœæ", 10);
				panel.add(textFieldCapacity);
				JTextField textFieldRegNr = new JTextField("Nr rejestracyjny", 15);
				panel.add(textFieldRegNr);
				JTextField textFieldX = new JTextField("x", 10);
				panel.add(textFieldX);
				JTextField textFieldY = new JTextField("y", 10);
				panel.add(textFieldY);
				JCheckBox checkBoxAbleToWork = new JCheckBox("Sprawny?", true);
				panel.add(checkBoxAbleToWork);
				checkBoxType.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (checkBoxType.isSelected()) {
							textFieldRegNr.setText("Nr rejestracyjny");
							checkBoxAbleToWork.setVisible(true);
						} else {
							textFieldRegNr.setText("Nazwa samolotu");
							checkBoxAbleToWork.setVisible(false);
						}
					}
				});
				JFrame addWindow = new JFrame("Dodaj pojazd");
				JButton bt = new JButton("ZatwierdŸ");
				bt.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						int cap, x, y;
						try {
							cap = Integer.parseInt(textFieldCapacity.getText());
							x = Integer.parseInt(textFieldX.getText());
							y = Integer.parseInt(textFieldY.getText());
						} catch (NumberFormatException exc) {
							cap = 10000;
							x = 200;
							y = 200;
						}
						Vehicle d;
						if (checkBoxType.isSelected())
							d = new Truck(textFieldRegNr.getText(), checkBoxAbleToWork.isSelected(), cap, x, y);
						else
							d = new Airplane(textFieldRegNr.getText(), cap, x, y);
						Main.getVehicleDao().create(d);
						Main.eventLog.info("Dodano pojazd: " + d);
						Calendar now = GregorianCalendar.getInstance();
						Main.eventDao.create(new EventLog(now.getTime()+" INFO: " + Main.loggedUser.getName() + " - Dodanie nowego pojazdu " + d));
						addWindow.dispose();

					}
				});
				panel.add(bt, BorderLayout.AFTER_LAST_LINE);
				addWindow.getContentPane().add(panel);
				addWindow.pack();
				addWindow.setVisible(true);
			}
		});
		buttonPanel.add(addNewTruck);
	}

	public static void removeVehicleButton() {
		JButton removeVehicle = new JButton("- Pojazd");
		removeVehicle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel panel = new JPanel(new BorderLayout());
				JComboBox<Vehicle> vehicleBox = new JComboBox<Vehicle>((Vehicle[]) Main.getVehicleDao().getList()
						.toArray(new Vehicle[Main.getVehicleDao().getList().size()]));
				panel.add(vehicleBox);

				JFrame addWindow = new JFrame("Usuñ pojazd");
				JButton bt = new JButton("ZatwierdŸ");
				bt.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Vehicle v = ((Vehicle) vehicleBox.getSelectedItem());
						if (v.getCurrentDriver() != null)
							v.getCurrentDriver().setDriverOf(null);
						v.setCurrentDriver(null); // najpierw usun powiazanie
						Main.getVehicleDao().update(v);
						Main.getVehicleDao().delete(((Vehicle) vehicleBox.getSelectedItem()).getVehicleID());
						Main.eventLog.info("Usuniêto pojazd " + v +" z bazy.");
						Calendar now = GregorianCalendar.getInstance();
						Main.eventDao.create(new EventLog(now.getTime()+" INFO: " + Main.loggedUser.getName() + " - Usuwanie pojazdu "+v));
						addWindow.dispose();
					}
				});
				panel.add(bt, BorderLayout.EAST);
				addWindow.getContentPane().add(panel);
				addWindow.pack();
				addWindow.setVisible(true);
			}
		});
		buttonPanel.add(removeVehicle);
	}

	public static void addDriverToVehicleButton() {
		JButton addNewCargo = new JButton("PRZYDZIEL KIEROWCÊ");
		addNewCargo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel panel = new JPanel(new BorderLayout());
				JComboBox<Driver> driverBox = new JComboBox<Driver>((Driver[]) Main.getDriverDao().getList()
						.toArray(new Driver[Main.getDriverDao().getList().size()]));
				JComboBox<Vehicle> vehicleBox = new JComboBox<Vehicle>((Vehicle[]) Main.getVehicleDao().getList()
						.toArray(new Vehicle[Main.getVehicleDao().getList().size()]));
				panel.add(driverBox, BorderLayout.WEST);
				panel.add(vehicleBox, BorderLayout.CENTER);
				JFrame addWindow = new JFrame("Przydziel kierowcê");
				JButton bt = new JButton("ZatwierdŸ");
				bt.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Driver d = (Driver) driverBox.getSelectedItem();
						Vehicle v = (Vehicle) vehicleBox.getSelectedItem();
						d.setDriverOf(v);
						v.setCurrentDriver(d);
						Main.getDriverDao().update(d);
						Main.getVehicleDao().update(v);
						Main.eventLog.info("Zmodyfikowano:\n" + v);
						Calendar now = GregorianCalendar.getInstance();
						Main.eventDao.create(new EventLog(now.getTime()+" INFO: " + Main.loggedUser.getName() + " - Dodanie kierowcy do pojazdu: " + v));
						addWindow.dispose();
					}
				});

				panel.add(bt, BorderLayout.EAST);
				addWindow.getContentPane().add(panel);
				addWindow.pack();
				addWindow.setVisible(true);
			}
		});
		buttonPanel.add(addNewCargo);
	}

	public static void addCargoButton() {
		JButton addNewCargo = new JButton("+ £adunek");
		addNewCargo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel panel = new JPanel(new GridLayout());
				JTextField textFieldWeight = new JTextField("Waga", 10);
				panel.add(textFieldWeight);
				JTextField textFieldCargoName = new JTextField("Nazwa ³adunku", 15);
				panel.add(textFieldCargoName);
				JFrame addWindow = new JFrame("Dodaj ³adunek");
				JButton bt = new JButton("ZatwierdŸ");
				bt.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						int weight;
						try {
							weight = Integer.parseInt(textFieldWeight.getText());
						} catch (NumberFormatException exc) {
							weight = 200;
						}
						Cargo c = new Cargo(textFieldCargoName.getText(), weight);
						Main.getCargoDao().create(c);
						Main.eventLog.info("Dodano: " + c);
						Calendar now = GregorianCalendar.getInstance();
						Main.eventDao.create(new EventLog(now.getTime()+" INFO: " + Main.loggedUser.getName() + " - Dodanie ³adunku: " + c));
						addWindow.dispose();
					}
				});
				panel.add(bt, BorderLayout.AFTER_LAST_LINE);
				addWindow.getContentPane().add(panel);
				addWindow.pack();
				addWindow.setVisible(true);
			}
		});
		buttonPanel.add(addNewCargo);
	}

	public static void removeCargoButton() {
		JButton removeCargo = new JButton("- £adunek");
		removeCargo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel panel = new JPanel(new BorderLayout());
				JComboBox<Cargo> cargoBox = new JComboBox<Cargo>(
						(Cargo[]) Main.getCargoDao().getList().toArray(new Cargo[Main.getCargoDao().getList().size()]));
				panel.add(cargoBox);

				JFrame addWindow = new JFrame("Usuñ ³adunek");
				JButton bt = new JButton("ZatwierdŸ");
				bt.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Cargo c = ((Cargo) cargoBox.getSelectedItem());
						if (c.getVehicle() != null)
							for (Vehicle v : c.getVehicle())
								v.removeFromCargo(c);
						c.setVehicle(null); // najpierw usun powiazanie
						Main.getCargoDao().update(c);
						Main.getCargoDao().delete(((Cargo) cargoBox.getSelectedItem()).getCargoID());
						Main.eventLog.info("Usuniêto ³adunek " + c +" z bazy.");
						Calendar now = GregorianCalendar.getInstance();
						Main.eventDao.create(new EventLog(now.getTime()+" INFO: " + Main.loggedUser.getName() + " - Usuwanie ³adunku: " + c));
						addWindow.dispose();
					}
				});
				panel.add(bt, BorderLayout.EAST);
				addWindow.getContentPane().add(panel);
				addWindow.pack();
				addWindow.setVisible(true);
			}
		});
		buttonPanel.add(removeCargo);
	}

	public static void addWarehouseButton() {
		JButton addNewWarehouse = new JButton("+ Magazyn");
		addNewWarehouse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel panel = new JPanel(new GridLayout());

				JTextField textFieldWarehouseName = new JTextField("Nazwa magazynu", 15);
				panel.add(textFieldWarehouseName);
				JTextField textFieldStreet = new JTextField("Ulica", 15);
				panel.add(textFieldStreet);
				JTextField textFieldStreetNo = new JTextField("Nr", 10);
				panel.add(textFieldStreetNo);
				JComboBox<Country> countryBox = new JComboBox<Country>((Country[]) Main.getCountryDao().getList()
						.toArray(new Country[Main.getCountryDao().getList().size()]));
				panel.add(countryBox);
				JTextField textFieldX = new JTextField("x", 15);
				panel.add(textFieldX);
				JTextField textFieldY = new JTextField("y", 15);
				panel.add(textFieldY);

				JFrame addWindow = new JFrame("Dodaj magazyn");
				JButton bt = new JButton("ZatwierdŸ");
				bt.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						int numer, x, y;
						try {
							numer = Integer.parseInt(textFieldStreetNo.getText());
						} catch (NumberFormatException exc) {
							numer = 0;
						}
						try {
							x = Integer.parseInt(textFieldX.getText());
							y = Integer.parseInt(textFieldY.getText());
						} catch (NumberFormatException exc) {
							x = 0;
							y = 0;
						}
						Country c = ((Country) countryBox.getSelectedItem());
						Warehouse d = new Warehouse(textFieldStreet.getText(), numer, textFieldWarehouseName.getText(),
								x, y, c);
						Main.getWarehouseDao().create(d);
						Main.eventLog.info("Dodano: " + d);
						Calendar now = GregorianCalendar.getInstance();
						Main.eventDao.create(new EventLog(now.getTime()+" INFO: " + Main.loggedUser.getName() + " - Dodanie magazynu: " + d));
						addWindow.dispose();
					}
				});

				panel.add(bt, BorderLayout.AFTER_LAST_LINE);
				addWindow.getContentPane().add(panel);
				addWindow.pack();
				addWindow.setVisible(true);
			}
		});
		buttonPanel.add(addNewWarehouse);
	}

	public static void removeWarehouseButton() {
		JButton removeWarehouse = new JButton("- Magazyn");
		removeWarehouse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel panel = new JPanel(new BorderLayout());
				JComboBox<Warehouse> warehouseBox = new JComboBox<Warehouse>((Warehouse[]) Main.getWarehouseDao()
						.getList().toArray(new Warehouse[Main.getWarehouseDao().getList().size()]));
				panel.add(warehouseBox);

				JFrame addWindow = new JFrame("Usuñ magazyn");
				JButton bt = new JButton("ZatwierdŸ");
				bt.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Warehouse w = ((Warehouse) warehouseBox.getSelectedItem());
						if (w.getAwaitingTransport() != null)
							for (Transport t : w.getAwaitingTransport())
								t.setDestination(null);
						w.setAwaitingTransport(null); // najpierw usun
														// powiazanie
						Main.getWarehouseDao().update(w);
						Main.getWarehouseDao().delete(((Warehouse) warehouseBox.getSelectedItem()).getWarehouseID());
						Main.eventLog.info("Usuniêto magazyn " + w +" z bazy.");
						Calendar now = GregorianCalendar.getInstance();
						Main.eventDao.create(new EventLog(now.getTime()+" INFO: " + Main.loggedUser.getName() + " - Usuniêcie magazynu: " + w));
						addWindow.dispose();
					}
				});
				panel.add(bt, BorderLayout.EAST);
				addWindow.getContentPane().add(panel);
				addWindow.pack();
				addWindow.setVisible(true);
			}
		});
		buttonPanel.add(removeWarehouse);
	}

	public static void addCargoToVehicleButton() {
		JButton addNewCargo = new JButton("ZA£ADUJ POJAZD");
		addNewCargo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel panel = new JPanel(new BorderLayout());
				JComboBox<Cargo> cargoBox = new JComboBox<Cargo>(
						(Cargo[]) Main.getCargoDao().getList().toArray(new Cargo[Main.getCargoDao().getList().size()]));
				JComboBox<Vehicle> vehicleBox = new JComboBox<Vehicle>((Vehicle[]) Main.getVehicleDao().getList()
						.toArray(new Vehicle[Main.getVehicleDao().getList().size()]));
				panel.add(cargoBox, BorderLayout.WEST);
				panel.add(vehicleBox, BorderLayout.CENTER);
				JFrame addWindow = new JFrame("Dodaj ³adunek");
				JButton bt = new JButton("ZatwierdŸ");
				bt.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Cargo c = (Cargo) cargoBox.getSelectedItem();
						Vehicle v = (Vehicle) vehicleBox.getSelectedItem();
						c.addToVehicles(v);
						v.addToCargo(c);
						Main.getCargoDao().update(c);
						Main.getVehicleDao().update(v);
						int totalWeight=0;
						for(Cargo ca : v.getCurrentCargo()) totalWeight += ca.getUnitWeight();
						Calendar now = GregorianCalendar.getInstance();
						Main.eventLog.info("Zmodyfikowano:\n" + v);
						if(totalWeight > v.getCapacity())
						{
							Main.eventLog.warning("Pojazd przeci¹¿ony!");
							Main.eventDao.create(new EventLog(now.getTime()+" INFO: " + Main.loggedUser.getName() + " - Dodanie ³adunku do pojazdu: " + v + "; POJAZD PRZECI¥¯ONY!"));
						}
						else
							Main.eventDao.create(new EventLog(now.getTime()+" WARN: " + Main.loggedUser.getName() + " - Przeci¹¿enie pojazdu w wyniku dodania ³adunku: " + v));
						addWindow.dispose();
					}
				});

				panel.add(bt, BorderLayout.EAST);
				addWindow.getContentPane().add(panel);
				addWindow.pack();
				addWindow.setVisible(true);
			}
		});
		buttonPanel.add(addNewCargo);
	}

	public static void addTransportButton() {
		JButton addNewTransport = new JButton("+ Transport");
		addNewTransport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel panel = new JPanel(new GridLayout());
				JComboBox<Warehouse> warehouseBox = new JComboBox<Warehouse>((Warehouse[]) Main.getWarehouseDao()
						.getList().toArray(new Warehouse[Main.getWarehouseDao().getList().size()]));
				panel.add(warehouseBox);
				JFrame addWindow = new JFrame("Dodaj zlecenie transportu");
				JButton bt = new JButton("ZatwierdŸ");
				bt.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Warehouse w = (Warehouse) warehouseBox.getSelectedItem();
						Transport t = new Transport(w);
						Main.getTransportDao().create(t);
						Main.eventLog.info("Dodano zlecenie:" + t);
						Calendar now = GregorianCalendar.getInstance();
						Main.eventDao.create(new EventLog(now.getTime()+" INFO: " + Main.loggedUser.getName() + " - Dodanie zlecenia: " + t));
						addWindow.dispose();
					}
				});

				panel.add(bt, BorderLayout.AFTER_LAST_LINE);
				addWindow.getContentPane().add(panel);
				addWindow.pack();
				addWindow.setVisible(true);
			}
		});
		buttonPanel.add(addNewTransport);
	}

	public static void addTransportToVehicleButton() {
		JButton addNewTransport = new JButton("WYŒLIJ TRANSPORT");
		addNewTransport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel panel = new JPanel(new BorderLayout());
				JComboBox<Transport> transportBox = new JComboBox<Transport>((Transport[]) Main.getTransportDao()
						.getList().toArray(new Transport[Main.getTransportDao().getList().size()]));
				JComboBox<Vehicle> vehicleBox = new JComboBox<Vehicle>((Vehicle[]) Main.getVehicleDao().getList()
						.toArray(new Vehicle[Main.getVehicleDao().getList().size()]));
				panel.add(transportBox, BorderLayout.WEST);
				panel.add(vehicleBox, BorderLayout.CENTER);
				JFrame addWindow = new JFrame("Przydziel zlecenie");
				JButton bt = new JButton("ZatwierdŸ");
				bt.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Transport t = (Transport) transportBox.getSelectedItem();
						Vehicle v = (Vehicle) vehicleBox.getSelectedItem();
						v.setTransport(t);
						v.setArrivalListener(t.getDestination());
						t.getDestination().addToTransports(t);
						Main.transportDao.current.addToTransports(t);
						Main.getTransportDao().update(t);
						Main.getVehicleDao().update(v);
						Main.eventLog.info("Dodano:\n" + v);
						Calendar now = GregorianCalendar.getInstance();
						Main.eventDao.create(new EventLog(now.getTime()+" INFO: " + Main.loggedUser.getName() + " - Zlecenie transportu: " + t + "; " + v));
						
						addWindow.dispose();
					}
				});

				panel.add(bt, BorderLayout.EAST);
				addWindow.getContentPane().add(panel);
				addWindow.pack();
				addWindow.setVisible(true);
			}
		});
		buttonPanel.add(addNewTransport);
	}

}
