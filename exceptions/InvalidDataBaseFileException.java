package exceptions;

public class InvalidDataBaseFileException extends Exception {
    public InvalidDataBaseFileException(String reason) {
        super(reason);
    }
}

