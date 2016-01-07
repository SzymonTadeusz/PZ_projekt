package pl.edu.wat.wcy.view;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MapPanel extends JPanel{
	private static final long serialVersionUID = 2968703178034486933L;

	MapPanel(){}

	public MapPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
	}

	public MapPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
	}

	public MapPanel(LayoutManager layout) {
		super(layout);
	}

	@Override
	protected void paintComponent(Graphics g) {

        super.paintComponent(g);
	}
	
	
	
}
