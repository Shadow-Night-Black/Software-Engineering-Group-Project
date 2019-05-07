package exceptions;

public class InvalidDataBaseFormatException
    extends Exception {
    public InvalidDataBaseFormatException(String reason, Throwable t) {
        super(reason, t);
    }
}
