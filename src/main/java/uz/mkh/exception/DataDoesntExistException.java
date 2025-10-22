package uz.mkh.exception;

public class DataDoesntExistException extends RuntimeException {
    public DataDoesntExistException(String message) {
        super(message);
    }
}
