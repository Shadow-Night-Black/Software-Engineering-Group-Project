package model;

import controller.Calculator;
import junit.framework.TestCase;


public class ModelTestUnit extends TestCase {
	
	// 01 should be opposed by 19, 02 by 20 and so forth.
	public void testThresholdDirectionSanity() {
		Runway r = new Runway(1, Designator.L, 3902, 3902, 3902, 3595, 306, 240, 60);
		for (int count = 1; count < 18; count++) {
			r = new Runway(count, Designator.L, 3902, 3902, 3902, 3595, 306, 240, 60);
			assertEquals("Threshold on left = " + count, 
					count, r.getEastFacing().getDirection());
			assertEquals("Threshold on right = " + (18 + count), 18 + count,
					r.getWestFacing().getDirection());
		}
		assertEquals("Threshold on right designated: R", Designator.R, r.getWestFacing().getDesignator());
	}
	
	public void testLandingTowardsObjectSpec() {
		//added 60 because we did not take into account the stripends
		Obstacle obs = new Obstacle("Debug", 2906+60, 150, 100, 50, 25, 180, 100, 25);
		Runway r = new Runway(9, Designator.L, 3902, 3902, 3902, 3595, 306, 240, 60);
        Aircraft a = new Aircraft("Debug", 300);
        Calculator calc = new Calculator(r, obs, a);
		assertEquals("In the case of an	obstacle occurring on the 09L runway 2600m from its threshold, then the reduced LDA will be:", 
				2300, calc.calculate().getEastFacing().getLda());
	}
	
	public void testLandingOverObjectSpec() {
		Obstacle obs = new Obstacle("Debug", 3384+60, 150, 100, 50, 25, 180, 0, 0);
		Runway r = new Runway(27, Designator.R, 3884, 3962, 3884, 3884, 0, 240, 60);
        Aircraft a = new Aircraft("Debug", 300);
        Calculator calc = new Calculator(r, obs, a);
		assertEquals("If an obstacle that is 25m tall occurs 500m from the runway 27R threshold, then the reduced LDA will be:",
				2074, calc.calculate().getWestFacing().getLda());
	}
	
	public void testTakeOffTowardsObjectSpec() {
		Obstacle obs = new Obstacle("Debug", 2806+60, 150, 10, 50, 25, 180, 0, 0);
		Runway r = new Runway(9, Designator.L, 3902, 3902, 3902, 3595, 306, 240, 60);
        Aircraft a = new Aircraft("Debug", 300);
        Calculator calc = new Calculator(r, obs, a);
        LogicalRunway obstructed = calc.calculate().getEastFacing();
		assertEquals("If an obstacle that is 25m tall occurs on the 09L runway 2500m from its threshold and the plane is taking off"
				+ "towards it, the reduced TORA will be: 1496m", 1496, obstructed.getTora());
		assertTrue("As Obstacle obstructs Clearway and Stopway, TODA = TORA", obstructed.getToda() == obstructed.getTora());
		assertTrue("As Obstacle obstructs Clearway and Stopway, ASDA = TORA", obstructed.getAsda() == obstructed.getTora());
	}
	
	public void testTakeOffAwayFromObjectSpec() {
		Obstacle obs = new Obstacle("Debug", 3384+60, 150, 100, 50, 25, 180, 0, 0);
		Runway r = new Runway(27, Designator.R, 3884, 3962, 3884, 3884, 0, 240, 60);
        Aircraft a = new Aircraft("Debug", 300);
        Calculator calc = new Calculator(r, obs, a);
        LogicalRunway obstructed = calc.calculate().getWestFacing();
		assertEquals("With an obstacle 500m from 27R threshold, TORA should be: 3084", 3084, obstructed.getTora());
		assertEquals("TODA should be: 3162", 3162, obstructed.getToda());
		assertEquals("ASDA should be: 3084", 3084, obstructed.getAsda());
	}
	
