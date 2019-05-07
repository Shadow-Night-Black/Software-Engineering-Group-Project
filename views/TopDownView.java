package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.*;

public class TopDownView extends ViewPanel {
	
	private int previousDirection = -1;
	private boolean rotate;

    public TopDownView(int width, int height, Obstacle obs, Runway r, GUIFrame frame) {
        super(width, height, obs, r, frame);
        if (height > getImage().getHeight()) rotate = true;
        else rotate = false;
        ImageMouseListener l = new ImageMouseListener(frame);
        this.addMouseListener(l);
        this.addMouseMotionListener(l);
    }

    void drawView(Graphics g) {
        //TODO: Drawing of stopway and clearway, creation of space to achieve this.
    	if (rotate) {
	    	g.translate(0, (getHeight()-getImage().getHeight())/2);
	    	rotateGraphic(g, getBaseRunway().getDirection());
    	}
        drawStopwayClearedArea(0, g);
        drawStopwayClearedArea(4 * getImage().getWidth() / 5, g);
        drawClearedArea(g);
        drawDirection(g);
        displayCalculationType(g);
        drawBaseRunway(g);
        drawAvailableTora(g);
        drawAvailableLDA(g);
        drawDisplacedThreshold(g);
        drawEndOfTora(g);
		drawRunwayBorder(g);
		drawBaseStopway(g);
		drawBaseClearway(g);
		drawAvailableStopway(g);
		drawAvailableClearway(g);
		drawRunwayDetail(g);
        drawObstacle(g);
        if (getAvailableRunway().isObstrusted()) {
            drawBlastAllowance(g);
            drawRESA(g);
        }
        displayObsPosX(g);
        if (isBaseDimensions()) drawBaseDimensions(g);
        g.drawImage(getImage(), 0, 0, null);
    }
    
    private void rotateGraphic(Graphics g, int runwayDirection) {
    	if (runwayDirection - 9 != 0) ((Graphics2D) g).rotate(Math.toRadians((runwayDirection-9)*10), getImage().getWidth()/2, getImage().getHeight()/2);
    }
    
    public boolean isRotate() {
    	return rotate;
    }
    
    public void setRotate(boolean rotate) {
    	this.rotate = rotate;
    }

    /*
     * Base dimensions of the runway (as seen in Spec)
     */
    private void drawBaseDimensions(Graphics g) {
        drawDimension(getBaseRunway().getStripEnd() + "m", 0, 200, (getWidth() / 5), Color.WHITE, false, g);
        drawDimension("150m", getImage().getWidth() / 5, 225, (int) invertX(210) - (getWidth() / 5) + 10, Color.WHITE, false, g);
        drawDimension("300m", getImage().getWidth() / 5, 260, (int) invertX(300) - (getWidth() / 5) + 15, Color.WHITE, false, g);
        drawDimension(getBaseRunway().getStripEnd() + "m", 4 * getImage().getWidth() / 5, 200, getImage().getWidth() / 5, Color.WHITE, false, g);
        drawDimension("75m", getImage().getWidth() / 3, 75, 75, Color.WHITE, true, g);
        drawDimension("105m", getImage().getWidth() / 3 + 30, 150, 105, Color.WHITE, true, g);
        drawDimension("150m", getImage().getWidth() / 2, 150, 150, Color.WHITE, true, g);
    }


    private void drawBaseStopway(Graphics g) {
        float xIncrement = (float) getBaseRunway().getStripEnd() / (getWidth() / 5); //Length of runway + both strip ends, per pixel.
        int yIncrement = 300 / getImage().getHeight();
        int stopway = getBaseRunway().getAsda() - getBaseRunway().getTora();
        if (stopway > 0) {
        	// If Left Threshold viewed
        	if (isLeftValue()) {
	            for (int imageX = 4 * getImage().getWidth() / 5; imageX < 4 * getImage().getWidth() / 5 + stopway / xIncrement; imageX++) {
	                for (int imageY = (int) (125. / yIncrement); imageY <= (int) (175. / yIncrement); imageY++) {
	                    getImage().setRGB(imageX, imageY, new Color(102, 0, 0).getRGB());
	                }
	            }
        	}
        	// Else right threshold viewed
        	else {
        		for (int imageX = getImage().getWidth() / 5; imageX > getImage().getWidth() / 5 - stopway / xIncrement; imageX--) {
	                for (int imageY = (int) (125. / yIncrement); imageY <= (int) (175. / yIncrement); imageY++) {
	                    getImage().setRGB(imageX, imageY, new Color(102, 0, 0).getRGB());
	                }
	            }
        	}
        }
    }

