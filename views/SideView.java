package views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

import model.Obstacle;
import model.Runway;

public class SideView extends ViewPanel {

	public SideView(int width, int height, Obstacle obs, Runway r,
			GUIFrame frame) {
		super(width, height, obs, r, frame);
		ImageMouseListener l = new ImageMouseListener(frame); // Listener will
																// send updated
																// Obstruction
																// position to
																// control.
		this.addMouseListener(l);
		this.addMouseMotionListener(l);
	}

	@Override
	void drawView(Graphics g) {
		drawBackground(g); // Used to refresh view with black background
		drawDirection(g);
		displayCalculationType(g);
		drawBaseRunway(g); // This is coloured red, signifies unusable Runway
		drawAvailableTora(g);
		drawAvailableLDA(g); // This is coloured grey, and has center line.
								// Drawn above base runway
		drawDisplacedThreshold(g);
		drawEndOfTora(g);
		drawRunwayBorder(g);
		drawBaseStopway(g);
		drawBaseClearway(g);
		drawAvailableStopway(g);
		drawAvailableClearway(g);
		drawObstacle(g); // Currently just a orange outlined triangle. To be
							// altered to a plane sprite later.
        if (getAvailableRunway().isObstrusted()) {
            drawBlastAllowance(g);
            drawRESA(g);
            drawTOCS(g);
        }
		displayObsPosX(g);
	}

	private void drawBaseStopway(Graphics g) {
		float xIncrement = (float) getBaseRunway().getStripEnd()
				/ (getWidth() / 5); // Length of runway + both strip ends, per
									// pixel.
		int yIncrement = 300 / getHeight();
		int stopway = getBaseRunway().getAsda() - getBaseRunway().getTora();
		if (stopway > 0) {
        	// If Left Threshold viewed
        	if (isLeftValue()) {
	            for (int imageX = 4 * getWidth() / 5; imageX < 4 * getWidth() / 5 + stopway / xIncrement; imageX++) {
	                for (int imageY = (int) (175. / yIncrement); imageY <= (int) (200. / yIncrement); imageY++) {
	                    getImage().setRGB(imageX, imageY, new Color(102, 0, 0).getRGB());
	                }
	            }
        	}
        	// Else right threshold viewed
        	else {
        		for (int imageX = getWidth() / 5; imageX > getWidth() / 5 - stopway / xIncrement; imageX--) {
	                for (int imageY = (int) (175. / yIncrement); imageY <= (int) (200. / yIncrement); imageY++) {
	                    getImage().setRGB(imageX, imageY, new Color(102, 0, 0).getRGB());
	                }
	            }
        	}
        }
	}

	private void drawAvailableStopway(Graphics g) {
		float xIncrement = (float) getBaseRunway().getStripEnd()
				/ (getWidth() / 5); // Length of runway + both strip ends, per
									// pixel.
		int yIncrement = 300 / getHeight();
		int stopway = getAvailableRunway().getAsda()
				- getAvailableRunway().getTora();
		if (stopway > 0) {
        	if (isLeftValue()) {
	            for (int imageX = 4 * getWidth() / 5; imageX < 4 * getWidth() / 5 + stopway / xIncrement; imageX++) {
	                for (int imageY = (int) (175. / yIncrement); imageY <= (int) (200. / yIncrement); imageY++) {
	                    getImage().setRGB(imageX, imageY, Color.PINK.getRGB());
	                }
	            }
        	}
        	// Else right threshold viewed
        	else {
        		for (int imageX = getWidth() / 5; imageX > getWidth() / 5 - stopway / xIncrement; imageX--) {
	                for (int imageY = (int) (175. / yIncrement); imageY <= (int) (200. / yIncrement); imageY++) {
	                    getImage().setRGB(imageX, imageY, Color.PINK.getRGB());
	                }
	            }
        	}
            if (isCalculatedDimensions())
                drawDimension(stopway + "m", 4 * getWidth() / 5, 200, (int) (stopway / xIncrement), Color.PINK, false, g);
        }
	}

