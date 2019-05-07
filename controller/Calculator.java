package controller;

import model.Aircraft;
import model.LogicalRunway;
import model.Obstacle;
import model.Runway;
 

public class Calculator {
    private Runway runway;
    private Obstacle obs;
    private Aircraft aircraft;

    public Calculator(Runway runway, Obstacle obs, Aircraft aircraft){
        this.runway = runway;
        this.obs = obs;
        this.aircraft = aircraft;
    }

    public Runway calculate(){
        if (needToUpdate()) {
            LogicalRunway east;
            LogicalRunway west;

            if(obs.getPosX() < runway.getEastFacing().getTora()/2){
                east = landsOverTakesOffAway(runway.getEastFacing());
                west = landsTowardsTakesOffTowards(runway.getWestFacing());
            }else{
                east = landsTowardsTakesOffTowards(runway.getEastFacing());
                west = landsOverTakesOffAway(runway.getWestFacing());
            }
            east.setObstructed(true);
            west.setObstructed(true);
            return new Runway(east, west);
        }else {
            return runway.clone();
        }

    }

    public boolean needToUpdate(){
        int obsY1 = obs.getPosY();
        int obsY2 = obsY1 + obs.getWidth();
        return ((obsY1 > 75 && obsY1 < 225) || (obsY2 > 75 && obsY2 < 225));
    }

    private LogicalRunway landsOverTakesOffAway(LogicalRunway runway) {
        LogicalRunway obstructed = runway.clone();
        //calculate landing first and take off changes the values of tora used in calulating the length of the runway
        landsOver(obstructed, runway);
        takeOffAway(obstructed, runway);
        return obstructed;
    }

    private LogicalRunway landsTowardsTakesOffTowards(LogicalRunway runway) {
        LogicalRunway obstructed = runway.clone();
        //calculate landing first and take off changes the values of tora used in calulating the length of the runway
        landsTowards(obstructed, runway);
        takeOffTowards(obstructed, runway);
        return obstructed;
    }

    private void landsTowards(LogicalRunway runway, LogicalRunway base) {
        int lda;
        //need to get displacement from the other threshold, so minused the original lda --mark
        //int displacementFromThreshold = base.getTora() - getDistanceFromDisplacedThreshold(base);
        lda = getDistanceFromDisplacedThreshold(base) - base.getResa() - base.getStripEnd();
        runway.setLda(lda);
        runway.setSLda("Distance from Displaced Threshold: " + getDistanceFromDisplacedThreshold(base) +
                        "\nRESA: " + base.getResa() +
                        "\nStrip End: " + base.getStripEnd() +
                        "\nLDA = DistanceFromDisplaceThreshold - RESAS - StripEnd" +
                        "\nLDA: " + lda);
    }
    
    // DisplacedThreshold calculation now used for all take-offs and landings. It works! --Jon
    private int getDistanceFromDisplacedThreshold(LogicalRunway runway) {
        int position = obs.getPosX();
        if (position < (runway.getTora() / 2)) {
            //previously only returned position, now minus 60 which represents the stripend --ed 
            //made similar changes to the else statement --ed
        	
        	// Taking into account either direction (if coming from left, take into account left Threshold
        	// else take into account right Threshold) -- Jon
        	if (runway.getDirection() < 18)
        		return position-60;
        	else return (runway.getTora() - (position - 60) + runway.getDisplacementThreshold());
        } else {
        	if (runway.getDirection() < 18) {
        		return (position - 60) - runway.getDisplacementThreshold();
        		}
        	else {
        		return (runway.getTora() - runway.getDisplacementThreshold()) - (position - 60);
        	}
        }
    }

    private void landsOver(LogicalRunway runway, LogicalRunway base) {
        int als = obs.getHeight() * 50;
        int displacementFromThreshold = getDistanceFromDisplacedThreshold(base);
        int originalLDA = base.getLda();
        int lda = originalLDA - displacementFromThreshold - als - base.getStripEnd();
        runway.setLda(lda);
        runway.setSLda("Distance from Displaced Threshold: " + displacementFromThreshold +
                        "\nObstacle Height: " + obs.getHeight() +
                        "\nALS: = 50 * Obstacle Height" +
                        "\nALS: " + als +
                        "\nStrip End: " + base.getStripEnd() +
                        "\nOriginalLDA: " + base.getLda() +
                        "\nLDA = OriginalLDA - DistanceFromDisplaceThreshold - ALS - StripEnd" +
                        "\nLDA: " + lda);
    }