    private void drawAvailableStopway(Graphics g) {
        float xIncrement = (float) getBaseRunway().getStripEnd() / (getWidth() / 5); //Length of runway + both strip ends, per pixel.
        int yIncrement = 300 / getImage().getHeight();
        int stopway = getAvailableRunway().getAsda() - getAvailableRunway().getTora();
        if (stopway > 0) {
        	if (isLeftValue()) {
	            for (int imageX = 4 * getImage().getWidth() / 5; imageX < 4 * getImage().getWidth() / 5 + stopway / xIncrement; imageX++) {
	                for (int imageY = (int) (125. / yIncrement); imageY <= (int) (175. / yIncrement); imageY++) {
	                    getImage().setRGB(imageX, imageY, Color.PINK.getRGB());
	                }
	            }
        	}
        	// Else right threshold viewed
        	else {
        		for (int imageX = getImage().getWidth() / 5; imageX > getImage().getWidth() / 5 - stopway / xIncrement; imageX--) {
	                for (int imageY = (int) (125. / yIncrement); imageY <= (int) (175. / yIncrement); imageY++) {
	                    getImage().setRGB(imageX, imageY, Color.PINK.getRGB());
	                }
	            }
        	}
            if (isCalculatedDimensions())
                drawDimension(stopway + "m", 4 * getImage().getWidth() / 5, 200, (int) (stopway / xIncrement), Color.PINK, false, g);
        }
    }

    private void drawBaseClearway(Graphics g) {
        float xIncrement = (float) getBaseRunway().getStripEnd() / (getWidth() / 5); //Length of runway + both strip ends, per pixel.
        int yIncrement = 300 / getImage().getHeight();
        int stopway = getBaseRunway().getToda() - getBaseRunway().getTora();
        if (stopway > 0) {
        	// If Left Threshold Viewed
        	if (isLeftValue()) {
	            for (int imageX = 4 * getImage().getWidth() / 5; imageX < 4 * getImage().getWidth() / 5 + stopway / xIncrement; imageX++) {
	                getImage().setRGB(imageX, (int) (125. / yIncrement), Color.RED.getRGB());
	                getImage().setRGB(imageX, (int) (175. / yIncrement), Color.RED.getRGB());
	            }
	            for (int imageY = 125; imageY <= 175; imageY++) {
	                getImage().setRGB((int) (4 * getImage().getWidth() / 5 + stopway / xIncrement - 1), imageY, Color.RED.getRGB());
	            }
        	}
        	// Else right threshold viewed
        	else {
        		for (int imageX = getImage().getWidth() / 5; imageX > getImage().getWidth() / 5 - stopway / xIncrement; imageX--) {
	                getImage().setRGB(imageX, (int) (125. / yIncrement), Color.RED.getRGB());
	                getImage().setRGB(imageX, (int) (175. / yIncrement), Color.RED.getRGB());
	            }
	            for (int imageY = 125; imageY <= 175; imageY++) {
	                getImage().setRGB((int) (getWidth() / 5 - stopway / xIncrement + 1), imageY, Color.RED.getRGB());
	            }
        	}
        }
    }

    private void drawAvailableClearway(Graphics g) {
        float xIncrement = (float) getBaseRunway().getStripEnd() / (getWidth() / 5); //Length of runway + both strip ends, per pixel.
        int yIncrement = 300 / getImage().getHeight();
        int stopway = getAvailableRunway().getToda() - getAvailableRunway().getTora();
        if (stopway > 0) {
        	if (isLeftValue()) {
	            for (int imageX = 4 * getImage().getWidth() / 5; imageX < 4 * getImage().getWidth() / 5 + stopway / xIncrement; imageX++) {
	                getImage().setRGB(imageX, (int) (125. / yIncrement), Color.PINK.getRGB());
	                getImage().setRGB(imageX, (int) (175. / yIncrement), Color.PINK.getRGB());
	            }
	            for (int imageY = 125; imageY <= 175; imageY++) {
	                getImage().setRGB((int) (4 * getImage().getWidth() / 5 + stopway / xIncrement - 1), imageY, Color.PINK.getRGB());
	            }
        	}
        	// Else right threshold viewed
        	else {
        		for (int imageX = getImage().getWidth() / 5; imageX > getImage().getWidth() / 5 - stopway / xIncrement; imageX--) {
	                getImage().setRGB(imageX, (int) (125. / yIncrement), Color.PINK.getRGB());
	                getImage().setRGB(imageX, (int) (175. / yIncrement), Color.PINK.getRGB());
	            }
	            for (int imageY = 125; imageY <= 175; imageY++) {
	                getImage().setRGB((int) (getWidth() / 5 - stopway / xIncrement + 1), imageY, Color.PINK.getRGB());
	            }
        	}
            if (isCalculatedDimensions())
                drawDimension(stopway + "m", 4 * getImage().getWidth() / 5, 240, (int) (stopway / xIncrement), Color.PINK, false, g);
        }
    }

