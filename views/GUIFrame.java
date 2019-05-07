package views;

import controller.UserController;
import model.Aircraft;
import model.LogicalRunway;
import model.Obstacle;
import model.Runway;

import javax.swing.*;

import java.awt.*;


public class GUIFrame extends JFrame {


    private UserController userController;
    private Runway r;
    private Obstacle obs;
    private Aircraft aircraft;
    private RotateTopDownFrame rotateFrame;

	private TopDownView top;
    private SideView side;
    private RunwayParameters oldValues;
    private RunwayParameters newValues;
    private DimensionTogglePanel dimensionPanel;


    // Now takes a UserController parameter, as this will be needed to read runways
    public GUIFrame(UserController controller) {
        super("Test frame");

        this.userController = controller;
        rotateFrame = null;
        // Using info from the reader to retrieve runway data
        r = controller.getDefaultRunway();
        obs = controller.getDefaultObstacle();
        aircraft = controller.getDefaultAircraft();
        
        // Creating views/parameter table based on values obtained from reader
        // JON: Added boolean parameter to TopDownView so enable and disable rotation
        top = new TopDownView(900, 300, obs, r, this);
        side = new SideView(900, 300, obs, r, this);
        oldValues = new RunwayParameters(r, this, userController);
        newValues = new RunwayParameters(r, this, userController);
        dimensionPanel = new DimensionTogglePanel(userController, top, side, this);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //this.setSize(1000, 700);
        this.setMinimumSize(new Dimension(1450, 1050));
        this.arrangePanels();
        this.pack();
        // this.setResizable(false);
        this.setVisible(true);
    }

    private void arrangePanels() {
        Container mainPanel = this.getContentPane();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.ipadx = 10;
        
        Container mainView = new Container();
        mainView.setLayout(new GridLayout(2, 1, 0, 10));
        
        Container topView = new Container();
        topView.setLayout(new BorderLayout());
        JLabel topLabel = new JLabel("Top Down");
        topView.add(topLabel, BorderLayout.NORTH);
        topView.add(top, BorderLayout.CENTER);
        
        Container sideView = new Container();
        sideView.setLayout(new BorderLayout());
        JLabel sideLabel = new JLabel("Side View");
        sideView.add(sideLabel, BorderLayout.NORTH);
        sideView.add(side, BorderLayout.CENTER);
        
        
        mainView.add(topView);
        mainView.add(sideView);

        Container parameterView = new Container();
        parameterView.setLayout(new GridLayout(3, 1, 0, 10));
        
        Container originalParam = new Container();
        originalParam.setLayout(new BorderLayout());
        JLabel originalLabel = new JLabel("Original Parameters");
        originalParam.add(originalLabel, BorderLayout.NORTH);
        originalParam.add(oldValues);
        
        Container newParam = new Container();
        newParam.setLayout(new BorderLayout());
        JLabel newLabel = new JLabel("New Parameters");
        newParam.add(newLabel, BorderLayout.NORTH);
        newParam.add(newValues);
        
        parameterView.add(new LegendPanel(500,190));
        parameterView.add(originalParam);
        parameterView.add(newParam);

        mainPanel.add(mainView, c);
        c.gridx = 1;
        mainPanel.add(parameterView, c);
        c.gridy = 2;
        c.gridx = 0;
        mainPanel.add(dimensionPanel, c);
    }

    //x and y are in meters
    public void updateViews(int x, int y) {
        obs.setPosX(Math.min(Math.max(x, 60), 60 + r.getEastFacing().getTora() - obs.getLength()));
        obs.setPosY(Math.min(Math.max(y, 0), 270));
        userController.update(r, obs, aircraft);
    }

    public void update(Runway r) {
        newValues.update(r);
        dimensionPanel.update(obs);
        top.setAvailableRunway(r);
        side.setAvailableRunway(r);
        top.repaint();
        side.repaint();
        if (rotateFrame != null) {
        	rotateFrame.getTop().setAvailableRunway(r);
        	rotateFrame.getTop().repaint();
        }
    }

    public Runway getR() {
        return r;
    }
    
    public void setRotateFrame(RotateTopDownFrame frame) {
    	rotateFrame = frame;
    }
    
    public LogicalRunway getAvailableRunway() {
    	return top.getAvailableRunway();
    }

    public void setRunway(Runway r) {
        this.r = r;
        top.setBaseRunway(r);
        side.setBaseRunway(r);
        if (rotateFrame != null) rotateFrame.getTop().setBaseRunway(r);
        oldValues.update(r);
    }

	public Aircraft getAircraft() {
		return aircraft;
	}

	public void setAircraft(Aircraft aircraft) {
		this.aircraft = aircraft;
	}

    public Obstacle getObs() {
        return obs;
    }

    public void setObs(Obstacle obs) {
        this.obs = obs;
        top.setObs(obs);
        side.setObs(obs);
    }

    public void setLeftValue(boolean b) {
        top.setLeftValue(b);
        side.setLeftValue(b);
    }
}
