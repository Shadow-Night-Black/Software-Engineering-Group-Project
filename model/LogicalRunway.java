package model;

public class LogicalRunway {

    private int direction;
    private Designator designator;
    private int tora, toda, asda, lda, resa, stripEnd, displacementThreshold;
    private boolean obstructed = false;
    private String sTora, sToda, sAsda, sLda;
    /*
     *  tora - the length of the runway available for take-off
     *  toda - the length of the runway (TORA) plus any Clearway (area beyond the runway that is considered free from obstructions)
     *  asda - the length of the runway (TORA) plus any Stopway (area that is not part of the TORA, but that can be used to safely stop an aircraft in an emergency)
     *  lda - the length of the runway available for landing.
     *  resa - An area symmetrical about the extended runway centreline and adjacent to the end of the strip primarily intended to reduce the risk of damage to an aircraft undershooting or overrunning the runway
     *  blast - A safety area behind an aircraft to prevent the engine blast from affecting any obstacles located behind it
     *  stripEnd - An area between the end of the runway and the end of the runway strip
     *  title - an exmaple would be 90L or 270L
     */
    private LogicalRunway(LogicalRunway r) {
        this(r.getDirection(), r.getDesignator(), r.getTora(), r.getToda(), r.getAsda(), r.getLda(), r.getDisplacementThreshold(), r.getResa(), r.getStripEnd());
        this.setObstructed(r.isObstrusted());
    }

    public LogicalRunway(int direction, Designator designator, int tora, int toda, int asda, int lda, int displacementThreshold, int resa, int stripEnd){
        this.direction = direction;
        this.designator = designator;
        this.setTora(tora);
        this.setToda(toda);
        this.setAsda(asda);
        this.setLda(lda);
        this.setDisplacementThreshold(displacementThreshold);
        this.setResa(resa);
        this.setStripEnd(stripEnd);
    }

    public String getTitle() {
        return String.format("%02d", direction) + designator.toString();
    }

    public Designator getDesignator() {
        return designator;
    }

    public int getDirection() {
        return direction;
    }

    public int getTora() {
        return tora;
    }

    public void setTora(int tora) {
        this.tora = tora;
    }

    public int getToda() {
        return toda;
    }

    public void setToda(int toda) {
        this.toda = toda;
    }

    public int getAsda() {
        return asda;
    }

    public void setAsda(int asda) {
        this.asda = asda;
    }

    public int getLda() {
        return lda;
    }

    public void setLda(int lda) {
        this.lda = lda;
    }

    public int getResa() {
        return resa;
    }

    public void setResa(int resa) {
        this.resa = resa;
    }

    public int getStripEnd() {
        return stripEnd;
    }

    public void setStripEnd(int stripEnd) {
        this.stripEnd = stripEnd;
    }

	public boolean isObstructed() {
		return obstructed;
	}

	public void setObstructed(boolean obstructed) {
		this.obstructed = obstructed;
	}

    public LogicalRunway clone() {
        return new LogicalRunway(this);
    }

    public int getDisplacementThreshold() {
        return displacementThreshold;
    }

    public void setDisplacementThreshold(int displacementThreshold) {
        this.displacementThreshold = displacementThreshold;
    }

    public boolean isObstrusted() {
        return obstructed;
    }

    public String getSToda() {
        return sToda;
    }

    public void setSToda(String sToda) {
        this.sToda = sToda;
    }

    public String getSTora() {
        return sTora;
    }

    public void setSTora(String sTora) {
        this.sTora = sTora;
    }

    public String getSAsda() {
        return sAsda;
    }

    public void setSAsda(String sAsda) {
        this.sAsda = sAsda;
    }

    public String getSLda() {
        return sLda;
    }

    public void setSLda(String sLda) {
        this.sLda = sLda;
    }
}
