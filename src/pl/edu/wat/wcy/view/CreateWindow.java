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
	static JFrame appWindow = new JFrame("PZ Projekt - Szymon Muszyñski");
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
		buttonPanel.setSize(200, 600);
		mapPanel.setSize(800, 600);
		/*
		 * Image bgImage = null; try { bgImage = ImageIO.read(new
		 * File("./resources/bgMap.jpg")); } catch (IOException e) {
		 * e.printStackTrace(); } JLabel contentPane = new JLabel();
		 * contentPane.setIcon(new ImageIcon(bgImage));
		 * contentPane.setLayout(new BorderLayout());
		 * CreateWindow.mapPanel.add(contentPane);
		 */

		windowPanel.add(mapPanel, BorderLayout.CENTER);
		windowPanel.add(buttonPanel, BorderLayout.EAST);
		appWindow.getContentPane().add(windowPanel);
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

	public static void addButtons() {
		{ // lokalny scope do ograniczania zasiêgu zmiennych JTextField i im
			// podobnych
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
		{
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
		{
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
							Cargo d = new Cargo(textFieldCargoName.getText(), weight);
							Main.getCargoDao().create(d);
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
		{
			JButton addNewCargo = new JButton("ZA£ADUJ");
			addNewCargo.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JPanel panel = new JPanel(new GridLayout());
					JComboBox<Cargo> cargoBox = new JComboBox<Cargo>((Cargo[]) Main.getCargoDao().getList()
							.toArray(new Cargo[Main.getCargoDao().getList().size()]));
					JComboBox<Vehicle> vehicleBox = new JComboBox<Vehicle>((Vehicle[]) Main.getVehicleDao().getList()
							.toArray(new Vehicle[Main.getVehicleDao().getList().size()]));
					panel.add(cargoBox);
					panel.add(vehicleBox);
					JFrame addWindow = new JFrame("Dodaj ³adunek");
					JButton bt = new JButton("ZatwierdŸ");
					bt.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							// System.out.println(cargoBox.getSelectedItem());
							// System.out.println(vehicleBox.getSelectedItem());
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

					panel.add(bt, BorderLayout.AFTER_LAST_LINE);
					addWindow.getContentPane().add(panel);
					addWindow.pack();
					addWindow.setVisible(true);
				}
			});
			buttonPanel.add(addNewCargo);
		}
		{
			JButton addNewTransport = new JButton("+ Transport");
			addNewTransport.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JPanel panel = new JPanel(new GridLayout());
					JComboBox<Warehouse> warehouseBox = new JComboBox<Warehouse>((Warehouse[]) Main.getWarehouseDao().getList()
							.toArray(new Warehouse[Main.getWarehouseDao().getList().size()]));
					panel.add(warehouseBox);
					JFrame addWindow = new JFrame("Dodaj zlecenie transportu");
					JButton bt = new JButton("ZatwierdŸ");
					bt.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							Warehouse w = (Warehouse)warehouseBox.getSelectedItem();
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
		{
			JButton addNewTransport = new JButton("WYŒLIJ TRANSPORT");
			addNewTransport.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JPanel panel = new JPanel(new BorderLayout());
					JComboBox<Transport> transportBox = new JComboBox<Transport>((Transport[]) Main.getTransportDao()
							.getList().toArray(new Transport[Main.getTransportDao().getList().size()]));
					JComboBox<Vehicle> vehicleBox = new JComboBox<Vehicle>((Vehicle[]) Main.getVehicleDao().getList()
							.toArray(new Vehicle[Main.getVehicleDao().getList().size()]));
					panel.add(transportBox,BorderLayout.WEST);
					panel.add(vehicleBox, BorderLayout.CENTER);
					JFrame addWindow = new JFrame("Przydziel zlecenie");
					JButton bt = new JButton("ZatwierdŸ");
					bt.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							Transport t = (Transport) transportBox.getSelectedItem();
							Vehicle v = (Vehicle) vehicleBox.getSelectedItem();
							v.setTransport(t);
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
		{
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
								x = Integer.parseInt(textFieldX.getText());
								y = Integer.parseInt(textFieldY.getText());
							} catch (NumberFormatException exc) {
								numer = 200;
								x = 0;
								y = 0;
							}
							Warehouse d = new Warehouse(textFieldStreet.getText(), numer,
									textFieldWarehouseName.getText(), x, y);
							Main.getWarehouseDao().create(d);
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
		{
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
							System.out.println("Usuniêto kierowcê z bazy.");
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
		{
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
							Vehicle r = ((Vehicle) vehicleBox.getSelectedItem());
							if (r.getCurrentDriver() != null)
								r.getCurrentDriver().setDriverOf(null);
							r.setCurrentDriver(null); // najpierw usun
														// powiazanie
							Main.getVehicleDao().update(r);
							Main.getVehicleDao().delete(((Vehicle) vehicleBox.getSelectedItem()).getVehicleID());
							System.out.println("Usuniêto pojazd z bazy.");
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
		{
			JButton removeCargo = new JButton("- £adunek");
			removeCargo.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JPanel panel = new JPanel(new BorderLayout());
					JComboBox<Cargo> cargoBox = new JComboBox<Cargo>((Cargo[]) Main.getCargoDao().getList()
							.toArray(new Cargo[Main.getCargoDao().getList().size()]));
					panel.add(cargoBox);

					JFrame addWindow = new JFrame("Usuñ ³adunek");
					JButton bt = new JButton("ZatwierdŸ");
					bt.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							Cargo r = ((Cargo) cargoBox.getSelectedItem());
							if (r.getVehicle() != null)
								for (Vehicle v : r.getVehicle())
									v.removeFromCargo(r);
							r.setVehicle(null); // najpierw usun powiazanie
							Main.getCargoDao().update(r);
							Main.getCargoDao().delete(((Cargo) cargoBox.getSelectedItem()).getCargoID());
							System.out.println("Usuniêto ³adunek z bazy.");
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
		{
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
							Warehouse r = ((Warehouse) warehouseBox.getSelectedItem());
							if (r.getAwaitingTransport() != null)
								for (Transport t : r.getAwaitingTransport())
									t.setDestination(null);
							r.setAwaitingTransport(null); // najpierw usun
															// powiazanie
							Main.getWarehouseDao().update(r);
							Main.getWarehouseDao()
									.delete(((Warehouse) warehouseBox.getSelectedItem()).getWarehouseID());
							System.out.println("Usuniêto magazyn z bazy.");
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

	}

}