	//Completely inside "purple area"
	public void testZeroYMinX()	{
		Obstacle obs = new Obstacle("Debug", 0, 0, 100, 50, 25, 180, 0, 0);
		Runway r = new Runway(27, Designator.R, 3884, 3962, 3884, 3884, 0, 240, 60);
        Aircraft a = new Aircraft("Debug", 300);
        Calculator calc = new Calculator(r, obs, a);
        LogicalRunway obstructed = calc.calculate().getWestFacing();
        assertEquals("As obstacle is out of cleared and graded area, TORA should not change (should be 3884)", 3884, obstructed.getTora());
        assertEquals("TODA should not change (should be 3962)", 3962, obstructed.getToda());
        assertEquals("ASDA should not change (should be 3884)", 3884, obstructed.getAsda());
        assertEquals("LDA should not change (should be 3884)", 3884, obstructed.getLda());
	}
	
	//Completely inside "purple area"
	public void testZeroYMiddleX() {
		Obstacle obs = new Obstacle("Debug", 1800, 0, 100, 50, 25, 180, 0, 0);
		Runway r = new Runway(27, Designator.R, 3884, 3962, 3884, 3884, 0, 240, 60);
        Aircraft a = new Aircraft("Debug", 300);
        Calculator calc = new Calculator(r, obs, a);
        LogicalRunway obstructed = calc.calculate().getWestFacing();
        assertEquals("As obstacle is out of cleared and graded area, TORA should not change (should be 3884)", 3884, obstructed.getTora());
        assertEquals("TODA should not change (should be 3962)", 3962, obstructed.getToda());
        assertEquals("ASDA should not change (should be 3884)", 3884, obstructed.getAsda());
        assertEquals("LDA should not change (should be 3884)", 3884, obstructed.getLda());
	}
	
	//Completely inside "purple area"
	public void testZeroYMaxX() {
		Obstacle obs = new Obstacle("Debug", 3961, 0, 100, 50, 25, 180, 0, 0);
		Runway r = new Runway(9, Designator.L, 3902, 3902, 3902, 3595, 306, 240, 60);
        Aircraft a = new Aircraft("Debug", 300);
        Calculator calc = new Calculator(r, obs, a);
        LogicalRunway obstructed = calc.calculate().getWestFacing();
        assertEquals("As obstacle is out of cleared and graded area, TORA should not change (should be 3902)", 3902, obstructed.getTora());
        assertEquals("TODA should not change (should be 3902)", 3902, obstructed.getToda());
        assertEquals("ASDA should not change (should be 3902)", 3902, obstructed.getAsda());
        assertEquals("LDA should not change (should be 3595)", 3595, obstructed.getLda());
	}
	
	// Obstruction sitting just inside purple area
	public void testLowYMinX() {
		Obstacle obs = new Obstacle("Debug", 0, 25, 100, 50, 25, 180, 0, 0);
		Runway r = new Runway(27, Designator.R, 3884, 3962, 3884, 3884, 0, 240, 60);
        Aircraft a = new Aircraft("Debug", 300);
        Calculator calc = new Calculator(r, obs, a);
        LogicalRunway obstructed = calc.calculate().getWestFacing();
        assertEquals("As obstacle is out of cleared and graded area, TORA should not change (should be 3884)", 3884, obstructed.getTora());
        assertEquals("TODA should not change (should be 3962)", 3962, obstructed.getToda());
        assertEquals("ASDA should not change (should be 3884)", 3884, obstructed.getAsda());
        assertEquals("LDA should not change (should be 3884)", 3884, obstructed.getLda());
	}
	
	// Sitting inside cleared and graded area, should calculate
	public void testLowYMiddleX() {
		Obstacle obs = new Obstacle("Debug", 1960, 25, 100, 50, 25, 180, 0, 0);
		Runway r = new Runway(9, Designator.L, 3902, 3902, 3902, 3595, 306, 240, 60);
        Aircraft a = new Aircraft("Debug", 300);
        Calculator calc = new Calculator(r, obs, a);
        LogicalRunway obstructed = calc.calculate().getWestFacing();
        assertEquals("As obstacle is inside cleared and graded area, TORA should change (should be 590)", 590, obstructed.getTora());
        assertEquals("TODA should not change (should be 590)", 590, obstructed.getToda());
        assertEquals("ASDA should not change (should be 590)", 590, obstructed.getAsda());
        assertEquals("LDA should not change (should be 1294)", 1294, obstructed.getLda());
	}
	
