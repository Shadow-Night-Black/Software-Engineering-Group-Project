package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class LegendPanel extends JPanel {
	
	private int width, height;
	private BufferedImage image;
	
	public LegendPanel(int width, int height) {
		this.width = width;
		this.height = height;
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		this.setPreferredSize(new Dimension(width, height));
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawLegend(g);
	}
	
	private void drawLegend(Graphics g) {
		drawKey(Color.BLACK, "Legend:", g, 160, 10, true);
		drawKey(Color.WHITE, "Threshold", g, 10, 40, true);
		drawKey(Color.YELLOW, "TORA", g, 10, 70, true);
		drawKey(Color.GRAY, "LDA", g, 10, 100, true);
		drawKey(Color.RED, "Unavailable", g, 10, 130, true);
		drawKey(new Color(138,43,226), "Safe area", g, 10, 160, true);
		drawKey(new Color(102,0,0), "Unavailable Stopway", g, 275, 40, true);
		drawKey(Color.PINK, "Stopway", g, 275, 70, true);
		drawKey(Color.RED, "Unavailable Clearway", g, 275, 100, false);
		drawKey(Color.PINK, "Clearway", g, 275, 130, false);
		drawKey(new Color(153, 76, 0), "Blast Allowance", g, 275, 160, true);
		g.drawImage(image, 0, 0, null);
	}
	
	private void drawKey(Color color, String string, Graphics g, int x, int y, boolean fill) {
		Graphics graphics = image.getGraphics();
		graphics.setColor(color);
        if (fill) graphics.fillRect(x, y, 20, 10);
        else graphics.drawRect(x, y, 20, 10);
        
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("Arial Black", Font.PLAIN, 14));
        graphics.drawString(string, x+30, y+10);
	}
}
