package model;
public enum Designator {
    L("L"),
    C("C"),
    R("R"),
    N(" ");

    private final String designator;

    Designator(String designator) {
        this.designator = designator;
    }

    public static Designator fromString(String s) {
        switch (s.toUpperCase()) {
            case "R":
                return R;
            case "C":
                return C;
            case "L":
                return L;
            case " ":
                return N;
            default:
                throw new RuntimeException("Designator " + s + " does not exists!");
        }
    }

    public String toString() {
        return designator;
    }

    public Designator opposite() {
        switch(this) {
            case L:
                return R;
            case C:
                return C;
            case R:
                return L;
            case N:
                return N;
        }
        throw new RuntimeException("Invalid Designator!");
    }

}