	// Obstruction sitting just inside purple area
	public void testLowYMaxX() {
		Obstacle obs = new Obstacle("Debug", 3961, 25, 100, 50, 25, 180, 0, 0);
		Runway r = new Runway(27, Designator.R, 3884, 3962, 3884, 3884, 0, 240, 60);
        Aircraft a = new Aircraft("Debug", 300);
        Calculator calc = new Calculator(r, obs, a);
	    LogicalRunway obstructed = calc.calculate().getWestFacing();
	    assertEquals("As obstacle is out of cleared and graded area, TORA should not change (should be 3884)", 3884, obstructed.getTora());
	    assertEquals("TODA should not change (should be 3962)", 3962, obstructed.getToda());
	    assertEquals("ASDA should not change (should be 3884)", 3884, obstructed.getAsda());
	    assertEquals("LDA should not change (should be 3884)", 3884, obstructed.getLda());
	}
	
	//Completely inside "purple area"
	public void testMaxYMinX()	{
		Obstacle obs = new Obstacle("Debug", 0, 250, 100, 50, 25, 180, 0, 0);
		Runway r = new Runway(27, Designator.R, 3884, 3962, 3884, 3884, 0, 240, 60);
        Aircraft a = new Aircraft("Debug", 300);
        Calculator calc = new Calculator(r, obs, a);
        LogicalRunway obstructed = calc.calculate().getWestFacing();
        assertEquals("As obstacle is out of cleared and graded area, TORA should not change (should be 3884)", 3884, obstructed.getTora());
        assertEquals("TODA should not change (should be 3962)", 3962, obstructed.getToda());
        assertEquals("ASDA should not change (should be 3884)", 3884, obstructed.getAsda());
        assertEquals("LDA should not change (should be 3884)", 3884, obstructed.getLda());
	}
	
	//Completely inside "purple area"
	public void testMaxYMiddleX() {
		Obstacle obs = new Obstacle("Debug", 1800, 250, 100, 50, 25, 180, 0, 0);
		Runway r = new Runway(27, Designator.R, 3884, 3962, 3884, 3884, 0, 240, 60);
        Aircraft a = new Aircraft("Debug", 300);
        Calculator calc = new Calculator(r, obs, a);
        LogicalRunway obstructed = calc.calculate().getEastFacing();
        assertEquals("As obstacle is out of cleared and graded area, TORA should not change (should be 3884)", 3884, obstructed.getTora());
        assertEquals("TODA should not change (should be 3962)", 3962, obstructed.getToda());
        assertEquals("ASDA should not change (should be 3884)", 3884, obstructed.getAsda());
        assertEquals("LDA should not change (should be 3884)", 3884, obstructed.getLda());
	}
	
	//Completely inside "purple area"
	public void testMaxYMaxX() {
		Obstacle obs = new Obstacle("Debug", 3961, 250, 100, 50, 25, 180, 0, 0);
		Runway r = new Runway(9, Designator.L, 3902, 3902, 3902, 3595, 306, 240, 60);
        Aircraft a = new Aircraft("Debug", 300);
        Calculator calc = new Calculator(r, obs, a);
	    LogicalRunway obstructed = calc.calculate().getWestFacing();
	    assertEquals("As obstacle is out of cleared and graded area, TORA should not change (should be 3902)", 3902, obstructed.getTora());
	    assertEquals("TODA should not change (should be 3902)", 3902, obstructed.getToda());
	    assertEquals("ASDA should not change (should be 3902)", 3902, obstructed.getAsda());
	    assertEquals("LDA should not change (should be 3595)", 3595, obstructed.getLda());
	}
	
