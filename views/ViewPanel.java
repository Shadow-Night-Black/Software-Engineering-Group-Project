package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.LogicalRunway;
import model.Obstacle;
import model.Runway;

public abstract class ViewPanel extends JPanel {

	private int width, height; //Panel dimensions
	private BufferedImage image;

	private Obstacle obs;
	private Runway baseRunway;
	private Runway availableRunway;
	private GUIFrame frame;
	
	// Used to determine presence of dimensions
	private boolean baseDimensions;
	private boolean calculatedDimensions;
	
	// Used to determine direction of takeoff/landing
	private boolean leftValue;

	public ViewPanel(int width, int height, Obstacle obs, Runway r, GUIFrame frame) {
		this.width = width;
		this.height = height;
		this.obs = obs;
		baseRunway = r;
		availableRunway = r;
		this.frame = frame;
		image = new BufferedImage(900, 300, BufferedImage.TYPE_INT_RGB);
		this.setPreferredSize(new Dimension(width, height));
		baseDimensions = false;
		calculatedDimensions = false;
		leftValue = true;
	}
	
	public boolean isLeftValue() {
		return leftValue;
	}

	public void setLeftValue(boolean leftValue) {
		this.leftValue = leftValue;
	}
	
	public boolean isBaseDimensions() {
		return baseDimensions;
	}

	public void setBaseDimensions(boolean baseDimensions) {
		this.baseDimensions = baseDimensions;
	}

	public boolean isCalculatedDimensions() {
		return calculatedDimensions;
	}

	public void setCalculatedDimensions(boolean calculatedDimensions) {
		this.calculatedDimensions = calculatedDimensions;
	}

	protected void drawDimension(String text, int x, int y, int length, Color color, boolean isVertical, Graphics g) {
		Graphics graphics = getImage().getGraphics();
		graphics.setColor(color);
		graphics.setFont(new Font("Arial Black", Font.PLAIN, 14));
		if (!isVertical)
		graphics.drawString(text, x+length/2 - text.length()*7, y);
		else graphics.drawString(text, x+12, y+length/2);

		int[] xPoints = new int[10];
		int[] yPoints = new int[10];
		if (!isVertical) {
			xPoints[0] = x;
			xPoints[1] = x+10;
			xPoints[2] = x+10;
			xPoints[3] = x+length-10;
			xPoints[4] = x+length-10;
			xPoints[5] = x+length;
			xPoints[6] = x+length-10;
			xPoints[7] = x+length-10;
			xPoints[8] = x+10;
			xPoints[9] = x+10;
			
			yPoints[0] = y+10;
			yPoints[1] = y+5;
			yPoints[2] = y+9;
			yPoints[3] = y+9;
			yPoints[4] = y+5;
			yPoints[5] = y+10;
			yPoints[6] = y+15;
			yPoints[7] = y+11;
			yPoints[8] = y+11;
			yPoints[9] = y+15;
		} else {
			yPoints[0] = y;
			yPoints[1] = y+10;
			yPoints[2] = y+10;
			yPoints[3] = y+length-10;
			yPoints[4] = y+length-10;
			yPoints[5] = y+length;
			yPoints[6] = y+length-10;
			yPoints[7] = y+length-10;
			yPoints[8] = y+10;
			yPoints[9] = y+10;
			
			xPoints[0] = x+10;
			xPoints[1] = x+5;
			xPoints[2] = x+9;
			xPoints[3] = x+9;
			xPoints[4] = x+5;
			xPoints[5] = x+10;
			xPoints[6] = x+15;
			xPoints[7] = x+11;
			xPoints[8] = x+11;
			xPoints[9] = x+15;
		}
		
		graphics.fillPolygon(xPoints, yPoints, 10);
		
		if (!isVertical) {
			if (x > 0) {
				graphics.drawLine(x, y+20, x, getHeight()/2 + 30);
			}
			graphics.drawLine(x+length, y+20, x+length, getHeight()/2 + 30);
		} else {
			if (x > 0) {
				graphics.drawLine(x, y, x+20, y);
			}
		}
		
		g.drawImage(getImage(), 0, 0, null);
	}
	
