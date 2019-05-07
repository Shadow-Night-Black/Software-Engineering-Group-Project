package controller;

import model.Aircraft;
import model.Obstacle;
import model.Runway;
import views.GUIFrame;

import java.util.List;

public class UserController {
    private GUIFrame gui;
    private ModelController modelController;

    public UserController() {
        modelController = ModelController.getModelController();
        this.gui = new GUIFrame(this);
    }

    public void update(Runway runway, Obstacle obs, Aircraft aircraft) {
        gui.setRunway(runway);
        Calculator c = new Calculator(runway, obs, aircraft);
       Runway obstructedRunway = c.calculate();
        gui.update(obstructedRunway);
    }


    // Fixed all the getters to return objects, and use modelController methods.
    public List<String> getAllRunways() {
        return modelController.getAllRunways();
    }

    public Runway getRunway(String s) {
        return modelController.getRunway(s);
    }

    public List<String> getAllObstacles() {
        return modelController.getAllObstacles();
    }

    public Obstacle getObstacle(String id) {
        return modelController.getObstacle(id);
    }

    public Runway getDefaultRunway() {
        List<String> l = modelController.getAllRunways();
        if (l.size() == 0) {
            Runway r = Runway.getDebugRunway();
            modelController.addRunway(r);
            return r;
        }

        return modelController.getRunway(l.get(0));
    }

    public Obstacle getDefaultObstacle() {
        List<String> l = modelController.getAllObstacles();
        if (l.size() == 0) {
            Obstacle o = Obstacle.getDebugObstacle();
            modelController.addObstacle(o);
            return o;
        }

        return modelController.getObstacle(l.get(0));
    }

    public Aircraft getDefaultAircraft() {
        List<String> l = modelController.getAllAircraft();
        if (l.size() == 0) {
            Aircraft a = Aircraft.getDefault();
            modelController.addAircraft(a);
            return a;
        }

        return modelController.getAircraft(l.get(0));
    }
}