	// Obstruction sitting just inside purple area
		public void testHighYMinX() {
			Obstacle obs = new Obstacle("Debug", 0, 225, 100, 50, 25, 180, 0, 0);
			Runway r = new Runway(27, Designator.R, 3884, 3962, 3884, 3884, 0, 240, 60);
            Aircraft a = new Aircraft("Debug", 300);
            Calculator calc = new Calculator(r, obs, a);
	        LogicalRunway obstructed = calc.calculate().getWestFacing();
	        assertEquals("As obstacle is out of cleared and graded area, TORA should not change (should be 3884)", 3884, obstructed.getTora());
	        assertEquals("TODA should not change (should be 3962)", 3962, obstructed.getToda());
	        assertEquals("ASDA should not change (should be 3884)", 3884, obstructed.getAsda());
	        assertEquals("LDA should not change (should be 3884)", 3884, obstructed.getLda());
		}
		
		// Sitting inside cleared and graded area, should calculate
		public void testHighYMiddleX() {
			Obstacle obs = new Obstacle("Debug", 1960, 225, 100, 50, 25, 180, 0, 0);
			Runway r = new Runway(9, Designator.L, 3902, 3902, 3902, 3595, 306, 240, 60);
            Aircraft a = new Aircraft("Debug", 300);
            Calculator calc = new Calculator(r, obs, a);
	        LogicalRunway obstructed = calc.calculate().getWestFacing();
	        assertEquals("As obstacle is inside cleared and graded area, TORA should change (should be 590)", 590, obstructed.getTora());
	        assertEquals("TODA should not change (should be 590)", 590, obstructed.getToda());
	        assertEquals("ASDA should not change (should be 590)", 590, obstructed.getAsda());
	        assertEquals("LDA should not change (should be 1294)", 1294, obstructed.getLda());
		}
		
		// Obstruction sitting just inside purple area
		public void testHighYMaxX() {
			Obstacle obs = new Obstacle("Debug", 3961, 225, 100, 50, 25, 180, 0, 0);
			Runway r = new Runway(27, Designator.R, 3884, 3962, 3884, 3884, 0, 240, 60);
            Aircraft a = new Aircraft("Debug", 300);
            Calculator calc = new Calculator(r, obs, a);
		    LogicalRunway obstructed = calc.calculate().getWestFacing();
		    assertEquals("As obstacle is out of cleared and graded area, TORA should not change (should be 3884)", 3884, obstructed.getTora());
		    assertEquals("TODA should not change (should be 3962)", 3962, obstructed.getToda());
		    assertEquals("ASDA should not change (should be 3884)", 3884, obstructed.getAsda());
		    assertEquals("LDA should not change (should be 3884)", 3884, obstructed.getLda());
		}
		
		// Obstruction partially inside cleared and graded, should calculator
		public void testAcrossBoundaryLowY() {
			Obstacle obs = new Obstacle("Debug", 3961, 80, 100, 50, 25, 180, 0, 0);
			Runway r = new Runway(27, Designator.R, 3884, 3962, 3884, 3884, 0, 240, 60);
            Aircraft a = new Aircraft("Debug", 300);
            Calculator calc = new Calculator(r, obs, a);
		    LogicalRunway obstructed = calc.calculate().getWestFacing();
		    assertNotSame("As obstacle is out of cleared and graded area, TORA should change", 3884, obstructed.getTora());
		    assertNotSame("TODA should change", 3962, obstructed.getToda());
		    assertNotSame("ASDA should change", 3884, obstructed.getAsda());
		    assertNotSame("LDA should change", 3884, obstructed.getLda());
		}
		
		public void testAcrossBoundaryHighY() {
			Obstacle obs = new Obstacle("Debug", 3961, 220, 100, 50, 25, 180, 0, 0);
			Runway r = new Runway(27, Designator.R, 3884, 3962, 3884, 3884, 0, 240, 60);
            Aircraft a = new Aircraft("Debug", 300);
            Calculator calc = new Calculator(r, obs, a);
		    LogicalRunway obstructed = calc.calculate().getWestFacing();
		    assertNotSame("As obstacle is out of cleared and graded area, TORA should change", 3884, obstructed.getTora());
		    assertNotSame("TODA should change", 3962, obstructed.getToda());
		    assertNotSame("ASDA should change", 3884, obstructed.getAsda());
		    assertNotSame("LDA should change", 3884, obstructed.getLda());
		}
}
