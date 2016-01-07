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
	static JFrame appWindow = new JFrame("PZ Projekt - Szymon Muszyñski");
	static JPanel windowPanel = new JPanel(new BorderLayout());
	static JPanel mapPanel = new MapPanel(new GridLayout());
	static JPanel buttonPanel = new JPanel(new GridLayout());

	public CreateWindow() {
		addButtons();
		initializeWindowParameters();
		appWindow.revalidate();
		appWindow.repaint();
	}

	public static void initializeWindowParameters() {
		appWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		appWindow.setSize(900, 600);
		buttonPanel.setSize(200,600);
		mapPanel.setSize(800, 600);
		Image bgImage = null;
		 try {
	            bgImage = ImageIO.read(new File("./resources/bgMap.jpg"));
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
       JLabel l = new JLabel("Mapa");
		JLabel contentPane = new JLabel();
		contentPane.setIcon(new ImageIcon(bgImage));
		contentPane.setLayout(new BorderLayout());
		CreateWindow.mapPanel.add(contentPane);
		
		windowPanel.add(mapPanel,BorderLayout.WEST);
		windowPanel.add(buttonPanel,BorderLayout.EAST);
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
		JButton addNewDriver = new JButton("+Kierowca");
		addNewDriver.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
        		JPanel panel = new JPanel(new GridLayout());
        		
        		JTextField textFieldName = new JTextField("Imie",10);
        		panel.add(textFieldName);
           		JTextField textFieldSurname = new JTextField("Nazwisko",15);
        		panel.add(textFieldSurname);
        		JTextField textFieldID = new JTextField("PESEL",11);
        		panel.add(textFieldID);
        		JTextField textFieldDrvLic = new JTextField("Nr Prawa Jazdy",15);
        		panel.add(textFieldDrvLic);
        		
        		JFrame addWindow = new JFrame("Dodaj kierowcê");
        		JButton bt = new JButton("ZatwierdŸ");
        		bt.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Driver d = new Driver(textFieldName.getText(), textFieldSurname.getText(),textFieldID.getText(),textFieldDrvLic.getText());
						Main.getDriverDao().create(d);
						addWindow.dispose();
					}
				});
        		
        		panel.add(bt,BorderLayout.AFTER_LAST_LINE);
        		addWindow.getContentPane().add(panel);
        		addWindow.pack();
        		addWindow.setVisible(true);
			}
		});
		buttonPanel.add(addNewDriver);
	}

	public static void addMap() {
		
	}
	
	

}
