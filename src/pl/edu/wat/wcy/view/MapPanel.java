package pl.edu.wat.wcy.view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.ConcurrentModificationException;
import java.util.GregorianCalendar;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import pl.edu.wat.wcy.main.Main;
import pl.edu.wat.wcy.model.entities.EventLog;
import pl.edu.wat.wcy.model.entities.Vehicle;
import pl.edu.wat.wcy.model.entities.Warehouse;

@SuppressWarnings("serial")
public class MapPanel extends JPanel implements ActionListener {
	private Timer timer;
	private Image bgImage;
	private int delay = 250;

	public MapPanel() {
		loadMap();
	}

	public MapPanel(LayoutManager arg0) {
		super(arg0);
		loadMap();
	}

	public MapPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		loadMap();
	}

	public MapPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		loadMap();
	}

	@Override
	protected void paintComponent(Graphics g) {
		try {
			super.paintComponent(g);
			g.drawImage(bgImage, 0, 0, null);
			for (Vehicle v : Main.getVehicleDao().getList()) {
				v.move();
				v.paint(g);
			}
			for (Warehouse w : Main.getWarehouseDao().getList()) {
				w.paint(g);
			}
		} catch (ConcurrentModificationException e) {}

	}

	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == timer) {
			repaint();
		}
	}

	public void loadMap() {
		try {
			bgImage = ImageIO.read(new File("./resources/bgMap.jpg"));
		} catch (IOException e) {
			Main.eventLog.warning("Nie zaladowano mapy!");
			Calendar now = GregorianCalendar.getInstance();
			Main.eventDao.create(new EventLog(now.getTime()+" WARN: " + Main.loggedUser.getName() + " - niepowodzenie �adowania mapy."));
		}
		timer = new Timer(delay, this);
		timer.start();
	}

}
