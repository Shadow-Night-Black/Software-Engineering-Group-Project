package model;
public class Aircraft {
	private int blastAllowance;
	private String id;

    public Aircraft(String id, int blastAllowance) {
        this.setId(id);
    	this.blastAllowance = blastAllowance;
    }

    public static Aircraft getDefault() {
        return new Aircraft("Debug", 500);
    }

	public int getBlastAllowance() {
		return blastAllowance;
	}

	public void setBlastAllowance(int blastAllowance) {
		this.blastAllowance = blastAllowance;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