    private void drawDisplacedThreshold(Graphics g) {
        float xIncrement = (float) ( (getBaseRunway().getTora()) / (3 * getImage().getWidth() / 5)); //Length of runway + both strip ends, per pixel.
        int yIncrement = 300 / getImage().getHeight();
        int actualX = getBaseRunway().getTora() - getBaseRunway().getLda();
        // If Left Threshold Viewed
        if (isLeftValue()) {
	        for (int imageX = getImage().getWidth() / 5 + (int) ((actualX - 1) / xIncrement); imageX <= getImage().getWidth() / 5 + (actualX + 1) / xIncrement; imageX++) {
	            for (int imageY = (int) (125. / yIncrement); imageY <= (int) (175. / yIncrement); imageY++) {
	                getImage().setRGB(imageX, imageY, Color.WHITE.getRGB());
	            }
	        }
        }
        // Else Right Threshold Viewed
        else {
        	for (int imageX = 4*getWidth() / 5 - (int) ((actualX - 1) / xIncrement); imageX >= 4*getWidth() / 5 - (actualX + 1) / xIncrement; imageX--) {
	            for (int imageY = (int) (125. / yIncrement); imageY <= (int) (175. / yIncrement); imageY++) {
	                getImage().setRGB(imageX, imageY, Color.WHITE.getRGB());
	            }
	        }
        }
        if (isCalculatedDimensions())
            drawDimension(actualX + "m", getImage().getWidth() / 5, 200, (int) (actualX / xIncrement), Color.WHITE, false, g);
    }

    private void drawEndOfTora(Graphics g) {
        float xIncrement = (float) (getBaseRunway().getTora() / (3 * getImage().getWidth() / 5)); //Length of runway + both strip ends, per pixel.
        int yIncrement = 300 / getImage().getHeight();
        // If Left Threshold Viewed
        if (isLeftValue()) {
	        if (getObs().getPosX() > getBaseRunway().getTora() / 2) {
				int actualX = getAvailableRunway().getTora() + getAvailableRunway().getStripEnd();
				for (int imageX = (int) invertX(actualX); imageX <= invertX(actualX) + 1; imageX++) {
					for (int imageY = (int) (125. / yIncrement); imageY <= (int) (175. / yIncrement); imageY++) {
						if (actualX >= getAvailableRunway().getStripEnd())
							getImage().setRGB(imageX, imageY, Color.YELLOW.getRGB());
					}
				}
			}
        }
        // If Right Threshold Viewed
        else {
        	if (getObs().getPosX() < getBaseRunway().getTora() / 2) {
				int actualX = getBaseRunway().getStripEnd() + getBaseRunway().getTora() - getAvailableRunway().getTora();
				for (int imageX = (int) invertX(actualX); imageX <= invertX(actualX) + 1; imageX++) {
					for (int imageY = (int) (125. / yIncrement); imageY <= (int) (175. / yIncrement); imageY++) {
						if (actualX <= getBaseRunway().getStripEnd() + getBaseRunway().getTora())
							getImage().setRGB(imageX, imageY, Color.YELLOW.getRGB());
					}
				}
			}
        }
    }

    private void drawRunwayDetail(Graphics g) {
        Graphics graphics = getImage().getGraphics();
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.setFont(new Font("Arial Black", Font.BOLD, 20));
        if (isLeftValue()) {
	        graphics.drawString(String.valueOf(getBaseRunway().getDirection()) + getBaseRunway().getDesignator().toString(), 225, 157);
	        graphics.drawString(String.valueOf(getBaseRunway().getDirection() + 18) + getBaseRunway().getDesignator().opposite().toString(), getImage().getWidth() - 265, 157);
        } else {
	        graphics.drawString(String.valueOf(getBaseRunway().getDirection()) + getBaseRunway().getDesignator().toString(), getImage().getWidth() - 265, 157); 
	        graphics.drawString(String.valueOf(getBaseRunway().getDirection() - 18) + getBaseRunway().getDesignator().opposite().toString(), 225, 157);       	
        }
        drawCenterline(g);
    }