    private void takeOffTowards(LogicalRunway runway, LogicalRunway base) {
        int tocs = obs.getHeight() * 50;
        int resa = base.getResa();
        //need to get displacement from the other threshold, so minused the original lda --mark
        int displacementFromThreshold = getDistanceFromDisplacedThreshold(base);
        int displacedThreshold = base.getDisplacementThreshold();
        int tora;
        String toraCalc;
        if (resa > tocs) {
            tora = displacementFromThreshold + displacedThreshold - resa - base.getStripEnd();
            toraCalc = "Distance from displaced threshold + Displaced Threshold - RESA - Stripend";
        } else {
            tora = displacementFromThreshold + displacedThreshold - tocs - base.getStripEnd();
            toraCalc = "Distance from displaced threshold + Displaced Threshold - TOCS - Stripend";
        }
        int toda = tora;
        int asda = tora;
        runway.setTora(tora);
        runway.setSTora(
                        "\nObstacle Height: " + obs.getHeight() +
                        "\nTOCS: = 50 * Obstacle Height" +
                        "\nTOCS: " + tocs +
                        "\nRESA: " + resa +
                        "\nDisplaced Threshold: " + displacedThreshold +
                        "\nDistance from Displaced Threshold: " + displacementFromThreshold +
                        "\n" + toraCalc +
                        "\nTORA: " + tora);
        runway.setToda(toda);
        runway.setSToda("TODA = TORA");
        runway.setAsda(asda);
        runway.setSAsda("ASDA = TORA");
    }

    private void takeOffAway(LogicalRunway runway, LogicalRunway base) {
        int distanceFromThreshold = getDistanceFromDisplacedThreshold(base);
        int displacement = 0 - distanceFromThreshold - aircraft.getBlastAllowance();
        // not sure if it should actually be >> int thing = 0 - distanceFromThreshold - aircraft.getBlastAllowance() + runway.getStopway() + runway.getClearway();
        // since the clearway is supposed to be included in the asda and the
        // stopway is supposed to be in the toda
        int tora = base.getTora() + displacement;
        int toda = base.getToda() + displacement;
        int asda = base.getAsda() + displacement;
        runway.setTora(tora);
        runway.setSTora("Aircraft Blast Allowance: " + aircraft.getBlastAllowance() +
                        "\nDistance from Displaced Threshold: " + distanceFromThreshold +
                        "\nDisplacement = DistanceFromDisplacedThreshold + AircraftBlastAllowance" +
                        "\nDisplacement: " + displacement +
                        "\nOldTORA: " + base.getTora() + 
                        "\nTORA = OldTORA - displacement" +
                        "\nTORA: " + tora);
        runway.setToda(toda);
        runway.setSToda("Aircraft Blast Allowance: " + aircraft.getBlastAllowance() +
                        "\nDistance from Displaced Threshold: " + distanceFromThreshold +
                        "\nDisplacement = DistanceFromDisplacedThreshold + AircraftBlastAllowance" +
                        "\nDisplacement: " + displacement +
                        "\nOldTODA: " + base.getToda() + 
                        "\nTODA = OldTODA - displacement" +
                        "\nTODA: " + toda); 
        runway.setAsda(asda);
        runway.setSAsda("Aircraft Blast Allowance: " + aircraft.getBlastAllowance() +
                        "\nDistance from Displaced Threshold: " + distanceFromThreshold +
                        "\nDisplacement = DistanceFromDisplacedThreshold + AircraftBlastAllowance" +
                        "\nDisplacement: " + displacement +
                        "\nOldASDA: " + base.getAsda() + 
                        "\nASDA = OldASDA - displacement" +
                        "\nASDA: " + tora);
    }
}
