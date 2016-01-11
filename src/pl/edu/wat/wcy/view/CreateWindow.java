package pl.edu.wat.wcy.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import org.hibernate.HibernateException;

import pl.edu.wat.wcy.main.Main;
import pl.edu.wat.wcy.model.dao.EMStorage;
import pl.edu.wat.wcy.model.entities.*;

public class CreateWindow {
	static JFrame appWindow = new JFrame("PZ Projekt - Szymon Muszy�ski");
	static JPanel windowPanel = new JPanel(new BorderLayout());
	static JPanel mapPanel = new MapPanel(new GridLayout());
	static JPanel buttonPanel = new JPanel(new GridLayout(10, 1));

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
		JLabel l = new JLabel("Trwa �adowanie danych z bazy, prosz� czeka�...");
		l.setLocation(400, 280);
		l.setSize(500, 50);
		l.setVisible(true);
		appWindow.getContentPane().add(l);
		appWindow.getContentPane().add(windowPanel);
		windowPanel.setVisible(false);
		appWindow.setVisible(true);
		appWindow.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.out.println("**********************************\n" + "Zamykam polaczenie oraz aplikacje.\n"
						+ "**********************************");
				try {
					EMStorage.getEm().close();
					EMStorage.getEmf().close();
				} catch (HibernateException e1) {
					System.out.println("Blad Hibernate'a!");
				} catch (IllegalStateException e2) {
					System.out.println("Blad - menedzer zostal wczesniej zamkniety!");
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
		addDriverButton();
		removeDriverButton();
		addVehicleButton();
		removeVehicleButton();
		addCargoToVehicleButton();
		addDriverToVehicleButton();
		addWarehouseButton();
		removeWarehouseButton();
		addCargoButton();
		removeCargoButton();
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
				JFrame addWindow = new JFrame("Dodaj kierowc�");
				JButton bt = new JButton("Zatwierd�");
				bt.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Driver d = new Driver(textFieldName.getText(), textFieldSurname.getText(),
								textFieldID.getText(), textFieldDrvLic.getText());
						Main.getDriverDao().create(d);
						System.out.println("Dodano kierowc�: " + d);
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

				JFrame addWindow = new JFrame("Usu� kierowc�");
				JButton bt = new JButton("Zatwierd�");
				bt.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Driver d = ((Driver) driverBox.getSelectedItem());
						if (d.getDriverOf() != null)
							d.getDriverOf().setCurrentDriver(null);
						d.setDriverOf(null); // najpierw usun powiazanie
						Main.getDriverDao().update(d);
						Main.getDriverDao().delete(((Driver) driverBox.getSelectedItem()).getDriverID());
						System.out.println("Usuni�to kierowc� " + d + "  z bazy.");
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
				JTextField textFieldCapacity = new JTextField("Pojemno��", 10);
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
				JButton bt = new JButton("Zatwierd�");
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
						System.out.println("Dodano pojazd: " + d);
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

				JFrame addWindow = new JFrame("Usu� pojazd");
				JButton bt = new JButton("Zatwierd�");
				bt.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Vehicle v = ((Vehicle) vehicleBox.getSelectedItem());
						if (v.getCurrentDriver() != null)
							v.getCurrentDriver().setDriverOf(null);
						v.setCurrentDriver(null); // najpierw usun powiazanie
						Main.getVehicleDao().update(v);
						Main.getVehicleDao().delete(((Vehicle) vehicleBox.getSelectedItem()).getVehicleID());
						System.out.println("Usuni�to pojazd " + v +" z bazy.");
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
		JButton addNewCargo = new JButton("PRZYDZIEL KIEROWC�");
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
				JFrame addWindow = new JFrame("Przydziel kierowc�");
				JButton bt = new JButton("Zatwierd�");
				bt.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Driver d = (Driver) driverBox.getSelectedItem();
						Vehicle v = (Vehicle) vehicleBox.getSelectedItem();
						d.setDriverOf(v);
						v.setCurrentDriver(d);
						Main.getDriverDao().update(d);
						Main.getVehicleDao().update(v);
						System.out.println("Zmodyfikowano:\n" + v);
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
		JButton addNewCargo = new JButton("+ �adunek");
		addNewCargo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel panel = new JPanel(new GridLayout());
				JTextField textFieldWeight = new JTextField("Waga", 10);
				panel.add(textFieldWeight);
				JTextField textFieldCargoName = new JTextField("Nazwa �adunku", 15);
				panel.add(textFieldCargoName);
				JFrame addWindow = new JFrame("Dodaj �adunek");
				JButton bt = new JButton("Zatwierd�");
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
						System.out.println("Dodano: " + c);
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
		JButton removeCargo = new JButton("- �adunek");
		removeCargo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel panel = new JPanel(new BorderLayout());
				JComboBox<Cargo> cargoBox = new JComboBox<Cargo>(
						(Cargo[]) Main.getCargoDao().getList().toArray(new Cargo[Main.getCargoDao().getList().size()]));
				panel.add(cargoBox);

				JFrame addWindow = new JFrame("Usu� �adunek");
				JButton bt = new JButton("Zatwierd�");
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
						System.out.println("Usuni�to �adunek " + c +" z bazy.");
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
				JButton bt = new JButton("Zatwierd�");
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
						System.out.println("Dodano: " + d);
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

				JFrame addWindow = new JFrame("Usu� magazyn");
				JButton bt = new JButton("Zatwierd�");
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
						System.out.println("Usuni�to magazyn " + w +" z bazy.");
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
		JButton addNewCargo = new JButton("ZA�ADUJ POJAZD");
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
				JFrame addWindow = new JFrame("Dodaj �adunek");
				JButton bt = new JButton("Zatwierd�");
				bt.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Cargo c = (Cargo) cargoBox.getSelectedItem();
						Vehicle v = (Vehicle) vehicleBox.getSelectedItem();
						c.addToVehicles(v);
						v.addToCargo(c);
						Main.getCargoDao().update(c);
						Main.getVehicleDao().update(v);
						System.out.println("Zmodyfikowano:\n" + v);
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
				JButton bt = new JButton("Zatwierd�");
				bt.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Warehouse w = (Warehouse) warehouseBox.getSelectedItem();
						Transport t = new Transport(w);
						Main.getTransportDao().create(t);
						System.out.println("Dodano zlecenie:" + t);
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
		JButton addNewTransport = new JButton("WY�LIJ TRANSPORT");
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
				JButton bt = new JButton("Zatwierd�");
				bt.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Transport t = (Transport) transportBox.getSelectedItem();
						Vehicle v = (Vehicle) vehicleBox.getSelectedItem();
						v.setTransport(t);
						v.setArrivalListener(t.getDestination());
						t.getDestination().addToTransports(t);
						Main.getTransportDao().update(t);
						Main.getVehicleDao().update(v);
						System.out.println("Dodano:\n" + v);
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