    private void drawRunwayBorder(Graphics g) {
        float xIncrement = ((float) (2 * getBaseRunway().getStripEnd()) + getBaseRunway().getTora()) / (3 * getImage().getWidth() / 5); //Length of runway + both strip ends, per pixel.
        int yIncrement = 300 / getImage().getHeight();
        for (int imageX = getImage().getWidth() / 5; imageX < 4 * getImage().getWidth() / 5; imageX++) {
            getImage().setRGB(imageX, (int) (125. / yIncrement), Color.BLACK.getRGB());
            getImage().setRGB(imageX, (int) (125. / yIncrement) - 1, Color.BLACK.getRGB());
            getImage().setRGB(imageX, (int) (175. / yIncrement), Color.BLACK.getRGB());
            getImage().setRGB(imageX, (int) (175. / yIncrement) + 1, Color.BLACK.getRGB());
        }
        for (int imageY = (int) (125. / yIncrement); imageY <= (int) (175. / yIncrement); imageY++) {
            getImage().setRGB(getWidth() / 5, imageY, Color.BLACK.getRGB());
            getImage().setRGB(getWidth() / 5 - 1, imageY, Color.BLACK.getRGB());
            getImage().setRGB(4 * getImage().getWidth() / 5, imageY, Color.BLACK.getRGB());
            getImage().setRGB(4 * getImage().getWidth() / 5 + 1, imageY, Color.BLACK.getRGB());
        }
    }

    private void drawClearedArea(Graphics g) {
        //TODO: Drawing of stopway and clearway, exaggeration of StripEnds required for this
        float xIncrement = ((float) (2 * getBaseRunway().getStripEnd()) + getBaseRunway().getTora()) / (3 * getImage().getWidth() / 5); //Length of runway + both strip ends, per pixel.
        int yIncrement = 300 / getImage().getHeight();            // width of runway in view / height of image in pixels

        float actualX = 0;
        float actualY = 0;
        Color safeColor = new Color(138, 43, 226);    //Area outside of clear and graded area, see spec for info
        Color safeBorderColor = Color.BLACK;
        Color cAndGColor = new Color(0, 0, 204);        //Area surrounding the track, see spec for info
        for (int imageX = getImage().getWidth() / 5; imageX < 4 * getImage().getWidth() / 5; imageX++) {
            actualX = (imageX - getImage().getWidth() / 5) * xIncrement;
            for (int imageY = 0; imageY < getImage().getHeight(); imageY++) {
                actualY = imageY * yIncrement;
                if (actualY <= 45 || actualY >= 255) {                        // 105m edge colouring
                    if ((actualY + yIncrement > 43 && actualY <= 45) || (actualY - yIncrement < 257 && actualY >= 255)) {
                        getImage().setRGB(imageX, imageY, safeBorderColor.getRGB());
                    } else getImage().setRGB(imageX, imageY, safeColor.getRGB());
                }
                if (actualY <= 75 || actualY >= 225) {                        // 75m edge colouring
                    if (actualX <= 210 || (actualX >= getBaseRunway().getTora() + getBaseRunway().getStripEnd() - 150)) {
                        if ((actualY + yIncrement > 73 && actualY <= 75) || (actualY - yIncrement < 227 && actualY >= 225)) {
                            getImage().setRGB(imageX, imageY, safeBorderColor.getRGB());
                        } else getImage().setRGB(imageX, imageY, safeColor.getRGB());
                    } else if (actualX <= 300 + getBaseRunway().getStripEnd()) {        // Colouring sloped edge between 75m and 105m edges, left side
                        if (actualY <= (75. - (1. / 5.) * (actualX - 210.))) {
                            if ((actualY + yIncrement > (73. - (1. / 5.) * (actualX - 210.)))) {
                                getImage().setRGB(imageX, imageY, safeBorderColor.getRGB());
                            } else getImage().setRGB(imageX, imageY, safeColor.getRGB());
                        } else if (actualY >= (225. + (1. / 5.) * (actualX - 210.))) {
                            if ((actualY - yIncrement < (227. + (1. / 5.) * (actualX - 210.)))) {
                                getImage().setRGB(imageX, imageY, safeBorderColor.getRGB());
                            } else getImage().setRGB(imageX, imageY, safeColor.getRGB());
                        } else {
                            getImage().setRGB(imageX, imageY, cAndGColor.getRGB());
                        }
                    } else if (actualX >= getBaseRunway().getTora() + getBaseRunway().getStripEnd() - 300) {    // Colouring sloped edge between 75m and 105m edges, right side
                        if (actualY <= (75. + (1. / 5.) * (actualX - (getBaseRunway().getTora() + getBaseRunway().getStripEnd() - 150)))) {
                            if ((actualY + yIncrement > (73. + (1. / 5.) * (actualX - (getBaseRunway().getTora() + getBaseRunway().getStripEnd() - 150))))) {
                                getImage().setRGB(imageX, imageY, safeBorderColor.getRGB());
                            } else getImage().setRGB(imageX, imageY, safeColor.getRGB());
                        } else if (actualY >= (225. - (1. / 5.) * (actualX - (getBaseRunway().getTora() + getBaseRunway().getStripEnd() - 150)))) {
                            if ((actualY - yIncrement < (227. - (1. / 5.) * (actualX - (getBaseRunway().getTora() + getBaseRunway().getStripEnd() - 150))))) {
                                getImage().setRGB(imageX, imageY, safeBorderColor.getRGB());
                            } else getImage().setRGB(imageX, imageY, safeColor.getRGB());
                        } else {
                            getImage().setRGB(imageX, imageY, cAndGColor.getRGB());
                        }
                    } else if (actualY > 45 && actualY <= 75) {    //Filling in cleared and graded area
                        getImage().setRGB(imageX, imageY, cAndGColor.getRGB());
                    } else if (actualY < 255 && actualY >= 225) {
                        getImage().setRGB(imageX, imageY, cAndGColor.getRGB());
                    }
                } else {
                    getImage().setRGB(imageX, imageY, cAndGColor.getRGB());
                }
            }
        }
        Graphics graphics = getImage().getGraphics();
        graphics.setColor(Color.YELLOW);
        graphics.setFont(new Font("Arial Black", Font.BOLD, 16));
        graphics.drawString("Cleared and Graded area", 465, 212);
    }

