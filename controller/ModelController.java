package controller;

import model.*;

import java.util.List;

public class ModelController {
    private static ModelController modelController;
    private RunwayManager runwayManager;
    private ObstacleManager obstacleManager;
    private AircraftManager aircraftManager;

    private ModelController() {
        runwayManager = new RunwayManager();
        obstacleManager = new ObstacleManager();
        aircraftManager = new AircraftManager();
    }

    public static ModelController getModelController() {
        if (modelController == null) {
            modelController = new ModelController();
        }
        return modelController;
    }

    public  List<String> getAllRunways() {
        return runwayManager.getAllRunways();
    }

    public Runway getRunway(String id) {
        return runwayManager.getRunway(id);
    }

    public void addRunway(Runway r) {
        runwayManager.addRunway(r);
        runwayManager.writeDatabase();
    }

    public List<String> getAllObstacles() {
        return obstacleManager.getAllObstacles();
    }

    public Obstacle getObstacle(String id) {
        return obstacleManager.getObstacle(id);
    }

    public void addObstacle(Obstacle o) {
        obstacleManager.addObstacle(o);
        obstacleManager.writeDatabase();
    }

    public Aircraft getAircraft(String id) {
        return aircraftManager.getAircraft(id);
    }

    public List<String> getAllAircraft() {
        return aircraftManager.getAllAircraft();
    }

    public void addAircraft(Aircraft a) {
        aircraftManager.addAircraft(a);
        aircraftManager.writeDatabase();
    }
}



