package model;

import data.XMLRunwayReader;
import data.XMLRunwayWriter;
import exceptions.InvalidDataBaseFileException;
import exceptions.InvalidDataBaseFormatException;

import java.util.*;

public class RunwayManager {
    public static final String fileName = "Runways.xml";
    private Map<String, Runway> runways;
    private List<String> ids;

    public RunwayManager() {
        try {
            XMLRunwayReader reader = new XMLRunwayReader(fileName);
            runways = new HashMap<>();
            ids = new ArrayList<>();
            for (Runway r: reader.getAll()) {
                this.addRunway(r);
            }
        } catch (InvalidDataBaseFormatException | InvalidDataBaseFileException e) {
            e.printStackTrace();
        }
    }

    public synchronized List<String> getAllRunways() {
        return ids;
    }

    public synchronized Runway getRunway(String s) {
            return runways.get(s);
    }

    public synchronized void addRunway(Runway r) {
        ids.add(r.getEastFacing().getTitle());
        ids.add(r.getWestFacing().getTitle());
        runways.put(r.getEastFacing().getTitle(), r);
        runways.put(r.getWestFacing().getTitle(), r);
    }

    public synchronized void writeDatabase() {
        XMLRunwayWriter writer = new XMLRunwayWriter(fileName);
        Set<Runway> runwaySet = new HashSet<>(runways.values());
        writer.writeRunways(runwaySet);
    }
}

