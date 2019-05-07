//package set up in project
package views;

import controller.UserController;
import model.LogicalRunway;
import model.Runway;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

//import so it knows about the Runway Class


public class RunwayParameters extends JPanel {

    //Added DefualtTableModel so we can update the values in the table
    private DefaultTableModel tableModel;
    private GUIFrame frame;
    private UserController controller;

	
	public RunwayParameters(Runway r, GUIFrame frame, UserController controller) {
        this.controller = controller;
        this.frame = frame;
		setLayout(new BorderLayout());

        String[] columnNames = {"Runway", "TORA (m)", "TODA (m)", "ASDA (m)", "LDA (m)"};

        LogicalRunway e = r.getEastFacing();
        LogicalRunway w = r.getWestFacing();
        Object[][] data = { 
            {e.getTitle(), e.getTora(), e.getToda(), e.getAsda(), e.getLda()},
            {w.getTitle(), w.getTora(), w.getToda(), w.getAsda(), w.getLda()}
        };

        //using tableModel so we can change the data in the table
        tableModel = new DefaultTableModel(data, columnNames) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(tableModel);

		table.setPreferredScrollableViewportSize(new Dimension(500, 50));
		table.setFillsViewportHeight(true);
		table.setEnabled(true);
		table.addMouseListener(new tableListener(table));
		JScrollPane sp = new JScrollPane(table);
		add(sp);
	}

    //allows the visible data to change
    public void update(Runway r) {
        LogicalRunway e = r.getEastFacing();
        LogicalRunway w = r.getWestFacing();

        Object[] east = {e.getTitle(), e.getTora(), e.getToda(), e.getAsda(), e.getLda()};
        Object[] west = {w.getTitle(), w.getTora(), w.getToda(), w.getAsda(), w.getLda()};

        boolean eastAdded = false, westAdded = false;
        for (int x = 0; x < tableModel.getRowCount(); x++) {
            if (!eastAdded && tableModel.getValueAt(x, 0).equals(e.getTitle())) {
                tableModel.removeRow(x);
                tableModel.insertRow(x, east);
                eastAdded = true;
            }else if (!westAdded && tableModel.getValueAt(x, 0).equals(w.getTitle())) {
                tableModel.removeRow(x);
                tableModel.insertRow(x, west);
                westAdded = true;
            }
        }

        if (!eastAdded) {
            tableModel.addRow(east);
        }
        if (!westAdded) {
            tableModel.addRow(west);
        }
    }
    
    private class tableListener implements MouseListener {

    	private JTable table;
    	
    	public tableListener(JTable table) {
    		this.table = table;
    	}

		@Override
		public void mouseClicked(MouseEvent e) {
            if (table.getSelectedRow() == -1) {return;}
            String name = (String) table.getModel().getValueAt(table.getSelectedRow(), 0);
            Runway r = controller.getRunway(name);
            if (Integer.parseInt(name.substring(0, 2)) < 19) {
                frame.setLeftValue(true);
			} else {
                frame.setLeftValue(false);
			}
            controller.update(r, frame.getObs(), frame.getAircraft());
            new Notification("Runway: " + name + " selected!");
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
    	
    }

	
    /*
	public static void main (String args[]){
		RunwayOriginalParameter gui = new RunwayOriginalParameter();
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setSize(1000,200);
		gui.setVisible(true);
		gui.setTitle("Runway Original Parameter");

        //added test to see if table will change
        gui.setRunway(Runway.getDebugRunway());
		
	}
    */
}
