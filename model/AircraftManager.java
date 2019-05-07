package model;

import data.XMLAircraftReader;
import data.XMLAircraftWriter;
import exceptions.InvalidDataBaseFileException;

import java.util.*;

public class AircraftManager {

    public static final String fileName = "Aircraft.xml";
    private Map<String, Aircraft> aircraft;
    private List<String> ids;

    public AircraftManager() {
        try {
            XMLAircraftReader reader = new XMLAircraftReader(fileName);
            aircraft = new HashMap<>();
            ids = new ArrayList<>();
            for (Aircraft a : reader.getAll()) {
                try {
                    this.addAircraft(a);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (InvalidDataBaseFileException e) {
            e.printStackTrace();
        }
    }

    public synchronized List<String> getAllAircraft() {
        return ids;
    }

    public synchronized Aircraft getAircraft(String s) {
        return aircraft.get(s);
    }

    public synchronized void addAircraft(Aircraft a) {
        ids.add(a.getId());
        aircraft.put(a.getId(), a);
    }

    public synchronized void writeDatabase() {
        XMLAircraftWriter writer = new XMLAircraftWriter(fileName);
        Set<Aircraft> obstacleSet = new HashSet<>(aircraft.values());
        writer.writeAircraft(obstacleSet);
    }
}
