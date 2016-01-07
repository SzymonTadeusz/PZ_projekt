package pl.edu.wat.wcy.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import org.hibernate.HibernateException;

import pl.edu.wat.wcy.main.Main;
import pl.edu.wat.wcy.model.dao.EMStorage;
import pl.edu.wat.wcy.model.entities.*;

public class CreateWindow {
	static JFrame appWindow = new JFrame("PZ Projekt - Szymon MuszyÒski");
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
		appWindow.setSize(920, 600);
		buttonPanel.setSize(200, 600);
		mapPanel.setSize(800, 600);
		Image bgImage = null;
		try {
			bgImage = ImageIO.read(new File("./resources/bgMap.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JLabel contentPane = new JLabel();
		contentPane.setIcon(new ImageIcon(bgImage));
		contentPane.setLayout(new BorderLayout());
		CreateWindow.mapPanel.add(contentPane);

		windowPanel.add(mapPanel, BorderLayout.WEST);
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
		{ //lokalny scope do ograniczania zasiÍgu zmiennych JTextField i im podobnych
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
					
					JFrame addWindow = new JFrame("Dodaj kierowcÍ");
					JButton bt = new JButton("Zatwierdü");
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
				JTextField textFieldCapacity = new JTextField("PojemnoúÊ", 10);
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
				JButton bt = new JButton("Zatwierdü");
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
				JTextField textFieldCargoName = new JTextField("Nazwa ≥adunku", 15);
				panel.add(textFieldCargoName);


				JFrame addWindow = new JFrame("Dodaj ≥adunek");
				JButton bt = new JButton("Zatwierdü");
				bt.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						int weight;
						try {
							weight = Integer.parseInt(textFieldWeight.getText());
							} catch (NumberFormatException exc) {
							weight = 200;
						}
						Cargo d = new Cargo(textFieldCargoName.getText(),weight);
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
		JButton addNewCargo = new JButton("//Zaladuj\\");
		addNewCargo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel panel = new JPanel(new GridLayout());
				JComboBox<Cargo> cargoBox = new JComboBox<Cargo>((Cargo[])Main.getCargoDao().getList().toArray(new Cargo[Main.getCargoDao().getList().size()]));
				JComboBox<Vehicle> vehicleBox = new JComboBox<Vehicle>((Vehicle[])Main.getVehicleDao().getList().toArray(new Vehicle[Main.getVehicleDao().getList().size()]));
				panel.add(cargoBox);
				panel.add(vehicleBox);
				

				JFrame addWindow = new JFrame("Dodaj ≥adunek");
				JButton bt = new JButton("Zatwierdü");
				bt.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						//System.out.println(cargoBox.getSelectedItem());
						//System.out.println(vehicleBox.getSelectedItem());
						Cargo c = (Cargo)cargoBox.getSelectedItem();
						Vehicle v = (Vehicle)vehicleBox.getSelectedItem();
						System.out.println(c);
						System.out.println(v);
						c.addToVehicles(v);
						v.addToCargo(c);
						Main.getCargoDao().update(c);
						Main.getVehicleDao().update(v);
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
				JButton bt = new JButton("Zatwierdü");
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
							x=0;
							y=0;
						}
						Warehouse d = new Warehouse(textFieldStreet.getText(), numer, textFieldWarehouseName.getText(), x, y);
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
	}

	
	
	public static void addMap() {

	}

}
