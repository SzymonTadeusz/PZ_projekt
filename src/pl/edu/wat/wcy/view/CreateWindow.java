package pl.edu.wat.wcy.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import java.util.GregorianCalendar;
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalTheme;
import javax.swing.plaf.metal.OceanTheme;

import org.hibernate.HibernateException;

import pl.edu.wat.wcy.main.Main;
import pl.edu.wat.wcy.model.dao.EMStorage;
import pl.edu.wat.wcy.model.entities.*;

public class CreateWindow {
	static JFrame appWindow = new JFrame("PZ Projekt - Szymon Muszyñski");
	static JPanel windowPanel = new JPanel(new BorderLayout());
	static JPanel mapPanel = new MapPanel();

	static DefaultListModel<String> listofDoneModel = new DefaultListModel<String>();
	static JList<String> listOfDone = new JList<String>(listofDoneModel);

	public static JPanel getMapPanel() {
		return mapPanel;
	}

	static JPanel buttonPanel = new JPanel(new GridLayout(10, 2));
	static JPanel buttonWholePanel = new JPanel(new BorderLayout());


	public CreateWindow() {
		addButtons();
		initializeWindowParameters();
		appWindow.revalidate();
		appWindow.repaint();
	}

	public static void initializeWindowParameters() {
		appWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		} catch (InstantiationException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		} catch (IllegalAccessException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		} catch (UnsupportedLookAndFeelException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		appWindow.setSize(1280, 640);
		appWindow.setLocation(100, 50);
		appWindow.setResizable(false);
		buttonPanel.setSize(200, 500);
		mapPanel.setSize(800, 600);
		windowPanel.add(mapPanel, BorderLayout.CENTER);
		buttonWholePanel.add(buttonPanel, BorderLayout.NORTH);
		buttonWholePanel.add(listOfDone);
		buttonWholePanel.setVisible(true);
		buttonWholePanel.setSize(200, 600);
		appWindow.getContentPane().add(windowPanel);//,BorderLayout.WEST);
		appWindow.getContentPane().add(buttonWholePanel,BorderLayout.EAST);
		windowPanel.setVisible(false);
		appWindow.setVisible(true);
		appWindow.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Main.eventLog.info("**********************************\n" + "Zamykam polaczenie oraz aplikacje.\n"
						+ "**********************************");
				Calendar now = GregorianCalendar.getInstance();
				Main.eventDao.create(new EventLog(now.getTime() + " INFO: Zamkniêcie po³¹czenia w normalnym trybie."));
				try {
					EMStorage.getEm().close();
					EMStorage.getEmf().close();
				} catch (HibernateException e1) {
					Main.eventLog.severe("Blad Hibernate'a!");
					now = GregorianCalendar.getInstance();
					Main.eventDao.create(new EventLog(now.getTime() + " ERROR: " + Main.loggedUser.getName()
							+ " - Zamkniêcie po³¹czenia w normalnym trybie."));
				} catch (IllegalStateException e2) {
					Main.eventLog.severe("Blad - menedzer zostal wczesniej zamkniety!");
					now = GregorianCalendar.getInstance();
					Main.eventDao.create(new EventLog(now.getTime() + " ERROR: " + Main.loggedUser.getName()
							+ " - Zamkniêcie po³¹czenia w normalnym trybie."));
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
		listOfDone.setSize(300, 200);
		listOfDone.setAutoscrolls(true);
		listOfDone.setVisible(true);
		
		switch (Main.loggedUser.getRole().getRoleName()) {
		case ("admin"): {
			addDriverButton();
			removeDriverButton();
			addVehicleButton();
			removeVehicleButton();
			addDriverToVehicleButton();
			removeDriverFromVehicleButton();
			addWarehouseButton();
			removeWarehouseButton();
			addCargoButton();
			removeCargoButton();
			addTransportButton();
			addTransportToVehicleButton();
			addUser();
			addCargoToVehicleButton();
		//	windowPanel.add(listOfDone,BorderLayout.SOUTH);
			windowPanel.revalidate();
			windowPanel.repaint();
			break;
		}
		case ("user"): {
			addDriverButton();
			addVehicleButton();
			addDriverToVehicleButton();
			removeDriverFromVehicleButton();
			addWarehouseButton();
			addCargoButton();
			addCargoToVehicleButton();
			addTransportToVehicleButton();
			addTransportButton();
			selectAllButton();
			showTransportHistoryButton();
			windowPanel.revalidate();
			windowPanel.repaint();
			break;
		}
		}
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
						listofDoneModel.add(0,"Dodano kierowcê: " + d);
						Calendar now = GregorianCalendar.getInstance();
						Main.eventDao.create(new EventLog(
								now.getTime() + " INFO: " + Main.loggedUser.getName() + " - Dodanie kierowcy " + d));
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
						listofDoneModel.add(0,"Usuniêto kierowcê " + d + "  z bazy.");
						Calendar now = GregorianCalendar.getInstance();
						Main.eventDao.create(new EventLog(
								now.getTime() + " INFO: " + Main.loggedUser.getName() + " - Usuwanie kierowcy " + d));
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
							x = Integer.parseInt(textFieldX.getText());
							y = Integer.parseInt(textFieldY.getText());
						} catch (NumberFormatException exc) {
							x = 200;
							y = 200;
						}
						try {
							cap = Integer.parseInt(textFieldCapacity.getText());
							} catch (NumberFormatException exc) {
							cap = 10000;
						}
						Vehicle d;
						if (checkBoxType.isSelected())
							d = new Truck(textFieldRegNr.getText(), checkBoxAbleToWork.isSelected(), cap, x, y);
						else
							d = new Airplane(textFieldRegNr.getText(), cap, x, y);
						Main.getVehicleDao().create(d);
						Main.eventLog.info("Dodano pojazd: " + d);
						listofDoneModel.add(0,"Dodano pojazd: " + d);

						Calendar now = GregorianCalendar.getInstance();
						Main.eventDao.create(new EventLog(now.getTime() + " INFO: " + Main.loggedUser.getName()
								+ " - Dodanie nowego pojazdu " + d));
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
						Main.getVehicleDao().getList().remove(v);
						Main.eventLog.info("Usuniêto pojazd " + v + " z bazy.");
						listofDoneModel.add(0,"Usuniêto pojazd " + v + " z bazy.");

						v.label.setVisible(false);
						Calendar now = GregorianCalendar.getInstance();
						Main.eventDao.create(new EventLog(
								now.getTime() + " INFO: " + Main.loggedUser.getName() + " - Usuwanie pojazdu " + v));
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
		JButton addDriverToVehicle = new JButton("PRZYDZIEL KIEROWCÊ");
		addDriverToVehicle.addActionListener(new ActionListener() {
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
						if(d.getDriverOf()!=null)d.getDriverOf().setCurrentDriver(null);
						d.setDriverOf(v);
						v.setCurrentDriver(d);
						Main.getDriverDao().update(d);
						Main.getVehicleDao().update(v);
						Main.eventLog.info("Zmodyfikowano:\n" + v);
						listofDoneModel.add(0,"Zmodyfikowano:\n" + v);

						Calendar now = GregorianCalendar.getInstance();
						Main.eventDao.create(new EventLog(now.getTime() + " INFO: " + Main.loggedUser.getName()
								+ " - Dodanie kierowcy do pojazdu: " + v));
						addWindow.dispose();
					}
				});