	protected void drawDirection(Graphics g) {
	    	Graphics graphics = getImage().getGraphics();
	        graphics.setColor(Color.LIGHT_GRAY);
	        graphics.setFont(new Font("Arial Black", Font.PLAIN, 14));
	        graphics.drawString("Direction of Landing and Takeoff", 250, 27);
	        
	        
	        
	        int[] xPoints = new int[3];
	        
	        if (leftValue) {
		        xPoints[0] = 670;
		        xPoints[1] = 670;
		        xPoints[2] = 680;
	        } else {
	        	xPoints[0] = 520;
		        xPoints[1] = 520;
		        xPoints[2] = 510;
	        }
	        
	        int[] yPoints = new int[3];
	        yPoints[0] = 13;
	        yPoints[1] = 33;
	        yPoints[2] = 23;
	        
	        graphics.fillPolygon(xPoints, yPoints, 3);
	        graphics.fillRect(520, 21, 150, 4);
	    }
	
	protected void displayObsPosX(Graphics g) {
		Graphics graphics = getImage().getGraphics();
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("Arial Black", Font.PLAIN, 14));
        String output = "Obstruction X Pos: " + (getObs().getPosX() - 60) + "m";
        graphics.drawString(output, getWidth()-200, getHeight()-20);
        g.drawImage(getImage(), 0, 0, null);
	}
	 
	protected void displayCalculationType(Graphics g) {
		Graphics graphics = getImage().getGraphics();
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.setFont(new Font("Arial Black", Font.PLAIN, 14));
        if (obs.getPosX() < (getBaseRunway().getTora()/2) ) {
        	if (leftValue)
        		graphics.drawString("Landing Over Obstruction/Taking Off Away From Obstruction", 220, 70);	
        	else 
        		graphics.drawString("Landing Towards Obstruction/Taking Off Towards Obstruction", 220, 70);
        } else {
        	if (leftValue)
        		graphics.drawString("Landing Towards Obstruction/Taking Off Towards Obstruction", 220, 70);	
        	else
        		graphics.drawString("Landing Over Obstruction/Taking Off Away From Obstruction", 220, 70);	
        }
	}

    public float convertX(int x) { //Pixel x to real x
    	float outputValue = 0;
    	if (x < getWidth()/5) {
    		outputValue = x * ( (float) (getBaseRunway().getStripEnd()) / (getWidth()/5));
    	} else if (x < 4*getWidth()/5) {
    		outputValue = getBaseRunway().getStripEnd() + (x - getWidth()/5) * ( (float) (getBaseRunway().getTora()) / (3 * getWidth()/5));
    	} else {
    		outputValue = getBaseRunway().getStripEnd() + getBaseRunway().getTora() + (x - 4*getWidth()/5) * ( (float) (getBaseRunway().getStripEnd()) / (getWidth()/5));
    	}
    	return outputValue;
    }
    
    public float invertX(int x) { //real x to pixel x
    	float outputValue = 0;
    	if (x <= getBaseRunway().getStripEnd()) {
    		outputValue = x / ( (float) (getBaseRunway().getStripEnd()) / (getWidth()/5));
    	} else if (x <= getBaseRunway().getStripEnd() + getBaseRunway().getTora()) {
    		outputValue = getWidth()/5 + (x - getBaseRunway().getStripEnd()) / ( (float) (getBaseRunway().getTora()) / (3 * getWidth()/5));
    	} else {
    		outputValue = 4*getWidth()/5 + (x - (getBaseRunway().getStripEnd() + getBaseRunway().getTora())) / ( (float) (getBaseRunway().getStripEnd()) / (getWidth()/5));
    	}    	
    	return outputValue;
    }

    public float convertY(int y) {
        return y * (300/getImage().getHeight()); 
    }
    
    public float invertY(int y) {
    	return y / (300/getImage().getHeight());
    }
	
	public GUIFrame getFrame() {
		return frame;
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		drawView(g);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public Obstacle getObs() {
		return obs;
	}

	public void setObs(Obstacle obs) {
		this.obs = obs;
	}

	public LogicalRunway getBaseRunway() {
		if (leftValue) return baseRunway.getEastFacing();
		else return baseRunway.getWestFacing();
	}

	public void setBaseRunway(Runway baseRunway) {
		this.baseRunway = baseRunway;
	}

	public LogicalRunway getAvailableRunway() {
		if (leftValue) return availableRunway.getEastFacing();
		else return availableRunway.getWestFacing();
	}

	public void setAvailableRunway(Runway availableRunway) {
		this.availableRunway = availableRunway;
	}
	
	abstract void drawView(Graphics g);
	
}
