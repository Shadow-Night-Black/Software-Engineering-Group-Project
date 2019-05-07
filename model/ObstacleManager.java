package model;

import data.XMLObstacleReader;
import data.XMLObstacleWriter;
import exceptions.InvalidDataBaseFileException;

import java.util.*;

public class ObstacleManager {
    public static final String fileName = "Obstacles.xml";
    private Map<String, Obstacle> obstacles;
    private List<String> ids;

    public ObstacleManager() {
        try {
            XMLObstacleReader reader = new XMLObstacleReader(fileName);
            obstacles = new HashMap<>();
            ids = new ArrayList<>();
            for (Obstacle o: reader.getAll()) {
                try {
                    this.addObstacle(o);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (InvalidDataBaseFileException e) {
            e.printStackTrace();
        }
    }

    public synchronized List<String> getAllObstacles() {
        return ids;
    }

    public synchronized Obstacle getObstacle(String s) {
        return obstacles.get(s);
    }

    public synchronized void addObstacle(Obstacle o) {
        ids.add(o.getId());
        obstacles.put(o.getId(), o);
    }

    public synchronized void writeDatabase() {
        XMLObstacleWriter writer = new XMLObstacleWriter(fileName);
        Set<Obstacle> obstacleSet = new HashSet<>(obstacles.values());
        writer.writeObstacles(obstacleSet);
    }
}