	private void drawBaseClearway(Graphics g) {
		float xIncrement = (float) getBaseRunway().getStripEnd()
				/ (getWidth() / 5); // Length of runway + both strip ends, per
									// pixel.
		int yIncrement = 300 / getHeight();
		int stopway = getBaseRunway().getToda() - getBaseRunway().getTora();
		if (stopway > 0) {
        	// If Left Threshold Viewed
        	if (isLeftValue()) {
	            for (int imageX = 4 * getWidth() / 5; imageX < 4 * getWidth() / 5 + stopway / xIncrement; imageX++) {
	                getImage().setRGB(imageX, (int) (175. / yIncrement), Color.RED.getRGB());
	                getImage().setRGB(imageX, (int) (200. / yIncrement), Color.RED.getRGB());
	            }
	            for (int imageY = 175; imageY <= 200; imageY++) {
	                getImage().setRGB((int) (4 * getWidth() / 5 + stopway / xIncrement - 1), imageY, Color.RED.getRGB());
	            }
        	}
        	// Else right threshold viewed
        	else {
        		for (int imageX = getWidth() / 5; imageX > getWidth() / 5 - stopway / xIncrement; imageX--) {
	                getImage().setRGB(imageX, (int) (175. / yIncrement), Color.RED.getRGB());
	                getImage().setRGB(imageX, (int) (200. / yIncrement), Color.RED.getRGB());
	            }
	            for (int imageY = 175; imageY <= 200; imageY++) {
	                getImage().setRGB((int) (getWidth() / 5 - stopway / xIncrement + 1), imageY, Color.RED.getRGB());
	            }
        	}
        }
	}

	private void drawAvailableClearway(Graphics g) {
		float xIncrement = (float) getBaseRunway().getStripEnd()
				/ (getWidth() / 5); // Length of runway + both strip ends, per
									// pixel.
		int yIncrement = 300 / getHeight();
		int stopway = getAvailableRunway().getToda()
				- getAvailableRunway().getTora();
		if (stopway > 0) {
        	if (isLeftValue()) {
	            for (int imageX = 4 * getWidth() / 5; imageX < 4 * getWidth() / 5 + stopway / xIncrement; imageX++) {
	                getImage().setRGB(imageX, (int) (175. / yIncrement), Color.PINK.getRGB());
	                getImage().setRGB(imageX, (int) (200. / yIncrement), Color.PINK.getRGB());
	            }
	            for (int imageY = 175; imageY <= 200; imageY++) {
	                getImage().setRGB((int) (4 * getWidth() / 5 + stopway / xIncrement - 1), imageY, Color.PINK.getRGB());
	            }
        	}
        	// Else right threshold viewed
        	else {
        		for (int imageX = getWidth() / 5; imageX > getWidth() / 5 - stopway / xIncrement; imageX--) {
	                getImage().setRGB(imageX, (int) (175. / yIncrement), Color.PINK.getRGB());
	                getImage().setRGB(imageX, (int) (200. / yIncrement), Color.PINK.getRGB());
	            }
	            for (int imageY = 175; imageY <= 200; imageY++) {
	                getImage().setRGB((int) (getWidth() / 5 - stopway / xIncrement + 1), imageY, Color.PINK.getRGB());
	            }
        	}
            if (isCalculatedDimensions())
                drawDimension(stopway + "m", 4 * getWidth() / 5, 240, (int) (stopway / xIncrement), Color.PINK, false, g);
        }
	}

	private void drawDisplacedThreshold(Graphics g) {
		float xIncrement = ((float) (2 * getBaseRunway().getStripEnd()) + getBaseRunway()
				.getTora()) / (3 * getWidth() / 5); // Length of runway + both
													// strip ends, per pixel.
		int yIncrement = 300 / getHeight();
		int actualX = getBaseRunway().getTora() - getBaseRunway().getLda();
		if (isLeftValue()) {
	        for (int imageX = getWidth() / 5 + (int) ((actualX - 1) / xIncrement); imageX <= getWidth() / 5 + (actualX + 1) / xIncrement; imageX++) {
	            for (int imageY = (int) (175. / yIncrement); imageY <= (int) (200. / yIncrement); imageY++) {
	                getImage().setRGB(imageX, imageY, Color.WHITE.getRGB());
	            }
	        }
        }
        // Else Right Threshold Viewed
        else {
        	for (int imageX = 4*getWidth() / 5 - (int) ((actualX - 1) / xIncrement); imageX >= 4*getWidth() / 5 - (actualX + 1) / xIncrement; imageX--) {
	            for (int imageY = (int) (175. / yIncrement); imageY <= (int) (200. / yIncrement); imageY++) {
	                getImage().setRGB(imageX, imageY, Color.WHITE.getRGB());
	            }
	        }
        }
	}

