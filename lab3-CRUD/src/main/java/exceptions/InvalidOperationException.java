package exceptions;

public class InvalidOperationException extends SalonException {
    public InvalidOperationException(String message) {
        super("Invalid operation: " + message);
    }
}