    private void drawStopwayClearedArea(int x, Graphics g) {
        int yIncrement = 300 / getImage().getHeight();            // width of runway in view / height of image in pixels
        int actualY = 0;
        Color safeColor = new Color(138, 43, 226);    //Area outside of clear and graded area, see spec for info
        Color safeBorderColor = Color.BLACK;
        Color cAndGColor = new Color(0, 0, 204);        //Area surrounding the track, see spec for info
        for (int imageX = x; imageX < x + getImage().getWidth() / 5; imageX++) {
            for (int imageY = 0; imageY < getImage().getHeight(); imageY++) {
                actualY = imageY * yIncrement;
                if (actualY <= 75 || actualY >= 225) {                        // 105m edge colouring
                    if ((actualY + yIncrement > 73 && actualY <= 75) || (actualY - yIncrement < 227 && actualY >= 225)) {
                        getImage().setRGB(imageX, imageY, safeBorderColor.getRGB());
                    } else getImage().setRGB(imageX, imageY, safeColor.getRGB());
                } else {
                    getImage().setRGB(imageX, imageY, cAndGColor.getRGB());
                }
            }
        }
    }

    private void drawBaseRunway(Graphics g) {
        int yIncrement = 300 / getImage().getHeight();
        float actualY = 0;
        // From left strip end to right strip end, draw base runway
        for (int imageX = getImage().getWidth() / 5; imageX < 4 * getImage().getWidth() / 5; imageX++) {
            actualY = 0;
            for (int imageY = (int) 125. / yIncrement; actualY < 175; imageY++) {
                actualY = imageY * yIncrement;
                getImage().setRGB(imageX, imageY, Color.RED.getRGB());
            }
        }
    }