	private void drawEndOfTora(Graphics g) {
		int yIncrement = 300 / getHeight();
		if (isLeftValue()) {
	        if (getObs().getPosX() > getBaseRunway().getTora() / 2) {
				int actualX = getAvailableRunway().getTora() + getAvailableRunway().getStripEnd();
				for (int imageX = (int) invertX(actualX); imageX <= invertX(actualX) + 1; imageX++) {
					for (int imageY = (int) (175. / yIncrement); imageY <= (int) (200. / yIncrement); imageY++) {
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
					for (int imageY = (int) (175. / yIncrement); imageY <= (int) (200. / yIncrement); imageY++) {
						if (actualX <= getBaseRunway().getStripEnd() + getBaseRunway().getTora())
							getImage().setRGB(imageX, imageY, Color.YELLOW.getRGB());
					}
				}
			}
        }
	}

	private void drawBackground(Graphics g) {
		for (int imageX = 0; imageX < getWidth(); imageX++) {
			for (int imageY = 0; imageY < getHeight(); imageY++) {
				if (imageY < getHeight() / 2 + 25)
					getImage().setRGB(imageX, imageY,
							new Color(0, 0, 102).getRGB());
				else
					getImage().setRGB(imageX, imageY,
							new Color(0, 102, 0).getRGB());
			}
		}
	}

	private void drawRunwayBorder(Graphics g) {
		float xIncrement = ((float) (2 * getBaseRunway().getStripEnd()) + getBaseRunway()
				.getTora()) / (3 * getWidth() / 5); // Length of runway + both
													// strip ends, per pixel.
		int yIncrement = 300 / getHeight();
		for (int imageX = getWidth() / 5; imageX < 4 * getWidth() / 5; imageX++) {
			getImage().setRGB(imageX, (int) (175. / yIncrement),
					Color.BLACK.getRGB());
			getImage().setRGB(imageX, (int) (175. / yIncrement) - 1,
					Color.BLACK.getRGB());
			getImage().setRGB(imageX, (int) (200. / yIncrement),
					Color.BLACK.getRGB());
			getImage().setRGB(imageX, (int) (200. / yIncrement) + 1,
					Color.BLACK.getRGB());
		}
		for (int imageY = (int) (175. / yIncrement); imageY <= (int) (200. / yIncrement); imageY++) {
			getImage().setRGB(getWidth() / 5, imageY, Color.BLACK.getRGB());
			getImage().setRGB(getWidth() / 5 - 1, imageY, Color.BLACK.getRGB());
			getImage().setRGB(4 * getWidth() / 5, imageY, Color.BLACK.getRGB());
			getImage().setRGB(4 * getWidth() / 5 + 1, imageY,
					Color.BLACK.getRGB());
		}
	}

	private void drawBaseRunway(Graphics g) {
		int yIncrement = 300 / getHeight();
		float actualY = 0;
		// From left strip end to right strip end, draw base runway
		for (int imageX = getWidth() / 5; imageX < 4 * getWidth() / 5; imageX++) {
			actualY = 0;
			for (int imageY = (int) 175. / yIncrement; actualY < 200; imageY++) {
				actualY = imageY * yIncrement;
				getImage().setRGB(imageX, imageY, Color.RED.getRGB());
			}
		}
	}

	private void drawAvailableLDA(Graphics g) {
		float xIncrement = ((float) getBaseRunway()
				.getTora() / (3 * getWidth() / 5)); // Length of runway + both
													// strip ends, per pixel.
		float thresholdDisplacement = getBaseRunway().getTora()
				- getBaseRunway().getLda();
		int yIncrement = 300 / getHeight();
		float actualX = 0;
		float actualY = 0;
		// From left strip end, to end of available runway, draw available
		// runway
		if (getObs().getPosX() < getBaseRunway().getTora() / 2) {
        	// If viewing Left Threshold
        	if (isLeftValue()) {
	            for (int imageX = (int) (getWidth() / 5 + thresholdDisplacement / xIncrement); imageX < 4 * getWidth() / 5; imageX++) {
	                actualY = 0;
	                for (int imageY = (int) 175. / yIncrement; actualY < 200; imageY++) {
	                    actualY = imageY * yIncrement;
	                    if (imageX >= getWidth() / 5 + (getBaseRunway().getTora() - getAvailableRunway().getLda()) / xIncrement) {
	                        getImage().setRGB(imageX, imageY, Color.GRAY.getRGB());
	                    }
	                }
	            }
        	}
        	// Else Right threshold viewed
        	else {
        		for (int imageX = (int) (4*getWidth()/5 - thresholdDisplacement / xIncrement); imageX >= getWidth()/5; imageX--) {
        			actualY = 0;
	                for (int imageY = (int) 175. / yIncrement; actualY < 200; imageY++) {
	                    actualY = imageY * yIncrement;
	                    if (imageX >= 4*getWidth()/5 - (getBaseRunway().getDisplacementThreshold() + getAvailableRunway().getLda()) / xIncrement) {
	                        getImage().setRGB(imageX, imageY, Color.GRAY.getRGB());
	                    }
	                }
        		}
        	}
            if (isCalculatedDimensions())
                drawDimension(getAvailableRunway().getLda() + "m", (int) (getWidth() / 5 + (getBaseRunway().getTora() - getAvailableRunway().getLda()) / xIncrement), 230, (int) (3 * getWidth() / 5 - (getBaseRunway().getTora() - getAvailableRunway().getLda()) / xIncrement), Color.GRAY, false, g);
        } else {
        	// If viewing Left Threshold
        	if (isLeftValue()) {
	            for (int imageX = 4 * getWidth() / 5; imageX >= invertX(getBaseRunway().getStripEnd() + getBaseRunway().getDisplacementThreshold()); imageX--) {
	                actualY = 0;
	                for (int imageY = (int) 175. / yIncrement; actualY < 200; imageY++) {
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
	                for (int imageY = (int) 175. / yIncrement; actualY < 200; imageY++) {
	                    actualY = imageY * yIncrement;
	                    if (imageX <= invertX(getBaseRunway().getStripEnd() + getAvailableRunway().getLda())) {
	                        getImage().setRGB(imageX, imageY, Color.GRAY.getRGB());
	                    }
	                }
        		}
        	}
		}
	}

	private void drawAvailableTora(Graphics g) {
		float xIncrement = ((float) (getBaseRunway()
				.getTora()) / (3 * getWidth() / 5)); // Length of runway + both
													// strip ends, per pixel.
		int yIncrement = 300 / getHeight();
		float actualX = 0;
		float actualY = 0;
		// From left strip end, to end of available runway, draw available
		// runway
		if (getObs().getPosX() < getBaseRunway().getTora() / 2) {
        	// If Left threshold being viewed
        	if (isLeftValue()) {
	            for (int imageX = getWidth() / 5; imageX <= 4 * getWidth() / 5; imageX++) {
	                actualY = 0;
	                for (int imageY = (int) 175. / yIncrement; actualY < 200; imageY++) {
	                    actualY = imageY * yIncrement;
	                    if (imageX >= getWidth() / 5 + (getBaseRunway().getTora() - getAvailableRunway().getTora()) / xIncrement) {
	                        getImage().setRGB(imageX, imageY, Color.YELLOW.getRGB());
	                    }
	                }
	            }
        	} 
        	// Else Right Threshold being viewed
        	else {
        		for (int imageX = 4*getWidth()/5; imageX >= getWidth()/5; imageX--) {
        			actualY = 0;
	                for (int imageY = (int) 175. / yIncrement; actualY < 200; imageY++) {
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
	        	for (int imageX = getWidth() / 5; imageX <= 4 * getWidth() / 5; imageX++) {
	                actualY = 0;
	                for (int imageY = (int) 175. / yIncrement; actualY < 200; imageY++) {
	                    actualY = imageY * yIncrement;
	                    if (convertX(imageX) <= getAvailableRunway().getTora() + getAvailableRunway().getStripEnd()) {
	                        getImage().setRGB(imageX, imageY, Color.YELLOW.getRGB());
	                    }
	                }
	            }
        	}
        	// Else Right Threshold being viewed
        	else {
	        	for (int imageX = 4*getWidth()/5; imageX >= getWidth()/5; imageX--) {
	                actualY = 0;
	                for (int imageY = (int) 175. / yIncrement; actualY < 200; imageY++) {
	                    actualY = imageY * yIncrement;
	                    if (convertX(imageX) <=  getAvailableRunway().getTora() + getBaseRunway().getStripEnd()) {
	                        getImage().setRGB(imageX, imageY, Color.YELLOW.getRGB());
	                    }
	                }
	            }
        	}
		// drawCenterline(g);
        }
	}

	private void drawObstacle(Graphics g) {
		float yIncrement = (float) (getHeight() / 300.); // Height of image /
															// real with of view
															// (300m)
		Graphics graphics = getImage().getGraphics();
		graphics.setColor(Color.ORANGE);
		double obsPosX = getObs().getPosX(); // Obstruction position on view
		double obsHeight = getObs().getHeight();
		double obsLen = getObs().getLength(); // Length of obstruction

		int[] xPoints = new int[3];
		xPoints[0] = (int) invertX((int) (obsPosX + obsLen));
		xPoints[1] = (int) invertX((int) (obsPosX + obsLen));
		xPoints[2] = (int) invertX((int) (obsPosX));

		int[] yPoints = new int[3];
		yPoints[0] = (int) (175 - obsHeight / yIncrement);
		yPoints[1] = (int) (175 / yIncrement);
		yPoints[2] = (int) (175 / yIncrement);

		graphics.fillPolygon(xPoints, yPoints, 3);
		g.drawImage(getImage(), 0, 0, null);
	}

	private void drawBlastAllowance(Graphics g) {
		if (getObs().getPosX() < (getBaseRunway().getTora() / 2)) {
			float realXStart = getObs().getHeighestX();
			for (int imageX = (int) (invertX(getObs().getHeighestX())); convertX(imageX) <= realXStart + 300; imageX++) {
				for (int imageY = 164; imageY <= 174; imageY++) {
					getImage().setRGB(imageX, imageY,
							new Color(153, 76, 0).getRGB());
				}
			}
		} else {
			float realXStart = getObs().getPosX();
			for (int imageX = (int) (invertX(getObs().getPosX())); convertX(imageX) >= realXStart - 300; imageX--) {
				for (int imageY = 164; imageY <= 174; imageY++) {
					getImage().setRGB(imageX, imageY,
							new Color(153, 76, 0).getRGB());
				}
			}
		}
	}

	private void drawRESA(Graphics g) {
		if (getObs().getPosX() < (getBaseRunway().getTora() / 2)) {
			float realXStart = getObs().getHeighestX();
			for (int imageX = (int) (invertX(getObs().getHeighestX())); convertX(imageX) <= realXStart
					+ getBaseRunway().getResa(); imageX++) {
				for (int imageY = 164; imageY <= 174; imageY++) {
					getImage().setRGB(imageX, imageY,
							new Color(50, 50, 50).getRGB());
				}
			}
		} else {
			float realXStart = getObs().getPosX();
			for (int imageX = (int) (invertX(getObs().getPosX())); convertX(imageX) >= realXStart
					- getBaseRunway().getResa(); imageX--) {
				for (int imageY = 164; imageY <= 174; imageY++) {
					getImage().setRGB(imageX, imageY,
							new Color(50, 50, 50).getRGB());
				}
			}
		}
	}

	private void drawTOCS(Graphics g) {
		float realXStart = getObs().getHeighestX();
		int imageY = (int) invertY(175 - getObs().getHeight());
		if (getObs().getPosX() < getBaseRunway().getTora() / 2) {
			for (int imageX = (int) (invertX(getObs().getHeighestX())); convertX(imageX) < realXStart + (float) (getObs().getHeight()/.02); imageX++) {
				imageY = (int) invertY((int) ((175 - getObs().getHeight()) + 0.02*(convertX(imageX)-realXStart)));
				if (imageX < getWidth()) {
					getImage().setRGB(imageX, imageY, Color.WHITE.getRGB());
				}
			}
		} else {
			for (int imageX = (int) (invertX(getObs().getHeighestX())); convertX(imageX) > realXStart - (float) (getObs().getHeight()/.02); imageX--) {
				imageY = (int) invertY((int) ((175 - getObs().getHeight()) - 0.02*(convertX(imageX)-realXStart)));
				if (imageX >= 0)
				getImage().setRGB(imageX, imageY, Color.WHITE.getRGB());
			}
		}
	}
	
	private class ImageMouseListener implements MouseListener,
			MouseMotionListener {

		private boolean isObsSelected = false;
		private GUIFrame frame;

		public ImageMouseListener(GUIFrame frame) {
			this.frame = frame;
		}

		// For this moment, remains bank.
		public void mouseMoved(MouseEvent e) {
		}

		public void mouseClicked(MouseEvent arg0) {
		}

		public void mouseEntered(MouseEvent arg0) {

		}

		public void mouseExited(MouseEvent arg0) {
		}

		/*
		 * If pressed within Obstruction's select box, enable dragging.
		 */
		public void mousePressed(MouseEvent e) {
			if ((e.getY() >= (175 - getObs().getHeight()) && (e.getY() <= 175))) {
				if ((e.getX() >= invertX(getObs().getPosX()))
						&& (e.getX() <= invertX(getObs().getPosX()
								+ getObs().getLength()))) {
					isObsSelected = true;
				}
			}
		}

		/*
		 * Finds x position of mouse during dragging, calls control for
		 * obstruction update. If obstruct being dragged out of bounds, place
		 * back within bounds.
		 */
		public void mouseDragged(MouseEvent e) {
			if (isObsSelected) {
				int newPosX = 0;
				int newPosY = getObs().getPosY();
				if (e.getX() >= 0 && e.getX() < getWidth()) {
					newPosX = (int) convertX(e.getX());
				} else if (e.getX() < 0)
					newPosX = 0;
				else
					newPosX = (int) (convertX(getWidth() - 1));

				frame.updateViews(newPosX, newPosY);
			}
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			isObsSelected = false;
		}

	}

}
