package views;

import controller.UserController;
import model.Obstacle;
import model.Runway;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.*;

public class DimensionTogglePanel extends JPanel {

	private JCheckBox baseDimension;
	private JCheckBox calculatedDimension;
	private CheckBoxListener base;
	private CheckBoxListener calc;
	
	private GUIFrame frame;
    private ViewPanel top, side;

	private UserController controller;

    private ObstacleAdderSubPanel obstaclePanel;
    
    //Will be used to pop up breakdown of calculations and Rotatable Top Down View in new frames.
    private PopUpWindowSubPanel windowPanel;

    public DimensionTogglePanel(UserController controller, ViewPanel top, ViewPanel side, GUIFrame frame) {
        this.frame = frame;
		this.controller = controller;
        this.top = top;
        this.side = side;
        this.obstaclePanel = new ObstacleAdderSubPanel();

        baseDimension = new JCheckBox("Display Base Dimensions");
		calculatedDimension = new JCheckBox("Display Calculated Parameter Dimensions");
		windowPanel = new PopUpWindowSubPanel();

		base = new CheckBoxListener(baseDimension);
		calc = new CheckBoxListener(calculatedDimension);
		
		baseDimension.addActionListener(base);
		calculatedDimension.addActionListener(calc);
		
		this.setLayout(new GridLayout(5,1,0,0));
		
		this.add(new RunwayComboBox());
		this.add(baseDimension);
		this.add(calculatedDimension);
        this.add(obstaclePanel);
        this.add(windowPanel);
    }

    public void update(Obstacle o) {
        obstaclePanel.update(o.getPosX(), o.getPosY());
    }

    private class RunwayComboBox extends JComboBox<String> {
		
		public RunwayComboBox() {
			super();
			for (String runway : controller.getAllRunways()) {
				this.addItem(runway);
			}
            this.addActionListener(new RunwayBoxListener(this));
		}
		
	}
	
	private class ObstacleComboBox extends JComboBox<String> {
		
		public ObstacleComboBox() {
			super();
			for (String ob : controller.getAllObstacles()) {
				this.addItem(ob);
			}
		}
		
	}
	
	private class RunwayBoxListener implements ActionListener {

		JComboBox<String> box;
		
		public RunwayBoxListener(JComboBox<String> box) {
			this.box = box;
		}
		
		public void actionPerformed(ActionEvent e) {
            String name = (String) box.getSelectedItem();
            Runway r = controller.getRunway(name);
            if (Integer.parseInt(name.substring(0, 2)) < 19) {
                frame.setLeftValue(true);
            } else {
                frame.setLeftValue(false);
            }
            controller.update(r, frame.getObs(), frame.getAircraft());
            new Notification("Runway: " + name + " selected!");
        }
		
	}
	
	private class CheckBoxListener implements ActionListener {

		JCheckBox box;
		
		public CheckBoxListener(JCheckBox box) {
			this.box = box;
		}
		
		public void actionPerformed(ActionEvent arg0) {
			if (box.equals(baseDimension)) {
				if (box.isSelected()) {
					calculatedDimension.setSelected(false);
					top.setBaseDimensions(true);
					top.setCalculatedDimensions(false);
					side.setBaseDimensions(true);
					side.setCalculatedDimensions(false);
				} else {
					top.setBaseDimensions(false);
					side.setBaseDimensions(false);
				}
			} else if (box.equals(calculatedDimension)) {
				if (box.isSelected()) {
					baseDimension.setSelected(false);
					top.setBaseDimensions(false);
					top.setCalculatedDimensions(true);
					side.setBaseDimensions(false);
					side.setCalculatedDimensions(true);
				} else {
					top.setCalculatedDimensions(false);
					side.setCalculatedDimensions(false);
				}
			}
            frame.repaint();
        }

    }
	
	private class AddButtonListener implements ActionListener {
		
		private JFormattedTextField xPos;
		private JFormattedTextField yPos;
		private ObstacleComboBox box;

		public AddButtonListener(JFormattedTextField xPos, JFormattedTextField yPos, ObstacleComboBox box) {
			this.xPos = xPos;
			this.yPos = yPos;
			this.box = box;
		}
		
		public void actionPerformed(ActionEvent e) {
			Obstacle obs = controller.getObstacle((String) box.getSelectedItem());
            int x = 0, y = 0;
            try {
                x = (int) ((Long) xPos.getValue() + 60);
                y = (int) ((Long) yPos.getValue() + 135);
            }catch (NullPointerException _) {
                x = obs.getPosX();
                y = obs.getPosY();
            }
            frame.setObs(obs);
            frame.updateViews(x, y);
            new Notification("Obstacle (" + obs.getId() + ") Moved to:\nx: " + (x - 60) + "\ny: " + (y - 135));
        }
		
	}
	
	private class ObstacleAdderSubPanel extends JPanel {
		
		private JFormattedTextField xPos;
		private JFormattedTextField yPos;
		private ObstacleComboBox box;
		
		public ObstacleAdderSubPanel() {
			super();
			this.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
            NumberFormat f = NumberFormat.getNumberInstance(); 
            f.setMaximumIntegerDigits(5);
            xPos = new JFormattedTextField(f);
            yPos = new JFormattedTextField(f);
            xPos.setColumns(7);
            yPos.setColumns(7);
			box = new ObstacleComboBox();
			JButton add = new JButton("Change Obstruction");
            ActionListener l = new AddButtonListener(xPos, yPos, box);
            add.addActionListener(l);
            box.addActionListener(l);
            xPos.addActionListener(l);
            yPos.addActionListener(l);

            c.ipadx = 10;
			
			c.gridx = 0;
			this.add(box, c);
			c.gridx = 1;
			this.add(xPos, c);
			c.gridx = 2;
			this.add(yPos, c);
			c.gridx = 3;
			this.add(add, c);
		}

        private void update(int x, int y) {
            this.xPos.setValue(new Long(x - 60));
            this.yPos.setValue(new Long(y - 135));
        }
    }
	
	private class PopUpWindowSubPanel extends JPanel {
		
		public PopUpWindowSubPanel() {
			super();
			this.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			JButton TopViewButton = new JButton("Display Large Top View");
			JButton calcBreak = new JButton("View Calculation Break Down");
			calcBreak.addActionListener(new BreakDownListener());
			TopViewButton.addActionListener(new TopDownListener());
			
			c.ipadx = 10;
			
			c.gridx = 0;
			this.add(TopViewButton, c);
			c.gridx = 1;
			this.add(calcBreak, c);
		}
		
	}
	
	private class BreakDownListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			new CalcBreakDownFrame(frame.getAvailableRunway());
		}
		
	}
	
	private class TopDownListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			RotateTopDownFrame rotateFrame = new RotateTopDownFrame(frame);
			frame.setRotateFrame(rotateFrame);
			
		}
		
	}
	
}