    private void drawAvailableLDA(Graphics g) {
        float xIncrement = (float) (getBaseRunway().getTora()) / (3 * getImage().getWidth() / 5); //Length of runway + both strip ends, per pixel.
        float thresholdDisplacement = getBaseRunway().getTora() - getBaseRunway().getLda();
        int yIncrement = 300 / getImage().getHeight();
        float actualY = 0;
        // From left strip end, to end of available runway, draw available runway
        if (getObs().getPosX() < getBaseRunway().getTora() / 2) {
        	// If viewing Left Threshold
        	if (isLeftValue()) {
	            for (int imageX = (int) (getWidth() / 5 + thresholdDisplacement / xIncrement); imageX < 4 * getImage().getWidth() / 5; imageX++) {
	                actualY = 0;
	                for (int imageY = (int) 125. / yIncrement; actualY < 175; imageY++) {
	                    actualY = imageY * yIncrement;
	                    if (imageX >= getImage().getWidth() / 5 + (getBaseRunway().getTora() - getAvailableRunway().getLda()) / xIncrement) {
	                        getImage().setRGB(imageX, imageY, Color.GRAY.getRGB());
	                    }
	                }
	            }
        	}
        	// Else Right threshold viewed
        	else {
        		for (int imageX = (int) (4*getWidth()/5 - thresholdDisplacement / xIncrement); imageX >= getImage().getWidth()/5; imageX--) {
        			actualY = 0;
	                for (int imageY = (int) 125. / yIncrement; actualY < 175; imageY++) {
	                    actualY = imageY * yIncrement;
	                    if (imageX >= 4*getWidth()/5 - (getBaseRunway().getDisplacementThreshold() + getAvailableRunway().getLda()) / xIncrement) {
	                        getImage().setRGB(imageX, imageY, Color.GRAY.getRGB());
	                    }
	                }
        		}
        	}
            if (isCalculatedDimensions())
                drawDimension(getAvailableRunway().getLda() + "m", (int) (getWidth() / 5 + (getBaseRunway().getTora() - getAvailableRunway().getLda()) / xIncrement), 230, (int) (3 * getImage().getWidth() / 5 - (getBaseRunway().getTora() - getAvailableRunway().getLda()) / xIncrement), Color.GRAY, false, g);
        } else {
        	// If viewing Left Threshold
        	if (isLeftValue()) {
	            for (int imageX = 4 * getImage().getWidth() / 5; imageX >= invertX(getBaseRunway().getStripEnd() + getBaseRunway().getDisplacementThreshold()); imageX--) {
	                actualY = 0;
	                for (int imageY = (int) 125. / yIncrement; actualY < 175; imageY++) {
	                    actualY = imageY * yIncrement;
	                    if (convertX(imageX) <= getAvailableRunway().getLda() + getAvailableRunway().getDisplacementThreshold() + getAvailableRunway().getStripEnd()) {
	                        getImage().setRGB(imageX, imageY, Color.GRAY.getRGB());
	                    }
	                }
	            }
        	}
        	// Else Right threshold viewed
        	else {
        		for (int imageX = (int) invertX(getBaseRunway().getStripEnd()); imageX <= 4*getWidth()/5; imageX++) {
        			actualY = 0;
	                for (int imageY = (int) 125. / yIncrement; actualY < 175; imageY++) {
	                    actualY = imageY * yIncrement;
	                    if (imageX <= invertX(getBaseRunway().getStripEnd() + getAvailableRunway().getLda())) {
	                        getImage().setRGB(imageX, imageY, Color.GRAY.getRGB());
	                    }
	                }
        		}
        	}
            if (isCalculatedDimensions())
                drawDimension(getAvailableRunway().getLda() + "m", (int) (getWidth() / 5 + thresholdDisplacement / xIncrement), 230, (int) (getAvailableRunway().getLda() / xIncrement), Color.GRAY, false, g);
        }

    }

    private void drawAvailableTora(Graphics g) {
        float xIncrement = ((float) (getBaseRunway().getTora()) / (3 * getImage().getWidth() / 5)); //Length of runway + both strip ends, per pixel.
        int yIncrement = 300 / getImage().getHeight();
        float actualY = 0;
        // From left strip end, to end of available runway, draw available runway
        if (getObs().getPosX() < getBaseRunway().getTora() / 2) {
        	// If Left threshold being viewed
        	if (isLeftValue()) {
	            for (int imageX = getImage().getWidth() / 5; imageX <= 4 * getImage().getWidth() / 5; imageX++) {
	                actualY = 0;
	                for (int imageY = (int) 125. / yIncrement; actualY < 175; imageY++) {
	                    actualY = imageY * yIncrement;
	                    if (imageX >= getImage().getWidth() / 5 + (getBaseRunway().getTora() - getAvailableRunway().getTora()) / xIncrement) {
	                        getImage().setRGB(imageX, imageY, Color.YELLOW.getRGB());
	                    }
	                }
	            }
        	} 
        	// Else Right Threshold being viewed
        	else {
        		for (int imageX = 4*getWidth()/5; imageX >= getImage().getWidth()/5; imageX--) {
        			actualY = 0;
	                for (int imageY = (int) 125. / yIncrement; actualY < 175; imageY++) {
	                    actualY = imageY * yIncrement;
	                    if (imageX >= (4*getWidth()/5) - (getAvailableRunway().getTora()) / xIncrement) {
	                        getImage().setRGB(imageX, imageY, Color.YELLOW.getRGB());
	                    }
	                }
        		}
        	}
        	if (isCalculatedDimensions())
                drawDimension(getAvailableRunway().getTora() + "m", (int) invertX(getBaseRunway().getStripEnd() + getBaseRunway().getTora() + getAvailableRunway().getTora()),
                		260, (int) ((getAvailableRunway().getTora()) / xIncrement), Color.YELLOW, false, g);
        } else {
        	// If left threshold being viewed
        	if (isLeftValue()) {
	        	for (int imageX = getImage().getWidth() / 5; imageX <= 4 * getImage().getWidth() / 5; imageX++) {
	                actualY = 0;
	                for (int imageY = (int) 125. / yIncrement; actualY < 175; imageY++) {
	                    actualY = imageY * yIncrement;
	                    if (convertX(imageX) <= getAvailableRunway().getTora() + getAvailableRunway().getStripEnd()) {
	                        getImage().setRGB(imageX, imageY, Color.YELLOW.getRGB());
	                    }
	                }
	            }
        	}
        	// Else Right Threshold being viewed
        	else {
	        	for (int imageX = 4*getWidth()/5; imageX >= getImage().getWidth()/5; imageX--) {
	                actualY = 0;
	                for (int imageY = (int) 125. / yIncrement; actualY < 175; imageY++) {
	                    actualY = imageY * yIncrement;
	                    if (convertX(imageX) <=  getAvailableRunway().getTora() + getBaseRunway().getStripEnd()) {
	                        getImage().setRGB(imageX, imageY, Color.YELLOW.getRGB());
	                    }
	                }
	            }
        	}
//            for (int imageX = 4 * getImage().getWidth() / 5; imageX >= getImage().getWidth() / 5; imageX--) {
//                actualX += xIncrement;
//                actualY = 0;
//                for (int imageY = (int) 125. / yIncrement; actualY < 175; imageY++) {
//                    actualY = imageY * yIncrement;
//                    if (imageX >= getImage().getWidth() / 5 + (getBaseRunway().getTora() - getAvailableRunway().getTora()) / xIncrement) {
//                    //if (actualX > (getBaseRunway().getStripEnd() + getBaseRunway().getTora() - getAvailableRunway().getTora())) {
//                        getImage().setRGB(imageX, imageY, Color.YELLOW.getRGB());
//                    }
//                }
//            }
            if (isCalculatedDimensions())
                drawDimension(getAvailableRunway().getTora() + "m", (int) invertX(getBaseRunway().getStripEnd()), 260,
                		(int) (getAvailableRunway().getTora() / xIncrement), Color.YELLOW, false, g);
        }
        //drawCenterline(g);
    }

