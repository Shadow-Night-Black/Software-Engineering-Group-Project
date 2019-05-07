package views;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;

import model.Aircraft;
import model.Obstacle;
import model.Runway;
import controller.UserController;

public class RotateTopDownFrame extends JFrame {

	private GUIFrame mainFrame;
	private TopDownView top;
    private Runway r;
    private Obstacle obs;
    private Aircraft aircraft;
	
	public RotateTopDownFrame(GUIFrame mainFrame) {
		super("Rotated Top Down View");
		this.mainFrame = mainFrame;
		r = mainFrame.getR();
		obs = mainFrame.getObs();
		aircraft = mainFrame.getAircraft();
		
		top = new TopDownView(900,900, obs, r, mainFrame);
        //this.setSize(1000, 700);
        this.setMinimumSize(new Dimension(900, 900));
        this.arrangePanels();
        this.pack();
        // this.setResizable(false);
        top.repaint();
        this.setVisible(true);
	}
	
	private void arrangePanels() {
		Container mainPanel = this.getContentPane();
		mainPanel.setLayout(new FlowLayout());
		mainPanel.add(top);
	}
	
	public TopDownView getTop() {
		return top;
	}
	
}