				panel.add(bt, BorderLayout.EAST);
				addWindow.getContentPane().add(panel);
				addWindow.pack();
				addWindow.setVisible(true);
			}
		});
		buttonPanel.add(addDriverToVehicle);
	}

	public static void removeDriverFromVehicleButton() {
		JButton addNewCargo = new JButton("OD£¥CZ KIEROWCÊ");
		addNewCargo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel panel = new JPanel(new BorderLayout());
				JComboBox<Vehicle> vehicleBox = new JComboBox<Vehicle>((Vehicle[]) Main.getVehicleDao().getList()
						.toArray(new Vehicle[Main.getVehicleDao().getList().size()]));
				panel.add(vehicleBox, BorderLayout.CENTER);
				JFrame addWindow = new JFrame("Od³¹cz kierowcê od samochodu");
				JButton bt = new JButton("ZatwierdŸ");
				bt.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Vehicle v = (Vehicle) vehicleBox.getSelectedItem();
						Main.getDriverDao().update(v.getCurrentDriver());
						if(v.getCurrentDriver()!=null){
						v.getCurrentDriver().setDriverOf(null);
						v.setCurrentDriver(null);
						}
						Main.getVehicleDao().update(v);
						Main.eventLog.info("Zmodyfikowano:\n" + v);
						listofDoneModel.add(0,"Zmodyfikowano:\n" + v);

						Calendar now = GregorianCalendar.getInstance();
						Main.eventDao.create(new EventLog(now.getTime() + " INFO: " + Main.loggedUser.getName()
								+ " - Dodanie kierowcy do pojazdu: " + v));
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
						Main.eventDao.create(new EventLog(
								now.getTime() + " INFO: " + Main.loggedUser.getName() + " - Dodanie ³adunku: " + c));
						listofDoneModel.add(0,"Dodano: " + c);

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
						Main.eventLog.info("Usuniêto ³adunek " + c + " z bazy.");
						listofDoneModel.add(0,"Usuniêto ³adunek " + c + " z bazy.");

						Calendar now = GregorianCalendar.getInstance();
						Main.eventDao.create(new EventLog(
								now.getTime() + " INFO: " + Main.loggedUser.getName() + " - Usuwanie ³adunku: " + c));
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
						listofDoneModel.add(0,"Dodano: " + d);

						Calendar now = GregorianCalendar.getInstance();
						Main.eventDao.create(new EventLog(
								now.getTime() + " INFO: " + Main.loggedUser.getName() + " - Dodanie magazynu: " + d));
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
						Main.eventLog.info("Usuniêto magazyn " + w + " z bazy.");
						listofDoneModel.add(0,"Usuniêto magazyn " + w + " z bazy.");

						Calendar now = GregorianCalendar.getInstance();
						Main.eventDao.create(new EventLog(
								now.getTime() + " INFO: " + Main.loggedUser.getName() + " - Usuniêcie magazynu: " + w));
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
						int totalWeight = 0;
						for (Cargo ca : v.getCurrentCargo())
							totalWeight += ca.getUnitWeight();
						Calendar now = GregorianCalendar.getInstance();
						Main.eventLog.info("Zmodyfikowano:\n" + v);
						listofDoneModel.add(0,"Zmodyfikowano:\n" + v);

						if (totalWeight > v.getCapacity()) {
							Main.eventLog.warning("Pojazd przeci¹¿ony!");
							Main.eventDao.create(new EventLog(now.getTime() + " INFO: " + Main.loggedUser.getName()
									+ " - Dodanie ³adunku do pojazdu: " + v + "; POJAZD PRZECI¥¯ONY!"));
						} else
							Main.eventDao.create(new EventLog(now.getTime() + " WARN: " + Main.loggedUser.getName()
									+ " - Przeci¹¿enie pojazdu w wyniku dodania ³adunku: " + v));
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
						listofDoneModel.add(0,"Dodano zlecenie:" + t);

						Calendar now = GregorianCalendar.getInstance();
						Main.eventDao.create(new EventLog(
								now.getTime() + " INFO: " + Main.loggedUser.getName() + " - Dodanie zlecenia: " + t));
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
						t.setExecutor(v);
						v.setArrivalListener(t.getDestination());
						t.getDestination().addToTransports(t);
						Main.transportDao.current.add(t);
						Main.getTransportDao().getList().remove(t);
						Main.getTransportDao().update(t);
						Main.getVehicleDao().update(v);
						Main.eventLog.info("Dodano:\n" + v);
						listofDoneModel.add(0,"Dodano:\n" + v);

						Calendar now = GregorianCalendar.getInstance();
						Main.eventDao.create(new EventLog(now.getTime() + " INFO: " + Main.loggedUser.getName()
								+ " - Zlecenie transportu: " + t + "; " + v));

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

	public static void addUser() {
		JButton addNewUser = new JButton("+ U¿ytkownik");
		addNewUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel panel = new JPanel(new GridLayout());
				JComboBox<Role> roleBox = new JComboBox<Role>(
						(Role[]) Main.roleDao.getList().toArray(new Role[Main.roleDao.getList().size()]));
				panel.add(roleBox);
				JTextField textFieldUsername = new JTextField("Login", 15);
				panel.add(textFieldUsername);
				JTextField textFieldPassword = new JTextField("Has³o", 15);
				panel.add(textFieldPassword);
				JFrame addWindow = new JFrame("Stwórz u¿ytkownika");
				JButton bt = new JButton("ZatwierdŸ");
				bt.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Role r = (Role) roleBox.getSelectedItem();
						User u = new User(textFieldUsername.getText(), textFieldPassword.getText(), r);
						Main.userDao.create(u);
						Main.eventLog.info("Dodano: " + u);
						listofDoneModel.add(0,"Dodano: " + u);

						Calendar now = GregorianCalendar.getInstance();
						Main.eventDao.create(new EventLog(
								now.getTime() + " INFO: " + Main.loggedUser.getName() + " - dodano u¿ytkownika: " + u));

						addWindow.dispose();
					}
				});

				panel.add(bt, BorderLayout.EAST);
				addWindow.getContentPane().add(panel);
				addWindow.pack();
				addWindow.setVisible(true);
			}
		});
		buttonPanel.add(addNewUser);
	}

	public static void removeUserButton() {
		JButton removeUser = new JButton("- U¿ytkownik");
		removeUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel panel = new JPanel(new BorderLayout());
				JComboBox<User> UserBox = new JComboBox<User>(
						(User[]) Main.userDao.getList().toArray(new User[Main.userDao.getList().size()]));
				panel.add(UserBox);

				JFrame addWindow = new JFrame("Usuñ u¿ytkownika");
				JButton bt = new JButton("ZatwierdŸ");
				bt.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						User u = ((User) UserBox.getSelectedItem());
						u.setRole(null);
						Main.userDao.delete(u);
						Main.eventLog.info("Usuniêto u¿ytkownika " + u + " z bazy.");
						listofDoneModel.add(0,"Usuniêto u¿ytkownika " + u + " z bazy.");

						Calendar now = GregorianCalendar.getInstance();
						Main.eventDao.create(new EventLog(now.getTime() + " INFO: " + Main.loggedUser.getName()
								+ " - Usuniêcie u¿ytkownika: " + u));
						addWindow.dispose();
					}
				});
				panel.add(bt, BorderLayout.EAST);
				addWindow.getContentPane().add(panel);
				addWindow.pack();
				addWindow.setVisible(true);
			}
		});
		buttonPanel.add(removeUser);
	}
	
	public static void showTransportHistoryButton() {
		JButton removeUser = new JButton("Historia transportu");
		removeUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel panel = new JPanel(new BorderLayout());
				DefaultListModel<String> listModel = new DefaultListModel<String>();
				JList<String> listBox= new JList<String>(listModel);
				panel.add(listBox);
				for(Transport t : Main.getTransportDao().history)listModel.add(0,t.toString());
				
				JFrame addWindow = new JFrame("Przegl¹d wykonanych transportów");
			
				addWindow.getContentPane().add(panel);
				addWindow.pack();
				addWindow.setVisible(true);
			}
		});
		buttonPanel.add(removeUser);
	}
	public static void selectAllButton() {
		JButton removeUser = new JButton("Poka¿ zasoby");
		removeUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel panel = new JPanel(new BorderLayout());
				
				DefaultListModel<String> listModel = new DefaultListModel<String>();
				JList<String> listBox= new JList<String>(listModel);
				panel.add(listBox);
				listModel.add(0,"\t\t*****POJAZDY*****");
				for(Vehicle v : Main.getVehicleDao().getList())listModel.add(0,v.toString());
				listModel.add(0,"\t\t*****KIEROWCY*****");
				for(Driver d : Main.getDriverDao().getList())listModel.add(0,d.toString());
				listModel.add(0,"\t\t*****MAGAZYNY*****");
				for(Warehouse w : Main.getWarehouseDao().getList())listModel.add(0,w.toString());
				listModel.add(0,"\t\t*****£ADUNEK*****");
				for(Cargo c : Main.getCargoDao().getList())listModel.add(0,c.toString());
				
				JFrame addWindow = new JFrame("Przegl¹d zasobów firmy");
				addWindow.getContentPane().add(panel);
				addWindow.pack();
				addWindow.setVisible(true);
			}
		});
		buttonPanel.add(removeUser);
	}
}