    private void drawCenterline(Graphics g) {
        int count = 0;
        boolean isPainting = false;
        for (int imageX = 285; imageX <= getImage().getWidth() - 280; imageX++) {
            if (count % 16 == 0) isPainting = !isPainting;
            if (isPainting && (15 * (count / 15) + 15 < getImage().getWidth() - 150)) {
                for (int imageY = getImage().getHeight() / 2 - 2; imageY <= getImage().getHeight() / 2 + 2; imageY++) {
                    getImage().setRGB(imageX, imageY, Color.LIGHT_GRAY.getRGB());
                }
            }
            count++;
        }
    }

    private void drawObstacle(Graphics g) {
        //float xIncrement = ( (float) (2*getBaseRunway().getStripEnd()) + getBaseRunway().getTora() ) / getImage().getWidth(); //Length of runway + both strip ends, per pixel.
        float yIncrement = (float) (getImage().getHeight() / 300.);    //Height of image / real with of view (300m)
        Graphics graphics = getImage().getGraphics();
        graphics.setColor(Color.ORANGE);
        double obsPosX = getObs().getPosX();        // Obstruction position on view
        double obsPosY = getObs().getPosY();
        double obsWid = getObs().getWidth();        //Width of obstruction
        double obsLen = getObs().getLength();    //Length of obstruction

        int[] xPoints = new int[3];
        xPoints[0] = (int) invertX((int) (obsPosX + obsLen));
        xPoints[1] = (int) invertX((int) (obsPosX + obsLen));
        xPoints[2] = (int) invertX((int) (obsPosX));

        int[] yPoints = new int[3];
        yPoints[0] = (int) ((int) obsPosY / yIncrement);
        yPoints[1] = (int) ((int) (obsPosY + obsWid) / yIncrement);
        yPoints[2] = (int) ((int) (obsPosY + obsWid / 2) / yIncrement);

        graphics.fillPolygon(xPoints, yPoints, 3);
        g.drawImage(getImage(), 0, 0, null);
    }

    private void drawBlastAllowance(Graphics g) {
        if (getObs().getPosX() < (getBaseRunway().getTora() / 2)) {
            float realXStart = getObs().getHeighestX();
            for (int imageX = (int) (invertX(getObs().getHeighestX())); convertX(imageX) <= realXStart + 300; imageX++) {
                for (int imageY = 145; imageY <= 155; imageY++) {
                    getImage().setRGB(imageX, imageY, new Color(153, 76, 0).getRGB());
                }
            }
        } else {
            float realXStart = getObs().getPosX();
            for (int imageX = (int) (invertX(getObs().getPosX())); convertX(imageX) >= realXStart - 300; imageX--) {
                for (int imageY = 145; imageY <= 155; imageY++) {
                    getImage().setRGB(imageX, imageY, new Color(153, 76, 0).getRGB());
                }
            }
        }
    }

