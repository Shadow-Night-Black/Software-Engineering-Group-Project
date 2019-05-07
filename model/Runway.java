package model;

public class Runway {
    private LogicalRunway east, west;

    public Runway(int direction, Designator designator, int tora, int toda, int asda, int lda, int displacementThreshold, int resa, int stripEnd) {
        if (direction < 19 && direction > 0) {
            east = new LogicalRunway(direction, designator, tora, toda, asda, lda, displacementThreshold, resa, stripEnd);
            // Changed 36 - direction to 18 + direction. The opposite of 1, for example, is NOT 35, but 19 --Jon
            west = new LogicalRunway(18 + direction, designator.opposite(), tora, toda, asda, lda, displacementThreshold, resa, stripEnd);
        } else if (direction <= 36) {
            east = new LogicalRunway(direction - 18, designator.opposite(), tora, toda, asda, lda, displacementThreshold, resa, stripEnd);
            west = new LogicalRunway(direction, designator, tora, toda, asda, lda, displacementThreshold, resa, stripEnd);
        } else
            throw new RuntimeException("Runway bearing is not bewteen 01 and 36!");
    }


    public Runway(LogicalRunway east, LogicalRunway west) {
        this.east = east;
        this.west = west;
    }

    public static Runway getDebugRunway() {
        return new Runway(9, Designator.L, 3902, 3942, 3922, 3595, 306, 240, 60);
    }

    public LogicalRunway getEastFacing() {
        return east;
    }

    public LogicalRunway getWestFacing() {
        return west;
    }

    public Runway clone() {
        return new Runway(east.clone(), west.clone());
    }

}