    private void drawRESA(Graphics g) {
    	if (getObs().getPosX() < (getBaseRunway().getTora()/2)) {
    		float realXStart = getObs().getHeighestX();
    		for (int imageX = (int) (invertX(getObs().getHeighestX())); convertX(imageX) <= realXStart + getBaseRunway().getResa(); imageX++) {
    			for (int imageY = 145; imageY <= 155; imageY++) {
    				getImage().setRGB(imageX, imageY, new Color(50, 50, 50).getRGB());
    			}
    		}
    	} else {
    		float realXStart = getObs().getPosX();
    		for (int imageX = (int) (invertX(getObs().getPosX())); convertX(imageX) >= realXStart - getBaseRunway().getResa(); imageX--) {
    			for (int imageY = 145; imageY <= 155; imageY++) {
    				getImage().setRGB(imageX, imageY, new Color(50, 50, 50).getRGB());
    			}
    		}
    	}
    }

    private class ImageMouseListener implements MouseListener, MouseMotionListener {

        private float stripXIncrement = getBaseRunway().getStripEnd() / (getImage().getWidth() / 5);
        private float runwayXIncrement = getBaseRunway().getTora() / (3 * getImage().getWidth() / 5);
        private float xIncrement = ((float) (2 * getBaseRunway().getStripEnd()) + getBaseRunway().getTora()) / getImage().getWidth(); //Length of runway + both strip ends, per pixel.
        private int yIncrement = 300 / getImage().getHeight();
        private boolean isObsSelected = false;
        private GUIFrame frame;

        public ImageMouseListener(GUIFrame frame) {
            this.frame = frame;
        }

        //For this moment, remains bank.
        public void mouseMoved(MouseEvent e) {
        }

        @Override
        public void mouseClicked(MouseEvent arg0) {
        }

        @Override
        public void mouseEntered(MouseEvent arg0) {

        }

        @Override
        public void mouseExited(MouseEvent arg0) {
        }

        /*
         * If pressed within Obstruction's select box, enable dragging.
         */
        public void mousePressed(MouseEvent e) {
        	int yPos = 0;
        	int xPos = 0;
        	if (!rotate) { yPos = e.getY() - (getHeight()-getImage().getHeight())/2;
        		xPos = e.getX();
        	}
        	else {
        		double sin = Math.abs(Math.sin(Math.toRadians((getBaseRunway().getDirection()-9)*10))),
        		           cos = Math.abs(Math.cos(Math.toRadians((getBaseRunway().getDirection()-9)*10)));
        		
        		yPos = (int) (((getWidth()-getImage().getWidth())/2 - e.getX())*sin - ((getHeight()-getImage().getHeight())/2 - e.getY())*cos);
        	}
            if ((yPos >= (float) getObs().getPosY() / yIncrement) && (yPos <= ((float) getObs().getPosY() + (float) getObs().getWidth()) / yIncrement)) {
                if ((e.getX() >= invertX(getObs().getPosX())) && (e.getX() <= invertX(getObs().getPosX() + getObs().getLength()))) {
                    isObsSelected = true;
                }
            }
        }

        /*
         * Finds x and y position of mouse during dragging, calls control for obstruction update.
         * If obstruct being dragged out of bounds, place back within bounds.
         */
        public void mouseDragged(MouseEvent e) {
        	int yPos = e.getY() - (getHeight()-getImage().getHeight())/2;
            if (isObsSelected) {
            	int newPosX = 0;
            	int newPosY = 0;
            	if (e.getX() >= 0 && e.getX() < getImage().getWidth()) {
            		newPosX = (int) convertX(e.getX());
            	}
            	else if (e.getX() < 0) newPosX = 0;
            	else newPosX = (int) (convertX(getWidth()-1));
            	
            	if (yPos >= 0 && yPos < getImage().getHeight() - (getObs().getWidth()/yIncrement) ) {
            		newPosY = (int) convertY(yPos*yIncrement);
            	}
            	else if (yPos < 0) newPosY = 0;
            	else newPosY = getImage().getHeight()*yIncrement - getObs().getWidth();
                frame.updateViews(newPosX, newPosY);

            }
        }

        @Override
        public void mouseReleased(MouseEvent arg0) {
            isObsSelected = false;
            new Notification("Obstacle now at \nx:" + getObs().getPosX() + "\ny: " + getObs().getPosY());
        }

    }
}
